package cn.tedu.ttms.product.dao;

import cn.tedu.ttms.product.entity.ProductType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
@Repository
public interface ProductTypeDao {
    List<Map<String,Object>> findObjects();
    int hasChilds(Integer id);
    int deleteObjects(@Param("id") Integer id);
    List<ProductType> findZtreeObject();
    int insertObject(ProductType productType);
    int updateObject(ProductType productType);
}
