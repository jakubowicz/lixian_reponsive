/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Composant;

import accesbase.Connexion;
import java.sql.ResultSet;
import java.sql.Statement; 
import java.util.Vector;

/**
 *
 * @author Joël
 */
public class dropDownnList {
  private String req="";
  private ResultSet rs=null;
  public Vector ListeItem = new Vector(30); 
  
public  void buildListeTypeModule(String nomBase,Connexion myCnx, Statement st){
 
  req= "SELECT     id, nom, description FROM   TypeModule ORDER BY ordre";
  this.buildListeItem( nomBase, myCnx,  st,  req);

}

public  void buildListeItem(String nomBase,Connexion myCnx, Statement st, String req){
 
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
          item theItem = new item();
          theItem.id = rs.getInt(1);
          theItem.nom = rs.getString(2);
        
        this.ListeItem.addElement(theItem);
      }
    }
    catch (Exception e) {}

} 

}
