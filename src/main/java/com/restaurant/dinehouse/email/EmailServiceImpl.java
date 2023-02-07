package com.restaurant.dinehouse.email;

import java.io.File;
import java.util.Calendar;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.restaurant.dinehouse.repository.OrderItemRepository;
import com.restaurant.dinehouse.repository.OrderRepository;
import com.restaurant.dinehouse.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailServiceImpl(JavaMailSender javaMailSender,
                            OrderItemRepository orderItemRepository,
                            OrderRepository orderRepository,
                            TransactionRepository transactionRepository) {

        this.javaMailSender = javaMailSender;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
    }

    public String sendSimpleMail(EmailDetails details) {
        try {
            populateEmailMessage(details);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

            mimeMessageHelper.setFrom(fromEmail);
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
        StringBuilder messageBuilder = new StringBuilder("<html>");
        messageBuilder.append(getSalesReport());
        messageBuilder.append(getStaffPerformanceReport());
        messageBuilder.append(getTransactionsReport());
        messageBuilder.append(" <br/> <span>************This is System Generated Message************!</span></html>");
        log.info("message body {}", messageBuilder);
        emailDetails.setMsgBody(messageBuilder.toString());
    }

    private String getSalesReport() {
        StringBuilder messageBuilder = new StringBuilder("");
        messageBuilder.append("<h3>Sales report</h3>")
                .append("<head><style>table, td, th {border: 1px solid;}table {width: 100%;border-collapse: collapse;padding-top: 12px;}th {background-color: #04AA6D;color: white;}</style></head>")
                .append("<table>")
                .append("<tr>")
                .append("<th>Name</th>")
                .append("<th>Type</th>")
                .append("<th>Status</th>")
                .append("<th>Qty</th>")
                .append("<th>Amount</th>")
                .append("</tr>");

        orderItemRepository.findByGroupByItems().stream().forEach(dailyAggregateItem -> messageBuilder.append("<tr>")
                .append("<td>").append(dailyAggregateItem.getName()).append("</td>")
                .append("<td>").append(dailyAggregateItem.getType()).append("</td>")
                .append("<td>").append(dailyAggregateItem.getStatus()).append("</td>")
                .append("<td>").append(dailyAggregateItem.getQuantity()).append("</td>")
                .append("<td>").append(dailyAggregateItem.getAmount()).append("</td>")
                .append("</tr>"));
        messageBuilder.append("</table><br/><br/>");
        return messageBuilder.toString();
    }

    private String getStaffPerformanceReport() {
        StringBuilder messageBuilder = new StringBuilder("");
        messageBuilder.append("<h3>Staff performance</h3>");
        messageBuilder.append("<table>")
                .append("<tr>")
                .append("<th>Created By</th>")
                .append("<th>Served By</th>")
                .append("<th>Order Type</th>")
                .append("<th>Payment Method</th>")
                .append("<th>Status</th>")
                .append("<th>Number of Orders</th>")
                .append("<th>Amount</th></tr>");

        orderRepository.findByGroupByOrders().stream().forEach(dailyAggregateOrder ->
                messageBuilder.append("<tr><td>").append(dailyAggregateOrder.getUserId()).append("</td>")
                        .append("<td>").append(dailyAggregateOrder.getServedBy()).append("</td>")
                        .append("<td>").append(dailyAggregateOrder.getType()).append("</td>")
                        .append("<td>").append(dailyAggregateOrder.getPaymentMethod()).append("</td>")
                        .append("<td>").append(dailyAggregateOrder.getStatus()).append("</td>")
                        .append("<td>").append(dailyAggregateOrder.getQuantity()).append("</td>")
                        .append("<td>").append(dailyAggregateOrder.getAmount()).append("</td></tr>"));

        messageBuilder.append("</table><br/><br/>");
        return messageBuilder.toString();
    }

    private String getTransactionsReport() {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("<h3>Inbound/Outbound Transactions</h3>");
        messageBuilder.append("<table>")
                .append("<tr>")
                .append("<th>Recorded By</th>")
                .append("<th>Group</th>")
                .append("<th>Type</th>")
                .append("<th>Payment Method</th>")
                .append("<th>Amount</th>");

        transactionRepository.findByGroupByTransactions().stream().forEach(transaction ->
                messageBuilder.append("<tr><td>").append(transaction.getUserId()).append("</td>")
                        .append("<td>").append(transaction.getTranGroup()).append("</td>")
                        .append("<td>").append(transaction.getType()).append("</td>")
                        .append("<td>").append(transaction.getPaymentMethod()).append("</td>")
                        .append("<td>").append(transaction.getAmount()).append("</td></tr>"));
        messageBuilder.append("</table>");
        return messageBuilder.toString();
    }

    public String sendMailWithAttachment(EmailDetails details) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromEmail);
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