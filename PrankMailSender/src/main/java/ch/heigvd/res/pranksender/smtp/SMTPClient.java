package ch.heigvd.res.pranksender.smtp;

import ch.heigvd.res.pranksender.model.mail.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class SMTPClient implements ISMTPClient {

    private String serverAdress;
    private int serverPort;

    public SMTPClient(String serverAdress, int serverPort) {
        this.serverAdress = serverAdress;
        this.serverPort = serverPort;
    }


    public void sendMessage(Message message) throws IOException {
        Socket socket = new Socket(serverAdress, serverPort);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

        String line = reader.readLine();
        writer.println("EHLO localhost\r\n");
        writer.flush();
        line = reader.readLine();
        if(!line.startsWith("250")){
            throw new IOException("SMTP error : " + line);
        }
        while(line.startsWith("250-")){
            line = reader.readLine();
        }
        writer.write("MAIL FROM: " + message.getFrom() + "\r\n");
        writer.flush();

        for(String to : message.getTo()){
            writer.write("RCPT TO: " + to + "\r\n");
            writer.flush();
            line = reader.readLine();
            if(!line.startsWith("250")){
                System.out.println("Error sending mail to " + to);
            }
        }

        for(String to : message.getCc()){
            writer.write("RCPT TO: " + to + "\r\n");
            writer.flush();
            line = reader.readLine();
            if(!line.startsWith("250")){
                System.out.println("Error sending mail to " + to);
            }
        }

        for(String to : message.getBcc()){
            writer.write("RCPT TO: " + to + "\r\n");
            writer.flush();
            line = reader.readLine();
            if(!line.startsWith("250")){
                System.out.println("Error sending mail to " + to);
            }
        }

        writer.write("DATA\r\n");
        writer.flush();

        writer.write("Content-Type: text/plain; charset=\"utf-8\"\r\n");
        writer.write("From: " + message.getFrom() + "\r\n");

        writer.write("To: " + message.getTo()[0] + "\r\n");
        for(int i = 0; i < message.getTo().length; ++i){
            writer.write(", " + message.getTo()[i]);
        }
        writer.write("\r\n");

        writer.write("Cc: " + message.getCc()[0]);
        for(int i = 0; i < message.getTo().length; ++i){
            writer.write(", " + message.getCc()[i]);
        }
        writer.write("\r\n");

        writer.write("Subject: " + message.getSubject() + "\r\n\n");

        writer.flush();

        writer.write(message.getBody());
        writer.write("\r\n.\r\n");

        writer.write("QUIT\r\n");
        
        socket.close();
        writer.close();
        reader.close();

    }
}
