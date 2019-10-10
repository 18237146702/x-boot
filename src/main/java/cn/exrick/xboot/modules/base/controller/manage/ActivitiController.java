package cn.exrick.xboot.modules.base.controller.manage;

import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.base.entity.activiti.ReModel;
import cn.exrick.xboot.modules.base.service.activiti.ReModelService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(description = "工作流接口")
@RequestMapping("/xboot/activiti")
@Transactional
public class ActivitiController {
    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加模型")
    public Result<Object> addModel(@ModelAttribute Model model){

        if(StrUtil.isBlank(model.getName()) || StrUtil.isBlank(model.getKey())){
            return new ResultUtil<Object>().setErrorMsg("缺少必需表单字段");
        }

        // 模型配置方法
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ModelQuery modelQuery = repositoryService.createModelQuery().modelName(model.getName());
        if(modelQuery.orderByModelName().desc().list() != null && modelQuery.orderByModelName().desc().list().size() > 0){
            return new ResultUtil<Object>().setErrorMsg("该模型已经存在");
        }
        // 删除缓存
        redisTemplate.delete("activiti::" + model.getName());
        ObjectMapper objectMapper = new ObjectMapper();

        Model modelData = repositoryService.newModel();
        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, model.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, model.getMetaInfo());
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName(model.getName());
        modelData.setKey(model.getKey());
        //保存模型
        repositoryService.saveModel(modelData);
        return new ResultUtil<Object>().setData(modelData);
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

    @RequestMapping(value = "/acti/modeler/{id}",method = RequestMethod.GET)
    public String addModel(@PathVariable String id){
        String str = "0";
        return "redirect:/modeler.html?modelId="+id;
    }


    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "模型在线设计")
    public void editModel(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);

            RepositoryService repositoryService = processEngine.getRepositoryService();
            repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + id);


            //获取当前请求的路径
           /* String basePath = request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort()+request.getContextPath();
            //如果request.getHeader("X-Requested-With") 返回的是"XMLHttpRequest"说明就是ajax请求，需要特殊处理 否则直接重定向就可以了
            if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
                //告诉ajax我是重定向
                response.setHeader("REDIRECT", "REDIRECT");
                //告诉ajax我重定向的路径
                response.setHeader("CONTENTPATH", basePath+"/login.html");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }else{
                response.sendRedirect(basePath + "/login.html");
            }*/


        } catch (Exception e) {
            System.out.println("在线设计模型失败："+e);
        }
    }
}
