package cn.exrick.xboot.modules.base.controller.activiti;

import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.base.entity.activiti.ActReModelEntity;
import cn.exrick.xboot.modules.base.service.activiti.IActReModelService;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(description = "工作流接口")
@RequestMapping("/xboot/activiti")
@Transactional
public class ActivitiController {

    @Autowired
    private IActReModelService actReModelService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加模型")
    public Result addModel(@ModelAttribute ActReModelEntity actReModel){
        String modelId = actReModelService.saveReModel(actReModel);
        return new ResultUtil<String>().setData(modelId);
    }

    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    @ApiOperation(value = "获取全部模型数据")
    public Result<List<Model>> getByCondition(){
        // 模型配置方法
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<Model> modelList = repositoryService.createModelQuery().list();
        return new ResultUtil<List<Model>>().setData(modelList);
    }

    @RequestMapping(value = "/deleteModel/{id}",method = RequestMethod.POST)
    public Result<Object> deleteModel(@PathVariable String id){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteModel(id);
        return new ResultUtil<Object>().setData(id);
    }
}
