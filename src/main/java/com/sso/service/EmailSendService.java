package com.sso.service;

import com.sso.model.EmailDetails;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import java.time.ZonedDateTime;

public interface EmailSendService {
    String sendSimpleMail(EmailDetails emailDetails);
    String sendMailWithAttachment(EmailDetails emailDetails);
    JobDetail buildJobDetail(EmailDetails emailDetails);
    Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt);
}
