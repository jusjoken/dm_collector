package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.config.TaskProcessor;
import ca.admin.delivermore.collector.data.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service("emailService")
public class EmailService
{
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage preConfiguredMessage;

    private Logger log = LoggerFactory.getLogger(EmailService.class);

    /**
     * This method will send compose and send the message
     * */
    public void sendMail(String to, String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        String from = Config.getInstance().getFromEmail();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(from);
        mailSender.send(message);
    }

    /**
     * This method will send a pre-configured message
     * */
    public void sendPreConfiguredMail(String message)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    public void sendMailWithAttachment(String to, String subject, String body, String fileToAttach, String attachmentName)
    {
        sendMailWithAttachment(to,subject,body,new File(fileToAttach), attachmentName);
    }

    public void sendMailWithAttachment(String to, String subject, String body, File fileToAttach, String attachmentName)
    {
        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {
            public void prepare(MimeMessage mimeMessage) throws Exception
            {
                String from = Config.getInstance().getFromEmail();

                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                mimeMessage.setFrom(new InternetAddress(from));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(body);

                FileSystemResource file = new FileSystemResource(fileToAttach);
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.addAttachment(attachmentName, file);
                helper.setText("", true);
            }
        };

        try {
            mailSender.send(preparator);
        }
        catch (MailException ex) {
            // simply log it and go on...
            log.error(ex.getMessage());
        }
    }

    public void sendMailWithInlineResources(String from, String to, String subject, String fileToAttach)
    {
        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {
            public void prepare(MimeMessage mimeMessage) throws Exception
            {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setFrom(new InternetAddress(from));
                mimeMessage.setSubject(subject);

                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setText("<html><body><img src='cid:identifier1234'></body></html>", true);

                FileSystemResource res = new FileSystemResource(new File(fileToAttach));
                helper.addInline("identifier1234", res);
            }
        };

        try {
            mailSender.send(preparator);
        }
        catch (MailException ex) {
            // simply log it and go on...
            log.error(ex.getMessage());
        }
    }
}