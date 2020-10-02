package br.com.integracaoKafka.ecommerce;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class newOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
      var producer = new KafkaProducer<String,String>(properties());
      var Key = UUID.randomUUID().toString();
       var value = Key + ",5478,123456";
       var record = new ProducerRecord<>("ECOMMERCER_NEW_ORDER", Key, value);
        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset " + data.offset() + "/ timestamp "
                    + data.timestamp());
        };
        var email = "welcome! we are processing your order!";
        var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", Key, email);
        producer.send(record,callback).get();
       producer.send(emailRecord, callback).get();
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
/*
    version: '3'
    services:



    kafka:
    image: wurstmeister/kafka:latest
    ports:
            - "9092:9092"
    environment:
    KAFKA_ADVERTISED_HOST_NAME: localhost
    KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181


    zookeeper:
    hostname: zookeeper
    container_name: zookeeper
    image: 'bitnami/zookeeper:latest'
    environment:
            - ALLOW_ANONYMOUS_LOGIN=yes




    volumes:

    kafka_conf:*/


}
