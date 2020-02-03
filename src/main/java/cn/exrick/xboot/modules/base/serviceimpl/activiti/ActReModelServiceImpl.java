package cn.exrick.xboot.modules.base.serviceimpl.activiti;

import cn.exrick.xboot.common.constant.ActivitiConstant;
import cn.exrick.xboot.common.exception.YYException;
import cn.exrick.xboot.modules.base.entity.activiti.ActReModelEntity;
import cn.exrick.xboot.modules.base.service.activiti.IActReModelService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 模型接口实现
 */
@Slf4j
@Service
@Transactional
public class ActReModelServiceImpl implements IActReModelService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ActReModelEntity findByModelName(String name) {

        return null;
    }

    @Override
    public String saveReModel(ActReModelEntity actReModelEntity) {
        String description = actReModelEntity.getDescription();
        String key = actReModelEntity.getKey();
        String name = actReModelEntity.getName();
        //版本号
        int revision = 1;

        // 元数据
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
        // 模型
        Model model = repositoryService.newModel();
        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);
        String id = model.getId();

        // 保存模型编辑器源文件
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        try {
            repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            //throw new XbootException("保存模型编辑器源文件失败",e);
        }
        return id;
    }

    @Override
    public List<ActReModelEntity> findByModelList() {
        return null;
    }

    @Override
    public void deploy(String id) {
        Model modelData = repositoryService.getModel(id);
        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
        JsonNode editor = null;
        try {
            editor = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
        } catch (IOException e) {
            throw new YYException("部署解析失败", e);
        }
        BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editor);
        BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
        byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

        String processName = modelData.getName();
        if (!StringUtils.endsWith(processName, ActivitiConstant.BPMN20)) {
            processName += ActivitiConstant.BPMN20;
        }
        ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addInputStream(processName, in)
                .deploy();
    }
}
