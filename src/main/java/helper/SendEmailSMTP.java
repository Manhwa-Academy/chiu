package helper;

import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;

public class SendEmailSMTP {

    public static String getOTP() {
        int min = 100000;
        int max = 999999;
        return Integer.toString((int) ((Math.random() * (max - min)) + min));
    }

    public static boolean sendOTP(String emailTo, String otp) {

        String username = "vuliztva1@gmail.com";
        String password = ""; //efdv dhbh njts oztg

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("Mã OTP khôi phục mật khẩu");

            String html = "<div style='font-family:Arial;padding:20px'>"
                    + "<h2 style='color:#0b5394'>Hệ thống quản lý kho</h2>"
                    + "<p>Xin chào,</p>"
                    + "<p>Bạn vừa yêu cầu đặt lại mật khẩu.</p>"
                    + "<p>Mã OTP của bạn (hiệu lực 5 phút):</p>"
                    + "<h1 style='background:#0b5394;color:white;display:inline-block;padding:10px 20px;border-radius:8px'>"
                    + otp + "</h1>"
                    + "<br><br>"
                    // + "<img src='https://tenor.googleapis.com/v2/media?id=18175356203862006295&format=optimizedgif&client_key=tenor_web&appversion=browser-r260203-1&access_token=ya29.A0AUMWg_JAb3zq1iQODjNWK9esrshNYAEK3cRRGnD1s7qwU5NAXF2WvmGHEj_HRQJV4FCn-sX7R9hFgV0UuLYRyx71_ZnLmcbRNYhGMwFH8w0IOW2SSsx5l-68haEitpl8dxXvic1FAFmzIW1DgLXoDG7oN_NFmbEU3nuQEkGIyYrTcyDrl0dtuSjIjV8Wexi4ReUj-e_3yhnD2753Qt480-Oqn2yjINFPvgBlsILHCdjlBQ8QBOX_K9oU3-XTxUAJWzqU0NU9eZpXQ2w2LzdZYeG1DqZsaCgYKATASARASFQHGX2Mi5rR6nU6NRQGphUNPA1q34w0291&key=AIzaSyC-P6_qz3FzCoXGLk6tgitZo4jEJ5mLzD8' width='200'/>"
                    + "<p style='color:gray'>Warehouse Management System</p>"
                    + "</div>";

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(html, "text/html; charset=UTF-8");

            // ===== IMAGE PART =====
            message.setContent(html, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("Email Sent Successfully");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
