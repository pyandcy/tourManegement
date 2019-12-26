package cn.tedu.ttms.product.controller;

import cn.tedu.ttms.common.web.JsonResult;
import cn.tedu.ttms.product.entity.Team;
import cn.tedu.ttms.product.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RequestMapping("/team/")
@Controller
public class TeamController {
    private Logger log=
            Logger.getLogger(TeamController.class.getName());
    @Autowired
    private TeamService teamService;
    @RequestMapping("listUI")
    public String listUI(){
        return "product/team_list";
    }
    @RequestMapping("editUI")
    public String editUI(){
        return "product/team_edit";
    }
    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(String name,Integer pageCurrent){
        log.info("teamProjectName="+name);
        log.info("teamPageCurrent="+pageCurrent);
        return new JsonResult(teamService.findPageObjects(name,pageCurrent));
    }
    @RequestMapping("doValidById")
    @ResponseBody
    public JsonResult doValidById(Integer valid,String ids){
       teamService.validById(valid,ids);
       return new JsonResult(valid==1?"启用ok":"禁用ok");
    }
    @RequestMapping("doFindPrjIdAndNames")
    @ResponseBody
    public JsonResult doFindPrjIdAndNames(){
        List<Map<String, Object>> list = teamService.findPrjIdAndNames();
        return new JsonResult(list);
    }
    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(Team entity){
        teamService.SaveObject(entity);
        return new JsonResult("insert ok");
    }
    @RequestMapping("doFindObjectById")
    @ResponseBody
    public JsonResult doFindObjectById(Integer id){
        Team team=teamService.findObjectById(id);
        return new JsonResult(team);
    }
    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(Team team){
        teamService.doUpdateObject(team);
        return new JsonResult("team update ok");
    }
}
