package Organisation; 

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import accesbase.Connexion;
import java.util.Vector;

/**
 * <p>Titre : Comptes utilisateurs</p>
 *
 * <p>Description : ensembels des comptes admin de Lixian</p>
 *
 * <p>Copyright : Copyright (c) 2004</p>
 *
 * <p>Soci�t� : Bytel</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Compte {
  public int id;
  public String login;
  public String mdp;
  public int idProfil;
  public String nom;
  private String type;
  private String req;

  public static Vector ListeComptes = new Vector(10);

  public Compte(String nom, String login,String mdp ) {
    this.nom = nom;
    this.login = login;
    this.mdp = mdp;
    this.type = "nom";
  }

  public Compte(String nom, String login,String mdp,int idProfil ) {
    this.nom = nom;
    this.login = login;
    this.mdp = mdp;
    this.idProfil=idProfil;
    this.type = "nom";
  }

  public Compte(int id) {
    this.id = id;
    this.type = "id";
  }

  public void setId(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    req = "SELECT     idAuthentification FROM    Authentification WHERE nom = '"+this.nom+"'";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      this.id = rs.getInt(1);

      } catch (SQLException s) {s.getMessage(); }
  }

  public static void getListeCompte(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    String req = "SELECT loginAuthentification, mdpAuthentification,idAuthentificationProfil FROM Authentification";
    rs = myCnx.ExecReq(st, nomBase, req);
    int i=0;
    try { while (rs.next()) {
      String loginAuthentification = rs.getString(1);
      String mdpAuthentification = rs.getString(2);
      int idAuthentificationProfil = rs.getInt(3);

      Compte theCompte = new Compte ("",loginAuthentification,mdpAuthentification,idAuthentificationProfil);
      ListeComptes.addElement(theCompte);

          }	} catch (SQLException s) {s.getMessage();}
}

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;

    if (this.type.equals("id"))
      req = "SELECT     nom, loginAuthentification, mdpAuthentification, idAuthentificationProfil FROM         Authentification WHERE idAuthentification ="+this.id;

    else if (this.type.equals("nom"))
      req = "SELECT     nom, loginAuthentification, mdpAuthentification, idAuthentificationProfil FROM         Authentification WHERE idAuthentification ="+this.id;


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      this.nom = rs.getString(1);
      this.login = rs.getString(2);
      this.mdp= rs.getString(3);
      this.idProfil = rs.getInt(4);

      } catch (SQLException s) {s.getMessage(); }


}
    public String bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
      ResultSet rs=null;
      String chaine_a_ecrire="";

      boolean Existe=false;
     req = "SELECT nom FROM    Authentification WHERE nom = '"+this.nom+"'";
     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try {if (rs.next()) Existe=true;	} catch (SQLException s) {s.getMessage(); }

     if (!Existe)
     {
       req = "INSERT INTO Authentification ";
       req+="(nom, loginAuthentification, mdpAuthentification, idAuthentificationProfil) VALUES  (";
       req+="'"+this.nom+"'";
       req+=",'"+this.login+"'";
       req+=",'"+this.mdp+"'";
       req+=","+this.idProfil+"";
       req+=")";
       if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
       chaine_a_ecrire+=" a �t� cr��";
     }
     else
      {
       chaine_a_ecrire+=" n'a pas �t� cr�� car il existe d�j�";
       return req+ chaine_a_ecrire;
      }


       return "OK";


  }

  public String bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;

    req = "UPDATE Authentification SET ";
    req+=" nom='"+this.nom+"'";
    req+=",loginAuthentification='"+this.login+"'";
    req+=",mdpAuthentification='"+this.mdp+"'";
    req+=",idAuthentificationProfil ='"+this.idProfil+"'";
    req+=" where idAuthentification   = '"+this.id +"'";


    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     return "OK";

  }

  public String bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;

    req = "DELETE FROM  Authentification where idAuthentification   = '"+this.id +"'";
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     return "OK";

  }

    public void dump(){
      if (Connexion.traceOn.equals("no")) return;
      System.out.println("==================================================");
      System.out.println("id="+this.id);
      System.out.println("nom="+this.nom);
      System.out.println("login="+this.login);
      System.out.println("mdp="+this.mdp);
      System.out.println("==================================================");
  }
}
