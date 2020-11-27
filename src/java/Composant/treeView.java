package Composant;
import java.util.Vector; 

import accesbase.Connexion;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public class treeView {
  static Connexion myCnx;

  public static int nbniveau= 0;

  public String req="";
  public String req_fin="";

  public int id;
  public String nom;
  public String alias;
  public String display="";
  public int niveau = 0;
  public int taille = 0;

  public Vector ListeTreeViews = new Vector(10);


  public treeView(int niveau) {
    this.niveau = niveau;

  }

  public void addNiveaux(String nomBase,Connexion myCnx, Statement st,  String req,String req_fin){

    if (this.niveau == 0)
      this.req = req;
    else
    {
      this.req = req + this.id + req_fin;
    }

    this.getListeSubItems( nomBase, myCnx,  st);
    int size = this.ListeTreeViews.size();
    if (this.ListeTreeViews.size() >= 1)
    {
      treeView thetreeView = (treeView)this.ListeTreeViews.elementAt(this.ListeTreeViews.size()-1);
      if (thetreeView.nom.equals("-- Cr�er"))size = this.ListeTreeViews.size() -1;
    }

    //this.nom +=" ("+size+")";

  }

  private void getListeSubItems(String nomBase,Connexion myCnx, Statement st){
        ResultSet rs=null;

        int i =0;
        String nom = "";
        //System.out.println("this.req="+this.req);
         rs = myCnx.ExecReq(st, nomBase, this.req);
         try {
            while (rs.next()) {
              int id = rs.getInt(1);
              String alias = rs.getString(2);
              //System.out.println("id="+id+"alias="+alias);
              if (alias.length() > 25)
               nom = alias.substring(0,25);
             else
               nom = alias;
              
              nom = alias;

              treeView thetreeView = new treeView(this.niveau +1);
//myCnx.trace("-- avant: ","thetreeView.nom",""+nom);
              thetreeView.id = id;
              //thetreeView.nom = nom.replaceAll("'", "&apos;").replace('\u0022',' ');
              thetreeView.nom = nom.replaceAll("'", "&apos;").replaceAll("\"", "&quot;").replaceAll("\r","").replaceAll("\n","");
              thetreeView.nom = thetreeView.nom.replaceAll("zz", "");
              thetreeView.alias = alias.replaceAll("'", "&apos;").replaceAll("\"", "&quot;").replaceAll("\r","").replaceAll("\n","");
              thetreeView.alias = thetreeView.alias.replaceAll("zz", "");
//myCnx.trace("-- après: ","thetreeView.nom",""+thetreeView.nom);
              this.ListeTreeViews.addElement(thetreeView);
            }
     } catch (SQLException s) {s.getMessage();}


}

public int getSize(){
  int nb=0;
  for (int i=0; i < this.ListeTreeViews.size(); i++)
  {
    treeView thetreeView = (treeView)this.ListeTreeViews.elementAt(i);
    if (!thetreeView.nom.equals("-- Creer")) nb++;

  }
  return nb;
}

    public void dump(){
      if (Connexion.traceOn.equals("no")) return;

      System.out.println("==================================================");
      System.out.println("niveau="+this.niveau);
      System.out.println("id="+this.id);
      System.out.println("nom="+this.nom);
      System.out.println("alias="+this.alias);
      System.out.println("req="+this.req);
      System.out.println("req_fin="+this.req_fin);

      System.out.println("this.ListeItems.size()="+this.ListeTreeViews.size());
      for (int i=0; i < this.ListeTreeViews.size(); i++)
      {
        treeView thetreeView = (treeView)this.ListeTreeViews.elementAt(i);
        //thetreeView.dump();
      }
      System.out.println("==================================================");
  }

  public static void main(String[] args) {
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    int idMembre;
    String NomProjet, previousActivite= "";
    float semaine =0;

    String reqRefSt="";
    st = myCnx.Connect();
    st2 = myCnx.Connect();

/*
    String req_n1 = "SELECT idSI, nomSI FROM SI ORDER BY ordre asc";
    String req_n2 = "SELECT idMacrost, aliasMacrost, nomMacrost FROM MacroSt WHERE siMacrost=";
    String req_n3 = "SELECT idVersionSt, nomSt, versionRefVersionSt FROM St, VersionSt WHERE e";
    req_n3 += "tatFicheVersionSt=3 AND etatVersionSt != 4 AND stVersionSt = idSt AND versionRef";
    req_n3 += "VersionSt=(SELECT MAX(versionRefVersionSt) FROM VersionSt WHERE etatFicheversion";
    req_n3 += "St=3 AND stVersionSt=idSt) AND macrostVersionSt=";
 String req_n1 = "SELECT * FROM (SELECT VersionSt.idVersionSt, St.nomSt FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoaVersionSt = Membre.idMembre WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (Membre.LoginMembre = '"+"JJAKUBOW"+"') UNION SELECT ListeST.idVersionSt, ListeST.nomSt FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt WHERE (Membre.LoginMembre = '"+"JJAKUBOW"+"')) AS maTable ORDER BY nomSt";
 String req_n2 = "SELECT     idRoadmap, version,version FROM  Roadmap WHERE (Etat <> 'MES') AND  idVersionSt  = ";
 String req_n2_fin = "";
 String req_n3 = "SELECT     id, (CAST(DAY(date) AS varchar(20)) + '/' + CAST(MONTH(date) AS varchar(20)) + '/' + CAST(YEAR(date)  AS varchar(20))) + '-' + CAST(num AS varchar(20)) + ' ' + CAST(version AS varchar(MAX)) as myDate, ''  FROM         Campagne WHERE   idRoadmap = ";
String req_n3_fin = "  UNION SELECT  99999, '-- Cr�er',''";
*/
String req_n1 = req_n1=  "exec SERVICE_nb_retardDEVIS '"+"74"+"'";
int nbNiveaux=1;
String req_n2 = "";
String req_n2_fin = "";
String req_n3 = "";
String req_n3_fin ="";

    treeView treeview_n0 = new treeView(0);
    treeview_n0.addNiveaux(myCnx.nomBase, myCnx,  st,req_n1,"");

    for (int i=0; i < treeview_n0.ListeTreeViews.size();i++)
    {
      treeView thetreeView_n1 = (treeView)treeview_n0.ListeTreeViews.elementAt(i);

      if (nbNiveaux > 1)
      {
        thetreeView_n1.addNiveaux(myCnx.nomBase, myCnx, st, req_n2, req_n2_fin);
        //thetreeView_n1.dump();
        for (int j = 0; j < thetreeView_n1.ListeTreeViews.size(); j++) {
          treeView thetreeView_n2 = (treeView) thetreeView_n1.ListeTreeViews.
              elementAt(j);
          thetreeView_n2.addNiveaux(myCnx.nomBase, myCnx, st2, req_n3, req_n3_fin);
          //thetreeView_n2.dump();

        }
      }
    }

    //treeview_n0.dump();
    for ( int i=0; i < treeview_n0.ListeTreeViews.size();i++)
    {
       treeView thetreeView_n1 = (treeView)treeview_n0.ListeTreeViews.elementAt(i);
       //thetreeView_n1.dump();

              if (nbNiveaux > 1)
              {
                for ( int j=0; j < thetreeView_n1.ListeTreeViews.size();j++)
                {
                  treeView thetreeView_n2 = (treeView)thetreeView_n1.ListeTreeViews.elementAt(j);
                  //thetreeView_n2.dump();

                if (nbNiveaux > 2)
                {
                  for (int  k=0; k < thetreeView_n2.ListeTreeViews.size();k++)
                  {
                    treeView thetreeView_n3 = (treeView)thetreeView_n2.ListeTreeViews.elementAt(k);
                    //thetreeView_n3.dump();

                  }
                }

              }

              }


      }
  }


}

