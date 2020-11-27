package ST; 

import accesbase.Connexion;
import accesbase.ErrorSpecific;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
public class Module {
  public int id=-1;
  public String nom="";
  public String descModule="";
  public int Ihm=0;
  public boolean checkIhm;
  public int InterfaceSynchrone=0;
  public boolean checkInterfaceSynchrone;
  public int InterfaceAsynchrone=0;
  public boolean checkInterfaceAsynchrone;
  public int idTypeModule = -1;
  public String nomTypeModule = "";
  public int idTypeEnvironnement = -1;
  public String nomTypeEnvironnement = "";  
  public int versionStModule = -1;
  
  public String nomSt;
  public String type;
  
  public int ordre= 0;
  
  private String req="";

  public Module(){

  }
  public Module(
       int id,
       String nom,
       String descModule,
       int Ihm,
       int InterfaceSynchrone,
       int InterfaceAsynchrone
) {
       this.id=id;
       this.nom=nom;
       this.descModule=descModule;

       this.Ihm=Ihm;

       if (Ihm==0)
               {
                 this.checkIhm = false;
               }
               else
               {
                 this.checkIhm = true;
               }

       this.InterfaceSynchrone=InterfaceSynchrone;
       if (InterfaceSynchrone==0)
               {
                 this.checkInterfaceSynchrone = false;
               }
               else
               {
                 this.checkInterfaceSynchrone = true;
               }

       this.InterfaceAsynchrone=InterfaceAsynchrone;
       if (InterfaceAsynchrone==0)
               {
                 this.checkInterfaceAsynchrone = false;
               }
               else
               {
                 this.checkInterfaceAsynchrone =true;
               }

  }

  public Module(
     int id,
     String nom,
     String descModule,
     String Ihm,
     String InterfaceSynchrone,
     String InterfaceAsynchrone
) {
     this.id=id;
     this.nom=nom.replaceAll("'", "''");
     this.descModule=descModule.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\r","");

     if (Ihm == null) this.Ihm = 0;
     else
     {
             if (Ihm.equals("on")) this.Ihm = 1; else this.Ihm = 0;
     }

     if (InterfaceSynchrone == null) this.InterfaceSynchrone = 0;
     else
     {
              if (InterfaceSynchrone.equals("on")) this.InterfaceSynchrone = 1; else this.InterfaceSynchrone = 0;
     }

     if (InterfaceAsynchrone == null) this.InterfaceAsynchrone = 0;
     else
     {
              if (InterfaceAsynchrone.equals("on")) this.InterfaceAsynchrone = 1; else this.InterfaceAsynchrone = 0;
                        }
}
public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs=null;

req = "SELECT     Module.nomModule, Module.descModule, Module.versionStModule, Module.Ihm, Module.InterfaceSynchrone, Module.InterfaceAsynchrone, Module.idTypeModule, TypeModule.nom AS type, Module.ordre";
req+= " FROM         Module LEFT OUTER JOIN";
req+= "                       TypeModule ON Module.idTypeModule = TypeModule.id";
req+= " WHERE     (Module.idModule = "+this.id+")";

  rs = myCnx.ExecReq(st, nomBase, req);

     try {
          rs.next();
          this.nom=rs.getString("nomModule");
          if ((this.nom == null) || this.nom.equals("null")) this.nom ="";
          this.descModule=rs.getString("descModule");
          if ((this.descModule == null) || this.descModule.equals("null")) this.descModule ="";
          this.Ihm=rs.getInt("Ihm");
          
          this.InterfaceSynchrone=rs.getInt("InterfaceSynchrone");
          this.InterfaceAsynchrone=rs.getInt("InterfaceAsynchrone");
          this.idTypeModule=rs.getInt("idTypeModule");
          this.type=rs.getString("type");
          if (this.type == null || this.type.equals("null")) this.type = "";
           this.ordre=rs.getInt("ordre");
         }
       

         catch (SQLException s) {
           s.getMessage();
         }
    if (this.type == null || this.type.equals("null")) this.type = "";
    
}

  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
    String description="";

    if (this.descModule != null)
    {
      for (int j=0; j < this.descModule.length();j++)
      {
        int c = (int)this.descModule.charAt(j);
        if (c != 34)
          {
            description += this.descModule.charAt(j);
          }
          else
          {
            description +="'";
          }
      }
      description = description.replaceAll("\r","").replaceAll("'","''");
         }

    req = "UPDATE [Module]";
    req+="    SET ";
    req+="    [nomModule] = '"+ this.nom+ "'";
    req+="   ,[descModule] = '"+ description+ "'";
    req+="   ,[Ihm] = '"+ this.Ihm+ "'";
    req+="   ,[InterfaceSynchrone] = '"+ this.InterfaceSynchrone+ "'";
    req+="   ,[InterfaceAsynchrone] = '"+ this.InterfaceAsynchrone+ "'";
    req+="   ,[idTypeModule] = '"+ this.idTypeModule+ "'";
    req+="   ,[ordre] = '"+ this.ordre+ "'";
    req+=" WHERE idModule=" + this.id;

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  }

  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){

    req = "DELETE FROM Module WHERE (idModule  = '"+this.id+"')";
    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction, int idVersionSt){
    String description="";
    if (this.descModule != null)
    {
      for (int j=0; j < this.descModule.length();j++)
      {
        int c = (int)this.descModule.charAt(j);
        if (c != 34)
          {
            description += this.descModule.charAt(j);
          }
          else
          {
            description +="'";
          }
      }
      description = description.replaceAll("\r","").replaceAll("'","''");
         }
  
    req = "INSERT INTO [Module]";
    req+= "           ([nomModule]";
    req+= "            ,[descModule]";
    req+= "            ,[versionStModule]";
    req+= "            ,[Ihm]";
    req+= "            ,[InterfaceSynchrone]";
    req+= "            ,[InterfaceAsynchrone]";
    req+= "            ,[idTypeModule]";
    req+= "            ,[ordre]";
    req+= "            )";    
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.nom+"'" + ",";
    req+= "'"+description+"'" + ",";
    req+= "'"+idVersionSt+"'" + ",";
    req+= "'"+this.Ihm+"'" + ",";
    req+= "'"+this.InterfaceSynchrone+"'" + ",";
    req+= "'"+this.InterfaceAsynchrone+"'" + ",";
    req+= "'"+this.idTypeModule+"'" + ",";
    req+= "'"+this.ordre+"'" + "";    
    req+= "            )";

      ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);

      if (myError.cause == -1) return myError;
      req="SELECT  idModule FROM   Module ORDER BY idModule DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("idModule");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  }
  
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("descModule="+this.descModule);
    System.out.println("Ihm="+this.Ihm);
    System.out.println("checkIhm="+this.checkIhm);
    System.out.println("InterfaceSynchrone="+this.InterfaceSynchrone);
    System.out.println("checkInterfaceSynchrone="+this.checkInterfaceSynchrone);
    System.out.println("InterfaceAsynchrone="+this.InterfaceAsynchrone);
    System.out.println("checkInterfaceAsynchrone="+this.checkInterfaceAsynchrone);
    System.out.println("checkInterfaceAsynchrone="+this.idTypeModule);
    System.out.println("==================================================");
  }
}
