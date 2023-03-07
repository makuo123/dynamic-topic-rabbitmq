package com.ixiudou.controller;

import com.alibaba.fastjson.JSONObject;
import com.ixiudou.util.OKHttpClientUtil;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Description: TODO
 * @Auther: zhouxw
 * @Date: 2019/11/25 21:03
 * @company：CTTIC
 */
@RestController
@AllArgsConstructor
public class ProducerController {

    private RabbitTemplate rabbit;

    private RabbitAdmin rabbitAdmin;



    @GetMapping("/queue_test")
    public String produce(String queueName ,String message){
        createMQIfNotExist(queueName,queueName);
        rabbit.convertAndSend(queueName,message);
        return "消息已经发送";
    }

    private void createMQIfNotExist(String queueName ,String exchangeName) {
        //判断队列是否存在
        Properties properties = rabbitAdmin.getQueueProperties(queueName);
        if(properties == null){
            Queue queue = new Queue(queueName, true, false, false, null);
            FanoutExchange fanoutExchange = new FanoutExchange(exchangeName);
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareExchange(fanoutExchange);
            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(fanoutExchange));
            //新启动一个线程，通知消费者新增listener
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String res = callAddNewListener(queueName);
                    if(!StringUtils.isEmpty(res)){
                        System.out.println("-->>调用创建新的 listener feign 失败");
                    }
                }
            }).start();

        }


    }

    private String callAddNewListener(String queueName){
        String url = "http://localhost:8020/add_new_listener";
        Map<String,String> param = new HashMap<String,String>();
        param.put("queueName",queueName);
        try {
            OKHttpClientUtil.doGet(url,null,param);
        }catch (Exception e){
            e.printStackTrace();
            return "调用添加listener feign失败";
        }
        return null;

    }

}
