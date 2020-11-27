package mail; 

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.*;

import accesbase.Connexion;

public class Jmail
{
   private String serverAddress;
   private boolean verbose = true;
   private String fromAddress;
   private String recipient;
   private String subject;
   private String body;
   private String Login;
   private String Password;
   private String Port;

   private Vector ListRecipient;
   String req = "";

   public Jmail(String serverAddress, String fromAddress, String recipient, String subject, String body)
   {
      this.serverAddress = serverAddress;
      this.fromAddress = fromAddress;
      this.recipient = recipient;
      this.subject = subject;
      this.body = body;
      this.verbose = true;

      this.ListRecipient = new Vector(10);
      this.setListRecipient(this.recipient);
   }
   
   public Jmail(String serverAddress, String Login, String Password, String Port, String fromAddress, String recipient, String subject, String body)
   {
      this.serverAddress = serverAddress;
      this.Login = Login;
      this.Password = Password;
      this.Port = Port;
      this.fromAddress = fromAddress;
      this.recipient = recipient;
      this.subject = subject;
      this.body = body;
      this.verbose = true;

      this.ListRecipient = new Vector(10);
      this.setListRecipient(this.recipient);
   }   

   public Jmail(String serverAddress, String fromAddress, String recipient, String subject, String body, boolean verbose)
   {
      this.serverAddress = serverAddress;
      this.fromAddress = fromAddress;
      this.recipient = recipient;
      this.subject = subject;
      this.body = body;
      this.verbose = verbose;
   }

   public static void envoi()  throws Exception{

   }
   public void dump(){
     if (Connexion.traceOn.equals("no")) return;
     System.out.println("==================================================");
     System.out.println("serverAddres="+this.serverAddress);
     System.out.println("fromAddress="+this.fromAddress);
     System.out.println("toAddress="+this.ListRecipient.elementAt(0));
     System.out.println("subject="+this.subject);
     System.out.println("body="+this.body);
     System.out.println("==================================================");

  }
   public void send() throws Exception
    {

      System.out.println("emetteur="+fromAddress+"::"+"destinataire="+this.recipient+"::"+"sujet="+this.subject+"::"+"Corps="+this.body+"::");

      Message mesg;
      Session session;
      //Connexion.trace("@01234","1","");
        Properties props = new Properties();
      //Connexion.trace("@01234","2","");
        props.put("mail.smtp.host", serverAddress);
      //Connexion.trace("@01234","3","");
        session = Session.getDefaultInstance(props, null);
        session.setDebug(verbose);
            mesg = new MimeMessage(session);
            //Connexion.trace("@01234","4","");
            this.setListFromAddress();
            //Connexion.trace("@01234","5","");
            mesg.setFrom(new InternetAddress(this.fromAddress));
            //Connexion.trace("@01234","this.ListRecipient.size()",""+this.ListRecipient.size());
            for (int i=0; i < this.ListRecipient.size();i++)
            {
              //System.out.println("(String)this.ListRecipient.elementAt("+i+")="+(String)this.ListRecipient.elementAt(i));
              //Connexion.trace("@01234","destinataire"+i,(String)this.ListRecipient.elementAt(i));
              mesg.addRecipient(Message.RecipientType.TO, new InternetAddress((String)this.ListRecipient.elementAt(i)));
            }

            mesg.setSubject(this.subject);
            //mesg.setText(body);
            //this.body = "<p style='font:'Times New Roman', Times, serif '>Ceci est un lien pour ouvrir <a href='http:\\Lixian' title='ouvrir Lixian'>Lixian</a></p>";
            this.body = "<p style=\"font:'Times New Roman', Times, serif \">" + this.body + "</p>";
            mesg.setContent(this.body, "text/html"); //Envoi en HTML
            Transport.send(mesg);

    }

    public void send2() throws Exception
     {
    String smtpHost = "smtp.gmail.com";
    String from = "joel.jakubowicz@gmail.com";
    String to = "joel.jakubowicz@gmail.com";
    String username = "joel.jakubowicz@gmail.com";
    String password = "16031960";
 
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");  
 
    Session session = Session.getDefaultInstance(props);
    session.setDebug(true);
 
    MimeMessage message = new MimeMessage(session);   
    message.setFrom(new InternetAddress(from));
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
    message.setSubject("Hello");
    message.setText("Hello World");
 
    Transport tr = session.getTransport("smtp");
    tr.connect(smtpHost, username, password);
    message.saveChanges();
 
    // tr.send(message);
    /** Genere l'erreur. Avec l authentification, oblige d utiliser sendMessage meme pour une seule adresse... */
 
    // tr.send(message);
    /** Genere l'erreur. Avec l authentification, oblige d utiliser sendMessage meme pour une seule adresse... */
 
    tr.sendMessage(message,message.getAllRecipients());
    tr.close();

     }
    
    public void send3() throws Exception
     {
    String smtpHost = this.serverAddress;
    String from = this.fromAddress;
    String to = this.recipient;
    String username = this.Login;
    String password = this.Password;
 
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", this.serverAddress);
    props.put("mail.smtp.port", this.Port);  
 
    Session session = Session.getDefaultInstance(props);
    session.setDebug(true);
 
    MimeMessage message = new MimeMessage(session);   
    message.setFrom(new InternetAddress(from));
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
    message.setSubject(this.subject);
    message.setText(this.body);
 
    Transport tr = session.getTransport("smtp");
    tr.connect(smtpHost, username, password);
    message.saveChanges();
 
    // tr.send(message);
    /** Genere l'erreur. Avec l authentification, oblige d utiliser sendMessage meme pour une seule adresse... */
 
    // tr.send(message);
    /** Genere l'erreur. Avec l authentification, oblige d utiliser sendMessage meme pour une seule adresse... */
 
    tr.sendMessage(message,message.getAllRecipients());
    tr.close();

     }   
    
    public void sendByAWS2() throws Exception
     {
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", "25"); 
    	
    	// Set properties indicating that we want to use STARTTLS to encrypt the connection.
    	// The SMTP session will begin on an unencrypted connection, and then the client
        // will issue a STARTTLS command to upgrade to an encrypted connection.
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.starttls.required", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("joel.jakubowicz@gmail.com"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress("admin@itssfrance.fr"));
        msg.setSubject("coucou");
        msg.setContent("ceci est un essai","text/plain");
            
        // Create a transport.        
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Attempting to send an email through the Amazon SES SMTP interface...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect("email-smtp.eu-west-1.amazonaws.com", "AKIAJQUWSWGPVKSVNJZQ", "Ard/k2dBPxmxyVAPCsP1SIrMMLZ14kWkr3oYbmtOSVVm");
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
            throw(ex);
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();        	
        }            
     }    
    public void sendByAWS() throws Exception
     {
     // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", this.Port); 
    	
    	// Set properties indicating that we want to use STARTTLS to encrypt the connection.
    	// The SMTP session will begin on an unencrypted connection, and then the client
        // will issue a STARTTLS command to upgrade to an encrypted connection.
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.starttls.required", "true");
        

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(this.fromAddress));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(this.recipient));
        msg.setSubject(this.subject);
        msg.setContent(this.body,"text/plain");
            
        // Create a transport.        
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Attempting to send an email through the Amazon SES SMTP interface...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(this.serverAddress, this.Login, this.Password);
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
            throw(ex);
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();        	
        }         
     }
     

   public void setRecipient(String recipient)
   {
      this.recipient = recipient;
   }


   public void setListRecipient(String recipient)
   {
     recipient+=";";
     String str;
     int i;
     for (StringTokenizer t = new StringTokenizer(recipient, ";") ; t.hasMoreTokens() ; ) {
       this.ListRecipient.add(t.nextToken());
       //str = t.nextToken();
       //System.out.println("str="+str);


     } //end for

   }


   public void setListFromAddress()
   {
     int pos = this.fromAddress.indexOf(";");
     if (pos >=0)
       this.fromAddress=this.fromAddress.substring(0, pos);
   }

    public static void main(String[] args) {
      Jmail myMail=null;
      

      try{
        myMail.send();
      }
      catch (Exception e){
        e.printStackTrace();

      }

    }

}
