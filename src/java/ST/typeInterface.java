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

/**
 *
 * @author Joël
 */
public class typeInterface extends item {
    public String ST_1 = "";
    public int isEquipement_1 = 0;
    public int isComposant_1 = 0;
    public int isSt_1 = 0;
    public int isAppli_1 = 0;
    public int isActeur_1 = 0;
    
    public String ST_2 = "";
    public int isEquipement_2 = 0;
    public int isComposant_2 = 0;
    public int isSt_2 = 0;
    public int isAppli_2 = 0;
    public int isActeur_2 = 0;  
    
    public String TypeInterface = "";
    public String sens = "";
    
    public int idSt_1 = -1;   
    public int idSt_2 = -1;  
    
    public String idTypeInterface = "";
    
  public typeInterface(int id) {
    this.id = id;
  } 
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
       req = "SELECT     AutomateInterface.ST_1, AutomateInterface.isEquipement_1, AutomateInterface.isComposant_1, AutomateInterface.isSt_1, AutomateInterface.isAppli_1, AutomateInterface.isActeur_1, ";
       req += "                      AutomateInterface.ST_2, AutomateInterface.isEquipement_2, AutomateInterface.isComposant_2, AutomateInterface.isSt_2, AutomateInterface.isAppli_2, AutomateInterface.isActeur_2, ";
       req += "                        AutomateInterface.[Type Interface], AutomateInterface.Sens, AutomateInterface.id, Config.Ordre AS idSt_1, Config_1.Ordre AS idSt_2";
       req += "  FROM         AutomateInterface INNER JOIN";
       req += "                        Config ON AutomateInterface.ST_1 = Config.valeur INNER JOIN";
       req += "                        Config AS Config_1 ON AutomateInterface.ST_2 = Config_1.valeur";
       req += "  WHERE     AutomateInterface.id = "+this.id;

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       
       this.ST_1 = rs.getString("ST_1");
       this.isEquipement_1 = rs.getInt("isEquipement_1");
       this.isComposant_1 = rs.getInt("isComposant_1");
       this.isSt_1 = rs.getInt("isSt_1");
       this.isAppli_1 = rs.getInt("isAppli_1");
       this.isActeur_1 = rs.getInt("isActeur_1");
       
       this.ST_2 = rs.getString("ST_2");
       this.isEquipement_2 = rs.getInt("isEquipement_2");
       this.isComposant_2 = rs.getInt("isComposant_2");
       this.isSt_2 = rs.getInt("isSt_2");
       this.isAppli_2 = rs.getInt("isAppli_2");
       this.isActeur_2 = rs.getInt("isActeur_2");
       
       this.TypeInterface = rs.getString("Type Interface");
       this.sens = rs.getString("sens");
       
       this.idSt_1 = rs.getInt("idSt_1");
       this.idSt_2 = rs.getInt("idSt_2");


       } catch (SQLException s) {s.getMessage(); }
  } 
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
  
    
    req = "UPDATE [AutomateInterface]";
    req+= "   SET ";
    req+= " ST_1 = "+ "'"+this.ST_1+ "'" + ",";
    req+= " isEquipement_1 = "+ "'"+this.isEquipement_1+ "'" + ",";
    req+= " isComposant_1 = "+ "'"+this.isComposant_1+ "'" + ",";
    req+= " isSt_1 = "+ "'"+this.isSt_1+ "'" + ",";
    req+= " isAppli_1 = "+ "'"+this.isAppli_1+ "'" + ",";
    req+= " isActeur_1 = "+ "'"+this.isActeur_1+ "'" + ",";
    req+= " ST_2 = "+ "'"+this.ST_2+ "'" + ",";
    req+= " isEquipement_2 = "+ "'"+this.isEquipement_2+ "'" + ",";
    req+= " isComposant_2 = "+ "'"+this.isComposant_2+ "'" + ",";
    req+= " isSt_2 = "+ "'"+this.isSt_2+ "'" + ",";
    req+= " isAppli_2 = "+ "'"+this.isAppli_2+ "'" + ",";
    req+= " isActeur_2 = "+ "'"+this.isActeur_2+ "'" + ",";    
    req+= " Sens = "+ "'"+this.sens+ "'" + ",";
    req+= "  [Type Interface] = "+ "'"+this.idTypeInterface+ "'" + "";

    req+= "  WHERE id = "+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }

  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;
    int nb=0;
    String ListeSt = "";
    /*
    
    req = "SELECT     St.nomSt";
    req+= " FROM         St INNER JOIN";
    req+= "                       VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req+= "                       TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi";
    req+= " WHERE     TypeSi.idTypeSi ="+ this.id;
    req+= " ORDER BY St.nomSt";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  
  try {
    while (rs.next()) {
      if (!ListeSt.equals("")) ListeSt += ", ";else ListeSt += " Produits: ";
      ListeSt += rs.getString(1) ;
    }
  }
  catch (SQLException s) {
    s.getMessage();
  }
  */
  
  if (!ListeSt.equals("")) {
    myError.Detail = ListeSt;
    myError.cause = -2;
    return myError;
  }  
  
    req = "DELETE FROM AutomateInterface WHERE (id   = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  String mydesc="";
  if (this.desc != null)
    mydesc=this.desc.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
  else
    mydesc="";
  
    req = "INSERT INTO [AutomateInterface]";
    req+= "           (";
    req+= "            [ST_1]";
    req+= "            ,[isEquipement_1]";
    req+= "            ,[isComposant_1]";
    req+= "            ,[isSt_1]";
    req+= "            ,[isAppli_1]";
    req+= "            ,[isActeur_1]";
    req+= "            ,[ST_2]";
    req+= "            ,[isEquipement_2]";
    req+= "            ,[isComposant_2]";
    req+= "            ,[isSt_2]";
    req+= "            ,[isAppli_2]";
    req+= "            ,[isActeur_2]";
    req+= "            ,[Type Interface]";
    req+= "            ,[Sens]";  
    req+= "           )";    
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.ST_1+"'" + ",";
    req+= "'"+this.isEquipement_1+"'" + ",";
    req+= "'"+this.isComposant_1+"'" + ",";
    req+= "'"+this.isSt_1+"'" + ",";
    req+= "'"+this.isAppli_1+"'" + ",";
    req+= "'"+this.isActeur_1+"'" + ",";
    req+= "'"+this.ST_2+"'" + ",";
    req+= "'"+this.isEquipement_2+"'" + ",";
    req+= "'"+this.isComposant_2+"'" + ",";
    req+= "'"+this.isSt_2+"'" + ",";
    req+= "'"+this.isAppli_2+"'" + ",";
    req+= "'"+this.isActeur_2+"'" + ",";
    req+= "'"+this.idTypeInterface+"'" + ",";
    req+= "'"+this.sens+"'" + "";    

    req+= "            )";

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause ==  -1) return myError;

      req="SELECT  TOP (1) id FROM   AutomateInterface ORDER BY id DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("id");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  }   
    
}
