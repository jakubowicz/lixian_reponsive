package PO; 

import java.util.Calendar;
import java.sql.Statement;
import java.sql.ResultSet;
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
public class save_In_LF {
  public save_In_LF() {
  }

  public static void main(String[] args) {
    Connexion myCnx = null;
    Statement st = null;
    ResultSet rs = null;

    String base = myCnx.getDate();
    st = myCnx.Connect();

    String nomProjet= "";
    String nomSt= "";
    String nomVersion= "";
    int Index = 0;

    st = Connexion.Connect();

    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH) + 1;

    PO thePO = new PO();
    thePO.LF_Month = -1;
    thePO.LF_Year = -1;

    thePO.getLignesRoadmap(Connexion.nomBase,myCnx,st);
    thePO.bd_InsertLignesRoadmap(Connexion.nomBase, myCnx, st, "ManageLF", currentMonth, currentYear);

    Connexion.Unconnect(st);


  }
}
