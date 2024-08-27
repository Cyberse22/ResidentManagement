<%-- 
    Document   : index.
    Created on : May 24, 2024, 11:32:11 AM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<h1 class="text-center text-dark mt-1">QUẢN LÝ CƯ DÂN</h1>
<div class="d-flex justify-content mb-2">
    <form class="d-flex mx-2" action="<c:url value="/"/>">
        <input type="hidden" name="block" value="0"/>
        <button class="btn btn-dark mt-2 mb-2" type="submit" >Cư dân đã khóa</button>
    </form>

    <c:url value="/resident" var="resident"/>
    <a class="btn btn-primary mt-2 mb-2 mx-2" href="${resident}">Thêm cư dân</a>
</div>

<table class="table">
    <tr>
        <th>#</th>
        <th>Họ và Tên</th>
        <th>Ngày sinh</th>
        <th>Địa chỉ</th>
        <th>Sdt</th>
        <th></th>
    </tr>
    <c:forEach items="${residents}" var="r">
        <tr>
            <td>${r[0]}</td>
            <td>${r[1]} ${r[2]}</td>
            <td>${r[3]}</td>
            <td>${r[4]}</td>
            <td>${r[5]}</td>
            <td>
                <c:if test="${r[6]==1}">
                    <c:url value="/api/resident/${r[7]}" var="deleteUrl"/>
                    <button onclick="confirmDelete('${deleteUrl}')" class="btn btn-warning" >Khóa cư dân</button>
                </c:if>
                <a href="<c:url value="/resident/${r[0]}/"/>"  class="btn btn-primary" >&#9881;</a>

                <c:url value="/api/resident/delete/${r[0]}" var="delete"/>
                <button onclick="confirmDeleteResident('${delete}')" class="btn btn-danger">&times;</button>
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

<script src="<c:url value="/js/script.js" />"></script>