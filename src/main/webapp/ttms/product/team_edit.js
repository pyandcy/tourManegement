$(document).ready(function () {
    doGetProjectIdAndNames();
    $("#modal-dialog").on("click",".ok",doSaveOrUpdateObject);
    $("#modal-dialog")
        .on("hidden.bs.modal",function() {
            console.log("==hidden.bs.modal==");
            $("#modal-dialog").off("click", ".ok");
        });
    var id=$("#modal-dialog").data("idKey");
    if (id) doFindObjectById(id);
});
function doFindObjectById(id) {
    var url="team/doFindObjectById.do";
    var params={
        id:id
    };
    $.getJSON(url,params,function (result) {
        if (result.state==1){
            doInitFormData(result.data);
        } else{
            alert(result.message);
        }
    });
}
function doInitFormData(data){
    $("#nameId").val(data.name);
    $("#noteId").val(data.note);
    $("#editFormId input[name='valid']").each(function () {
        if ($(this).val()==data.valid){
            $(this).prop("checked",true);
        }
    });
    $("#projectId").val(data.projectId);
}
function doSaveOrUpdateObject() {
    if (!$("#editFormId").valid()) return;
    var params=getEditFormData();
    var insertUrl="team/doSaveObject.do";
    var updateUrl="team/doUpdateObject.do";
    var id=$("#modal-dialog").data("idKey");
    var url=id?updateUrl:insertUrl;
    if (id) params.id=id;
    console.log(params);
    $.post(url,params,function (result) {
        if (result.state==1){
            $("#modal-dialog").modal("hide");
            alert(result.message);
            doGetObjects();
        } else{
            alert(result.message);
        }
    });
}
function getEditFormData(){
    // console.log("进入了EditForm");
    var params={
        name:$("#nameId").val(),
        projectId:$("#projectId").val(),
        valid:$('input[name="valid"]:checked').val(),
        note:$("#noteId").val(),
    }
    return params;
}
function doGetProjectIdAndNames() {
       var url="team/doFindPrjIdAndNames.do";
       $.getJSON(url,function (result) {
           if (result.state==1){
               doInitProjectSelect(result.data);
           } else{
               alert(result.message);
           }
       });
}
function doInitProjectSelect(list) {
    var select=$("#projectId");
    select.append("<option>==请选择==</option>");
    var option="<option value=[id]>[name]</option>";
    for (var i in list){
        select.append(
            option.replace("[id]",list[i].id)
                .replace("[name]",list[i].name)
        )
    }
}

