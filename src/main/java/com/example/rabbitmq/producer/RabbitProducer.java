package com.example.rabbitmq.producer;

import com.example.rabbitmq.entity.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by gavinkim at 2018-11-30
 */
@Slf4j
@Service
public class RabbitProducer {

    @Value("${my.queue.hello}")
    private String helloQueue;

    @Value("${my.queue.member}")
    private String memberQueue;

    @Value("${my.queue.q.hello}")
    private String qHelloQueue;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    //큐에 String 문자를 전송 한다.
    public void sendStringMessage(String queue,String message){
        rabbitTemplate.convertAndSend(queue,message);
    }

    //큐에 객체 를 json 으로 컨버팅 하여 전송 한다.
    public void sendMember(String queue,Member member) throws JsonProcessingException{
        var json = objectMapper.writeValueAsString(member);
        rabbitTemplate.convertAndSend(queue,json);
    }

    //Fanout 타입으로 exchange 의 x.hr 에 Member 객체를 전송 하도록 한다. 해당 객체를 전송 하면 바인딩된 각 큐들에 전송 되어 진다.
    public void sendFanoutMember(Member member) throws JsonProcessingException{
        var json = objectMapper.writeValueAsString(member);
        rabbitTemplate.convertAndSend("x.hr","",json);
    }

    //1초마다 해당 큐로 메시지를 전송한다.
    //@Scheduled(fixedDelay = 1000)
    public void sendTaskMesasge(){
        sendStringMessage(helloQueue,"Gavin"+Math.random());
    }

    //1초마다 해당 큐로 메시지를 전송한다.
    @Scheduled(fixedDelay = 2000)
    public void sendTaskQHelloMessage(){
        sendStringMessage(qHelloQueue,"Gavin"+Math.random());
    }

    //1초마다 해당 큐로 메시지를 전송한다.
    //@Scheduled(fixedDelay = 1000)
    public void sendTaskMember() throws Exception {
        sendMember(memberQueue,Member.builder()
                .email(String.format("test%s@gmail.com",Math.random()))
                .name(String.format("Gavin%s",Math.random()))
                .phone(String.format("0101234%s",Math.random()))
                .build());
    }

   // @Scheduled(fixedDelay = 1000)
    public void sendTaskFanoutMember() throws Exception {
        sendFanoutMember(Member.builder()
                .email(String.format("test%s@gmail.com",Math.random()))
                .name(String.format("Gavin%s",Math.random()))
                .phone(String.format("0101234%s",Math.random()))
                .build());
    }

}
