package com.team32.ong.service.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    @Autowired
    SendGrid sendGrid;

    @Override
    public void sendEmail(String email) throws IOException{
        try {
            Mail mail = new Mail();
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
}
