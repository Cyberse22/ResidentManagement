<%-- 
    Document   : survey
    Created on : May 26, 2024, 10:31:42 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<h1 class="text-center text-dark mt-1">QUẢN LÝ KHẢO SÁT</h1>


<div class="d-flex justify-content mb-2">
    <form class="d-flex mx-2" action="<c:url value="/surveys"/>">
        <input type="hidden" name="status" value=""/>
        <button class="btn btn-primary mt-2 mb-2" type="submit" >All</button>
    </form>

    <form class="d-flex mx-2" action="<c:url value="/surveys"/>">
        <input type="hidden" name="status" value="1"/>
        <button class="btn btn-success mt-2 mb-2" type="submit" >Không khóa</button>
    </form>

    <form class="d-flex mx-2" action="<c:url value="/surveys"/>">
        <input type="hidden" name="status" value="0"/>
        <button class="btn btn-warning mt-2 mb-2" type="submit" >Đã khóa</button>
    </form>
</div>

<table class="table">
    <tr>
        <th>#</th>
        <th>Tên khảo sát</th>
        <th>Ngày tạo</th>
        <th>Trạng thái</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${surveys}" var="s">
        <tr>
            <td>${s.id}</td>
            <td>${s.title}</td>
            <td>${s.createdDate}</td>

            <c:choose>
                <c:when test="${s.active == 1 }">
                    <td class="text-success">Chưa khóa</td>
                </c:when>
                <c:otherwise>
                    <td class="text-danger">Đã khóa</td>
                </c:otherwise>
            </c:choose>

            <td>
                <c:url value="/survey_detail/${s.id}" var="detail"/>
                <a class="btn btn-success" href="${detail}">Xem thống kê</a>
                
                
                <c:if test="${s.active==1}">
                    <c:url value="/api/surveys/${s.id}" var="blocked"/>
                    <button onclick="blockSurvey('${blocked}')" class="btn btn-warning ">Khóa khảo sát</button>
                </c:if>

            </td>
            <td>
                <c:url value="/api/surveys/delete/${s.id}" var="delete"/>
                <button onclick="confirmDeleteSurvey('${delete}')"  class="btn btn-danger">&times;</button>
            </td>

        </tr>
    </c:forEach>
</table>
    
<c:url value="/add_survey" var="addSurvey"/>
<a class="btn btn-primary mt-2 mb-2" href="${addSurvey}">Tạo khảo sát</a>

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
