/**
 * Copyright 2018 lenos
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.exrick.xboot.modules.base.entity.activiti.dto;

import cn.exrick.xboot.common.validator.AddGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

/**
 * @author 司马缸砸缸了
 * @description 任务操作
 * @date 2019/7/27 19:05
 */
@Data
public class LeaveOperateDTO implements Serializable {
    @ApiModelProperty(value = "工单id", name = "工单id", required = true)
    @NotBlank(message = "工单id不能为空", groups = AddGroup.class)
    private String id;

    @ApiModelProperty(value = "任务id", name = "任务id", required = true)
    @NotBlank(message = "任务id不能为空", groups = AddGroup.class)
    private String taskId;

    @ApiModelProperty(value = "审批意见", name = "comment")
    private String comment;

    @ApiModelProperty(value = "流程参数", name = "流程参数")
    private Map<String, Object> params;

}
