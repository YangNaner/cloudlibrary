<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta charset="utf-8">
    <title>借阅记录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script src="${pageContext.request.contextPath}/js/my.js"></script>
</head>

<body class="hold-transition skin-red sidebar-mini">
<!-- .box-body -->
<div class="box-header with-border">
    <h3 class="box-title">借阅记录</h3>
</div>
<div class="box-body">
    <!--工具栏 数据搜索 -->
    <div class="box-tools pull-right">
        <div class="has-feedback">
            <form action="${pageContext.request.contextPath}/recordServlet?method=searchRecords" method="post">
                <c:if test="${USER_SESSION.role =='ADMIN'}">
                    借阅人：<input name="borrower" value="${search.borrower}">&nbsp&nbsp&nbsp&nbsp
                </c:if>
                图书名称：<input name="bookname" value="${search.bookname}">&nbsp&nbsp&nbsp&nbsp
                <button class="btn btn-default" type="submit">查询</button>
            </form>
        </div>
    </div>
    <!--工具栏 数据搜索 /-->
    <!-- 数据列表 -->
    <div class="table-box">
        <!--数据表格-->
        <table id="dataList" class="table table-bordered table-striped table-hover dataTable text-center">
            <thead>
            <tr>
                <th class="sorting">借阅人</th>
                <th class="sorting_asc">图书名称</th>
                <th class="sorting">借阅时间</th>
                <th class="sorting">归还时间</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageBean.list}" var="record">
                <tr>
                    <td>${record.borrower}</td>
                    <td>${record.bookname}</td>
                    <td>${record.borrowTime}</td>
                    <td>${record.remandTime}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!--数据表格/-->
        <!--分页 -->
        <div style="width: 380px; margin: 0 auto; margin-top: 1px;">
            <ul class="pagination" style="text-align: center; margin-top: 10px;">

                <!-- 判断当前页是否是第一页 -->
                <c:if test="${pageBean.currPage==1 }">
                    <li class="disabled">
                        <a href="javascript:void(0);" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>
                <c:if test="${pageBean.currPage!=1 }">
                    <li>
                        <a href="${pageContext.request.contextPath }/bookServlet?method=searchBorrowed&currPage=${pageBean.currPage-1 }" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>


                <c:forEach begin="1" end="${pageBean.totalPage }" var="page">
                    <c:if test="${page==pageBean.currPage }">
                        <li class="active"><a href="${pageContext.request.contextPath }/bookServlet?method=searchBorrowed&currPage=${page }">${page }</a></li>
                    </c:if>
                    <c:if test="${page!=pageBean.currPage }">
                        <li><a href="${pageContext.request.contextPath }/bookServlet?method=searchBorrowed&currPage=${page }">${page }</a></li>
                    </c:if>
                </c:forEach>

                <!-- 判断是否是最后一页 -->
                <c:if test="${pageBean.currPage==pageBean.totalPage }">
                    <li class="disabled">
                        <a href="javascript:void(0);" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
                <c:if test="${pageBean.currPage!=pageBean.totalPage }">
                    <li>
                        <a href="${pageContext.request.contextPath }/bookServlet?method=searchBorrowed&currPage=${pageBean.currPage+1 }" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>

            </ul>
        </div>
        <!-- 分页结束 -->
    </div>
    <!-- 数据列表 /-->
</div>
<!-- /.box-body -->
</body>

</html>