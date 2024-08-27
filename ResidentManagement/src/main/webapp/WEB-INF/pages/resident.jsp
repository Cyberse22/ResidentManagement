<%-- 
    Document   : resident
    Created on : May 24, 2024, 10:18:49 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<h1 class="text-center text-dark mt-1">THÊM CƯ DÂN</h1>

<c:if test="${param.duplicate != null}">
    <div class="alert alert-danger"> 
        Người dùng này đã tồn tại, vui lòng kiểm tra lại thông tin
    </div>
</c:if>

<c:if test="${param.error != null}">
    <div class="alert alert-danger"> 
        Cập nhật thất bại
    </div>
</c:if>

<c:url value="/resident" var="action"/>
<form:form method="post" action="${action}" modelAttribute="user">
    <form:errors path="*" element="div" cssClass="alert alert-danger" />


    <c:choose>
        <c:when test="${user.id != null}">
            <div class="form-floating mb-3 mt-3">
                <form:input type="hidden" class="form-control"  id="id"  placeholder="id" path="id" />
            </div>
            <div class="form-floating mb-3 mt-3">
                <form:input type="hidden" class="form-control"  id="avatar"  placeholder="avatar" path="avatar" />
            </div>
            <div class="form-floating mb-3 mt-3">
                <form:input type="hidden" class="form-control"  id="role"  placeholder="role" path="role" />
            </div>
            <div class="form-floating mb-3 mt-3">
                <form:input type="hidden" class="form-control"  id="active"  placeholder="active" path="active" />
            </div>
            <div class="form-floating mb-3 mt-3">
                <form:input type="hidden" class="form-control"  id="id"  placeholder="notificationToken" path="notificationToken" />
            </div>
        </c:when>
    </c:choose>

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


    <c:choose>
        <c:when test="${user.id == null}">
            <div class="form-floating mb-3 mt-3">
                <button class="btn btn-primary mt-1" type="submit">
                    Thêm cư dân
                </button>
            </div>  
        </c:when>
        <c:otherwise>
            <div class="form-floating mb-3 mt-3">
                <button class="btn btn-primary mt-1" type="submit">
                    Xác nhận chỉnh sửa
                </button>
            </div>  
        </c:otherwise>
    </c:choose>



</form:form>

