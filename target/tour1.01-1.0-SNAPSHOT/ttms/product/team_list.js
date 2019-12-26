$(function(){
    $("#queryFormId")
        .on("click",".btn-search",doQueryObjects)
        .on("click",".btn-invalid,.btn-valid",doValidById)
        .on("click",".btn-add,.btn-update",doLoadEditPage)
    doGetObjects();
});
function doLoadEditPage() {
    var title;
    if($(this).hasClass("btn-add")){
        title="添加团目信息";
    }
    if ($(this).hasClass("btn-update")){
        var idValue=$(this).parent().parent().data("id");
        $("#modal-dialog").data("idKey",idValue);
        title="修改项目信息,id="+idValue;
    }
    var url="team/editUI.do";
    $("#modal-dialog .modal-body")
        .load(url,function () {
            $(".modal-title").html(title);
            $("#modal-dialog").modal("show");
        })
}
function doValidById() {
    var valid;
    if ($(this).hasClass("btn-valid")) {
        valid=1;
    }
    if ($(this).hasClass("btn-invalid")) {
        valid=0;
    }
    var ids="";
    $("#tbodyId input[name='checkdId'] ").each(function () {
        if ($(this).prop("checked")) {
            if (ids =="") {
                ids+=$(this).val();
            }else{
                ids+=","+$(this).val();
            }
        }
    });
    console.log("valid="+valid);
    console.log("ids="+ids);
    if (ids==""){
        alert("请至少选择一个：");
        return;
    }
    var params={
        "valid":valid,
        "ids":ids,
    }
    var url="team/doValidById.do";
    $.post(url,params,function (result) {
        if (result.state==1){
            alert(result.message);
            doGetObjects();
        } else{
            alert(result.message);
        }
    })
}
function doQueryObjects(){
    //1.初始化当前页码
    $("#pageId").data("pageCurrent",1);
    //2.执行查询操作
    doGetObjects();
}
function doGetObjects(){
    //初始化url
    var url="team/doFindPageObjects.do?t="+Math.random();
    //初始化参数对象
    var params={name:$("#searchNameId").val()};//object
    var pageCurrent=$("#pageId").data("pageCurrent");
    if(!pageCurrent)pageCurrent=1;
    //params.pageCurrent=pageCurrent;
    params['pageCurrent']=pageCurrent;//{page-Current:1}
    //发送异步启动获取分页数据
	console.log(params);
    $.post(url,params,function(result){//JsonResult
        if(result.state==1){
            //1.tbodyId对应位置显示team信息
            setTableBodyRows(result.data.list)
            //2.设置分页信息
            setPagination(result.data.pageObject);
        }else{
            alert(result.message);
        }
    });
}
function setTableBodyRows(list){
    //1.获得tbodyId对应的对象
    var tBody=$("#tbodyId");
    tBody.empty();
    //2.迭代list集合(多个team记录信息)
    for(var i in list){
        //2.1 创建 tr 对象
        var tr=$("<tr></tr>");
        tr.data("id",list[i].id);//可选(看业务)
        //2.2 创建tds串
        var tds="<td><input type='checkbox' name='checkdId' value='"+list[i].id+"'></td>"+
            "<td>"+list[i].name+"</td>"+
            "<td>"+list[i].project.name+"</td>"+
            "<td>"+(list[i].valid?'启用':'禁用')+"</td>"+
            "<td><input type='button' class='btn btn-default btn-update' value='修改'></td>";
        //2.3将td追加到tr对象
        tr.append(tds);
        //2.4将tr追加到tBody中
        tBody.append(tr);
    }
}