package ST; 
import java.sql.*;
import accesbase.*;
/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Soci�t� : </p>
 * @author non attribuable
 * @version 1.0
 */

public class VersionSt {
  int idSt = -1;
  int idVersionSt= -1;
  int etat = -1;
  int PreviousidVersionSt = -1;
  int NextidVersionSt = -1;

  final static int etat_notifie=1;
  final static int etat_valide=3;
  final static int etat_supprime=4;

  String req = "";


  public VersionSt(int idSt,int idVersionSt) {
    this.idSt=idSt;
    this.idVersionSt = idVersionSt;
    this.etat = VersionSt.etat_notifie;
  }

  public void valider(){
    this.idVersionSt =  VersionSt.etat_valide;
  }

  public void rejeter(){
    this.idVersionSt =  VersionSt.etat_notifie;
  }

  public void retablir(){
    this.idVersionSt =  VersionSt.etat_valide;
  }


  public void delete(String nomBase,Connexion myCnx, Statement st,ResultSet rs){

  }

  public static void main(String[] args) {

  }

}
