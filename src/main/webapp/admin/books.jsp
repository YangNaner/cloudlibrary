<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>图书管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<%--    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>--%>
    <script src="${pageContext.request.contextPath}/js/my.js"></script>
</head>

<body class="hold-transition skin-red sidebar-mini">
<!-- .box-body -->
<div class="box-header with-border">
    <h3 class="box-title">图书借阅</h3>
</div>
<div class="box-body">
    <%--新增按钮：如果当前登录用户是管理员，页面展示新增按钮--%>
    <c:if test="${USER_SESSION.role =='ADMIN'}">
        <div class="pull-left">
            <div class="form-group form-inline">
                <div class="btn-group">
                    <button type="button" class="btn btn-default" title="新建" data-toggle="modal"
                            data-target="#addOrEditModal" onclick="resetFrom()"> 新增
                    </button>
                </div>
            </div>
        </div>
    </c:if>
    <%--新增按钮 /--%>
    <!--工具栏 数据搜索 -->
    <div class="box-tools pull-right">
        <div class="has-feedback">
            <form action="${pageContext.request.contextPath}/bookServlet?method=search" method="post">
                图书名称：<input name="name" value="${search.name}">&nbsp&nbsp&nbsp&nbsp
                图书作者：<input name="author" value="${search.author}">&nbsp&nbsp&nbsp&nbsp
                出版社：<input name="press" value="${search.press}">&nbsp&nbsp&nbsp&nbsp
                <input class="btn btn-default" type="submit" value="查询">
            </form>
        </div>
    </div>
    <!--工具栏 数据搜索 /-->
    <!-- 数据列表 -->
    <div class="table-box">
        <!-- 数据表格 -->
        <table id="dataList" class="table table-bordered table-striped table-hover dataTable text-center">
            <thead>
            <tr>
                <th class="sorting_asc">图书名称</th>
                <th class="sorting">图书作者</th>
                <th class="sorting">出版社</th>
                <th class="sorting">图书状态</th>
                <th class="sorting">借阅人</th>
                <th class="sorting">借阅时间</th>
                <th class="sorting">预计归还时间</th>
                <th class="text-center">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageBean.list}" var="book">
                <tr>
                    <td> ${book.name}</td>
                    <td>${book.author}</td>
                    <td>${book.press}</td>
                    <td>
                        <c:if test="${book.status ==0}">可借阅</c:if>
                        <c:if test="${book.status ==1}">借阅中</c:if>
                        <c:if test="${book.status ==2}">归还中</c:if>
                    </td>
                    <td>${book.borrower }</td>
                    <td>${book.borrowTime}</td>
                    <td>${book.returnTime}</td>
                    <td class="text-center">
                        <c:if test="${book.status ==0}">
                            <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                    data-target="#borrowModal" onclick="findBookById(${book.id},'borrow')"> 借阅
                            </button>
                            <c:if test="${USER_SESSION.role =='ADMIN'}">
                                <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                        data-target="#addOrEditModal" onclick="findBookById(${book.id},'edit')"> 编辑
                                </button>
                            </c:if>
                        </c:if>
                        <c:if test="${book.status ==1 ||book.status ==2}">
                            <button type="button" class="btn bg-olive btn-xs" disabled="true">借阅</button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!--数据表格/-->
    </div>
    <!--数据列表/-->
</div>
<!-- /.box-body -->

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
                <a href="${pageContext.request.contextPath }/bookServlet?method=search&currPage=${pageBean.currPage-1 }" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
        </c:if>


        <c:forEach begin="1" end="${pageBean.totalPage }" var="page">
            <c:if test="${page==pageBean.currPage }">
                <li class="active"><a href="${pageContext.request.contextPath }/bookServlet?method=search&currPage=${page }">${page }</a></li>
            </c:if>
            <c:if test="${page!=pageBean.currPage }">
                <li><a href="${pageContext.request.contextPath }/bookServlet?method=search&currPage=${page }">${page }</a></li>
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
                <a href="${pageContext.request.contextPath }/bookServlet?method=search&currPage=${pageBean.currPage+1 }" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </c:if>

    </ul>
</div>
<!-- 分页结束 -->
<%--引入存放模态窗口的页面--%>
<jsp:include page="/admin/book_modal.jsp"></jsp:include>

<!-- 添加和编辑图书的模态窗口 -->
<div class="modal fade" id="addOrEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="myModalLabel">图书信息</h3>
            </div>
            <div class="modal-body">
                <form id="addOrEditBook">
                    <span><input type="hidden" id="ebid" name="id"></span>
                    <table id="addOrEditTab" class="table table-bordered table-striped" width="800px">
                        <%--图书的id,不展示在页面--%>
                        <tr>
                            <td>图书名称</td>
                            <td><input class="form-control" placeholder="图书名称" name="name" id="ebname"></td>
                            <td>图书价格</td>
                            <td><input class="form-control" placeholder="图书价格" name="price" id="eprice"></td>
                        </tr>
                        <tr>
                            <td>出版社</td>
                            <td><input class="form-control" placeholder="出版社" name="press" id="ebpress"></td>
                            <td>作者</td>
                            <td><input class="form-control" placeholder="作者" name="author" id="ebauthor"></td>
                        </tr>
                        <tr>
                            <td>图书页数</td>
                            <td><input class="form-control" placeholder="图书页数" name="pagination" id="ebpagination"></td>
                            <td>上架状态</td>
                            <td>
                                <select class="form-control" id="ebstatus" name="status" >
                                    <option value="0">上架</option>
                                    <option value="3">下架</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" id="aoe" disabled onclick="addOrEdit()">保存
                </button>
                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
    </div>
</div>
</body>

</html>