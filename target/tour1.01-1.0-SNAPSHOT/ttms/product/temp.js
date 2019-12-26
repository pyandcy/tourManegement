//页面加载完成执行
$(document).ready(function(){
    //初始化所属项目的select列表
    doGetProjectIdAndNames();
    //在模态框的对应按钮上注册事件
    $("#modal-dialog")
        .on("click",".ok",doSaveObject);
    //在模态框隐藏时解除事件的注册
    $("#modal-dialog")
    //hidden.bs.modal为固定写法表示模态框隐藏事件
        .on("hidden.bs.modal",function(){
            //.ok上移除click事件
            $(this).off("click",".ok");
            $(this).removeData("id");
        });
    //判定模态框上是否绑定了id值
    //var id=$("#modal-dialog").data("id");
    //if(id)doFindObjectById();
});
function doFindObjectById(){
    var url="team/doFindObjectById.do";
    var id=$("#modal-dialog").data("id");
    var params={"id":id};
    $.post(url,params,function(result){
        if(result.state==1){
            //初始化页面数据
            doInitEditFormData(result.data);
        }else{
            alert(result.message);
        }
    })
}
function doInitEditFormData(result){
    $("#nameId").val(result.name);
    $("#noteId").html(result.note);
    $('#editFormId input[name="valid"]')
        .each(function(){
            if($(this).val()==result.valid){
                $(this).prop("checked",true)
            }
        });
    $("#projectId").val(result.projectId);
    /*$('#projectId option').each(function(){
        console.log("$(option).val()="
                +$(this).val()+"/"+result.projectId)
        if($(this).val()==result.projectId){
            $(this).prop("selected",true)
        }
    });*/
}

function doSaveObject(){
    //1.验证表单数据(非空验证)
    if(!$("#editFormId").valid())return;
    //2.获得表单数据
    var params=getEditFormData();
    var id=$("#modal-dialog").data("id");
    if(id)params.id=id;//假如是修改要追加id
    //3.提交异步请求,将数据写入到服务端
    var saveUrl="team/doSaveObject.do";
    var updateUrl="team/doUpdateObject.do";
    var url=id?updateUrl:saveUrl;
    $.post(url,params,function(result){
        if(result.state==1){
            $("#modal-dialog").modal("hide");
            doGetObjects();
        }else{alert(result.message);}
    });
}
/*获得表单数据*/
function getEditFormData(){
    var params={
        "name":$("#nameId").val(),
        "projectId":$("#projectId").val(),
        "valid":$('input[name="valid"]:checked').val(),
        "note":$('#noteId').val()
    };return params;
}
/*获得项目的id和名称*/
function doGetProjectIdAndNames(){
    var url="team/doFindPrjIdAndNames.do";
    $.getJSON(url,function(result){
        if(result.state==1){
            doInitProjectSelect(result.data);
            //修改时,等select列表页面初始化完成要根据
            //id初始化其它数据
            var id=$("#modal-dialog").data("id");
            if(id)doFindObjectById();
        }else{
            alert(result.message);
        }
    });
}
/*初始化所属项目的select下拉框*/
function doInitProjectSelect(list){
    var select=$("#projectId");
    select.append(
        "<option>==请选择==</option>")
    var option=
        "<option value=[id]>[name]</option>"
    for(var i in list){
        select.append(
            option.replace("[id]",list[i].id)
                .replace("[name]",list[i].name));
    }
}





type_list.js

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
$(document).ready(function(){
    $("#formHead")
        .on("click",".btn-delete",doDeleteObject)
        .on("click",".btn-add,.btn-update",doLoadEditPage);
    doGetObjects();
});
/**加载编辑页面到制定位置*/
function doLoadEditPage(){
    var title;
    if($(this).hasClass("btn-add")){
        title="添加分类信息";
    }
    if($(this).hasClass("btn-update")){
        title="修改分类信息"
        var id=getSelectedId();//获得选中的记录id值
        if(id==-1){
            alert("请先选择");return;
        }
        $("#container").data("id",id);
        console.log("id="+id);
    }
    var url="type/editUI.do"
    $(".content").load(url,function(){
        $(".panel-heading").html(title)
    })
}
/**获得选中的id值*/
function getSelectedId(){
    //1.1 获得选中的对象,默认返回值为一个对象数组
    var selections=$("#typeTable")
        .bootstrapTreeTable("getSelections");
    if(selections.length==0){
        return -1;//表示没选择任何对象
    }
    //1.2获得选中数组中下标为0的元素id的值
    return selections[0].id;
}
/**执行删除操作*/
function doDeleteObject(){
    //debugger
    //1.获得选中的id
    var typeId=getSelectedId();
    if(typeId==-1){
        alert("请先选择");
        return;
    }
    console.log("typeId="+typeId);
    //2.发送异步请求,根据id执行删除操作
    //2.1定义url
    var url="type/doDeleteObject.do";
    //2.2定义参数值("id"要与controller方法中参数的名字相同)
    var params={"id":typeId};
    //2.3执行异步删除操作
    $.post(url,params,function(result){
        if(result.state==1){
            doGetObjects();
            alert("删除ok");
        }else{
            alert(result.message);
        }
    });
}
function doGetObjects(){
    var tableId="typeTable";//对象type_list.jsp中的table id
    var url="type/doFindGridTreeObjects.do";
    var table=new TreeTable(tableId,url,columns);
    table.setIdField("id");//设置选中记录的返回id()
    table.setCodeField("id");//设置级联关系的id
    table.setParentCodeField("parentId");//设置级联关系中的parentId
    table.setExpandColumn(2);//设置默认展开列
    table.setExpandAll(false);//设置默认不展开
    table.init();//初始化对象树(底层会发起异步请求)
}






type_edit.js

$(document).ready(function(){
    $("#editTypeForm").on("click",".load-product-type",doLoadZTreeNodes)
    $("#btn-save").click(doSaveOrUpdate)
    //点击返回按钮执行doBack函数
    $("#btn-return").click(doBack);
    $("#typeLayer")
        .on("click",".btn-cancle",doHideZtree)
        .on("click",".btn-confirm",doSetSelectedNode);

    //获得id值(等修改或返回以后一定要将id的值移除)
    var id=$("#container").data("id");
    //假如id有值说明是修改,则根据id执行查找
    if(id)doFindObjectById(id);
});
/*点击回退或修改结束执行此方法*/
function doBack(){
    //清空编辑页面数据,解除数据绑定?
    doClearData();
    //加载列表页面,重新显示查询结果
    var listUrl="type/listUI.do?t="+Math.random(1000);
    $("#container").load(listUrl);
}
/**根据id执行查询获得记录信息*/
function doFindObjectById(id){
    var url="type/doFindObjectById.do";
    var params={"id":id};
    $.post(url,params,function(result){
        if(result.state==1){
            doSetEditFormData(result.data);//初始化表单数据
        }else{
            alert(result.message);
        }
    });
}
/*修改时初始化表单数据*/
function doSetEditFormData(obj){
    $("#typeNameId").val(obj.name);
    $("#parentNameId").val(obj.parentName);
    $("#editTypeForm").data("parentId",obj.parentId);
    $("#typeSortId").val(obj.sort);
    $("#typeNoteId").html(obj.note);
}
function doSaveOrUpdate(){
    //1.获得页面表单中的数据
    //1.1获得用户输入的数据
    var params=getEditFormData();
    //1.2获得页面绑定的id值(假如有值说明是修改)
    var id=$("#container").data("id");
    if(id)params.id=id;//在json对象中添加一个新的key/value
    //2.发送异步请求提交数据
    var saveUrl="type/doSaveObject.do";
    var updateUrl="type/doUpdateObject.do";
    var url=id?updateUrl:saveUrl;
    console.log(JSON.stringify(params));
    $.post(url,params,function(result){
        if(result.state==1){
            doBack();
        }else{
            alert(result.message);
        }
    });
}
/*清空相关数据*/
function doClearData(){
    //1清空所有类选择器dynamicClear标识的对象的内容
    $(".dynamicClear").val('');//技巧应用
    //2.移除绑定的数据(因为添加和修改要共用一个页面)
    $("#container").removeData("id");
    $("#editTypeForm").removeData("parentId");
}
function getEditFormData(){
    var params={
        "name":$("#typeNameId").val(),
        "parentId":$("#editTypeForm").data("parentId"),
        "sort":$("#typeSortId").val(),
        "note":$("#typeNoteId").val()
    };
    return params;
}
/*设置选中的节点*/
function doSetSelectedNode(){
    //1.获得选中的的节点对象
    var selectedNodes=//返回值是一个数组
        //getSelectedNodes是zTree中的一个函数
        zTree.getSelectedNodes();
    //2.获得具体的节点(node)对象
    var node=selectedNodes[0];
    //3.通过node节点数据更新页面内容
    $("#parentNameId").val(node.name);
    $("#editTypeForm").data("parentId",node.id);
    //4.隐藏zTree
    doHideZtree();
}
/**隐藏Ztree*/
function doHideZtree(){
    $("#typeLayer").css("display","none");
}
/**显示Ztree以及树上的节点信息*/
function doLoadZTreeNodes(){
//1.显示Ztree(在type_edit.jsp页面上默认是隐藏的)
    $("#typeLayer").css("display","block");
//2.发送异步请求加载分类信息,更新Ztree节点内容
    var url="type/doFindZtreeObjects.do"
    $.getJSON(url,function(result){
        console.log("result="+JSON.stringify(result))
        if(result.state==1){
            //访问zTree方法通过数据初始化节点信息
            zTree=$.fn.zTree.init($("#typeTree"),setting,result.data);
        }else{
            alert(result.message);
        }
    });
}

attchament Controller
import cn.tedu.ttms.common.exception.ServiceException;
import com.sun.xml.internal.ws.api.message.Attachment;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService {
@Resource
    private AttachmentDao attachementDao;
@Override
    public Attachment findObjectById(Integer id) {
    //1.判定参数有效性
    if(id==null)
    throw new ServiceException("id的值不能为空");
    //2.执行查询操作
    Attachment a=attachementDao.findObjectById(id);
    //3.对查询结果进行业务判定
    if(a==null)
    throw new ServiceException("没找到对应记录");
    return a;
}
/**获得所有附件信息*/
@Override
public List<Attachment> findObjects() {
    return attachementDao.findObjects();
}

    @Override
public void uploadObject(String title,
    MultipartFile mFile) {
    System.out.println("title.isEmpty()="+title.isEmpty());
    //1.实现文件上传
    //1.1验证参数有效性
    if(title==null||title.trim().length()==0)
        throw new ServiceException("上传标题不能为空");
    if(mFile==null)
        throw new ServiceException("需要选择上传文件");
    if(mFile.isEmpty())
        throw new ServiceException("上传文件不能为空");
    //1.2判定文件是否已经上传过(根据摘要信息)?

    //获得文件中的字节(应在文件上传之前)
    String fileDisgest=null;
    byte buf[]=null;
    try{
        buf=mFile.getBytes();
        fileDisgest=
            //对文件内容进行md5加密并转换为16进制显示
            DigestUtils.md5DigestAsHex(buf);
        //a)对文件内容进行md5加密以后形成字符串称之为文件的摘要信息
        //b)文件内容一样,构建的摘要字符串也是一样的
        //c)文件内容不同,摘要字符串也是不同的.
        System.out.println("fileDisgest="+fileDisgest);
    }catch(Exception e){
        e.printStackTrace();
        throw new ServiceException("文件摘要创建失败");
    }
    //根据摘要字符串查询并统计记录
    int count=
        attachementDao.getRowCountByDigest(
            fileDisgest);
    if(count>0)//假如统计结果大于0说明文件已经上传过了
        throw new ServiceException("文件已上传,不能再次上传");
    //1.3实现文件上传
    //1.3.1 构建文件上传路径(d:/uploads/2017/08/15/xxxxx.png)
    SimpleDateFormat sdf=
        new SimpleDateFormat("yyyy/MM/dd");
    String dateDir=sdf.format(new Date());
    String baseDir="d:/uploads/";
    File uploadDir=new File(baseDir+dateDir);
    if(!uploadDir.exists()){
        uploadDir.mkdirs();
    }
    //1.3.2 构建新的文件名(相同目录下不允许出现重复的名字)
    String srcFileName=
        mFile.getOriginalFilename();
    String destfileName=
        UUID.randomUUID().toString()+"."
        +FilenameUtils.getExtension(srcFileName);
    //1.3.3创建目标文件对象
    File dest=new File(uploadDir,
        destfileName);
    //1.3.4 实现文件上传
    try{
        //实现文件上传(本质上就是文件的复制)
        mFile.transferTo(dest);
    }catch(IOException e){
        e.printStackTrace();
        throw new ServiceException("文件上传失败");
    }
    //2.将文件相关信息写入数据库
    Attachment a=new Attachment();
    a.setTitle(title);
    a.setFileName(mFile.getOriginalFilename());
    a.setContentType(mFile.getContentType());
    a.setFilePath(dest.getAbsolutePath());
    a.setFileDisgest(fileDisgest);
    a.setAthType(1);//暂且没用到
    a.setBelongId(1);//暂且没用到
    int rows=attachementDao.insertObject(a);
    if(rows==-1)
        throw new ServiceException("insert error");
}

}



attachmentController
 @RequestMapping("doDownload")
    @ResponseBody
public byte[] doDownload(Integer id,HttpServletResponse response)
throws IOException{
    //1.根据id执行查找操作
    Attachment a=attachmentService.findObjectById(id);
    //2.设置下载内容类型以及响应头(固定格式)
    response.setContentType("appliction/octet-stream");
    String fileName=URLEncoder.encode(a.getFileName(),"utf-8");
    response.setHeader("Content-disposition",
        "attachment;filename="+fileName);
    //3.获得指定文件的路径对象(java.nio.Path)
    Path path=Paths.get(a.getFilePath());
    //4.读取path路径对应的文件,并返回字节数组
    return Files.readAllBytes(path);
}






















