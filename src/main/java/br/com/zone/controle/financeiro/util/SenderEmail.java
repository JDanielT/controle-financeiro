package br.com.zone.controle.financeiro.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SenderEmail {

    private String SMTP_AUTH_USER;
    private String SMTP_AUTH_PWD;

    public SenderEmail() {
        SMTP_AUTH_USER = "delivery.controle.financeiro@gmail.com";
        SMTP_AUTH_PWD = "PASS_HERE";
    }

    public void send(String subject, String text, String email) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
            }
        });

        Message message = new MimeMessage(session);

        try {

            Address[] toUser = InternetAddress.parse(email);

            message.setFrom(new InternetAddress(SMTP_AUTH_USER));
            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(subject);
            message.setContent(text, "text/html");

            Transport.send(message);

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
