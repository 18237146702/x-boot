package cn.exrick.xboot.modules.base.entity.activiti;

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
 * 流程历史记录 -历史的流程实例
 * @author pf.yang
 * @since 2019-09-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ACT_HI_PROCINST")
public class ActHiProcinst implements Serializable {


    @TableId(value = "ID_")
    private String id;

    @TableField("PROC_INST_ID_")
    private String procInstId;

    @TableField("BUSINESS_KEY_")
    private String businessKey;

    @TableField("PROC_DEF_ID_")
    private String procDefId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("START_TIME_")
    private Timestamp startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("END_TIME_")
    private Timestamp endTime;

    @TableField("DURATION_")
    private Long duration;

    @TableField("START_USER_ID_")
    private String startUserId;

    @TableField("START_ACT_ID_")
    private String startActId;

    @TableField("END_ACT_ID_")
    private String endActId;

    @TableField("SUPER_PROCESS_INSTANCE_ID_")
    private String superProcessInstanceId;

    @TableField("DELETE_REASON_")
    private String deleteReason;

    @TableField("TENANT_ID_")
    private String tenantId;

    @TableField("NAME_")
    private String name;


}
