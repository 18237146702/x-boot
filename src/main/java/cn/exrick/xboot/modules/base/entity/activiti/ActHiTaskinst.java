package cn.exrick.xboot.modules.base.entity.activiti;

import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 * @author pf.yang
 * @since 2019-09-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ActHiTaskinst implements Serializable {


    @TableId("ID_")
    private String id;

    @TableField("PROC_DEF_ID_")
    private String procDefId;

    @TableField("TASK_DEF_KEY_")
    private String taskDefKey;

    @TableField("PROC_INST_ID_")
    private String procInstId;

    @TableField("EXECUTION_ID_")
    private String executionId;

    @TableField("NAME_")
    private String name;

    @TableField("PARENT_TASK_ID_")
    private String parentTaskId;

    @TableField("DESCRIPTION_")
    private String description;

    @TableField("OWNER_")
    private String owner;

    @TableField("ASSIGNEE_")
    private String assignee;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("START_TIME_")
    private Timestamp startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CLAIM_TIME_")
    private Timestamp claimTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("END_TIME_")
    private Timestamp endTime;

    @TableField("DURATION_")
    private Long duration;

    @TableField("DELETE_REASON_")
    private String deleteReason;

    @TableField("PRIORITY_")
    private Integer priority;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("DUE_DATE_")
    private Timestamp dueDate;

    @TableField("FORM_KEY_")
    private String formKey;

    @TableField("CATEGORY_")
    private String category;

    @TableField("TENANT_ID_")
    private String tenantId;

    @ApiModelProperty(value = "审批意见")
    @TableField(exist = false)
    private String comment;
}
