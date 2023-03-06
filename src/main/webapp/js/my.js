//借阅窗口中时间标签的内容改变时执行
function cg() {
    $("#savemsg").attr("disabled", false);
    var rt = $("#time").val().split("-");
    var ny = new Date().getFullYear();
    var nm = new Date().getMonth() + 1;
    var nd = new Date().getDate();
    if (rt[0] < ny) {
        alert("日期不能早于今天")
        $("#savemsg").attr("disabled", true);
    } else if (rt[0] == ny) {
        if (rt[1] < nm) {
            alert("日期不能早于今天")
            $("#savemsg").attr("disabled", true);
        } else if (rt[1] == nm) {
            if (rt[2] < nd) {
                alert("日期不能早于今天")
                $("#savemsg").attr("disabled", true);
            } else {
                $("#savemsg").attr("disabled", false);
            }
        }
    }
}
//点击借阅图书时执行
function borrow() {
    var url =getProjectPath()+ "/bookServlet?method=borrowBook";
    $.post(url, $("#borrowBook").serialize(), function (response) {
        var result = eval('(' + response + ')');
        alert(result.message)
        if (result.flag == true) {
            window.location.href = getProjectPath()+"/bookServlet?method=search";
        }
    })
}

//重置添加和编辑窗口中输入框的内容
function resetFrom() {
    $("#aoe").attr("disabled",true)
    var $vals=$("#addOrEditBook input");
    $vals.each(function(){
        $(this).attr("style","").val("")
    });
}
//重置添加和编辑窗口中输入框的样式
function resetStyle() {
    $("#aoe").attr("disabled",false)
    var $vals=$("#addOrEditBook input");
    $vals.each(function(){
        $(this).attr("style","")
    });
}
//查询id对应的图书信息，并将图书信息回显到编辑或借阅的窗口中
function findBookById(id,doname) {
    resetStyle()
    var url = getProjectPath()+"/bookServlet?method=findById&id=" + id;
    $.get(url, function (response) {
        var result = eval('(' + response + ')');
        if(result.flag==true){
            //如果是编辑图书，将获取的图书信息回显到编辑的窗口中
            if(doname=='edit'){
                $("#ebid").val(result.data.id);
                $("#ebname").val(result.data.name);
                $("#eprice").val(result.data.price);
                $("#ebpress").val(result.data.press);
                $("#ebauthor").val(result.data.author);
                $("#ebpagination").val(result.data.pagination);
                $("#ebprice").val(result.data.price);
                $("#ebstatus").val(result.data.status);
            }
            //如果是借阅图书，将获取的图书信息回显到借阅的窗口中
            if(doname=='borrow'){
                $("#savemsg").attr("disabled",true)
                $("#time").val("");
                $("#bid").val(result.data.id);
                $("#bname").val(result.data.name);
                $("#bprice").val(result.data.price);
                $("#bpress").val(result.data.press);
                $("#bauthor").val(result.data.author);
                $("#bpagination").val(result.data.pagination);
            }
        }else{
            alert(result.message);
        }

    })
}
//点击添加或编辑的窗口的确定按钮时，提交图书信息
function addOrEdit() {
    //获取表单中图书id的内容
    var ebid = $("#ebid").val();
    //如果表单中有图书id的内容，说明本次为编辑操作
    if (ebid > 0) {
        var url = getProjectPath()+"/bookServlet?method=editBook";
        $.post(url, $("#addOrEditBook").serialize(), function (response) {
            var result = eval('(' + response + ')');
            alert(result.message)
            if (result.flag == true) {
                window.location.href = getProjectPath()+"/bookServlet?method=search";
            }
        })
    }
    //如果表单中没有图书id，说明本次为添加操作
    else {
        var url = getProjectPath()+"/bookServlet?method=addBook";
        $.post(url, $("#addOrEditBook").serialize(), function (response) {
            var result = eval('(' + response + ')');
            alert(result.message)
            if (result.flag == true) {
                window.location.href = getProjectPath()+"/bookServlet?method=search";
            }
        })
    }
}
//归还图书时执行
function returnBook(bid) {
    var r = confirm("确定归还图书?");
    if (r) {
        var url = getProjectPath()+"/bookServlet?method=returnBook&id=" + bid;
        $.get(url, function (response) {
            var result = eval('(' + response + ')');
            alert(result.message)
            //还书成功时，刷新当前借阅的列表数据
            if (result.flag == true) {
                window.location.href = getProjectPath()+"/bookServlet?method=searchBorrowed";
            }
        })
    }
}
//确认图书已经归还
function returnConfirm(bid) {
    var r = confirm("确定图书已归还?");
    if (r) {
        var url = getProjectPath()+"/bookServlet?method=returnConfirm&id=" + bid;
        $.get(url, function (response) {
            var result = eval('(' + response + ')');
            alert(result.message)
            //还书确认成功时，刷新当前借阅的列表数据
            if (result.flag == true) {
                window.location.href = getProjectPath()+"/bookServlet?method=searchBorrowed";
            }
        })
    }
}
//检查图书信息的窗口中，图书信息填写是否完整
function checkval(){
    var $inputs=$("#addOrEditTab input")
    var count=0;
    $inputs.each(function () {
        if($(this).val()==''||$(this).val()=="不能为空！"){
            count+=1;
        }
    })
    //如果全部输入框都填写完整，解除确认按钮的禁用状态
    if(count==0){
        $("#aoe").attr("disabled",false)
    }
}
//页面加载完成后，给图书模态窗口的输入框绑定失去焦点和获取焦点事件
$(function() {
    var $inputs=$("#addOrEditBook input")
    var eisbn="";
    $inputs.each(function () {
        //给输入框绑定失去焦点事件
        $(this).blur(function () {
            if($(this).val()==''){
                $("#aoe").attr("disabled",true)
                $(this).attr("style","color:red").val("不能为空！")
            }
            else{
                checkval()
            }
        }).focus(function () {
            if($(this).val()=='不能为空！'){
                $(this).attr("style","").val("")
            }else{
                $(this).attr("style","")
            }
        })
    })
});

//重置添加和编辑窗口中输入框的内容
function resetUserFrom() {
    $("#savemsg").attr("disabled",true)
    $("#addmsg").html("")
    var $vals=$("#addUer input");
    $vals.each(function(){
        $(this).attr("style","").val("")
    });

}

function changeVal() {
    $("#addmsg").html("")
}

//获取当前项目的名称
    function getProjectPath() {
        //获取主机地址之后的目录，如： cloudlibrary/admin/books.jsp
        var pathName = window.document.location.pathname;
        //获取带"/"的项目名，如：/cloudlibrary
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        return projectName;
    }



