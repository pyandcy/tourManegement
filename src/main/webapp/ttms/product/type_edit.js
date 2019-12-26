var zTree;
var setting = {
		data : {   
			simpleData : {
				enable : true,
				idKey : "id",  //节点数据中保存唯一标识的属性名称
				pIdKey : "parentId",  //节点数据中保存其父节点唯一标识的属性名称
				rootPId : null  //根节点id
			}
		}
}
$(document).ready(function () {
	$("#btn-return").click(doBack);
	$(".load-product-type").click(doLoadZTreeNodes);
	$("#typeLayer").on("click",".btn-cancle",doHideZtree)
		.on("click",".btn-confirm",doGetSelectedNodes)
	$("#editTypeForm").on("click","#btn-save",doSaveOrUpdate)
});
function doSaveOrUpdate() {
	var params=doGetForm();
	var id=$("#container").data("id");
	if(id) params.id=id;
	console.log(params);
	var saveUrl="type/doSaveObject.do";
	var updateUrl="type/doUpdateObject.do";
	var url=id?updateUrl:saveUrl;
	$.post(url,params,function (result) {
		if (result.state==1){
			doBack();
		}else {
            alert(result.message);
		}
    });
}
function doGetForm() {
	var params={
        name:$("#typeNameId").val(),
		parentId:$("#editTypeForm").data("parentId"),
		sort:$("#typeSortId").val(),
		note:$("#typeNoteId").val()
	}
	return params;
}
function doGetSelectedNodes() {
	// 1.得到选择的节点
	// 2.将所选节点数据显示和绑定到表单
	// 3.隐藏菜单
	var nodes = zTree.getSelectedNodes();
	// console.log(nodes);
	$("#parentNameId").val(nodes[0].name);
	$("#editTypeForm").data("parentId",nodes[0].id);
    doHideZtree();
}
function doHideZtree() {
    $("#typeLayer").css("display","none");
}
function doLoadZTreeNodes() {
	$("#typeLayer").css("display","block");
	var url="type/doFindZtreeObject.do";
	$.getJSON(url,function (result) {
        // console.log("进入了doLoadZTreeNodes的function (result)");
        // console.log("result="+JSON.stringify(result));
		if (result.state==1){
			zTree=$.fn.zTree.init($("#typeTree"),setting,result.data);
		} else{
			alert(result.message);
		}
    });
}
function doBack() {
	// console.log("进入了doBack");
    doClearData();
	var url="type/listUI.do?t="+Math.random(1000);
	$(".content").load(url);
}
function doClearData() {
    //1清空所有类选择器dynamicClear标识的对象的内容
    $(".dynamicClear").val('');//技巧应用
    //2.移除绑定的数据(因为添加和修改要共用一个页面)
    $("#container").removeData("id");
    $("#editTypeForm").removeData("parentId");
}
