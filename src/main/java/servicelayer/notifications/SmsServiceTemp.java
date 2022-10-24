package servicelayer.notifications;

import dto.SmsMessage;

public class SmsServiceTemp implements SmsService {
    @Override
    public boolean sendSms(SmsMessage message) {
        return false;
    }
}
