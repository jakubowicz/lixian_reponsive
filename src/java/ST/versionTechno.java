package ST; 

import accesbase.Connexion;
import accesbase.ErrorSpecific;
import java.util.Vector;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public class versionTechno {
  public int id = -1;
  public String nom="";
  public int idMiddleware = -1;
  public Vector ListeLogiciels = new Vector(10);
  
  private String req="";

  public versionTechno() {
  }
  
  public versionTechno(int id) {
      this.id=id;
  }  

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st){

    req="SELECT     nomVersionMW, mwVersionMW";
    req+=" FROM         VersionMW";
    req+=" WHERE     idVersionMW = "+this.id;

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try {
       while (rs.next()) {
         this.nom = rs.getString("nomVersionMW");
         this.idMiddleware = rs.getInt("mwVersionMW");
       }
        } catch (SQLException s) {s.getMessage();}

  } 
  
  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;
    
  req = "SELECT     St.nomSt";
  req+=  " FROM         VersionMW INNER JOIN";
  req+=  "                       ST_MW ON VersionMW.idVersionMW = ST_MW.mwST_MW INNER JOIN";
  req+=  "                       VersionSt ON ST_MW.versionStST_MW = VersionSt.idVersionSt INNER JOIN";
  req+=  "                      St ON VersionSt.stVersionSt = St.idSt";
  req+=  " WHERE     VersionMW.idVersionMW = " + this.id;
  req+=  " ORDER BY St.nomSt";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  String ListeSt = "";
  try {
    while (rs.next()) {
      if (!ListeSt.equals("")) ListeSt += ", ";
      ListeSt += rs.getString(1) ;
    }
  }
  catch (SQLException s) {
    s.getMessage();
  }

  if (!ListeSt.equals("")) {
    myError.Detail = ListeSt;
    myError.cause = -2;
    return myError;
  }

    String req = "DELETE FROM VersionMW WHERE  idVersionMW = " + this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  }  
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){

    ErrorSpecific myError = new ErrorSpecific();
    

    req = "UPDATE    VersionMW  SET ";
    req +=" nomVersionMW ='"+ this.nom + "'";

    req += " WHERE idVersionMW ="+ this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  ErrorSpecific myError = new ErrorSpecific();
    
    req = "INSERT VersionMW ( nomVersionMW, mwVersionMW) ";
    req+=" VALUES (";
    req+="'"+this.nom+"'";
    req+=",";
    req+="'"+this.idMiddleware+"'";
    req+=")";


    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_insert",""+this.id);
    if (myError.cause == -1) return myError;
    
      req="SELECT     TOP (1) idVersionMW FROM   VersionMW ORDER BY idVersionMW DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();}    


    return myError;
  }  
  
  
  public  void getListeLogiciels(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    int nbTotalLc=0;

    String req = "SELECT     idMiddleware, nomMiddleware";
    req+="    FROM         Middleware";
    req+=" WHERE     (LogicielMiddleWare = "+this.id+")";
    req+="     ORDER BY nomMiddleware";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    ListeLogiciels.removeAllElements();
    try {
      while (rs.next()) {
        int idMiddleware= rs.getInt("idMiddleware");
        String nomMiddleware= rs.getString("nomMiddleware");

        Logiciel theLogiciel = new Logiciel();
        theLogiciel.id=idMiddleware;
        theLogiciel.nom=nomMiddleware;
        ListeLogiciels.addElement(theLogiciel);
        }

    } catch (SQLException s) {s.getMessage();}

  }
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("==================================================");
  }

  public static void main(String[] args) {
    versionTechno versiontechno = new versionTechno();
  }
}
