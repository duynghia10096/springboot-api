package com.DDN.login.controller;

import com.DDN.login.payload.request.EmailRequest;
import com.DDN.login.security.service.EmailSenderService;
import com.DDN.login.security.service.TableGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private TableGenerationService tableGenerationService;




    @PostMapping("/send-email")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
//        emailSenderService.sendSimpleEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
        return "Email sent Successfully";
    }


    @PostMapping("/send-html-email")
    public String sendHtmlEmail() {
        String htmlString = tableGenerationService.generateReportMessage();
        emailSenderService.sendEmailWithHtmlTemplate("Test Email", htmlString, "duynghia10099@gmail.com");

//        emailSenderService.sendEmailWithHtmlTemplate(emailRequest.getTo(), emailRequest.getSubject(), "email-template", context);
        return "HTML email sent successfully!";
    }
}
