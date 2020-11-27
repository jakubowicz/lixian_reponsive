package Favori; 

import java.sql.ResultSet;
import java.sql.Statement;
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
public class itemNavigation {
  public int idObj;
  public String nomObj;
  public String typeObj;
  public String descObj;
  public String aliasObj;
  public String rubriqueObj;
  public String Niveau;

  public itemNavigation(int idObj, String Niveau,String typeObj) {
    this.idObj = idObj;
    this.Niveau = Niveau;
    this.typeObj = typeObj;
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;

     String req = "";
     String libRubrique="";
     if (typeObj.equals("Membre")) {
             req = "SELECT  nomMembre,prenomMembre, fonctionMembre,nomService,mail FROM Membre, Service WHERE idMembre="+idObj+" AND idService=serviceMembre";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {
               rs.next();
               nomObj = rs.getString(1);
               descObj= "Nom................"+nomObj+"<br>";
               descObj+="Pr�nom............"+rs.getString(2)+"<br>";
               descObj+="Fonction..........."+rs.getString(3)+"<br>";
               descObj+="Service............."+rs.getString(4)+"<br>";
               descObj+="mail................."+rs.getString(5)+"<br>";
               //myCnx.trace(base,"descObj",descObj);
             } catch (SQLException s) {s.getMessage(); }


     }


     else if (typeObj.equals("MacroSt")) {
             libRubrique = "SI d'appartenance";
             req = "SELECT nomMacroSt, descMacroSt, aliasMacroSt,nomSI FROM MacroSt, SI WHERE idMacrost='"+idObj+"' AND siMacroSt=idSI";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=rs.getString(3); rubriqueObj=rs.getString(4);	} catch (SQLException s) {s.getMessage(); }

     }

     else if (typeObj.equals("Criticite")) {
        //myCnx.trace(base,"coucou <<<<<<<<<","");
             libRubrique = "Rupture technologique";
             req = "SELECT     TypeCriticite.nomTypeCriticite, TypeCriticite.descCriticite FROM  TypeCriticite INNER JOIN   Criticite ON TypeCriticite.idTypeCriticite = Criticite.CriticiteTypeCriticite WHERE  Criticite.idCriticite ='"+idObj+"' ";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=""; rubriqueObj="";	} catch (SQLException s) {s.getMessage(); }
             if ((descObj)==null) descObj = "d�finition non connue";
     }

     else if (typeObj.equals("TypeRupture")) {
             libRubrique = "Rupture technologique";
             req = "SELECT     nomMiddleware, descMiddleWare FROM Middleware WHERE idMiddleware ='"+idObj+"' ";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=""; rubriqueObj="";	} catch (SQLException s) {s.getMessage(); }
             if ((descObj)==null) descObj = "d�finition non connue";
     }

     else if (typeObj.equals("TypeSi")) {
             libRubrique = "Type de SI d'appartenance";
             req = "SELECT nomTypeSi, descTypeSi,nomSI FROM TypeSi, SI WHERE idTypeSi='"+idObj+"' AND siTypesi=idSI";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=descObj; rubriqueObj=rs.getString(3);	} catch (SQLException s) {s.getMessage(); }
     }

     else if (typeObj.equals("TypeSiMOA")) {
             libRubrique = "Type de SI d'appartenance";
             req = "SELECT     TypeSi.nomTypeSi, TypeSi.descTypeSi FROM   TypeSi INNER JOIN  Service ON TypeSi.siTypesi = Service.siService WHERE     (50 * TypeSi.idTypeSi + Service.idService ='"+idObj+"') ";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=descObj; rubriqueObj="";	} catch (SQLException s) {s.getMessage(); }
     }

     else if (typeObj.equals("TypeAnnonce")) {
             libRubrique = "Type de SI d'appartenance";
             req = "SELECT     libelle, descPerso FROM   Personalisation WHERE (idPerso ='"+idObj+"') ";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=descObj; rubriqueObj="";	} catch (SQLException s) {s.getMessage(); }
     }

     else if ((typeObj.equals("SI")) || (typeObj.equals("nom du SI"))) {
             libRubrique = "SI d'appartenance";
             req = "SELECT nomSi, descSi FROM SI WHERE idSi='"+idObj+"'";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=descObj; rubriqueObj=rs.getString(3);	} catch (SQLException s) {s.getMessage(); }
     }
     else if (typeObj.equals("Etat")) {
             libRubrique = "Etat du ST";
             req = "SELECT nomEtat, descEtat FROM Etat WHERE idEtat='"+idObj+"'";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=descObj; rubriqueObj=rs.getString(3);	} catch (SQLException s) {s.getMessage(); }
     }
     else if (typeObj.equals("Service")) {
             libRubrique = "Service";
             req = "SELECT nomService, descService FROM Service WHERE idService='"+idObj+"'";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=descObj; rubriqueObj=rs.getString(3);	} catch (SQLException s) {s.getMessage(); }


     }
     else if (typeObj.equals("Direction")) {
             libRubrique = "Direction";
             req = "SELECT nomDirection , libelleDirection  FROM Direction WHERE idDirection='"+idObj+"'";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=descObj; rubriqueObj=rs.getString(3);	} catch (SQLException s) {s.getMessage(); }


     }
     else if (typeObj.equals("FamilleOM")) {
             libRubrique = "Famille Objet M�tier";
             req = "SELECT nomFamilleOM, descFamilleOM FROM FamilleOM WHERE idFamilleOM='"+idObj+"'";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=descObj; rubriqueObj=rs.getString(3);	} catch (SQLException s) {s.getMessage(); }
     }
     else if (typeObj.equals("Plan de Charge")) {
             libRubrique = "Famille Objet M�tier";
             req = "SELECT     St.nomSt, VersionSt.descVersionSt FROM         St INNER JOIN    VersionSt ON St.idSt = VersionSt.stVersionSt WHERE  VersionSt.idVersionSt = '"+idObj+"'";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=descObj; rubriqueObj=rs.getString(3);	} catch (SQLException s) {s.getMessage(); }
             //nomObj = idObj;
             //descObj = "description du plan de charge";
     }
     else if (typeObj.equals("Package")) {
       //myCnx.trace(base,"**********YYYY",""+idObj);
             libRubrique = "Famille Objet M�tier";
             req = "SELECT     nomPackage, descPackage FROM   packageOM WHERE idPackage = '"+idObj+"'";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=descObj; rubriqueObj=rs.getString(3);	} catch (SQLException s) {s.getMessage(); }
     }
     else if (typeObj.equals("Membre")) {
             libRubrique = "Membre";
             req = "SELECT nomMembre, descMembre FROM Membre WHERE idMembre='"+idObj+"'";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=descObj; rubriqueObj=rs.getString(3);	} catch (SQLException s) {s.getMessage(); }
     }


     else if (typeObj.equals("SousPhaseNP")) {
             libRubrique = "Phase du NP";
             req = "SELECT nomSousPhaseNP, descSousPhaseNP, aliasSousPhaseNP,nomPhaseNP FROM SousPhaseNP, PhaseNP WHERE idSousPhaseNP="+idObj+" AND phaseSousPhaseNP=idPhaseNP";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=rs.getString(3); rubriqueObj=rs.getString(4);	} catch (SQLException s) {s.getMessage(); }
     }

     else if (typeObj.equals("TypeEtatST")) {
             libRubrique = "TypeEtatST";
             req = "SELECT     nom2TypeEtat, desc2TypeEtat, idTypeEtat FROM  TypeEtat WHERE     (idTypeEtat ="+idObj+")";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=""; rubriqueObj="";	} catch (SQLException s) {s.getMessage(); }
     }

     else if (typeObj.equals("TypeInterface")) {
             libRubrique = "TypeEtatST";
             req = "SELECT     nomImplementation, descImplementation FROM         Implementation WHERE     (idImplementation ="+idObj+")";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=""; rubriqueObj="";	} catch (SQLException s) {s.getMessage(); }
      //nomObj="nomObj"; descObj="descObj"; aliasObj="aliasObj"; rubriqueObj="rubriqueObj";
     }

     else if (typeObj.equals("TypeEtatOM")) {
             libRubrique = "TypeEtatST";
             req = "SELECT     nom2TypeEtat, desc3TypeEtat, idTypeEtat FROM  TypeEtat WHERE     (idTypeEtat ="+idObj+")";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=""; rubriqueObj="";	} catch (SQLException s) {s.getMessage(); }
     }

     else if (typeObj.equals("ObjetMetier")) {
             req = "SELECT nomObjetMetier, defObjetMetier, nomFamilleOM FROM ObjetMetier, FamilleOM WHERE idObjetMetier="+idObj+" AND famObjetMetier=idFamilleOM";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); rubriqueObj=rs.getString(3); } catch (SQLException s) {s.getMessage(); }
             // Domaine(s) d'application de l'OM

     }

     else if (typeObj.equals("Approbation")) {
             libRubrique = "Approbation MOA";
             req = "SELECT Approbation.nomApprobation,  'niveau Approbation par ' + Service.nomService + ' de type:' + Approbation.nomApprobation AS descApprobation FROM Approbation CROSS JOIN Service WHERE  (50 * Service.idService + Approbation.numApprobation ='"+idObj+"')";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2);	} catch (SQLException s) {s.getMessage(); }
     }

     else if (typeObj.equals("Roadmap")) {
             typeObj="Direction";
             libRubrique = "Direction";
             req = "SELECT     nomDirection, libelleDirection, idDirection FROM   Direction WHERE  idDirection= '"+idObj+"'";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=descObj; rubriqueObj=libRubrique;	} catch (SQLException s) {s.getMessage(); }
     }

     else if (typeObj.equals("Equipe")) {
             libRubrique = "Equipe";
             req = "SELECT     nom, [desc] FROM  Equipe WHERE     id =  '"+idObj+"'";
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); nomObj=rs.getString(1); descObj=rs.getString(2); aliasObj=descObj; rubriqueObj=libRubrique;	} catch (SQLException s) {s.getMessage(); }
     }


     else {
             req = "SELECT desc"+typeObj+",nom"+ typeObj + " FROM "+typeObj+" WHERE id"+typeObj+"="+idObj;
             rs = myCnx.ExecReq(st, myCnx.nomBase, req);
             try {rs.next(); descObj = rs.getString(1); nomObj = rs.getString(2);} catch (SQLException s) {s.getMessage(); }
             //myCnx.trace(base,"descObj",descObj);
           }
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("nomObj="+nomObj);
    System.out.println("descObj="+this.descObj);
    System.out.println("aliasObj="+this.aliasObj);
    System.out.println("rubriqueObj="+this.rubriqueObj);

    System.out.println("==================================================");
  }

  public static void main(String[] args) {
    itemNavigation itemnavigation = new itemNavigation(1,"","");
  }
}
