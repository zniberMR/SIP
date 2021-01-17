package ma.ac.ehtp.sip.config;

import ma.ac.ehtp.sip.entities.Administrateur;
import ma.ac.ehtp.sip.entities.Utilisateur;

import javax.ejb.Stateless;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Properties;

@Stateless
public class Mailer {
    public static final String ATT_CONTEXT_UTILISATEURS                 = "contextUtilisateurs";

    public void send(HttpServletRequest request, String subject, String msg){
        final String user     = "ehtpsip@gmail.com"; // "admin@sipapp.com";
        final String password = "Admin2020@";        // "admin";

        // Get the session object
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // localhost
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        // Compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            ServletContext servletContext = request.getServletContext();
            Map<Long, Utilisateur> mapUtilisateurs = (Map<Long, Utilisateur>)servletContext.getAttribute(ATT_CONTEXT_UTILISATEURS);
            Utilisateur utilisateur = null;
            for(Map.Entry<Long, Utilisateur> entry : mapUtilisateurs.entrySet()) {
                utilisateur = entry.getValue();
                if(utilisateur instanceof Administrateur) {
                    if (utilisateur.getUsername().equals("admin"))
                        continue;
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(utilisateur.getEmail()));
                }
            }
            message.setSubject(subject);
            message.setText(msg);

            // Send message
            Transport.send(message);

        } catch (MessagingException e){
            e.printStackTrace();
        }
    }

    public void send(HttpServletRequest request, String subject, String msg, Utilisateur to){
        final String user     = "ehtpsip@gmail.com"; // "admin@sipapp.com";
        final String password = "Admin2020@";        // "admin";

        // Get the session object
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // localhost
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        // Compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            ServletContext servletContext = request.getServletContext();
            Map<Long, Utilisateur> mapUtilisateurs = (Map<Long, Utilisateur>)servletContext.getAttribute(ATT_CONTEXT_UTILISATEURS);
            Utilisateur utilisateur = null;
            for(Map.Entry<Long, Utilisateur> entry : mapUtilisateurs.entrySet()) {
                utilisateur = entry.getValue();
                if(utilisateur instanceof Administrateur){
                    if(utilisateur.getUsername().equals("admin"))
                        continue;
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(utilisateur.getEmail()));
                }
            }
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to.getEmail()));
            message.setSubject(subject);
            message.setText(msg);

            // Send message
            Transport.send(message);

        } catch (MessagingException e){
            e.printStackTrace();
        }
    }
}
