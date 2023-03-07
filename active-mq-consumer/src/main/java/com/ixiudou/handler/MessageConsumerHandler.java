package com.ixiudou.handler;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @Auther: zhouxw
 * @Date: 2019/11/26 14:38
 * @company：CTTIC
 */
@Component
public class MessageConsumerHandler implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println(message.toString());
    }

}
