package com.ads.enade.utils.impl;

import com.ads.enade.utils.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendResetPasswordEmail(String email, String resetLink) {
        System.out.println("Enviando email para: "+email);
        System.out.println("Link de reset: "+resetLink);
    }
}
