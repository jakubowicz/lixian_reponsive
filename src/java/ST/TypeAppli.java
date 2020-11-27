/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ST;
import Composant.*;
import accesbase.Connexion;
import accesbase.ErrorSpecific;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Joël
 */
public class TypeAppli extends item {
    public String formeTypeAppli = "images/Logos/iconeInconnu.gif";
    public String formedefautTypeAppli = "images/Logos/iconeInconnu.gif";
    public int isEquipement = 0;
    public int isComposant = 0;
    public int isSt = 0;
    public int isAppli = 0;
    public int isActeur = 0;
    
    private String req="";
    
    
//idTypeAppli, nomTypeAppli, formeTypeAppli, formedefautTypeAppli, isEquipement, isComposant, isSt, isAppli, isActeur
  public TypeAppli(int id) {
    this.id = id;
    this.nom="";
  }
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
       req = "SELECT      nomTypeAppli, formeTypeAppli, formedefautTypeAppli, isEquipement, isComposant, isSt, isAppli, isActeur FROM         TypeAppli";
       req+= " WHERE     idTypeAppli = "+ this.id;


     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       
       this.nom = rs.getString("nomTypeAppli");
       if (this.nom == null) this.nom="";
       
       this.formeTypeAppli = rs.getString("formeTypeAppli");
       if ((this.formeTypeAppli == null) || (this.formeTypeAppli.equals("")) )
           this.formeTypeAppli = "images/Logos/iconeInconnu.gif";
       
       this.formedefautTypeAppli =this.formeTypeAppli;
       
       this.isEquipement = rs.getInt("isEquipement");
       this.isComposant = rs.getInt("isComposant");
       this.isSt = rs.getInt("isSt");
       this.isAppli = rs.getInt("isAppli");
       this.isActeur = rs.getInt("isActeur");
       
       } catch (SQLException s) {s.getMessage(); }
  }  
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
    String description="";


    req = "UPDATE [TypeAppli]";
    req+= "   SET [nomTypeAppli] = "+ "'"+this.nom+ "'" + ",";
    req+= "      [formeTypeAppli] = "+ "'"+this.formeTypeAppli+ "'" + ",";
    req+= "       [formedefautTypeAppli] = "+ "'"+this.formedefautTypeAppli+ "'" + ",";
    req+= "       [isEquipement] = "+ "'"+this.isEquipement+ "'" + ",";
    req+= "       [isComposant] = "+ "'"+this.isComposant+ "'" + ",";
    req+= "       [isSt] = "+ "'"+this.isSt+ "'" + ",";
    req+= "       [isAppli] = "+ "'"+this.isAppli+ "'" + ",";
    req+= "       [isActeur] = "+ "'"+this.isActeur+ "'" ;

    req+= "  WHERE idTypeAppli= "+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }

  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;
    int nb=0;
    
  req = "SELECT     St.nomSt";
    req+= " FROM         TypeAppli INNER JOIN";
    req+= "                       St ON TypeAppli.idTypeAppli = St.typeAppliSt";
    req+= " WHERE     TypeAppli.idTypeAppli = "+ this.id;;
    req+= " ORDER BY St.nomSt";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  String ListeSt = "";
  try {
    while (rs.next()) {
      if (!ListeSt.equals("")) ListeSt += ", ";else ListeSt += " Produits: ";
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
  
    req = "DELETE FROM TypeAppli WHERE (idTypeAppli  = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  
    req = "INSERT INTO [TypeAppli]";
    req+= "           ([nomTypeAppli]";
    req+= "            ,[formeTypeAppli]";
    req+= "            ,[formedefautTypeAppli]";
    req+= "            ,[isEquipement]";
    req+= "            ,[isComposant]";
    req+= "            ,[isSt]";
    req+= "            ,[isAppli]";
    req+= "            ,[isActeur]";
    req+=")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.nom+"'" + ",";
    req+= "'"+this.formeTypeAppli+"'" + ",";
    req+= "'"+this.formedefautTypeAppli+"'" + ",";
    req+= "'"+this.isEquipement+"'" + ",";
    req+= "'"+this.isComposant+"'"+ ",";
    req+= "'"+this.isSt+"'"+ ",";
    req+= "'"+this.isAppli+"'"+ ",";
    req+= "'"+this.isActeur+"'";
    req+= "            )";


      ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
      if (myError.cause == -1) return myError;

      req="SELECT  idTypeAppli FROM   TypeAppli ORDER BY idTypeAppli DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("idTypeAppli");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  }  
}
