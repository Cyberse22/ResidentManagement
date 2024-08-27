<%--
    Document   : resident
    Created on : May 24, 2024, 10:18:49 PM
    Author     : ADMIN
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<h1 class="text-center text-dark mt-1">Chi tiết hàng</h1>


<c:if test="${param.error != null}">
    <div class="alert alert-danger"> 
        Vui lòng nhập thông tin hàng
    </div>
</c:if>

<c:url value="/electronic-lockers/${elId}/items/" var="action" />
<form:form method="post" action="${action}" modelAttribute="item">
    <form:hidden id="id"  path="id" />
    <form:hidden id="electronicLockerId" path="electronicLockerId" />
    
    <form:errors path="*" element="div" cssClass="alert alert-danger" />
    
    <div class="form-floating mb-3 mt-3">
        <form:input  class="form-control"  id="description"  placeholder="Mô tả" path="description" />
        <label for="description">Mô tả</label>
    </div>


    <div class="form-floating mb-3 mt-3">

        <form:select class="form-control" path="status">
            <form:option value="0">Chưa nhận</form:option>
            <form:option value="1">Đã nhận</form:option>
        </form:select>

        <label for="status">Trạng thái</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <button class="btn btn-primary mt-1" type="submit">
            Lưu
        </button>
    </div>


</form:form>

