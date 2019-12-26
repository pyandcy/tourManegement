package cn.tedu.ttms.product.service;

import cn.tedu.ttms.product.entity.Team;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface TeamService {
    Map<String,Object> findPageObjects(@Param("name") String name,
                                       @Param("pageCurrent") Integer pageCurrent);
    void validById(@Param("valid") Integer valid,@Param("ids") String ids);
    List<Map<String,Object>> findPrjIdAndNames();

    void SaveObject(Team entity);

    Team findObjectById(Integer id);

    void doUpdateObject(Team team);
}
