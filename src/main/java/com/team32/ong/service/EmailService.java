package com.team32.ong.service;

import com.sendgrid.helpers.mail.Mail;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface EmailService {
    void sendEmail(String email) throws IOException;
    Mail prepareMail(String email) throws TemplateException, IOException;
}
