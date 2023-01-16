package com.victor.microserviciousuarios.models.util;



import com.victor.microserviciousuarios.models.response.GenericResponse;
import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger logger = LogManager.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void enviarEmail(String emailTo, String subject, String text, String bodyHtml) throws Exception {
        logger.info("*****Enviando email*****");
        String fromEmail = "safetraveldev@gmail.com";
        String password = "safetravel123";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enale", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            if(bodyHtml != null || bodyHtml.isEmpty()) {
                messageBodyPart.setContent(bodyHtml, "text/html; charset=utf-8");

            } else {
                message.setText(text);
            }
            Multipart multipart = new MimeMultipart();
            message.setContent(multipart);
            Transport.send(message);
            logger.info("*****Email enviado*****");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void enviarEmail(String emailTo, String subject, String text, String bodyHtml, List<String> fileNames,
                                        List<File> listFiles) throws Exception {
        logger.info("*****Enviando email*****");
        String fromEmail = "safetraveldev@gmail.com";
        String password = "ngcwiqmdagkgrbqc";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();

            if(bodyHtml != null) {
                messageBodyPart.setContent(bodyHtml, "text/html; charset=utf-8");
            } else {
                messageBodyPart.setText(text);
            }
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if(listFiles != null && listFiles.size() > 0) {
                int index = 0;
                for(File file : listFiles) {
                    DataSource source = new FileDataSource(file);
                    messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(fileNames.get(index));
                    multipart.addBodyPart(messageBodyPart);
                    index = index + 1;
                }
            }
            message.setContent(multipart);
            Transport.send(message);
            logger.info("*****Email enviado*****");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void enviarEmail2(String emailTo, String subject, String text, String bodyHtml, List<String> fileNames,
                             List<File> listFiles) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            logger.info("*****Enviando email*****");
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(emailTo);
            mimeMessageHelper.setSubject(subject);
            if(bodyHtml != null) {
                mimeMessageHelper.setText(bodyHtml, "text/html; charset=utf-8");
            } else {
                mimeMessageHelper.setText(text);
            }
            if(listFiles != null && listFiles.size() > 0) {
                int index = 0;
                for(File file : listFiles) {
                    FileSystemResource attachment = new FileSystemResource(file);
                    mimeMessageHelper.addAttachment(fileNames.get(index), file);
                    index = index + 1;
                }
            }
            javaMailSender.send(mimeMessage);
            logger.info("*****Email enviado*****");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public GenericResponse<Object> enviarEmail3(String emailTo, String subject, String text, String bodyHtml, List<String> fileNames,
                                        List<ByteArrayResource> listFiles) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            logger.info("*****Enviando email*****");
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(emailTo);
            mimeMessageHelper.setSubject(subject);
            if(bodyHtml != null) {
                mimeMessageHelper.setText(bodyHtml, "text/html; charset=utf-8");
            } else {
                mimeMessageHelper.setText(text);
            }
            if(listFiles != null && listFiles.size() > 0) {
                int index = 0;
                for(ByteArrayResource file : listFiles) {
                    ByteArrayOutputStream baos = setearPasswordArchivo(file);
                    DataSource source = new ByteArrayDataSource(baos.toByteArray(),"application/octet-stream");
                    mimeMessageHelper.addAttachment(fileNames.get(index), source);
                    index = index + 1;
                }
            }
            javaMailSender.send(mimeMessage);
            logger.info("*****Email enviado*****");
            return new GenericResponse<>(true, "Email enviado");

        } catch (Exception e) {
            e.printStackTrace();
            return new GenericResponse<>(false, "Error en el proceso de envio de email.");
        }

    }

    public ByteArrayOutputStream setearPasswordArchivo(ByteArrayResource fileBar) throws Exception {
        ByteArrayOutputStream os = null;
        try {
            PDDocument pdd = PDDocument.load(fileBar.getByteArray());
            AccessPermission ap = new AccessPermission();

            // step 3. Creating instance of
            // StandardProtectionPolicy
            StandardProtectionPolicy stpp  = new StandardProtectionPolicy("123456", "123456", ap);

            // step 4. Setting the length of Encryption key
            stpp.setEncryptionKeyLength(128);

            // step 5. Setting the permission
            stpp.setPermissions(ap);

            // step 6. Protecting the PDF file
            pdd.protect(stpp);
            os = new ByteArrayOutputStream();
            pdd.save(os);
            return os;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

        }
    }


}
