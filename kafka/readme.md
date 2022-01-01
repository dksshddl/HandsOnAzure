# HandsOnAzure kafka 과제

## 1. kafka 설정
> kafka config document: https://kafka.apache.org/documentation/#configuration
> 
> spring kafka document: https://docs.spring.io/spring-kafka/reference/html/

### 1.1 configuration (파일을 통해 bean 등록)
src/main/reosurces에서 yml or properties 파일을 통해 설정 가능

### 1.2 java code (bean 직접 등록)
spring에서 제공하는 kafka 설정 파일들로 직접 bean 등록 가능, kafka security 설정을 위해서는 파일을 통한 등록보다 다소 복잡 
- consumer/producer bean 등록 예제코드
``` java
@Bean
public ConsumerFactory<String, Email> consumer() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, <bootstrapserver>);
    return new DefaultKafkaConsumerFactory<>(props);
}

@Bean
public ProducerFactory<String, Email> producer() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, <bootstrapserver>); 
    return new DefaultKafkaProducerFactory<>(props);
}

@Bean
public KafkaTemplate<String, Email> kafkaTemplate() {
    return new KafkaTemplate<>(producer());
}

@Bean
public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Email> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumer());
    factory.setBatchListener(true);
    return factory;
}
```
<br/>
 
## 2. serializer & deserializer
kafka에서는 전송 및 수신을 위한 integer, list, string 등 다양한 serializer 제공
만약, 내가 만든 객체를 json 전송하고 싶으면 직접 구현 필요

- 방법
  1. kafka에서 제공하는 serializer, deserializer interface를 통해 customizing
  2. org.springframework.kafka.support.serializer.JsonSerializer를 생성(trsut 어쩌고가 발생해서 추가 설정필요 근데 쉬움)
<br/>

## 3. TODO
- kafka security 공부
- serializer/deserializer 공부
<br/>

## 4. 문제
1. spring cloud version 문제가 있었음 openfeign은 spring cloud에 포함되어 있고,
각  springboot 버전에 맞는 버전을 추가해야함. 아래 document 참고
> spring cloud document: https://spring.io/projects/spring-cloud
>
> spring cloud release github: https://github.com/spring-cloud/spring-cloud-release/wiki/Spring-Cloud-2021.0-Release-Notes
<br/>

2. 이메일 전송 시, 여러개의 주소를 보내기 위해서는 <주소1>;<주소2>와 같은 포맷으로 전송해야함
but, 실제 코드에서는 여러개의 입력은 array로 받는 경우가 일반적이기 때문에, String[]으로 받은 데이터를 세미콜론으로 
join해줘야 함. --> 이러면 또 kafka serialization, json serialization(controller requestbody)에서 문제가 발생함
feign에 request할 vo, controller의 request vo를 따로 만들어서 변환해줘야 할 지, serializer를 뚝딱뚝딱
만들어야 하는지 고민이 조금 필요
<br/>

3. 이건 이유를 모르겠는데 azure portal에서 hostname이 안보이는 문제 (원래 개요에 있음)
근데 동작은함 (namespace.servicebus.windows.net:9093) 이런식
