package Organisation; 

import java.util.Vector;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import accesbase.Connexion;
import Composant.item;
import Projet.typeJalon;
import accesbase.ErrorSpecific;

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
public class Profil extends item {

  public int idMembre = -1;
  public int Annee = 2016;
  public int Mois = 1;
  public int isJalon0 = 0;
  public int isJalon1 = 0;
  public int isJalon2 = 0;
  public int isJalon3 = 0;
  public int isJalon4 = 0;
  public int idClient = -1;
  public int idSt = -1;
  public int isDecalage = -1;
  public int isTaches = -1;
  public int nbPage = 14;
  
  public String couleurPeriode1 = "";
  public String couleurPeriode2 = "";
  public String couleurPeriode3 = "";
  public String couleurPeriode4 = "";
  
  public String ListeTags = "";
  public String ListeApplications = "";
  public String ListeProjets = "";
  
  
  public Vector ListeSt = new Vector(10);
  public Vector ListeRoadmap = new Vector(10);
  public String str_ListeCouleurPeriode = "";
  public Vector ListeCouleurPeriode = new Vector(10);
  
  public String str_ListePeriode = "";
  public Vector ListePeriode = new Vector(10);  
  
  public int idCp = -1;

  private String req;


  public Profil(int id) {
    this.id = id;
  }
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    req = "SELECT     idMembre, nom, description, Annee, Mois,  ";
    req+= " idClient, idSt, isDecalage, isTaches,  ";
    req+= " ListeTags, ListeApplications, ListeProjets, idCp, nbPage, ListeCouleurPeriode, ListePeriode";
    req+= " FROM         ProfilsProjet ";
    req+= " WHERE  id = " + this.id;
    req+= " ORDER BY nom ";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      this.idMembre = rs.getInt("idMembre");
      this.nom = rs.getString("nom");
      this.desc = rs.getString("description");
      this.Annee = rs.getInt("Annee");
      this.Mois = rs.getInt("Mois");
      
      
      this.idClient = rs.getInt("idClient");
      this.idSt = rs.getInt("idSt");
      this.isDecalage = rs.getInt("isDecalage");
      this.isTaches = rs.getInt("isTaches");
            
      this.ListeTags = rs.getString("ListeTags");
      this.ListeApplications = rs.getString("ListeApplications");
      this.ListeProjets = rs.getString("ListeProjets");
      
      this.idCp = rs.getInt("idCp");
      this.nbPage = rs.getInt("nbPage");
      
      this.str_ListeCouleurPeriode = rs.getString("ListeCouleurPeriode");
      if (this.str_ListeCouleurPeriode != null && !this.str_ListeCouleurPeriode.equals(""))
      {
        try{
                String LigneJalons[] = this.str_ListeCouleurPeriode.split("@");
                for (int i= 0; i < LigneJalons.length; i++)
                {
                   String onePeriod[]  = LigneJalons[i].split(";");
                   item theCouleurPeriode = new item();
                   theCouleurPeriode.id = Integer.parseInt(onePeriod[0]);
                   theCouleurPeriode.nom = onePeriod[1];                  
                   this.ListeCouleurPeriode.addElement(theCouleurPeriode);       
                }
            }
        catch (Exception e)
            {
            }          
      }
      
      this.str_ListePeriode = rs.getString("ListePeriode");
      if (this.str_ListePeriode != null && !this.str_ListePeriode.equals(""))
      {
        try{
                String onePeriod[] = this.str_ListePeriode.split(";");
                for (int i= 0; i < onePeriod.length; i++)
                {
                   item thePeriode = new item();
                   thePeriode.id = Integer.parseInt(onePeriod[i]);                
                   this.ListePeriode.addElement(thePeriode);       
                }
            }
        catch (Exception e)
            {
            }          
      }      
      

      } catch (SQLException s) {s.getMessage(); } 
  }
  
  public String getColorByJalon(int idJalon){
      String color = "";
      for (int i=0; i < this.ListeCouleurPeriode.size(); i++)
      {
          item theItem = (item)this.ListeCouleurPeriode.elementAt(i);
          if (theItem.id == idJalon) return theItem.nom;
      }
      return color;
  }
  
  public  item getCouleurPeriode(typeJalon myTypeJalon, Vector ListeTypeJalonsEssentiels){

    item theItem = new item(-1);
    theItem.nom = "000";
    
    for (int i=0; i < this.ListeCouleurPeriode.size(); i++)
    {
        theItem = (item)this.ListeCouleurPeriode.elementAt(i);
        if (theItem.id == myTypeJalon.id)
        {
           return theItem;
        }        
    }
    
    for (int i=0; i < ListeTypeJalonsEssentiels.size(); i++)
    {
        typeJalon theTypeJalon = (typeJalon)ListeTypeJalonsEssentiels.elementAt(i);
        // 1- si Appartient au Profil
        
        // 1- si Appartient à l'ergonomie du collaborateur
        
        // 3- sinon prendre la couleur standard du Jalon
        if (theTypeJalon.id == myTypeJalon.id)
        {
           theItem = new item() ;
           theItem.id = theTypeJalon.id;
           theItem.nom = theTypeJalon.couleur;
           return theItem;
        }
    }
    return theItem;

}   
  
  public String getPeriodeByJalon(String nomBase,Connexion myCnx, Statement st, int idJalon){
      ResultSet rs = null;
      String periode = "";
      for (int i=0; i < this.ListePeriode.size(); i++)
      {
          item theItem = (item)this.ListePeriode.elementAt(i);
          req="SELECT      alias FROM  typeJalon WHERE id =" +  idJalon;
          rs = myCnx.ExecReq(st, nomBase, req);
            try { rs.next();
                periode = rs.getString("alias");
                } catch (SQLException s) {s.getMessage();}    
      }
      return periode;
  } 
  
    public int isPeriodeSelected(int idJalon){
      for (int i=0; i < this.ListePeriode.size(); i++)
      {
          item theItem = (item)this.ListePeriode.elementAt(i);
          if (theItem.id == idJalon) return 1;
      }
      return 0;
  }
  
  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;

    String req = "DELETE FROM ProfilsProjet WHERE  id = " + this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  }  
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){

    ErrorSpecific myError = new ErrorSpecific();
    
    if (this.desc != null)
        this.desc=this.desc.replaceAll("\u0092","'").replaceAll("'","''");
 
    req = "UPDATE    ProfilsProjet  SET ";
    req +=" idMembre ='"+ this.idMembre + "'";
    req+=",";
    req +=" nom ='"+ this.nom + "'";
    req+=",";
    req +=" description ='"+ this.desc + "'";
    req+=",";
    req +=" Annee ='"+ this.Annee + "'";
    req+=",";
    req +=" Mois ='"+ this.Mois + "'";
    req+=",";
    req +=" isJalon0 ='"+ this.isJalon0 + "'";
    req+=",";
    req +=" isJalon1 ='"+ this.isJalon1 + "'";
    req+=",";
    req +=" isJalon2 ='"+ this.isJalon2 + "'";
    req+=",";    
    req +=" isJalon3 ='"+ this.isJalon3 + "'";
    req+=",";
    req +=" isJalon4 ='"+ this.isJalon4 + "'";
    req+=",";
    req +=" idClient ='"+ this.idClient + "'";
    req+=",";
    req +=" idSt ='"+ this.idSt + "'";
    req+=",";
    req +=" isDecalage ='"+ this.isDecalage + "'";
    req+=",";
    req +=" isTaches ='"+ this.isTaches + "'";
    req+=",";     
    req +=" couleurPeriode1 ='"+ this.couleurPeriode1 + "'";
    req+=",";     
    req +=" couleurPeriode2 ='"+ this.couleurPeriode2 + "'";
    req+=",";     
    req +=" couleurPeriode3 ='"+ this.couleurPeriode3 + "'";
    req+=",";     
    req +=" couleurPeriode4 ='"+ this.couleurPeriode4 + "'";
    req+=",";     
    req +=" ListeTags ='"+ this.ListeTags + "'";
    req+=",";         
    req +=" ListeApplications ='"+ this.ListeApplications + "'";
    req+=",";   
    req +=" ListeProjets ='"+ this.ListeProjets + "'";
    req+=",";  
    req +=" ListeCouleurPeriode ='"+ this.str_ListeCouleurPeriode + "'";
    req+=",";   
    req +=" ListePeriode ='"+ this.str_ListePeriode + "'";
    req+=",";     
    req +=" idCp ='"+ this.idCp + "'";
    req+=",";      
    req +=" nbPage ='"+ this.nbPage + "'";    
     
    req += " WHERE id ="+ this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }
  
  public ErrorSpecific bd_updateMigration(String nomBase,Connexion myCnx, Statement st, String transaction){

    ErrorSpecific myError = new ErrorSpecific();
    
    int typeAD = -1;
    int typeT0 = -1;
    int typeEB = -1;
    int typeTEST = -1;
    int typeMEP = -1;
    int typeVSR = -1;
   
    ResultSet rs;
    String req = "";

    req = "SELECT     id, nom FROM  typeJalon WHERE (isEssentiel = 1) ORDER BY ordre";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    int num=0;
    try {
      while (rs.next()) {
          if (num == 0)
            typeAD =  rs.getInt("id");          
          if (num == 1)
            typeT0 =  rs.getInt("id");
          else if (num == 2)
            typeEB =  rs.getInt("id"); 
          else if (num == 3)
            typeTEST =  rs.getInt("id"); 
          else if (num == 4)
            typeMEP =  rs.getInt("id"); 
          else if (num == 5)
            typeVSR =  rs.getInt("id");           
         num++;
      }}   catch (SQLException s) {s.getMessage();}      
    
    this.str_ListePeriode = "";
    if (this.isJalon0 == 1)
        this.str_ListePeriode += typeT0;
    if (this.isJalon1 == 1)
    {
        if (!this.str_ListePeriode.equals("")) this.str_ListePeriode += ";";
        this.str_ListePeriode += typeEB;   
    }
    if (this.isJalon2 == 1)
    {
        if (!this.str_ListePeriode.equals("")) this.str_ListePeriode += ";";
        this.str_ListePeriode += typeTEST;   
    }
    if (this.isJalon3 == 1)
    {
        if (!this.str_ListePeriode.equals("")) this.str_ListePeriode += ";";
        this.str_ListePeriode += typeMEP;   
    }
    if (this.isJalon4 == 1)
    {
        if (!this.str_ListePeriode.equals("")) this.str_ListePeriode += ";";
        this.str_ListePeriode += typeVSR;   
    }
    
    this.str_ListeCouleurPeriode = "";
    
    this.str_ListeCouleurPeriode += typeEB;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurPeriode1;
    
    this.str_ListeCouleurPeriode += "@"; 
    
    this.str_ListeCouleurPeriode += typeTEST;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurPeriode2;  
    
    this.str_ListeCouleurPeriode += "@"; 
    
    this.str_ListeCouleurPeriode += typeMEP;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurPeriode3;
    
    this.str_ListeCouleurPeriode += "@"; 
    
    this.str_ListeCouleurPeriode += typeVSR;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurPeriode4;
    
 
    req = "UPDATE    ProfilsProjet  SET ";
    req +=" ListeCouleurPeriode ='"+ this.str_ListeCouleurPeriode + "'";
    req+=",";   
    req +=" ListePeriode ='"+ this.str_ListePeriode + "'";  
     
    req += " WHERE id ="+ this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }  
  
  public void bd_updateMigration(String nomBase,Connexion myCnx, Statement st){
    
    int typeAD = -1;
    int typeT0 = -1;
    int typeEB = -1;
    int typeTEST = -1;
    int typeMEP = -1;
    int typeVSR = -1;
   
    ResultSet rs;
    String req = "";

    req = "SELECT     id, nom FROM  typeJalon WHERE (isEssentiel = 1) ORDER BY ordre";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    int num=0;
    try {
      while (rs.next()) {
          if (num == 0)
            typeAD =  rs.getInt("id");          
          if (num == 1)
            typeT0 =  rs.getInt("id");
          else if (num == 2)
            typeEB =  rs.getInt("id"); 
          else if (num == 3)
            typeTEST =  rs.getInt("id"); 
          else if (num == 4)
            typeMEP =  rs.getInt("id"); 
          else if (num == 5)
            typeVSR =  rs.getInt("id");           
         num++;
      }}   catch (SQLException s) {s.getMessage();}      
    
    this.str_ListePeriode = "";
    if (this.isJalon0 == 1)
        this.str_ListePeriode += typeT0;
    if (this.isJalon1 == 1)
    {
        if (!this.str_ListePeriode.equals("")) this.str_ListePeriode += ";";
        this.str_ListePeriode += typeEB;   
    }
    if (this.isJalon2 == 1)
    {
        if (!this.str_ListePeriode.equals("")) this.str_ListePeriode += ";";
        this.str_ListePeriode += typeTEST;   
    }
    if (this.isJalon3 == 1)
    {
        if (!this.str_ListePeriode.equals("")) this.str_ListePeriode += ";";
        this.str_ListePeriode += typeMEP;   
    }
    if (this.isJalon4 == 1)
    {
        if (!this.str_ListePeriode.equals("")) this.str_ListePeriode += ";";
        this.str_ListePeriode += typeVSR;   
    }
    
    this.str_ListeCouleurPeriode = "";
    
    this.str_ListeCouleurPeriode += typeEB;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurPeriode1;
    
    this.str_ListeCouleurPeriode += "@"; 
    
    this.str_ListeCouleurPeriode += typeTEST;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurPeriode2;  
    
    this.str_ListeCouleurPeriode += "@"; 
    
    this.str_ListeCouleurPeriode += typeMEP;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurPeriode3;
    
    this.str_ListeCouleurPeriode += "@"; 
    
    this.str_ListeCouleurPeriode += typeVSR;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurPeriode4;
    
 
    req = "UPDATE    ProfilsProjet  SET ";
    req +=" ListeCouleurPeriode ='"+ this.str_ListeCouleurPeriode + "'";
    req+=",";   
    req +=" ListePeriode ='"+ this.str_ListePeriode + "'";  
     
    req += " WHERE id ="+ this.id;

    myCnx.ExecUpdate(st,nomBase,req,true);
  }  
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  ErrorSpecific myError = new ErrorSpecific();
    if (this.desc != null)
        this.desc=this.desc.replaceAll("\u0092","'").replaceAll("'","''");
    
    req = "INSERT ProfilsProjet (idMembre, nom, description, Annee, Mois, ";
    req+=" idClient, idSt, isDecalage, ";
    req+=" ListeTags, ListeApplications, ListeProjets, idCp, nbPage, ListeCouleurPeriode, ListePeriode) ";
    req+=" VALUES (";
    req +="'"+ this.idMembre + "'";
    req+=",";
    req +="'"+ this.nom + "'";
    req+=",";    
    req +="'"+ this.desc + "'";
    req+=",";
    req +="'"+ this.Annee + "'";
    req+=",";
    req +="'"+ this.Mois + "'";
    req+=",";
    req +="'"+ this.idClient + "'";
    req+=",";
    req +="'"+ this.idSt + "'";
    req+=",";
    req +="'"+ this.isDecalage + "'";
    req+=",";     
    req +="'"+ this.ListeTags + "'";
    req+=",";         
    req +="'"+ this.ListeApplications + "'";
    req+=",";   
    req +="'"+ this.ListeProjets + "'";
    req+=",";   
    req +="'"+ this.idCp + "'";    
    req+=",";   
    req +="'"+ this.nbPage + "'";     
    req+=",";   
    req +="'"+ this.str_ListeCouleurPeriode + "'";    
    req+=",";   
    req +="'"+ this.str_ListePeriode + "'";     
    req+=")";


    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_insert",""+this.id);
    if (myError.cause == -1) return myError;
    
      req="SELECT     TOP (1) id FROM   ProfilsProjet ORDER BY id DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();}    


    return myError;
  }    


  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("login="+this.desc);

    System.out.println("==================================================");

  }

}
