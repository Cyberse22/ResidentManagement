<%-- 
    Document   : header
    Created on : May 24, 2024, 5:11:07 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <div class="container-fluid">

        <a class="navbar-brand" href="#">
            <img src="https://res.cloudinary.com/dwdvnztnn/image/upload/v1716542240/2_k3qyxl.png" 
                 style="width:70px;" class="rounded-pill"> 
        </a>

        <c:url value="/" var="index"/>
        <c:url value="/feedbacks" var="feedbacks"/>
        <c:url value="/surveys" var="surveys"/>
        <c:url value="/admin" var="admin"/>
        <c:url value="/logout" var="logout"/>
        <c:url value="/visitor" var="visitor"/>
        <div class="collapse navbar-collapse" id="mynavbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="${index}">Resident management</a></li>
                <li class="nav-item"><a class="nav-link" href="${admin}">Đăng ký quản trị viên</a></li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">Tiện ích</a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="${index}">Quản lý cư dân</a></li>
                        <li><a class="dropdown-item" href="${visitor}">Quản lý khách</a></li>
                        <li><a class="dropdown-item" href="<c:url value="/electronic-lockers"/>">Quản lý tủ đồ</a></li>
                        <li><a class="dropdown-item" href="<c:url value='/invoice-residents'/>">Quản lý hóa đơn</a></li>
                        <li><a class="dropdown-item" href="${feedbacks}">Quản lý phản hồi</a></li>
                        <li><a class="dropdown-item" href="${surveys}">Quản lý khảo sát</a></li>
                    </ul>
                </li>
                <c:if test="${pageContext.request.userPrincipal.name != null }">
                    <li><a class="nav-link" href="${logout}">Đăng xuất</a></li>
                    </c:if>
            </ul>
            <form class="d-flex" >
                <input class="form-control me-2" type="text" placeholder="Search" name="kw">
                <button class="btn btn-primary" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>
