/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesbase;
import Composant.item;
/**
 *
 * @author Joël
 */
public class champ extends item {
    public String type="";
    public boolean isKey = false;
    
    public champ (String nom, String type, boolean isKey){
        this.nom = nom;
        this.type = type;
        this.isKey = isKey;
    }
}
