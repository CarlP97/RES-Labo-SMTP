package ch.heigvd.res.pranksender.smtp;

import ch.heigvd.res.pranksender.model.mail.Message;

import java.io.IOException;

public interface ISMTPClient {
    void sendMessage(Message message) throws IOException;
}
