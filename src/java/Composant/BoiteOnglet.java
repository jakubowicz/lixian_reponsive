package Composant; 

import java.util.Vector;
import Organisation.Collaborateur;
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
public class BoiteOnglet extends item {
  private String req;
  public Vector ListeOnglets = new Vector(10);
  private static boolean isSetSuperAdmin=false;

  public BoiteOnglet() {
      isSetSuperAdmin=false;
  }

  public BoiteOnglet(String nom) {
    this.nom=nom;
    isSetSuperAdmin=false;
  }
  
  public BoiteOnglet(int id) {
    this.id=id;
    isSetSuperAdmin=false;
  }  

  public void addOnglet(Onglet theOnglet) {
    this.ListeOnglets.addElement(theOnglet);
  }

  public int getNumOnglet(String nomOnglet){
    for (int i=0; i < this.ListeOnglets.size(); i++)
    {
      Onglet theOnglet = (Onglet)this.ListeOnglets.elementAt(i);
      if (theOnglet.nom.equals(nomOnglet)) return i;
    }
    return -1;
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;

    req="SELECT     Onglet.ordre, Onglet.nom, Onglet.signet, Onglet.visibility, Onglet.Type, Onglet.Action, Onglet.Admin";
    req+=" FROM         Onglet";
    //req+=" WHERE     (Onglet.Type = '"+this.nom+"')";
    req+=" WHERE     (Onglet.idPere = '"+this.id+"')";
    req+=" ORDER BY Onglet.ordre";
    
    req = "SELECT    DISTINCT Onglet.Ordre, Onglet.nom, Onglet.signet, Onglet.visibility, Onglet.Type, Onglet.Action, Onglet.Admin, Onglet_1.nom AS nomPere";
    req+=" FROM         Onglet INNER JOIN";
    req+="                       Onglet AS Onglet_1 ON Onglet.idPere = Onglet_1.id";
    req+=" WHERE     (Onglet_1.nom = '"+this.nom+"') AND (Onglet.visibility = 1)";
    req+=" ORDER BY Onglet.Ordre";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        Onglet theOnglet = new Onglet();
        theOnglet.ordre = rs.getInt("ordre");
        theOnglet.nom = rs.getString("nom");
        theOnglet.signet = rs.getString("signet");
        theOnglet.visibility = rs.getInt("visibility");
        theOnglet.image = "";
        theOnglet.Action = rs.getString("Action");
        theOnglet.Admin = rs.getInt("Admin");

        if (theOnglet.visibility == 1)
            this.ListeOnglets.addElement(theOnglet);
      }
    }
              catch (SQLException s) {s.getMessage();}


}
  
  public void setProfil(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    this.ListeOnglets.removeAllElements();
    if (this.nom.equals("ARCHITECTURE"))
    {
        this.ListeOnglets.addElement(new Onglet("Accueil", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Budget", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Brief", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Projet", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Tache", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Devis", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Test", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Produit", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Processus", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Dictionnaire", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Support", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Admin", 1,"" ));
    }
    else if (this.nom.equals("PROJET"))
    {
        this.ListeOnglets.addElement(new Onglet("Accueil", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Budget", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Brief", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Projet", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Tache", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Devis", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Test", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Produit", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Processus", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Dictionnaire", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Support", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Admin", 1,"" ));        
    }   
    else if (this.nom.equals("GOUVERNANCE"))
    {
        this.ListeOnglets.addElement(new Onglet("Accueil", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Budget", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Brief", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Projet", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Tache", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Devis", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Test", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Produit", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Processus", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Dictionnaire", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Support", 1,"" ));
        this.ListeOnglets.addElement(new Onglet("Admin", 1,"" ));         
    } 
    else
    {
        this.ListeOnglets.addElement(new Onglet("Accueil", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Budget", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Brief", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Projet", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Tache", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Devis", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Test", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Produit", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Processus", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Dictionnaire", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Support", 0,"" ));
        this.ListeOnglets.addElement(new Onglet("Admin", 0,"" ));
    }
    
    for (int i=0; i < this.ListeOnglets.size(); i++)
    {
        Onglet theOnglet = (Onglet)this.ListeOnglets.elementAt(i);
        req = "UPDATE    Onglet  SET ";
        req +=" visibility  ='"+ theOnglet.visibility + "'";
        req += " WHERE (Type =  'home')";
        req += " AND (nom =  '"+theOnglet.nom+"')";

        rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    }



}  

public void setVitrine(String nomBase,Connexion myCnx, Statement st, Collaborateur theCollaborateur ){
    
                  
        addOngletForBaseVide(1, "Accueil", "javascript:AfficherPage('FicheST','vitrine_accueil.jsp?visiteur=yes','Création')", -1, -1);
        addOngletForBaseVide(2, "Architecture", "javascript:AfficherPage('FicheST','vitrine_Architecture.jsp?visiteur=yes','Création')", -1, -1);
        addOngletForBaseVide(3, "Projet", "javascript:AfficherPage('FicheST','vitrine_Projet.jsp','Création')", -1, -1);
        addOngletForBaseVide(4, "Gouvernance", "javascript:AfficherPage('FicheST','vitrine_Gouvernance.jsp','Création')", -1, -1);     
        addOngletForBaseVide(5, "nos Produits", "javascript:AfficherPage('FicheST','vitrine_Produits.jsp','Création')", -1, -1);
        addOngletForBaseVide(6, "nos Prix", "javascript:AfficherPage('FicheST','vitrine_Prix.jsp','Création')", -1, -1);
        addOngletForBaseVide(7, "Contact", "javascript:AfficherPage('FicheST','vitrine_contact.jsp','Création')", -1, -1);
                                    
        

}


public void setSuperAdmin(String nomBase,Connexion myCnx, Statement st, Collaborateur theCollaborateur ){
    
    if (!theCollaborateur.isSuperAdmin()) return;
        addOngletForBaseVide(900, "Super Admin", "javascript:AfficherPageBas('Administration',6,'Validation','Admin','Mes Contacts','Menu3')", -1, -1);
        
             
        addOngletForBaseVide(901, "Mes Contacts", "javascript:AfficherPageBas('Administration',6,'Validation','Admin','Mes Contacts','Menu3')", -1, 900);
        addOngletForBaseVide(902, "Mes Clients", "javascript:AfficherPageBas('Administration',6,'Validation','Admin','Mes Clients','Menu3')", -1, 900);
        addOngletForBaseVide(903,"Backup", "javascript: AfficherPage('Administration', 'gen_backup.jsp?idRoadmap=0','Création')", -1, 900);
        addOngletForBaseVide(904,"Génération Clefs", "javascript: AfficherPage('Administration', 'gen_key.jsp','Création')", -1, 900);
                                                                       
    
}

public void setSuperAdminPere(String nomBase,Connexion myCnx, Statement st, Collaborateur theCollaborateur ){
    
    if (!theCollaborateur.isSuperAdmin()) return;
        addOngletForBaseVide(900, "Super Admin", "javascript:AfficherPageBas('Administration',6,'Validation','Admin','Mes Contacts','Menu3')", -1, -1);
                                                                               
    
}

public void setSuperAdminFils(String nomBase,Connexion myCnx, Statement st, Collaborateur theCollaborateur ){
    
    if (!theCollaborateur.isSuperAdmin()) return;        
             
        addOngletForBaseVide(901, "Mes Contacts", "javascript:AfficherPageBas('Administration',6,'Validation','Admin','Mes Contacts','Menu3')", -1, 900);
        addOngletForBaseVide(902, "Mes Clients", "javascript:AfficherPageBas('Administration',6,'Validation','Admin','Mes Clients','Menu3')", -1, 900);
        //addOngletForBaseVide(903,"Backup", "javascript: AfficherPage('Administration', 'gen_backup.jsp?idRoadmap=0','Création')", -1, 900);
        addOngletForBaseVide(903,"Dump", "javascript: AfficherPage('Administration', 'gen_dump.jsp?idRoadmap=0','Création')", -1, 900);
        addOngletForBaseVide(904,"Génération Clefs", "javascript: AfficherPage('Administration', 'gen_key.jsp','Création')", -1, 900);
        addOngletForBaseVide(904,"Test de Connexion", "javascript: AfficherPage('Administration', 'gen_testConnexion.jsp','Création')", -1, 900);
        addOngletForBaseVide(904,"Gestion RDS et Standalone", "javascript: AfficherPage('Administration', 'gen_restoreBases.jsp','Création')", -1, 900);
        addOngletForBaseVide(905,"Gestion Onglets Internes", "javascript:AfficherPageBas('Administration',103,'Validations','Admin','Gestion Onglets Internes','Menu3')", -1, 900);
        addOngletForBaseVide(904,"Programmes de Migration", "javascript: AfficherPage('Administration', 'gen_migration.jsp','Création')", -1, 900);
        
                                                                       
    
}

public void getAllFromBdByCollaborateur(String nomBase,Connexion myCnx, Statement st, Collaborateur theCollaborateur, String client ){
    

              ResultSet rs;

              req="SELECT     Onglet.id,Onglet.idPere,Onglet.ordre, idicone, Onglet.nom, Onglet.signet, Onglet.visibility, Onglet.Type, Onglet.Action, Onglet.Admin";
              req+=" FROM         Onglet ";
              req+=" WHERE     (Onglet.idPere = '"+this.id+"') AND (isManaged = 1) AND (visibility = 1)";
              req+=" AND Onglet.id <> 90009";
              req+=" ORDER BY Onglet.ordre";


              rs = myCnx.ExecReq(st, myCnx.nomBase, req);
              try {
                while (rs.next()) {
                  Onglet theOnglet = new Onglet();
                  theOnglet.id = rs.getInt("id");
                  theOnglet.idPere = rs.getInt("idPere");
                  theOnglet.ordre = rs.getInt("ordre");
                  theOnglet.idicone = rs.getInt("idicone");
                  theOnglet.nom = rs.getString("nom");
                  theOnglet.signet = rs.getString("signet");
                  theOnglet.visibility = rs.getInt("visibility");
                  theOnglet.image = "";
                  theOnglet.Action = rs.getString("Action");
                  theOnglet.Admin = rs.getInt("Admin");
                  //myCnx.trace(theOnglet.nom+"----","theOnglet.Admin",""+theOnglet.Admin+":: isProjet= "+theCollaborateur.isProjet);

                  if
                      (
                       (theOnglet.Admin == 0)
                          ||
                       ((theOnglet.Admin == -1) &&  (client.equals("Vide")))                          
                       ||
                       ((theOnglet.Admin == 1) && (theCollaborateur.isAdmin == 1)) // Onglet Admin
                       ||
                       ((theOnglet.Admin == 2) && (theCollaborateur.isProjet == 1)) // Onglet Projet
                       ||
                       ((theOnglet.Admin == 3) && ((theCollaborateur.isProjet == 1) || (theCollaborateur.isTest == 1)) ) // Onglet Test
                       ||
                       //((theOnglet.Admin == 4) && (theCollaborateur.isDPS == 1) && ((theCollaborateur.Manager.equals("yes") ) || (theCollaborateur.GOUVERNANCE.equals("yes") ))) // Onglet PO: etre DPS et Manager
                       ((theOnglet.Admin == 4) &&  ((theCollaborateur.isManager == 1) || (theCollaborateur.GOUVERNANCE.equals("yes") ))) // Onglet PO: Manager
                       ||
                       ((theOnglet.Admin == 5) ) 
                       ||
                       ((theOnglet.Admin == 6) && (theCollaborateur.isPo == 1)) // Onglet PO: isPO=1
                       )
                  {
                    //myCnx.trace(theOnglet.nom+"----","add","");
                        this.ListeOnglets.addElement(theOnglet);
                  }
                  else
                  {
                    //myCnx.trace(theOnglet.nom+"----","removed","");
                  }
                }
              }
                        catch (SQLException s) {
                          s.getMessage();
                        }
}

  public void addOngletForBaseVide(int id, String nom, String Action, int Admin, int idPere){
        Onglet theOnglet = new Onglet(id);                        
                  theOnglet.ordre = 3;
                  theOnglet.idicone = 3;
                  theOnglet.nom = nom;
                  theOnglet.signet = nom;
                  theOnglet.visibility = 1;
                  theOnglet.isManaged = 1;
                  theOnglet.Action = Action;
                  theOnglet.Admin = Admin;  
                  theOnglet.idPere = idPere;
                                    
                  //if (this.nom.equals("Home"))
                          this.ListeOnglets.addElement(theOnglet); 
  }
  
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("nom="+this.nom);

    System.out.println("this.ListeItems.size()="+this.ListeOnglets.size());
    for (int i=0; i < this.ListeOnglets.size(); i++)
    {
      Onglet theOnglet = (Onglet)this.ListeOnglets.elementAt(i);
      //theOnglet.dump();
    }
    System.out.println("==================================================");
  }


}
