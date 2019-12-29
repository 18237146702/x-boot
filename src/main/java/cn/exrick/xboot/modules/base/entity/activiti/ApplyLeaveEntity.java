package cn.exrick.xboot.modules.base.entity.activiti;

import cn.exrick.xboot.common.validator.AddGroup;
import cn.exrick.xboot.common.validator.UpdateGroup;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author pf.yang
 * @since 2019-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("it_leave")
public class ApplyLeaveEntity implements Serializable {

    //value与数据库主键列名一致，若实体类属性名与表主键列名一致可省略value
    //设置自增
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    //若没有开启驼峰命名，或者表中列名不符合驼峰规则，可通过该注解指定数据库表中的列名，exist标明数据表中有没有对应列  默认: true
    @TableField(value = "process_instance_id")
    private String processInstanceId;

    @NotBlank(message = "标题不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String title;

    @NotBlank(message = "用户ID不能为空", groups = AddGroup.class)
    @TableField(value = "user_id")
    private String userId;

    @TableField(value = "user_name")
    private String userName;

    private Integer days;

    @NotNull(message = "开始时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "start_time")
    private Timestamp startTime;

    @NotNull(message = "结束时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "end_time")
    private Timestamp endTime;

    @NotBlank(message = "请假类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @TableField(value = "leave_type")
    private String leaveType;

    private String reason;

    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time")
    private Timestamp updateTime;

    @ApiModelProperty(value = "提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "submit_time")
    private Timestamp submitTime;

    @ApiModelProperty(value = "流程任务")
    @TableField(exist = false)
    private ActRuTaskEntity task;

    @ApiModelProperty(value = "流程任务集合")
    @TableField(exist = false)
    private List<ActRuTaskEntity> taskList;

    @ApiModelProperty(value = "流程实例")
    @TableField(exist = false)
    private ActRuExecution actRuExecution;

    @ApiModelProperty(value = "历史流程任务")
    @TableField(exist = false)
    private ActHiTaskinst actHiTaskinst;

    @ApiModelProperty(value = "历史流程实例")
    @TableField(exist = false)
    private ActHiProcinst actHiProcinst;
}
