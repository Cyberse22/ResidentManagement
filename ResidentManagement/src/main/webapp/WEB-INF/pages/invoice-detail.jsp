<%--
    Document   : resident
    Created on : May 24, 2024, 10:18:49 PM
    Author     : ADMIN
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<h1 class="text-center text-dark mt-1">Chi tiết hóa đơn</h1>

<c:url value="/invoices" var="action" />
<form:form method="post" action="${action}" modelAttribute="invoice">
    <form:hidden id="id"  path="id" />
    <form:hidden id="residentId"  path="residentId" />

    <div class="form-floating mb-3 mt-3">
        <form:select class="form-control"  id="name" path="name">
            <form:option value="Phí gửi xe">Phí gửi xe</form:option>
            <form:option value="Phí điện nước">Phí điện nước</form:option>
            <form:option value="Phí sửa chữa">Phí sửa chữa</form:option>
            <form:option value="Phí dịch vụ">Phí dịch vụ</form:option>
        </form:select>
        
        <label for="name">Tên</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:input  class="form-control" type="number" id="amount"  placeholder="Số tiền" path="amount" />
        <label for="amount">Số tiền</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:input  class="form-control" type="date" id="dueDate" value="${invoice.dueDate}" placeholder="Hạn đóng" path="dueDate" />
        <label for="dueDate">Hạn đóng</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:select class="form-control" path="status">
            <form:option value="unpaid">Chưa thanh toán</form:option>
            <form:option value="paid">Đã thanh toán</form:option>
        </form:select>
        <label for="status">Trạng thái</label>
    </div>
        
        <c:if test="${invoice.paymentProve.length() > 0}">
            <img src="${invoice.paymentProve}" width="200" class="rounded" />
        </c:if>

    <div class="form-floating mb-3 mt-3">
        <button class="btn btn-primary mt-1" type="submit">
            Lưu
        </button>
    </div>
</form:form>

