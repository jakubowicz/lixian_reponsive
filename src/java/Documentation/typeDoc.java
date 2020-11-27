/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Documentation;
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
public class typeDoc extends item {
  public typeDoc(int id) {
    this.id = id;
  }    
    
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
       req = "SELECT     nomType, descType FROM         TypeDocumentation  WHERE     idTypeDoc = " + this.id;
     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       
       this.nom = rs.getString("nomType");
       this.desc = rs.getString("descType");

       } catch (SQLException s) {s.getMessage(); }
  }  
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
    String description="";

    if (this.desc != null)
    {
      //description = theInterface.descInterface.replaceAll("\r","").replaceAll("0x22","'").replaceAll("\u0092","'").replaceAll("'","''");
      for (int j=0; j < this.desc.length();j++)
      {
        int c = (int)this.desc.charAt(j);
        //System.out.println("j: "+j+"---"+theInterface.descInterface.charAt(j) + " --" +(int)theInterface.descInterface.charAt(j));
        if (c != 34)
          {
            description += this.desc.charAt(j);
          }
          else
          {
            description +="'";
          }
      }
      description = description.replaceAll("\r","").replaceAll("'","''");
     }

    req = "UPDATE [TypeDocumentation]";
    req+= "   SET [nomType] = "+ "'"+this.nom+ "'" + ",";
    req+= "      [descType] = "+ "'"+description+ "'" + "";
    req+= "  WHERE idTypeDoc = "+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }

  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();

  
    req = "DELETE FROM TypeDocumentation WHERE (idTypeDoc   = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  String mydesc="";
  if (this.desc != null)
    mydesc=this.desc.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
  else
    mydesc="";
  
    req = "INSERT INTO [TypeDocumentation]";
    req+= "           ([nomType]";
    req+= "            ,[descType]";
    req+= "            )";    
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.nom+"'" + ",";
    req+= "'"+mydesc+"'" + "";
    req+= "            )";

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause ==  -1) return myError;

      req="SELECT  TOP (1) idTypeDoc FROM   TypeDocumentation ORDER BY idTypeDoc DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("idTypeDoc");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  }      
    
}

