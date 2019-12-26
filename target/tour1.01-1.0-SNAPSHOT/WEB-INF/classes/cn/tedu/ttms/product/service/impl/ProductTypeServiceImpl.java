package cn.tedu.ttms.product.service.impl;

import cn.tedu.ttms.common.exception.ServiceException;
import cn.tedu.ttms.product.dao.ProductTypeDao;
import cn.tedu.ttms.product.entity.ProductType;
import cn.tedu.ttms.product.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.rmi.server.ServerCloneException;
import java.util.List;
import java.util.Map;
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Resource
    private ProductTypeDao productTypeDao;
    @Override
    public List<Map<String, Object>> findObjects() {
        return productTypeDao.findObjects();
    }

    @Override
    public void deleteObjetcs(Integer id) {
        if (id==null)
            throw new ServiceException("删除对象不能为空！");
//        int i = productTypeDao.hasChilds(id);
        if (productTypeDao.hasChilds(id)>0)
            throw new ServiceException("有子产品，请先删除子产品！");
        int rows = productTypeDao.deleteObjects(id);
        if (rows!=1)
            throw new ServiceException("产品删除失败！");
    }

    @Override
    public List<ProductType> findZtreeObject() {
        return productTypeDao.findZtreeObject();
    }

    @Override
    public void saveObject(ProductType productType) {
        if (productType.getName()==null||productType.getNote()==null||productType.getSort()==null||productType.getParentId()==null)
          throw new ServiceException("保存对象不能为空");
        int rows=productTypeDao.insertObject(productType);
        if (rows<=0)
            throw new ServiceException("insert error");
    }

    @Override
    public void updateObject(ProductType productType) {
        if (productType==null)
            throw new ServiceException("更新对象不能为空！");
        int rows = productTypeDao.updateObject(productType);
        if (rows<=0)
            throw new ServiceException("update error");
    }
}
