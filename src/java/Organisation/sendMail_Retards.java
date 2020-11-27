package Organisation; 

import java.sql.Statement;
import java.sql.ResultSet;
import accesbase.Connexion;
import java.util.Date;
import java.sql.SQLException;
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
public class sendMail_Retards {
  public static Vector ListeCollaborateurs = new Vector(6);
  public static String corps;
  public static int num=1;

  //public static String serveur = "bt1shk5v";
  //public static String root = "BaseCarto";

  public static String serveur = "";
  public static String root = "";
  public static String Login = "";

  public sendMail_Retards() {
  }

  static String sendMailByCollaborateur_action(String nomBase, Connexion myCnx, Statement st,String TypeDate,String str_date, Collaborateur theCollaborateur, String req){
    ResultSet rs = null;
    ResultSet rs2 = null;

    req = "SELECT     actionSuivi.nom, Roadmap.version, Roadmap.idVersionSt, St_1.nomSt";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                   actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN";
    req+="                   TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id INNER JOIN";
    req+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                   St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                   St AS St_1 ON VersionSt.stVersionSt = St_1.idSt";
    req+=" WHERE     (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)) AND ";
    req+="                   (actionSuivi.acteur LIKE '%"+theCollaborateur.Login+"%')";



    String destinataire = theCollaborateur.mail;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String nomAction = rs.getString("nom");
        String version = rs.getString("version");
        String idVersionSt = rs.getString("idVersionSt");
        String nomSt = rs.getString("nomSt");
        String nomProjet = nomSt + "-" + version;


        //int idMembre = rs.getInt("idMembre");

        corps += "<br>";
        corps += ""+(num)+") ";
        corps += "Vous �tes "+"<a href='http://"+serveur+":8080/"+root+"/jsp/CreerSuivi.jsp?idVersionSt="+idVersionSt+"&typeForm=Modification&etatFicheVersionSt=1&Admin=no&nomSt="+nomSt+"&version="+version+"&Login="+theCollaborateur.Login+"&Load=yes"+"' title='Ouvrir le projet' target='_blank'>en retard</a>"+" sur le traitement de l'ACTION: ";
        corps += "<font style='color:#FF0000'>"+ nomAction+"</font>";
        corps += " du projet: "+nomProjet;
        corps += "<br>";
        num++;
      }
    }
    catch (Exception e) {}
    ;

    return corps;
  }

  static String sendMailByCollaborateur_action2(String nomBase, Connexion myCnx, Statement st,String TypeDate,String str_date, Collaborateur theCollaborateur, String req){
    ResultSet rs = null;
    ResultSet rs2 = null;




    String destinataire = theCollaborateur.mail;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String nomAction = rs.getString("nom");
        String nomProjet = rs.getString("nomProjet");
        String nomSt = rs.getString("nomSt");
        String version = rs.getString("version");
        String idVersionSt = rs.getString("idVersionSt");
        int idMembre = rs.getInt("idMembre");

        corps += "<br>";
        corps += ""+(num)+") ";
        corps += "Vous �tes "+"<a href='http://"+serveur+":8080/"+root+"/jsp/CreerSuivi.jsp?idVersionSt="+idVersionSt+"&typeForm=Modification&etatFicheVersionSt=1&Admin=no&nomSt="+nomSt+"&version="+version+"&Login="+theCollaborateur.Login+"&Load=yes"+"' title='Ouvrir le projet' target='_blank'>en retard</a>"+" sur le traitement de l'ACTION: ";
        corps += "<font style='color:#FF0000'>"+ nomAction+"</font>";
        corps += " du projet: "+nomProjet;
        corps += "<br>";
        num++;
      }
    }
    catch (Exception e) {}
    ;

    return corps;
  }
  static String sendMailByCollaborateur_commun(String nomBase, Connexion myCnx, Statement st,String TypeDate,String str_date, Collaborateur theCollaborateur, String req){
    ResultSet rs = null;
    ResultSet rs2 = null;




    String destinataire = theCollaborateur.mail;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String nomAction = rs.getString(2);
        String version = rs.getString("version");
        String idVersionSt = rs.getString("idVersionSt");
        int idMembre = rs.getInt("respMoaVersionSt");
        String nomSt = rs.getString("nomSt");

        corps += "<br>";
        corps += ""+(num)+") ";
        corps += "Vous �tes "+"<a href='http://"+serveur+":8080/"+root+"/jsp/CreerSuivi.jsp?idVersionSt="+idVersionSt+"&typeForm=Modification&etatFicheVersionSt=1&Admin=no&nomSt="+nomSt+"&version="+version+"&Login="+theCollaborateur.Login+"' title='Ouvrir le projet' target='_blank'>en retard</a>"+" sur le traitement("+TypeDate+") du projet: ";
        corps += "<font style='color:#FF0000'>"+ nomAction+"</font>";
        corps += "<br>";
        num++;
      }
    }
    catch (Exception e) {}
    ;

    return corps;
  }

  static void sendMailByCollaborateur(String nomBase, Connexion myCnx, Statement st,String TypeDate,String str_date){
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";


    String emetteur = "jjakubow"+"@" + myCnx.mail;
    String objet = "** RETARDS sur PROJETS (Lixian)";
    corps += "Bonjour,"+"<br>";
    corps += "<br>";

    for (int i=0; i < ListeCollaborateurs.size(); i++)
    {
      num=1;
      corps = "";
      Collaborateur theCollaborateur = (Collaborateur)ListeCollaborateurs.elementAt(i);


      TypeDate = "T0";
      req = "exec PROJET_nb_retardByActeur" + TypeDate + " " + theCollaborateur.id + "";
      corps=sendMailByCollaborateur_commun( nomBase,  myCnx,  st, TypeDate, str_date,  theCollaborateur, req);
      req = "exec PROJET_nb_retardByEquipe" + TypeDate + " " + theCollaborateur.id + "";
      corps=sendMailByCollaborateur_commun( nomBase,  myCnx,  st, TypeDate, str_date,  theCollaborateur, req);

      TypeDate = "EB";
      req = "exec PROJET_nb_retardByActeur" + TypeDate + " " + theCollaborateur.id + "";
      corps=sendMailByCollaborateur_commun( nomBase,  myCnx,  st, TypeDate, str_date,  theCollaborateur, req);
      req = "exec PROJET_nb_retardByEquipe" + TypeDate + " " + theCollaborateur.id + "";
      corps=sendMailByCollaborateur_commun( nomBase,  myCnx,  st, TypeDate, str_date,  theCollaborateur, req);

      TypeDate = "TEST";
      req = "exec PROJET_nb_retardByActeur" + TypeDate + " " + theCollaborateur.id + "";
      corps=sendMailByCollaborateur_commun( nomBase,  myCnx,  st, TypeDate, str_date,  theCollaborateur, req);
      req = "exec PROJET_nb_retardByEquipe" + TypeDate + " " + theCollaborateur.id + "";
      corps=sendMailByCollaborateur_commun( nomBase,  myCnx,  st, TypeDate, str_date,  theCollaborateur, req);

      TypeDate = "PROD";
      req = "exec PROJET_nb_retardByActeur" + TypeDate + " " + theCollaborateur.id + "";
      corps=sendMailByCollaborateur_commun( nomBase,  myCnx,  st, TypeDate, str_date,  theCollaborateur, req);
      req = "exec PROJET_nb_retardByEquipe" + TypeDate + " " + theCollaborateur.id + "";
      corps=sendMailByCollaborateur_commun( nomBase,  myCnx,  st, TypeDate, str_date,  theCollaborateur, req);

      TypeDate = "MES";
      req = "exec PROJET_nb_retardByActeur" + TypeDate + " " + theCollaborateur.id + "";
      corps=sendMailByCollaborateur_commun( nomBase,  myCnx,  st, TypeDate, str_date,  theCollaborateur, req);
      req = "exec PROJET_nb_retardByEquipe" + TypeDate + " " + theCollaborateur.id + "";
      corps=sendMailByCollaborateur_commun( nomBase,  myCnx,  st, TypeDate, str_date,  theCollaborateur, req);

      TypeDate = "ACTION";
      req = "exec PROJET_nb_retardByActeur" + TypeDate + " " + theCollaborateur.id + "";
      corps=sendMailByCollaborateur_action( nomBase,  myCnx,  st, TypeDate, str_date,  theCollaborateur, req);
      //req = "exec PROJET_nb_retardByEquipe" + TypeDate + " " + theCollaborateur.id + "";
      //corps=sendMailByCollaborateur_action( nomBase,  myCnx,  st, TypeDate, str_date,  theCollaborateur, req);


      try{
        if (!corps.equals(""))
        {
          myCnx.sendmail(emetteur, theCollaborateur.mail, objet, corps);
        }
        else
        {
          System.out.print("@@pas de message envoy� pour: "+theCollaborateur.Login);
          corps = "pas de message envoy�";
        }
      }
      catch (Exception e){
        myCnx.trace("@01234******","",e.getMessage());
      }
        req = "INSERT INTO Traces  (timeStamp, base, page,typeTrace,trace) VALUES     ('" +
            str_date + "', '" + emetteur + "', '" + theCollaborateur.mail + "', '" +
            objet + "', '" + corps.replaceAll("'","''") + "')";
          rs2 = myCnx.ExecReq(st, myCnx.nomBase, req);


      corps += "<br>";
      corps += "<br>";
      corps += "Cordialement";

    }


  }

  public static void sendDateRetard_Action(String nomBase, Connexion myCnx, Statement st,Statement st2,Statement st3, String nomService,   String str_date) {
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";

    req = "exec SERVICE_retardActions "+" '" + nomService + "'";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String nomAction = rs.getString(2);
        int idMembre = rs.getInt("respMoaVersionSt");
        String nomProjet = rs.getString("nomProjet");
        String nomSt = rs.getString("nomSt");
        String version = rs.getString("version");
        String idVersionSt = rs.getString("idVersionSt");

        //-------------------------------------------------------------------------------------------------------
        // r�cup�ration de la liste des projets en retard du service
        // ------------------- Bouclage sur la liste des taches

        // R�cup�ration du collaborateur traitant la tache
        // Envoi du mail
        Collaborateur theCollaborateur = new Collaborateur(idMembre);
        theCollaborateur.getAllFromBd(myCnx.nomBase, myCnx, st2);
        //theCollaborateur.dump();
        //if (theCollaborateur.Login.toUpperCase().equals("JJAKUBOW"))
        {
          //myCnx.trace("@1234***********","theCollaborateur.Login",theCollaborateur.Login);
          String emetteur = "jjakubow@"+"@" + myCnx.mail;
          String destinataire = theCollaborateur.mail;
          String objet = "Action du projet: "+nomProjet;
          String corps ="";
          corps += "Bonjour,"+"<br>";
          corps += "<br>";
          corps += "Vous �tes en retard sur le traitement de l''action: "+nomAction + " du projet: " +
              nomProjet+"<br>"+ "<br>";
          corps += "<br>";
          corps += "Pour acc�der directement au projet et r�gler le retard, ";
          corps += "<a href='http://"+serveur+":8080/"+root+"/jsp/CreerSuivi.jsp?idVersionSt="+idVersionSt+"&typeForm=Modification&etatFicheVersionSt=1&Admin=no&nomSt="+nomSt+"&version="+version+"&Login="+theCollaborateur.Login+"' title='Ouvrir le projet' target='_blank'>Cliquez ici</a>";
          corps += "<br>";
          corps += "<br>";
          corps += "Cordialement";

          try{
            myCnx.sendmail(emetteur, destinataire, "*** Retard sur:" + objet, corps);
          }
          catch (Exception e){
            myCnx.trace("@01234******","",e.getMessage());
            //e.printStackTrace();
          }
          req =
              "INSERT INTO Traces  (timeStamp, base, page,typeTrace,trace) VALUES     ('" +
              str_date + "', '" + emetteur + "', '" + destinataire + "', '" +
              "Action" + "', '" + corps.replaceAll("'","''") + "')";
          rs2 = myCnx.ExecReq(st3, myCnx.nomBase, req);
        }
      }
    }
    catch (SQLException s) {
      s.getMessage();
      }

      req = " SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, ";
      req += "                       actionSuivi.dateFin, Service.nomService, VersionSt.respMoaVersionSt, St.nomSt + '-' + Roadmap.version AS nomProjet, St.nomSt, Roadmap.version, ";
      req += "                       Roadmap.idVersionSt, Membre.idMembre ";
      req += " FROM         Roadmap INNER JOIN ";
      req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN ";
      req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN ";
      req += "                       actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN ";
      req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id INNER JOIN ";
      req += "                       Service ON VersionSt.serviceMoaVersionSt = Service.idService INNER JOIN ";
      req += "                       Equipe ON St.nomSt = Equipe.nom INNER JOIN ";
      req += "                       EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN ";
      req += "                       Membre ON EquipeMembre.idMembre = Membre.idMembre ";
      req += " WHERE     (Service.nomService = '"+nomService+"') AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)) ";

      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      try {
        while (rs.next()) {
          String nomAction = rs.getString(2);

          String nomProjet = rs.getString("nomProjet");
          String nomSt = rs.getString("nomSt");
          String version = rs.getString("version");
          String idVersionSt = rs.getString("idVersionSt");
          int idMembre = rs.getInt("idMembre");

          //-------------------------------------------------------------------------------------------------------
          // r�cup�ration de la liste des projets en retard du service
          // ------------------- Bouclage sur la liste des taches

          // R�cup�ration du collaborateur traitant la tache
          // Envoi du mail
          Collaborateur theCollaborateur = new Collaborateur(idMembre);
          theCollaborateur.getAllFromBd(myCnx.nomBase, myCnx, st2);
          //theCollaborateur.dump();
          //if (theCollaborateur.Login.toUpperCase().equals("JJAKUBOW"))
          {
            //myCnx.trace("@1234***********","theCollaborateur.Login",theCollaborateur.Login);
            String emetteur = "jjakubow"+"@" + myCnx.mail;
            String destinataire = theCollaborateur.mail;
            String objet = "Action du projet: "+nomProjet;
            String corps ="";
            corps += "Bonjour,"+"<br>";
            corps += "<br>";
            corps += "Vous �tes en retard sur le traitement de l''action: "+nomAction + " du projet: " +
                nomProjet+"<br>"+ "<br>";
            corps += "<br>";
            corps += "Pour acc�der directement au projet et r�gler le retard, ";
            corps += "<a href='http://"+serveur+":8080/"+root+"/jsp/CreerSuivi.jsp?idVersionSt="+idVersionSt+"&typeForm=Modification&etatFicheVersionSt=1&Admin=no&nomSt="+nomSt+"&version="+version+"&Login="+theCollaborateur.Login+"' title='Ouvrir le projet' target='_blank'>Cliquez ici</a>";
            corps += "<br>";
            corps += "<br>";
            corps += "Cordialement";

            try{
              myCnx.sendmail(emetteur, destinataire, "*** Retard sur:" + objet, corps);
            }
            catch (Exception e){
              myCnx.trace("@01234******","",e.getMessage());
              //e.printStackTrace();
            }
            req =
                "INSERT INTO Traces  (timeStamp, base, page,typeTrace,trace) VALUES     ('" +
                str_date + "', '" + emetteur + "', '" + destinataire + "', '" +
                "Action" + "', '" + corps.replaceAll("'","''") + "')";
            rs2 = myCnx.ExecReq(st3, myCnx.nomBase, req);
          }
        }
      }
      catch (SQLException s) {
        s.getMessage();
      }
  }

  public static void deleteTraces(String nomBase, Connexion myCnx, Statement st) {
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";

    req = "delete from Traces";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  }




  public static int insertTempIdMembre(String nomBase, Connexion myCnx, Statement st,Statement st2, String nomService, String TypeDate) {
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";

    int nb = 0;

    if (!TypeDate.equals("ACTION"))
    {
      req = "exec ACTEUR_nb_retard" + TypeDate + " '" + nomService + "'";
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      try {
        while (rs.next()) {
          int idMembre = rs.getInt("respMoaVersionSt");
          req = "INSERT INTO tempIdMembre  (idMembre) VALUES     (" + idMembre +
              ")";
          rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);
          nb++;

        }

      }
      catch (SQLException s) {
        s.printStackTrace();
      }
    }
      else
      {
        Service theService = new Service(nomService);
        theService.getListeCollaborateursRetardAction( nomBase, myCnx,  st,   st2);

          for(int i=0; i < theService.ListeCollaborateurs.size();i++)
          {
            Collaborateur theCollaborateur = (Collaborateur)theService.ListeCollaborateurs.elementAt(i);
            req = "INSERT INTO tempIdMembre  (idMembre) VALUES     (" + theCollaborateur.id + ")";
            rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);
            nb++;

          }

      }
      return nb;
  }

  public static void sendDateRetard(String nomBase, Connexion myCnx, Statement st,Statement st2,Statement st3, String nomService, String TypeDate,  String str_date) {

    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    ListeCollaborateurs.removeAllElements();

    req = "delete from tempIdMembre";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    int nb = 0;

    nb +=insertTempIdMembre( nomBase,  myCnx,  st,st2,  nomService, "T0");
    nb +=insertTempIdMembre( nomBase,  myCnx,  st,st2,  nomService, "EB");
    nb +=insertTempIdMembre( nomBase,  myCnx,  st,st2,  nomService, "TEST");
    nb +=insertTempIdMembre( nomBase,  myCnx,  st,st2,  nomService, "PROD");
    nb +=insertTempIdMembre( nomBase,  myCnx,  st,st2,  nomService, "MES");
    nb +=insertTempIdMembre( nomBase,  myCnx,  st,st2,  nomService, "ACTION");

      req = "select distinct idMembre from tempIdMembre";
      rs = myCnx.ExecReq(st2, myCnx.nomBase, req);

      Collaborateur theCollaborateur = null;
      Service theService = null;

      try {
        while (rs.next()) {
          int idMembre = rs.getInt("idMembre");
          theCollaborateur = new Collaborateur(idMembre);
          theCollaborateur.getAllFromBd(myCnx.nomBase, myCnx, st);
          //theCollaborateur.dump();

          theService = new Service(theCollaborateur.service);
          theService.getAllFromBd(myCnx.nomBase, myCnx, st3);

          if (theService.sendRetard == 1)
            ListeCollaborateurs.addElement(theCollaborateur);

        }
      }
      catch (SQLException s) {
        s.printStackTrace();
        //myCnx.trace("@@------------------", "", "" + theCollaborateur.nom);
      }
      sendMailByCollaborateur(nomBase, myCnx, st, TypeDate, str_date);

  }

  public static void sendBidon(String nomBase, Connexion myCnx, Statement st,Statement st2,Statement st3, String nomService, String TypeDate,  String str_date) {
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";

      {
        String nomProjet = "BIDON";
        int idMembre = 297;

        //-------------------------------------------------------------------------------------------------------
        // r�cup�ration de la liste des projets en retard du service
        // ------------------- Bouclage sur la liste des taches

        // R�cup�ration du collaborateur traitant la tache
        // Envoi du mail
        Collaborateur theCollaborateur = new Collaborateur(idMembre);
        theCollaborateur.getAllFromBd(myCnx.nomBase, myCnx, st2);
        //theCollaborateur.dump();
        //if (theCollaborateur.Login.toUpperCase().equals("JJAKUBOW"))
        {
          //myCnx.trace("@1234***********","theCollaborateur.Login",theCollaborateur.Login);
          String emetteur = "jjakubow"+"@" + myCnx.mail;
          String destinataire = "jjakubow"+"@" + myCnx.mail;
          String objet = "BIDON";
          String corps="";
          corps += "Bonjour,"+"<br>";
          corps += "<br>";
          corps += "Vous �tes en retard sur le traitement("+TypeDate+") du projet: " +
              nomProjet;
          corps += "<br>";
          corps += "Pour acc�der directement au projet et r�gler le retard, ";
          corps += "<a href='http://"+serveur+":8080/"+root+"/jsp/CreerSuivi.jsp?idVersionSt=1759&typeForm=Modification&etatFicheVersionSt=1&Admin=no&nomSt=Lixian&version=3.0&Login=JJAKUBOW' title='Ouvrir le projet' target='_blank'>Cliquez ici</a>";
          corps += "<br>";
          corps += "<br>";
          corps += "Cordialement";

          try{
            myCnx.sendmail(emetteur, destinataire, "*** Retard sur:" + objet, corps);
          }
          catch (Exception e){
            myCnx.trace("@01234******","",e.getMessage());
            //e.printStackTrace();
          }
          req =
              "INSERT INTO Traces  (timeStamp, base, page,typeTrace,trace) VALUES     ('" +
              str_date + "', '" + emetteur + "', '" + destinataire + "', '" +
              objet + "', '" + corps.replaceAll("'","''") + "')";
          rs2 = myCnx.ExecReq(st3, myCnx.nomBase, req);
        }
      }


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
    Date theDate=null;

    String str_date="";

    theDate = new Date();
    str_date  = theDate.toString();


    req = "delete from Traces";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    // ------------ Bouclage sur tous les services
    Service.getListeServices(myCnx.nomBase,myCnx,st);
    for (int i=0; i < Service.ListeServices.size();i++)
    {
      //------------------------------ Cr�ation du Service ----------------------------------------------------
      Service theService = (Service)Service.ListeServices.elementAt(i);
      theService.getAllFromBd(myCnx.nomBase, myCnx, st);

      String nomService = theService.nom;
      if (theService.sendRetard != 1) continue;
      //theService.dump();
      sendDateRetard(myCnx.nomBase, myCnx, st,st2,st3, nomService,"T0",  str_date);

    }

    // ------------------- Fin Bouclage tache

    // ------------ Fin de Bouclage sur tous les services

    myCnx.Unconnect(st2);
    myCnx.Unconnect(st);
  }
}
