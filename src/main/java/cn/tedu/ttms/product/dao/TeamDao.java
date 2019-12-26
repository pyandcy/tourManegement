package cn.tedu.ttms.product.dao;

import cn.tedu.ttms.product.entity.Team;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.List;
@Repository
public interface TeamDao {
//    实现团目信息的分页查询
//    name 项目名称
    List<Team> findPageObjects(@Param("name") String name,
                               @Param("startIndex") Integer startIndex,
                               @Param("pageSize") Integer pageSize);
    int getRowCount(@Param("name") String name);
    int validById(@Param("valid") Integer valid,@Param("ids") String[] ids);
    int insertObject(Team entity);

    Team findObjectById(Integer id);

    int updateObject(Team team);
}
