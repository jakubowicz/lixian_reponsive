package Anomalies; 

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
public class LigneAnomalie {
  public String objetAnomalie="";
  public String criticiteAnomalie="";
  public String nomMembre="";
  public String desc2TypeEtat="";
  public String Etat="";
  public String Cout="";
  public String reponse="";
  
  public String creationAnomalie="";
  public String dateReponse="";

  public LigneAnomalie(String objetAnomalie,String criticiteAnomalie,String nomMembre,String desc2TypeEtat,String Etat,String Cout) {
    this.objetAnomalie = objetAnomalie;
    this.criticiteAnomalie = criticiteAnomalie;
    this.nomMembre = nomMembre;
    this.desc2TypeEtat = desc2TypeEtat;
    this.Etat = Etat;
    this.Cout = Cout;
  }

  public static void main(String[] args) {
    LigneAnomalie ligneanomalie = new LigneAnomalie("","","","","","");
  }
}
