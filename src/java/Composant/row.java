/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Composant;

import java.util.Vector;

/**
 *
 * @author Joël
 */
public class row {
    public Vector ListeFieldsValue= new Vector(10);
    public value searchValueByIdField(int idField)
    {
        int id = -1;
        value theValue = null;
        for (int j=0; j < this.ListeFieldsValue.size(); j++)
        {
             theValue = (value)this.ListeFieldsValue.elementAt(j);
             if (theValue.idField ==idField )
             {
                 return theValue;
             }
            
        }
        return theValue;
    }
  public boolean isRuleOk(value theValue, Vector ListeFieldsColumn)
  {
    for (int j=0; j < ListeFieldsColumn.size(); j++)
       {
            field theField = (field)ListeFieldsColumn.elementAt(j);      
            for (int i=0; i < theField.listRules.size(); i++)
            {
                item theItem = (item)theField.listRules.elementAt(i);
                if (theField.type.equals("Integer"))
                { 
                    if (theItem.type.equals("=")) 
                    {
                        if (Integer.parseInt(theItem.valeur)== theField.theValue.valueInt)
                            return true;
                        else
                            return false;
                  
                    }
                    else if (theItem.type.equals(">")) 
                    {
                        if (theField.theValue.valueInt > Integer.parseInt(theItem.valeur))
                            return true;
                        else
                            return false;
                  
                    } 
                    if (theField.theValue.valueInt < Integer.parseInt(theItem.valeur)) 
                    {
                        if (theField.theValue.valueInt < Integer.parseInt(theItem.valeur))
                            return true;
                        else
                            return false;
                  
                    }                    
                }
                else if (theField.type.equals("Float"))
                {

                }          
                else if (theField.type.equals("String"))
                {

                }
                else if (theField.type.equals("Date"))
                {

                }
          }
       }
      return true;
      
  }    
}
