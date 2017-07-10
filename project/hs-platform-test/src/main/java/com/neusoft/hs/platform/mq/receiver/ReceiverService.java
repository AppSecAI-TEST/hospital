package com.neusoft.hs.platform.mq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.neusoft.hs.platform.mq.model.Bar;
import com.neusoft.hs.platform.mq.model.Foo;

/**
 * Created by patterncat on 2016-02-02.
 */
@Component
public class ReceiverService {

    @RabbitListener(queues = "queue.foo")
    public void receiveFooQueue(Foo foo) {
        System.out.println("Received Foo<" + foo.getName() + ">");
    }

    @RabbitListener(queues = "queue.bar")
    public void receiveBarQueue(Bar bar) {
        System.out.println("Received Bar<" + bar.getAge() + ">");
    }
}
