package Projet; 

import Composant.idNom;
import PO.LignePO;
import accesbase.Connexion;
import Documentation.doc;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import General.Utils;
import java.util.*;
import accesbase.transaction;
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
public class Besoin extends idNom{
  public int type = -1;

  public int IntegDevis = -1;

  public String Commentaire = "";
  public String idBesoin = "";
  public String nomTypeIntegration = "";
  private String req="";
  public int idRoadmap = -1;
  public String detail = "";
  public int isExigence = 1;
  public String version = "";
  public String image = "";

  public int nbNiveau = 1;
  public int niv1= -1;
  public int niv2= -1;
  public int niv3= -1;
  public int niv4= -1;


  public int niv1_old= -1;
  public int niv2_old= -1;
  public int niv3_old= -1;
  public int niv4_old= -1;


  public String chapitre = "";

  public int Complexite= -1;
  public String nomTypeComplexite= "";

  public float Cout= 0;

  public Vector ListeExigences = new Vector(10);


  public Besoin() {
  }

  public Besoin(int id) {
    this.id = id;
    this.description = "";
    this.nom = "";
  }

  public void getListeExigencesFilles(String nomBase,Connexion myCnx, Statement st){
    String ListNomSt="";
    ResultSet rs;

    req="SELECT     id, niv1, niv2, niv3, niv4, nbNiveau";
    req+="    FROM         BesoinsUtilisateur";
    req+=" WHERE     (idRoadmap = "+this.idRoadmap+")  AND (nbNiveau > "+this.nbNiveau+")";

    if (this.nbNiveau == 1)
      req+=" AND (niv1 = "+this.niv1_old+")";
    else if (this.nbNiveau == 2)
    {
      req += " AND (niv1 = " + this.niv1_old + ")";
      req += " AND (niv2 = " + this.niv2_old + ")";
    }
    else if (this.nbNiveau == 3)
    {
      req += " AND (niv1 = " + this.niv1_old + ")";
      req += " AND (niv2 = " + this.niv2_old + ")";
      req += " AND (niv3 = " + this.niv3_old + ")";
    }


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    int i=0;
    try {
      while (rs.next()) {
        Besoin theBesoin = new Besoin(rs.getInt("id"));
        theBesoin.niv1 = rs.getInt("niv1");
        theBesoin.niv2 = rs.getInt("niv2");
        theBesoin.niv3 = rs.getInt("niv3");
        theBesoin.niv4 = rs.getInt("niv4");
        theBesoin.nbNiveau = rs.getInt("nbNiveau");
        this.ListeExigences.addElement(theBesoin);

      }
    }
    catch (Exception s) {
    }

  }


  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
     String chemin = "";

     req = "SELECT     id, typeBesoin, nom, description, integDevis, Commentaires, idBesoin, idRoadmap, detail, isExigence, version, image, nbNiveau, niv1, niv2, niv3, niv4, chapitre, Complexite";
     req+= "    FROM         BesoinsUtilisateur";
     req+= " WHERE     (id = "+this.id+")";


     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       this.id = rs.getInt("id");
       this.type = rs.getInt("typeBesoin");
       this.nom = rs.getString("nom");
       this.description = rs.getString("description");
       if (this.description == null) this.description = "";
       this.nomTypeIntegration = rs.getString("integDevis");
       this.Commentaire = rs.getString("Commentaires");
       if (this.Commentaire == null) this.Commentaire = "";
       this.idBesoin = rs.getString("idBesoin");
       if (this.idBesoin == null) this.idBesoin = "";
       this.idRoadmap = rs.getInt("idRoadmap");
       this.detail = rs.getString("detail");
       if (this.detail == null) this.detail = "";
       this.isExigence = rs.getInt("isExigence");
       this.version = rs.getString("version");
       if (this.version == null) this.version = "";
       this.image = rs.getString("image");
       if (this.image == null) this.image = "";

       this.nbNiveau = rs.getInt("nbNiveau");
       this.niv1 = rs.getInt("niv1");
       this.niv2 = rs.getInt("niv2");
       this.niv3 = rs.getInt("niv3");
       this.niv4 = rs.getInt("niv4");

       this.niv1_old=this.niv1;
       this.niv2_old=this.niv2;
       this.niv3_old=this.niv3;
       this.niv4_old=this.niv4;

       if (this.nbNiveau == 1)
         this.chapitre = ""+ this.niv1;
       else if(this.nbNiveau == 2)
           this.chapitre = ""+ this.niv1 + "." +this.niv2;
       else if(this.nbNiveau == 3)
           this.chapitre = ""+ this.niv1 + "." +this.niv2+ "." +this.niv3;
       else if(this.nbNiveau == 4)
           this.chapitre = ""+ this.niv1 + "." +this.niv2+ "." +this.niv3+ "." +this.niv4;

       this.Complexite = rs.getInt("Complexite");
       this.Cout = rs.getFloat("Cout");

       } catch (SQLException s) {s.getMessage(); }


  }

  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){

    String chapitre="";
    if (this.chapitre != null)
      chapitre=this.chapitre.replaceAll("\u0092","'").replaceAll("'"," ").replaceAll("&","et").replace('+',' ');
    else
    chapitre="";

  String version="";
  if (this.version != null)
    version=this.version.replaceAll("\u0092","'").replaceAll("'"," ").replaceAll("&","et").replace('+',' ');
  else
    version="";

  String description="";
  if (this.description != null)
    description=this.description.replaceAll("\u0092","'").replaceAll("'","''");
  else
    description="";

  String detail="";
  if (this.detail != null)
    detail=this.detail.replaceAll("\u0092","'").replaceAll("'","''");
  else
    detail="";

  if (this.nbNiveau == 1)
  {
    this.chapitre = "" + this.niv1;
    this.niv2 = -1;
    this.niv3 = -1;
    this.niv4 = -1;
  }
  else if(this.nbNiveau == 2)
  {
    this.chapitre = "" + this.niv1 + "." + this.niv2;
    this.niv3 = -1;
    this.niv4 = -1;
  }
  else if(this.nbNiveau == 3)
  {
    this.chapitre = "" + this.niv1 + "." + this.niv2 + "." + this.niv3;
    this.niv4 = -1;
  }
  else if(this.nbNiveau == 4)
  {
    this.chapitre = "" + this.niv1 + "." + this.niv2 + "." + this.niv3 + "." +
        this.niv4;
  }

  if (this.Complexite == -1) this.Complexite = 1;

    req = "INSERT BesoinsUtilisateur (";
    req+="typeBesoin";
    req+=",";
    req+="description";
    req+=",";
    req+="integDevis";
    req+=",";
    req+="idBesoin";
    req+=",";
    req+="idRoadmap";
    req+=",";
    req+="detail";
    req+=",";
    req+="isExigence";
    req+=",";
    req+="version";
    req+=",";
    req+="image";
    req+=",";
    req+="nbNiveau";
    req+=",";
    req+="niv1";
    req+=",";
    req+="niv2";
    req+=",";
    req+="niv3";
    req+=",";
    req+="niv4";
    req+=",";
    req+="chapitre";
    req+=",";
    req+="Complexite";
    req+="   )";
    req+=" VALUES (";
    req+="'"+this.type+"'";
    req+=",";
    req+="'"+description+"'";
    req+=",";
    req+="'"+this.IntegDevis+"'";
    req+=",";
    req+="'"+this.idBesoin+"'";
    req+=",";
    req+="'"+this.idRoadmap+"'";
    req+=",";
    req+="'"+detail+"'";
    req+=",";
    req+="'"+this.isExigence+"'";
    req+=",";
    req+="'"+version+"'";
    req+=",";
    req+="'"+this.image+"'";
    req+=",";
    req+="'"+this.nbNiveau+"'";
    req+=",";
    req+="'"+this.niv1+"'";
    req+=",";
    req+="'"+this.niv2+"'";
    req+=",";
    req+="'"+this.niv3+"'";
    req+=",";
    req+="'"+this.niv4+"'";
    req+=",";
    req+="'"+this.chapitre+"'";
    req+=",";
    req+="'"+this.Complexite+"'";
    req+=")";
    
    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);

      req="SELECT     TOP (1) id FROM   BesoinsUtilisateur ORDER BY id DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();}

    return myError;
  }

  public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    req = "DELETE FROM BesoinsUtilisateur WHERE (id  = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  }

public Vector bd_saveOldListe(String nomBase,Connexion myCnx, Statement st, Besoin theBesoinSave, String transaction){
    // recherche de l'entr�e qui a le m�me chapitre et qui n'est pas l'ntr�e courante
Vector myListe = new Vector(10);

if (this.nbNiveau == 1)
    {
      req = "SELECT     id, niv1, niv2, niv3, niv4, nbNiveau";
      req += "    FROM         BesoinsUtilisateur";
      req += " WHERE     (idRoadmap = " + this.idRoadmap + ") AND (niv1 = '" + this.niv1 + "')";
    }
else if (this.nbNiveau == 2)
    {
      req = "SELECT     id, niv1, niv2, niv3, niv4, nbNiveau";
      req += "    FROM         BesoinsUtilisateur";
      req += " WHERE     (idRoadmap = " + this.idRoadmap + ") AND (niv1 = '" + this.niv1 + "') AND (niv2 = '" + this.niv2 + "')";;
    }
else if (this.nbNiveau == 3)
   {
      req = "SELECT     id, niv1, niv2, niv3, niv4, nbNiveau";
      req += "    FROM         BesoinsUtilisateur";
      req += " WHERE     (idRoadmap = " + this.idRoadmap + ") AND (niv1 = '" + this.niv1 + "') AND (niv2 = '" + this.niv2 + "') AND (niv3 = '" + this.niv3 + "')";
    }

else if (this.nbNiveau == 4)
   {
      req = "SELECT     id, niv1, niv2, niv3, niv4, nbNiveau";
      req += "    FROM         BesoinsUtilisateur";
      req += " WHERE     (idRoadmap = " + this.idRoadmap + ") AND (niv1 = '" + this.niv1 + "') AND (niv2 = '" + this.niv2 + "') AND (niv3 = '" + this.niv3 + "') AND (niv4 = '" + this.niv4 + "')";
   }

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try {
       while (rs.next()) {
         Besoin theBesoin = new Besoin(rs.getInt("id"));
         theBesoin.niv1 = rs.getInt("niv1");
         theBesoin.niv2 = rs.getInt("niv2");
         theBesoin.niv3 = rs.getInt("niv3");
         theBesoin.niv4 = rs.getInt("niv4");
         theBesoin.nbNiveau = rs.getInt("nbNiveau");
         myListe.addElement(theBesoin);
       }
           } catch (SQLException s) {s.getMessage();}

return myListe;

  }


  public String bd_Exchange(String nomBase,Connexion myCnx, Statement st, Besoin theBesoinSave, Vector myListe, String transaction){
      // recherche de l'entr�e qui a le m�me chapitre et qui n'est pas l'ntr�e courante

  for (int i=0; i <     myListe.size(); i++)
    {
      Besoin theBesoin = (Besoin)myListe.elementAt(i);
      String newChapitre="";
      req = "UPDATE    BesoinsUtilisateur  SET ";
      //req +=" nbNiveau ='"+ theBesoinSave.nbNiveau + "', ";
      if (theBesoin.nbNiveau == 1)
      {
        req += " niv1 ='" + theBesoinSave.niv1 + "', ";
        newChapitre = "" + theBesoinSave.niv1;

      }
      else if (theBesoin.nbNiveau == 2)
      {
        if (this.nbNiveau ==1 )
        {
          req += " niv1 ='" + theBesoinSave.niv1 + "', ";
          req += " niv2 ='" + theBesoin.niv2 + "', ";
          req += " niv3 ='" + theBesoin.niv3 + "', ";
          newChapitre = "" + theBesoinSave.niv1 + "." + theBesoin.niv2;
        }
        else if (this.nbNiveau == 2 )
        {
          req += " niv1 ='" + theBesoinSave.niv1 + "', ";
          req += " niv2 ='" + theBesoinSave.niv2 + "', ";
          req += " niv3 ='" + theBesoin.niv3 + "', ";
          newChapitre = "" + theBesoinSave.niv1 + "." + theBesoinSave.niv2;
        }

      }
      else if (theBesoin.nbNiveau == 3)
      {
        if (this.nbNiveau ==1 )
        {
          req += " niv1 ='" + theBesoinSave.niv1 + "', ";
          req += " niv2 ='" + theBesoin.niv2 + "', ";
          req += " niv3 ='" + theBesoin.niv3 + "', ";
          newChapitre = "" + theBesoinSave.niv1 + "." + theBesoin.niv2 +
              "." + theBesoin.niv3;
        }
        else if (this.nbNiveau == 2 )
        {
          req += " niv1 ='" + theBesoinSave.niv1 + "', ";
          req += " niv2 ='" + theBesoinSave.niv2 + "', ";
          req += " niv3 ='" + theBesoin.niv3 + "', ";
          newChapitre = "" + theBesoinSave.niv1 + "." + theBesoinSave.niv2 +
              "." + theBesoin.niv3;
        }
        else if (this.nbNiveau == 3 )
        {
          req += " niv1 ='" + theBesoinSave.niv1 + "', ";
          req += " niv2 ='" + theBesoinSave.niv2 + "', ";
          req += " niv3 ='" + theBesoinSave.niv3 + "', ";
          newChapitre = "" + theBesoinSave.niv1 + "." + theBesoinSave.niv2 +
              "." + theBesoinSave.niv3;
        }

      }
      else if (theBesoin.nbNiveau == 4)
      {


        if (this.nbNiveau ==1 )
        {
          req += " niv1 ='" + theBesoinSave.niv1 + "', ";
          req += " niv2 ='" + theBesoin.niv2 + "', ";
          req += " niv3 ='" + theBesoin.niv3 + "', ";
          req += " niv4 ='" + theBesoin.niv4 + "',";
          newChapitre = "" + theBesoinSave.niv1 + "." + theBesoin.niv2 +
              "." + theBesoin.niv3+"." + theBesoin.niv4;
        }
        else if (this.nbNiveau == 2 )
        {
          req += " niv1 ='" + theBesoinSave.niv1 + "', ";
          req += " niv2 ='" + theBesoinSave.niv2 + "', ";
          req += " niv3 ='" + theBesoin.niv3 + "', ";
          req += " niv4 ='" + theBesoin.niv4 + "',";
          newChapitre = "" + theBesoinSave.niv1 + "." + theBesoinSave.niv2 +
              "." + theBesoin.niv3+"." + theBesoin.niv4;
        }
        else if (this.nbNiveau == 3 )
        {
          req += " niv1 ='" + theBesoinSave.niv1 + "', ";
          req += " niv2 ='" + theBesoinSave.niv2 + "', ";
          req += " niv3 ='" + theBesoinSave.niv3 + "', ";
          req += " niv4 ='" + theBesoin.niv4 + "',";
          newChapitre = "" + theBesoinSave.niv1 + "." + theBesoinSave.niv2 +
              "." + theBesoinSave.niv3+"." + theBesoin.niv4;
        }
        else if (this.nbNiveau == 4 )
        {
          req += " niv1 ='" + theBesoinSave.niv1 + "', ";
          req += " niv2 ='" + theBesoinSave.niv2 + "', ";
          req += " niv3 ='" + theBesoinSave.niv3 + "', ";
          req += " niv4 ='" + theBesoinSave.niv4 + "',";
          newChapitre = "" + theBesoinSave.niv1 + "." + theBesoinSave.niv2 +
              "." + theBesoinSave.niv3+"." + theBesoinSave.niv4;
        }

      }

      req +=" chapitre ='"+ newChapitre + "' ";

      req += " WHERE id ="+ theBesoin.id;
      if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1)
      {
        myCnx.trace(nomBase,"*** ERREUR *** req",req);
        return req;
      }

    }



      // update de cette entr�e avec les anciennes valeurs de l'entr�e courante
      return "OK";
  }

  public String bd_Exchange2(String nomBase,Connexion myCnx, Statement st, Besoin theBesoinSave, String transaction){
    // recherche de l'entr�e qui a le m�me chapitre et qui n'est pas l'ntr�e courante
    req = "SELECT     id";
    req+="    FROM         BesoinsUtilisateur";
    req+=" WHERE     (idRoadmap = "+this.idRoadmap+") AND (chapitre = '"+this.chapitre+"') AND (id <> "+this.id+")";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try {
       while (rs.next()) {
         theBesoinSave.id = rs.getInt("id");
       }
           } catch (SQLException s) {s.getMessage();}

           req = "UPDATE    BesoinsUtilisateur  SET ";
           req +=" nbNiveau ='"+ theBesoinSave.nbNiveau + "', ";
           req +=" niv1 ='"+ theBesoinSave.niv1 + "', ";
           req +=" niv2 ='"+ theBesoinSave.niv2 + "', ";
           req +=" niv3 ='"+ theBesoinSave.niv3 + "', ";
           req +=" niv4 ='"+ theBesoinSave.niv4 + "', ";
           req +=" chapitre ='"+ theBesoinSave.chapitre + "' ";

           req += " WHERE id ="+ theBesoinSave.id;
             if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1)
               {
                 myCnx.trace(nomBase,"*** ERREUR *** req",req);
                 return req;
      }

    // update de cette entr�e avec les anciennes valeurs de l'entr�e courante
    return "OK";
  }


  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){

    String chapitre="";
    if (this.chapitre != null)
      chapitre=this.chapitre.replaceAll("\u0092","'").replaceAll("'"," ").replaceAll("&","et").replace('+',' ');
    else
    chapitre="";

  String version="";
  if (this.version != null)
    version=this.version.replaceAll("\u0092","'").replaceAll("'"," ").replaceAll("&","et").replace('+',' ');
  else
    version="";

  String description="";
  if (this.description != null)
    description=this.description.replaceAll("\u0092","'").replaceAll("'","''");
  else
    description="";

  String detail="";
  if (this.detail != null)
    detail=this.detail.replaceAll("\u0092","'").replaceAll("'","''");
  else
    detail="";

  if (this.nbNiveau == 1)
  {
    this.chapitre = "" + this.niv1;
    this.niv2 = -1;
    this.niv3 = -1;
    this.niv4 = -1;
  }
  else if(this.nbNiveau == 2)
  {
    this.chapitre = "" + this.niv1 + "." + this.niv2;
    this.niv3 = -1;
    this.niv4 = -1;
  }
  else if(this.nbNiveau == 3)
  {
    this.chapitre = "" + this.niv1 + "." + this.niv2 + "." + this.niv3;
    this.niv4 = -1;
  }
  else if(this.nbNiveau == 4)
  {
    this.chapitre = "" + this.niv1 + "." + this.niv2 + "." + this.niv3 + "." +
        this.niv4;
  }
  if (this.Complexite == -1) this.Complexite = 1;

  req = "UPDATE    BesoinsUtilisateur  SET ";
  req +=" description ='"+ description + "', ";
  req +=" integDevis ='"+ this.IntegDevis + "', ";
  req +=" idBesoin ='"+ this.idBesoin + "',";
  req +=" idRoadmap ='"+ this.idRoadmap + "', ";
  req +=" detail ='"+ detail + "', ";
  req +=" isExigence ='"+ this.isExigence + "', ";
  req +=" version ='"+ version + "', ";
  req +=" image ='"+ this.image + "', ";
  req +=" nbNiveau ='"+ this.nbNiveau + "', ";
  req +=" niv1 ='"+ this.niv1 + "', ";
  req +=" niv2 ='"+ this.niv2 + "', ";
  req +=" niv3 ='"+ this.niv3 + "', ";
  req +=" niv4 ='"+ this.niv4 + "', ";
  req +=" chapitre ='"+ this.chapitre + "', ";
  req +=" Complexite ='"+ this.Complexite + "' ";

  req += " WHERE id ="+ this.id;

   return myCnx.ExecUpdateTransaction(st, nomBase, req, true, transaction, getClass().getName(),"bd_Update",""+this.id);
  }

  public ErrorSpecific bd_UpdateExigencesFilles(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    for (int i=0; i < this.ListeExigences.size();i++)
    {
      Besoin theBesoinFille = (Besoin)this.ListeExigences.elementAt(i);
      //theBesoinFille.dump();

      req = "UPDATE    BesoinsUtilisateur  SET ";

      if (theBesoinFille.nbNiveau == 2)
      {
        req += " niv1 = " + this.niv1 + "";
        req += ",";
        req += " chapitre ='" + this.niv1 + "." + theBesoinFille.niv2 + "' ";
      }
      else if (theBesoinFille.nbNiveau == 3)
      {
        req += " niv1 = " + this.niv1 + "";
        req += ",";
        req += " niv2 = " + this.niv2 + "";
        req += ",";
        req += " chapitre ='" + this.niv1 + "." + this.niv2 + "." + theBesoinFille.niv3 + "' ";
      }
      else if (theBesoinFille.nbNiveau == 4)
      {
        req+=" niv1 = "+this.niv1+"";
        req += ",";
        req+=" niv2 = "+this.niv2+"";
        req += ",";
        req+=" niv3 = "+this.niv3+"";
        req += ",";
        req += " chapitre ='" + this.niv1 + "." + this.niv2 + "." + this.niv3 + "." + theBesoinFille.niv4+ "' ";
    }

        req += " WHERE id ="+ theBesoinFille.id;
        myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateExigencesFilles",""+this.id);
    }



    return myError;
  }

  public String bd_UpdateNew(String nomBase,Connexion myCnx, Statement st, Besoin theBesoinSave, String transaction){

  Vector myListe = new Vector(10);
  req = "SELECT     id, niv1, niv2, niv3, niv4,nbNiveau ";
  req += "    FROM         BesoinsUtilisateur";
  req += " WHERE     (idRoadmap = " + this.idRoadmap + ") AND (nbNiveau > '" + this.nbNiveau + "')";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);

  try {
     while (rs.next()) {
       Besoin theBesoin = new Besoin(rs.getInt("id"));
       theBesoin.niv1 = rs.getInt("niv1");
       theBesoin.niv2 = rs.getInt("niv2");
       theBesoin.niv3 = rs.getInt("niv3");
       theBesoin.niv4 = rs.getInt("niv4");
       theBesoin.nbNiveau = rs.getInt("nbNiveau");
       myListe.addElement(theBesoin);
     }
  } catch (SQLException s) {s.getMessage();}


  for (int i=0; i <     myListe.size(); i++)
    {
      Besoin theBesoin = (Besoin) myListe.elementAt(i);
      String newChapitre = "";
      req = "UPDATE    BesoinsUtilisateur  SET ";

      if (theBesoin.nbNiveau == 1)
      {
        req += " niv1 ='" + this.niv1 + "', ";
        newChapitre = "" + this.niv1;

      }
      else if (theBesoin.nbNiveau == 2)
      {
        if (this.nbNiveau ==1 )
        {
          req += " niv1 ='" + this.niv1 + "', ";
          req += " niv2 ='" + theBesoin.niv2 + "', ";
          req += " niv3 ='" + theBesoin.niv3 + "', ";
          newChapitre = "" + this.niv1 + "." + theBesoin.niv2;
        }
        else if (this.nbNiveau == 2 )
        {
          req += " niv1 ='" + this.niv1 + "', ";
          req += " niv2 ='" + this.niv2 + "', ";
          req += " niv3 ='" + theBesoin.niv3 + "', ";
          newChapitre = "" + this.niv1 + "." + this.niv2;
        }

      }
      else if (theBesoin.nbNiveau == 3)
      {
        if (this.nbNiveau ==1 )
        {
          req += " niv1 ='" + this.niv1 + "', ";
          req += " niv2 ='" + theBesoin.niv2 + "', ";
          req += " niv3 ='" + theBesoin.niv3 + "', ";
          newChapitre = "" + this.niv1 + "." + theBesoin.niv2 +
              "." + theBesoin.niv3;
        }
        else if (this.nbNiveau == 2 )
        {
          req += " niv1 ='" + this.niv1 + "', ";
          req += " niv2 ='" + this.niv2 + "', ";
          req += " niv3 ='" + theBesoin.niv3 + "', ";
          newChapitre = "" + this.niv1 + "." + this.niv2 +
              "." + theBesoin.niv3;
        }
        else if (this.nbNiveau == 3 )
        {
          req += " niv1 ='" + this.niv1 + "', ";
          req += " niv2 ='" + this.niv2 + "', ";
          req += " niv3 ='" + this.niv3 + "', ";
          newChapitre = "" + this.niv1 + "." + this.niv2 +
              "." + this.niv3;
        }

      }
      else if (theBesoin.nbNiveau == 4)
      {


        if (this.nbNiveau ==1 )
        {
          req += " niv1 ='" + this.niv1 + "', ";
          req += " niv2 ='" + theBesoin.niv2 + "', ";
          req += " niv3 ='" + theBesoin.niv3 + "', ";
          req += " niv4 ='" + theBesoin.niv4 + "',";
          newChapitre = "" + this.niv1 + "." + theBesoin.niv2 +
              "." + theBesoin.niv3+"." + theBesoin.niv4;
        }
        else if (this.nbNiveau == 2 )
        {
          req += " niv1 ='" + this.niv1 + "', ";
          req += " niv2 ='" + this.niv2 + "', ";
          req += " niv3 ='" + theBesoin.niv3 + "', ";
          req += " niv4 ='" + theBesoin.niv4 + "',";
          newChapitre = "" + this.niv1 + "." + this.niv2 +
              "." + theBesoin.niv3+"." + theBesoin.niv4;
        }
        else if (this.nbNiveau == 3 )
        {
          req += " niv1 ='" + this.niv1 + "', ";
          req += " niv2 ='" + this.niv2 + "', ";
          req += " niv3 ='" + this.niv3 + "', ";
          req += " niv4 ='" + theBesoin.niv4 + "',";
          newChapitre = "" + this.niv1 + "." + this.niv2 +
              "." + this.niv3+"." + theBesoin.niv4;
        }
        else if (this.nbNiveau == 4 )
        {
          req += " niv1 ='" + this.niv1 + "', ";
          req += " niv2 ='" + this.niv2 + "', ";
          req += " niv3 ='" + this.niv3 + "', ";
          req += " niv4 ='" + this.niv4 + "',";
          newChapitre = "" + this.niv1 + "." + this.niv2 +
              "." + this.niv3+"." + this.niv4;
        }

      }

      req += " chapitre ='" + newChapitre+ "' ";

      if (this.nbNiveau == 1) {
        req += " WHERE niv1 =" + theBesoinSave.niv1;
      }
      else if (this.nbNiveau == 2) {
        req += " WHERE niv2 =" + theBesoinSave.niv2;
      }
      else if (this.nbNiveau == 3) {
        req += " WHERE niv3 =" + theBesoinSave.niv3;
      }
      else if (this.nbNiveau == 4) {
        req += " WHERE niv4 =" + theBesoinSave.niv4;
      }

      req += " AND id ="+ theBesoin.id;

      if (myCnx.ExecUpdate(st, nomBase, req, true, transaction) == -1) {
        myCnx.trace(nomBase, "*** ERREUR *** reqRoadmap", req);
        return req;
      }
    }
    return "OK";
  }

  public String bd_UpdateNew2(String nomBase,Connexion myCnx, Statement st, Besoin theBesoinSave, String transaction){

  Vector myListe = new Vector(10);
  req = "SELECT     id, niv1, niv2, niv3, niv4,nbNiveau ";
  req += "    FROM         BesoinsUtilisateur";
  req += " WHERE     (idRoadmap = " + this.idRoadmap + ") AND (nbNiveau > '" + this.nbNiveau + "')";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);

  try {
     while (rs.next()) {
       Besoin theBesoin = new Besoin(rs.getInt("id"));
       theBesoin.niv1 = rs.getInt("niv1");
       theBesoin.niv2 = rs.getInt("niv2");
       theBesoin.niv3 = rs.getInt("niv3");
       theBesoin.niv4 = rs.getInt("niv4");
       theBesoin.nbNiveau = rs.getInt("nbNiveau");
       myListe.addElement(theBesoin);
     }
  } catch (SQLException s) {s.getMessage();}


  for (int i=0; i <     myListe.size(); i++)
    {
      Besoin theBesoin = (Besoin) myListe.elementAt(i);
      String newChapitre = "";
      req = "UPDATE    BesoinsUtilisateur  SET ";

      if (theBesoin.nbNiveau == 1) {
        req += " niv1 ='" + this.niv1 + "', ";
        newChapitre = "" + this.niv1;
      }
      else if (theBesoin.nbNiveau == 2) {
        req += " niv1 ='" + this.niv1 + "', ";
        req += " niv2 ='" + theBesoin.niv2 + "', ";
        newChapitre = "" + this.niv1 + "." + theBesoin.niv2;
      }
      else if (theBesoin.nbNiveau == 3) {
        req += " niv1 ='" + this.niv1 + "', ";
        req += " niv2 ='" + this.niv2 + "', ";
        req += " niv3 ='" + theBesoin.niv3 + "', ";
        newChapitre = "" + this.niv1 + "." + this.niv2 + "." + theBesoin.niv3;
      }
      else if (theBesoin.nbNiveau == 4) {
        req += " niv1 ='" + this.niv1 + "', ";
        req += " niv2 ='" + this.niv2 + "', ";
        req += " niv3 ='" + this.niv3 + "', ";
        req += " niv4 ='" + theBesoin.niv4 + "', ";
        newChapitre = "" + this.niv1 + "." + this.niv2 + "." + this.niv3 + "." +
            theBesoin.niv4;
      }

      req += " chapitre ='" + newChapitre+ "' ";

      if (this.nbNiveau == 1) {
        req += " WHERE niv1 =" + theBesoinSave.niv1;
      }
      else if (this.nbNiveau == 2) {
        req += " WHERE niv2 =" + theBesoinSave.niv2;
      }
      else if (this.nbNiveau == 3) {
        req += " WHERE niv3 =" + theBesoinSave.niv3;
      }
      else if (this.nbNiveau == 4) {
        req += " WHERE niv4 =" + theBesoinSave.niv4;
      }

      req += " AND id ="+ theBesoin.id;

      if (myCnx.ExecUpdate(st, nomBase, req, true, transaction) == -1) {
        myCnx.trace(nomBase, "*** ERREUR *** reqRoadmap", req);
        return req;
      }
    }
    return "OK";
  }
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    //System.out.println("description="+this.description);
    System.out.println("Commentaire="+this.Commentaire);
    System.out.println("type="+this.type);
    System.out.println("IntegDevis="+this.IntegDevis);
    System.out.println("idBesoin="+this.idBesoin);
    System.out.println("nomTypeIntegration="+this.nomTypeIntegration);
    System.out.println("isExigence="+this.isExigence);
    System.out.println("detail="+this.detail);
    System.out.println("image="+this.image);
    System.out.println("nbNiveau="+this.nbNiveau);
    System.out.println("niv1="+this.niv1);
    System.out.println("niv2="+this.niv2);
    System.out.println("niv3="+this.niv3);
    System.out.println("niv4="+this.niv4);

  }
  public static void main(String[] args) {

    Connexion myCnx = null;
    Statement st, st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    st2 = myCnx.Connect();

    String result="";

    Besoin theBesoin = new Besoin(3238);
    theBesoin.getAllFromBd(myCnx.nomBase,myCnx,  st );

    Besoin theBesoinSave = new Besoin(3238);
    theBesoinSave.nbNiveau=theBesoin.nbNiveau;
    theBesoinSave.niv1=theBesoin.niv1;
    theBesoinSave.niv2=theBesoin.niv2;
    theBesoinSave.niv3=theBesoin.niv3;
    theBesoinSave.niv4=theBesoin.niv4;
    theBesoinSave.chapitre=theBesoin.chapitre;


    theBesoin.nbNiveau=2;
    theBesoin.niv1 = 4;
    theBesoin.niv2 = 1;
    theBesoin.niv3 = -1;
    theBesoin.niv4 = -1;

    transaction theTransaction = new transaction ("EnregDevis");
    theTransaction.begin(base,myCnx,st);
    Vector myListe = theBesoin.bd_saveOldListe(base,myCnx,st,theBesoinSave, theTransaction.nom);
   // myCnx.trace("------","myListe.size()",""+myListe.size());
    //if (true) return;

    theBesoin.bd_Update(base,myCnx,st,theTransaction.nom);

    result = theBesoin.bd_UpdateNew(base,myCnx,st,theBesoinSave,theTransaction.nom);

    result = theBesoin.bd_Exchange(base,myCnx,st,theBesoinSave, myListe, theTransaction.nom);

    theTransaction.commit(base,myCnx,st);
  }
}
