package Composant; 

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
public class Menu {
  static Connexion myCnx;
  final public static int maxNiveaux = 2500;

  public  int maxNiveaux2 = 0;
  public  int maxNiveaux3 = 0;

  public String req_n1="";
  public String req_n2="";
  public String req_n3="";

  public String req_n1_fin="";
  public String req_n2_fin="";
  public String req_n3_fin="";

  public String alias_n1="";
  public String alias_n2="";
  public String alias_n3="";


  public String menu1="";
  public String menu2="";
  public String menu3="";

  public String niveau1="";
  public String niveau2="";
  public String niveau3="";

  public int nbNiveau1 = 0;

  public String displayVersion="";
  public boolean isSecure= false;

  public int nbNiveaux = 1;
  public int[] niveau1_id;

  public int[] nbNiveau2;
  public int[][] niveau2_id;
  public int[][][] niveau3_id;

  public int[][] nbNiveau3;

  public Menu(int nbNiveaux) {
    this.nbNiveaux=nbNiveaux;

  }

  public void setNiveaux(String nomBase,Connexion myCnx, Statement st){

    this.getListeNiveau1_id( nomBase, myCnx,  st);

    if (this.nbNiveaux > 1)
    {
      this.getListeNiveau2_id(nomBase, myCnx, st);
    }
    //System.out.println("niveau2_id["+0+"]["+0+"]= "+niveau2_id[0][0]);

    if (this.nbNiveaux > 2)
      this.getListeNiveau3_id( nomBase, myCnx,  st);

  }

  private void getListeNiveau1_id(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs=null;
  int i =0;

  rs = myCnx.ExecReq(st, nomBase, this.req_n1);
  try {
     while (rs.next()) {
       int x = rs.getInt(1);
       nbNiveau1++;
     }
      } catch (SQLException s) {s.getMessage();}

  this.niveau1_id = new int[nbNiveau1];

  i =0;
  rs = myCnx.ExecReq(st, nomBase, this.req_n1);
  try {
     while (rs.next()) {
       this.niveau1_id[i] = rs.getInt(1);
       i++;
     }
     if (i > this.nbNiveau1) this.nbNiveau1 = i;
      } catch (SQLException s) {s.getMessage();}

}


 private void getListeNiveau2_id(String nomBase,Connexion myCnx, Statement st){
       ResultSet rs=null;
       int i =0;

       this.nbNiveau2 = new int[this.nbNiveau1];
       niveau2_id = new int[nbNiveau1][maxNiveaux];
       for (i=0; i< this.nbNiveau1; i++) {
         this.nbNiveau2[i] = 0;
         //********** Recup�ration des rubriques de niveau 2

        String req = this.req_n2 + niveau1_id[i] + req_n2_fin;
        rs = myCnx.ExecReq(st, nomBase, req);
         int j = 0;
         try {
           while (rs.next()) {
             niveau2_id[i][j] = rs.getInt(1);
             //System.out.println("niveau2_id["+i+"]["+j+"]= "+niveau2_id[i][j]);
             nbNiveau2[i]++;
             j++;
           }
           if (j > this.maxNiveaux2) this.maxNiveaux2 = j;
         }


         catch (SQLException s) {
           s.getMessage();
         }
         //System.out.println("niveau2_id["+0+"]["+0+"]= "+niveau2_id[0][0]);
       }

}

 private void getListeNiveau3_id(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;
    String req = "";
    this.nbNiveau3 = new int[nbNiveau1][maxNiveaux];

    for (int i=0; i<nbNiveau1; i++) {

            if (nbNiveaux>2) {// il y a plus que deux niveaux de rubriques

                    for (int j=0; j<nbNiveau2[i]; j++) {
                      //this.niveau3_id = new int[nbNiveau1][][];
                      //this.niveau3_id[i] = new int[nbNiveau2[i]][maxNiveaux];

                            this.nbNiveau3[i][j] = 0;
                            //********** Recup�ration des rubriques de niveau 3
                            //this.niveau3_id = new int[nbNiveau1][nbNiveau2[i]][maxNiveaux];
                            req = req_n3+niveau2_id[i][j]+req_n3_fin;

                            rs = myCnx.ExecReq(st, nomBase, req);
                            int k=0;
                            try {while(rs.next()) {
                                    int x = rs.getInt(1);
                                   k++;
                            }
                            if (k > this.maxNiveaux3) this.maxNiveaux3 = k;

                          } catch (SQLException s) {s.getMessage();}
                    } // end for sur le niveau 2
            } // end if sur le niveau 2
    } // end for sur le niveau 1

    this.niveau3_id = new int[nbNiveau1][this.maxNiveaux2][this.maxNiveaux3];
    for (int i=0; i<nbNiveau1; i++) {



            if (nbNiveaux>2) {// il y a plus que deux niveaux de rubriques

                    for (int j=0; j<nbNiveau2[i]; j++) {
                      //this.niveau3_id = new int[nbNiveau1][][];
                      //this.niveau3_id[i] = new int[nbNiveau2[i]][maxNiveaux];

                            this.nbNiveau3[i][j] = 0;
                            //********** Recup�ration des rubriques de niveau 3
                            //this.niveau3_id = new int[nbNiveau1][nbNiveau2[i]][maxNiveaux];
                            req = req_n3+niveau2_id[i][j]+req_n3_fin;

                            rs = myCnx.ExecReq(st, nomBase, req);
                            int k=0;
                            try {while(rs.next()) {
                                    this.niveau3_id[i][j][k] = rs.getInt(1);
                                    //System.out.println("**niveau3_id["+i+"]["+j+"]["+k+"]= "+this.niveau3_id[i][j][k]);
                                    this.nbNiveau3[i][j]++;
                                   k++;
                            } } catch (SQLException s) {s.getMessage();}
                    } // end for sur le niveau 2
            } // end if sur le niveau 2
    } // end for sur le niveau 1

}

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("nbNiveaux="+nbNiveaux);
    System.out.println("req_n1="+this.req_n1);
    System.out.println("req_n2="+this.req_n2);
    System.out.println("req_n3="+this.req_n3);
    System.out.println("req_n1_fin="+this.req_n1_fin);
    System.out.println("req_n2_fin="+this.req_n2_fin);
    System.out.println("req_n3_fin="+this.req_n3_fin);
    System.out.println("alias_n1="+this.alias_n1);
    System.out.println("alias_n2="+this.alias_n2);
    System.out.println("alias_n3="+this.alias_n3);
    System.out.println("menu1="+this.menu1);
    System.out.println("menu2="+this.menu2);
    System.out.println("menu3="+this.menu3);
    System.out.println("niveau1="+this.niveau1);
    System.out.println("niveau2="+this.niveau2);
    System.out.println("niveau3="+this.niveau3);

    System.out.println("nbNiveau1="+this.nbNiveau1);

    for (int i=0; i < this.nbNiveau1; i++)
    {
      System.out.println("nbNiveau2["+i+"]=" + this.nbNiveau2[i]);
      System.out.println("niveau1_id[" + i +"]=" + this.niveau1_id[i]);
      for (int j=0; j < this.nbNiveau2[i]; j++)
      {
        System.out.println("niveau2_id[" + i + "]["+j+"]=" + this.niveau2_id[i][j]);
        System.out.println("nbNiveau3[" + i + "]["+j+"]=" + this.nbNiveau3[i][j]);
        for (int k=0; k < this.nbNiveau3[i][j]; k++)
        {
          System.out.println("@@niveau3_id[" + i + "]["+j+"]["+k+"]=" + + this.niveau3_id[i][j][k]);
        }
      }
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

    Menu menu = new Menu(3);
    menu.req_n1 = "SELECT idSI, nomSI FROM SI ORDER BY ordre asc";
    menu.req_n2 = "SELECT idMacrost, aliasMacrost, nomMacrost FROM MacroSt WHERE siMacrost=";
    menu.req_n3 = "SELECT idVersionSt, nomSt, versionRefVersionSt FROM St, VersionSt WHERE e";
    menu.req_n3 += "tatFicheVersionSt=3 AND etatVersionSt != 4 AND stVersionSt = idSt AND versionRef";
    menu.req_n3 += "VersionSt=(SELECT MAX(versionRefVersionSt) FROM VersionSt WHERE etatFicheversion";
    menu.req_n3 += "St=3 AND stVersionSt=idSt) AND macrostVersionSt=";

    menu.setNiveaux(myCnx.nomBase, myCnx, st);
    //menu.dump();

    myCnx.Unconnect(st);
    myCnx.Unconnect(st2);
  }
}
