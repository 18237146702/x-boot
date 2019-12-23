package cn.exrick.xboot.modules.base.service.activiti;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.base.entity.activiti.ApplyLeaveEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 司马缸砸缸了
 * @since 2019-08-28
 */
public interface IApplyLeaveService extends IService<ApplyLeaveEntity> {

    /**
     * 我的申请
     *
     * @param params
     * @return
     */
    //PageUtils queryMyApplyPage(Map<String, Object> params);

    /**
     * 提交申请
     *
     * @param applyLeaveEntity
     */
    void saveLeave(ApplyLeaveEntity applyLeaveEntity);

    /**
     * 申请列表
     * @param applyLeaveEntity
     * @return
     */
    List<ApplyLeaveEntity> getAll(ApplyLeaveEntity applyLeaveEntity);
    /**
     * 提交申请
     *
     * @param leaveApplyDTO
     * @return
     */
//    Result apply(LeaveApplyDTO leaveApplyDTO);
//
//    /**
//     * 待办任务
//     *
//     * @param params
//     * @return
//     */
//    PageUtils queryTodoPage(Map<String, Object> params);
//
//    /**
//     * 已办任务
//     *
//     * @param params
//     * @return
//     */
//    PageUtils queryDonePage(Map<String, Object> params);
//
//    /**
//     * 运行中的流程
//     *
//     * @param params
//     * @return
//     */
//    PageUtils queryRunningPage(Map<String, Object> params);
//
//    /**
//     * 结束的流程
//     *
//     * @param params
//     * @return
//     */
//    PageUtils queryFinishPage(Map<String, Object> params);
//
//    /**
//     * 历史活动节点
//     *
//     * @param taskId
//     * @return
//     */
//    List<HistoricActivityInstance> historyActivity(String taskId);
//
//    /**
//     * 历史任务
//     *
//     * @param id
//     * @return
//     */
//    List<ActHiTaskinst> historyTaskList(String id);
//
//    /**
//     * 完成任务
//     *
//     * @param leaveOperate
//     * @return
//     */
//    Result complete(LeaveOperateDTO leaveOperate);
}
