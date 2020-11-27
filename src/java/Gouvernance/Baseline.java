/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gouvernance;
import Composant.item;
import java.sql.*;
import java.util.*;
import accesbase.*;
import java.util.Date;
import Organisation.Collaborateur;
import Projet.Roadmap;

import accesbase.ErrorSpecific;
import org.apache.poi.ss.usermodel.Row;
import General.Utils;
import Projet.Charge;
import Projet.Jalon;
import Projet.typeJalon;
/**
 *
 * @author Joël
 */
public class Baseline extends item {
    
    public java.util.Date Ddate = null; 
    public String date = "";
    public int idRoadmap = -1;
    public int idPere = -1;
    
    public java.util.Date DdateT0 = null;
    public java.util.Date DdateEB = null;
    public java.util.Date DdateTest = null;
    public java.util.Date DdateProd = null;
    public java.util.Date DdateMes = null;
  
    public String dateT0 = null;
    public String dateEB = null;
    public String dateTest = null;
    public String dateProd = null;
    public String dateMes = null;  
    
    public String Login = null;
    
    public Vector ListeCharges = new Vector(10);
    public Vector ListeBaselines = new Vector(10);
    public static Vector ListeBaseline_static = new Vector(10);
    public Vector ListeJalons = new Vector(10);
    
    public float totalCharge = 0;
    
    private ResultSet rs=null;
    private String req="";
    
    public Baseline(int id, int idRoadmap){
        this.id = id;
        this.idRoadmap = idRoadmap;
        this.Ddate = new Date();
        this.date = this.Ddate.getDate()+"/"+(this.Ddate.getMonth() +1)+"/"+(this.Ddate.getYear() + 1900);
        
    }
    
    public Baseline(int idRoadmap){
        this.id = -1;
        this.idRoadmap = idRoadmap;
        
    }    
    
public Jalon getJalonsByType(String nomBase,Connexion myCnx, Statement st, int type){
  ResultSet rs;
    req="SELECT    id, date FROM  JalonsBaseline WHERE type = " + type + " AND  idBaseline  = " + this.id;

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  
  Jalon theJalon = null;

  try {
  while(rs.next())
  {
     theJalon = new Jalon();
     theJalon.id = rs.getInt("id");
     theJalon.date = rs.getDate("date");
        if (theJalon.date != null)
          theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
         else
          theJalon.strDate = "";       
   }

  }
   catch (SQLException s) { }
    return theJalon; 
}    
    
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
      req = "SELECT     idRoadmap, nom, description, date, dateT0, dateEB, dateTest, dateMep, dateMes, Login, idPere";
      req+=" FROM         Baselines";
      req+=" WHERE     id = " + this.id;
      
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      
    try {
      while (rs.next()) {

        this.idRoadmap = rs.getInt("idRoadmap");
        this.nom = rs.getString("nom");
        this.desc = rs.getString("description");

        this.Ddate = rs.getDate("date");
        this.date=Utils.getDateFrench(this.Ddate);
        
        this.DdateT0 = rs.getDate("dateT0");
        this.dateT0=Utils.getDateFrench(this.DdateT0);
        
        this.DdateEB = rs.getDate("dateEB");
        this.dateEB=Utils.getDateFrench(this.DdateEB);     
        
        this.DdateTest = rs.getDate("dateTest");
        this.dateTest=Utils.getDateFrench(this.DdateTest);         
        
        this.DdateProd = rs.getDate("dateMep");
        this.dateProd=Utils.getDateFrench(this.DdateProd);         
                
        this.DdateMes = rs.getDate("dateMes");
        this.dateMes=Utils.getDateFrench(this.DdateMes); 
        
        this.Login = rs.getString("Login");
        this.idPere = rs.getInt("idPere");

      }
    }

        catch (SQLException s) {
          s.getMessage();
        }      
  }
  
  public static void getListeProjetsMigration(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    Roadmap theRoadmap= new Roadmap(-1);
    theRoadmap.getListeTypeJalons(nomBase,myCnx,  st);
    
    typeJalon theTypeJalonT0 =(typeJalon) theRoadmap.ListeTypeJalonsEssentiels.elementAt(1);
    typeJalon theTypeJalonEB =(typeJalon) theRoadmap.ListeTypeJalonsEssentiels.elementAt(2);
    typeJalon theTypeJalonTEST =(typeJalon) theRoadmap.ListeTypeJalonsEssentiels.elementAt(3);
    typeJalon theTypeJalonMEP =(typeJalon) theRoadmap.ListeTypeJalonsEssentiels.elementAt(4);;
    typeJalon theTypeJalonMES =(typeJalon) theRoadmap.ListeTypeJalonsEssentiels.elementAt(5);
     
    
    String req = "SELECT     id, dateT0, dateEB, dateTest, dateMep, dateMes FROM  Baselines";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        Baseline theBaseline = new Baseline(rs.getInt("id"), -1);
        theBaseline.DdateT0 = rs.getDate("dateT0");       
        theBaseline.DdateEB = rs.getDate("dateEB");       
        theBaseline.DdateTest = rs.getDate("dateTest");
        theBaseline.DdateProd = rs.getDate("dateMep");        
        theBaseline.DdateMes = rs.getDate("dateMes");
        
        if (theBaseline.DdateT0!= null)
        {
            Jalon theJalon = new Jalon();
            theJalon.idRoadmap = theBaseline.id;
            theJalon.type =  theTypeJalonT0.id;
            theJalon.nom = theTypeJalonT0.nom;
            theJalon.alias = theTypeJalonT0.alias;
            theJalon.isEssentiel = 1;
            theJalon.isStLie = 0;
            theJalon.date = theBaseline.DdateT0;
            theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
            theBaseline.ListeJalons.addElement(theJalon);
        }
        
        if (theBaseline.DdateEB!= null)
        {
            Jalon theJalon = new Jalon();
            theJalon.idRoadmap = theBaseline.id;
            theJalon.type =  theTypeJalonEB.id;
            theJalon.nom = theTypeJalonEB.nom;
            theJalon.alias = theTypeJalonEB.alias;
            theJalon.isEssentiel = 1;
            theJalon.isStLie = 0;
            theJalon.date = theBaseline.DdateEB;
            theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
            theBaseline.ListeJalons.addElement(theJalon);
        }
        
        if (theBaseline.DdateTest!= null)
        {
            Jalon theJalon = new Jalon();
            theJalon.idRoadmap = theBaseline.id;
            theJalon.type =  theTypeJalonTEST.id;
            theJalon.nom = theTypeJalonTEST.nom;
            theJalon.alias = theTypeJalonTEST.alias;
            theJalon.isEssentiel = 1;
            theJalon.isStLie = 0;
            theJalon.date = theBaseline.DdateTest;
            theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
            theBaseline.ListeJalons.addElement(theJalon);
        }
        
        if (theBaseline.DdateProd!= null)
        {
            Jalon theJalon = new Jalon();
            theJalon.idRoadmap = theBaseline.id;
            theJalon.type =  theTypeJalonMEP.id;
            theJalon.nom = theTypeJalonMEP.nom;
            theJalon.alias = theTypeJalonMEP.alias;
            theJalon.isEssentiel = 1;
            theJalon.isStLie = 1;
            theJalon.date = theBaseline.DdateProd;
            theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
            theBaseline.ListeJalons.addElement(theJalon);
        }
        
        if (theBaseline.DdateMes!= null)
        {
            Jalon theJalon = new Jalon();
            theJalon.idRoadmap = theBaseline.id;
            theJalon.type =  theTypeJalonMES.id;
            theJalon.nom = theTypeJalonMES.nom;
            theJalon.alias = theTypeJalonMES.alias;
            theJalon.isEssentiel = 1;
            theJalon.isStLie = 0;
            theJalon.date = theBaseline.DdateMes; 
            theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
            theBaseline.ListeJalons.addElement(theJalon);
        }        
  
        ListeBaseline_static.addElement(theBaseline);
      }
    }
            catch (SQLException s) {s.getMessage();}

  } 

 public ErrorSpecific bd_InsertJalons(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();

    req = "DELETE FROM JalonsBaseline WHERE idBaseline = "+this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertJalons",""+this.id);
    if (myError.cause == -1) return myError;

     for (int i=0; i < this.ListeJalons.size(); i++)
     {
         
        Jalon theJalon = (Jalon)this.ListeJalons.elementAt(i);
        String[] ligneDate = theJalon.strDate.split("/");
        String theDate = "convert(datetime, '"+ligneDate[0]+"/"+ligneDate[1]+"/"+ligneDate[2]+"', 103)";
    
    
    req = "INSERT INTO [JalonsBaseline]";
    req+= "           ([type]";
    req+= "            ,[date]";
    req+= "            ,[idBaseline]";
    req+=")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+theJalon.type+"'" + ",";
    req+= ""+theDate+"" + ",";
    req+= "'"+this.id+"'";
    req+= " )";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertJalons",""+this.id);
        if (myError.cause == -1) return myError;
     }
     return myError;
  }   
  
 public static void bd_deleteJalons(String nomBase,Connexion myCnx, Statement st){

    String req = "DELETE FROM JalonsBaseline";
    myCnx.ExecUpdate(st,nomBase,req,true);

  }  
 
 public void bd_InsertJalons(String nomBase,Connexion myCnx, Statement st){

    for (int i=0; i < this.ListeJalons.size(); i++)
     {
         
        Jalon theJalon = (Jalon)this.ListeJalons.elementAt(i);
        String[] ligneDate = theJalon.strDate.split("/");
        String theDate = "convert(datetime, '"+ligneDate[0]+"/"+ligneDate[1]+"/"+ligneDate[2]+"', 103)";
    
    
    req = "INSERT INTO [JalonsBaseline]";
    req+= "           ([type]";
    req+= "            ,[date]";
    req+= "            ,[idBaseline]";
    req+=")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+theJalon.type+"'" + ",";
    req+= ""+theDate+"" + ",";
    req+= "'"+this.id+"'";
    req+= " )";

     myCnx.ExecUpdate(st,nomBase,req,true);

     }

  }
 
  public void getAllFromBdLast(String nomBase,Connexion myCnx, Statement st ){
      req = "SELECT      TOP (1) id, idRoadmap, nom, description, date, dateT0, dateEB, dateTest, dateMep, dateMes";
      req+=" FROM         Baselines";
      req+=" WHERE     id = " + this.id;
      req+=" ORDER BY date DESC" ;
      
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      
    try {
      while (rs.next()) {
          
        this.id = rs.getInt("id");
        this.idRoadmap = rs.getInt("idRoadmap");
        this.nom = rs.getString("nom");
        this.desc = rs.getString("description");

        this.Ddate = rs.getDate("date");
        this.date=Utils.getDateFrench(this.Ddate);
        
        this.DdateT0 = rs.getDate("dateT0");
        this.dateT0=Utils.getDateFrench(this.DdateT0);
        
        this.DdateEB = rs.getDate("dateEB");
        this.dateEB=Utils.getDateFrench(this.DdateEB);     
        
        this.DdateTest = rs.getDate("dateTest");
        this.dateTest=Utils.getDateFrench(this.DdateTest);         
        
        this.DdateProd = rs.getDate("dateMep");
        this.dateProd=Utils.getDateFrench(this.DdateProd);         
                
        this.DdateMes = rs.getDate("dateMes");
        this.dateMes=Utils.getDateFrench(this.DdateMes); 
        
        this.Login = rs.getString("Login");

      }
    }

        catch (SQLException s) {
          s.getMessage();
        }      
  } 
  
  public void getListeCharges(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;

    req = "SELECT     projetMembre, semaine, Charge, anneeRef, etat, idBaseline";
    req+= " FROM         BaselinesPlanDeCharge";
    req+= " WHERE   idBaseline = " + this.id + " AND (Charge > 0)";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        Charge theCharge = new Charge();
        theCharge.idMembre = rs.getInt("projetMembre");
        theCharge.semaine = rs.getInt("semaine");
        theCharge.hj = rs.getFloat("Charge");
        theCharge.anneeRef = rs.getInt("anneeRef");
        theCharge.idEtat = rs.getInt("idEtat");
        theCharge.idObj = rs.getInt("idBaseline");  
        
        this.totalCharge += theCharge.hj;
               
        this.ListeCharges.addElement(theCharge);

      }
    }
    catch (Exception s) {
      s.getMessage();

    }

  } 
  
  public void getListeBaselines(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;

    req = "SELECT     idRoadmap,id, nom, description, date, dateT0, dateEB, dateTest, dateMep, dateMes, Login, idPere ";
    req += " FROM         Baselines";
    req += " WHERE     idPere =" + this.id;
    req += " order by id asc";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    
    int lastIdRoadmap = 0;
    
    try {
      while (rs.next()) {
          int idRoadmap = rs.getInt("idRoadmap");
          if (idRoadmap == lastIdRoadmap)
              continue;
          else
              lastIdRoadmap = idRoadmap;
          
        Baseline theBaseline = new Baseline(idRoadmap);
        theBaseline.id = rs.getInt("id");
        theBaseline.nom = rs.getString("nom");
        theBaseline.desc = rs.getString("description");

        theBaseline.Ddate = rs.getDate("date");
        theBaseline.date=Utils.getDateFrench(theBaseline.Ddate);
        
        theBaseline.DdateT0 = rs.getDate("dateT0");
        theBaseline.dateT0=Utils.getDateFrench(theBaseline.DdateT0);
        
        theBaseline.DdateEB = rs.getDate("dateEB");
        theBaseline.dateEB=Utils.getDateFrench(theBaseline.DdateEB);     
        
        theBaseline.DdateTest = rs.getDate("dateTest");
        theBaseline.dateTest=Utils.getDateFrench(theBaseline.DdateTest);         
        
        theBaseline.DdateProd = rs.getDate("dateMep");
        theBaseline.dateProd=Utils.getDateFrench(theBaseline.DdateProd);         
                
        theBaseline.DdateMes = rs.getDate("dateMes");
        theBaseline.dateMes=Utils.getDateFrench(theBaseline.DdateMes); 
        
        theBaseline.Login = rs.getString("Login");
        
        theBaseline.idPere = rs.getInt("idPere");

               
        this.ListeBaselines.addElement(theBaseline);

      }
    }
    catch (Exception s) {
      s.getMessage();

    }

  }   
  
  public float getTotalChargesProjets(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;
   if (this.id == -1) return 0; 
  if (this.ListeBaselines.size() == 0)
  {
    req="SELECT     SUM(Charge) AS total";
    req+="   FROM         BaselinesPlanDeCharge";
    req+=" WHERE     (idBaseline = "+this.id+")";
  }
  else
  {
    req="SELECT     SUM(BaselinesPlanDeCharge.Charge) AS total";
    req+=" FROM         BaselinesPlanDeCharge INNER JOIN";
    req+="                       Baselines ON BaselinesPlanDeCharge.idBaseline = Baselines.id";
    req+=" WHERE     (Baselines.idPere = "+this.id+")";
  }
  rs = myCnx.ExecReq(st, nomBase, req);

  float total=0;
  try {
      while (rs.next()) {
                   total = rs.getFloat(1);

               }

        }
                         catch (SQLException s) {s.getMessage();}

  return total;

}  
  
  public float getTotalChargesGlobal(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;
   if (this.id == -1) return 0;
  req="SELECT     SUM(BaselinesPlanDeCharge.Charge) AS total";
  req+=" FROM         BaselinesPlanDeCharge INNER JOIN";
  req+="                      Baselines ON BaselinesPlanDeCharge.idBaseline = Baselines.id";
  req+=" WHERE     (Baselines.idRoadmap IN";
  req+="                          (SELECT     idRoadmap";
  req+="                            FROM          Roadmap";
  req+="                            WHERE      (idRoadmapPere = "+this.idRoadmap+")))";
  req+=" GROUP BY Baselines.nom";
  req+=" HAVING      (Baselines.nom = '"+this.nom+"')";

  rs = myCnx.ExecReq(st, nomBase, req);

  float total=0;
  try {
      while (rs.next()) {
                   total = rs.getFloat(1);

               }

        }
                         catch (SQLException s) {s.getMessage();}

  return total;

}  
  
  public float getTotalChargesByIdMembre(String nomBase,Connexion myCnx, Statement st , int idMembre){
  ResultSet rs;
  if (this.id == -1) return 0;
  req="SELECT     SUM( Charge) as nb ";
  req+=" FROM         BaselinesPlanDeCharge";
  req+=" WHERE     (projetMembre = "+idMembre+") AND (idBaseline  = "+this.id+")";

  rs = myCnx.ExecReq(st, nomBase, req);

  float total=0;
  try {
      while (rs.next()) {
                   total = rs.getFloat(1);

               }

        }
                         catch (SQLException s) {s.getMessage();}

  return total;

}  
  
  public float getTotalChargesByIdMembreGlobal(String nomBase,Connexion myCnx, Statement st , int idMembre){
  ResultSet rs=null;
  if (this.id == -1) return 0;
  req="SELECT     SUM(BaselinesPlanDeCharge.Charge) AS total ";
  req+=" FROM         BaselinesPlanDeCharge ";
  req+=" INNER JOIN                      Baselines ON BaselinesPlanDeCharge.idBaseline = Baselines.id ";
  req+=" WHERE     (Baselines.idRoadmap IN                          (";
  req+=" SELECT     idRoadmap                            FROM          Roadmap                            WHERE      (idRoadmapPere = "+this.idRoadmap+"))) ";
  req+=" AND BaselinesPlanDeCharge.projetMembre = "+idMembre;

  req+=" GROUP BY Baselines.nom";
  req+=" HAVING      (Baselines.nom = '"+this.nom+"')";

  rs = myCnx.ExecReq(st, nomBase, req);
  
  float total=0;
  try {
      while (rs.next()) {
                   total = rs.getFloat(1);

               }

        }
                         catch (SQLException s) {s.getMessage();}

  return total;

}  
  
  public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();

    req = " DELETE FROM  BaselinesPlanDeCharge";
    req+= " WHERE     (idBaseline  = '"+this.id+"') ";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;   
    
    req = " DELETE FROM  Baselines";
    req+= " WHERE     (id  = '"+this.id+"') ";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;  
    
   
    
    return myError;
  }
  
  public float getTotalChargesByIdMembre(String nomBase,Connexion myCnx, Statement st , int idMembre, int anneeRef){
  ResultSet rs;
  req="SELECT     SUM( Charge) as nb ";
  req+=" FROM         BaselinesPlanDeCharge";
  req+=" WHERE     (projetMembre = "+idMembre+") AND (idRoadmap = "+this.id+") AND (anneeRef = "+anneeRef+")";

  rs = myCnx.ExecReq(st, nomBase, req);

  float total=0;
  try {
      while (rs.next()) {
                   total = rs.getFloat(1);

               }

        }
                         catch (SQLException s) {s.getMessage();}

  return total;

} 
  
  public ErrorSpecific bd_Insert2(String nomBase,Connexion myCnx, Statement st, Statement st2, String transaction){
    ErrorSpecific myError = new ErrorSpecific();

    String str_date ="convert(datetime, '"+this.Ddate.getDate()+"/"+(this.Ddate.getMonth() +1)+"/"+(this.Ddate.getYear()+1900)+"', 103)";
    
    String str_dateT0 ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((dateT0 != null) &&(!dateT0.equals("")))
    {
     Utils.getDate(dateT0);
     str_dateT0 ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }
    
    String str_dateEB ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((dateEB != null) &&(!dateEB.equals("")))
    {
     Utils.getDate(dateEB);
     str_dateEB ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateTest ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";

    
    if ((dateTest != null) &&(!dateTest.equals("")))
    {
      Utils.getDate(dateTest);
      str_dateTest ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateProd ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((dateProd != null) &&(!dateProd.equals("")))
    {
      Utils.getDate(dateProd);
      str_dateProd ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

     String str_dateMes="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((dateMes != null) &&(!dateMes.equals("")))
    {
      Utils.getDate(dateMes);
      str_dateMes ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }  
    
    //if (this.nom == null || this.nom.equals("")) this.nom = this.date.substring(0,10);
    if (this.nom == null || this.nom.equals("")) 
    {
        Date myDate = new Date();
        //this.nom = this.date;
        this.nom = myDate.getDate() + "/" + (myDate.getMonth() +1) + "/" + (myDate.getYear() + 1900)+ " "+myDate.getHours()+ ":"+myDate.getMinutes()+ ":"+myDate.getSeconds();
    }
    
      req = "INSERT Baselines ( idRoadmap, nom, description, date, dateT0, dateEB, dateTest, dateMep, dateMes, Login, idPere) ";
      req+=" VALUES ('"+this.idRoadmap+"',  '"+this.nom+"',  '"+this.desc+"', "+str_date+","+str_dateT0+","+str_dateEB+","+str_dateTest+", "+str_dateProd+", "+str_dateMes+",  '"+this.Login+"',  "+this.idPere+")";

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
      if (myError.cause == -1) return myError;  
      
      req="SELECT     TOP (1) id FROM   Baselines ORDER BY id DESC";
      rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();}
           
      req = "SELECT     projetMembre, semaine, Charge, anneeRef, idRoadmap, etat";
      req+=" FROM         PlanDeCharge";
      req+=" WHERE     idRoadmap = " + this.idRoadmap;
      req+=" AND     Charge > 0 ";
      
      rs = myCnx.ExecReq(st, nomBase, req);
      
    try {
      while (rs.next()) {
        int projetMembre = rs.getInt("projetMembre");
        int semaine = rs.getInt("semaine");
        float Charge = rs.getFloat("Charge");
        int anneeRef = rs.getInt("anneeRef");
        int idRoadmap = rs.getInt("idRoadmap");
        int etat = rs.getInt("etat");

        req = "INSERT BaselinesPlanDeCharge (  projetMembre, semaine, Charge, anneeRef, etat, idBaseline) ";
        req+=" VALUES ('"+projetMembre+"',  '"+semaine+"',  '"+Charge+"',  '"+anneeRef+"',  '"+etat+"',  "+this.id+")";
        myError = myCnx.ExecUpdateTransaction(st2,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
        if (myError.cause == -1) return myError;         

      }
    }
            catch (SQLException s) {s.getMessage();}      
    
    return myError;
  }  
  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, Statement st2, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    String str_date ="convert(datetime, '"+this.Ddate.getDate()+"/"+(this.Ddate.getMonth() +1)+"/"+(this.Ddate.getYear()+1900)+"', 103)";

    if (this.nom == null || this.nom.equals("")) 
    {
        Date myDate = new Date();
        this.nom = myDate.getDate() + "/" + (myDate.getMonth() +1) + "/" + (myDate.getYear() + 1900)+ " "+myDate.getHours()+ ":"+myDate.getMinutes()+ ":"+myDate.getSeconds();
    }
    
      req = "INSERT Baselines ( idRoadmap, nom, description, date , Login, idPere) ";
      req+=" VALUES ('"+this.idRoadmap+"',  '"+this.nom+"',  '"+this.desc+"', "+str_date+ ",  '"+this.Login+"',  "+this.idPere+")";

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
      if (myError.cause == -1) return myError;  
            
      req="SELECT     TOP (1) id FROM   Baselines ORDER BY id DESC";
      rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();}
      
      myError = bd_InsertJalons( nomBase, myCnx,  st,  transaction);
      if (myError.cause == -1) return myError;      
           
      req = "SELECT     projetMembre, semaine, Charge, anneeRef, idRoadmap, etat";
      req+=" FROM         PlanDeCharge";
      req+=" WHERE     idRoadmap = " + this.idRoadmap;
      req+=" AND     Charge > 0 ";
      
      rs = myCnx.ExecReq(st, nomBase, req);
      
    try {
      while (rs.next()) {
        int projetMembre = rs.getInt("projetMembre");
        int semaine = rs.getInt("semaine");
        float Charge = rs.getFloat("Charge");
        int anneeRef = rs.getInt("anneeRef");
        int idRoadmap = rs.getInt("idRoadmap");
        int etat = rs.getInt("etat");

        req = "INSERT BaselinesPlanDeCharge (  projetMembre, semaine, Charge, anneeRef, etat, idBaseline) ";
        req+=" VALUES ('"+projetMembre+"',  '"+semaine+"',  '"+Charge+"',  '"+anneeRef+"',  '"+etat+"',  "+this.id+")";
        myError = myCnx.ExecUpdateTransaction(st2,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
        if (myError.cause == -1) return myError;         

      }
    }
            catch (SQLException s) {s.getMessage();}      
    
    return myError;
  }  
 
  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
    String description="";

    if (this.desc != null)
    {
      for (int j=0; j < this.desc.length();j++)
      {
        int c = (int)this.desc.charAt(j);
        if (c != 34)
          {
            description += this.desc.charAt(j);
          }
          else
          {
            description +="'";
          }
      }
      description = description.replaceAll("\r","").replaceAll("'","''");
     }

    req = "UPDATE [Baselines]";
    req+= "   SET [nom] = "+ "'"+this.nom+ "'" + ",";
    req+= "      [description] = "+ "'"+description+ "'" ;
    req+= "  WHERE id = "+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }    
    
}
