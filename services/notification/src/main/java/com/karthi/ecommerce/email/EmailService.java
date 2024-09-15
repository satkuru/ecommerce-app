package com.karthi.ecommerce.email;

import com.karthi.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.karthi.ecommerce.email.EmailTemplates.ORDER_CONFIRMATION;
import static com.karthi.ecommerce.email.EmailTemplates.PAYMENT_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
        messageHelper.setFrom("satkuru.karthigeyan@gmail.com");
        final String templateName = PAYMENT_CONFIRMATION.getTemplate();
        Map<String,Object> emailProperties = new HashMap<>();
        emailProperties.put("customerName",customerName);
        emailProperties.put("amount",amount);
        emailProperties.put("orderReference",orderReference);

        Context context = new Context();
        context.setVariables(emailProperties);
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        String htmlMessage = templateEngine.process(templateName, context);
        messageHelper.setText(htmlMessage,true);
        messageHelper.setTo(destinationEmail);
        emailSender.send(mimeMessage);
        log.info("Payment confirmation email was send to {}",customerName);
    }

    @Async
    public void sendOrderConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
        messageHelper.setFrom("satkuru.karthigeyan@gmail.com");
        final String templateName = ORDER_CONFIRMATION.getTemplate();
        Map<String,Object> emailProperties = new HashMap<>();
        emailProperties.put("customerName",customerName);
        emailProperties.put("totalAmount",amount);
        emailProperties.put("orderReference",orderReference);
        emailProperties.put("products",products);

        Context context = new Context();
        context.setVariables(emailProperties);
        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        String htmlMessage = templateEngine.process(templateName, context);
        messageHelper.setText(htmlMessage,true);
        messageHelper.setTo(destinationEmail);
        emailSender.send(mimeMessage);
        log.info("Order confirmation email was send to {}",customerName);
    }

}
