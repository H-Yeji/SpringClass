<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>hello</title>
</head>
<body>
    <p>data(EL문법) : ${myData}</p>
    <p>data(jstl문법 - java코드) : <%
        String getData = (String)request.getAttribute("myData");
        out.print(getData);
    %></p>
        <form action="/hello/servlet/jsp/post" method="post">
            <div>
                 이름 : ${param.name}
            </div>
            <div>
                이메일 : ${param.email}
            </div>
            <div>
                비밀번호 : ${param.password}
            </div>
        </form>
</body>
</html>