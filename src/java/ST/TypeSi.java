package ST; 

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
public class TypeSi {
  public int id;
   public int idSi;
  public String nom="";
   public String desc="";
  private String req;
  private String Type="";
  
  public String couleurSI="";
  public String couleurdefautSI="";
          

  public TypeSi(int id, String nom) {
    this.id = id;
    this.nom = nom;
  }

  public TypeSi(int id) {
    this.id = id;
    this.Type = "id";
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
       req = "SELECT     nomTypeSi, descTypeSi, siTypesi, couleurSI, couleurdefautSI";
       req+= " FROM         TypeSi";
       req+= " WHERE     idTypeSi = " + this.id;

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       
       this.nom = rs.getString("nomTypeSi");
       if (this.nom == null) this.nom="";
       this.desc = rs.getString("descTypeSi");
       if (this.desc == null) this.desc="";
       
       this.couleurSI = rs.getString("couleurSI");
       this.couleurdefautSI = rs.getString("couleurdefautSI");


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

    req = "UPDATE [typeSi]";
    req+= "   SET [nomTypeSi] = "+ "'"+this.nom+ "'" + ",";
    req+= "      [descTypeSi] = "+ "'"+description+ "'" + ",";
    req+= "      [siTypesi] = "+ "'"+this.idSi+ "'" + ",";
    req+= "       [couleurSI] = "+ "'"+this.couleurSI+ "'" + ",";
    req+= "       [couleurdefautSI] = "+ "'"+this.couleurdefautSI+ "'" + "";
    req+= "  WHERE idTypeSi = "+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }

  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;
    int nb=0;
    
  req = "SELECT     St.nomSt";
    req+= " FROM         St INNER JOIN";
    req+= "                       VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req+= "                       TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi";
    req+= " WHERE     TypeSi.idTypeSi ="+ this.id;
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
  
    req = "DELETE FROM TypeSi WHERE (idTypeSi   = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  String mydesc="";
  if (this.desc != null)
    mydesc=this.desc.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
  else
    mydesc="";
  
    req = "INSERT INTO [typeSi]";
    req+= "           ([nomTypeSi]";
    req+= "            ,[descTypeSi]";
    req+= "            ,[siTypesi]";
    req+= "            ,[couleurSI]";
    req+= "            ,[couleurdefautSI])";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.nom+"'" + ",";
    req+= "'"+mydesc+"'" + ",";
    req+= "'"+this.idSi+"'" + ",";
    req+= "'"+this.couleurSI+"'" + ",";

    req+= "'"+this.couleurdefautSI+"'";
    req+= "            )";

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause ==  -1) return myError;

      req="SELECT  TOP (1) idTypeSi FROM   TypeSi ORDER BY idTypeSi DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("idTypeSi");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  }  
  
  public static void main(String[] args) {
    TypeSi typesi = new TypeSi(0,""); 
  }
}
