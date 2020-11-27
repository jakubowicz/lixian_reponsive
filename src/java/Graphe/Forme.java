package Graphe; 

import accesbase.Connexion;
import Composant.item;
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
public class Forme extends item{
  public String Type;
  public String old_couleur="";
  public String couleur="";
  public String icone="";

  public String Label=""; //valeur de la boite
  public String Detail=""; //valeur de la boite
  public int x;
  public int y;
  public int Largeur;
  public int Longueur;
  public int Hauteur;

  public int x_vrai;
  public int y_vrai;
  public int Largeur_vrai;
  public int Longueur_vrai;
  public int Hauteur_vrai;


  //Police myFont=null;
  //Color CouleurFond = null;
  //Color CouleurContour = Color.black;
  //Color CouleurTexte = Color.black;

  public String forme;

  public String Style;
  public String CouleurTexte;
  public int Epaisseur;
  public int Taille;

  //Image myImage;

  public Forme() {

  }
  
  public Forme(int id) {
      this.id=id;

  }  

  public Forme  (String Type, String couleur,String icone) {
    this.Type=Type;
    this.couleur=couleur;
    this.icone=icone;
    int Index = icone.lastIndexOf("\\");
    this.icone = icone.substring(Index);
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
       req = "SELECT     nomTypeAppli, formeTypeAppli, formedefautTypeAppli, icone, couleur";
       req+= " FROM         AppliIcone";
       req+=" WHERE     id = "+ this.id;


     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       
       this.forme = rs.getString("nomTypeAppli");
       if (this.forme == null) this.forme="";
       
       this.icone = rs.getString("icone");
       if (this.icone == null) this.icone="";
       
       this.couleur = rs.getString("couleur");
       if (this.couleur == null) this.couleur="";       
              
       } catch (SQLException s) {s.getMessage(); }
  }  

  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){

    req = "DELETE FROM AppliIcone WHERE (id  = '"+this.id+"')";
    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  }   
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
    String description="";


    req = "UPDATE [AppliIcone]";
    req+= "   SET [nomTypeAppli] = "+ "'"+this.forme+ "'" + ",";
    req+= "      [formeTypeAppli] = "+ "'"+this.forme+ "'" + ",";
    req+= "       [formedefautTypeAppli] = "+ "'"+this.forme+ "'" + ",";
    req+= "       [icone] = "+ "'"+this.icone+ "'" + ",";
    req+= "       [couleur] = "+ "'"+this.couleur+ "'" ;
    req+= "  WHERE id= "+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
    
    if (!this.old_couleur.equals(this.couleur))
    {
        req = "update TypeSi set couleurSI='"+this.couleur+"' where couleurSI='"+this.old_couleur+"'";
        myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);        
    }

    return myError;
  }  

  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

    req = "INSERT INTO [AppliIcone]";
    req+= "           ([nomTypeAppli]";
    req+= "            ,[formeTypeAppli]";
    req+= "            ,[formedefautTypeAppli]";
    req+= "            ,[icone]";
    req+= "            ,[couleur]";
    req+=")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.forme+"'" + ",";
    req+= "'"+this.forme+"'" + ",";
    req+= "'"+this.forme+"'" + ",";
    req+= "'"+this.icone+"'"+ ",";
    req+= "'"+this.couleur+"'";
    req+= "            )";


    //myCnx.trace("@@456789--------","req",""+req);
      ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);

      //myCnx.trace("@@456789--------","bd_Insert",""+transaction);

      //req = "SELECT idRoadmap FROM  Briefs WHERE   (version = '"+this.version+"')";
      req="SELECT  id FROM   AppliIcone ORDER BY id DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("id");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  }    
  
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("Type="+this.Type);
    System.out.println("couleur="+this.couleur);
    System.out.println("icone="+this.icone);
    System.out.println("==================================================");
  }
}
