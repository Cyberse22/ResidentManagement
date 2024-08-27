<%--
  Created by IntelliJ IDEA.
  User: cyberse
  Date: 5/27/2024
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .table td, .table th{
        text-align: center;
        vertical-align: middle;
    }
</style>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Invoice Details</title>
    </head>
    <body>
        <h1 class="text-center text-dark mt-1">QUẢN LÝ HÓA ĐƠN</h1>
        <c:if test="${param.error != null}">
            <div class="alert alert-danger"> 
                Xảy ra lỗi khi tạo hóa đơn
            </div>
        </c:if>
        <a class="btn btn-success" href="<c:url value='/invoice-residents/${residentId}/create'/>">Tạo mới</a>
        <table class="table" >
            <thead>
                <tr>
                    <th>Tên</th>
                    <th>Số tiền</th>
                    <th>Hạn đóng</th>
                    <th>Trạng thái</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${invoices}" var="i">
                    <tr>
                        <td>${i.name}</td>
                        <td>${i.amount}</td>
                        <td>${i.dueDate}</td>

                        <td>


                            <c:if test="${i.status eq 'unpaid'}">
                                <p class="text-danger">Chưa thanh toán</p>
                            </c:if>
                            <c:if test="${i.status eq 'waiting'}">
                                <p class="text-primary">Chờ xử lý</p>
                            </c:if>
                            <c:if test="${i.status eq 'paid'}">
                                <p class="text-success">Đã thanh toán</p>
                            </c:if>
                        </td>

                        <td>
                            <a class="btn btn-primary"
                               href="<c:url value="/invoice-residents/${i.residentId.id}/${i.id}"/>">
                                Chi tiết
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
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
    </body>
</html>
