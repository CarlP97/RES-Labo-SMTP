package ch.heigvd.res.pranksender;

import ch.heigvd.res.pranksender.config.ConfigurationManager;
import ch.heigvd.res.pranksender.config.IConfigurationManager;
import ch.heigvd.res.pranksender.model.prank.Prank;
import ch.heigvd.res.pranksender.model.prank.PrankGenerator;
import ch.heigvd.res.pranksender.smtp.SMTPClient;

import java.io.IOException;

public class PrankSender {

    public static void main(String[] args) throws IOException {
        ConfigurationManager configurationManager = null;
        try {
            configurationManager = new ConfigurationManager();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrankGenerator prankGenerator = new PrankGenerator(configurationManager);
        SMTPClient smtpClient = new SMTPClient(configurationManager.getSmtpServerAddress(),
                configurationManager.getSmtpServerPort());

        for(Prank prank : prankGenerator.generatePrank()){
            smtpClient.sendMessage(prank.generateMessage());
        }

    }



}
