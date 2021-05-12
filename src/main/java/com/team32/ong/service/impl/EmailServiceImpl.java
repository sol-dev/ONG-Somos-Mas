package com.team32.ong.service.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.team32.ong.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    @Value("${app.sengrid.templateId}")
    private String templateId;
    @Autowired
    SendGrid sendGrid;

    @Override
    public String sendEmail(String email) {
        try {
            Mail mail = prepareMail(email);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");


            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            if(response!= null){
                System.out.println("response code from sendgrid" + response.getHeaders());
                System.out.println(response.getStatusCode());
                System.out.println(response.getBody());
            }
        } catch (IOException e) {
            System.out.println("catch");
            e.printStackTrace();
            return "Error in sent email";
        }

        return "mail has been sent check your inbox";
    }

    public Mail prepareMail(String email){
        Mail mail = new Mail();

        Email fromEmail = new Email();
        fromEmail.setEmail("matias-leandro@outlook.com");
        mail.setFrom(fromEmail);

        Email toEmail = new Email();
        toEmail.setEmail(email);

        Personalization personalization = new Personalization();
        personalization.addTo(toEmail);


        mail.setTemplateId(templateId);
        mail.addPersonalization(personalization);
        return mail;
    }
}
