<%--
    博客顶部部分
    包括：顶部菜单，主要菜单(包括搜索按钮)，面包屑
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script src="js/jquery.min.js"></script>
<script>
    $(document).ready(function (){
        $('#personal').hide(); //打开页面隐藏下拉列表
        $('#reader').hover( //鼠标滑过导航栏目时
            function(){
                $('#personal').show(); //显示下拉列表
            },
            function(){
                $('#personal').hide(); //鼠标移开后隐藏下拉列表
            }
        );
        $('#personal').hover( //鼠标滑过下拉列表自身也要显示，防止无法点击下拉列表
            function(){
                $('#personal').show();
            },
            function(){
                $('#personal').hide(); //鼠标移开后隐藏下拉列表
            }
        );
        $('person_li').hover( //鼠标滑过下拉列表是改变当前栏目样式
            function(){
                $(this).css({'color':'red','background-color':'orange'});
            },
            function(){
                $(this).css({'color':'white','background-color':'black'});
            }
        );
    });
</script>
<%--导航 start--%>
<header id="masthead" class="site-header">
    <%--顶部菜单 start--%>
    <nav id="top-header">
        <div class="top-nav">
            <div class="user-login">
                <c:choose>
                    <c:when test="${sessionScope.reader==null&&sessionScope.user==null}">
                        <a href="/reader/login">登录</a>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${sessionScope.user==null}">
                                <p id="reader"><img src="${sessionScope.reader.readerAvatar}" width="20px" height="20px">${sessionScope.reader.readerName}</p>
                                <ul id="personal" style="display: none;position: absolute">
                                    <li class="person_li" style="width:180px;height: 30px;background-color: #ffffff"><a href="">个人中心</a></li>
                                    <li class="person_li" style="width:180px;height: 30px;"><a href="/reader/logout">登出</a></li>
                                </ul>
                            </c:when>
                            <c:otherwise>
                                <img src="${sessionScope.user.userAvatar}" width="20px" height="20px">${sessionScope.user.userName}
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="menu-topmenu-container">
                <ul id="menu-topmenu" class="top-menu">
                    <c:forEach items="${menuList}" var="m">
                        <li class="menu-item">
                            <c:if test="${m.menuLevel==1}">
                                <a href="${m.menuUrl}">
                                    <i class="${m.menuIcon}"></i>
                                    <span class="font-text">${m.menuName}&nbsp;</span>&nbsp;
                                </a>
                            </c:if>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </nav><!-- #top-header -->
    <%--顶部菜单 end--%>

    <%--主要菜单 satrt--%>
    <div id="menu-box">
        <div id="top-menu">
                <span class="nav-search">
                    <i class="fa fa-search"></i>
                </span>
            <div class="logo-site"><h1 class="site-title">
                <a href="/" title="${options.optionSiteTitle}">${options.optionSiteTitle}</a>
            </h1>
                <p class="site-description">${options.optionSiteDescrption}</p>
            </div><!-- .logo-site -->
            <div id="site-nav-wrap">
                <div id="sidr-close">
                    <a href="#sidr-close" class="toggle-sidr-close">×</a>
                </div>
                <nav id="site-nav" class="main-nav">
                    <a href="#sidr-main" id="navigation-toggle" class="bars">
                        <i class="fa fa-bars"></i>
                    </a>
                    <div class="menu-pcmenu-container">
                        <ul id="menu-pcmenu" class="down-menu nav-menu sf-js-enabled sf-arrows">

                            <li>
                                <a href="/">
                                    <i class="fa-home fa"></i>
                                    <span class="font-text">首页</span>
                                </a>
                            </li>

                            <c:forEach items="${allCategoryList}" var="category">
                                <c:if test="${category.categoryPid==0}">
                                    <li>
                                        <a href="/category/${category.categoryId}">
                                            <i class="${category.categoryIcon}"></i>
                                            <span class="font-text">${category.categoryName}&nbsp;</span>
                                        </a>
                                        <ul class="sub-menu">
                                            <c:forEach items="${allCategoryList}" var="cate">
                                                <c:if test="${cate.categoryPid==category.categoryId}">
                                                    <li>
                                                        <a href="/category/${cate.categoryId}"
                                                           target="_blank">${cate.categoryName}</a>
                                                    </li>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                    </li>
                                </c:if>
                            </c:forEach>
                            <%--主要菜单其余部分--%>
                            <c:forEach items="${menuList}" var="m">
                                <c:if test="${m.menuLevel == 2}">
                                    <li>
                                        <a href="${m.menuUrl}">
                                            <i class="${m.menuIcon}"></i>
                                            <span class="font-text">${m.menuName}&nbsp;</span>
                                        </a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>
                </nav>
            </div>
            <div class="clear"></div>
        </div><!-- #top-menu -->
    </div><!-- #menu-box -->
    <%--主要菜单 satrt--%>

</header>
<!-- #masthead -->
<%--导航 end start--%>

<%--搜索框 start--%>
<div id="search-main">
    <div class="searchbar">
        <form method="get" id="searchform" action="/search" accept-charset="UTF-8">
            <span>
                <input type="text" value="" name="keywords" id="s" placeholder="输入搜索内容" required="">
                <button type="submit" id="searchsubmit">搜索</button>
            </span>
        </form>
    </div>
    <div class="clear"></div>
</div>
<%--搜索框 end--%>

<rapid:block name="breadcrumb"></rapid:block>