package com.example.edo.kafka;

import com.example.edo.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class SenderProducer {

    private final KafkaTemplate<String, MessageDto> template;

    public void sendFeedback(MessageDto dto) {
        log.info("Sending feedback");
        String messageKey = UUID.randomUUID().toString();

        Message<MessageDto> kafkaMessage = MessageBuilder.withPayload(dto)
                .setHeader(KafkaHeaders.TOPIC, "sendFeedbackToStudent")
                .setHeader(KafkaHeaders.KEY, messageKey)
//                .setHeader(headerKey, headerValue)
                .build();
        template.send(kafkaMessage);
    }

    public void sendNotificationOfResetPassword(MessageDto dto) {
        log.info("Sending notification of reset Password");
        String messageKey = UUID.randomUUID().toString();

        Message<MessageDto> message = MessageBuilder.withPayload(dto)
                .setHeader(KafkaHeaders.TOPIC, "sendNotificationOfResetPassword")
                .setHeader(KafkaHeaders.KEY, messageKey)
                .build();
        template.send(message);
    }

    public void sendNotificationOfNewDocuments(MessageDto dto) {
        log.info("Sending notification of New Documents");
        String messageKey = UUID.randomUUID().toString();

        Message<MessageDto> message = MessageBuilder.withPayload(dto)
                .setHeader(KafkaHeaders.TOPIC, "sendNotificationOfNewDocuments")
                .setHeader(KafkaHeaders.KEY, messageKey)
                .build();
        template.send(message);
    }
// todo: сделать отправку сообщения в кафку асинхронной

}
