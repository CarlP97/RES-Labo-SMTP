package ch.heigvd.res.pranksender.smtp;

import ch.heigvd.res.pranksender.model.mail.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SMTPClient implements ISMTPClient {

    private String serverAdress;
    private int serverPort;

    public SMTPClient(String serverAdress, int serverPort) {
        this.serverAdress = serverAdress;
        this.serverPort = serverPort;
    }

    /**
     * Creates a connection on the address and port, writes and sends the message
     * @param message the message sent
     * @throws IOException
     */
    public void sendMessage(Message message) throws IOException {
        Socket socket = new Socket(serverAdress, serverPort);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

        String line = reader.readLine();
        writer.write("EHLO localhost\r\n");
        writer.flush();
        line = reader.readLine();
        if(!line.startsWith("250")){
            throw new IOException("SMTP error : " + line);
        }
        while(line.startsWith("250-")){
            line = reader.readLine();
        }

        writer.write("MAIL FROM: ");
        writer.write(message.getFrom());
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();

        for(String to : message.getTo()){
            writer.write("RCPT TO: ");
            writer.write(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            if(!line.startsWith("250")){
                System.out.println("Error sending mail to " + to);
            }
        }

        if(message.getCc() != null) {
            for (String to : message.getCc()) {
                writer.write("RCPT TO: " + to + "\r\n");
                writer.flush();
                line = reader.readLine();
                if (!line.startsWith("250")) {
                    System.out.println("Error sending mail to " + to);
                }
            }
        }

        if(message.getBcc() != null){
            for(String to : message.getBcc()){
                writer.write("RCPT TO: " + to + "\r\n");
                writer.flush();
                line = reader.readLine();
                if(!line.startsWith("250")){
                    System.out.println("Error sending mail to " + to);
                }
            }
        }


        writer.write("DATA\r\n");
        writer.flush();
        line = reader.readLine();
        writer.write("Content-Type: text/plain; charset=\"utf-8\"\r\n");
        writer.write("From: " + message.getFrom() + "\r\n");

        writer.write("To: " + message.getTo()[0]);
        for(int i = 1; i < message.getTo().length; ++i){
            writer.write(", " + message.getTo()[i]);
        }
        writer.write("\r\n");

        writer.write("Cc: " + message.getCc()[0]);
        for(int i = 1; i < message.getCc().length; ++i){
            writer.write(", " + message.getCc()[i]);
        }
        writer.write("\r\n");

        writer.write("Subject: =?UTF-8?B?" + Base64.getEncoder().encodeToString(message.getSubject().getBytes(StandardCharsets.UTF_8)) + "?=\r\n");

        writer.flush();

        writer.write(message.getBody());
        writer.flush();
        writer.write("\r\n.\r\n");
        writer.flush();
        line = reader.readLine();
        if(line.startsWith("250")){
            System.out.println("Mail sent from : " + message.getFrom());
        }else{
            System.out.println("Error sending mail from : " + message.getFrom());
        }
        writer.write("QUIT\r\n");
        socket.close();
        writer.close();
        reader.close();

    }
}
