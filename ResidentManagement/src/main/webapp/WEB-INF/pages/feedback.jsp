<%-- 
    Document   : feedback
    Created on : May 26, 2024, 2:36:47 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<h1 class="text-center text-dark mt-1">QUẢN LÝ PHẢN HỒI</h1>

<div class="d-flex justify-content mb-2">
    <form class="d-flex mx-2" action="<c:url value="/feedbacks"/>">
        <input type="hidden" name="status" value=""/>
        <button class="btn btn-primary mt-2 mb-2" type="submit" >All</button>
    </form>
    <form class="d-flex mx-2" action="<c:url value="/feedbacks"/>">
        <input type="hidden" name="status" value="1"/>
        <button class="btn btn-success mt-2 mb-2" type="submit" >Đã xử lý</button>
    </form>
    <form class="d-flex mx-2" action="<c:url value="/feedbacks"/>">
        <input type="hidden" name="status" value="0"/>
        <button class="btn btn-warning mt-2 mb-2" type="submit" >Chưa xử lý</button>
    </form>
</div>


<table class="table">
    <tr>
        <th>#</th>
        <th>Nội dung</th>
        <th>Người gửi</th>
        <th>Trạng thái</th>
        <th>Ngày gửi</th>
        <th></th>

    </tr>
    <c:forEach items="${feedbacks}" var="f">
        <tr>
            <td>${f[0]}</td>
            <td>${f[1]}</td>
            <td>${f[2]} ${f[3]}</td>

            <c:choose>
                <c:when test="${f[4] == 0 }">
                    <td class="text-danger">Chưa xử lý</td>
                </c:when>
                <c:otherwise>
                    <td class="text-success">Đã xử lý</td>
                </c:otherwise>
            </c:choose>

            <td>${f[5]}</td>



            <td>
                <c:choose>
                    <c:when test="${f[4] == 0}">
                        <c:url value="/api/feedbacks/${f[0]}" var="solved"/>
                        <button onclick="solveFeedback('${solved}')" class="btn btn-success" >Xử lý</button>
                    </c:when>
                    <c:otherwise>
                        <c:url value="/api/feedbacks/delete/${f[0]}" var="delete"/>
                        <button onclick="confirmDeleteFeedback('${delete}')"  class="btn btn-danger" >&times;</button>
                    </c:otherwise>
                </c:choose>
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