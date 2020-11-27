package mail; 

/**
 * <p>Titre : </p>
 *
 * <p>Description : </p>
 *
 * <p>Copyright : Copyright (c) 2004</p>
 *
 * <p>Soci�t� : </p>
 *
 * @author not attributable
 * @version 1.0
 */


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.*;
import accesbase.Connexion;

public class mailHtml
{
   private String serverAddress;
   private boolean verbose = true;
   private String fromAddress;
   private String recipient;
   private String subject;
   private String body;

   private Vector ListRecipient;
   String req = "";

   public mailHtml(String serverAddress, String fromAddress, String recipient, String subject, String body)
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

   public mailHtml(String serverAddress, String fromAddress, String recipient, String subject, String body, boolean verbose)
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
            mesg.setContent(this.body, "text/html"); //Envoi en HTML

            //mesg.setText(body);
            Transport.send(mesg);

    }

    public void send2() throws Exception
     {

       System.out.println("emetteur="+fromAddress+"::"+"destinataire="+this.recipient+"::"+"sujet="+this.subject+"::"+"Corps="+this.body+"::");

       Message mesg;
       Session session;

         Properties props = new Properties();
         props.put("mail.smtp.host", serverAddress);
         session = Session.getDefaultInstance(props, null);
         session.setDebug(verbose);
             mesg = new MimeMessage(session);
             mesg.setFrom(new InternetAddress(this.fromAddress));
             mesg.addRecipient(Message.RecipientType.TO, new InternetAddress(this.recipient));
             mesg.setSubject(this.subject);
             mesg.setText(body);
             Transport.send(mesg);

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

   public static void sendmail(String emetteur,String Destinataire, String objet, String corps) throws Exception{
     //sendMail
     if (!Connexion.sendMail.equals("1")) return;

   mailHtml myMail=null;

   myMail = new mailHtml (Connexion.serverSMTP,
                       emetteur,
                       Destinataire,
                       Connexion.epureMail(objet),
                       Connexion.epureMail(corps));
   //myMail.dump();

     myMail.send();


}
    public static void main(String[] args) {
      Connexion myCnx = null;
      Statement st = null;
      ResultSet rs = null;

      String req = "";
      String base = myCnx.getDate();
      st = myCnx.Connect();

      String emetteur = "jjakubow"+"@"+myCnx.mail;;
      String destinataire = "jjakubow"+"@"+myCnx.mail;;
      String objet = "Salut Claire";
      String corps = "<p style='font:'Times New Roman', Times, serif '>Ceci est un lien pour ouvrir <a href='http:\\Lixian' title='ouvrir Lixian'>Lixian</a></p>";

      try {
        sendmail(emetteur, destinataire, objet, corps);
      }
      catch (Exception e) {
        Connexion.trace("@01234******", "", e.getMessage());
        //e.printStackTrace();
      }
    }
}
