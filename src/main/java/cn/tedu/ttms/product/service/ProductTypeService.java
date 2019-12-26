package cn.tedu.ttms.product.service;

import cn.tedu.ttms.product.entity.ProductType;

import java.util.List;
import java.util.Map;

public interface ProductTypeService {
    List<Map<String,Object>> findObjects();
    void deleteObjetcs(Integer id);
    List<ProductType> findZtreeObject();
    void saveObject(ProductType productType);
    void updateObject(ProductType productType);
}
