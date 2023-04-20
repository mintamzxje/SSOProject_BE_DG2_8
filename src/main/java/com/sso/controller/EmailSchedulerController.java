package com.sso.controller;

import com.sso.factory.email.ScheduleEmailResponse;
import com.sso.model.EmailDetails;
import com.sso.service.EmailDetailService;
import com.sso.service.EmailSendService;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.ZonedDateTime;


@RestController
@RequestMapping("/api/email")
public class EmailSchedulerController {
    private static final Logger logger = LoggerFactory.getLogger(EmailSchedulerController.class);
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private EmailSendService emailSendService;
    @Autowired
    private EmailDetailService emailDetailService;

    @PostMapping("/schedule")
    public ResponseEntity<?> scheduleEmail(@Valid @RequestBody EmailDetails emailDetails) {
        try {
            ZonedDateTime dateTime = ZonedDateTime.of(emailDetails.getDateTime(), emailDetails.getTimeZone());
            if(dateTime.isBefore(ZonedDateTime.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ScheduleEmailResponse(false,HttpStatus.BAD_REQUEST,
                                "dateTime must be after current time",emailDetails)
                );
            }
            JobDetail jobDetail = emailSendService.buildJobDetail(emailDetails);
            Trigger trigger = emailSendService.buildJobTrigger(jobDetail, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ScheduleEmailResponse(true,HttpStatus.OK,"null",
                            emailDetailService.addEmail(emailDetails))
            );
        } catch (SchedulerException ex) {
            logger.error("Error scheduling email", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
