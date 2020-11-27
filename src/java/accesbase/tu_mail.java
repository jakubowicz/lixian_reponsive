/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesbase;

import mail.Jmail;

/**
 *
 * @author Joël
 */
public class tu_mail {

public static String replaceAll (String theStr, String oldStr, String newStr){
if (theStr == null) return "";
int index = theStr.indexOf(oldStr);
String Suffixe="";
String Prefixe="";
String theNewStr="";

if (index == -1) return theStr;

      while (index !=-1){
        if (index > 0)
        {
          Prefixe+=theStr.substring(0, index)+newStr;
          Suffixe=theStr.substring(index + 1);
          theStr = Suffixe;
        }
        else
        {
          theStr = newStr +theStr.substring( 1);
        }
        index = Suffixe.indexOf(oldStr);

      }

theNewStr=Prefixe+Suffixe;
return theNewStr;
} 
public static String epureMail (String theStr){

  String newStr=theStr;
  newStr=replaceAll(newStr,"�","e");
  newStr=replaceAll(newStr,"�","e");
  newStr=replaceAll(newStr,"�","e");
  newStr=replaceAll(newStr,"�","e");
  newStr=replaceAll(newStr,"�","a");
  newStr=replaceAll(newStr,"�","u");

  return newStr;
}    
public static void sendmail(String serverSMTP, String emetteur,String Destinataire, String objet, String corps) throws Exception{

Jmail myMail=null;

myMail = new Jmail (serverSMTP,
                    emetteur,
                    Destinataire,
                    epureMail(objet),
                    epureMail(corps));
//myMail.dump();

  //myMail.send();
myMail.send2();


} 

    public static void main(String[] args) {
        String serveurSMTP="bt1fcdcmim00.bpa.bouyguestelecom.fr";
        String emetteur="lemuller@bouyguestelecom.fr";
        String Destinataire="lemuller@bouyguestelecom.fr";
        String objet="essai";
        String corps="Ceci est un essai";
        
        if (args.length >0)
        {
            serveurSMTP= args[0];
            emetteur=args[1];
            Destinataire=args[1];
            objet="essai";
            corps="Ceci est un essai";
        }
        try{
        sendmail(serveurSMTP,emetteur,Destinataire, objet, corps);
        }
        catch(Exception e)
        {
           System.out.print("send mail failed !!!!!!");
        }
        }

    
}

        