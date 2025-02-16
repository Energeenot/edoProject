//package com.example.edo.mailSender;
//
//
//import jakarta.mail.*;
//import jakarta.mail.internet.*;
//
//import java.util.Date;
//import java.util.Properties;
//
//public class Sender {
//    private final String username = "";
//    private final String password = "";
//    private final Properties props;
//
//    public Sender() {
//
//        props = new Properties();
//        props.put("mail.smtp.host", "smtp.mail.ru");
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.port", "465");
//    }
//// отправка уведомления о новых файлах
//    public void sendNotificationOfNewDocuments(String uniqueCode, String toEmail, String fio, String numberGroup, String messageToTeacher){
//        Session session = getSession();
//
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));// от кого
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // кому
//            message.setSubject("Поступили документы на проверку");// тема сообщения
//
//            Date date = new Date();
//            String str = String.format("Время поступления документов: %tc", date);
//
//            MimeBodyPart mimeBodyPart = new MimeBodyPart();
//            mimeBodyPart.setContent(str, "text/html; charset=utf-8");
//            MimeBodyPart bodyPartMessage = new MimeBodyPart();
//            bodyPartMessage.setText("Ученик" + fio + "из " + numberGroup + " группы сдал документы на проверку, введите код ниже на странице 'Валидатор' для проверки документов.");
//            MimeBodyPart uniqueCodePart = new MimeBodyPart();
//            uniqueCodePart.setText(uniqueCode);
//            MimeBodyPart messagePart = new MimeBodyPart();
//            messagePart.setText("Сообщение от ученика: " + messageToTeacher);
//            MimeBodyPart bodyPartText = new MimeBodyPart();
//            bodyPartText.setText("Зайдите в личный аккаунт перед проверкой");
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(mimeBodyPart);
//            multipart.addBodyPart(bodyPartMessage);
//            multipart.addBodyPart(uniqueCodePart);
//            multipart.addBodyPart(messagePart);
//            multipart.addBodyPart(bodyPartText);
//            message.setContent(multipart);
//            session.setDebug(true);
//
//            Transport.send(message);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void sendNotificationOfResetPassword(String token, String toEmail){
//        Session session = getSession();
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));// от кого
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // кому
//            message.setSubject("Попытка восстановить пароль");// тема сообщения
//
//            Date date = new Date();
//            String str = String.format("Попытка восстановить пароль поступила в: %tc", date);
//
//            MimeBodyPart mimeBodyPart = new MimeBodyPart();
//            mimeBodyPart.setContent(str, "text/html; charset=utf-8");
//            MimeBodyPart bodyPartBasicMessage = new MimeBodyPart();
//            bodyPartBasicMessage.setText("Введите следующий код в специальном поле, чтобы восстановить пароль");// текст
//            MimeBodyPart bodyPartMessage = new MimeBodyPart();
//            bodyPartMessage.setText("Вернитесь на страницу восстановления пароля, и введите код в специальное поле.");
//            MimeBodyPart uniqueCodePart = new MimeBodyPart();
//
//            uniqueCodePart.setText(token);
//            MimeBodyPart bodyPartText = new MimeBodyPart();
//            bodyPartText.setText("http://localhost:8080/checkCode");
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(mimeBodyPart);
//            multipart.addBodyPart(bodyPartBasicMessage);
//            multipart.addBodyPart(bodyPartMessage);
//            multipart.addBodyPart(uniqueCodePart);
//            multipart.addBodyPart(bodyPartText);
//            message.setContent(multipart);
//            session.setDebug(true);
//
//            Transport.send(message);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void sendFeedbackToStudent(String toEmail, String teacherName, String feedback){
//        Session session = getSession();
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));// от кого
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // кому
//            message.setSubject("Сообщение от преподавателя о документах");// тема сообщения
//
//            Date date = new Date();
//            String str = String.format("Сообщение отправили в: %tc", date);
//
//            MimeBodyPart mimeBodyPart = new MimeBodyPart();
//            mimeBodyPart.setContent(str, "text/html; charset=utf-8");
//            MimeBodyPart bodyPartMessage = new MimeBodyPart();
//            bodyPartMessage.setText("Преподаватель " + teacherName + " отправил вам сообщение: " + feedback);
//
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(mimeBodyPart);
//            multipart.addBodyPart(bodyPartMessage);
//            message.setContent(multipart);
//            session.setDebug(true);
//
//            Transport.send(message);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private Session getSession() {
//        return Session.getInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//    }
//}
