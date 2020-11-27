/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projet;
import Composant.item;
import java.util.Date;

/**
 *
 * @author Joël
 */
public class PhaseProjet extends item {
    public java.util.Date DDate_start = null;
    public java.util.Date DDate_end = null;
    public boolean isCompleted = false;
    public boolean isLate = false;
    
    public PhaseProjet(String nom, Date date_start, Date date_end){
        this.DDate_start = date_start;
        this.DDate_end = date_end;
        this.nom = nom;
    }
    public void setIsLate(int ordreJalon){
        
        if (ordreJalon >= this.ordre)
        {
            this.isLate = false;
            return;
        }
        if (this.DDate_end == null)
        {
            //System.out.println("@9876: "+ this.nom);
            isLate = false;
            return;
        }
        
        Date currentDate = new Date();
        long l_currentDate = currentDate.getTime();  
        
        long l_endDate = this.DDate_end.getTime();
        
               
        if (l_endDate >= l_currentDate)
                isLate = false;
            else
                isLate = true;
        
        
    }
    
}
