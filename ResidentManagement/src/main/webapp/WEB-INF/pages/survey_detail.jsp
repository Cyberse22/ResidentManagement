<%-- 
    Document   : survey_detail
    Created on : May 26, 2024, 11:56:34 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<h1 class="text-center text-dark mt-1">${stats[0][0]}</h1>

<c:forEach items="${stats}" var="s">
    <h3>Khảo sát: ${s[1]}</h3>
    <div class="row mt-4 mb-5">
        <div class="col-md-7 col-12">
            <div>
                <canvas id="myChart${s[4]}"></canvas>
            </div>
        </div>
        <div class="col-md-5 col-12">
            <table class="table table-bordered ">
                <tr>
                    <th>Đánh giá</th>
                    <th>Tốt</th>
                    <th>Không tốt</th>
                </tr>
                <tr>
                    <td></td>
                    <td>${s[2]}</td>
                    <td>${s[3]}</td>
                </tr>
            </table>
        </div>
    </div>

    <script>
        window.onload = function () {
        <c:forEach var="s" items="${stats}">
            const ctx${s[4]} = document.getElementById('myChart${s[4]}');
            new Chart(ctx${s[4]}, {
                type: 'bar',
                data: {
                    labels: ['Tốt ', 'Không tốt'],
                    datasets: [{
                            label: '${s[1]}',
                            data: [${s[2]}, ${s[3]}],
                            backgroundColor: ['#00AEEF', '#FF6D6A']
                        }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        </c:forEach>
        };
    </script>

</c:forEach>
