package Traces; 

import java.util.Vector;
import java.sql.Statement;
import java.sql.ResultSet;
import Statistiques.LigneStat;
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
public class Trace {
  String orderBy ;
  String sens ;
  String action ;
  public Vector ListeLigneTrace = new Vector(10);

  public Trace(String orderBy,String sens,String action  ) {
    this.orderBy = orderBy;
    this.sens = sens;
    this.action = action;
  }

  public  void getListeLigneTrace(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    String req;
    if (this.action.equals("delete"))
    {
            req ="delete from Traces";
            rs = myCnx.ExecReq(st, nomBase, req);
  }

     req="SELECT * from Traces";
    req+=" ORDER BY "+this.orderBy+" "+this.sens;

     rs = myCnx.ExecReq(st, nomBase, req);

    try { while(rs.next()) {
        String timeStamp  = rs.getString(1);
        String base = rs.getString(2);
        String pageHtml = rs.getString(3);
        String typeTrace = rs.getString(4);
        String trace = rs.getString(5).replaceAll("\r","").replaceAll("\n","<br>");;

        LigneTrace theLigneStat = new LigneTrace (timeStamp,base, pageHtml,typeTrace,trace);
        this.ListeLigneTrace.addElement(theLigneStat);
        }	} catch (SQLException s) {s.getMessage();}
}

  public static void main(String[] args) {
    Trace trace = new Trace("","","");
  }
}
