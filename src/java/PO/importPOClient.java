package PO; 

import java.util.Calendar;

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
public class importPOClient {
  static int Annee;
  public importPOClient() {
  }

  public static void main(String[] args) {
    if (args.length > 0)
    {
      Annee = Integer.parseInt(args[0]);
    }
    else
    {
      Calendar calendar = Calendar.getInstance();
      Annee = calendar.get(Calendar.YEAR);
    }
    importPO thePOClient = new importPO();
    thePOClient.xl_read_PO_Client(Annee);
    thePOClient.xl_write_PO_Client(Annee);


  }
}
