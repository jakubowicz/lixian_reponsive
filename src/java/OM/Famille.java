package OM;

import accesbase.Connexion;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.ErrorSpecific;

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
public class Famille {
  public int id;
  public String nom="";
  public String description="";
  public String nomPackage="";
  public int idPackage=-1;

  private String req="";

  public Famille() {
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
     req= " SELECT     idFamilleOM, nomFamilleOM, descFamilleOM";
     req+=" FROM         FamilleOM";
     req+=" WHERE     (idFamilleOM = "+this.id+")";

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       this.id = rs.getInt("idFamilleOM");
       this.nom = rs.getString("nomFamilleOM");
       this.description = rs.getString("descFamilleOM");

       } catch (SQLException s) {s.getMessage(); }
  }

  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();
           req = "INSERT FamilleOM (";
             req+="nomFamilleOM"+",";
             req+="descFamilleOM";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+this.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
             req+="'"+this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
           req+=")";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

          req = "SELECT  idFamilleOM FROM   FamilleOM WHERE nomFamilleOM = '"+this.nom+"'";
          rs = myCnx.ExecReq(st, nomBase, req);
          try {rs.next(); this.id =rs.getInt("idFamilleOM"); } catch (SQLException s) {s.getMessage();}

    return myError;
  }

  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();      
    req = "UPDATE FamilleOM SET ";

    req+="nomFamilleOM='"+this.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
    req+="descFamilleOM='"+this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";

    req+=" WHERE idFamilleOM="+this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

     return myError;

}
  
  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
      
    ErrorSpecific myError = new ErrorSpecific();      
    req = "DELETE FROM  FamilleOM";
    req+=" WHERE idFamilleOM="+this.id;
    
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

     return myError;

}  


  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id=" + this.id);
    System.out.println("nom=" + this.nom);
    System.out.println("nomPackage=" + this.nomPackage);
    System.out.println("idPackage=" + this.idPackage);
    System.out.println("==================================================");
  }
  public static void main(String[] args) {
    Famille famille = new Famille();
  }
}
