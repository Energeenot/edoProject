package com.example.edo.mailSender;


import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Date;
import java.util.Properties;

public class Sender {
    private final String username = "edopoject@mail.ru";
    private final String password = "ctsjtWNvLTrxhifKCYq9";
    private final Properties props;

    public Sender() {

        props = new Properties();
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }
// отправка уведомления о новых файлах
    public void sendNotificationOfNewDocuments(String uniqueCode, String toEmail){
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));// от кого
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // кому
            message.setSubject("Поступили документы на проверку");// тема сообщения
            message.setText("Ученик сдал документы на проверку, введите код ниже на странице 'Валидатор' для проверки документов.");// текст


//            дата и время
            Date date = new Date();
            String str = String.format("Время поступления документов: %tc", date);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(str, "text/html; charset=utf-8");
            MimeBodyPart bodyPartMessage = new MimeBodyPart();
            bodyPartMessage.setText("Ученик сдал документы на проверку, введите код ниже на странице 'Валидатор' для проверки документов.");
            MimeBodyPart uniqueCodePart = new MimeBodyPart();


//            bodyPart.setContent("Средняя температура 9 градусов цельсия, весь день облачно.", "text/html; charset=utf-8");
//            MimeBodyPart attachmentBodyPart = new MimeBodyPart();

//            attachmentBodyPart.attachFile(new File("C:\\Users\\abram\\IdeaProjects\\sendingLetter\\src\\main\\java\\kot_i_komp.jpg"));
//            MimeBodyPart attachmentJavaPart = new MimeBodyPart();
//            attachmentJavaPart.attachFile(new File("C:\\Users\\abram\\IdeaProjects\\sendingLetter\\src\\main\\java\\Main.java"));
//            MimeBodyPart attachmentJava1Part = new MimeBodyPart();
//            attachmentJava1Part.attachFile(new File("C:\\Users\\abram\\IdeaProjects\\sendingLetter\\src\\main\\java\\Sender.java"));

            uniqueCodePart.setText(uniqueCode);
            MimeBodyPart bodyPartText = new MimeBodyPart();
            bodyPartText.setText("Зайдите в личный аккаунт перед проверкой");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            multipart.addBodyPart(bodyPartMessage);
            multipart.addBodyPart(uniqueCodePart);
            multipart.addBodyPart(bodyPartText);
//            multipart.addBodyPart(attachmentBodyPart);
//            multipart.addBodyPart(attachmentJavaPart);
//            multipart.addBodyPart(attachmentJava1Part);
            message.setContent(multipart);
            session.setDebug(true);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
//        catch (MessagingException | IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
