package product.service;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.tedu.ttms.product.entity.ProductType;
import cn.tedu.ttms.product.entity.Team;
import cn.tedu.ttms.product.service.ProductTypeService;
import cn.tedu.ttms.product.service.TeamService;
import cn.tedu.ttms.product.service.impl.TeamServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.ttms.common.web.PageObject;
import cn.tedu.ttms.product.entity.Project;
import cn.tedu.ttms.product.service.ProjectService;
public class TestProjectService {
	ClassPathXmlApplicationContext ctx;
	@Before
	public void init(){
		ctx=new ClassPathXmlApplicationContext(
		"spring-mvc.xml","spring-mybatis.xml");
	}
	@Test
	public void testFindObjects(){
		//1.获得ProjectService对象
		ProjectService projectService=
		ctx.getBean("projectServiceImpl",
				ProjectService.class);
		//2.执行ProjectService对象的findObjects方法
		List<Project> list=
				projectService.findObjects();
		//3.验证结果是否正确
//		Assert.assertNotEquals(0, list.size());
		//4.输出执行结果
		System.out.println(list);
	}
	@Test
	public void testFindPageObjects(){
		//1.获得ProjectService对象
		ProjectService projectService=
		ctx.getBean("projectServiceImpl",
				ProjectService.class);
		//2.分页查询数据
		Map<String,Object> map=
		projectService.findPageObjects("环球",1,1);
		List<Project> list=(List<Project>)
				map.get("list");
		PageObject pageObject=(PageObject)
				map.get("pageObject");
		//3.对获得数据进行测试
//		Assert.assertEquals(1, list.size());
//		Assert.assertEquals(1,
//				pageObject.getPageCount());
	    System.out.println("list="+list);
	}
	@Test
	public void testValidById(){
		//1.获得ProjectService对象
		ProjectService ps=
				ctx.getBean("projectServiceImpl",
						ProjectService.class);
		Integer valid=0;
		String ids="1,3,4";
		ps.validById(valid,ids);
	}
	@Test
	public void testSaveObject(){
		//1.获得ProjectService对象
		ProjectService ps=
				ctx.getBean("projectServiceImpl",
						ProjectService.class);
		Project project = new Project();
		project.setId(8);
		project.setName("台湾游");
		project.setCode("tt-20190909-cn-tw-009");
		project.setBeginDate(new Date());
		project.setEndDate(new Date());
		project.setValid(1);
		ps.saveObject(project);
//		Project project1 = new Project();
//		ps.saveObject(project1);
	}
	@Test
	public void testUpdateObject() {
		//1.获得ProjectService对象
		ProjectService ps =
				ctx.getBean("projectServiceImpl",
						ProjectService.class);
		Project project = new Project();
		project.setId(100);
		project.setName("南昌游");
		project.setCode("tt-20190909-cn-nc-009");
		project.setBeginDate(new Date());
		project.setEndDate(new Date());
		project.setValid(1);
		ps.updateObject(project);
	}
	@Test
	public void testFindObjectById() {
		//1.获得ProjectService对象
		ProjectService ps =
				ctx.getBean("projectServiceImpl",
						ProjectService.class);
		System.out.println(ps.findObjectById(3));
	}
	@Test
	public void testFindTeamPageObjects(){
		//1.获得TeamService对象
		TeamService teamService = ctx.getBean("teamServiceImpl", TeamService.class);
		//2.分页查询数据
		Map<String, Object> map = teamService.findPageObjects("环球", 1);
		System.out.println(map);
//		Map<String, Object> map = teamService.findPageObjects("", 1);
//		Object list = map.get("list");
//		PageObject pageObject = (PageObject) map.get("pageObject");
//		System.out.println("pageObject="+pageObject);
//		List<Project> list=(List<Project>)
//				map.get("list");
//		PageObject pageObject=(PageObject)
//				map.get("pageObject");
		//3.对获得数据进行测试
//		System.out.println("list="+list);
	}
	@Test
	public void testTeamValid() {
		//1.获得TeamService对象
		TeamService teamService = ctx.getBean("teamServiceImpl", TeamService.class);
		int valid=0;
		String ids="4,5,7";
		teamService.validById(valid,ids);
	}
	@Test
	public void testTeamFindPrjIdAndNames() {
		//1.获得TeamService对象
		TeamService teamService = ctx.getBean("teamServiceImpl", TeamService.class);
		List<Map<String, Object>> list = teamService.findPrjIdAndNames();
		System.out.println(list);
	}
	@Test
	public void testTeamSaveObject(){
		TeamService ts = ctx.getBean("teamServiceImpl",
				TeamService.class);
		Team team = new Team();
		team.setId(666);
		team.setCreatedTime(new Date());
		team.setModifiedTime(new Date());
		team.setName("台湾日月潭游");
		team.setNote("日月潭风景秀丽");
		team.setValid(0);
		team.setProjectId(8);
		ts.SaveObject(team);
	}
	@Test
	public void testProductTypeObject() {
		ProductTypeService pts = ctx.getBean("productTypeServiceImpl",
				ProductTypeService.class);
		List<Map<String, Object>> list = pts.findObjects();
		System.out.println(list);
	}
	@Test
	public void testProductTypeDeleteObject() {
		ProductTypeService pts = ctx.getBean("productTypeServiceImpl",
				ProductTypeService.class);
		pts.deleteObjetcs(5);
		System.out.println("删除之后!");
	}
	@Test
	public void testProductTypeFindZtree() {
		ProductTypeService pts = ctx.getBean("productTypeServiceImpl",
				ProductTypeService.class);
		System.out.println(pts.findZtreeObject());
	}
	@Test
	public void testProductTypeSave() {
		ProductTypeService pts = ctx.getBean("productTypeServiceImpl",
				ProductTypeService.class);
		ProductType productType = new ProductType();
		productType.setName("江西游");
		productType.setSort(5);
		productType.setParentId(2);
		productType.setNote("江西风景好");
		pts.saveObject(productType);
		System.out.println("insert ok");
	}
	@Test
	public void testProductTypeUpdate() {
		ProductTypeService pts = ctx.getBean("productTypeServiceImpl",
				ProductTypeService.class);
		ProductType productType = new ProductType();
		productType.setName("九江游");
		productType.setSort(7);
		productType.setParentId(8);
		productType.setNote("九江也有长江大桥");
		productType.setId(11);
		pts.updateObject(productType);
		System.out.println("update ok");
	}
	@After
	public void destory(){
		ctx.close();
	}
}
