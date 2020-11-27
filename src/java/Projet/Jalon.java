package Projet; 
import Composant.*;
import PO.LignePO;
import accesbase.Connexion;
import java.util.Vector;
import java.util.Date;
import java.sql.Statement;
import General.Utils;
import accesbase.ErrorSpecific;
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
public class Jalon extends idNom{
  public int type = -1;
  public String Commentaire = "";
  public String Acteur = "";
  public int typeDoc = -1;
  public String str_typeDoc = ""; 
  
  public String Livrable = "";
  public Date date = null;
  public String strDate = "";
  
  public Date date_init = null;
  public String strDate_init = "";  
  
  public Date date_moe = null;
  public String strDate_moe = "";   
  
  public Date date_go = null;
  public String strDate_go = "";   
  
  public int idRoadmap = -1;
  
  public typeJalon typology = null;
  
  private String req = "";
  
  public int isEssentiel = 0;
  public int isStLie = 0;
  public String alias = "";
  public int numIndicateur = -1;
  public int isEngagement = -1;
  
  public String couleur = "";
  
  public float x = 0;
  public float y = 0;
  public float x_init = 0;
  public float x_go = 0;
  public float y_init = 0;  
  
  public int x0 = 0;
  public int x1 = 0;
  public int y0 = 0;
  public int y1 = 0;
  
  public boolean traversantPrecedent = false;

  public static Vector ListeTypeJalons = new Vector(10);

  public Jalon(int id) {
    this.id = id;
  }

  public Jalon() {

  }
  
  public Jalon(typeJalon typology) {
      this.typology = typology;

  }  
  
  public Jalon(String nom) {
    this.nom = nom;
  }
  
  public void setDatesPlanning(int AnneeRef, int mois_start ){

    try
    {
      Utils.getDate( this.strDate);

      if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
      {
        this.x = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
        if (this.x > 12) this.x = 12;
      }
      else
      {
        this.x = 0;
        this.traversantPrecedent = true;
    }
    }
    catch (Exception e)
    {
      this.x = -1;
    }

    try
    {
      Utils.getDate( this.strDate_init);

      if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
      {
        this.x_init = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
        if (this.x_init > 12) this.x_init = 12;
      }
      else
      {
        this.x_init = 0;
        this.traversantPrecedent = true;
    }
    }
    catch (Exception e)
    {
      this.x_init = -1;
    }
    
    try
    {
      Utils.getDate( this.strDate_go);

      if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
      {
        this.x_go = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
        if (this.x_go > 12) this.x_go = 12;
      }
      else
      {
        this.x_go = -1;
        //this.traversantPrecedent = true;
    }
    }
    catch (Exception e)
    {
      this.x_go = -1;
    }    

    if (this.x < 0) this.x = 0;
    if (this.x_init < 0) this.x_init = 0;
    if (this.x_go < 0) this.x_go = -1;

    }  
    
  public static void getListeTypeJalons(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String req="";

    req = "SELECT     id, nom, description";
    req+="    FROM         typeJalon";
    req+="     ORDER BY ordre";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        Jalon theJalon = new Jalon(rs.getInt("id"));
        theJalon.nom= rs.getString("nom"); // nom du ST
        theJalon.description= rs.getString("description"); // nom du ST

        ListeTypeJalons.addElement(theJalon);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
     
    req="SELECT     typeJalon.id, typeJalon.nom, typeJalon.description, typeJalon.ordre, typeJalon.alias, typeJalon.isEssentiel, typeJalon.isStLie, JalonsProjet.date, JalonsProjet.date_moe, JalonsProjet.date_go, JalonsProjet.type";
    req+=" FROM         typeJalon INNER JOIN";
    req+="                       JalonsProjet ON typeJalon.id = JalonsProjet.type";
    req+=" WHERE     JalonsProjet.idRoadmap = "+ this.id;
    req+=" AND     typeJalon.isEssentiel = 1";
    req+= " WHERE     id = "+ this.id;

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  try {
  while(rs.next())
  {
     this.nom = rs.getString("nom");
     this.alias = rs.getString("alias");
     this.isEssentiel = rs.getInt("isEssentiel");
     this.isStLie = rs.getInt("isStLie");
     
     this.date = rs.getDate("date");
        if (this.date != null)
          this.strDate = ""+this.date.getDate()+"/"+(this.date.getMonth()+1) + "/"+(this.date.getYear() + 1900);
         else
          this.strDate = "";  
        
     this.date_moe = rs.getDate("date_moe");
        if (this.date_moe != null)
          this.strDate_moe = ""+this.date_moe.getDate()+"/"+(this.date_moe.getMonth()+1) + "/"+(this.date_moe.getYear() + 1900);
         else
          this.strDate_moe = "";      
        
     this.date_go = rs.getDate("date_go");
        if (this.date_go != null)
          this.strDate_go = ""+this.date_go.getDate()+"/"+(this.date_go.getMonth()+1) + "/"+(this.date_go.getYear() + 1900);
         else
          this.strDate_go = "";             
        
     this.type = rs.getInt("type");
   }

  }
   catch (SQLException s) { }     
  } 
  
  public String getColorFromList(Vector myListe){
      String myColor = "";
      
      for (int i= 0; i < myListe.size(); i++)
      {
          item theItem = (item)myListe.elementAt(i);
          if (this.type == theItem.id)
          {
              return theItem.nom;
          }
      }
      return myColor;

  }
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
      
    String[] ligneDate = this.strDate.split("/");
    String theDate = "convert(datetime, '"+ligneDate[0]+"/"+ligneDate[1]+"/"+ligneDate[2]+"', 103)";

    req = "UPDATE [JalonsProjet]";
    req+= "   SET [type] = "+ "'"+this.type+ "'" + ",";
    req+= "       [date] = " +theDate + ",";
    req+= "       [idRoadmap] = "+ "'"+this.idRoadmap+ "'" ;

    req+= "  WHERE id= "+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }
  
  public void bd_updateMigration(String nomBase,Connexion myCnx, Statement st){
      
    String theDate = "convert(datetime, '"+this.date.getDate()+"/"+(this.date.getMonth()+1) + "/"+(this.date.getYear() + 1900)+"', 103)";
    String str_Livrable = "";
    if (this.Livrable != null) str_Livrable = this.Livrable.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
    String str_Commentaire = "";
    if (this.Commentaire != null) str_Commentaire = this.Commentaire.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");    

    req = "UPDATE [JalonsProjet]";
    req+= "   SET [type] = "+ "'"+this.type+ "'" + ",";
    req+= "       [date_moe] = " +theDate + ",";
    req+= "       [Livrable] = "+ "'"+str_Livrable+ "'" + ",";;
    req+= "       [Remarques] = "+ "'"+str_Commentaire+ "'" ;

    req+= "  WHERE idRoadmap= "+this.idRoadmap;
    req+= "  AND type= "+this.type;

    myCnx.ExecUpdate(st,nomBase,req,true);

  }  
  
  public void bd_updateMigrationGo(String nomBase,Connexion myCnx, Statement st){
     ResultSet rs;
    String req = ""; 
    
    int typeEB = -1;
    
    req = "SELECT     id, nom FROM  typeJalon WHERE (numIndicateur =2 )";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
            typeEB =  rs.getInt("id");
      }}   catch (SQLException s) {s.getMessage();}  
    
      
    String theDate = "convert(datetime, '"+this.date.getDate()+"/"+(this.date.getMonth()+1) + "/"+(this.date.getYear() + 1900)+"', 103)";  

    req = "UPDATE [JalonsProjet]";
    req+= "   SET [type] = "+ "'"+typeEB+ "'" + ",";
    req+= "       [date_go] = " +theDate + "";

    req+= "  WHERE idRoadmap= "+this.idRoadmap;
    req+= "  AND type= "+typeEB;

    myCnx.ExecUpdate(st,nomBase,req,true);

  }    

  public void bd_updateMigrationDoc(String nomBase,Connexion myCnx, Statement st){
     ResultSet rs;
    String req = ""; 
    
    int typeEB = -1;
    
    req = "SELECT     id, nom FROM  typeJalon WHERE (numIndicateur =2 )";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
            typeEB =  rs.getInt("id");
      }}   catch (SQLException s) {s.getMessage();}  
    
      
    String theDate = "convert(datetime, '"+this.date.getDate()+"/"+(this.date.getMonth()+1) + "/"+(this.date.getYear() + 1900)+"', 103)";  

    req = "UPDATE [JalonsProjet]";
    req+= "   SET [type] = "+ "'"+typeEB+ "'" + ",";
    req+= "       [date_go] = " +theDate + "";

    req+= "  WHERE idRoadmap= "+this.idRoadmap;
    req+= "  AND type= "+typeEB;

    myCnx.ExecUpdate(st,nomBase,req,true);

  } 
  
  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;
    
  
    req = "DELETE FROM JalonsProjet WHERE (id  = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){
      
    String[] ligneDate = this.strDate.split("/");
    String theDate = "convert(datetime, '"+ligneDate[0]+"/"+ligneDate[1]+"/"+ligneDate[2]+"', 103)";
    
    String theDate_init = "NULL";
    String theDate_moe = "NULL";
    String theDate_go = "NULL";
    
    if ((this.strDate_init != null) && (!this.strDate_init.equals("")))
    {
        String[] ligneDate_init = this.strDate_init.split("/");
        theDate_init = "convert(datetime, '"+ligneDate_init[0]+"/"+ligneDate_init[1]+"/"+ligneDate_init[2]+"', 103)";  
    }
    
    if ((this.strDate_moe != null) && (!this.strDate_moe.equals("")))
    {
        String[] ligneDate_moe = this.strDate_moe.split("/");
        theDate_moe = "convert(datetime, '"+ligneDate_moe[0]+"/"+ligneDate_moe[1]+"/"+ligneDate_moe[2]+"', 103)";  
    }
    
    if ((this.strDate_go != null) && (!this.strDate_go.equals("")))
    {
        String[] ligneDate_go = this.strDate_go.split("/");
        theDate_go = "convert(datetime, '"+ligneDate_go[0]+"/"+ligneDate_go[1]+"/"+ligneDate_go[2]+"', 103)";  
    }    
    
    String str_Livrable = "";
    if (this.Livrable != null) str_Livrable = this.Livrable.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
    String str_Commentaire = "";
    if (this.Commentaire != null) str_Commentaire = this.Commentaire.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");    
    
    
    req = "INSERT INTO [JalonsProjet]";
    req+= "           ([type]";
    req+= "            ,[date]";
    req+= "            ,[date_init]";;
    req+= "            ,[date_moe]";
    req+= "            ,[date_go]";
    req+= "            ,[Livrable]";
    req+= "            ,[Remarques]";
    req+= "            ,[idRoadmap]";
    req+=")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.type+"'" + ",";
    req+= ""+theDate+"" + ",";
    req+= ""+theDate_init+"" + ",";
    req+= ""+theDate_moe+"" + ",";
    req+= ""+theDate_go+"" + ",";
    req+= "'"+str_Livrable+"'" + ",";
    req+= "'"+str_Commentaire+"'" + ",";
    req+= "'"+this.idRoadmap+"'";
    req+= " )";

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);

    return myError;
  } 
  
  public void bd_insert(String nomBase,Connexion myCnx, Statement st){
      
    String[] ligneDate = this.strDate.split("/");
    String theDate = "convert(datetime, '"+ligneDate[0]+"/"+ligneDate[1]+"/"+ligneDate[2]+"', 103)";
    
    String theDate_init = "NULL";
    
    if ((this.strDate_init != null) && (!this.strDate_init.equals("")))
    {
        String[] ligneDate_init = this.strDate_init.split("/");
        theDate_init = "convert(datetime, '"+ligneDate_init[0]+"/"+ligneDate_init[1]+"/"+ligneDate_init[2]+"', 103)";  
    }
    
    req = "INSERT INTO [JalonsProjet]";
    req+= "           ([type]";
    req+= "            ,[date]";
    req+= "            ,[date_init]";
    req+= "            ,[idRoadmap]";
    req+=")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.type+"'" + ",";
    req+= ""+theDate+"" + ",";
    req+= ""+theDate_init+"" + ",";
    req+= "'"+this.idRoadmap+"'";
    req+= " )";

    myCnx.ExecUpdate(st,nomBase,req,true);
  }  
  
  public ErrorSpecific bd_insertBaseline(String nomBase,Connexion myCnx, Statement st, String transaction){
      
    String[] ligneDate = this.strDate.split("/");
    String theDate = "convert(datetime, '"+ligneDate[0]+"/"+ligneDate[1]+"/"+ligneDate[2]+"', 103)";
    
    
    req = "INSERT INTO [JalonsBaseline]";
    req+= "           ([type]";
    req+= "            ,[date]";
    req+= "            ,[idBaseline]";
    req+=")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.type+"'" + ",";
    req+= ""+theDate+"" + ",";
    req+= "'"+this.id+"'";
    req+= " )";

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);

    return myError;
  }  

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    LignePO theLignePO=null;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("description="+this.description);
    System.out.println("Commentaire="+this.Commentaire);
    System.out.println("type="+this.type);
    System.out.println("Acteur="+this.Acteur);
    System.out.println("Livrable="+this.Livrable);
    System.out.println("dateCreation="+this.dateCreation);

  }

  public static void main(String[] args) {
    Jalon jalon = new Jalon();
  }
}
