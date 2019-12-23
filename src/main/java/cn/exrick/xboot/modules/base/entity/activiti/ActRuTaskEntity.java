package cn.exrick.xboot.modules.base.entity.activiti;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 流程运行实例表  -   运行时任务
 * @author pf.yang
 * @since 2019-09-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ACT_RU_TASK")
public class ActRuTaskEntity implements Serializable {


    @TableId(value = "ID_")
    private String id;

    @TableField("REV_")
    private Integer rev;

    @TableField("EXECUTION_ID_")
    private String executionId;

    @TableField("PROC_INST_ID_")
    private String procInstId;

    @TableField("PROC_DEF_ID_")
    private String procDefId;

    @TableField("NAME_")
    private String name;

    @TableField("PARENT_TASK_ID_")
    private String parentTaskId;

    @TableField("DESCRIPTION_")
    private String description;

    @TableField("TASK_DEF_KEY_")
    private String taskDefKey;

    @TableField("OWNER_")
    private String owner;

    @TableField("ASSIGNEE_")
    private String assignee;

    @TableField("DELEGATION_")
    private String delegation;

    @TableField("PRIORITY_")
    private Integer priority;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_TIME_")
    private Timestamp createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("DUE_DATE_")
    private Timestamp dueDate;

    @TableField("CATEGORY_")
    private String category;

    @TableField("SUSPENSION_STATE_")
    private Integer suspensionState;

    @TableField("TENANT_ID_")
    private String tenantId;

    @TableField("FORM_KEY_")
    private String formKey;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CLAIM_TIME_")
    private Timestamp claimTime;


}
