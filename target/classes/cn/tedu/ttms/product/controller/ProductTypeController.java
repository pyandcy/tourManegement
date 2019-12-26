package cn.tedu.ttms.product.controller;

import cn.tedu.ttms.common.web.JsonResult;
import cn.tedu.ttms.product.entity.ProductType;
import cn.tedu.ttms.product.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.rowset.serial.SerialException;

@Controller
@RequestMapping("/type/")
public class ProductTypeController {
    @Autowired
    @Qualifier("productTypeServiceImpl")
    private ProductTypeService productTypeService;
    @RequestMapping("listUI")
    public String listUI(){
        return "product/type_list";
    }
    @RequestMapping("editUI")
    public String editUI(){
        return "product/type_edit";
    }
    @RequestMapping("doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects(){
        return new JsonResult(productTypeService.findObjects());
    }
    @RequestMapping("doDeleteObjects")
    @ResponseBody
    public JsonResult doDeleteObjects(Integer id){
        productTypeService.deleteObjetcs(id);
        return new JsonResult("删除ok");
    }
    @RequestMapping("doFindZtreeObject")
    @ResponseBody
    public JsonResult doFindZtreeObject(){
        return new JsonResult(productTypeService.findZtreeObject());
    }
    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(ProductType productType){
//        if (productType==null)
//            throw new SerialException("请添加数据！");
        productTypeService.saveObject(productType);
        return new JsonResult("insert ok");
    }
    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(ProductType productType){
        productTypeService.updateObject(productType);
        return new JsonResult("update ok");
    }
}
