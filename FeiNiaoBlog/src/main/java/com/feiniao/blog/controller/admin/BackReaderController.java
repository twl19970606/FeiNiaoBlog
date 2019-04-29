package com.feiniao.blog.controller.admin;


import com.feiniao.blog.entity.Reader;
import com.feiniao.blog.service.ReaderService;
import com.feiniao.blog.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/reader")
public class BackReaderController {

    @Autowired
    private ReaderService readerService;

    /**
     * 后台用户列表显示
     *
     * @return
     */
    @RequestMapping(value = "")
    public ModelAndView readerList() {
        ModelAndView modelandview = new ModelAndView();

        List<Reader> readerList = readerService.listReader();
        modelandview.addObject("readerList", readerList);

        modelandview.setViewName("Admin/Reader/index");
        return modelandview;

    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String deleteReader(@PathVariable("id") Integer id) {
        readerService.deleteReader(id);
        return "redirect:/admin/reader";
    }

    /**
     * 发送邮件
     *
     * @param readerEmail
     * @return
     */
    @RequestMapping(value = "/sendEmail")
    public String sendEmail(@RequestParam("readerEmail") String readerEmail) {
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.sendMail(readerEmail);
        return "redirect:/admin/reader";
    }

}
