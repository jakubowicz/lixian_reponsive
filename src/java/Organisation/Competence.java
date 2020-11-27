package Organisation; 
import General.Utils;
import static Organisation.Collaborateur.ListeCollaborateur;
import ST.Logiciel;
import ST.ST;
import accesbase.Connexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
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
public class Competence {
  public Collaborateur theCollaborateur = null;
  public ST theSt = null;
  public Logiciel theLogiciel= null;
  public int note=0;
  public String nomNote="";
  public static Vector ListeCollaborateurs = new Vector(10);
  private String req="";

  public Competence() {
  }

  public Competence(Collaborateur theCollaborateur, Logiciel theLogiciel, int note) {
    this.theCollaborateur=theCollaborateur;
    this.theLogiciel=theLogiciel;
    this.note=note;
  }
  
  public static void getListeCollaborateur(String nomBase,Connexion myCnx, Statement st, Statement st2, String ListeCompetences){
  ResultSet rs;
  String req="";

     String ClauseCompetence="";
     ListeCollaborateurs.removeAllElements();
    int nb = 0;
   if ((ListeCompetences != null) && !ListeCompetences.equals("") && !ListeCompetences.equals("-1") && !ListeCompetences.equals("-1;"))
   {
     ClauseCompetence = " WHERE (";
     

     for (StringTokenizer t = new StringTokenizer(ListeCompetences, "@");t.hasMoreTokens(); ) {
       String LigneCompetence = t.nextToken();
       StringTokenizer tt = new StringTokenizer(LigneCompetence, ";");
       String idMiddleware =  tt.nextToken();
       String note =  tt.nextToken();
       
       if (nb > 0)
           ClauseCompetence += " OR ";
       
         ClauseCompetence += "(";
         ClauseCompetence += "(Middleware.idMiddleware=" + idMiddleware + ")";
         ClauseCompetence += " AND ";
         ClauseCompetence += "(CollaborateurCompetence.note >=" + note + ")";
         ClauseCompetence += ")";
         
       nb++;
     }

     ClauseCompetence += ") ";
   }
  
  // delete tempListeMembre
    req = "DELETE FROM tempListeMembres";
    myCnx.ExecReq(st, myCnx.nomBase, req); 
  

    req="SELECT     Membre.nomMembre, Membre.prenomMembre, Membre.LoginMembre, Membre.idMembre, Membre.photo, Service.nomService, Direction.nomDirection, COUNT(Membre.nomMembre) AS nb, SUM(CollaborateurCompetence.note) AS totalNote";
    req+=" FROM         Membre INNER JOIN";
    req+="                       CollaborateurCompetence ON Membre.idMembre = CollaborateurCompetence.idCollaborateur INNER JOIN";
    req+="                       Service ON Membre.serviceMembre = Service.idService INNER JOIN";
    req+="                       Direction ON Service.dirService = Direction.idDirection INNER JOIN";
    req+="                       Middleware ON CollaborateurCompetence.idCompetence = Middleware.idMiddleware";
    req+= ClauseCompetence;
    req+=" GROUP BY Membre.nomMembre, Membre.prenomMembre, Membre.LoginMembre, Membre.idMembre, Membre.photo, Service.nomService, Direction.nomDirection";
    req+=" HAVING      (COUNT(Membre.nomMembre) = "+nb+")";
    req+=" ORDER BY totalNote DESC";

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     int idMembre = rs.getInt("idMembre");
     Collaborateur theCollaborateur = new Collaborateur(idMembre );
     theCollaborateur.nom = rs.getString("nomMembre");
     theCollaborateur.prenom = rs.getString("prenomMembre");
     theCollaborateur.Login = rs.getString("LoginMembre");
     theCollaborateur.photo = rs.getString("photo");
     theCollaborateur.nomService = rs.getString("nomService");
     theCollaborateur.nomDirection = rs.getString("nomDirection");
     theCollaborateur.noteCompetence = rs.getInt("totalNote");

     //theCollaborateur.getTotalCompetenceRoadmap(nomBase,myCnx,st2, this.id);
     //this.ListeCollaborateurs.addElement(theCollaborateur);
     // note = noteCompetence - note Dispo
     // insertion dans temp liste
     // insertoion dans tempListe
     req = "INSERT tempListeMembres (  idMembre, nomMembre, prenomMembre, LoginMembre, noteCompetence, noteDisponibilite, photo) ";
     req+=" VALUES (";
     req+="'"+theCollaborateur.id + "'";   
     req+=",";
     req+="'"+theCollaborateur.nom + "'";   
     req+=",";
     req+="'"+theCollaborateur.prenom + "'";   
     req+=",";
     req+="'"+theCollaborateur.Login + "'";   
     req+=",";
     req+="'"+theCollaborateur.noteCompetence + "'";   
     req+=",";
     req+="'"+theCollaborateur.noteCharge + "'";   
     req+=",";
     req+="'"+theCollaborateur.photo + "'";       
     req+=")";
 
     myCnx.ExecReq(st2, myCnx.nomBase, req);    
   }

  }
   catch (SQLException s) { }
  
  // Lecture du TOP 20 par note decroissante
  // insertion dans ListeCollaborateurs
    req="SELECT   tempListeMembres.Login, tempListeMembres.idMembre, tempListeMembres.nomMembre, tempListeMembres.prenomMembre, tempListeMembres.LoginMembre, tempListeMembres.photo,";
    req+="                      tempListeMembres.noteCompetence, tempListeMembres.noteDisponibilite, tempListeMembres.noteCompetence - tempListeMembres.noteDisponibilite / 5 AS note, Service.nomService, ";
    req+="                       Direction.nomDirection";
    req+=" FROM         Direction INNER JOIN";
    req+="                       Service ON Direction.idDirection = Service.dirService INNER JOIN";
    req+="                       Membre ON Service.idService = Membre.serviceMembre INNER JOIN";
    req+="                       tempListeMembres ON Membre.idMembre = tempListeMembres.idMembre";
    req+=" ORDER BY note DESC";
    
    //req+=" where idRoadmap <> "+this.id;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        int idMembre = rs.getInt("idMembre");
        Collaborateur theCollaborateur = new Collaborateur(idMembre );
        theCollaborateur.nom = rs.getString("nomMembre");
        theCollaborateur.prenom = rs.getString("prenomMembre");
        theCollaborateur.Login = rs.getString("LoginMembre");
        theCollaborateur.photo = rs.getString("photo");
        theCollaborateur.noteCompetence = rs.getInt("noteCompetence");
        theCollaborateur.noteCharge = rs.getInt("noteDisponibilite");
        theCollaborateur.nomService = rs.getString("nomService");
        theCollaborateur.nomDirection = rs.getString("nomDirection");

        ListeCollaborateurs.addElement(theCollaborateur);
      }
    }
            catch (SQLException s) {s.getMessage();}  

  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    if (this.theCollaborateur != null) this.theCollaborateur.dump();
    if (this.theLogiciel != null) this.theLogiciel.dump();
    System.out.println("id="+this.note);

    System.out.println("==================================================");
  }
  public static void main(String[] args) {
    Competence competence = new Competence();
  }
}
