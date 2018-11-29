## Practice RabbitMQ with Spring boot

### RabbitMQ 설치/실행/접속 방법 (on Mac)
```sql
- 설치
brew install rabbitmq
-실행
brew services start rabbitmq
- 관리페이지 접속 방법
http://localhost:15672
최초 계정 정보: guest/guest

- 개발시 접속 포트는 5672

````

### 큐 추가 방법
```sql
1. Queues 탭 선택

2. Add a new queue
    name: hello
3. Add Queue 로 추가

```

### queue type
````sql
    - Fanout (Broadcast to all queues) 기본 
      하나의 메시지를 다양한 큐에 메시지를 전송 하도록 한다.
        example: member 정보를 accounting,marketing 큐로 바인딩하여 cunsuming 하도록 한다.
          queue 생성: q.hr.account, q.hr.marketing
          exchange 생성: x.hr
          바인딩: x.hr 에 q.hr.account,q.hr.marketing 큐를 바인딩 해준다. (타입은 fanout 으로 선) 
                방법: x.hr 을 선택 후 Bindings 옵션에서 Tobinding 에 바인딩할 큐(위에서 생성한 큐) 이름을 입력 하도록 한다.
                확인: queue 탭에서 바인딩 설정 되어있는지 확인 하도록 한다.
            
    
    - Direct
      지정한 큐로 보내도록 한다.
      라우팅 키 기반으로 동작 한다.
      메시지를 페기 할수 있다.
      설정은 Fanout 과 동일하며, 타입 설정만 direct 로 지정한다.
      스프링에서 메시지 전송시 타입을 지정하여 전송 하도록 한다.
    
    - Topic
      바인딩 된 큐와 아래 두가지 특별한 라우팅 키 기반 (정규식)으로 토픽에서 큐로 메시지를 전송 할 수 있다.
      Multiple criteria routing
      Tow special characters on routing key 
        1. *
        2. #
        
      
````

### Error handling
````sql
    DLX (Dead Letter Exchange)
    예외가 발생 하거나, 네트워크 전송 문제로 실패시 저장될 큐로 저장하도록 한다.
    방법:
      예외 발생시 사용할 exchange 생성. 예외발생시 저장할 큐와 바인딩한다.  
      실제 사용할 큐 생성시 arguments: x-dead-letter-exchange = exchange (dlx 발생시 라우팅할 excange 이름 설정)
        
    
````