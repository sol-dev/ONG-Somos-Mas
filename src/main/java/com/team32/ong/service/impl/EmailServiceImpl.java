package com.team32.ong.service.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.model.Contact;
import com.team32.ong.model.User;
import com.team32.ong.repository.ContactRepository;
import com.team32.ong.repository.UserRepository;
import com.team32.ong.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    @Autowired
    SendGrid sendGrid;
    
    @Value("${app.sengrid.emailIssuing}")
    private String emailIssuing;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private Configuration config;

    @Override
    public void sendEmail(String email, String template) throws IOException{
        try {
        	Mail mail = prepareMail(email, template);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");

            request.setBody(mail.build());
            
           sendGrid.api(request);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            throw new IOException(ConstantExceptionMessage.MSG_ERROR_SEND_EMAIL);
        }
    }

    public Mail prepareMail(String email, String template) throws TemplateException, IOException{
        Mail mail = new Mail();

        Email fromEmail = new Email();
        Content content = new Content();
        content.setType("text/html");
        if (template.equals(WELCOME)){
            content.setValue(prepareWelcomeTemplate(email));
        }else if (template.equals(CONTACT)){
            content.setValue(prepareContactTemplate(email));
        }else {
            throw new IOException(ConstantExceptionMessage.MSG_ERROR_TEMPLATE_INVALID);
        }


        fromEmail.setEmail(emailIssuing);
        mail.setFrom(fromEmail);

        Email toEmail = new Email();
        toEmail.setEmail(email);

        Personalization personalization = new Personalization();
        personalization.addTo(toEmail);

        mail.addContent(content);
        mail.setSubject("¡Somos Mas!");
        mail.addPersonalization(personalization);
        return mail;
    }

    public String prepareWelcomeTemplate(String email) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();

        User user = userRepository.findByEmail(email);

        model.put("title", "¡Bienvenido a Somos Mas!");
        model.put("firstName", user.getFirstName());
        model.put("lastName", user.getLastName());

        return FreeMarkerTemplateUtils.processTemplateIntoString(config.getTemplate("plantilla_email.html"), model);
    }

    public String prepareContactTemplate(String email) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();

        Contact user = contactRepository.findByEmail(email);

        model.put("title", "El contacto ha sido exitoso");
        model.put("firstName", user.getName());
        model.put("lastName", user.getEmail());

        return FreeMarkerTemplateUtils.processTemplateIntoString(config.getTemplate("plantilla_email.html"), model);
    }
}
