package cn.exrick.xboot.modules.base.serviceimpl.activiti;

import cn.exrick.xboot.common.constant.ActivitiConstant;
import cn.exrick.xboot.common.exception.YYException;
import cn.exrick.xboot.modules.base.dao.activiti.ApplyLeaveDao;
import cn.exrick.xboot.modules.base.dao.mapper.ApplyLeaveMapper;
import cn.exrick.xboot.modules.base.entity.activiti.ApplyLeaveEntity;
import cn.exrick.xboot.modules.base.entity.activiti.dto.LeaveApplyDTO;
import cn.exrick.xboot.modules.base.service.activiti.IApplyLeaveService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 司马缸砸缸了
 * @since 2019-08-28
 */
@Slf4j
@Service
@Transactional
public class ApplyLeaveServiceImpl extends ServiceImpl<ApplyLeaveMapper,ApplyLeaveEntity> implements IApplyLeaveService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ApplyLeaveMapper applyLeaveMapper;

    @Autowired
    private HistoryService historyService;
//    @Override
//    public PageUtils queryMyApplyPage(Map<String, Object> params) {
//        String userId = (String) params.get("userId");
//        Integer status = (Integer) params.get("status");
//        String title = (String) params.get("title");
//        String leaveType = (String) params.get("leaveType");
//        String startTime = (String) params.get("startTime");
//        String endTime = (String) params.get("endTime");
//        // 时间查询
//        Date start = null;
//        Date end = null;
//        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
//            start = DateUtil.date(Long.parseLong(startTime));
//            end = DateUtil.date(Long.parseLong(endTime));
//        }
//
//        IPage<LeaveEntity> page = this.page(new Query<LeaveEntity>(params).getPage(), Wrappers.<LeaveEntity>lambdaQuery()
//                .eq(StringUtils.isNotBlank(userId), LeaveEntity::getUserId, userId)
//                .eq(status != null, LeaveEntity::getStartTime, status)
//                .eq(StringUtils.isNotBlank(leaveType), LeaveEntity::getLeaveType, leaveType)
//                .like(StringUtils.isNotBlank(title), LeaveEntity::getTitle, title)
//                .between(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime),
//                        LeaveEntity::getCreateTime, start, end)
//                .orderByDesc(LeaveEntity::getCreateTime));
//
//        // 设置当前任务
//        Optional.ofNullable(page.getRecords()).ifPresent(leaves ->
//                leaves.forEach(leave -> {
//                    // businessKey
//                    String businessKey = ActivitiConstant.LEAVE_BUSINESSKEY + leave.getId();
//                    List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKey(businessKey)
//                            .orderByTaskCreateTime()
//                            .desc()
//                            .list();
//                    /**
//                     * 转换类型ActRuTaskEntity
//                     */
//                    leave.setTaskList(ConvertUtils.convertActRuTaskEntityList(taskList));
//                })
//        );
//        return new PageUtils(page);
//    }


    @Override
    public void saveLeave(ApplyLeaveEntity leaveEntity) {
        leaveEntity.setStatus(ActivitiConstant.ORDER_STATUS_NEW);
        applyLeaveMapper.insert(leaveEntity);
    }

    @Override
    public List<ApplyLeaveEntity> getAll(ApplyLeaveEntity applyLeaveEntity) {
        Map map = new HashMap();
        if(StringUtils.isNotEmpty(applyLeaveEntity.getTitle())){
            map.put("title",applyLeaveEntity.getTitle());
        }
        if(StringUtils.isNotEmpty(applyLeaveEntity.getLeaveType())){
            map.put("leave_type",applyLeaveEntity.getLeaveType());
        }
        return applyLeaveMapper.selectByMap(map);
    }

    @Override
    public void apply(LeaveApplyDTO leaveApplyDTO) {
        String businessKey = ActivitiConstant.LEAVE_BUSINESSKEY + leaveApplyDTO.getId();
        // 参数
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("users", leaveApplyDTO.getUsers());
        // 校验重复提交
        checkRepeatSubmit(businessKey);

        ProcessInstance processInstance;
        try {
            // 启动流程
            processInstance = runtimeService.startProcessInstanceByKey(ActivitiConstant.LEAVE_KEY, businessKey, variables);
            String processInstanceId = processInstance.getId();
            // 更新业务
            ApplyLeaveEntity applyLeaveEntity = new ApplyLeaveEntity();
            applyLeaveEntity.setId(leaveApplyDTO.getId());
            applyLeaveEntity.setProcessInstanceId(processInstanceId);
            applyLeaveEntity.setUpdateTime(DateUtil.date().toTimestamp());
            applyLeaveEntity.setSubmitTime(DateUtil.date().toTimestamp());
            applyLeaveEntity.setStatus(ActivitiConstant.ORDER_STATUS_SUBMIT);
            baseMapper.updateById(applyLeaveEntity);
            log.info("start process key={}, businessKey={}, pid={}, variables={}", ActivitiConstant.LEAVE_KEY, businessKey,
                    processInstanceId, variables);
        } catch (Exception e) {
            throw new YYException("流程启动失败", e);
        }
    }
//
//    @Override
//    public PageUtils queryTodoPage(Map<String, Object> params) {
//        String userId = (String) params.get("userId");
//        String taskName = (String) params.get("taskName");
//
//        // 分页
//        Query query = new Query<LeaveEntity>(params);
//        int curPage = (int) query.getCurPage();
//        int limit = (int) query.getLimit();
//        // 根据当前人的ID查询，参与者或者执行者
//        TaskQuery taskQuery = taskService.createTaskQuery()
//                .taskCandidateOrAssigned(userId)
//                .orderByTaskCreateTime()
//                .desc();
//        // 任务名称过滤
//        if (StringUtils.isNotBlank(taskName)) {
//            taskQuery.taskName(taskName);
//        }
//        List<Task> tasks = taskQuery.listPage((curPage - 1) * limit, limit);
//        // 返回集合
//        List<LeaveEntity> results = new ArrayList<LeaveEntity>();
//        // 根据流程的业务ID查询实体并关联
//        for (Task task : tasks) {
//            String processInstanceId = task.getProcessInstanceId();
//            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
//                    .processInstanceId(processInstanceId).active().singleResult();
//            if (processInstance == null) {
//                log.warn("任务的流程实例为空processInstanceId:{}", processInstanceId);
//                continue;
//            }
//            String businessKey = processInstance.getBusinessKey();
//            if (StringUtils.isBlank(businessKey)) {
//                log.warn("流程实例的businessKey为空processInstanceId:{}", processInstanceId);
//                continue;
//            }
//            // 获取业务ID
//            String businessId = parseBusinessKey(businessKey);
//            if (StringUtils.isBlank(businessId)) {
//                continue;
//            }
//            LeaveEntity leave = baseMapper.selectById(businessId);
//            // 设置任务
//            leave.setTask(ConvertUtils.convertObject(task));
//            results.add(leave);
//        }
//
//        Page<LeaveEntity> page = query.getPage();
//        page.setTotal(taskQuery.count());
//        page.setRecords(results);
//        return new PageUtils(page);
//    }
//
//    @Override
//    public PageUtils queryDonePage(Map<String, Object> params) {
//        String userId = (String) params.get("userId");
//        String taskName = (String) params.get("taskName");
//
//        // 分页
//        Query query = new Query<LeaveEntity>(params);
//        int curPage = (int) query.getCurPage();
//        int limit = (int) query.getLimit();
//
//        // 创建历史任务实例查询, 我的已完成任务
//        HistoricTaskInstanceQuery hisTaskQuery = historyService.createHistoricTaskInstanceQuery()
//                .taskAssignee(userId) // 指定办理人
//                //.finished() // 查询已经完成的任务
//                .orderByTaskCreateTime()
//                .desc();
//        // 任务名称过滤
//        if (StringUtils.isNotBlank(taskName)) {
//            hisTaskQuery.taskName(taskName);
//        }
//
//        List<HistoricTaskInstance> tasks = hisTaskQuery.listPage((curPage - 1) * limit, limit);
//        // 返回集合
//        List<LeaveEntity> results = new ArrayList<LeaveEntity>();
//        // 根据流程的业务ID查询实体并关联
//        for (HistoricTaskInstance task : tasks) {
//            String processInstanceId = task.getProcessInstanceId();
//            // 历史流程实例
//            HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
//                    .processInstanceId(processInstanceId)
//                    .singleResult();
//            if (processInstance == null) {
//                log.warn("任务的流程实例为空processInstanceId:{}", processInstanceId);
//                continue;
//            }
//            String businessKey = processInstance.getBusinessKey();
//            if (StringUtils.isBlank(businessKey)) {
//                log.warn("流程实例的businessKey为空processInstanceId:{}", processInstanceId);
//                continue;
//            }
//            // 获取业务ID
//            String businessId = parseBusinessKey(businessKey);
//            if (StringUtils.isBlank(businessId)) {
//                continue;
//            }
//            LeaveEntity leave = baseMapper.selectById(businessId);
//            // 设置任务审批结果
//            ActHiTaskinst actHiTaskinst = ConvertUtils.convertObject(task);
//            List<Comment> comments = taskService.getTaskComments(task.getId());
//            if (CollUtil.isNotEmpty(comments)) {
//                actHiTaskinst.setComment(comments.get(0).getFullMessage());
//            }
//            // 设置历史任务
//            leave.setActHiTaskinst(actHiTaskinst);
//
//            results.add(leave);
//        }
//
//        Page<LeaveEntity> page = query.getPage();
//        page.setTotal(hisTaskQuery.count());
//        page.setRecords(results);
//        return new PageUtils(page);
//    }
//
//    @Override
//    public PageUtils queryRunningPage(Map<String, Object> params) {
//        String userId = (String) params.get("userId");
//        String searchBusinesskey = (String) params.get("businessKey");
//
//        // 分页
//        Query query = new Query<LeaveEntity>(params);
//        int curPage = (int) query.getCurPage();
//        int limit = (int) query.getLimit();
//        // 运行中的实例查询
//        ProcessInstanceQuery instancequery = runtimeService.createProcessInstanceQuery()
//                .processDefinitionKey(ActivitiConstant.LEAVE_KEY)
//                .active()
//                .orderByProcessInstanceId()
//                .desc();
//        if (StringUtils.isNotBlank(searchBusinesskey)) {
//            instancequery.processInstanceBusinessKey(searchBusinesskey);
//        }
//        // 运行中的请假实例
//        List<ProcessInstance> list = instancequery.listPage((curPage - 1) * limit, limit);
//
//        // 返回集合
//        List<LeaveEntity> results = new ArrayList<LeaveEntity>();
//        // 关联业务实体
//        for (ProcessInstance processInstance : list) {
//            String businessKey = processInstance.getBusinessKey();
//            if (StringUtils.isBlank(businessKey)) {
//                log.warn("历史流程实例的businessKey为空processInstanceId:{}", processInstance.getId());
//                continue;
//            }
//            // 获取业务ID
//            String businessId = parseBusinessKey(businessKey);
//            if (StringUtils.isBlank(businessId)) {
//                continue;
//            }
//
//            LeaveEntity leave = baseMapper.selectById(businessId);
//            // 设置流程实例
//            leave.setActRuExecution(ConvertUtils.convertObject(processInstance));
//            // 当前任务
//            List<Task> tasks = taskService.createTaskQuery()
//                    .processInstanceId(processInstance.getId())
//                    .active()
//                    .orderByTaskCreateTime()
//                    .desc()
//                    .list();
//
//            leave.setTaskList(ConvertUtils.convertActRuTaskEntityList(tasks));
//            results.add(leave);
//        }
//
//        Page<LeaveEntity> page = query.getPage();
//        page.setTotal(instancequery.count());
//        page.setRecords(results);
//        return new PageUtils(page);
//    }
//
//    @Override
//    public PageUtils queryFinishPage(Map<String, Object> params) {
//        String userId = (String) params.get("userId");
//        String searchBusinesskey = (String) params.get("businessKey");
//
//        // 分页
//        Query query = new Query<LeaveEntity>(params);
//        int curPage = (int) query.getCurPage();
//        int limit = (int) query.getLimit();
//
//        // 历史已完成流程, 已完成的请假实例
//        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery()
//                .processDefinitionKey(ActivitiConstant.LEAVE_KEY)
//                .finished()
//                .orderByProcessInstanceEndTime()
//                .desc();
//        List<HistoricProcessInstance> list = historicProcessInstanceQuery.listPage((curPage - 1) * limit, limit);
//
//        List<LeaveEntity> results = new ArrayList<>();
//        // 关联业务实体
//        for (HistoricProcessInstance historicProcessInstance : list) {
//            String businessKey = historicProcessInstance.getBusinessKey();
//            if (StringUtils.isBlank(businessKey)) {
//                log.warn("历史流程实例的businessKey为空processInstanceId:{}", historicProcessInstance.getId());
//                continue;
//            }
//            // 获取业务ID
//            String businessId = parseBusinessKey(businessKey);
//            if (StringUtils.isBlank(businessId)) {
//                continue;
//            }
//            LeaveEntity leave = baseMapper.selectById(businessId);
//            // 设置历史流程实例
//            leave.setActHiProcinst(ConvertUtils.convertObject(historicProcessInstance));
//            results.add(leave);
//        }
//
//        Page<LeaveEntity> page = query.getPage();
//        page.setTotal(historicProcessInstanceQuery.count());
//        page.setRecords(results);
//        return new PageUtils(page);
//    }
//
//    @Override
//    public List<HistoricActivityInstance> historyActivity(String taskId) {
//        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
//        if (task == null) {
//            log.warn("流程任务不存在, taskId:{}", taskId);
//            return null;
//        }
//        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
//                .processInstanceId(task.getProcessInstanceId())
//                // .finished() // 查询已经完成的任务
//                .list();
//
//        return list;
//    }
//
//    @Override
//    public List<ActHiTaskinst> historyTaskList(String id) {
//        String businessKey = ActivitiConstant.LEAVE_BUSINESSKEY + id;
//        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
//                .processInstanceBusinessKey(businessKey)
//                .list();
//
//        for (HistoricTaskInstance hti : historicTaskInstanceList) {
//            System.out.println("任务ID:" + hti.getId());
//            System.out.println("流程实例ID:" + hti.getProcessInstanceId());
//            System.out.println("任务名称：" + hti.getName());
//            System.out.println("办理人：" + hti.getAssignee());
//            System.out.println("开始时间：" + hti.getStartTime());
//            System.out.println("结束时间：" + hti.getEndTime());
//            System.out.println("=================================");
//        }
//
//        List<ActHiTaskinst> list = ConvertUtils.convertActHiTaskinstList(historicTaskInstanceList);
//        // 获取审批记录
//        if (CollectionUtil.isNotEmpty(list)) {
//            list.forEach(item -> {
//                List<Comment> comments = taskService.getTaskComments(item.getId());
//                if (CollUtil.isNotEmpty(comments)) {
//                    item.setComment(comments.get(0).getFullMessage());
//                }
//            });
//        }
//
//        return list;
//    }
//
//    @Override
//    public Result complete(LeaveOperateDTO leaveOperate) {
//        // 添加审批记录
//        if (StringUtils.isNotBlank(leaveOperate.getComment())) {
//            // 使用任务id,获取任务对象，获取流程实例id
//            Task task = taskService.createTaskQuery().taskId(leaveOperate.getTaskId()).singleResult();
//            // 利用任务对象，获取流程实例id
//            String processInstancesId = task.getProcessInstanceId();
//            taskService.addComment(leaveOperate.getTaskId(), processInstancesId, leaveOperate.getComment());
//        }
//        // 流程参数
//        Map<String, Object> variables = leaveOperate.getParams();
//        taskService.complete(leaveOperate.getTaskId(), variables);
//        // TODO:可以把审批结果设置在任务变量中
//
//        // 判断流程是否结束
//        String businessKey = ActivitiConstant.LEAVE_BUSINESSKEY + leaveOperate.getId();
//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
//                .processInstanceBusinessKey(businessKey)
//                .singleResult();
//        if (processInstance == null) {
//            // 更新业务
//            LeaveEntity leaveEntity = new LeaveEntity();
//            leaveEntity.setId(leaveOperate.getId());
//            leaveEntity.setUpdateTime(DateUtil.date().toTimestamp());
//            leaveEntity.setStatus(ActivitiConstant.ORDER_STATUS_FINISH);
//            baseMapper.updateById(leaveEntity);
//        }
//
//        return Result.ok();
//    }
//
    /**
     * 校验工单重复提交
     *
     * @param businessKey
     */
    private void checkRepeatSubmit(String businessKey) {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .singleResult();
        if (pi != null) {
            throw new YYException("流程启动失败: 工单已存在，请勿重复提交！ProcessInstanceId：" + pi.getId());
        }
        HistoricProcessInstance historyProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .singleResult();
        if (historyProcessInstance != null) {
            throw new YYException("流程启动失败: 工单已存在，请勿重复提交！ProcessInstanceId：" +
                    historyProcessInstance.getId());
        }
    }

//    /**
//     * 获取businessID
//     *
//     * @return
//     */
//    private String parseBusinessKey(String businessKey) {
//        String[] strArray = businessKey.split("\\.");
//        if (strArray == null || strArray.length != 2) {
//            log.warn("流程实例的businessKey解析出错, businessKey:{}", businessKey);
//            return null;
//        }
//        return strArray[1];
//    }

}
