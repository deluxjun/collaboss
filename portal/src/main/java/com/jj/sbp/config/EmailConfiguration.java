package com.jj.sbp.config;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

//@PropertySource(value={"classpath:application.properties"})
@org.springframework.context.annotation.Configuration
public class EmailConfiguration {

//    @Value("${spring.mail.host}")
//    private String mailServerHost;
//
//    @Value("${spring.mail.port}")
//    private Integer mailServerPort;
//
//    @Value("${spring.mail.username}")
//    private String mailServerUsername;
//
//    @Value("${spring.mail.password}")
//    private String mailServerPassword;
//
//    @Value("${spring.mail.protocol:smtp}")
//    private String mailProtocol;
//
//    @Value("${spring.mail.properties.mail.auth:false}")
//    private String mailServerAuth;
//
//    @Value("${spring.mail.properties.mail.starttls.enable:false}")
//    private String mailServerStartTls;
//
//    @Value("${spring.mail.timeout:20000}")
//    private Integer mailTimeout;
//
    @Value("${spring.mail.templates.path}")
    private String mailTemplatesPath;
//
//    @Value("${spring.mail.debug:false}")
//    private String mailDebug;
//
//    @Bean
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//
//        mailSender.setHost(mailServerHost);
//        mailSender.setPort(mailServerPort);
//
//        mailSender.setUsername(mailServerUsername);
//        mailSender.setPassword(mailServerPassword);
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", mailProtocol);
//        props.put("mail."+mailProtocol+".auth", mailServerAuth);
//        props.put("mail."+mailProtocol+".starttls.enable", mailServerStartTls);
//        props.put("mail."+mailProtocol+".timeout", mailTimeout);
//        props.put("mail.debug", mailDebug);
//
//        return mailSender;
//    }
    
    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("This is the test email template for your email:\n%s\n");
        return message;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerClassLoaderConfig() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
        TemplateLoader templateLoader = new ClassTemplateLoader(this.getClass(), "/" + mailTemplatesPath);
        configuration.setTemplateLoader(templateLoader);
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setConfiguration(configuration);
        return freeMarkerConfigurer;
    }

//    @Bean
//    public FreeMarkerConfigurer freemarkerFilesystemConfig() throws IOException {
//        Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
//        TemplateLoader templateLoader = new FileTemplateLoader(new File(mailTemplatesPath));
//        configuration.setTemplateLoader(templateLoader);
//        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
//        freeMarkerConfigurer.setConfiguration(configuration);
//        return freeMarkerConfigurer;
//    }

    // localization
    @Bean("emailMessages")
    public ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("lang/mailMessages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}