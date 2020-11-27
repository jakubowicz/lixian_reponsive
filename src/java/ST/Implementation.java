/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ST;
import Composant.item;
import accesbase.Connexion;
import accesbase.ErrorSpecific;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author Joël
 */
public class Implementation extends item {
    public Vector ListeInterfaces = new Vector(10);
    public String LigneInterfaces ="";
    
  public Implementation(int id) {
    this.id = id;
  } 
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
      req = "SELECT      nomImplementation, descImplementation, typeImplementation";
      req += " FROM         Implementation";
      req += " WHERE     idImplementation = " + this.id;

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       
       this.nom = rs.getString("nomImplementation");
       this.desc = rs.getString("descImplementation");
       this.LigneInterfaces = rs.getString("typeImplementation");

       } catch (SQLException s) {s.getMessage(); }
  } 
  
  public void getListeInterfaces(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
       req = "SELECT     id, nom, description FROM         typeInterfaces ORDER BY description";

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     
    try {
        while (rs.next()) {  
            item theItem = new item();
            theItem.id = rs.getInt("id");
            theItem.nom = rs.getString("nom");  
            theItem.desc = rs.getString("description");  
            this.ListeInterfaces.addElement(theItem);
    }

       } 
  catch (SQLException s) {
    s.getMessage();
  }    
  } 
  
 public String getisChecked(item theItem){
     
    String[] LigneInterfaces = this.LigneInterfaces.split(";");

    for (int i=0; i < LigneInterfaces.length; i++)
        {
            if (LigneInterfaces[i] .equals(theItem.nom))
                {
                   return "checked";
                }
        }
  
  return "";
}  
   
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
  
    
    req = "UPDATE [Implementation]";
    req+= "   SET ";
    req+= " nomImplementation = "+ "'"+this.nom+ "'" + ",";
    req+= " descImplementation = "+ "'"+this.desc+ "'" + ",";
    req+= " typeImplementation = "+ "'"+this.type+ "'" + "";

    req+= "  WHERE idImplementation = "+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }
   
 
  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;
  
    req = "DELETE FROM Implementation WHERE (idImplementation   = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  String mydesc="";
  if (this.desc != null)
    mydesc=this.desc.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
  else
    mydesc="";
  
    req = "INSERT INTO [Implementation]";
    req+= "           (";
    req+= "            [nomImplementation]";
    req+= "            ,[descImplementation]";
    req+= "            ,[typeImplementation]";

    req+= "           )";    
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.nom+"'" + ",";
    req+= "'"+this.desc+"'" + ",";
    req+= "'"+this.type+"'" + "";
    req+= "            )";

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause ==  -1) return myError;

      req="SELECT  TOP (1) idImplementation FROM   Implementation ORDER BY idImplementation DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("idImplementation");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  }   
    
}
