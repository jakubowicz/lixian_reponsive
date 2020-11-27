package accesbase;
 
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
public class transaction {
  public String nom;
  private String req;

  public transaction(String nom) {
    this.nom = nom;
  }

  public void begin(String nomBase,Connexion myCnx, Statement st){
    req = "BEGIN TRANSACTION "+this.nom;
    myCnx.ExecReq(st, nomBase, req);
  }
  public void commit(String nomBase,Connexion myCnx, Statement st){
    req = "COMMIT TRANSACTION "+this.nom;
    myCnx.ExecReq(st, nomBase, req);
  }
  public void rollback(String nomBase,Connexion myCnx, Statement st){
    req = "ROLLBACK TRANSACTION "+this.nom;
    myCnx.ExecReq(st, nomBase, req);
  }

  public static void main(String[] args) {
    transaction transaction = new transaction("");
  }
}
