package app;

import java.io.File;
import java.util.HashMap;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import PostgreSQLJDBC.*;
import static java.awt.Event.INSERT;
import java.io.IOException;
import org.annolab.tt4j.TreeTaggerException;

/** 
 * @author imssbora
 */
public class MainClass {
    
 protected static HashMap<String,Integer> globalCompteMot; 
    
  public static void main(String[] args) throws SQLException, IOException, TreeTaggerException {
     
        globalCompteMot=new HashMap<>();
        
        File folder = new File("F:\\memoire\\Corpus\\1");
        
        //Prétraitements & Calul TF/DOC
        for(File file : folder.listFiles())
        {                       
         //  Pretraitement.getMotOccurence(file);  
        }
        
        //Calcul TFIDF/DOC
        Pretraitement.calculTFIDF();
        
        
   /*     
       
          
        //  lire les fichier -1
        folder = new File("F:\\memoire\\Corpus\\-1");
        
        for(File file : folder.listFiles())
       {            
            motCompte=Pretraitement.getMotOccurence(file);
            
            if(!motCompte.isEmpty())
            {           
                //traitement sur la base de données
           }
                
        }
                
    }
*/
          

   
}}
