package Documentation; 

import Organisation.Collaborateur;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import accesbase.ErrorSpecific;
import java.util.Vector;

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
public class doc {
  public int id = -1;
  public String chemin = "";
  public String commentaire = "";
  public String cheminIcone = "";
  public String nom = "";
  public String Type = "";
  public int idType= -1;
  public String extension = "";
  
  public int  idDocIcone = -1;
   public int  idDocVersionSt = -1;

  public static Vector ListeTypeDoc = new Vector(10);
  private String req="";


  public doc(){

  }
  
  public doc(int id){
      this.id = id;

  }
  public doc(
  int id,
   String chemin,
   String commentaire,
   String cheminIcone,
   String nom,
   String Type,
   int idType
) {
   if ((commentaire == null) || (commentaire.equals("null")))
     commentaire = "";

   if (cheminIcone == null)
     cheminIcone = "images\\Applis\\Inconnue.png";
   else
     cheminIcone = cheminIcone.replaceAll("\\\\\\\\", "\\\\"); ;
   if (commentaire != null)
     commentaire = commentaire.replaceAll("\r", "").replaceAll("\n",
         "<br>");
   else
          commentaire = "";
   this.id=id;
   this.chemin=chemin;
   this.commentaire=commentaire;
   this.cheminIcone=cheminIcone;
   this.nom=nom;
   this.Type=Type;
   this.idType=idType;

   int Index = chemin.lastIndexOf(".");
   if (Index > -1)
        this.extension = chemin.substring(Index);
  }

public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs=null;

req = "SELECT     idDocType, nom, chemin, commentaire, idDocIcone, idDocVersionSt, extension";
req+= " FROM         Documentation";
req+= " WHERE     (idDoc = "+this.id+")";

  rs = myCnx.ExecReq(st, nomBase, req);

     try {
          rs.next();
          this.idType=rs.getInt("idDocType");
          this.nom=rs.getString("nom");
          if ((this.nom == null) || this.nom.equals("null")) this.nom ="";
          
          this.chemin=rs.getString("chemin");
          if ((this.chemin == null) || this.chemin.equals("null")) this.chemin ="";
          
          this.commentaire=rs.getString("commentaire");
          if ((this.commentaire == null) || this.commentaire.equals("null")) this.commentaire ="";
          
          this.idDocIcone=rs.getInt("idDocIcone");
          this.idDocVersionSt=rs.getInt("idDocVersionSt");
          
          this.extension=rs.getString("extension");
          if ((this.extension == null) || this.extension.equals("null")) this.extension ="";
          
         }

         catch (SQLException s) {
           s.getMessage();
         }
    
}  
  public void getIcone(String nomBase,Connexion myCnx, Statement st){

    if (this.chemin == null) return;
    int Index = this.chemin.lastIndexOf(".");
    if (Index <=0) return;

    this.extension = this.chemin.substring(Index);

        String req="SELECT     extension, CheminIcone FROM  IconeDocumentation WHERE (extension = '"+this.extension+"')";
        ResultSet rs = myCnx.ExecReq(st, nomBase, req);
        doc theDoc = null;
        try {
           while (rs.next()) {
             this.cheminIcone = rs.getString("CheminIcone");
           }
            } catch (SQLException s) {s.getMessage();}

  }

  public static void getListeTypeDoc(String nomBase,Connexion myCnx, Statement st){

    String req="SELECT idTypeDoc, nomType FROM TypeDocumentation ORDER BY nomType";
    ListeTypeDoc.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    doc theDoc = null;
    try {
       while (rs.next()) {
         int idTypeDoc = rs.getInt(1);
         String nomType = rs.getString(2);
         theDoc = new doc();
         theDoc.idType = idTypeDoc;
         theDoc.Type = nomType;
         //theST.dump();
         ListeTypeDoc.addElement(theDoc);
       }
        } catch (SQLException s) {s.getMessage();}

  }
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
    String description="";

    if (this.commentaire != null)
    {
      for (int j=0; j < this.commentaire.length();j++)
      {
        int c = (int)this.commentaire.charAt(j);
        if (c != 34)
          {
            description += this.commentaire.charAt(j);
          }
          else
          {
            description +="'";
          }
      }
      description = description.replaceAll("\r","").replaceAll("'","''");
         }

    req = "UPDATE [Documentation]";
    req+="    SET ";
    req+="    [idDocType] = '"+ this.idType+ "'";
    req+="   ,[chemin] = '"+ this.chemin+ "'";
    req+="   ,[commentaire] = '"+ description+ "'";
    req+="   ,[idDocVersionSt] = '"+ this.idDocVersionSt+ "'";
    req+="   ,[extension] = '"+ this.extension+ "'";
    req+=" WHERE idDoc=" + this.id;

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_update",""+this.id);

    return myError;
  }

  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){

    req = "DELETE FROM Documentation WHERE (idDoc  = '"+this.id+"')";
    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction, int idVersionSt){
    String description="";

    if (this.commentaire != null)
    {
      for (int j=0; j < this.commentaire.length();j++)
      {
        int c = (int)this.commentaire.charAt(j);
        if (c != 34)
          {
            description += this.commentaire.charAt(j);
          }
          else
          {
            description +="'";
          }
      }
      description = description.replaceAll("\r","").replaceAll("'","''");
         }
  
    req = "INSERT INTO [Documentation]";
    req+= "           ([idDocType]";
    req+= "            ,[chemin]";
    req+= "            ,[commentaire]";
    req+= "            ,[idDocVersionSt]";
    req+= "            ,[extension]";
    req+= "            )";    
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.idType+"'" + ",";
    req+= "'"+chemin+"'" + ",";
    req+= "'"+description+"'" + ",";
    req+= "'"+this.idDocVersionSt+"'" + ",";
    req+= "'"+this.extension+"'" + "";
 
    req+= "            )";

      ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
      if (myError.cause == -1) return myError;
      
      req="SELECT  idDoc FROM   Documentation ORDER BY idDoc DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("idDoc");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  }  
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("chemin="+this.chemin);
    System.out.println("cheminIcone="+this.cheminIcone);
    System.out.println("==================================================");
  }
}
