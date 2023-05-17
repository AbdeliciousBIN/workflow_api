package com.i2s.worfklow_api_final.controller;

import com.i2s.worfklow_api_final.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/actions")
public class ActionController {
    private final ActionService actionService;

    @Autowired
    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestParam String sender, @RequestParam String receiver, @RequestParam String subject, @RequestParam String content) {
        try {
            actionService.sendEmail(sender, receiver, subject, content);
            return ResponseEntity.ok("Email sent successfully");
        } catch (MailException e) {
            // handle email sending error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email sending error occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // handle invalid input data error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");
        } catch (Exception e) {
            // handle any other unexpected error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @PostMapping("/sendSms")
    public ResponseEntity<?> sendSms(@RequestParam String from, @RequestParam String to, @RequestParam String text) {
        try {
            actionService.sendSms(from, to, text);
            return ResponseEntity.ok("SMS sent successfully ");
        } catch (MailException e) {
            // handle email sending error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SMS sending error occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // handle invalid input data error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");
        } catch (Exception e) {
            // handle any other unexpected error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }
}



