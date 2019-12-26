package cn.tedu.ttms.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tedu.ttms.common.exception.ServiceException;
import cn.tedu.ttms.common.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.ttms.common.web.PageObject;
import cn.tedu.ttms.product.dao.ProjectDao;
import cn.tedu.ttms.product.entity.Project;
import cn.tedu.ttms.product.service.ProjectService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Service
public class ProjectServiceImpl 
           implements ProjectService{
	@Resource
	private ProjectDao projectDao;
	@Override
	public List<Project> findObjects() {
		List<Project> list=projectDao.findObjects();
		return list;
	}
	@Override
	public Map<String, Object> findPageObjects(
			String name,Integer valid,int pageCurrent) {
		int pageSize=5;
		int startIndex=(pageCurrent-1)*pageSize;
		//获取当前页数据
		List<Project> list=
		projectDao.findPageObjects(
				name,valid,startIndex, pageSize);
		//获取总记录数并封装分页信息
		int rowCount=
				projectDao.getRowCount(name,valid);
		PageObject pageObject=new PageObject();
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setStartIndex(startIndex);
		//将当前页数据以及分页信息封装到map
		Map<String,Object>  map=
				new HashMap<String,Object>();
		map.put("list", list);
		map.put("pageObject", pageObject);
		return map;
	}

    @Override
    public void validById(Integer valid, String ids) {
	    if (valid!=0&&valid!=1)
	        throw new ServiceException("valid的值不合法valid="+valid);
	    if (StringUtils.isEmpty(ids))
	        throw new ServiceException("ids的值不能为空!");
        String[] idArray = ids.split(",");
        int rows = projectDao.validById(valid, idArray);
        if (rows==0)
            throw new ServiceException("修改失败！");
    }

	@Override
	public void saveObject(Project entity) {
		if (entity==null)
			throw new ServiceException("保存对象不能为空！");
		int rows = projectDao.insertObject(entity);
		if (rows<=0)
			throw new ServiceException("insert error");
	}

	@Override
	public void updateObject(Project entity) {
		if (entity==null)
			throw new ServiceException("更新对象不能为空!");
		int rows = projectDao.updateObject(entity);
		if (rows<=0)
			throw new ServiceException("project update error");
	}

	@Override
	public Project findObjectById(Integer id) {
		if (id==null)
			throw new ServiceException("更新对象不能为空！");
		Project project = projectDao.findObjectById(id);
		if (project==null)
			throw new ServiceException("对象不存在！");
		return project;
	}
}




