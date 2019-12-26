var columns = [
{
field : 'selectItem',
radio : true
},
{
title : '分类id',
field : 'id',
visible : false,
align : 'center',
valign : 'middle',
width : '80px'
},
{
title : '分类名称',
field : 'name',
align : 'center',
valign : 'middle',
sortable : true,
width : '180px'
},
{
title : '上级分类',
field : 'parentName',
align : 'center',
valign : 'middle',
sortable : true,
width : '180px'
},
{
title : '排序号',
field : 'sort',
align : 'center',
valign : 'middle',
sortable : true,
width : '100px'
}];
$(function () {
	doGetObjects();
	$("#formHead").on("click",".btn-delete",doDeleteObject)
		.on("click",".btn-add,.btn-update",doLoadEditPage)
});
function doLoadEditPage() {
	var url="type/editUI.do?t="+Math.random(1000);
	var title;
	if ($(this).hasClass("btn-add")) {
		title="添加分类";
	}
    if ($(this).hasClass("btn-update")) {
        var id=getSelectedId();
        $("#container").data("id",id);
        title="更新分类"+id;
    }
	// console.log("进入了doLoadEditPage");
    $(".content").load(url,function () {
		$("#pageTitle").html(title);
    });
}
function doDeleteObject() {
	var id=getSelectedId();
	var params={
		id:id
	}
	var url="type/doDeleteObjects.do";
	$.post(url,params,function (result) {
		if (result.state==1){
			alert(result.message);
			doGetObjects();
		} else{
            alert(result.message);
		}
    });
}
function getSelectedId() {
	var selections=$("#typeTable").bootstrapTreeTable("getSelections");
	console.log(selections);
	if (selections.length==0){
		alert("请先选择要删除的id");
		return;
	}
	return selections[0].id;
}
function doGetObjects() {
	var tableId="typeTable";
	var url="type/doFindObjects.do";
	var table = new TreeTable(tableId,url,columns);
	table.setExpandColumn(2);
	table.init();
}