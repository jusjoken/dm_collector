package ca.admin.delivermore.collector.config;

import ca.admin.delivermore.collector.data.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig
{
    @Bean
    public SimpleMailMessage emailTemplate()
    {
        //TODO:: this has not been tested and will need some adjustments if used
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("support@delivermore.ca");
        message.setFrom("tara.birch@delivermore.ca");
        message.setSubject("Important email");
        message.setText("FATAL - Application crash. Save your job !!");
        return message;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();

        Properties p = new Properties();
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.host", "smtp.office365.com");
        p.setProperty("mail.smtp.port", "587");
        p.setProperty("mail.protocol", "smtp");
        p.setProperty("mail.tls", "true");
        p.setProperty("mail.smtp.starttls.enable", "true");
        p.setProperty("mail.smtp.ssl.trust", "smtp.office365.com");

        sender.setUsername("tara.birch@delivermore.ca");
        sender.setPassword(""); //TODO: move password to ENV

        Config.getInstance().setFromEmail("tara.birch@delivermore.ca");

        sender.setJavaMailProperties(p);

        return sender;
    }
}