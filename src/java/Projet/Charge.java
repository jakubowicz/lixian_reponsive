package Projet; 

import accesbase.Connexion;
import accesbase.ErrorSpecific;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
public class Charge {
   public int idObj = -1;
   public String nomObj;
   public int idMembre = -1;
   public int idRoadmap = -1;
   public int anneeRef;
  public int semaine;
  public int idEtat;
  public String nomEtat;
  public float hj;
  
  private String req="";
  
  public Charge(int anneeRef, int semaine, float hj) {
    this.anneeRef=anneeRef;
    this.semaine=semaine;
    this.hj=hj;

  }
  public Charge() {

  }
  
  public Charge(int idObj) {
    this.idObj=idObj;

  }

  public Charge(int idObj, float hj) {
    this.anneeRef=anneeRef;
    this.idObj=idObj;
    this.hj=hj;

  }
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
 

    req = "UPDATE [PlanDeCharge]";
    req+="    SET ";
    req+="   [Charge] = '"+ this.hj+ "'";

    req+=" WHERE     (projetMembre = "+this.idMembre+") AND (idRoadmap = "+this.idRoadmap+") AND (semaine = "+this.semaine+") AND (anneeRef = "+this.anneeRef + ")";

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_update",""+this.idMembre);

    return myError;
  }
  
  public ErrorSpecific bd_updateAndInsert(String nomBase,Connexion myCnx, Statement st, String transaction){
 
    req = "DELETE FROM [PlanDeCharge]";
    req+=" WHERE     (projetMembre = "+this.idMembre+") AND (idRoadmap = "+this.idRoadmap+") AND (semaine = "+this.semaine+") AND (anneeRef = "+this.anneeRef + ")";

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_update",""+this.idMembre);
    
           req = "INSERT PlanDeCharge (";
             req+="nomProjet"+",";
             req+="projetMembre"+",";
             req+="semaine"+",";
             req+="Charge"+",";
             req+="anneeRef"+",";
             req+="idRoadmap"+",";
             req+="etat"+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+""+"',";
             req+="'"+this.idMembre+"',";
             req+="'"+this.semaine+"',";
             req+="'"+this.hj+"',";
             req+="'"+this.anneeRef+"',";
             req+="'"+this.idRoadmap+"',";
             req+="'"+this.idEtat+"'";
           req+=")";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_update",""+this.idMembre);

    return myError;
  }  
  
public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs=null;

req = "SELECT     PlanDeCharge.Charge, PlanDeCharge.etat, TypeEtatImputation.nom";
req+=" FROM         PlanDeCharge INNER JOIN";
req+="                       TypeEtatImputation ON PlanDeCharge.etat = TypeEtatImputation.id";
req+=" WHERE     (projetMembre = "+this.idMembre+") AND (idRoadmap = "+this.idRoadmap+") AND (semaine = "+this.semaine+") AND (anneeRef = "+this.anneeRef + ")";

  rs = myCnx.ExecReq(st, nomBase, req);

     try {
          rs.next();
          this.hj = rs.getFloat("Charge");
          this.idEtat=rs.getInt("etat");
          this.nomEtat=rs.getString("nom");
     }
         catch (SQLException s) {
           s.getMessage();
         }
    
}  

public void getNomState(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs=null;

req = "SELECT     nom FROM   TypeEtatImputation WHERE     id = " + this.idEtat;

  rs = myCnx.ExecReq(st, nomBase, req);

     try {
          rs.next();
          this.nomEtat=rs.getString("nom");
     }
         catch (SQLException s) {
           s.getMessage();
         }
    
}
  
  public ErrorSpecific bd_updateState(String nomBase,Connexion myCnx, Statement st, String transaction){
 

    req = "UPDATE [PlanDeCharge]";
    req+="    SET ";
    req+="   [Charge] = '"+ this.hj+ "'";
    req+=" ,";
    req+="   [etat] = '"+ this.idEtat+ "'";
    req+=" WHERE     (projetMembre = "+this.idMembre+") AND (idRoadmap = "+this.idRoadmap+") AND (semaine = "+this.semaine+") AND (anneeRef = "+this.anneeRef + ")";

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.idMembre);

    return myError;
  }  
  

  public void dump(){
    System.out.println("==================================================");
    System.out.println("idObj="+this.idObj);
    System.out.println("anneeRef="+this.anneeRef);
    System.out.println("semaine="+this.semaine);
    System.out.println("hj="+this.hj);
    System.out.println("==================================================");
  }
}
