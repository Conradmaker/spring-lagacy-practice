<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: yhg03
  Date: 2020-12-02
  Time: 오후 7:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <style>
        table *{margin: 5px}
        table {width:100%}
    </style>
</head>
<body>
<!-- 이쪽에 메뉴바 포함 할꺼임 -->
<jsp:include page="../common/menubar.jsp"/>

<div class="content">
    <br><br>
    <div class="innerOuter">
        <h2>게시글 상세보기</h2>
        <br>

        <a class="btn btn-secondary" style="float:right" href="list.bo">목록으로</a>
        <br><br>
        <table id="contentArea" align="center" class="table">
            <tr>
                <th width="100">제목</th>
                <td colspan="3">${b.boardTitle}</td>
            </tr>
            <tr>
                <th>작성자</th>
                <td>${b.boardWriter}</td>
                <th>작성일</th>
                <td>${b.createDate}</td>
            </tr>
            <tr>
                <th>첨부파일</th>
                <td colspan="3">
                    <c:choose>
                        <c:when test="${empty b.originName}">
                            첨부파일이 없습니다.
                        </c:when>
                        <c:otherwise>
                            <a href="${b.changeName}" download="${b.originName}">${b.originName}</a>
                        </c:otherwise>
                    </c:choose>

                </td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3"></td>
            </tr>
            <tr>
                <td colspan="4"><p style="height:150px">${b.boardContent}</p></td>
            </tr>
        </table>
        <br>

        <c:if test="${loginUser.userId eq b.boardWriter}">
            <div align="center">
                <!-- 수정하기, 삭제하기 버튼은 이글이 본인글일 경우만 보여져야됨 -->
                <button type="button" class="btn btn-primary" onclick="postFormSubmit(1)">수정하기</button>
                <button type="button" class="btn btn-danger" onclick="postFormSubmit(2)">삭제하기</button>
            </div><br><br>
        </c:if>
        <form action="" method="post" id="postForm">
            <input type="hidden" name="bno" value="${b.boardNo}">
            <input type="hidden" name="fileName" value="${b.changeName}}">
        </form>

        <script>
            function postFormSubmit(num){
                var url = "";
                if(num === 1){
                    url = "updateForm.bo"
                }else if(num === 2){
                    url = "delete.bo"
                }
                $("#postForm").attr("action",url).submit()
            }
        </script>

        <!-- 댓글 기능은 나중에 ajax 배우고 접목시킬예정! 우선은 화면구현만 해놓음 -->
        <table id="replyArea" class="table" align="center">
            <thead>
            <tr>
                <th colspan="2">
                    <textarea class="form-control" name="" id="content" cols="55" rows="2" style="resize:none; width:100%"></textarea>
                </th>
                <th style="vertical-align: middle"><button class="btn btn-secondary">등록하기</button></th>
            </tr>
            <tr>
                <td colspan="3">댓글 (<span id="rcount">3</span>) </td>
            </tr>
            </thead>
            <tbody class="board__reply__box">

            </tbody>
        </table>
    </div>
    <br><br>
</div>
<script defer>
    const selectReplyList=()=>{
        axios.post('rlist.bo',{bno:${b.boardNo}})
            .then((res)=>{
                res.data.forEach(v=>{
                    const replyBox = document.createElement("tr")
                    replyBox.className='board__reply'
                    const writer = document.createElement("th")
                    writer.innerText = v.replyWriter
                    const content = document.createElement("td")
                    content.innerText = v.replyContent
                    const create = document.createElement("td")
                    create.innerText = v.createDate
                    replyBox.appendChild(writer);
                    replyBox.appendChild(content);
                    replyBox.appendChild(create);
                    document.querySelector('.board__reply__box').appendChild(replyBox);
                })
                console.log(res.data)
            })
            .catch((e)=>{
                console.error(e.response.data)
            })
    }
    selectReplyList();
</script>
<!-- 이쪽에 푸터바 포함할꺼임 -->
<jsp:include page="../common/footer.jsp"/>

</body>
</html>
