package cn.anytec.anguan.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by imyzt on 2018/8/24 17:07
 */
@RestController
@RequestMapping("/api")
@Api(value = "接口列表")
public class RestApiController {

    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public List<String> list(String input) {
        return Arrays.asList("1,2,3,4,5,6");
    }

}
