package edu.hawaii.jabsom.tri.ecmo.app.report;

import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * Sends out email messages through a SMTP server. Requires the following JAR files:
 * <ul>
 *   <li>mail.jar
 *   <li>activation.jar
 * </ul>
 * 
 * @author king
 * @since July 1, 2009
 */
public final class Mailer {

  /** The available recipient types. */
  public enum RecipientType { TO, CC, BCC };
  
  
  /**
   * The constructor is private to prevent instantiation.
   */
  private Mailer() {
    // not used
  }
  
  /** 
   * Sends out a single email message to a single recipient.
   * 
   * @param senderEmail  The sender's email address.
   * @param senderName  The sender's name.
   * @param recipientEmail  The recipient's email address.
   * @param recipientName  The recipient's name.
   * @param subject  The message's subject.
   * @param body  The body of the message.
   * @param host  The SMTP host address such as smtp.hostname.com.
   * @param port  The SMTP port such as 25, 465 or 587.
   * @param user  The SMTP user name.
   * @param password  The SMTP password.
   * @throws AddressException  If there is an addressing problem.
   * @throws MessagingException  If something goes wrong sending the message.
   * @throws UnsupportedEncodingException  If the email address cannot be encoded.
   */
  public static void send(String senderEmail, String senderName
                        , String recipientEmail, String recipientName
                        , String subject, String body
                        , String host, int port, final String user, final String password) 
                          throws AddressException, MessagingException, UnsupportedEncodingException {
     // put recipeint into array
     String[] recipientEmails = new String[1];
     String[] recipientNames = new String[1];
     RecipientType[] recipientTypes = new RecipientType[1];
     
     recipientEmails[0] = recipientEmail;
     recipientNames[0] = recipientName;
     recipientTypes[0] = RecipientType.TO;
     
     // and send the message
     send(senderEmail, senderName
        , recipientEmails, recipientNames, recipientTypes
        , subject, body
        , host, port, user, password);
  }
  
  /** 
   * Sends out a single email message to a multiple recipients.
   * 
   * @param senderEmail  The sender's email address.
   * @param senderName  The sender's name.
   * @param recipientEmails  The recipients' email address.
   * @param recipientNames  The recipients' name.
   * @param recipientTypes  The recipients' type.
   * @param subject  The message's subject.
   * @param body  The body of the message.
   * @param host  The SMTP host address such as smtp.hostname.com.
   * @param port  The SMTP port such as 25, 465 or 587.
   * @param user  The SMTP user name.
   * @param password  The SMTP password.
   * @throws AddressException  If there is an addressing problem.
   * @throws MessagingException  If something goes wrong sending the message.
   * @throws UnsupportedEncodingException  If the email address cannot be encoded.
   */
  public static void send(String senderEmail, String senderName
                        , String[] recipientEmails, String[] recipientNames, RecipientType[] recipientTypes
                        , String subject, String body
                        , String host, int port, final String user, final String password) 
                          throws AddressException, MessagingException, UnsupportedEncodingException {
    // create the session
    Properties props = new Properties();
//    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", String.valueOf(port)); 
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
//    props.put("mail.smtp.ssl.enable", "true");
    Authenticator authenticator = new Authenticator() {
      public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
      }
    };
    Session session = Session.getDefaultInstance(props, authenticator);   
    
    // create the message
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(senderEmail, senderName));
    for (int i = 0; i < recipientEmails.length; i++) {
      // add all the recipients
      InternetAddress recipient = new InternetAddress(recipientEmails[i], recipientNames[i]);
      Message.RecipientType recipientType;
      if (recipientTypes[i] == RecipientType.TO) {
        recipientType = Message.RecipientType.TO;
      }
      else if (recipientTypes[i] == RecipientType.CC) {
        recipientType = Message.RecipientType.CC;
      }
      else if (recipientTypes[i] == RecipientType.BCC) {
        recipientType = Message.RecipientType.BCC;
      }
      else {
        recipientType = null;
      }
      message.addRecipient(recipientType, recipient);
    }
    message.setSubject(subject);
    message.setContent(body, "text/plain");
    
    // and send the message...
    Transport.send(message);
  }
}

