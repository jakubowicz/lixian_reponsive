package OM; 

import General.Utils;
import ST.ST;
import accesbase.Connexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>Titre : </p>
 *
 * <p>Description : </p>
 *
 * <p>Copyright : Copyright (c) 2004</p>
 *
 * <p>Soci�t� : </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Relation {
  public int id = -1;
  public OM origineOM;
  public OM destinationOM ;
  public String sens;
  public String type;
  public String cardinaliteOrigine ;
  public String cardinaliteDestination;
  public String description;
  public String param_edges;
  private String req="";
  public int idSens = -1;
  
  public int idTypeRelation = -1;


  public Relation(OM origineOM, OM destinationOM, String sens, String type, String cardinaliteOrigine, String cardinaliteDestination, String description ) {
    this.origineOM=origineOM;
    this.destinationOM=destinationOM;
    this.sens=sens;
    this.type=type;
    this.cardinaliteOrigine=cardinaliteOrigine;
    this.cardinaliteDestination=cardinaliteDestination;
    this.description=description;

    this.param_edges = origineOM.nomObjetMetier+"#"+destinationOM.nomObjetMetier+"!"+description+"!"+sens+"!"+"normal"+",";

  }
  
  public Relation(int id ) {
      this.id = id;
  }  
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
     req = "SELECT     sens, type, origineOM, cardinaliteOrigine, destinationOM, cardinaliteDestination, description, idTypeRelation";
     req += " FROM         RelationsOM";
     req += " WHERE     id = "+this.id;

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       this.sens = rs.getString("sens");
       this.type = rs.getString("type");
       this.origineOM = new OM(rs.getInt("origineOM"));
       this.cardinaliteOrigine = rs.getString("cardinaliteOrigine");
       this.destinationOM = new OM(rs.getInt("destinationOM"));
       this.cardinaliteDestination = rs.getString("cardinaliteDestination");       
       this.description = rs.getString("description");
       if (this.description == null) this.description = "";
       this.idTypeRelation = rs.getInt("idTypeRelation");
     }
     catch (Exception s) {} 
  
  } 

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    this.origineOM.dump();
    this.destinationOM.dump();
    System.out.println("sens="+this.sens);
    System.out.println("type="+this.type);
    System.out.println("cardinaliteOrigine="+this.cardinaliteOrigine);
    System.out.println("cardinaliteDestination="+this.cardinaliteDestination);
    System.out.println("cardinaliteDestination="+this.cardinaliteDestination);
    System.out.println("description="+this.description);
    System.out.println("==================================================");
  }
}
