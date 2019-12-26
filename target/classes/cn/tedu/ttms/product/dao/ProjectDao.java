package cn.tedu.ttms.product.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import cn.tedu.ttms.product.entity.Project;
import org.springframework.stereotype.Repository;

/**项目模块持久层对象*/
@Repository
public interface ProjectDao {
     /**获取所有项目信息*/
	 List<Project> findObjects();
	 /**
	  * @param name 查询时用户输入的项目名
	  * @param valid 查询时用户输入的状态
	  * @param startIndex 分页查询时的起始位置
	  * @param pageSize 每页最多显示多少条记录
	  * @return 当前页数据
	  */
	 List<Project> findPageObjects(
	   @Param("name")String name,
	   @Param("valid")Integer valid,
	   @Param("startIndex") int startIndex,
	   @Param("pageSize") int pageSize);
	 
	 /**
	  * @return 返回记录总数
	  */
	 int getRowCount(
		 @Param("name")String name,
		 @Param("valid")Integer valid);

	 /*
	 禁用或启用项目信息
	  */
	 int validById(@Param("valid") Integer valid,
                   @Param("ids") String[] ids);
	 int insertObject(Project entity);
	 int updateObject(Project entity);
	 Project findObjectById(Integer id);
	 List<Map<String,Object>> findPrjIdAndNames();
}








