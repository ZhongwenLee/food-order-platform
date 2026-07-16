package com.foodorder.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.foodorder.common.result.Result;
import com.foodorder.common.constant.ResultCode;
import com.foodorder.exception.BusinessException;

@RestController
@RequestMapping("/api/health")
public class HealthController {
    @GetMapping
    public Result<String> health() {
        return Result.success("ok");
    }
    @GetMapping("/biz-error")
    public Result<String> bizError() {
        throw new BusinessException(ResultCode.NOT_FOUND);
    }
    @GetMapping("/sys-error")
    public Result<String> sysError() {
        throw new IllegalStateException("这都是故意抛出的异常测试");
    }
}
