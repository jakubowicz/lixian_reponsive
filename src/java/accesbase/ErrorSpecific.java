package accesbase; 

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
public class ErrorSpecific {
  public  String req="";
  public  int cause= 0;
  public  int nb= 0;
  public  String Class="";
  public  String Instance="";
  public  String Method="";
  public  String Detail="";
  public ErrorSpecific() {
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");

    System.out.println("req="+this.req);
    System.out.println("cause="+this.cause);
    System.out.println("Class="+this.Class);
    System.out.println("Instance="+this.Instance);
    System.out.println("Method="+this.Method);
    System.out.println("Detail="+this.Detail);

    System.out.println("==================================================");
    }

  public static void main(String[] args) {
    ErrorSpecific errorspecific = new ErrorSpecific();
  }
}
