/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projet;
import Composant.item;
import static Organisation.Service.ListeRoadmapProfil;
import accesbase.Connexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import accesbase.ErrorSpecific;
/**
 *
 * @author JJAKUBOW
 */
public class RoadmapProfil extends item {
    public String phase = "";
    public int delais = 0;
    public int charge = 0;
    
    private String req="";

  public RoadmapProfil(int id) {
    this.id = id;
  }    
  
  
  public RoadmapProfil() {
   
  } 

 public ErrorSpecific bd_updateProfil(String nomBase,Connexion myCnx, Statement st, String transaction,String theNom, String thePhase){

   ResultSet rs=null;
   ErrorSpecific myError = new ErrorSpecific();

   String req = "DELETE FROM  RoadmapProfil";
      req+=" WHERE     (nom = '"+theNom+"')";
      req+=" AND     (phase  = '"+thePhase +"')";   

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateProfil","");
    if (myError.cause == -1) return myError;
   //myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

      req="INSERT RoadmapProfil ";
      req+=" (";
      req+=" nom, ";
      req+=" phase, ";
      req+=" delais,";
      req+=" charge)";

      req+=" VALUES (";

      req+="'"+theNom+"',";
      req+="'"+thePhase+"',";
      req+=this.delais+",";
      req+=this.charge+"";
      
      req+=" )";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateProfil","");
    if (myError.cause == -1) return myError;

    return myError;
  }  
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs=null;

      //String req = "SELECT     id, nom, acteur, idetat, idRoadmap, dateAction FROM   actionSuivi WHERE idRoadmap ="+this.id;
      req = "SELECT     nom, phase, delais, charge";
      req+=" FROM         RoadmapProfil";
      req+=" WHERE     (id = "+this.id+")";

      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      try {
        while (rs.next()) {
          
          this.nom = rs.getString("nom");
          this.phase = rs.getString("phase");
          this.delais = rs.getInt("delais");
          this.charge = rs.getInt("charge");

        }
      }
            catch (SQLException s) {s.getMessage();}

  }  
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st,String theNom, String thePhase ){
    ResultSet rs=null;

      //String req = "SELECT     id, nom, acteur, idetat, idRoadmap, dateAction FROM   actionSuivi WHERE idRoadmap ="+this.id;
      req = "SELECT     delais, charge";
      req+=" FROM         RoadmapProfil";
      req+=" WHERE     (nom = '"+theNom+"')";
      req+=" AND     (phase  = '"+thePhase +"')";

      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      try {
        while (rs.next()) {
          
          this.delais = rs.getInt("delais");
          this.charge = rs.getInt("charge");

        }
      }
            catch (SQLException s) {s.getMessage();}

  }  
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st, int id, String thePhase ){
    ResultSet rs=null;

      //String req = "SELECT     id, nom, acteur, idetat, idRoadmap, dateAction FROM   actionSuivi WHERE idRoadmap ="+this.id;
      req = "SELECT     RoadmapProfil.delais, RoadmapProfil.charge";
      req+=" FROM         RoadmapProfil INNER JOIN";
      req+="                       typeProfilRoadmap ON RoadmapProfil.nom = typeProfilRoadmap.nom";
      req+=" WHERE     (RoadmapProfil.phase = '"+thePhase +"') AND (typeProfilRoadmap.id = '"+id +"')";

      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      try {
        while (rs.next()) {
          
          this.delais = rs.getInt("delais");
          this.charge = rs.getInt("charge");

        }
      }
            catch (SQLException s) {s.getMessage();}

  }    
  
}
