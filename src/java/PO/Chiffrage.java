package PO; 
import java.util.*;
import Projet.Charge;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import accesbase.Connexion;
import accesbase.ErrorSpecific;
import ST.ST;

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
public class Chiffrage {
  public int id=-1;
  public int idService=-1;
  public String nomService="";
  public String description="";
  public int idPO=-1;
  public int idPOLie=-1;
  public Vector ListeCharges = null;
  public int Annee=-1;
  public int LF=-1;
  public float totalCharge=0;
  public float Forfait=0;
  private String req="";

  public Chiffrage(String nomService,int Annee, int LF) {
    this.nomService = nomService;
    this.Annee = Annee;
    this.LF = LF;
    ListeCharges = new Vector(10);

  }
  public void addCharges(int idVersionSt, float chargeSt)
  {
    Charge theCharge = new Charge(idVersionSt,chargeSt);
    this.ListeCharges.addElement(theCharge);
    this.totalCharge+=chargeSt;
  }

  public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){

      ErrorSpecific myError = new ErrorSpecific();
      ResultSet rs = null;


      req = "delete FROM ChiffragePlanOperationnelClient WHERE (Annee = "+this.Annee+")";
      int check = 0;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
      if (myError.cause == -1) return myError;

      return myError;
}
  
  public ErrorSpecific bd_DeleteMacroProjets(String nomBase,Connexion myCnx, Statement st, String transaction){

      ErrorSpecific myError = new ErrorSpecific();
      ResultSet rs = null;
      
      ST theSt = new ST(1,"");
      int idSi = theSt.get_IdSiFictif(nomBase,myCnx,st);


      req = "delete  from roadmap where roadmap.idVersionst in";
      req+="  ( SELECT      idVersionSt";
      req+="     FROM         ListeST";      
      req+="     WHERE     (nomSt LIKE 'SIR[_,_]%') AND (siVersionSt = "+idSi+")";
      req+="  )";
      
      int check = 0;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
      if (myError.cause == -1) return myError;

      return myError;
}  
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs=null;

    req = "SELECT     id, idService, idPO, idPOLie, ST1, charge1, ST2, charge2, ST3, charge3, ST4, charge4, ST5, charge5, ST6, charge6, ST7, charge7, ST8, charge8, ST9, ";
    req+="                  charge9, ST10, charge10, Annee, LF, nomService, Forfait";
    req+=" FROM         ChiffragePlanOperationnelClient";
    req+=" WHERE     (nomService = '"+this.nomService+"') AND (LF = "+this.LF+") AND (Annee = "+this.Annee+")";
    req+=" ORDER BY idPO";


    rs = myCnx.ExecReq(st, nomBase, req);

    int idVersionSt=-1;
    float charge = 0;

       try {
            rs.next();
            this.id=rs.getInt("id");
            this.idPO=rs.getInt("idPO");
            this.idPOLie=rs.getInt("idPOLie");

            idVersionSt=rs.getInt("ST1");
            charge=rs.getFloat("charge1");
            this.addCharges(idVersionSt,charge);

            idVersionSt=rs.getInt("ST2");
            charge=rs.getFloat("charge2");
            this.addCharges(idVersionSt,charge);

            idVersionSt=rs.getInt("ST3");
            charge=rs.getFloat("charge3");
            this.addCharges(idVersionSt,charge);

            idVersionSt=rs.getInt("ST4");
            charge=rs.getFloat("charge4");
            this.addCharges(idVersionSt,charge);

            idVersionSt=rs.getInt("ST5");
            charge=rs.getFloat("charge5");
            this.addCharges(idVersionSt,charge);

            idVersionSt=rs.getInt("ST6");
            charge=rs.getFloat("charge6");
            this.addCharges(idVersionSt,charge);

            idVersionSt=rs.getInt("ST7");
            charge=rs.getFloat("charge7");
            this.addCharges(idVersionSt,charge);

            idVersionSt=rs.getInt("ST8");
            charge=rs.getFloat("charge8");
            this.addCharges(idVersionSt,charge);

            idVersionSt=rs.getInt("ST9");
            charge=rs.getFloat("charge9");
            this.addCharges(idVersionSt,charge);

            idVersionSt=rs.getInt("ST10");
            charge=rs.getFloat("charge10");
            this.addCharges(idVersionSt,charge);

            this.nomService=rs.getString("nomService");
            this.Forfait = rs.getFloat("Forfait");
           }

           catch (SQLException s) {
             s.getMessage();
           }

}

  public void dump(){
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("idService="+this.idService);
    System.out.println("idPO="+this.idPO);
    System.out.println("Annee="+this.Annee);
    System.out.println("LF="+this.LF);
    for (int i=0; i < this.ListeCharges.size();i++)
    {
      Charge theCharge = (Charge)this.ListeCharges.elementAt(i);
      //theCharge.dump();
    }
    System.out.println("description="+this.description);
    System.out.println("Forfait="+this.Forfait);
    System.out.println("==================================================");
  }

  public static void main(String[] args) {
    Chiffrage chiffrage = new Chiffrage("",2012, 5);
  }
}
