/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Organisation;

import Composant.Personne;
import General.Utils;
import accesbase.Connexion;
import accesbase.ErrorSpecific;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author Joël
 */
public class Contact extends Personne{
    public int id=-1;
    public String demande="";
    public String reponse="";
    public int idEtat = 1;
    public String nomEtat = "";
    public java.util.Date DdateDemande = null;
    public java.util.Date DdateReponse = null;
    public String dateDemande="";
    public String dateReponse="";   
    
    private String req="";
    private ResultSet rs=null;
    
  public Contact(int id) {
    this.id = id;
  }    
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    Date theDate = null;

    req="SELECT     Contacts.nom, Contacts.prenom, Contacts.entreprise, Contacts.telephone, Contacts.adresse, Contacts.mail, Contacts.demande, Contacts.dateDemande, Contacts.reponse, Contacts.dateReponse, ";
    req+= "                      Contacts.idEtat, TypeEtatContact.Nom AS nomEtat";
    req+= " FROM         Contacts INNER JOIN";
    req+= "                       TypeEtatContact ON Contacts.idEtat = TypeEtatContact.id";
    req+= " WHERE     Contacts.id = "+ this.id;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      this.nom = rs.getString("nom");
      this.prenom = rs.getString("prenom");
      this.entreprise = rs.getString("entreprise");
      this.telephone = rs.getString("telephone");
      this.adresse = rs.getString("adresse");
      this.mail = rs.getString("mail");
      this.demande = rs.getString("demande");
      if (this.demande != null) 
          this.demande = this.demande.replaceAll("\n", "<br>").replaceAll("\r", "<br>");
      else 
          this.demande = "";
      
      this.DdateDemande = rs.getDate("dateDemande");
      if (this.DdateDemande !=null)
       {
         this.dateDemande = this.DdateDemande.getDate() + "/" + (this.DdateDemande.getMonth() + 1) + "/" + (this.DdateDemande.getYear() + 1900);
       }
       else this.dateDemande = "";
      
      this.reponse = rs.getString("reponse");
      if (this.reponse == null)  this.reponse = "";
     
      
      this.DdateReponse = rs.getDate("dateReponse");
      if (this.DdateReponse !=null)
       {
         this.dateDemande = this.DdateReponse.getDate() + "/" + (this.DdateReponse.getMonth() + 1) + "/" + (this.DdateReponse.getYear() + 1900);
       }
       else this.dateDemande = "";   
      
       this.idEtat = rs.getInt("idEtat");
       this.nomEtat = rs.getString("nomEtat");

  }
    catch (SQLException s) {s.getMessage(); }
 }
  
public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
  ErrorSpecific myError = new ErrorSpecific();
  String req = "delete from Contacts WHERE id= " + this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;

  return myError;
}  

  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){

    Date theDate = new Date();
    String str_dateDemande ="convert(datetime, '"+theDate.getDate()+"/"+(theDate.getMonth() +1)+"/"+(theDate.getYear() +1900)+"', 103)";   
             
    ErrorSpecific myError = new ErrorSpecific();
    req = "INSERT Contacts (";
    req+="nom";
    req+=",";
    req+="prenom";
    req+=",";
    req+="entreprise";
    req+=",";
    req+="telephone";
    req+=",";
    req+="adresse";
    req+=",";    
    req+="mail";
    req+=",";
    req+="demande";
    req+=",";
    req+="dateDemande";
    req+=",";
    req+="idEtat";
    req+="   )";
    req+=" VALUES (";
    req+="'"+this.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
    req+=",";
    req+="'"+this.prenom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
    req+=",";
    req+="'"+this.entreprise.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
    req+=",";
    req+="'"+this.telephone.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
    req+=",";
    req+="'"+this.adresse.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
    req+=",";    
    req+="'"+this.mail.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
    req+=",";
    req+="'"+this.demande.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
    req+=",";
    req+=""+str_dateDemande+"";
    req+=",";
    req+="'"+this.idEtat+"'";

    req+=")";


    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

        req = "SELECT id FROM  Contacts WHERE     mail = '"+this.mail + "'";
        ResultSet rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
          this.id = rs.getInt("id");
        } catch (SQLException s) {s.getMessage();}    

     return myError;
  }
  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){

    req = "UPDATE    Contacts  SET ";
    req +=" nom ='"+ this.nom + "', ";
    req +=" prenom ='"+ this.prenom + "', ";
    req +=" entreprise ='"+ this.entreprise + "', ";
    req +=" telephone ='"+ this.telephone + "', ";
    req +=" adresse ='"+ this.adresse + "', ";
    req +=" mail ='"+ this.mail + "',";
    req +=" demande ='"+ this.demande + "', ";
    req +=" idEtat ='"+ this.idEtat + "' ";
    req += " WHERE id ="+ this.id;   

     ErrorSpecific myError = new ErrorSpecific();


      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
      if (myError.cause == -1) return myError;

     return myError;
  }    
  
  public ErrorSpecific bd_UpdateReponse(String nomBase,Connexion myCnx, Statement st, String transaction){
    Date theDate = new Date();
    
    String str_dateReponse ="NULL";  
    
    String myReponse = "";
    if (this.reponse != null)
        myReponse = this.reponse.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
    else
        myReponse = "";
    
    str_dateReponse ="convert(datetime, '"+theDate.getDate()+"/"+(theDate.getMonth() +1)+"/"+(theDate.getYear() +1900)+"', 103)";  
    req = "UPDATE    Contacts  SET ";
    req +=" reponse ='"+ myReponse + "', ";
    req +=" dateReponse ="+ str_dateReponse + ", ";
    req +=" idEtat ='"+ this.idEtat + "' ";
    req += " WHERE id ="+ this.id;    

     ErrorSpecific myError = new ErrorSpecific();

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
      if (myError.cause == -1) return myError;

     return myError;
  }     
}
