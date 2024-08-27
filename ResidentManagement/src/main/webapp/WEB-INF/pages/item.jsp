<%--
  Created by IntelliJ IDEA.
  User: cyberse
  Date: 5/27/2024
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<h2>Hàng trong tủ của cư dân: ${residentName}</h2>


<div class="d-flex justify-content mb-2">
    <form class="d-flex mx-2">
        <input type="hidden" name="status" value=""/>
        <button class="btn btn-primary mt-2 mb-2" type="submit" >All</button>
    </form>
    <form class="d-flex mx-2">
        <input type="hidden" name="status" value="1"/>
        <button class="btn btn-success mt-2 mb-2" type="submit" >Đã nhận</button>
    </form>
    <form class="d-flex mx-2">
        <input type="hidden" name="status" value="0"/>
        <button class="btn btn-warning mt-2 mb-2" type="submit" >Chưa nhận</button>
    </form>
</div>
<table class="table">

    <tr>
        <th>ID</th>
        <th>Mô tả hàng</th>
        <th>Trạng thái</th>
        <th>Ngày nhận</th>
        <th></th>
    </tr>


    <c:forEach items="${items}" var="item">
        <tr>
            <td>${item.id}</td>
            <td>${item.description}</td>
            <c:choose>
                <c:when test="${item.status == 0}">
                    <td class="text-danger">Chưa nhận</td>
                </c:when>
                <c:otherwise>
                    <td class="text-success">Đã nhận</td>
                </c:otherwise>
            </c:choose>
            <td class="createdDate" data-date="${item.createdDate}"></td>
            <td>
                <a class="btn btn-primary"
                   href="<c:url value="/electronic-lockers/${elId}/items/${item.id}"/>">
                    Chi tiết
                </a>

                <button class="btn btn-danger" onclick="deleteItem('<c:url value="/api/item/${item.id}/"/>')">Xóa hàng</button>
            </td>
        </tr>
    </c:forEach>

</table>

<nav>
    <ul class="pagination">
        <c:if test="${currentPage > 1}">
            <li class="page-item"><a class="page-link" href="?page=${currentPage - 1}">Previous</a></li>
        </c:if>
        <c:forEach var="i" begin="1" end="${totalPages}">
            <li class="page-item <c:if test='${i == currentPage}'>active</c:if>'">
                <a class="page-link" href="?page=${i}">${i}</a>
            </li>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <li class="page-item"><a class="page-link" href="?page=${currentPage + 1}">Next</a></li>
        </c:if>
    </ul>
</nav>

<a class="btn btn-primary" href="<c:url value='/electronic-lockers/${elId}/items/create'/>">Tạo mới</a>

<script src="<c:url value="/js/script.js" />"></script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Lấy tất cả các phần tử có class "createdDate"
        const dateElements1 = document.querySelectorAll('.createdDate');
        
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
        
        
    });
</script>