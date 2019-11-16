package cn.exrick.xboot.modules.base.serviceimpl.activiti;

import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.modules.base.entity.activiti.ActReModelEntity;
import cn.exrick.xboot.modules.base.service.activiti.IActReModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
