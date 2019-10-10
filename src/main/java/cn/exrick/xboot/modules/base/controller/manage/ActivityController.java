package cn.exrick.xboot.modules.base.controller.manage;

import cn.exrick.xboot.common.vo.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@Api(description = "工作流接口")
@RequestMapping("/xboot/activiti")
@Transactional
public class ActivityController {

    @RequestMapping(value = "/modeler/{id}",method = RequestMethod.GET)
    public String addModel(@PathVariable String id, HttpServletResponse response){
        String str = "0";
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:9000");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "accept,x-requested-with,Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        /*response.setHeader("Cache-Control","no-cache");
        response.setHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "accept,x-requested-with,Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:9000");*/
        return "redirect:/modeler.html?modelId="+id;
    }
}
