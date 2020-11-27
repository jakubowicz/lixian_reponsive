package Traces; 

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
public class LigneTrace {
  public String timeStamp;
  public String base;
  public String pageHtml;
  public String typeTrace;
  public String trace;
  public LigneTrace(String timeStamp, String base, String pageHtml, String typeTrace, String trace) {
    this.timeStamp = timeStamp;
    this.base = base;
    this.pageHtml = pageHtml;
    this.typeTrace = typeTrace;
    this.trace = trace;
  }

  public static void main(String[] args) {
    LigneTrace lignetrace = new LigneTrace("","","","","");
  }
}
