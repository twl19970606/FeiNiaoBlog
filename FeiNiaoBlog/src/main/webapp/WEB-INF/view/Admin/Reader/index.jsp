<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>

    <rapid:override name="title">
        - 读者列表
    </rapid:override>
<rapid:override name="header-style">
    <style>
        /*覆盖 layui*/

        .layui-table {
            margin-top: 0;
        }

        .layui-btn {
            margin: 2px 0!important;
        }
    </style>
</rapid:override>

<rapid:override name="content">
    <blockquote class="layui-elem-quote">
         <span class="layui-breadcrumb" lay-separator="/">
              <a href="/admin">首页</a>
              <a><cite>读者列表</cite></a>
        </span>
    </blockquote>

<table class="layui-table" lay-even lay-skin="nob" >
    <colgroup>
        <col width="100">
        <col width="100">
        <col width="50">
        <col width="100">
        <col width="50">
    </colgroup>
    <thead>
    <tr>
        <th>用户名</th>
        <th>电子邮件</th>
        <th>状态</th>
        <th>操作</th>
        <th>ID</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${readerList}" var="reader">
        <tr>
            <td>
                <img src="${reader.readerAvatar}" width="48" height="48">
                ${reader.readerName}
            </td>
            <td >
                ${reader.readerEmail}
            </td>
            <td>
                <c:choose>
                    <c:when test="${reader.readerStatus==0}">
                        <span style="color:#FF5722;">禁用</span>
                    </c:when>
                    <c:otherwise>
                        正常
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <a href="/admin/reader/delete/${reader.readerId}" class="layui-btn layui-btn-danger layui-btn-mini" onclick="return confirmDelete()">删除</a>
            </td>
            <td>
                ${reader.readerId}
            </td>
        </tr>

    </c:forEach>
    </tbody>
</table>

</rapid:override>
<rapid:override name="footer-script">
    <script>

    </script>
</rapid:override>

<%@ include file="../Public/framework.jsp"%>
