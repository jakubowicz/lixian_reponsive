/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Composant;
import java.util.Vector;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import accesbase.ErrorSpecific;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Joël
 */
public class field extends item {
    public int position=1;
    public int width=50;
    public int idDatasource=1;
    public int idLinkedField = -1;
    public Vector listeTypes = new Vector(10);
    public String str_rules = "";
    public Vector listRules = new Vector(10);
    public value theValue=null;
    public int isDelete=0;
       
  public field(int id){
      this.id = id;
  }
  
  public int getLastPosition(String nomBase,Connexion myCnx, Statement st){
      int nb=0;
  ResultSet rs;

  req="SELECT     MAX(position) AS nb FROM   Field WHERE  idDatasource = "+this.idDatasource;

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
       nb = rs.getInt("nb");
     }
   }
            catch (SQLException s) {s.getMessage();}
   return nb;
 }  
  
  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;
    int nb=0;
    
  
    req = "DELETE FROM Field  WHERE (id   = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  String mydesc="";
  if (this.desc != null)
    mydesc=this.desc.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
  else
    mydesc="";
  
  if (this.str_rules != null ) this.str_rules = this.str_rules.replaceAll("\r","").replaceAll("'","''");
  
    req = "INSERT INTO [Field]";
    req+= "           ([nom]";
    req+= "            ,[desc]";
    req+= "            ,[ordre]";    
    req+= "            ,[width]";
    req+= "            ,[type]";
    req+= "            ,[position]";
    req+= "            ,[idDatasource]";
    req+= "            ,[idLinkedField]";
    req+= "            ,[ListRules]";
    req+= "            ,[isDelete]";
    req+= ")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.nom+"'";
    req+=",";
    req+= "'"+mydesc+"'";
    req+=",";
    req+= "'"+this.ordre+"'";
    req+=",";
    req+= "'"+this.width+"'";
    req+=",";    
    req+= "'"+this.type+"'";
    req+=",";
    req+= "'"+this.position+"'";
    req+=",";
    req+= "'"+this.idDatasource+"'";
    req+=",";
    req+= "'"+this.idLinkedField+"'";    
    req+=",";
    req+= "'"+this.str_rules+"'";     
    req+=",";
    req+= "'"+this.isDelete+"'";     
    req+= "            )";

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause ==  -1) return myError;

      req="SELECT  TOP (1) id FROM   Field ORDER BY id DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("id");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  } 
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
    String description="";

    if (this.desc != null)
    {

      for (int j=0; j < this.desc.length();j++)
      {
        int c = (int)this.desc.charAt(j);
        if (c != 34)
          {
            description += this.desc.charAt(j);
          }
          else
          {
            description +="'";
          }
      }
      description = description.replaceAll("\r","").replaceAll("'","''");
     }
    
    
    if (this.str_rules != null ) this.str_rules = this.str_rules.replaceAll("\r","").replaceAll("'","''");

    req = "UPDATE [Field]";
    req+= "   SET [nom] = "+ "'"+this.nom+ "'";
    req+=",";    
    req+= "      [desc] = "+ "'"+description+ "'";
    req+=",";    
    req+= "      [ordre] = "+ "'"+this.ordre+ "'";
    req+=",";    
    req+= "      [position] = "+ "'"+this.position+ "'";
    req+=","; 
    req+= "      [width] = "+ "'"+this.width+ "'";
    req+=","; 
    req+= "      [type] = "+ "'"+this.type+ "'";
    req+=",";    
    req+= "      [idDatasource] = "+ "'"+this.idDatasource+ "'";
    req+=",";    
    req+= "      [idLinkedField] = "+ "'"+this.idLinkedField+ "'";    
    req+=",";    
    req+= "      [ListRules] = "+ "'"+this.str_rules+ "'";    
    req+=",";    
    req+= "      [isDelete] = "+ "'"+this.isDelete+ "'";     
  
    req+= "  WHERE id = "+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }      

  public void getListRules(){
     this.listRules.removeAllElements();
     String[] ligneRules =  this.str_rules.split(";");
     for (int i=0; i < ligneRules.length; i++)
     {
         String[] oneRules =  ligneRules[i].split("@");
         if (oneRules.length != 2) continue;
         item theItem = new item();
         theItem.type = oneRules[0];
         theItem.valeur = oneRules[1];
         this.listRules.addElement(theItem);
     }

  } 
  
  public boolean isRuleOk(){
      boolean ruleOk = true;
   
            for (int i=0; i < this.listRules.size(); i++)
            {
                item theItem = (item)this.listRules.elementAt(i);
                if (this.type.equals("Integer"))
                { 
                    if (theItem.type.equals("=")) 
                    {
                        if (Integer.parseInt(theItem.valeur)== this.theValue.valueInt)
                            ruleOk = true;
                        else
                            return false;
                  
                    }
                    else if (theItem.type.equals("!=")) 
                    {
                        if (Integer.parseInt(theItem.valeur)!= this.theValue.valueInt)
                            ruleOk = true;
                        else
                            return false;
                  
                    }                    
                    else if (theItem.type.equals(">")) 
                    {
                        if (this.theValue.valueInt > Integer.parseInt(theItem.valeur))
                            ruleOk = true;
                        else
                            return false;
                  
                    } 
                    else if (theItem.type.equals(">=")) 
                    {
                        if (this.theValue.valueInt >= Integer.parseInt(theItem.valeur))
                            ruleOk = true;
                        else
                            return false;
                  
                    }                     
                   else if (theItem.type.equals("<")) 
                    {
                        if (this.theValue.valueInt < Integer.parseInt(theItem.valeur))
                            ruleOk = true;
                        else
                            return false;
                  
                    }        
                   else if (theItem.type.equals("<=")) 
                    {
                        if (this.theValue.valueInt <= Integer.parseInt(theItem.valeur))
                            ruleOk = true;
                        else
                            return false;
                  
                    }                      
                }
                else if (this.type.equals("Float"))
                { 
                    if (theItem.type.equals("=")) 
                    {
                        if (Float.parseFloat(theItem.valeur)== this.theValue.valueFloat)
                            ruleOk = true;
                        else
                            return false;
                  
                    }
                    else if (theItem.type.equals("!=")) 
                    {
                        if (Float.parseFloat(theItem.valeur)!= this.theValue.valueFloat)
                            ruleOk = true;
                        else
                            return false;
                  
                    }                    
                    else if (theItem.type.equals(">")) 
                    {
                        if (Float.parseFloat(theItem.valeur)> this.theValue.valueFloat)
                            ruleOk = true;
                        else
                            return false;
                  
                    } 
                    else if (theItem.type.equals(">=")) 
                    {
                        if (Float.parseFloat(theItem.valeur)>= this.theValue.valueFloat)
                            ruleOk = true;
                        else
                            return false;
                  
                    }                     
                   else if (theItem.type.equals("<")) 
                    {
                        if (Float.parseFloat(theItem.valeur)< this.theValue.valueFloat)
                            ruleOk = true;
                        else
                            return false;
                  
                    }        
                   else if (theItem.type.equals("<=")) 
                    {
                        if (Float.parseFloat(theItem.valeur)<= this.theValue.valueFloat)
                            ruleOk = true;
                        else
                            return false;
                  
                    }                      
                }         
                else if (this.type.equals("String"))
                {
                    if (theItem.type.equals("=")) 
                    {
                        if (theItem.valeur.equals(this.theValue.valueString) )
                            ruleOk = true;
                        else
                            return false;
                  
                    } 
                    else if (theItem.type.equals("!=")) 
                    {
                        if (!theItem.valeur.equals(this.theValue.valueString) )
                            ruleOk = true;
                        else
                            return false;
                  
                    }    
                    else if (theItem.type.equals("LIKE")) 
                    {
                        if (this.theValue.valueString.indexOf(theItem.valeur) >= 0 )
                            ruleOk = true;
                        else
                            return false;
                  
                    }  
                    else if (theItem.type.equals("NOT LIKE")) 
                    {
                        if (this.theValue.valueString.indexOf(theItem.valeur) == -1 )
                            ruleOk = true;
                        else
                            return false;
                  
                    }                    

                }
                else if (this.type.equals("Date"))
                {
                    Date theItemDate = null;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        theItemDate = simpleDateFormat.parse(theItem.valeur);
                        //System.out.println(theItemDate);
                    } catch (Exception e) {e.printStackTrace();}
                    
                    if (theItem.type.equals("=")) 
                    {
                        try {
                            if (theItemDate.getTime()== this.theValue.valueDate.getTime())
                                ruleOk = true;
                            else
                                return false;
                            } catch (Exception e) {
                                ruleOk = true;
                            }                                         
                    }
                    else if (theItem.type.equals("!=")) 
                    {
                        try {
                            if (theItemDate.getTime()!= this.theValue.valueDate.getTime())
                                ruleOk = true;
                            else
                                return false;
                            } catch (Exception e) {
                                ruleOk = true;
                            } 
                  
                    }                    
                    else if (theItem.type.equals(">")) 
                    {
                        try {
                            if (this.theValue.valueDate.getTime() > theItemDate.getTime() )
                                ruleOk = true;
                            else
                                return false;
                            } catch (Exception e) {
                                ruleOk = true;
                            } 
                  
                    } 
                    else if (theItem.type.equals(">=")) 
                    {
                        try {
                            if (this.theValue.valueDate.getTime() >= theItemDate.getTime() )
                                ruleOk = true;
                            else
                                return false;
                            } catch (Exception e) {
                                ruleOk = true;
                            } 
                  
                    }                     
                   else if (theItem.type.equals("<")) 
                    {
                        try {
                            if (this.theValue.valueDate.getTime() < theItemDate.getTime() )
                                ruleOk = true;
                            else
                                return false;
                            } catch (Exception e) {
                                ruleOk = true;
                            } 
                  
                    }        
                   else if (theItem.type.equals("<=")) 
                    {
                        try {
                            if (this.theValue.valueDate.getTime() <= theItemDate.getTime() )
                                ruleOk = true;
                            else
                                return false;
                            } catch (Exception e) {
                                ruleOk = true;
                            } 
                  
                    }    
                }
          }
       
      return ruleOk;
      
  }    

  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    req = "SELECT    nom, type, position, width, [desc], ordre, idDatasource, idLinkedField, ListRules, isDelete FROM  Field WHERE id = "+this.id;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      this.nom = rs.getString("nom");
      this.type = rs.getString("type");
      this.position = rs.getInt("position");
      this.width = rs.getInt("width");           
      this.desc = rs.getString("desc");
      this.ordre = rs.getInt("ordre");
      this.idDatasource = rs.getInt("ordre");
      this.idLinkedField = rs.getInt("idLinkedField");
      this.str_rules = rs.getString("ListRules");
      if (this.str_rules == null) this.str_rules = "";
      this.isDelete = rs.getInt("isDelete");

      } catch (SQLException s) {s.getMessage(); }
  }  
  
  public void getListeSousTypeStByTypeSt(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;

   req="SELECT     Field.id, Field.nom";
   req+=" FROM         template INNER JOIN";
   req+="                       datasource ON template.id = datasource.idTemplate INNER JOIN";
   req+="                       Field ON datasource.id = Field.idDatasource INNER JOIN";
   req+="                       datasource AS datasource_1 ON template.id = datasource_1.idTemplate INNER JOIN";
   req+="                       Field AS Field_1 ON datasource_1.id = Field_1.idDatasource";
   req+=" WHERE     (datasource.sens = 'in') AND (Field.type = '"+this.type+"') AND Field_1.id = "+this.id;
   req+=" ORDER BY Field.nom";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();
      item theItem = new item(rs.getInt("id"));
      theItem.nom = rs.getString("nom");
      this.listeTypes.addElement(theItem);

      } catch (SQLException s) {s.getMessage(); }
  }    
    
}
