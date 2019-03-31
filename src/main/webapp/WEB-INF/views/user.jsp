<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


Hi ${user}

<table  border='1' cellspacing='0'>
    <tr>
        <td>id</td>
        <td>name</td>
        <td>操作</td>
    </tr>
    <c:forEach items="${users.list}" var="s" varStatus="st">
        <tr>
            <td>${s.id}</td>
            <td>${s.name}</td>
            <td>
                <a href="javascript:;" class="delete_button" data-id="${s.id}">删除</a>
                <a href="/users/${s.id}/edit">修改</a>
            </td>
        </tr>
    </c:forEach>
</table>

<c:forEach items="${users.navigatepageNums}" var="s" varStatus="st">
    <a href="?page=${s}" <c:if test="${s==users.pageNum}">style="color: red"</c:if>>${s}</a>
</c:forEach>

<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $(document).on('click', '.delete_button', function () {
            var $this = $(this);
            $.ajax({
                url: "/users/" + $this.data('id'),
                data: {},
                type: "DELETE",
                dataType: 'json',
                beforeSend: function (xmlHttp) {
                },
                success: function (res) {
                    if (res.code == 0) {
                        $this.parents("tr").remove();
                    } else {
                        alert(res.msg);
                    }
                    console.log(res);
                },
                error: function (err) {

                },
//XMLHttpRequest 对象和一个描述请求类型的字符串。
                complete: function (XHR, TS) {
                }

            });
        });

    });
</script>