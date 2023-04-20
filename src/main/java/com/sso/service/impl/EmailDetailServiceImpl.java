package com.sso.service.impl;

import com.sso.exception.DuplicateRecordException;
import com.sso.model.EmailDetails;
import com.sso.repository.EmailDetailRepository;
import com.sso.service.EmailDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailDetailServiceImpl implements EmailDetailService {
    @Autowired
    private EmailDetailRepository emailDetailRepository;
    @Override
    public EmailDetails addEmail(EmailDetails emailDetail) {
        if(emailDetail != null) {
        return emailDetailRepository.save(emailDetail);
        } else {
            throw new DuplicateRecordException("Email is empty");
        }
    }
}
