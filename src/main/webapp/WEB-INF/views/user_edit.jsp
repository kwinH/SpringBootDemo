<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form method="post" id="form">
    <input type="hidden" name="id" value="${user.getId()}">
    <input type="text" name="name" value="${user.getName()}" readonly>
    <input type="text" name="password">
    <input type="button" name="submit" id="submit" value="submit">
</form>


<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $(document).on('click', '#submit', function () {
            $.post('', $('#form').serialize(), function (data, status) {
                console.log(data);
                //  console.log(JSON.stringify(data))
                if (data.code == 0) {
                    alert('修改成功');
                    //location.href = "/users";
                } else {
                    alert(data.msg);
                }
            }, "json");
        });
    });

</script>