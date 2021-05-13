package com.team32.ong.service.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    @Autowired
    SendGrid sendGrid;
    
    @Value("${app.sengrid.emailIssuing}")
    private String emailIssuing;

    @Override
    public void sendEmail(String email) throws IOException{
        try {
        	Mail mail = prepareMail(email);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");

            request.setBody(mail.build());
            
           sendGrid.api(request);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(ConstantExceptionMessage.MSG_ERROR_SEND_EMAIL);
        }
    }

    public Mail prepareMail(String email){
        Mail mail = new Mail();

        Email fromEmail = new Email();

        fromEmail.setEmail(emailIssuing);
        mail.setFrom(fromEmail);

        Email toEmail = new Email();
        toEmail.setEmail(email);

        Personalization personalization = new Personalization();
        personalization.addTo(toEmail);


        mail.setSubject("test");
        mail.addPersonalization(personalization);
        return mail;
    }
}
