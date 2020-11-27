/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Projet;

import accesbase.Connexion;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author JJAKUBOW
 */

public class dependanceRoadmap {
    public Roadmap roadmapOrigine=null;
    public Roadmap roadmapDestination=null;
    public String type = "";
    public String nomStRoadmapOrigine="";
    public String versionRoadmapOrigine="";
    public int nb=0;
    
    public dependanceRoadmap(){
        
    }
    
    public long getDecalage(String nomBase,Connexion myCnx, Statement st){
    long delta = 0;
    
    try
    {
    
    Jalon jalon_destination = this.roadmapDestination.getJalonsEssentielByOrdre(nomBase,myCnx, st, 1);
    Jalon jalon_origine = this.roadmapOrigine.getJalonsEssentielByOrdre(nomBase,myCnx, st, 4);
    
    //long timeDestination = this.roadmapDestination.DdateT0.getTime();
    //long timeOrigine = this.roadmapOrigine.DdateProd.getTime();   
    
    long timeDestination = jalon_destination.date.getTime();
    long timeOrigine = jalon_origine.date.getTime();
    
    delta = timeOrigine- timeDestination ;
     
    if (delta >= 0 )
    {
        return delta+1000 * 3600 *24;
    }
    else
    {
       return 0; 
    }
    }
    catch (Exception e){
       return 0; 
    }

    }
    
    public Date setDecalage (Date myDate, long delta){
        
        long timeInMs = myDate.getTime();
        timeInMs += delta;
        Date newDate = new Date(timeInMs);
        return newDate;
    }
}
