package com.ixiudou.feign;

import com.ixiudou.handler.QueueService;
import com.ixiudou.util.SpringUtil;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.annotation.ExceptionProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: TODO
 * @Auther: zhouxw
 * @Date: 2019/11/26 16:24
 * @company：CTTIC
 */
@RestController
public class MqConsumerFeign {

    @Autowired
    private QueueService queueService;

    @GetMapping("add_new_listener")
    public String addNewListener(String queueName){
        SimpleMessageListenerContainer container = SpringUtil.getBean(SimpleMessageListenerContainer.class);
        List<String> queueNameList = queueService.getMQJSONArray();
        int count = 0;
        while(!queueNameList.contains(queueName)){
            queueNameList = queueService.getMQJSONArray();
            count++;
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(count >=5){
                return "动态添加监听失败";
            }
        }
        container.addQueueNames(queueName);
        long consumerCount = container.getActiveConsumerCount();
        return "修改成功:现有队列监听者["+consumerCount+"]个";
    }
}
