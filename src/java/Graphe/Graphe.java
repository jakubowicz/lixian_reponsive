/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
package Graphe; 

import Composant.item;
import ST.Module;
import accesbase.Connexion;
import accesbase.ErrorSpecific;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author JJAKUBOW
 */
public class Graphe extends item {
  public Vector ListeNodes = new Vector(10); 
  public Vector ListeLink = new Vector(10);
  public Vector ListeEmplacements = new Vector(10); 
  private String req="";
  public int Largeur = 0; 
  public int Hauteur = 0;
  public int offsetLeft = 0;
  public int offsetTop = 0;
  
  public static int AjustementX=13;
  public static int AjustementY=10;
 
  public Graphe(int id) {
    this.id = id;
  } 
  
  public Graphe(int id, int Largeur, int Hauteur,int offsetLeft, int offsetTop) {
    this.id = id;
    this.Largeur = Largeur;
    this.Hauteur = Hauteur;
    this.offsetLeft = offsetLeft;
    this.offsetTop = offsetTop;
  }   

 public boolean isLinkExist (Link myLink)
  {
      for (int i=0; i < this.ListeLink.size();i++)
      {
          Link theLink = (Link)this.ListeLink.elementAt(i);
          if (
                  (myLink.NodeOrigine.x == myLink.NodeOrigine.x) && (myLink.NodeOrigine.y == myLink.NodeOrigine.y)
                  ||
                  (myLink.NodeOrigine.x == myLink.NodeOrigine.x) && (myLink.NodeDestination.y == myLink.NodeDestination.y)
              )
          {
              return true;
          }
      }
      return false;
  }
 
  public void setEmplacements (int nb_dalles)
  {
      int LargeurEmplacement = this.Largeur/nb_dalles;
      int HauteurEmplacement = this.Hauteur/nb_dalles;
      
      for (int i=0; i < nb_dalles; i++)
      {
        
        for (int j=0; j < nb_dalles; j++)
        {
          Emplacement theEmplacement = new Emplacement();
          theEmplacement.Ligne = i;
          theEmplacement.Colonne = j;
          theEmplacement.Largeur = LargeurEmplacement;
          theEmplacement.Hauteur = HauteurEmplacement;
          theEmplacement.icone = "images/edit_add.gif";
          theEmplacement.x = i * LargeurEmplacement ;
          theEmplacement.y = j * HauteurEmplacement;          
          //System.out.println("** Emplacement: i="+i+" :: j="+j+" :: x="+theEmplacement.x+" :: y="+theEmplacement.y);
          this.ListeEmplacements.addElement(theEmplacement);
        }          

      }
      
      
  }
  
 public void addNode(Node theNode)
  {
      System.out.println(theNode.nomNode);
      for (int i=0; i < this.ListeNodes.size(); i++)
      {
          Node myNode = (Node)this.ListeNodes.elementAt(i);
          if (myNode.nomNode.equals(theNode.nomNode))
          {
              System.out.println("trouve: " + theNode.nomNode);
              return;
          }
      }
      this.ListeNodes.addElement(theNode); 
  }  
 
 public void setNodesNonPlaces ()
  {
      for (int numNode=0; numNode< this.ListeNodes.size(); numNode++)
      {
        Node theNode = (Node)this.ListeNodes.elementAt(numNode);
        //theNode.dump();
        if (theNode.x !=0 && theNode.y !=0) continue;
        theNode.x_vrai = theNode.x;
        theNode.y_vrai = theNode.y;   
        
       boolean trouve = false;
       if (theNode.centre.equals("centre"))
       {
         int milieu = this.ListeEmplacements.size()/2;
         Emplacement theEmplacement = (Emplacement)this.ListeEmplacements.elementAt(milieu);
          if (theEmplacement.ListeNodes.size() == 0)
          {
                theNode.x = theEmplacement.x + theEmplacement.Largeur/2;
                theNode.y = theEmplacement.y+ theEmplacement.Hauteur/2;
                theNode.idEmplacement = milieu;
                //System.out.println("*** modification des coordonnees ***" + theNode.nomNode + "->" + theNode.x + "/"+theNode.y);
                theEmplacement.ListeNodes.addElement(theNode);
                trouve = true;              
          }
       }
        for (int i=0; i < this.ListeEmplacements.size(); i++)
        {
            if (trouve) continue;
            Emplacement theEmplacement = (Emplacement)this.ListeEmplacements.elementAt(i);
            if (theEmplacement.ListeNodes.size() == 0)
            {
               
                theNode.x = theEmplacement.x + theEmplacement.Largeur/2;
                theNode.y = theEmplacement.y+ theEmplacement.Hauteur/2;
                theNode.idEmplacement = i;
                //System.out.println("*** modification des coordonnees ***" + theNode.nomNode + "->" + theNode.x + "/"+theNode.y);
                theEmplacement.ListeNodes.addElement(theNode);
                trouve = true;
            }
        }      
      }
      
  }
 
  public void setNodesPlaces ()
  {
      for (int numNode=0; numNode< this.ListeNodes.size(); numNode++)
      {
        Node theNode = (Node)this.ListeNodes.elementAt(numNode);
        //theNode.dump();
        if ((theNode.x ==0) && (theNode.y ==0)) continue;
        theNode.x_vrai = theNode.x;
        theNode.y_vrai = theNode.y;         
       boolean trouve = false;
        for (int i=0; i < this.ListeEmplacements.size(); i++)
        {
            if (trouve) continue;
            Emplacement theEmplacement = (Emplacement)this.ListeEmplacements.elementAt(i);
            //theEmplacement.dump();
            if (theEmplacement.isInEmplacement(theNode)) 
            {
                theNode.idEmplacement = i;
                continue;
            }
            if (
                    (theNode.x -AjustementX   >= theEmplacement.x ) 
                    &&
                    (theNode.x -AjustementX  <= theEmplacement.x + theEmplacement.Largeur)
                    &&
                    (theNode.y -AjustementY >= theEmplacement.y) 
                    &&
                    (theNode.y -AjustementY   <= theEmplacement.y + theEmplacement.Hauteur)                    
                )
            {
                theNode.idEmplacement = i;
                theEmplacement.ListeNodes.addElement(theNode);
                trouve = true;
            }
        }      
      }
      
  }
  
  
  public ErrorSpecific bd_InsertNodesMacroSt(String nomBase,Connexion myCnx, Statement st, String transaction, int idMacroSt){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
    req = " DELETE FROM GrapMacroSt WHERE     (idMacroSt  = "+idMacroSt+")";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertNodesMacroSt",""+this.id);
    if (myError.cause == -1) return myError;

     for (int i=0; i < this.ListeNodes.size(); i++)
     {
       Node theNode = (Node)this.ListeNodes.elementAt(i);
        req = "INSERT GrapMacroSt (idMacroSt, idSt, x, y) ";
       req+=" VALUES ('"+idMacroSt+"',  '"+theNode.idNode+"',  '"+theNode.x+"', '"+theNode.y+"')";

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertNodes",""+this.id);
       if (myError.cause == -1) return myError;
     }

     return myError;

  }  
  
  public ErrorSpecific bd_InsertNodesSt(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
    
    req = " DELETE FROM GraphSt WHERE     (graphGraphSt  = "+this.id+")";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertNodes",""+this.id);
    if (myError.cause == -1) return myError;

     for (int i=0; i < this.ListeNodes.size(); i++)
     {
       Node theNode = (Node)this.ListeNodes.elementAt(i);
       req = "INSERT GraphSt (graphGraphSt, stGraphSt, abscisseGraphSt, ordonneeGraphSt) ";
       req+=" VALUES ('"+this.id+"',  '"+theNode.idNode+"',  '"+theNode.x+"', '"+theNode.y+"')";

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertNodes",""+this.id);
       if (myError.cause == -1) return myError;
     }

     return myError;

  }  
  
  public ErrorSpecific bd_InsertNodesOm(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
    req = " DELETE FROM GraphOM WHERE     (idOMRef  = "+this.id+")";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertNodes",""+this.id);
    if (myError.cause == -1) return myError;

     for (int i=0; i < this.ListeNodes.size(); i++)
     {
       Node theNode = (Node)this.ListeNodes.elementAt(i);
       req = "INSERT GraphOM (idOMRef, idOM, x, y) ";
       req+=" VALUES ('"+this.id+"',  '"+theNode.idNode+"',  '"+theNode.x+"', '"+theNode.y+"')";

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertNodes",""+this.id);
       if (myError.cause == -1) return myError;
     }

     return myError;

  }  
  
  public ErrorSpecific bd_InsertNodesCirculationOm(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
    req = " DELETE FROM GraphCirculationOm WHERE     (idOm  = "+this.id+")";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertNodes",""+this.id);
    if (myError.cause == -1) return myError;

     for (int i=0; i < this.ListeNodes.size(); i++)
     {
       Node theNode = (Node)this.ListeNodes.elementAt(i);
       req = "INSERT GraphCirculationOm ( idOm, idVersionSt, x, y) ";
       req+=" VALUES ('"+this.id+"',  '"+theNode.idNode+"',  '"+theNode.x+"', '"+theNode.y+"')";

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertNodes",""+this.id);
       if (myError.cause == -1) return myError;
     }

     return myError;

  }    
  
}
