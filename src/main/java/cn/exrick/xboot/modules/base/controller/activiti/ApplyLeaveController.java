package cn.exrick.xboot.modules.base.controller.activiti;


import cn.exrick.xboot.base.BaseController;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.validator.AddGroup;
import cn.exrick.xboot.common.validator.ValidatorUtils;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.base.entity.activiti.ApplyLeaveEntity;
import cn.exrick.xboot.modules.base.entity.activiti.dto.LeaveApplyDTO;
import cn.exrick.xboot.modules.base.service.activiti.IApplyLeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(description = "工作流接口")
@Slf4j
@RestController
@RequestMapping("/xboot/leave")
@Transactional
public class ApplyLeaveController extends BaseController {

    @Autowired
    private IApplyLeaveService leaveService;

    @ApiOperation(value = "保存请假", notes = "保存请假")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result insert(@ModelAttribute ApplyLeaveEntity applyLeaveEntity) throws Exception {
        // 默认管理员方便测试
        applyLeaveEntity.setUserId("1");
        applyLeaveEntity.setUserName("admin");
        // 保存
        leaveService.saveLeave(applyLeaveEntity);
        return new ResultUtil<Object>().setSuccessMsg("保存成功");
    }

    @ApiOperation(value = "提交申请", notes = "提交申请")
    @RequestMapping(value = "/applyLeave",method = RequestMethod.POST)
    public Result applyLeave(@ModelAttribute LeaveApplyDTO leaveApplyDTO) {
        leaveService.apply(leaveApplyDTO);
        return new ResultUtil<Object>().setSuccessMsg("保存成功");
    }


    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    @ApiOperation(value = "获取全部申请")
    public Result<List<ApplyLeaveEntity>> getAll(@ModelAttribute ApplyLeaveEntity applyLeaveEntity){
        List<ApplyLeaveEntity> leaveEntityList = leaveService.getAll(applyLeaveEntity);
        if(null != leaveEntityList && leaveEntityList.size() > 0){
            return new ResultUtil<List<ApplyLeaveEntity>>().setData(leaveEntityList);
        }
        return new ResultUtil<List<ApplyLeaveEntity>>().setErrorMsg("获取数据为空");
    }

    @ApiOperation(value = "删除申请")
    @RequestMapping(value = "/deleteLeave/{id}",method = RequestMethod.POST)
    public Result<Object> deleteLeave(@PathVariable String id){
        leaveService.removeById(id);
        return new ResultUtil<Object>().setData(id);
    }

}
