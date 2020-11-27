package Projet; 

import accesbase.Connexion;
import General.Utils;
import accesbase.ErrorSpecific;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

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
public class ActionAnomalie {
  public   int id;
  public   int idAction;
  public String  dateAnomalie;

  private String req="";

  public ActionAnomalie(int id) {
    this.idAction = id;
    Calendar calendar = Calendar.getInstance();
    this.dateAnomalie = calendar.get(Calendar.DATE)+"/"+(calendar.get(Calendar.MONTH) +1)+"/"+calendar.get(Calendar.YEAR);
  }

  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

    Calendar calendar = Calendar.getInstance();

    String str_dateAnomalie ="convert(datetime, '"+this.dateAnomalie+"', 103)";

    req = "INSERT ActionAnomalie (idAction, dateAnomalie)";
    req+=" VALUES (";
    req+="'"+this.idAction+"'";
    req+=",";
    req+=""+str_dateAnomalie+"";

    req+=")";


    //myCnx.trace("@@456789--------","req",""+req);
      ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);


    return myError;
  }
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("idAction="+this.idAction);
    System.out.println("dateAnomalie="+this.dateAnomalie);

    System.out.println("==================================================");
 }

  public static void main(String[] args) {
    ActionAnomalie actionanomalie = new ActionAnomalie(1);
  }
}
