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
        <title>Invoice</title>
    </head>
    <body>
        
        <h1 class="text-center text-dark mt-1">HÓA ĐƠN THEO CƯ DÂN</h1>
        <c:if test="${param.error != null}">
            <div class="alert alert-danger"> 
                Xảy ra lỗi khi tạo hóa đơn
            </div>
        </c:if>
        <a class="btn btn-success" href="<c:url value="/invoices/create-multiple"/>">Tạo hóa đơn hàng loạt</a>


        
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên cư dân</th>
                    <th>Hóa đơn đã thanh toán</th>
                    <th>Hóa đơn chờ thanh toán</th>
                    <th>Hóa đơn chưa thanh toán</th>
                    
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${residents}" var="r">
                    <tr>
                        <td>${r[0]}</td>
                        <td>${r[1]} ${r[2]}</td>
                        <td>${r[3]}</td>
                        <td>${r[4]}</td>
                        <td>${r[5]}</td>
                        <td>    
                            <a class="btn btn-primary"
                               href="<c:url value="/invoice-residents/${r[0]}/all"/>">
                                Danh sách hóa đơn
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