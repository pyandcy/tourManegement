$(document).ready(function(){
    var id=$("#modal-dialog").data("idKey");
    if (id) doFindObjectById(id);
    $("#modal-dialog")
    //模态框的.ok按钮上注册点击事件
        .on("click",".ok",doSaveOrUpdate);
    //在模态框隐藏后移除ok上注册的事件,防止数据多次提交.
    $("#modal-dialog")
        .on("hidden.bs.modal",function(){
            console.log("==hidden.bs.modal==");
            $("#modal-dialog").off("click",".ok");
            // 模态框隐藏时移除绑定的idKey
            $("#modal-dialog").removeData("idKey");
        });
})
// 根据id查询数据
function doFindObjectById(id){
    var url="project/doFindObjectById.do";
    var params={"id":id};
    $.getJSON(url,params,function (result) {
        // console.log(result.data);
        if (result.state==1){
            doInitFormData(result.data);
        } else{
            alert(result.message);
        }
    })
}
// 根据id查询到后显示在模态框中
function doInitFormData(data) {
    $("#nameId").val(data.name);
    $("#codeId").val(data.code);
    $("#beginDateId").val(data.beginDate);
    $("#endDateId").val(data.endDate);
    $("#noteId").html(data.note);
    $("#beginDateId").val(data.beginDate);
    $("#editFormId input[name='valid']").each(function () {
        if ($(this).val()==data.valid){
            $(this).prop("checked",true);
        }
    })
}
/*添加或修改数据*/
function doSaveOrUpdate(){
    //0.验证表单数据是否为空
    if(!$("#editFormId").valid())return;
    //1.获得表单数据
    var params=getEditFormData();
    console.log(params);
    //2.异步提交表单数据
    var insertUrl="project/doSaveObject.do";
    var updateUrl="project/doUpdateObject.do";
    var id=$("#modal-dialog").data("idKey");
    var url=id?updateUrl:insertUrl;
    if (id) params.id=id;
    $.post(url,params,function(result){
        if(result.state==1){
            //隐藏模态框
            $("#modal-dialog").modal("hide");
            //显示相关信息
            alert(result.message);
            //重新查询数据
            doGetObjects();
        }else{
            alert(result.message);
        }
    });
}
/*获取表单数据*/
function getEditFormData(){
    var params={
        name:$("#nameId").val(),
        code:$("#codeId").val(),
        beginDate:$("#beginDateId").val(),
        endDate:$("#endDateId").val(),
        valid:$("input[type='radio']:checked").val(),
        note:$("#noteId").val(),
    }//JSON 对象
    return params;
}