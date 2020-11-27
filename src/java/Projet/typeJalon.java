/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projet;
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
public class typeJalon extends item {
    public String periode = "";
    public String couleur = "";
    public int isEssentiel = 0;
    public int isStLie = 0;
    public int numIndicateur = -1;
    public int isEngagement = 0;
    public int typeDoc = -1;
      
    public String Livrable = "";
    public String nomLivrable = "";
    
  public typeJalon (int id){
      this.id = id;
  }
  
  public boolean searchJalon(String nomBase,Connexion myCnx, Statement st, int num){
     ResultSet rs = null;
       req = "SELECT     id, nom, description, ordre, alias, isEssentiel, isStLie";
       req += " FROM         typeJalon";
       req += " WHERE     (isEssentiel = 1)";
       req += " ORDER BY ordre";

       int index = 0;

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
       int Id = rs.getInt("id");
       if ((Id == id) && (num == index))
       {
           return true;
       }     
       index ++;
      }
       
       } catch (SQLException s) {s.getMessage(); }      
      
      
      
      return false;
  }
    
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
     if (this.id == -1)
     {
       req = "SELECT     MAX(ordre) + 1 AS max FROM         typeJalon ";     
        rs = myCnx.ExecReq(st, myCnx.nomBase, req);
        try { rs.next();
            this.ordre = rs.getInt("max");
       
       } catch (SQLException s) {s.getMessage(); }     
         return;
     }        
       req = "SELECT     nom, description, ordre, alias, isEssentiel, isStLie, couleur, numIndicateur, isEngagement, typeDoc FROM         typeJalon ";
       req+= " WHERE     id = "+ this.id;


     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();
      
       this.nom = rs.getString("nom"); 
       this.desc = rs.getString("description");
       if (this.desc == null) this.desc = "";
       this.ordre = rs.getInt("ordre");
       this.periode = rs.getString("alias");
       if (this.periode == null) this.periode="";
       this.isEssentiel = rs.getInt("isEssentiel");
       this.isStLie = rs.getInt("isStLie");
       this.couleur = rs.getString("couleur");
       this.numIndicateur = rs.getInt("numIndicateur");
       this.isEngagement = rs.getInt("isEngagement");
       this.typeDoc = rs.getInt("typeDoc");
       
       } catch (SQLException s) {s.getMessage(); }
     

     
  }  
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
    String description="";
    if (this.desc != null) description = this.desc.replaceAll("'","''");
    else description ="";


    req = "UPDATE [typeJalon]";
    req+= "   SET [nom] = "+ "'"+this.nom+ "'" + ",";
    req+= "       [description] = "+ "'"+description+ "'" + ",";
    req+= "       [ordre] = "+ "'"+this.ordre+ "'" + ",";
    req+= "       [alias] = "+ "'"+this.periode+ "'" + ",";
    req+= "       [isEssentiel] = "+ "'"+this.isEssentiel+ "'" + ",";
    req+= "       [couleur] = "+ "'"+this.couleur+ "'" + ",";
    req+= "       [isStLie] = "+ "'"+this.isStLie+ "'" + ",";
    req+= "       [numIndicateur] = "+ "'"+this.numIndicateur+ "'" + ",";
    req+= "       [isEngagement] = "+ "'"+this.isEngagement+ "'" + ",";
    req+= "       [typeDoc] = "+ "'"+this.typeDoc+ "'" ;

    req+= "  WHERE id= "+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }
  
    public ErrorSpecific bd_updateAll(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
    String description="";
    if (this.desc != null) description = this.desc.replaceAll("'","''");
    else description ="";


    req = "UPDATE [typeJalon]";
    req+= "  SET [numIndicateur] = "+ "'-1'" ;
    req+= "  WHERE numIndicateur= "+this.numIndicateur;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
    
    req = "UPDATE [typeJalon]";
    req+= "  set ordre = (SELECT     MAX(ordre) +1 FROM typeJalon) " ;
    req+= "  WHERE ordre= "+this.ordre;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);     

    return myError;
  }

  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;
    
    req = "SELECT     id  FROM         typeJalon WHERE    ordre = - 1";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    
    int jalonAdId = -1;
    try {
      while (rs.next()) {
       jalonAdId= rs.getInt("id");
      }
       
       } catch (SQLException s) {s.getMessage(); }    
    
    req = "UPDATE Roadmap SET idJalon = '"+jalonAdId+"' WHERE (idJalon  = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    
    req = "DELETE FROM typeJalon WHERE (id  = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

    String description="";
    if (this.desc != null) description = this.desc.replaceAll("'","''");
    else description ="";
    
    req = "INSERT INTO [TypeJalon]";
    req+= "           ([nom]";
    req+= "            ,[description]";
    req+= "            ,[ordre]";
    req+= "            ,[alias]";
    req+= "            ,[isEssentiel]";
    req+= "            ,[isStLie]";
    req+= "            ,[couleur]";
    req+= "            ,[numIndicateur]";
    req+= "            ,[isEngagement]";
    req+= "            ,[typeDoc]";
    req+=")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.nom+"'" + ",";
    req+= "'"+description+"'" + ",";
    req+= "'"+this.ordre+"'" + ",";
    req+= "'"+this.periode+"'" + ",";
    req+= "'"+this.isEssentiel+"'"+ ",";
    req+= "'"+this.isStLie+"'"+ ",";
    req+= "'"+this.couleur+"'"+ ",";
    req+= "'"+this.numIndicateur+"'"+ ",";
    req+= "'"+this.isEngagement+"'"+ ",";
    req+= "'"+this.typeDoc+"'";
    req+= " )";


      ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
      if (myError.cause == -1) return myError;

      req="SELECT  id FROM   TypeJalon ORDER BY id DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("id");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  }      
}
