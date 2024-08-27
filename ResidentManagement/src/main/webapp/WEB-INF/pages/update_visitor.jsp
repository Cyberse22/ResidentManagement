<%-- 
    Document   : update_vistor
    Created on : Jun 14, 2024, 2:20:04 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<h1 class="text-center text-dark mt-1">CHỈNH SỬA THÔNG TIN KHÁCH</h1>

<c:url value="/visitor" var="action" />
<form:form method="post" action="${action}" modelAttribute="visitor">
    <form:errors path="*" element="div" cssClass="alert alert-danger" />
    <div class="form-floating mb-3 mt-3">
        <form:input  class="form-control"  id="id"  placeholder="id" path="id" />
        <label for="id">Id</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input  class="form-control"  id="name"  placeholder="Tên khách" path="name" />
        <label for="name">Tên khách</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input  class="form-control"  id="relation"  placeholder="Quan hệ với chủ hộ" path="relation" />
        <label for="relation">Quan hệ với chủ hộ</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input  class="form-control"  id="createdDate"  placeholder="Ngày đăng ký" path="createdDate" />
        <label for="createdDate">Ngày đăng ký</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input  class="form-control"  id="updatedDate"  placeholder="Ngày chỉnh sửa" path="updatedDate" />
        <label for="updatedDate">Ngày chỉnh sửa</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:select class="form-select" id="status"  path="status">
            <option value="waiting">Đang chờ</option>
            <option value="confirm">Xác nhận</option>
        </form:select>
        <label for="status" class="form-label">Tình trạng: </label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <button class="btn btn-primary mt-1" type="submit">
            Xác nhận
        </button>
    </div>
</form:form>
