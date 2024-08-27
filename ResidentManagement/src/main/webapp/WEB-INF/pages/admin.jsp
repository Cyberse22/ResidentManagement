<%-- 
    Document   : admin
    Created on : May 28, 2024, 6:00:52 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>

<h1 class="text-center text-dark mt-1">THÊM QUẢN TRỊ VIÊN</h1>

<c:if test="${param.duplicate != null}">
    <div class="alert alert-danger"> 
        Người dùng này đã tồn tại, vui lòng kiểm tra lại thông tin
    </div>
</c:if>

<c:url value="/admin" var="action" />
<form:form method="post" action="${action}" modelAttribute="user">
    <form:errors path="*" element="div" cssClass="alert alert-danger" />
    
    
    <div class="form-floating mb-3 mt-3">
        <form:input  class="form-control"  id="firstName"  placeholder="Họ" path="firstName" />
        <label for="firstName">Họ</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control"  id="lastName"  placeholder="Tên" path="lastName" />
        <label for="lastName">Tên</label>
    </div>

    <!--gioi tinh-->
    <div class="form-floating mb-3 mt-3">
        <form:select class="form-select" id="gender"  path="gender">
            <option value="0">Nam</option>
            <option value="1">Nữ</option>
        </form:select>
        <label for="gender" class="form-label">Giới tính :</label>
    </div>
    <!--gioi tinh-->

    <div class="form-floating mb-3 mt-3">
        <form:input type="date"  class="form-control"  id="dob"   placeholder="Ngày sinh" path="dob" />
        <label for="dob">Ngày sinh</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control"  id="address"  placeholder="Địa chỉ" path="address" />
        <label for="address">Địa chỉ</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control"  id="phone"  placeholder="Số điện thoại" path="phone" />
        <label for="phone">Số điện thoại</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control"  id="Email"  placeholder="Email" path="email" />
        <label for="Email">Email</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control"  id="Username"  placeholder="Username" path="username" />
        <label for="Username">Username</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control"  id="Password"  placeholder="Password" path="password" />
        <label for="Password">Password</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <button class="btn btn-warning mt-1" type="submit">
            Thêm quản trị viên
        </button>
    </div>  


</form:form>