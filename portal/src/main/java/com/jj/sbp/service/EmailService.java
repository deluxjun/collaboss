package com.jj.sbp.service;

import com.jj.sbp.domain.Company;
import com.jj.sbp.domain.Verification;
import com.jj.sbp.error.AppException;
import com.jj.sbp.error.ErrorCode;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor    // inject dependency
@Component
@Slf4j
public class EmailService {

//    @Qualifier("getJavaMailSender")
    @Autowired
    private JavaMailSender emailSender;

    @Qualifier("freemarkerClassLoaderConfig")
    @Autowired
    private FreeMarkerConfigurer freemarkerConfigurer;

//    @Value("classpath:/mail-logo.png")
//    private Resource resourceFile;

    @Autowired
    @Qualifier("emailMessages")
    private ResourceBundleMessageSource emailMessageSource;

    @Value("${sbp.mail.sender}")
    private String noReplyAddress;

    @Value("${sbp.mail.request.notification:false}")
    private boolean notificationToSupporter;

    @Value("${sbp.mail.request.supporter}")
    private String[] supporters;

//    public void sendMessageWithAttachment(String to,
//                                          String subject,
//                                          String text,
//                                          String pathToAttachment) {
//        try {
//            MimeMessage message = emailSender.createMimeMessage();
//            // pass 'true' to the constructor to create a multipart message
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setFrom(noReplyAddress);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(text);
//
//            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
//            helper.addAttachment("Invoice", file);
//
//            emailSender.send(message);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }

    // mail to customer a verification code
    public void sendVerificationCode(String to, String code) {

        String subject = emailMessageSource.getMessage("code.subject",null,"", LocaleContextHolder.getLocale());
        String greetings = emailMessageSource.getMessage("code.greetings", null, LocaleContextHolder.getLocale());

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("subject", subject);
        templateModel.put("greetings", greetings);
        templateModel.put("code", code);
        templateModel.put("bottom1", emailMessageSource.getMessage("bottom1",null,"", LocaleContextHolder.getLocale()));
        templateModel.put("bottom2", emailMessageSource.getMessage("bottom2",null,"", LocaleContextHolder.getLocale()));

        String htmlBody = getBodyFromTemplate(
                Verification.VerificationType.CONFIRMATION.getMailTemplate(), templateModel);
        sendHtmlMessage(to, subject, htmlBody);
    }

    // mail to operator during just demo period
    public void sendApplicationMailToOperator(Company company) {

        // if mail list is empty, then exit
        if (false == notificationToSupporter || ObjectUtils.isEmpty(supporters)) {
            return;
        }

        Object[] params = {
                company.getCompanyName(),
                company.getAdminName(),
                company.getTel(),
                company.getAdminEmail(),
                company.getUsersLimit()
        };

        String subject = emailMessageSource.getMessage("support.request.subject",
                new Object[]{company.getCompanyName()},
                "You have a request", LocaleContextHolder.getLocale());
        String greeting = emailMessageSource.getMessage("support.request.greeting",
                null,"Company information", LocaleContextHolder.getLocale());
        String body = emailMessageSource.getMessage("support.request.body",
                params, LocaleContextHolder.getLocale());

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("greeting", greeting);
        templateModel.put("body", body);

        String htmlBody = getBodyFromTemplate("template-request-notification.ftl", templateModel);

        for (String supporter : supporters) {
            sendHtmlMessage(supporter, subject, htmlBody);
        }
    }

    private String getBodyFromTemplate(String mailTemplate, Map<String,Object> model) {
        Template freemarkerTemplate = null;
        String htmlBody = null;
        try {
            freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate(mailTemplate);
            htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, model);
        } catch (IOException|TemplateException e) {
            log.error(e.getMessage(), e);
            throw new AppException(ErrorCode.SENDING_EMAIL, e.getMessage());
        }

        return htmlBody;
    }


    private void sendHtmlMessage(String to, String subject, String htmlBody) {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(noReplyAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            // for attaching a image
//        helper.addInline("attachment.png", resourceFile);
            emailSender.send(message);

        } catch (MessagingException e) {
            throw new AppException(ErrorCode.SENDING_EMAIL, e.getMessage());
        }

    }
}