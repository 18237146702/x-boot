package cn.exrick.xboot.modules.base.service.activiti;

import cn.exrick.xboot.modules.base.entity.activiti.ActReModelEntity;

import java.util.List;

/**
 * 模型接口
 */
public interface IActReModelService {
    /**
     * 通过模型名字获取模型
     * @param name
     * @return
     */
    ActReModelEntity findByModelName(String name);

    /**
     * 保存模型
     * @param actReModelEntity
     * @return
     */
    String saveReModel(ActReModelEntity actReModelEntity);

    /**
     * 获取所有模型
     * @return
     */
    List<ActReModelEntity> findByModelList();

    /**
     * 部署模型发布
     * @param id
     */
    void deploy(String id);
}
