/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ST;
import Composant.item;
import accesbase.Connexion;
import accesbase.ErrorSpecific;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author Joël
 */
public class typeInterface_item extends item {
    public String couleur = "000000";
    public int style = -1;
    public float epaisseur = 1;
    public Vector ListeImplementations = new Vector(10);
    public String LigneImplementation ="";
    private boolean byId=true;
    public static Vector ListeTypeInterface_item = new Vector(10);
    
  public typeInterface_item(int id) {
    this.id = id;
  } 
  
  public typeInterface_item(String nom) {
    this.nom = nom;
    byId=false;
  }   
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
     if (byId)
     {
      req = "SELECT     typeInterfaces.id, typeInterfaces.nom, typeInterfaces.description, typeInterfaces.ordre, typeInterfaces.Type, typeInterfaces.couleur, typeInterfaces.style, typeInterfaces.epaisseur,  typeInterfaces.style ";
      req += " FROM         typeInterfaces  LEFT OUTER JOIN";
      req += "                       typeStyles ON typeInterfaces.style = typeStyles.id";
      req += " WHERE     typeInterfaces.id = " + this.id;
     }
     else
     {
      req = "SELECT     typeInterfaces.id, typeInterfaces.nom, typeInterfaces.description, typeInterfaces.ordre, typeInterfaces.Type, typeInterfaces.couleur, typeInterfaces.style, typeInterfaces.epaisseur,  typeInterfaces.style";
      req += " FROM         typeInterfaces  LEFT OUTER JOIN";
      req += "                       typeStyles ON typeInterfaces.style = typeStyles.id";
      req += " WHERE     typeInterfaces.nom = '" + this.nom + "'";
     }         

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       this.id = rs.getInt("id");
       this.nom = rs.getString("nom");
       this.desc = rs.getString("description");
       this.ordre = rs.getInt("ordre");
       this.type = rs.getString("Type");
       this.couleur = rs.getString("couleur");
       if (this.couleur == null) this.couleur = "000000";
       this.style = rs.getInt("style");
       this.epaisseur = rs.getFloat("epaisseur");
       this.style = rs.getInt("style");

       } catch (SQLException s) {s.getMessage(); }
  } 
  
 public static void getListeTypeInterface_item(String nomBase,Connexion myCnx, Statement st){

   String req="SELECT     id, nom, description, ordre, Type, couleur, style, epaisseur FROM  typeInterfaces ORDER BY nom";
   ListeTypeInterface_item.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   try {
      while (rs.next()) {
        typeInterface_item theTypeInterface_item = new typeInterface_item(rs.getInt("id"));
        theTypeInterface_item.nom = rs.getString("nom");
        theTypeInterface_item.desc = rs.getString("description");
        theTypeInterface_item.type = rs.getString("Type");
        theTypeInterface_item.couleur = rs.getString("couleur");
        theTypeInterface_item.style = rs.getInt("style");
        theTypeInterface_item.epaisseur = rs.getFloat("epaisseur");

        ListeTypeInterface_item.addElement(theTypeInterface_item);
      }
       } catch (SQLException s) {s.getMessage();}

 }  
 public static void getListeTypeInterface_item(String nomBase,Connexion myCnx, Statement st, String type){

   String req="SELECT     id, nom, description, ordre, Type, couleur, style, epaisseur FROM  typeInterfaces ";
   req+=" WHERE Type = '"+ type + "'";
   req+=" ORDER BY nom";
   ListeTypeInterface_item.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   try {
      while (rs.next()) {
        typeInterface_item theTypeInterface_item = new typeInterface_item(rs.getInt("id"));
        theTypeInterface_item.nom = rs.getString("nom");
        theTypeInterface_item.desc = rs.getString("description");
        theTypeInterface_item.type = rs.getString("Type");
        theTypeInterface_item.couleur = rs.getString("couleur");
        theTypeInterface_item.style = rs.getInt("style");
        theTypeInterface_item.epaisseur = rs.getFloat("epaisseur");

        ListeTypeInterface_item.addElement(theTypeInterface_item);
      }
       } catch (SQLException s) {s.getMessage();}

 } 
  
  public void getListeImplementations(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
       req = "SELECT     idImplementation, nomImplementation, descImplementation, typeImplementation";
       req += " FROM         Implementation order by nomImplementation";

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     
    try {
        while (rs.next()) {  
            item theItem = new item();
            theItem.id = rs.getInt("idImplementation");
            theItem.nom = rs.getString("nomImplementation");  
            theItem.desc = rs.getString("typeImplementation");  
            this.ListeImplementations.addElement(theItem);
    }

       } 
  catch (SQLException s) {
    s.getMessage();
  }    
  } 
  
 public String getisChecked(item theItem){
     
    String[] LigneImplementation = theItem.desc.split(";");

    for (int i=0; i < LigneImplementation.length; i++)
        {
            if (LigneImplementation[i] .equals(this.nom))
                {
                   return "checked";
                }
        }
  
  return "";
}  
  
    public void getListeImplementations2(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
       req = "SELECT     idImplementation, nomImplementation, descImplementation, typeImplementation";
       req += " FROM         Implementation";

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     item theItem = new item();
    try {
        while (rs.next()) {  
            
            theItem.id = rs.getInt("idImplementation");
            theItem.nom = rs.getString("nomImplementation");           
            String typeImplementation = rs.getString("typeImplementation");
            String[] LigneImplementation = typeImplementation.split(";");
            for (int i=0; i < LigneImplementation.length; i++)
            {
                if (LigneImplementation[i] .equals(this.nom))
                {
                    this.ListeImplementations.addElement(theItem);
                }
            }
    }

       } 
  catch (SQLException s) {
    s.getMessage();
  }    
  }
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
  
    
    req = "UPDATE [typeInterfaces]";
    req+= "   SET ";
    req+= " nom = "+ "'"+this.nom+ "'" + ",";
    req+= " description = "+ "'"+this.desc+ "'" + ",";
    req+= " ordre = "+ "'"+this.ordre+ "'" + ",";
    req+= " Type = "+ "'"+this.type+ "'" + ",";
    req+= " couleur = "+ "'"+this.couleur+ "'" + ",";
    req+= " style = "+ "'"+this.style+ "'" + ",";
    req+= " epaisseur = "+ "'"+this.epaisseur+ "'" + "";

    req+= "  WHERE id = "+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }
  
  private boolean isFound(String id, String[] tabLigne){
      boolean trouve = false;
                
                for (int j=0; j < tabLigne.length; j++)
                {
                   // est ce que l'implementation est dans la liste des implementations du type d'interface ? ?
                    if (id.equals(tabLigne[j]))
                    {
                       trouve = true;
                    }
                  
                }      
      return trouve;
  }
  
  public ErrorSpecific bd_updateImplementations(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();

     String[] tabLigneImplementation = this.LigneImplementation.split(";"); // liste des id d'implementation dans lequel l'interface doit apparaître
     // Pour toutes les implementations
     for (int i = 0; i < this.ListeImplementations.size(); i++) 
     {
        item theImplementation = (item)this.ListeImplementations.elementAt(i);
                //System.out.println("KO Interface::id="+theImplementation.id + "/" +"nom="+theImplementation.nom+ "/" +"desc="+theImplementation.desc);
                String[] tabLigneInterface = theImplementation.desc.split(";");    
                String listeInterfaces="";
                if (isFound(""+theImplementation.id, tabLigneImplementation)) 
                    {
                        // l'interface doit être dans l'implementation

                        // est ce qu'ell y est ?
                            // Oui: ne rien faire
                            // Non: la rajouter
                            if (isFound(this.nom, tabLigneInterface))
                                {
                                    // // Oui: ne rien faire
                                    System.out.println("l'interface: "+this.nom + " est référenceée dans l'implementation: "+theImplementation.nom+ "(" +theImplementation.desc + ")" + " et doit l'être: "+theImplementation.id + "--> ne rien faire");  
                                    
                                }
                            else
                                {
                                    // Non: la rajouter
                                    listeInterfaces = theImplementation.desc  + this.nom + ";";
                                    System.out.println("l'interface: "+this.nom + " n'est pas référenceée dans l'implementation: "+theImplementation.nom+"(" +theImplementation.desc + ")" + " et doit l'être: "+theImplementation.id + "--> il faut la rajouter: "+listeInterfaces); 
                                    req = "UPDATE [Implementation]";
                                    req+= "   SET ";
                                    req+= " typeImplementation = "+ "'"+listeInterfaces+ "'" + "";
                                    req+= "  WHERE idImplementation = "+theImplementation.id;
                                    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);                                    
                                }                         
                    
                    }
                else
                    {
                        // l'interface ne doit pas être dans l'implementation                      

                        // est ce qu'ell y est ?
                            
                             
                            if (isFound(this.nom, tabLigneInterface))
                                {
                                    // Oui: il faut l'enlever
                                    for (int x=0; x < tabLigneInterface.length; x++)
                                    {
                                        if (!tabLigneInterface[x].equals(this.nom))
                                        {
                                          listeInterfaces += tabLigneInterface[x] + ";";  
                                        }
                                        
                                    }
                                    System.out.println("l'interface: "+this.nom + " est référencée dans l'implementation: "+theImplementation.nom+"(" +theImplementation.desc + ")" + " et ne doit pas l'être: "+theImplementation.id + "--> la supprimer: "+listeInterfaces);    
                                    req = "UPDATE [Implementation]";
                                    req+= "   SET ";
                                    req+= " typeImplementation = "+ "'"+listeInterfaces+ "'" + "";
                                    req+= "  WHERE idImplementation = "+theImplementation.id;
                                    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);                                     
                                }
                            else
                                {
                                    // Non: ne rien faire
                                    System.out.println("l'interface: "+this.nom + " n'est pas référeneée dans l'implementation: "+theImplementation.nom+"(" +theImplementation.desc + ")" + " et ne doit pas l'être: "+theImplementation.id + "--> ne rien faire");                               
                                }                          
                       
                   }                 

                                          
                   }

    return myError;
  }  

  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;
    int nb=0;
    String ListeSt = "";
    /*
    
    req = "SELECT     St.nomSt";
    req+= " FROM         St INNER JOIN";
    req+= "                       VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req+= "                       TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi";
    req+= " WHERE     TypeSi.idTypeSi ="+ this.id;
    req+= " ORDER BY St.nomSt";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  
  try {
    while (rs.next()) {
      if (!ListeSt.equals("")) ListeSt += ", ";else ListeSt += " Produits: ";
      ListeSt += rs.getString(1) ;
    }
  }
  catch (SQLException s) {
    s.getMessage();
  }
  */
  
  if (!ListeSt.equals("")) {
    myError.Detail = ListeSt;
    myError.cause = -2;
    return myError;
  }  
  
    req = "DELETE FROM typeInterfaces WHERE (id   = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  String mydesc="";
  if (this.desc != null)
    mydesc=this.desc.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
  else
    mydesc="";
  
    req = "INSERT INTO [typeInterfaces]";
    req+= "           (";
    req+= "            [nom]";
    req+= "            ,[description]";
    req+= "            ,[ordre]";
    req+= "            ,[Type]";
    req+= "            ,[couleur]";
    req+= "            ,[style]";
    req+= "            ,[epaisseur]";

    req+= "           )";    
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.nom+"'" + ",";
    req+= "'"+this.desc+"'" + ",";
    req+= "'"+this.ordre+"'" + ",";
    req+= "'"+this.type+"'" + ",";
    req+= "'"+this.couleur+"'" + ",";
    req+= "'"+this.style+"'" + ",";
    req+= "'"+this.epaisseur+"'" + "";

    req+= "            )";

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause ==  -1) return myError;

      req="SELECT  TOP (1) id FROM   typeInterfaces ORDER BY id DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("id");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  }   
    
}
