package cn.exrick.xboot.modules.base.entity.activiti;

import cn.exrick.xboot.common.utils.SnowFlakeUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *  模型实体
 *  @Author: 杨鹏飞
 *  @Description:
 *  @Date: 10:25 2019/9/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ACT_RE_MODEL")
public class ActReModelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "ID_")
    private String id;

    @ApiModelProperty(value = "乐观锁版本号")
    @TableField("REV_")
    private Integer rev;

    @ApiModelProperty(value = "模型的名称")
    @TableField("NAME_")
    private String name;

    @ApiModelProperty(value = "模型的关键字")
    @TableField("KEY_")
    private String key;

    @ApiModelProperty(value = "类型")
    @TableField("CATEGORY_")
    private String category;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_TIME_")
    private Timestamp createTime;

    @ApiModelProperty(value = "最后修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("LAST_UPDATE_TIME_")
    private Timestamp lastUpdateTime;

    @ApiModelProperty(value = "版本，从1开始")
    @TableField("VERSION_")
    private Integer version;

    @ApiModelProperty(value = "数据元信息，json格式")
    @TableField("META_INFO_")
    private String metaInfo;

    @ApiModelProperty(value = "部署ID")
    @TableField("DEPLOYMENT_ID_")
    private String deploymentId;

    @ApiModelProperty(value = "编辑源值ID，是 ACT_GE_BYTEARRAY 表中的ID_值")
    @TableField("EDITOR_SOURCE_VALUE_ID_")
    private String editorSourceValueId;

    @ApiModelProperty(value = "编辑源额外值ID，是 ACT_GE_BYTEARRAY 表中的ID_值")
    @TableField("EDITOR_SOURCE_EXTRA_VALUE_ID_")
    private String editorSourceExtraValueId;

    @TableField("TENANT_ID_")
    private String tenantId;

    @TableField(exist = false)
    private String description;
}
