<%-- 
    Document   : new_survey
    Created on : May 27, 2024, 4:20:18 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<h1 class="text-center text-dark mt-1">TẠO KHẢO SÁT</h1>



<c:url value="/add_survey" var="action" />
<form:form method="post" action="${action}"  modelAttribute="survey" id="surveyForm">
    <form:errors path="*" element="div" cssClass="alert alert-danger" />
    
    <div id="alert">
        
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input  class="form-control"   id="title"   placeholder="Vd: Khảo sát đợt 1 " path="title" />
        <label for="title">Đặt tựa đề cho lần khảo sát</label>
    </div>

</form:form>

<div class="d-flex justify-content mb-2">
    <button type="button" onclick="addInput()" class="btn btn-secondary d-flex mx-2">Thêm câu hỏi</button>
</div>

<div id="container">

</div>

<div class="form-floating mb-3 mt-3">
    <button class="btn btn-primary mt-1" type="submit" onclick="submitForm()">
        Tạo khảo sát
    </button>
</div>   


<script>
    var questionCount = 0;
    var contentsList = [];

    function addInput() {
        questionCount++;

        const container = document.getElementById('container');
        const newInput = document.createElement('input');


        newInput.type = 'text';
        newInput.name = 'content' + questionCount;
        newInput.id = 'questions' + questionCount;
        newInput.placeholder = 'Câu hỏi ' + questionCount;
        newInput.classList.add('form-control', 'mt-2', 'mb-2');

        container.appendChild(newInput);

    }

    function submitForm() {
        
        const title = document.getElementById('title');

        for (var i = 1, max = questionCount; i <= max; i++) {
            var ct = document.getElementById('questions' + i);
            contentsList.push(ct.value);
        }

        // Thêm danh sách contentsList vào form
        var form = document.getElementById('surveyForm');
        var input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'contentsList';
        input.value = JSON.stringify(contentsList); // Chuyển danh sách thành chuỗi JSON để gửi
        form.appendChild(input);
        
        if(title.value !== null && title.value !== ""){
            document.getElementById('surveyForm').submit();
        }else
        {
            const alertContainer = document.getElementById('alert');
            alertContainer.className = "alert alert-danger";
            alertContainer.textContent = "Vui lòng nhập tựa đề cho đợt khảo sát";
        }
        
        
        
    }

</script>