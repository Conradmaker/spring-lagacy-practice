<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
</head>
<body>

    <!-- 이쪽에 메뉴바 포함 할꺼임 -->
    <jsp:include page="../common/menubar.jsp"/>
    
    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>회원가입</h2>
            <br>

            <form action="insert.me" method="post" onsubmit="">
                <div class="form-group">
                    <label for="userId">* ID :</label>
                    <input type="text" class="form-control" id="userIdE" name="userId" placeholder="Please Enter ID" required><br>
                    <div id="check-result" style="font-size: 0.8em;display:none"></div>

                    <label for="userPwd">* Password :</label>
                    <input type="password" class="form-control" id="userPwdE" name="userPwd" placeholder="Please Enter Password" required><br>
                    
                    <label for="checkPwd">* Password Check :</label>
                    <input type="password" class="form-control" id="checkPwd" placeholder="Please Enter Password" required><br>
                    
                    <label for="userName">* Name :</label>
                    <input type="text" class="form-control" id="userName" name="userName" placeholder="Please Enter Name" required><br>
                    
                    <label for="email"> &nbsp; Email :</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Please Enter Email"><br>
                    
                    <label for="age"> &nbsp; Age :</label>
                    <input type="number" class="form-control" id="age" name="age" placeholder="Please Enter Age"><br>
                    
                    <label for="phone"> &nbsp; Phone :</label>
                    <input type="tel" class="form-control" id="phone" name="phone" placeholder="Please Enter Phone (-없이)"><br>
                    
                    <label for="address"> &nbsp; Address :</label>
                    <input type="text" class="form-control" id="address" name="address" placeholder="Please Enter Address"><br>
                    
                    <label> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Male" value="M">
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Female" value="F">
                    <label for="Female">여자</label><br>
                    
                </div>
                <br>
                <div class="btns" align="center">
                    <button id="enroll-btn" disabled type="submit" class="btn btn-primary">회원가입</button>
                    <button type="reset" class="btn btn-danger"> 초기화</button>
                </div>
            </form>
        </div>
        <br><br>
    </div>

    <script defer>
        document.querySelector('#userIdE').addEventListener('input',(e)=>{
            if(e.target.value.length >= 5){
                axios.post('idCheck.me',{userId:e.target.value})
                    .then((res)=>{
                        if(res.data===1){
                            document.querySelector('#check-result').style.display='block';
                            document.querySelector('#check-result').innerHTML = '중복된 아이디입니다.';
                            document.querySelector('#enroll-btn').setAttribute('disabled','true');
                        }else{
                            document.querySelector('#check-result').style.display='block';
                            document.querySelector('#check-result').innerHTML = '사용가능한 아이디.';
                            document.querySelector('#enroll-btn').setAttribute('disabled','false');
                        }
                    })
                    .catch((e)=>{
                        console.error(e)
                    })

            }else{
                document.querySelector('#check-result').style.display='block';
                document.querySelector('#check-result').innerHTML = '5글자이상 입력하세요';
                document.querySelector('#enroll-btn').setAttribute('disabled','true');

            }
        })
    </script>
    <!-- 이쪽에 푸터바 포함할꺼임 -->
    <jsp:include page="../common/footer.jsp"/>
    
</body>
</html>