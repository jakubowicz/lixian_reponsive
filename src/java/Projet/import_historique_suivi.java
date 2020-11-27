package Projet; 

import java.util.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;

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
public class import_historique_suivi {
  public import_historique_suivi() {
  }

  public static void main(String[] args) {
    Connexion myCnx = null;
    Statement st, st2, st3 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    st2 = myCnx.Connect();
    st3 = myCnx.Connect();


    Date currentDate = new Date();

   String transaction = "Migration";
   //transaction theTransaction = new transaction (transaction);

   //theTransaction.begin(myCnx.nomBase,myCnx,st);

    // delete de l'historique
    req = "delete historiqueSuivi";
    myCnx.ExecReq(st, myCnx.nomBase, req);
    // bouclage de toute la table roadmap

    req = "SELECT     idRoadmap, Suivi, SuiviMOE";
    req+=" FROM         Roadmap";
    req+=" WHERE     (LF_Month = - 1) AND (LF_Year = - 1)";

    // lecture d'une ligne

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String Suivi="";
    String SuiviMOE="";
    int  idRoadmap;

    try {
      while (rs.next()) {
        idRoadmap = rs.getInt("idRoadmap");
        Suivi = rs.getString("Suivi");
        SuiviMOE = rs.getString("SuiviMOE");

        if (Suivi != null && !Suivi.equals(""))
        {
          req = "INSERT historiqueSuivi (";
          req += "dateSuivi" + ",";
          req += "nom" + ",";
          req += "description" + ",";
          req += "idRoadmap" + ",";
          req += "type" + "";
          req += ")";
          req += " VALUES ";
          req += "(";
          req += "CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)"+ ",";
          req += "'" + "Migration initiale" + "',";
          req += "'" + Suivi.replaceAll("\u0092","'").replaceAll("'","''") + "',";
          req += "'" + idRoadmap + "',";
          req += "'" + "MOA" + "'";
          req += ")";

          rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);        }
        if (SuiviMOE != null && !SuiviMOE.equals(""))
        {
          req = "INSERT historiqueSuivi (";
          req += "dateSuivi" + ",";
          req += "nom" + ",";
          req += "description" + ",";
          req += "idRoadmap" + ",";
          req += "type" + "";
          req += ")";
          req += " VALUES ";
          req += "(";
          req += "CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)"+ ",";
          req += "'" + "Migration initiale" + "',";
          req += "'" + SuiviMOE.replaceAll("\u0092","'").replaceAll("'","''") + "',";
          req += "'" + idRoadmap + "',";
          req += "'" + "MOE" + "'";
          req += ")";

          rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);
        }
      }
    }
           catch (SQLException s) {s.getMessage();}
    // si le suivi est non null, cr�ation d'un historique de type MOA, clef etrang�re = id rodamap, date du jour
    // si le suiviMOE est non null, cr�ation d'un historique de type MOA, clef etrang�re = id rodamap, date du jour
    // fin de bouclage

     //theTransaction.commit(myCnx.nomBase,myCnx,st);
  }
}
