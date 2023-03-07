package com.ixiudou.test;

import com.ixiudou.handler.QueueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: TODO
 * @Auther: zhouxw
 * @Date: 2019/11/26 15:23
 * @company：CTTIC
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CommonTest {

    @Resource
    private QueueService service;

    @Test
    public void test_2_common(){
        List<String> list = service.getMQJSONArray();
        System.out.println(list);
    }

}
