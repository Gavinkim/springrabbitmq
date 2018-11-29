package com.example.rabbitmq.consumer;

import com.example.rabbitmq.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by gavinkim at 2018-11-30
 */
@Slf4j
@Service
public class RabbitConsumer {


    private ObjectMapper objectMapper = new ObjectMapper();

    //실시간으로 큐에 들어오는 메시지를 읽어서 출력 한다.
    //concurrency 는 컨슈머 최소 수
    @RabbitListener(queues = "hello",concurrency = "3")
    public void listen(String message) throws InterruptedException{
        Thread.sleep(ThreadLocalRandom.current().nextInt(2000));
        log.info("Consuming: {}",message);
    }

    @RabbitListener(queues = "q.hello")
    public void listenDlx(String message) throws InterruptedException{
        Thread.sleep(ThreadLocalRandom.current().nextInt(2000));
        throw new AmqpRejectAndDontRequeueException("Exception.....XXXX");
    }

    //컨버팅 된 member 의 문자열을 읽어 온다.
    @RabbitListener(queues = "member",concurrency = "3")
    public void listenMember(String message) throws Exception{
        Thread.sleep(ThreadLocalRandom.current().nextInt(2000));
        var member = objectMapper.readValue(message, Member.class);
        log.info("Consuming Member: {}",member.toString());
    }


    //q.hr.accounting 으로부터 읽어온다.
    @RabbitListener(queues = "q.hr.accounting")
    public void listenAccounting(String message) throws Exception{
        Thread.sleep(ThreadLocalRandom.current().nextInt(2000));
        var member = objectMapper.readValue(message, Member.class);
        log.info("Consuming Accounting: {}",member.toString());
    }

    //q.hr.marketing 으로부터 읽어온다.
    @RabbitListener(queues = "q.hr.accounting")
    public void listenMarketing(String message) throws Exception{
        Thread.sleep(ThreadLocalRandom.current().nextInt(2000));
        var member = objectMapper.readValue(message, Member.class);
        log.info("Consuming Marketing: {}",member.toString());
    }

}
