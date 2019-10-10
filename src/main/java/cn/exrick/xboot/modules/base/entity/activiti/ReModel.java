package cn.exrick.xboot.modules.base.entity.activiti;

import cn.exrick.xboot.common.utils.SnowFlakeUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 *  模型实体
 *  @Author: 杨鹏飞
 *  @Description:
 *  @Date: 10:25 2019/9/3
 */
@ApiModel(value = "模型")
public class ReModel{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模型ID")
    private String modelId;

    @ApiModelProperty(value = "模型名称")
    private String name;

    @ApiModelProperty(value = "模型key")
    private String identifier;

    @ApiModelProperty(value = "模型信息")
    private String metaInfo;

    @ApiModelProperty(value = "模型备注")
    private String description;
}
