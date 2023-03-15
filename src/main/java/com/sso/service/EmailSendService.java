package com.sso.service;

import com.sso.model.EmailDetails;

public interface EmailSendService {
    String sendSimpleMail(EmailDetails emailDetails);
    String sendMailWithAttachment(EmailDetails emailDetails);
}
