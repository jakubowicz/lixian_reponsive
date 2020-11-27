/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Composant;
import java.util.Vector;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import accesbase.ErrorSpecific;

/**
 *
 * @author Joël
 */
public class template extends item{
    public datasource in;
    public datasource out;
    public int idItem = -1;
  
    public template(int id){
        this.id= id;
    }
    
public String getRecap(String nomBase,Connexion myCnx, Statement st, String sens ){
    ResultSet rs = null;
    String str="";
    req="SELECT     Field.nom, Field.ListRules, datasource.sens, Field_1.nom AS nom2, datasource.type, datasource_1.type AS type2";
    req+= " FROM         datasource AS datasource_1 INNER JOIN";
    req+= "                       Field AS Field_1 ON datasource_1.id = Field_1.idDatasource RIGHT OUTER JOIN";
    req+= "                       Field INNER JOIN";
    req+= "                       datasource ON Field.idDatasource = datasource.id INNER JOIN";
    req+= "                       template ON template.id = datasource.idTemplate ON Field_1.id = Field.idLinkedField";
    req+= " WHERE     (template.id = "+this.id+") AND (datasource.sens = '"+sens+"')";
    req+= " ORDER BY Field.position";
  
  String padding = "            ";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  String ListeSt = "";
  try {
    while (rs.next()) {
      String nom =  rs.getString("nom");;
      if (nom.length() < 12) nom+= padding.substring(nom.length());
      String rules =  rs.getString("ListRules");;
      String nom2 =  rs.getString("nom2");;
      if (nom2 == null) nom2 = "(   -   )";
      if (rules == null) rules = "";
      str += nom ;
      if (sens.equals("out"))
      str += nom + "\t-> "+ nom2+ "\tFiltre:\t" + rules;
      str += "\n";
    }
  }
  catch (SQLException s) {
    s.getMessage();
  }    
    
    return str;
      
}  

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    req = "SELECT    nom, type, ordre, [desc], idItem FROM  template WHERE id = "+this.id;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      this.nom = rs.getString("nom");
      this.type = rs.getString("type");
      this.ordre = rs.getInt("ordre");
       this.desc = rs.getString("desc");
       this.idItem = rs.getInt("idItem");


      } catch (SQLException s) {s.getMessage(); }
  } 
  
  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;
    int nb=0;
    
  req = "SELECT count(*) as nb";
    req+= " FROM         datasource ";
    req+= " WHERE     idTemplate ="+ this.id;
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  String ListeSt = "";
  try {
    while (rs.next()) {
      nb = rs.getInt(1) ;
    }
  }
  catch (SQLException s) {
    s.getMessage();
  }
  
  if (nb > 0) {
    myError.Detail = "Il existe des datasources";
    myError.cause = -2;
    return myError;
  }  
  
    req = "DELETE FROM template WHERE (id   = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  String mydesc="";
  if (this.desc != null)
    mydesc=this.desc.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
  else
    mydesc="";
  
    req = "INSERT INTO [template]";
    req+= "           ([nom]";
    req+= "            ,[desc]";
    req+= "            ,[ordre]";
    req+= "            ,[idItem]";
    req+= "            )";    
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.nom+"'" + ",";
    req+= "'"+mydesc+"'" + ",";
    req+= "'"+this.ordre+"'" + ",";
    req+= "'"+this.idItem+"'" + "";
    req+= "            )";

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause ==  -1) return myError;

      req="SELECT  TOP (1) id FROM   template ORDER BY id DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("id");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  } 
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
    String description="";

    if (this.desc != null)
    {

      for (int j=0; j < this.desc.length();j++)
      {
        int c = (int)this.desc.charAt(j);
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

    req = "UPDATE [template]";
    req+= "   SET [nom] = "+ "'"+this.nom+ "'" + ",";
    req+= "      [desc] = "+ "'"+description+ "'" + ",";
    req+= "      [ordre] = "+ "'"+this.ordre+ "'" + ",";
    req+= "      [idItem] = "+ "'"+this.idItem+ "'" + "";
    req+= "  WHERE id = "+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }  
    
public ErrorSpecific  Import(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    return myError;
}
public ErrorSpecific  Export(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    int nb_read = 0;
    int nb_write = 0;
    
    in = this.getDatasource(nomBase, myCnx, st, "in");
    out = this.getDatasource(nomBase, myCnx, st, "out");
    
    nb_read=in.read(nomBase,myCnx, st);
    nb_write=out.write(nomBase,myCnx, st, transaction, in.ListeFieldsColumn, in.ListeRows, out.ListeFieldsColumn);
    
    myError.Detail = "Nb Lus = "+nb_read+"/ Nb Ecrits = "+nb_write;
  
    return myError;
}

public datasource getDatasource(String nomBase,Connexion myCnx, Statement st, String sens){
    datasource theDatasource = new datasource(-1);
    
    req = "SELECT      id FROM         datasource WHERE    (sens = '"+sens+"') AND idTemplate ="+this.id;
    ResultSet rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();
      theDatasource.id = rs.getInt("id");
     
      } catch (SQLException s) {s.getMessage(); }
    
    theDatasource.getAllFromBd(nomBase,myCnx,st);
    
    return theDatasource;
}
    
}
