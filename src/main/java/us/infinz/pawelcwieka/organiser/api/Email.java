package us.infinz.pawelcwieka.organiser.api;

import us.infinz.pawelcwieka.organiser.service.MessageWindowProvider;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Email {

    private String to;
    private final String from = "organiser@us.infinz.pawelcwieka.pl";
    private String subject;
    private String eventName;
    private String messageContent;
    private InputStream inputStreamAttachment;

    public Email(String to, String subject, String eventName, String messageContent, InputStream inputStreamAttachment) {
        this.to = to;
        this.subject = subject;
        this.eventName = eventName;
        this.messageContent = messageContent;
        this.inputStreamAttachment = inputStreamAttachment;
    }

    public boolean send(){


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication  getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "inzinf2015@gmail.com", "Infa1dwa34");
            }
        });

        try {

            Message message = new MimeMessage(session);


            message.setFrom(new InternetAddress(from));


            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();


            messageBodyPart.setText(messageContent);

            Multipart multipart = new MimeMultipart();


            multipart.addBodyPart(messageBodyPart);


            messageBodyPart = new MimeBodyPart();
            String filename = eventName;
            ByteArrayDataSource ds = new ByteArrayDataSource(inputStreamAttachment, "text/calendar");
            messageBodyPart.setDataHandler(new DataHandler(ds));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

                    "Email został wysłany pomyślnie",
                    "Wydarzenie zostało wysłane na adres: " + to + ", możesz je teraz dodać do Google Calendar."
            );

            messageWindowProvider.showMessageWindow();

            System.out.println("Sent message successfully....");

            return true;

        } catch (IOException e){

            throw new RuntimeException(e);


        } catch (MessagingException e) {

            MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

                    "Nie można połączyć z smtp.gmail.com",
                    "Sprawdź połączenie sieciowe."
            );

            messageWindowProvider.showMessageWindow();

            e.printStackTrace();
        }

        return false;

    }

}
