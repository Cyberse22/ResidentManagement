<%-- 
    Document   : visitor.jsp
    Created on : Jun 14, 2024, 1:25:03 AM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<h1 class="text-center text-dark mt-1">QUẢN LÝ KHÁCH</h1>

<div class="d-flex justify-content mb-2">
    <form class="d-flex mx-2" action="<c:url value="/visitor"/>">
        <input type="hidden" name="status" value=""/>
        <button class="btn btn-primary mt-2 mb-2" type="submit" >All</button>
    </form>
    <form class="d-flex mx-2" action="<c:url value="/visitor"/>">
        <input type="hidden" name="status" value="confirm"/>
        <button class="btn btn-success mt-2 mb-2" type="submit" >Đã xác nhận</button>
    </form>
    <form class="d-flex mx-2" action="<c:url value="/visitor"/>">
        <input type="hidden" name="status" value="waiting"/>
        <button class="btn btn-warning mt-2 mb-2" type="submit" >Đang chờ</button>
    </form>
</div>

<table class="table">
    <tr>
        <th>#</th>
        <th>Tên khách</th>
        <th>Thời gian đăng ký</th>
        <th>Thời gian chỉnh sửa</th>
        <th>Trạng thái</th>
        <th></th>
    </tr>
    <c:forEach items="${visitors}" var="v">
        <tr>
            <td>${v.id}</td>
            <td>${v.name}</td>
            <td class="createdDate" data-date="${v.createdDate}"></td>
            <td class="updatedDate" data-date="${v.updatedDate}"></td>
            <c:choose>
                <c:when test="${v.status eq 'waiting'}">
                    <td class="text-danger">Đang chờ</td>
                </c:when>
                <c:otherwise>
                    <td class="text-success">Đã xác nhận</td>
                </c:otherwise>
            </c:choose>
            
            <td>
                <a class="btn btn-primary" href="<c:url value="/visitor/${v.id}"/>">Chỉnh sửa</a>
                <c:url value="/api/visitor/${v.id}/" var="delete"/>
                <button class="btn btn-danger" onclick="deleteVisitor('${delete}')">Xóa</button>
            </td>
        </tr>
    </c:forEach>

</table>




<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Lấy tất cả các phần tử có class "createdDate"
        const dateElements1 = document.querySelectorAll('.createdDate');
        const dateElements2 = document.querySelectorAll('.updatedDate');

        dateElements1.forEach(function (element) {
            // Lấy dữ liệu ngày từ thuộc tính data-date
            const originalDate = element.getAttribute('data-date');

            // Chuyển đổi dữ liệu ngày thành đối tượng Date
            const date = new Date(originalDate);

            // Định dạng lại ngày theo ý muốn, ví dụ: "dd/mm/yyyy hh:mm:ss"
            const formattedDate = date.toLocaleString('vi-VN', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit'
            });

            // Hiển thị ngày đã định dạng lên trang HTML
            element.textContent = formattedDate;
        });
        
        dateElements2.forEach(function (element) {
            // Lấy dữ liệu ngày từ thuộc tính data-date
            const originalDate = element.getAttribute('data-date');

            // Chuyển đổi dữ liệu ngày thành đối tượng Date
            const date = new Date(originalDate);

            // Định dạng lại ngày theo ý muốn, ví dụ: "dd/mm/yyyy hh:mm:ss"
            const formattedDate = date.toLocaleString('vi-VN', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit'
            });

            // Hiển thị ngày đã định dạng lên trang HTML
            element.textContent = formattedDate;
        });
    });
</script>
<script src="<c:url value="/js/script.js" />"></script>