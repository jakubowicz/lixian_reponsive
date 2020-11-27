package Anomalies; 
import java.util.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import accesbase.Connexion;
import java.sql.SQLException;

import ST.ST;
import java.util.Vector;
import Statistiques.LigneStat;
import accesbase.ErrorSpecific;
import java.util.Date;

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
public class Anomalie {
  public int id=-1;
  public String objet="";
  public String description="";
  public int versionStAnomalie = 0;
  public String DateCreation="";
  public Date DDateCreation;
  public String DateReponse="";
  public Date DDateReponse;
  public int idTypeEtat=-1;
  public int nextIdEtat = 1;
  public String nomEtat="";
  public String Reponse="";
  public String Criticite="";
  public int idTypeCriticite=-1;
  public int idTypeCriticiteEtat=-1;
  public String Cout="";
  public String LoginCreateur="";
  public String LoginReponse="";
  public String MailCreateur="";

  public ST anoST=null;

  private String req = "";
  public String chaine_a_ecrire="";

  public static Vector ListeTypeCriticite = new Vector(10);
  public Vector ListeExcel = new Vector(10);
  public static Vector ListeANO = new Vector(10);

  public String lienImage="";


  public Anomalie(int id) {
    this.id = id;
    this.versionStAnomalie = 0;
    this.objet = "";
    this.idTypeCriticite = -1;
    this.description = "";
    this.Cout = "";
    this.idTypeEtat = -1;
  }

  public Anomalie(int versionStAnomalie,String objet, int idTypeCriticite, String description, int idTypeEtat ) {
    this.versionStAnomalie = versionStAnomalie;
    this.objet = objet;
    this.idTypeCriticite = idTypeCriticite;
    this.description = description;
    this.Cout = "";
    this.idTypeEtat = idTypeEtat;
  }

  public void setIdCriticite(){
    try{
      this.idTypeCriticiteEtat = (this.idTypeCriticite - 1) * 4 + this.nextIdEtat;
    }
    catch (Exception e) {this.idTypeCriticite = this.idTypeEtat;};
  }

  public static void getListeANO(String nomBase,Connexion myCnx, Statement st){

    String req="EXEC LISTEOM_SelectObjetMetier";
    ListeANO.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Anomalie theAnomalie = null;
    try {
       while (rs.next()) {
         theAnomalie = new Anomalie(rs.getInt(1));
         theAnomalie.objet=rs.getString(2);
         //theST.dump();
         ListeANO.addElement(theAnomalie);
       }
        } catch (SQLException s) {s.getMessage();}

 }
  public ErrorSpecific bd_Enreg(String action, String typeForm, String nomBase,Connexion myCnx, Statement st, String transaction){
ErrorSpecific myError = new ErrorSpecific();
    if (this.objet != null)
      this.objet =  this.objet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","").replaceAll("\"","");

    if (this.description != null)
      this.description =  this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");

    if (this.Reponse != null)
      this.Reponse =  this.Reponse.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");

    if (this.objet != null)
    this.objet=this.objet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");

    String result="";
    if (typeForm.equals("CreationAno")) { // Cr�ation d'un nouveau ST
      myError=this.bd_Insert( nomBase, myCnx,  st,  transaction);
    }
    else if (typeForm.equals("Suppression"))
    {
       myError=this.bd_Delete(nomBase,myCnx,st,transaction);
    }
    else if (typeForm.equals("ModificationAno"))
    {
       myError=this.bd_Update( nomBase, myCnx,  st,  transaction);
    }
    else if (typeForm.equals("ModifEtat")) //on traite une modification de l'�ta de l'anomalie
    {
      myError=this.bd_ChangeState( nomBase, myCnx,  st,  transaction);
    }
    else
    {
      myCnx.trace("****************"," Etat inconnu: ",typeForm);
    }

    return myError;
  }

  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();      
    if (this.objet != null)
      this.objet =  this.objet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","").replaceAll("\"","");

    if (this.description != null)
      this.description =  this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");

    if (this.Reponse != null)
      this.Reponse =  this.Reponse.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");

    if (this.objet != null)
    this.objet=this.objet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");     
 
   
    

    if (this.versionStAnomalie!=0)
    {
      req = "INSERT Anomalie (objetAnomalie,createurAnomalie,   descAnomalie, versionStAnomalie,IdcriticiteAnomalie, lienImage) VALUES('"+this.objet+"', '"+this.LoginCreateur+"',  '"+this.description+"', '"+this.versionStAnomalie+"',"+this.idTypeCriticiteEtat+",'"+this.lienImage+"')";
      }
    else
    {
       req = "INSERT Anomalie (objetAnomalie,createurAnomalie,   descAnomalie,IdcriticiteAnomalie, lienImage) VALUES('"+this.objet+"', '"+this.LoginCreateur+"',  '"+this.description+"',"+this.idTypeCriticiteEtat+",'"+this.lienImage+"')";
    }

           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
           if (myError.cause == -1) return myError;

      req="SELECT     TOP (1) idAnomalie FROM  Anomalie ORDER BY idAnomalie DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();}
      
     return myError;
  }

  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
 ErrorSpecific myError = new ErrorSpecific();      
    if (this.objet != null)
      this.objet =  this.objet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","").replaceAll("\"","");

    if (this.description != null)
      this.description =  this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");

    if (this.Reponse != null)
      this.Reponse =  this.Reponse.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");

    if (this.objet != null)
    this.objet=this.objet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");    
    
    if (this.Reponse != null) 
      this.Reponse =  this.Reponse.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");     

    req = "UPDATE Anomalie SET objetAnomalie='"+this.objet+"' WHERE idAnomalie="+this.id;
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
           if (myError.cause == -1) return myError;

    req = "UPDATE Anomalie SET descAnomalie='"+this.description+"' WHERE idAnomalie="+this.id;
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
           if (myError.cause == -1) return myError;

    req = "UPDATE Anomalie SET criticiteAnomalie='"+this.Criticite+"' WHERE idAnomalie="+this.id;
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
           if (myError.cause == -1) return myError;

    req = "UPDATE Anomalie SET IdcriticiteAnomalie ='"+this.idTypeCriticiteEtat+"' WHERE idAnomalie="+this.id;
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
           if (myError.cause == -1) return myError;
           
    if ((this.idTypeEtat == 2) && (this.nextIdEtat == 3))
    {
        req = "UPDATE Anomalie SET loginReponse ='"+this.LoginReponse+"' WHERE idAnomalie="+this.id;
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
           if (myError.cause == -1) return myError;   
           
            
        req = "UPDATE Anomalie SET dateReponse =GETDATE() WHERE idAnomalie="+this.id;
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
           if (myError.cause == -1) return myError;             
    }

    if (this.versionStAnomalie == 0)
      req = "UPDATE Anomalie SET versionStAnomalie=NULL WHERE idAnomalie="+this.id;
    else
      req = "UPDATE Anomalie SET versionStAnomalie="+this.versionStAnomalie+" WHERE idAnomalie="+this.id;
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
           if (myError.cause == -1) return myError;

    req = "UPDATE Anomalie SET Cout='"+this.Cout+"' WHERE idAnomalie="+this.id;
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
           if (myError.cause == -1) return myError;
    
    req = "UPDATE Anomalie SET RepAnomalie = '"+this.Reponse+"' WHERE idAnomalie = "+this.id;
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
           if (myError.cause == -1) return myError;

    req = "UPDATE Anomalie SET lienImage='"+this.lienImage+"' WHERE idAnomalie="+this.id;
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
           if (myError.cause == -1) return myError;

     return myError;
  }

  public ErrorSpecific bd_ChangeState(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    //this.getAllFromBd( nomBase, myCnx,  st);

    req = "UPDATE Anomalie SET IdcriticiteAnomalie="+this.idTypeCriticiteEtat+" WHERE idAnomalie="+this.id;
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
           if (myError.cause == -1) return myError;


    if (this.nextIdEtat==0) {
            req = "DELETE Anomalie WHERE idAnomalie="+this.id;}
    else {
            req = "UPDATE Anomalie SET typeEtatAnomalie = "+this.nextIdEtat+" WHERE idAnomalie = "+this.id;
            req = "UPDATE Anomalie SET RepAnomalie = '"+this.Reponse+"' WHERE idAnomalie = "+this.id;
         }
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
           if (myError.cause == -1) return myError;

   return myError;
}


public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
  ResultSet rs = null;
    ErrorSpecific myError = new ErrorSpecific();
  
    req = "DELETE Anomalie WHERE idAnomalie="+this.id;
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
           if (myError.cause == -1) return myError;

 return myError;
}

  public String getListeDiffusion(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
  String ListeDiffusion = "";

  req = "SELECT mail FROM  Membre WHERE  (isAdmin = 1)";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  int nb = 0;
  try {
  while (rs.next()) {
    if (nb > 0) ListeDiffusion += ";";
    ListeDiffusion += rs.getString("mail");
    nb++;

  }
}
  catch (SQLException s) {s.getMessage();}

  return ListeDiffusion;
  }
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs=null;

    req =" exec ANO_SelectUneAno "+this.id;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String stAnomalie="";
    try {rs.next();
            this.objet = rs.getString("objetAnomalie");
            this.Criticite = rs.getString("nomTypeCriticite");
            
            this.description = rs.getString("descAnomalie");
            if (this.description == null
                    ) this.description = "";
             
            this.versionStAnomalie = rs.getInt("versionStAnomalie");
            this.DateCreation = rs.getString("Day")+"/"+rs.getString("Month")+"/"+rs.getString("Year");
            
            this.Reponse = rs.getString("RepAnomalie");
            if (this.Reponse == null) this.Reponse = "";
            
            this.nomEtat=rs.getString("nomTypeEtat");
            this.Cout=rs.getString("Cout");
            this.LoginCreateur=rs.getString("createurAnomalie");
            this.idTypeCriticite=rs.getInt("idTypeCriticite");
            this.idTypeEtat=rs.getInt("idTypeEtat");
            this.idTypeCriticiteEtat=rs.getInt("idTypeCriticiteEtat");
            this.lienImage=rs.getString("lienImage");
            this.LoginReponse=rs.getString("loginReponse");
            this.DDateReponse = rs.getDate("dateReponse");
            if (this.DDateReponse != null)
            {
                this.DateReponse= this.DDateReponse.getDate()+"/"+(this.DDateReponse.getMonth() +1)+"/"+(this.DDateReponse.getYear()+1900);
            }
    } catch (SQLException s) {s.getMessage();}

    if ((this.Cout==null) ||(this.Cout.equals(""))||(this.Cout.equals("null)"))) this.Cout = "";
    if (this.Reponse == null)
    {
            if (this.idTypeEtat == 2) this.Reponse = "Prise en compte sans reponse specifique";
            if (this.idTypeEtat == 3) this.Reponse = "Traitee sans reponse specifique";
            if (this.idTypeEtat == 4) this.Reponse = "Rejetee sans reponse specifique";
}

    this.MailCreateur = this.LoginCreateur+"@" + myCnx.mail;

    if (this.versionStAnomalie !=0)
     {
             rs = myCnx.ExecReq(st, nomBase, "EXEC ST_SelectSonSt "+this.versionStAnomalie);
             try {
               rs.next();
               this.anoST = new ST(this.versionStAnomalie,"idVersionSt");
               this.anoST.nomSt = rs.getString(2);
             } catch (SQLException s) {s.getMessage(); }
      }
  }

  public static int nbByState (String nomBase,Connexion myCnx, Statement st,String Etat){
    ResultSet rs=null;
    int nb = 0;

    String req = "SELECT     COUNT(*) AS nbANO";
    req += " FROM         Criticite INNER JOIN";
    req += "                       Anomalie ON Criticite.idCriticite = Anomalie.IdcriticiteAnomalie INNER JOIN";
    req += "                       TypeEtat ON Criticite.EtatCriticite = TypeEtat.idTypeEtat";
    req += " WHERE     (TypeEtat.nomTypeEtat = '"+Etat+"')";
    rs = myCnx.ExecReq(st, nomBase, req);
     try { rs.next();
            nb = rs.getInt(1);
 } catch (SQLException s) {s.getMessage();}

    return nb;
  }

  public static int nbByIdState (String nomBase,Connexion myCnx, Statement st,int idEtat){
    ResultSet rs=null;
    int nb = 0;

    String req = "SELECT     COUNT(*) AS nbANO";
    req += "     FROM         Criticite INNER JOIN";
    req += "                   Anomalie ON Criticite.idCriticite = Anomalie.IdcriticiteAnomalie";
    req += " GROUP BY Criticite.EtatCriticite";
    req += " HAVING      (Criticite.EtatCriticite = "+idEtat+")";

    rs = myCnx.ExecReq(st, nomBase, req);
     try { rs.next();
            nb = rs.getInt(1);
 } catch (SQLException s) {s.getMessage();}

    return nb;
  }

  public static void getCriticite(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs=null;

    ListeTypeCriticite.removeAllElements();
    String req = "SELECT  nomTypeCriticite, idTypeCriticite FROM  TypeCriticite ORDER BY Priorite";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { while(rs.next()) {
        String nomTypeCriticite = rs.getString("nomTypeCriticite");
        int idTypeCriticite = rs.getInt("idTypeCriticite");

        TypeCriticite theTypeCriticite = new TypeCriticite (idTypeCriticite, nomTypeCriticite);
        ListeTypeCriticite.addElement(theTypeCriticite);
      }	} catch (SQLException s) {s.getMessage();}
  }

  public  void getListeExcel(String nomBase,Connexion myCnx, Statement st ){

    String req = "SELECT     Anomalie.objetAnomalie, Criticite.nomCriticite, Anomalie.createurAnomalie AS nomMembre, Anomalie.descAnomalie, TypeEtat.nomTypeEtat, Anomalie.Cout, Anomalie.RepAnomalie, Anomalie.creationAnomalie, Anomalie.dateReponse";
    req+=" FROM         Anomalie INNER JOIN";
    req+="                       Criticite ON Anomalie.IdcriticiteAnomalie = Criticite.idCriticite AND Anomalie.IdcriticiteAnomalie = Criticite.idCriticite INNER JOIN";
    req+="                       TypeEtat ON Criticite.EtatCriticite = TypeEtat.idTypeEtat LEFT OUTER JOIN";
    req+="                       Membre ON Anomalie.AnoIdMembre = Membre.idMembre";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try { while(rs.next()) {
        String objetAnomalie = rs.getString(1);
        myCnx.trace("","objetAnomalie",""+objetAnomalie);
        String criticiteAnomalie= rs.getString(2);
        String nomMembre= rs.getString(3);
        String desc2TypeEtat= rs.getString(4).replaceAll("\\r","").replaceAll("\\n","");
        String Etat= rs.getString(5);
        String Cout= rs.getString(6);
        if ((Cout==null) ||(Cout.equals(""))||(Cout.equals("null)"))) Cout = "";

        LigneAnomalie theLigneAnomalie = new LigneAnomalie (objetAnomalie,criticiteAnomalie, nomMembre,desc2TypeEtat,Etat,Cout);
        theLigneAnomalie.reponse = rs.getString("RepAnomalie");
        if (theLigneAnomalie.reponse != null) 
            theLigneAnomalie.reponse = theLigneAnomalie.reponse.replaceAll("\\r","").replaceAll("\\n","");
        else
            theLigneAnomalie.reponse = "";
        
        theLigneAnomalie.creationAnomalie = rs.getString("creationAnomalie");
        theLigneAnomalie.dateReponse = rs.getString("dateReponse");
        
        this.ListeExcel.addElement(theLigneAnomalie);
        }	} catch (SQLException s) {s.getMessage();}
}

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("objet="+this.objet);
    System.out.println("Reponse="+this.Reponse);
    System.out.println("description="+this.description);
    System.out.println("versionStAnomalie="+this.versionStAnomalie);
    System.out.println("DateCreation="+this.DateCreation);
    System.out.println("Cout="+this.Cout);
    System.out.println("LoginCreateur="+this.LoginCreateur);
    System.out.println("versionStAnomalie="+this.versionStAnomalie);
    System.out.println("Login="+this.LoginCreateur);
    System.out.println("idTypeEtat="+this.idTypeEtat);
    System.out.println("nomEtat="+this.nomEtat);
    System.out.println("Criticite="+this.Criticite);
    System.out.println("idTypeCriticite="+this.idTypeCriticite);
    System.out.println("idTypeCriticiteEtat="+this.idTypeCriticiteEtat);
    System.out.println("lienImage="+this.lienImage);
    System.out.println("==================================================");
  }
}
