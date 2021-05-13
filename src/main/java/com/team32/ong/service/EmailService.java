package com.team32.ong.service;

import com.sendgrid.helpers.mail.Mail;

import java.io.IOException;

public interface EmailService {
    void sendEmail(String email) throws IOException;
    Mail prepareMail(String email);
}
