/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesbase;
import Composant.item;
import static accesbase.Connexion.ExecReq;
import static accesbase.Connexion.nomBase;
import static accesbase.Connexion.trace;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import Fichier.*;
/**
 *
 * @author Joël
 */
public class table extends item{
public Vector ListeChamps = new Vector(10);    
public int identity = 1;

public table (String nom){
    this.nom = nom;
    this.ListeChamps.removeAllElements();
}
    public void addChamp(String nom, String type, boolean isKey){
        champ theChamp = new champ(nom,type, isKey );
        this.ListeChamps.addElement(theChamp);
    }
    
public void bd_dump(String nomBase,Connexion myCnx, Statement st, String transaction, FichierTexte theFichier, db theBase){
    ErrorSpecific myError = new ErrorSpecific();
     ResultSet rs = null;

    //this.isIdentity(nomBase, myCnx, st);
    req = "exec Gen_Insert_InstructionsWithPk 'dbo', '"+this.nom+"'";

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  String ListeSt = "";
  
  theFichier.write("use "+theBase.nom);
  theFichier.write("--- Fichier: "+this.nom);
  theFichier.write("print '---------------- DEBUT dump: "+ this.nom +" -----------------------'");
  theFichier.write("DELETE FROM "+this.nom);
  if (this.identity == 1)
    theFichier.write("SET IDENTITY_INSERT "+this.nom+" ON");
  System.out.println(ListeSt);
  try {
    while (rs.next()) {
      ListeSt = rs.getString(1) ;

      if (ListeSt != null)
      {
          ListeSt = ListeSt.replaceAll("VALUES \\(,", "VALUES \\(");
      }
        theFichier.write(ListeSt);
    }

  }
    
  catch (SQLException s) {
    s.getMessage();
    myError.cause = 1;
    return;
  } 
  if (this.identity == 1)  
    theFichier.write("SET IDENTITY_INSERT "+this.nom+" OFF");
  
  theFichier.write("print '---------------- FIN dump: "+ this.nom +" -----------------------'");
  
}

public void bd_dump(String nomBase,Connexion myCnx, Statement st, FichierTexte theFichier, db theBase){
     ResultSet rs = null;
    req = "exec Gen_Insert_InstructionsWithPk 'dbo', '"+this.nom+"'";

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  String ListeSt = "";
  
  theFichier.write("use "+theBase.nom);
  theFichier.write("--- Fichier: "+this.nom);
  theFichier.write("print '---------------- DEBUT dump: "+ this.nom +" -----------------------'");
  theFichier.write("DELETE FROM "+this.nom);
  if (this.identity == 1)
    theFichier.write("SET IDENTITY_INSERT "+this.nom+" ON");
  System.out.println(ListeSt);
  try {
    while (rs.next()) {
      ListeSt = rs.getString(1) ;

      if (ListeSt != null)
      {
          ListeSt = ListeSt.replaceAll("VALUES \\(,", "VALUES \\(");
      }
        theFichier.write(ListeSt);
    }

  }
    
  catch (SQLException s) {
    s.getMessage();
    return;
  } 
  if (this.identity == 1)  
    theFichier.write("SET IDENTITY_INSERT "+this.nom+" OFF");
  
  theFichier.write("print '---------------- FIN dump: "+ this.nom +" -----------------------'");
  
}

public void bd_progress(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
     ResultSet rs = null;
     
     String listeTable = Config.getValueFromNom(nomBase,myCnx,st,"LISTE_TABLES");
     if (!listeTable.equals("")) listeTable+=";";
     listeTable+=this.nom;

     myError = Config.bd_UpdateValueFromNom(nomBase,myCnx,st,"LISTE_TABLES", "LISTE_TABLES",listeTable);


  }

public void bd_progress(String nomBase,Connexion myCnx, Statement st){
     ResultSet rs = null;
     
     String listeTable = Config.getValueFromNom(nomBase,myCnx,st,"LISTE_TABLES");
     if (!listeTable.equals("")) listeTable+=";";
     listeTable+=this.nom;

    req = "UPDATE Config SET ";
    req+="valeur ='"+listeTable+"'";
    req+=" WHERE     (nom = '"+"LISTE_TABLES"+"')";
    myCnx.ExecUpdate(st,nomBase,req,false);

  }
   


public void isIdentity(String nomBase,Connexion myCnx, Statement st){
    
    ErrorSpecific myError = new ErrorSpecific();
     ResultSet rs = null;

    req = "SET IDENTITY_INSERT "+this.nom+" OFF";   
   try {
         st.execute(req);
         myError.cause=0;
       }
       catch (Exception s)
       {
         trace(nomBase, "*** NOT IDENTY*** req", this.nom);
         this.identity = 0;
         return;
         //Unconnect(st);


         }
   this.identity = 1;

}


public String writeFile(String ligne){

  if (ligne != null)
  {
      ligne = ligne.replaceAll("VALUES \\(,", "VALUES \\(");
  }
  return ligne;
}

}
