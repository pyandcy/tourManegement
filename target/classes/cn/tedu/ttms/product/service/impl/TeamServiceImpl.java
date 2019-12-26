package cn.tedu.ttms.product.service.impl;

import cn.tedu.ttms.common.exception.ServiceException;
import cn.tedu.ttms.common.web.PageObject;
import cn.tedu.ttms.product.dao.ProjectDao;
import cn.tedu.ttms.product.dao.TeamDao;
import cn.tedu.ttms.product.entity.Team;
import cn.tedu.ttms.product.service.TeamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class TeamServiceImpl implements TeamService {
    @Resource
    private TeamDao teamDao;
    @Resource
    private ProjectDao projectDao;
    @Override
    public Map<String, Object>
    findPageObjects(String name, Integer pageCurrent) {
        int pageSize=5;
        int startIndex=(pageCurrent-1)*pageSize;
        List<Team> list = teamDao.findPageObjects(name, startIndex, pageSize);
        PageObject pageObject = new PageObject();
        int rowCount = teamDao.getRowCount(name);
        pageObject.setPageSize(pageSize);
        pageObject.setStartIndex(startIndex);
        pageObject.setRowCount(rowCount);
        pageObject.setPageCurrent(pageCurrent);
        Map<String, Object> map = new HashMap<>();
        map.put("list",list);
        map.put("pageObject",pageObject);
        return map;
    }

    @Override
    public void validById(Integer valid, String ids) {
        if (valid!=0&&valid!=1)
            throw new ServiceException("valid的值错误！");
        if (valid==null||valid.equals(""))
            throw new ServiceException("valid的值不能为空");
        String[] idArray = ids.split(",");
        int row = teamDao.validById(valid, idArray);
        if (row==0)
            throw new ServiceException("修改失败！");
    }

    @Override
    public List<Map<String, Object>> findPrjIdAndNames() {
        return projectDao.findPrjIdAndNames();
    }

    @Override
    public void SaveObject(Team entity) {
        if (entity==null)
            throw new ServiceException("保存对象不为空！");
        int rows = teamDao.insertObject(entity);
        if (rows<=0){
            throw new ServiceException("insert error");
        }
    }

    @Override
    public Team findObjectById(Integer id) {
        if (id==null)
            throw new ServiceException("更新对象不能为空！");
        Team team=teamDao.findObjectById(id);
        if (team==null)
            throw new ServiceException("对象不存在！");
        return team;
    }

    @Override
    public void doUpdateObject(Team team) {
        if (team==null)
            throw new ServiceException("更新对象不能为空!");
        int rows = teamDao.updateObject(team);
        if (rows<=0)
            throw new ServiceException("team update error");
    }
}
