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
import accesbase.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author Joël
 */
public class Client extends Personne{
    private String req="";
    public int nbLicences = 0;
    public String profil = "";
    public String mode = "";
    public Date DdebutEvaluation = null;
    public String debutEvaluation = "";
    public int nbJoursEvaluation = 0;
    public String adresseIP = "";
    public String key = "";
    public String langue = "";
    public db infoTechnique=null;
    
   public Client(int id) {
    this.id = id;
    this.infoTechnique = new db(id);
  }    
    
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    Date theDate = null;

    req="SELECT     nomClient, nomContact, prenomContact, telephoneContact, adresse, mail, nbLicences, profil,";
    req+= " mode, debutEvaluation, nbJoursEvaluation, adresseIP, [key], langue";
    req+=" FROM         db";
    req+=" WHERE     id = " + this.id;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      this.entreprise = rs.getString("nomClient");
      if (this.entreprise == null) this.entreprise = "";
      
      this.nom = rs.getString("nomContact");
      if (this.nom == null) this.nom = "";
      
      this.prenom = rs.getString("prenomContact");
      if (this.prenom == null) this.prenom = "";
      
      this.telephone = rs.getString("telephoneContact");
      if (this.telephone == null) this.telephone = "";
      
      this.adresse = rs.getString("adresse");
      if (this.adresse == null) this.adresse = "";
      
      this.mail = rs.getString("mail");
      if (this.mail == null) this.mail = "";
      
      this.nbLicences = rs.getInt("nbLicences");
      this.profil = rs.getString("profil");
      this.mode = rs.getString("mode");
      this.DdebutEvaluation = rs.getDate("debutEvaluation");
      this.debutEvaluation=Utils.getDateFrench(this.DdebutEvaluation);
      if (this.debutEvaluation == null) this.debutEvaluation = "";      
      
      this.nbJoursEvaluation = rs.getInt("nbJoursEvaluation");
      
      this.adresseIP = rs.getString("adresseIP");
      if (this.adresseIP == null) this.adresseIP = "";
      
      this.key = rs.getString("key");
      if (this.key == null) this.key = "";
      
      this.langue = rs.getString("langue");

  }
    catch (SQLException s) {s.getMessage(); }
    
    this.infoTechnique.getAllFromBd(nomBase,myCnx, st);
 }  
  
 public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
  ErrorSpecific myError = new ErrorSpecific();
  String req = "delete from db WHERE id= " + this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;

  return myError;
}

  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
    String str_debutEvaluation = "";
    if ((this.debutEvaluation != null) &&(!this.debutEvaluation.equals("")))
    {
      Utils.getDate(this.debutEvaluation);
      str_debutEvaluation ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }
    else
      str_debutEvaluation = "null";
        
    req = "UPDATE    db  SET ";
    req +=" nomClient ='"+ this.entreprise + "', ";
    req +=" nomContact ='"+ this.nom + "', ";
    req +=" prenomContact ='"+ this.prenom + "', ";
    req +=" telephoneContact ='"+ this.telephone + "', ";
    req +=" adresse ='"+ this.adresse + "', ";
    req +=" mail ='"+ this.mail + "',";
    req +=" nbLicences ='"+ this.nbLicences + "', ";
    req +=" profil ='"+ this.profil + "', ";
    req +=" mode ='"+ this.mode + "', ";
    req +=" debutEvaluation ="+ str_debutEvaluation + ", ";
    req +=" nbJoursEvaluation ='"+ this.nbJoursEvaluation + "', ";
    req +=" adresseIP ='"+ this.adresseIP + "', ";
    req +=" [key] ='"+ this.key + "', ";
    req +=" ordre ='"+ this.infoTechnique.ordre + "', ";
    req +=" nom ='"+ this.infoTechnique.nom + "', ";
    req +=" driver ='"+ this.infoTechnique.driver + "', ";
    req +=" URL ='"+ this.infoTechnique.URL + "', ";
    req +=" login ='"+ this.infoTechnique.login + "', ";
    req +=" password ='"+ this.infoTechnique.psw + "', ";
    req +=" langue ='"+ this.langue + "', ";
    req +=" serverSMTP ='"+ this.infoTechnique.serverSMTP + "', ";
    req +=" portSMTP ='"+ this.infoTechnique.portSMTP + "', ";
    req +=" loginSMTP ='"+ this.infoTechnique.loginSMTP + "', ";
    req +=" passwordSMTP ='"+ this.infoTechnique.passwordSMTP + "' ";
    req += " WHERE id ="+ this.id;   

     ErrorSpecific myError = new ErrorSpecific();


      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
      if (myError.cause == -1) return myError;

     return myError;
  }
  
  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){

    String str_debutEvaluation = "";
    if ((this.debutEvaluation != null) &&(!this.debutEvaluation.equals("")))
    {
      Utils.getDate(this.debutEvaluation);
      str_debutEvaluation ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }
    else
      str_debutEvaluation = "null";
             
    ErrorSpecific myError = new ErrorSpecific();
    req = "INSERT db (";
    req+="nom";
    req+=",";
    req+="driver";
    req+=",";
    req+="url";
    req+=",";
    req+="login";
    req+=",";
    req+="password";
    req+=",";    
    req+="ordre";
    req+=",";
    req+="nomClient";
    req+=",";
    req+="nomContact";
    req+=",";
    req+="prenomContact";
    req+=",";
    req+="telephoneContact";
    req+=",";
    req+="adresse";
    req+=",";
    req+="mail";
    req+=",";
    req+="nbLicences";
    req+=",";
    req+="profil";
    req+=",";
    req+="mode";
    req+=",";
    req+="debutEvaluation";
    req+=",";    
    req+="nbJoursEvaluation";
    req+=","; 
    req+="adresseIP";
    req+=",";   
    req+=" [key]";
    req+=",";   
    req+=" langue"; 
    req+=",";   
    req+=" serverSMTP"; 
    req+=",";   
    req+=" portSMTP"; 
    req+=",";   
    req+=" loginSMTP"; 
    req+=",";   
    req+=" passwordSMTP";     
    
    req+="   )";
    req+=" VALUES (";
    req+="'"+this.infoTechnique.nom+"'";;
    req+=",";
    req+="'"+this.infoTechnique.driver+"'";;
    req+=",";    
    req+="'"+this.infoTechnique.URL+"'";;
    req+=",";    
    req+="'"+this.infoTechnique.login+"'";;
    req+=",";    
    req+="'"+this.infoTechnique.psw+"'";;
    req+=",";    
    req+="'"+this.infoTechnique.ordre+"'";;
    req+=",";   
    req+="'"+this.entreprise.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
    req+=",";    
    req+="'"+this.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
    req+=",";
    req+="'"+this.prenom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
    req+=",";
    req+="'"+this.telephone.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
    req+=",";
    req+="'"+this.adresse.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
    req+=",";    
    req+="'"+this.mail.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
    req+=",";
    req+="'"+this.nbLicences+"'";
    req+=",";
    req+="'"+this.profil+"'";
    req+=",";
    req+="'"+this.mode+"'";
    req+=",";
    req+=""+str_debutEvaluation+"";
    req+=",";
    req+="'"+this.nbJoursEvaluation+"'";
    req+=",";
    
    req+="'"+this.adresseIP+"'";
    req+=",";    
    req+="'"+this.key+"'";
    req+=",";    
    req+="'"+this.langue+"'";
    req+=",";    
    req+="'"+this.infoTechnique.serverSMTP+"'";
    req+=",";    
    req+="'"+this.infoTechnique.portSMTP+"'";
    req+=",";    
    req+="'"+this.infoTechnique.loginSMTP+"'";
    req+=",";    
    req+="'"+this.infoTechnique.passwordSMTP+"'";    
    req+=")";


    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

        req = "SELECT id FROM  db WHERE     nomClient = '"+this.entreprise + "'";
        ResultSet rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
          this.id = rs.getInt("id");
        } catch (SQLException s) {s.getMessage();}    

     return myError;
  }  
    
}


