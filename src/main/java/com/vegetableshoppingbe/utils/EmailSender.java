package com.vegetableshoppingbe.utils;

import com.vegetableshoppingbe.mail.MailInfo;
import jakarta.mail.MessagingException;

public interface EmailSender {
    void sendEmail(MailInfo email) throws MessagingException;

}
