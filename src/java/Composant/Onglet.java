package Composant; 

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
public class Onglet extends item{
  public String signet="";
  public int visibility;
  public String image;
  public String Action="";
  public String Type="";
  public int Admin = 0;
  public int idicone = -1;
  public int idPere = -1;
  public int isManaged = 0;
  
  private String req = "";

  public Onglet() {
  }
  
  public Onglet(int id) {
      this.id = id;
  }  

  public Onglet(String nom, int visibility,String image ) {
    this.image = image;
    this.nom = nom;
    this.visibility = visibility;
  }

  public Onglet(int ordre,String nom,String signet, int visibility,String image ) {
    this.ordre = ordre;
    this.image = image;
    this.nom = nom;
    this.signet = signet;
    this.visibility = visibility;
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st){

    req="SELECT     id, idPere, Ordre, idicone, nom, signet, visibility, Type, Action, Admin, isManaged";
    req+=" FROM         Onglet";
    req+=" WHERE     id = " + this.id;
    //req+=" AND     isManaged = " + this.isManaged;

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try {
       while (rs.next()) {
         this.idPere = rs.getInt("idPere");
         this.ordre = rs.getInt("Ordre");
         this.idicone = rs.getInt("idicone");
         
         this.nom = rs.getString("nom");
         if (this.nom == null) this.nom = "";     
         
         this.signet = rs.getString("signet");
         if (this.signet == null) this.signet = "";         
         
         this.visibility = rs.getInt("visibility");
         
         this.Type = rs.getString("Type");
         if (this.Type == null) this.Type = "";
         
         this.Action = rs.getString("Action");
         if (this.Action == null) this.Action = "";
         
         this.Admin = rs.getInt("Admin");
         this.isManaged = rs.getInt("isManaged");

       }
        } catch (SQLException s) {s.getMessage();}

  }
  
  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;

    req = "DELETE FROM Onglet WHERE  idPere = " + this.id;
    req+= " AND isManaged=" + this.isManaged;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    
    req = "DELETE FROM Onglet WHERE  id = " + this.id;
    //req+= " AND isManaged=" + this.isManaged;    
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  }  
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){

    ErrorSpecific myError = new ErrorSpecific();
    
    if (this.Action != null)
        this.Action=this.Action.replaceAll("\u0092","'").replaceAll("'","''");
 
    req = "UPDATE    Onglet  SET ";
    req +=" idPere ='"+ this.idPere + "', ";
    req +=" ordre ='"+ this.ordre + "', ";
    req +=" idicone ='"+ this.idicone + "', ";
    req +=" nom ='"+ this.nom + "', ";
    req +=" signet ='"+ this.signet + "', ";
    req +=" visibility ='"+ this.visibility + "', ";
    req +=" Type ='"+ this.Type + "', ";
    req +=" Action ='"+ this.Action + "', ";
    req +=" Admin ='"+ this.Admin + "', ";
    req +=" isManaged ='"+ this.isManaged + "'";

    req += " WHERE id ="+ this.id;
    //req+= " AND isManaged=" + this.isManaged;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){
      
    if (this.Action != null)
        this.Action=this.Action.replaceAll("\u0092","'").replaceAll("'","''");      

  ErrorSpecific myError = new ErrorSpecific();

    req = "SELECT MAX(id) AS id FROM   Onglet";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try {
       while (rs.next()) {
         this.id = rs.getInt("id") + 1;
       }
        } catch (SQLException s) {s.getMessage();}    
    
    req = "INSERT Onglet (id, idPere, Ordre, idicone, nom, signet, visibility, Type, Action, Admin, isManaged) ";
    req+=" VALUES (";
    req+="'"+this.id+"'";
    req+=",";
    req+="'"+this.idPere+"'";
    req+=",";
    req+="'"+this.ordre+"'"; 
    req+=",";
    req+="'"+this.idicone+"'";  
    req+=",";
    req+="'"+this.nom+"'";  
    req+=",";
    req+="'"+this.signet+"'";  
    req+=",";
    req+="'"+this.visibility+"'";  
    req+=",";
    req+="'"+this.Type+"'";  
    req+=",";
    req+="'"+this.Action+"'";  
    req+=",";
    req+="'"+this.Admin+"'";  
    req+=",";
    req+="'"+this.isManaged+"'";      
    req+=")";


    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_insert",""+this.id);
    if (myError.cause == -1) return myError; 


    return myError;
  }    

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("ordre="+this.ordre);
    System.out.println("nom="+this.nom);
    System.out.println("signet="+this.signet);
    System.out.println("image="+this.image);
    System.out.println("visibility="+this.visibility);
    System.out.println("Action="+this.Action);
    System.out.println("Admin="+this.Admin);

    System.out.println("==================================================");
  }

  public static void main(String[] args) {
    Onglet onglet = new Onglet();
  }
}
