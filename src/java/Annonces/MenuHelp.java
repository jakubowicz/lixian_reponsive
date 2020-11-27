package Annonces; 

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
public class MenuHelp {
  public String menu1="";
  public String L1C1="";
  public String L2C1="";
  public String L3C1="";
  public String C2="";

  public MenuHelp(String menu1) {
    this.menu1 = menu1;
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    String req="";
     ResultSet rs = null;

     req = req="SELECT homeRubrique.idRubrique, homeRubrique.menu1, homeRubrique.menu2, help.jsp AS L1C1, help_1.jsp AS L2C1, help_2.jsp AS L3C1, help_3.jsp AS C2";
     req+=	" FROM         homeRubrique INNER JOIN";
     req+=   " help ON homeRubrique.L1C1 = help.idHome INNER JOIN";
     req+=   " help help_3 ON homeRubrique.C2 = help_3.idHome LEFT OUTER JOIN";
     req+=   " help help_2 ON homeRubrique.L3C1 = help_2.idHome LEFT OUTER JOIN";
     req+=   " help help_1 ON homeRubrique.L2C1 = help_1.idHome";
     req+=   " WHERE  (homeRubrique.menu1 = '"+this.menu1+"')";

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       this.L1C1=rs.getString("L1C1");
       this.L2C1=rs.getString("L2C1");
       this.L3C1=rs.getString("L3C1");
       this.C2=rs.getString("C2");

       } catch (SQLException s) {s.getMessage(); }

       if (this.L1C1==null) this.L1C1="";
       if (this.L2C1==null) this.L2C1="";
       if (this.L3C1==null) this.L3C1="";
       if (this.C2==null) this.C2="";
  }

  public static void main(String[] args) {
    MenuHelp menuhelp = new MenuHelp("");
  }
}
