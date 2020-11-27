/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO; 
import Composant.item;
import accesbase.Connexion;
import accesbase.ErrorSpecific;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Joël
 */
public class Budget extends item {
    public int Annee=-1;
    private String req="";
    
  public Budget(int Annee) {
    this.Annee = Annee;
    
  }    
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;


    req="SELECT     nom, Description";
    req+=" FROM         Budgets";
    req+=" WHERE    Annee =" + this.Annee;


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        this.nom= rs.getString("nom");
        this.desc= rs.getString("Description");

      }
    }
            catch (SQLException s) {s.getMessage();}


  } 
  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;

    String req = "DELETE FROM Budgets WHERE  Annee = " + this.Annee;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  }  
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){

    ErrorSpecific myError = new ErrorSpecific();
    

    req = "UPDATE    Budgets  SET ";
    req +=" nom ='"+ this.nom + "'";
    req +=",";
    req +=" Description ='"+ this.desc + "'";

    req += " WHERE Annee  ="+ this.Annee ;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    int nb = 0;
    ResultSet rs=null;
    req="SELECT     count(*) as nb";
    req+=" FROM         Budgets";
    req+=" WHERE    Annee =" + this.Annee;


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        nb= rs.getInt("nb");

      }
    }
    catch (SQLException s) {s.getMessage();}    
    
    if (nb > 0)
    {
        myError.cause =  1;
        return myError;
    }
      
  
  
      if (this.nom != null)
      this.nom =  this.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
      
      if (this.desc != null)
      this.desc =  this.desc.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");      
    
    req = "INSERT Budgets (Annee, nom, Description) ";
    req+=" VALUES (";
    req+="'"+this.Annee+"'";
    req+=",";    
    req+="'"+this.nom+"'";
    req+=",";
    req+="'"+this.desc+"'";
    req+=")";


    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_insert",""+this.id);
    if (myError.cause == -1) return myError;


    return myError;
  }   
    
}
