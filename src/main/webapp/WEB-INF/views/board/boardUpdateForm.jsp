<%--
  Created by IntelliJ IDEA.
  User: yhg03
  Date: 2020-12-07
  Time: 오후 4:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <style>
        #updateForm>table{width:100%;}
        #updateForm>table *{ margin:5px;}
    </style>
</head>
<body>

<!-- 이쪽에 메뉴바 포함 할꺼임 -->
<jsp:include page="../common/menubar.jsp"/>

<div class="content">
    <br><br>
    <div class="innerOuter">
        <h2>게시글 수정하기</h2>
        <br>

        <form id="updateForm" method="post" action="update.bo" enctype="multipart/form-data">
            <input type="hidden" value="${b.boardNo}" name="boardNo">
            <table align="center">
                <tr>
                    <th><label for="title">제목</label></th>
                    <td><input type="text" id="title" class="form-control" name="boardTitle" value="${b.boardTitle}" required></td>
                </tr>
                <tr>
                    <th><label for="writer">작성자</label></th>
                    <td><input type="text" id="writer" class="form-control" value="${b.boardWriter}" name="boardWriter" readonly></td>
                </tr>
                <tr>
                    <th><label for="upfile">첨부파일</label></th>
                    <td>
                        <input type="file" id="upfile" class="form-control-file border" name="reupFile">
                        <c:if test="${!empty b.originName}">
                            현재 업로드된 파일 :
                            <a href="${b.changeName}" download="${b.originName}">flower.png</a>
                            <input type="hidden" value="${b.originName}" name="originName">
                            <input type="hidden" value="${b.changeName}" name="changeName">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <th colspan="2"><label for="content">내용</label></th>
                </tr>
                <tr>
                    <th colspan="2"><textarea class="form-control" required name="boardContent" id="content" rows="10" style="resize:none;">${b.boardContent}</textarea></th>
                </tr>
            </table>
            <br>

            <div align="center">
                <button type="submit" class="btn btn-primary">수정하기</button>
                <button type="button" class="btn btn-danger" onclick="javascript:history.go(-1);">이전으로</button>
            </div>
        </form>
    </div>
    <br><br>
</div>

<!-- 이쪽에 푸터바 포함할꺼임 -->
<jsp:include page="../common/footer.jsp"/>

</body>
</html>
