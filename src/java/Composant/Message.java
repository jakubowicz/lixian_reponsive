/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Composant;

import org.apache.log4j.Logger;
import General.Utils;

public class Message {

      Logger log = Logger.getLogger("Composant.Message");

      private String msg;
      private String source;
      private String client;
      private String login;

       public Message(String source) {
         this.source = source.substring("org.apache.jsp.".length()).replaceAll("005f","").replaceAll("_002d","-").replaceAll("_jsp",".jsp");  
       }
       public Message(String source, String client, String login) {
        this.source = source;
        this.client =client;
        this.login =login;
       }       
      public void setMessageDebug(String msg) {
            this.msg = msg;
            log.debug(this.client + " :: "+this.login + " :: "+this.source + " :: "+msg);
      }    
      public void setMessageInfo(String msg) {
            this.msg = msg;
            log.info(this.client + " :: "+this.login + " :: "+this.source + " :: "+msg);
      }  
      public void setMessageWarn(String msg) {
            this.msg = msg;
            log.warn(this.client + " :: "+this.login + " :: "+this.source + " :: "+msg);
      }  
      public void setMessageError(String msg) {
            this.msg = msg;
            log.error(this.client + " :: "+this.login + " :: "+this.source + " :: "+msg);
      }   
      public void setMessageFatal(String msg) {
            this.msg = msg;
            log.fatal(this.client + " :: "+this.login + " :: "+this.source + " :: "+msg);
      }        
      public void setMessage(String msg) {
            this.msg = msg;

            log.debug("debug: "+msg);
            log.info("info: "+msg);
            log.warn("warn: "+msg);
            log.error("error: "+msg);
            log.fatal("fatal: "+msg);
      }

      public String getMessage() {

            log.debug("debug: "+this.msg);
            log.info("info: "+this.msg);
            log.warn("warn: "+this.msg);
            log.error("error: "+this.msg);
            log.fatal("fatal: "+this.msg);

            return msg;
      }
} 
