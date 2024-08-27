
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<h1 class="text-center text-dark mt-1">Chi tiết hóa đơn</h1>
<c:if test="${param.error != null}">
    <div class="alert alert-danger"> 
        Xảy ra lỗi khi tạo hóa đơn
    </div>
</c:if>
<c:url value="/invoices/create-multiple" var="action" />
<form:form method="post" action='${action}' modelAttribute="invoice">
    <div class="form-floating mb-3 mt-3">
        <div class="form-floating mb-3 mt-3">
            <form:select class="form-control"  id="name" path="name">
                <form:option value="Phí gửi xe">Phí gửi xe</form:option>
                <form:option value="Phí điện nước">Phí điện nước</form:option>
                <form:option value="Phí sửa chữa">Phí sửa chữa</form:option>
                <form:option value="Phí dịch vụ">Phí dịch vụ</form:option>
            </form:select>
            <label for="name">Loại phí</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input  class="form-control" type="number" id="amount"  placeholder="Số tiền" path="amount" />
            <label for="amount">Số tiền</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input  class="form-control" type="date" id="dueDate" value="${invoice.dueDate}" placeholder="Hạn đóng" path="dueDate" />
            <label for="dueDate">Hạn đóng</label>
        </div>


        <span>Chọn cư dân: </span>
        <select required="true" class="selectpicker" multiple data-live-search="true" id="residents" name="residents">
            <c:forEach items='${residents}' var="r">
                <c:set var="label" value='${r.userId.lastName} ${r.userId.firstName} - ${r.userId.username}'/>
                <c:set var="value" value='${r.id}'/>
                <option value="${value}">${label}</option>
            </c:forEach>
        </select>

        <div class="form-floating mb-3 mt-3">
            <button class="btn btn-primary mt-1" type="submit">
                Lưu
            </button>
        </form:form>
    </div>




