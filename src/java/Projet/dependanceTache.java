/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Projet;
import java.util.Date;
/**
 *
 * @author JJAKUBOW
 */
public class dependanceTache {
    public Action tacheOrigine=null;
    public Action tacheDestination=null;
    public String type = "";
    public String codeTacheDestination="";
    
    public dependanceTache(){
        
    }
    
    public long getDecalage(){
    long delta = 0;
    
    try
    {
    long timeDestination = this.tacheDestination.Ddate_start.getTime();
    long timeOrigine = this.tacheOrigine.Ddate_end.getTime();
    
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
