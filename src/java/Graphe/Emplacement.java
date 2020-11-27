/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
package Graphe;

import accesbase.Connexion;
import java.util.Vector;

/**
 *
 * @author JJAKUBOW
 */
public class Emplacement extends Forme {
    public Vector ListeNodes = new Vector(10);
    public int Ligne = -1;
    public int Colonne = -1;
    
    public String getNom(){
        for (int i=0; i < this.ListeNodes.size();i++)
        {
            Node theNode = (Node)this.ListeNodes.elementAt(i);
            if (i==0)
                this.Label=theNode.nomNode;
            else
                this.Label+=","+theNode.nomNode;   
        }
        return this.Label;
    }
    
    public boolean isInEmplacement(Node Node2Find){
        for (int i=0; i < this.ListeNodes.size();i++)
        {
            Node theNode = (Node)this.ListeNodes.elementAt(i);
            if (theNode.idNode==Node2Find.idNode)
                return true;
        }        
        return false;
    }
    
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("Ligne/Colonne="+this.Ligne + "/"+ this.Colonne);
    System.out.println("x="+this.x+"->"+this.x+this.Largeur);
    System.out.println("y="+this.y+"->"+this.x+this.Hauteur);
    System.out.println("ListeNodes.size="+this.ListeNodes.size());
    System.out.println("==================================================");
  }    
}
