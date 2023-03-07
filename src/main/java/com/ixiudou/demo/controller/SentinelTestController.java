package com.ixiudou.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @Auther: zhouxw
 * @Date: 2019/11/22 11:17
 * @company：CTTIC
 */
@RestController
@RequestMapping("sentinelTest/")
public class SentinelTestController {

    @GetMapping("test")
    public String sentinelTest(){
        return "this is a test for sentinel";
    }
}
