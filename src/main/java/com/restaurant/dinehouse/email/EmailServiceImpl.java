package com.restaurant.dinehouse.email;

import java.io.File;
import java.util.Calendar;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.restaurant.dinehouse.repository.OrderItemRepository;
import com.restaurant.dinehouse.repository.OrderRepository;
import com.restaurant.dinehouse.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;

    public String sendSimpleMail(EmailDetails details) {
        try {
            populateEmailMessage(details);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"utf-8");

            mimeMessageHelper.setFrom("dinehouse.ap@gmail.com");
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody(), true);

            mimeMessageHelper.setSubject(details.getSubject().concat("---").concat(Calendar.getInstance().getTime().toString()));
            javaMailSender.send(mimeMessage);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            log.error("failed at sending simple mail {}", e);
            return "Error while Sending Mail";
        }
    }

    private void populateEmailMessage(EmailDetails emailDetails) {
        StringBuilder messageBuilder = new StringBuilder("");
        messageBuilder.append("<html><h3>Item Sales report</h3>")
                .append("<style>table, th, td {border:1px solid black;}</style>")
                .append("<table>")
                .append("<tr>")
                .append("<th>Name</th>")
                .append("<th>Type</th>")
                .append("<th>Status</th>")
                .append("<th>Qty</th>")
                .append("<th>Amount</th>")
                .append("</tr>");

        orderItemRepository.findByGroupByItems().stream().forEach(dailyAggregateItems -> {
            messageBuilder.append("<tr>")
                    .append("<td>").append(dailyAggregateItems.getName()).append("</td>")
                    .append("<td>").append(dailyAggregateItems.getType()).append("</td>")
                    .append("<td>").append(dailyAggregateItems.getStatus()).append("</td>")
                    .append("<td>").append(dailyAggregateItems.getQuantity()).append("</td>")
                    .append("<td>").append(dailyAggregateItems.getAmount()).append("</td>")
                    .append("</tr>");
        });

        messageBuilder.append("</table></html>");
        log.info("message body {}", messageBuilder);
        emailDetails.setMsgBody(messageBuilder.toString());
    }

    public String sendMailWithAttachment(EmailDetails details) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {

            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("dinehouse.ap@gmail.com");
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
            mimeMessageHelper.addAttachment(file.getFilename(), file);

            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        } catch (MessagingException e) {
            log.info("failed to send an email with attachment {}", e);
            return "Error while sending mail!!!";
        }
    }
}