package com.feiniao.blog.controller.home;

import com.feiniao.blog.dto.Response;
import com.feiniao.blog.entity.*;
import com.feiniao.blog.enums.NoticeStatus;
import com.feiniao.blog.service.*;
import com.feiniao.blog.util.CodeUtil;
import com.feiniao.blog.util.MD5Util;
import com.github.pagehelper.PageInfo;
import com.feiniao.blog.entity.Link;
import com.feiniao.blog.enums.ArticleStatus;
import com.feiniao.blog.enums.LinkStatus;
import com.feiniao.blog.util.RedisUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReaderService readerService;

    @RequestMapping(value = {"/", "/article"})
    public String index(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                        @RequestParam(required = false, defaultValue = "10") Integer pageSize, Model model,
                        HttpServletRequest request) {
        //获得访问ip
        String remoteAddr = request.getRemoteAddr();
        HttpSession session = request.getSession();
        session.setAttribute("userIp",remoteAddr);

        HashMap<String, Object> criteria = new HashMap<>(1);
        criteria.put("status", ArticleStatus.PUBLISH.getValue());
        //文章列表
        PageInfo<Article> articleList = articleService.pageArticle(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", articleList);

        //公告
        List<Notice> noticeList = noticeService.listNotice(NoticeStatus.NORMAL.getValue());
        model.addAttribute("noticeList", noticeList);
        //友情链接
        List<Link> linkList = linkService.listLink(LinkStatus.NORMAL.getValue());
        model.addAttribute("linkList", linkList);

        //侧边栏显示
        //标签列表显示
        List<Tag> allTagList = tagService.listTag();
        model.addAttribute("allTagList", allTagList);
        //最新评论
        List<Comment> recentCommentList = commentService.listRecentComment(10);
        model.addAttribute("recentCommentList", recentCommentList);
        model.addAttribute("pageUrlPrefix", "/article?pageIndex");
        return "Home/index";
    }

    @RequestMapping(value = "/search")
    public String search(
            @RequestParam("keywords") String keywords,
            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize, Model model) {
        //文章列表
        HashMap<String, Object> criteria = new HashMap<>(2);
        criteria.put("status", ArticleStatus.PUBLISH.getValue());
        criteria.put("keywords", keywords);
        PageInfo<Article> articlePageInfo = articleService.pageArticle(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", articlePageInfo);

        //侧边栏显示

        //标签列表显示
        List<Tag> allTagList = tagService.listTag();
        model.addAttribute("allTagList", allTagList);

        //获得随机文章
        List<Article> randomArticleList = articleService.listRandomArticle(8);
        model.addAttribute("randomArticleList", randomArticleList);

        //获得热评文章
        List<Article> mostCommentArticleList = articleService.listArticleByCommentCount(8);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);

        //最新评论
        List<Comment> recentCommentList = commentService.listRecentComment(10);
        model.addAttribute("recentCommentList", recentCommentList);
        model.addAttribute("pageUrlPrefix", "/search?pageIndex");
        return "Home/Page/search";
    }

    @RequestMapping("/404")
    public String NotFound(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);
        return "Home/Error/404";
    }

    @RequestMapping("/500")
    public String ServerError(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);
        return "Home/Error/500";
    }

    @Autowired(required = false)
    private RedisUtil redisUtil;

    @RequestMapping("/testRedis")
    @ResponseBody
    public String testRedis(@RequestParam(name = "key") String key) {
        return (String) redisUtil.get(key);
    }

    @RequestMapping("/reader/login")
    public String loginPage() {
        return "Home/Page/reader_login";
    }

    @RequestMapping("/reader/register")
    public String registerPage() {
        return "Home/Page/reader_register";
    }

    /**
     * 用户注册
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/reader/registerVerify")
    @ResponseBody
    public String register(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        Response result = null;
        if(readerService.getReaderByEmail(email)!=null){
            result = Response.no("该邮箱已被注册，请登录!");
        }else if(readerService.getReaderByName(name)!=null){
            result = Response.no("注册失败，用户名已存在！");
        }else{
            result = Response.yes();
            Reader reader = new Reader();
            reader.setReaderName(name);
            reader.setReaderPass(MD5Util.getMD5String(password));
            reader.setReaderStatus(1);
            reader.setReaderEmail(email);
            reader.setReaderAvatar("/img/avatar.jpg");
            readerService.insertReader(reader);
        }
        String res = new JSONObject(result).toString();
        return res;
    }

    /**
     * 登录验证
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/reader/loginVerify",method = RequestMethod.POST)
    @ResponseBody
    public String loginVerify(HttpServletRequest request, HttpServletResponse response)  {
        String readerName = request.getParameter("readerName");
        String readerPass = request.getParameter("readerPass");
        String rememberme = request.getParameter("rememberme");
        Reader reader = readerService.getReaderByNameOrEmail(readerName);
        Response result = null;
        if(reader==null) {
            result = Response.no("用户不存在！");
        } else if(!reader.getReaderPass().equals(MD5Util.getMD5String(readerPass))) {
            result = Response.no("密码错误，请重新输入！");
        } else {
            //登录成功
            result = Response.yes();
            //添加session
            request.getSession().setAttribute("reader", reader);
            //添加cookie
            if(rememberme!=null) {
                //创建两个Cookie对象
                Cookie nameCookie = new Cookie("readerName", readerName);
                //设置Cookie的有效期为3天
                nameCookie.setMaxAge(60 * 60 * 24 * 3);
                Cookie pwdCookie = new Cookie("readerPass", readerPass);
                pwdCookie.setMaxAge(60 * 60 * 24 * 3);
                response.addCookie(nameCookie);
                response.addCookie(pwdCookie);
            }


        }
        String res = new JSONObject(result).toString();
        return res;
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/reader/logout")
    public String logout(HttpSession session)  {
        session.removeAttribute("reader");
        session.invalidate();
        return "redirect:/article";
    }

    /**
     * 生成验证码
     *
     */
    @RequestMapping(value = "/getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response){
        // 调用工具类生成的验证码和验证码图片
        Map<String, Object> codeMap = CodeUtil.generateCodeAndPic();

        // 将四位数字的验证码保存到Session中。
        HttpSession session = request.getSession();
        session.setAttribute("code", codeMap.get("code").toString());

        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", -1);

        response.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos;
        try {
            sos = response.getOutputStream();
            ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}




