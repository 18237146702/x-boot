package cn.exrick.xboot.modules.base.entity.activiti;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 运行时流程执行实例
 * @author pf.yang
 * @since 2019-09-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ACT_RU_EXECUTION")
public class ActRuExecution implements Serializable {


    @TableId(value = "ID_")
    private String id;

    @TableField("REV_")
    private Integer rev;

    @TableField("PROC_INST_ID_")
    private String procInstId;

    @TableField("BUSINESS_KEY_")
    private String businessKey;

    @TableField("PARENT_ID_")
    private String parentId;

    @TableField("PROC_DEF_ID_")
    private String procDefId;

    @TableField("SUPER_EXEC_")
    private String superExec;

    @TableField("ROOT_PROC_INST_ID_")
    private String rootProcInstId;

    @TableField("ACT_ID_")
    private String actId;

    @TableField("IS_ACTIVE_")
    private String isActive;

    @TableField("IS_CONCURRENT_")
    private String isConcurrent;

    @TableField("IS_SCOPE_")
    private String isScope;

    @TableField("IS_EVENT_SCOPE_")
    private String isEventScope;

    @TableField("IS_MI_ROOT_")
    private String isMiRoot;

    @TableField("SUSPENSION_STATE_")
    private Integer suspensionState;

    @TableField("CACHED_ENT_STATE_")
    private Integer cachedEntState;

    @TableField("TENANT_ID_")
    private String tenantId;

    @TableField("NAME_")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("START_TIME_")
    private Timestamp startTime;

    @TableField("START_USER_ID_")
    private String startUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("LOCK_TIME_")
    private Timestamp lockTime;

    @TableField("IS_COUNT_ENABLED_")
    private String isCountEnabled;

    @TableField("EVT_SUBSCR_COUNT_")
    private Integer evtSubscrCount;

    @TableField("TASK_COUNT_")
    private Integer taskCount;

    @TableField("JOB_COUNT_")
    private Integer jobCount;

    @TableField("TIMER_JOB_COUNT_")
    private Integer timerJobCount;

    @TableField("SUSP_JOB_COUNT_")
    private Integer suspJobCount;

    @TableField("DEADLETTER_JOB_COUNT_")
    private Integer deadletterJobCount;

    @TableField("VAR_COUNT_")
    private Integer varCount;

    @TableField("ID_LINK_COUNT_")
    private Integer idLinkCount;


}
