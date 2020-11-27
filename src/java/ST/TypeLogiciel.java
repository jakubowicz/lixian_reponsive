package ST; 

import static ST.Logiciel.ListeLogiciel;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import accesbase.Connexion;
import Composant.item;
import accesbase.ErrorSpecific;

/**
 * <p>Titre : </p>
 *
 * <p>Description : </p>
 *
 * <p>Copyright : Copyright (c) 2015</p>
 *
 * <p>Societe: </p>
 *
 * @author Joel Jakubowicz
 * @version 1.0
 */
public class TypeLogiciel extends item {
    public int visible = 1;

  public TypeLogiciel(String nom) {
    this.nom = nom;
  }
  
  public TypeLogiciel(int id) {
    this.id = id;
  }  

  public int getId(String nomBase,Connexion myCnx,Statement st ){
    String req= req = "SELECT idLogiciel, nomLogiciel FROM  Logiciel WHERE nomLogiciel = '"+this.nom+"'";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    try {
       while (rs.next()) {
         this.id = rs.getInt("idLogiciel");
       }
        } catch (SQLException s) {s.getMessage();}

        return this.id;

}
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st){

    req="SELECT     nomLogiciel, descLogiciel, visible";
    req+= " FROM         Logiciel";
    req+= " WHERE     idLogiciel = "+this.id;

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try {
       while (rs.next()) {
         this.nom = rs.getString("nomLogiciel");
         this.desc = rs.getString("descLogiciel");

       }
        } catch (SQLException s) {s.getMessage();}

  } 
  
  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;

    String req = "DELETE FROM Logiciel WHERE  idLogiciel = " + this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  }  
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){

    ErrorSpecific myError = new ErrorSpecific();
    
    if (this.desc != null)
        this.desc=this.desc.replaceAll("\u0092","'").replaceAll("'","''");
 
    req = "UPDATE    Logiciel  SET ";
    req +=" nomLogiciel ='"+ this.nom + "', ";
    req +=" descLogiciel ='"+ this.desc + "'";

    req += " WHERE idLogiciel ="+ this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  ErrorSpecific myError = new ErrorSpecific();
    if (this.desc != null)
        this.desc=this.desc.replaceAll("\u0092","'").replaceAll("'","''");
    
    req = "INSERT Logiciel (nomLogiciel, descLogiciel, visible) ";
    req+=" VALUES (";
    req+="'"+this.nom+"'";
    req+=",";
    req+="'"+this.desc+"'";
    req+=",";
    req+="'"+this.visible+"'";    
    req+=")";


    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_insert",""+this.id);
    if (myError.cause == -1) return myError;
    
      req="SELECT     TOP (1) idLogiciel FROM   Logiciel ORDER BY idLogiciel DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();}    


    return myError;
  }  

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("==================================================");
  }


  public static void main(String[] args) {
    TypeLogiciel typelogiciel = new TypeLogiciel("");
  }
}
