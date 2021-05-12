package com.team32.ong.service;

import com.sendgrid.helpers.mail.Mail;

public interface EmailService {
    String sendEmail(String email);
    Mail prepareMail(String email);
}
