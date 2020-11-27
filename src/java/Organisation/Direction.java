package Organisation;  

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import accesbase.Connexion;
import ST.MacroSt;
import java.util.Vector;
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
public class Direction {
  public int id;
  public String nom="";
  public String old_nom="";
  public String description="";
  private String req  ="";
  public String chaine_a_ecrire="";
  public Vector ListeServices = new Vector(10);


  public Direction(int id) {
    this.id = id;
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    req = "EXEC ACTEUR_SelectUndirection "+this.id;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      this.nom = rs.getString(1);
      this.description = rs.getString(2);
      } catch (SQLException s) {s.getMessage(); }
  }

  public void getListeServices(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String nom="";
    String dateEB="";
    String datePROD="";
    String dateMES="";
    int idVersionSt = -1;
    String version= "";

    req="SELECT     idService, nomService, alias, descService, mdpService, dirService, typeService, siService, nomServicePO, sendRetard, aliasGraphe";
    req+="    FROM         Service";
    req+=" WHERE     (dirService = "+this.id+")";
    req+=" ORDER BY nomService";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
    while(rs.next())
    {
       int idService = rs.getInt("idService");
       Service theService = new Service(idService );
       theService.nom = rs.getString("nomService");
       this.ListeServices.addElement(theService);
     }

    }
     catch (SQLException s) { }

  }
  public ErrorSpecific bd_Enreg(String action, String typeForm, String nomBase,Connexion myCnx, Statement st, String transaction){

    ErrorSpecific myError = new ErrorSpecific();
    String result="";
    if (typeForm.equals("Creation")) { // Cr�ation d'un nouveau ST
      myError=this.bd_Insert( nomBase, myCnx,  st,  transaction);
    }
    else { //MAJ du ST
      if (action.equals("supp"))
          { //l'utilisateur a cliqu� sur le bouton supprimer

           myError=this.bd_Delete(nomBase,myCnx,st,transaction);

          }
            else
            {
              myError=this.bd_Update( nomBase, myCnx,  st,  transaction);
            }
}
    return myError;
  }

  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  ErrorSpecific myError = new ErrorSpecific();

  String myDesc=this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
  req = "EXEC ACTEUR_InsererDirection ";
    req+="'"+this.nom+"','";
    req+=myDesc+"'";

  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
  if (myError.cause == -1) return myError;

      req="SELECT     TOP (1) idDirection  FROM   Direction ORDER BY idDirection  DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();}    
      
   return myError;
}

 public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
   ErrorSpecific myError = new ErrorSpecific();

   req = "EXEC ACTEUR_MajDirection ";
     req+="'"+this.id+"',";
     req+="'"+this.nom+"',";
     req+="'"+this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";

   myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
   if (myError.cause == -1) return myError;


   MacroSt theMacroSt = new MacroSt(this.old_nom);
   theMacroSt.getAllFromBd(nomBase, myCnx, st);

   if (theMacroSt.id != -1)
   {
     req = "UPDATE MacroSt SET nomMacrost ='" + this.nom + "' WHERE idMacrost =" + theMacroSt.id;
     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
     if (myError.cause == -1) return myError;

   req = "UPDATE MacroSt SET aliasMacrost ='" + this.nom + "' WHERE idMacrost =" + theMacroSt.id;
   myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
   if (myError.cause == -1) return myError;


   }

    return myError;
  }

  public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;

      this.getListeServices(nomBase,myCnx, st);
      for (int i=0; i < this.ListeServices.size();i++)
      {
        Service theService = (Service)this.ListeServices.elementAt(i);
        theService.getAllFromBd(nomBase,myCnx, st);
        myError=theService.bd_Delete(nomBase,myCnx, st,transaction);
        if (myError.cause == -1) return myError;
      }

      req = "DELETE Direction WHERE idDirection =" + this.id;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
      if (myError.cause == -1) return myError;

      req = "DELETE MacroSt WHERE nomMacrost  ='" + this.nom+"'";
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
      if (myError.cause == -1) return myError;


     return myError;
}



  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("nom="+this.description);
    System.out.println("==================================================");
  }
}
