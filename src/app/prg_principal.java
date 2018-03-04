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

/**
 * @author imssbora
 */
public class prg_principal {
  public static void main(String[] args) throws SQLException {
     
//conexion to database*
      
        App app = new App();
        Connection conn = app.connect();
  
         
        
// lire les fichier +1
        File folder = new File("F:\\memoire\\Corpus\\1");
       
        HashMap<String,Integer> motCompte;
   
        for(File file : folder.listFiles())
        {            
           String  name;
                   name= file.getName();
            System.out.println(name);

           String request= "INSERT INTO tab_docc (nom) VALUES ('"+name+"')" ;
           Statement st1 = conn.createStatement();
           st1.execute(request);
           
          // motCompte=ManipulationFichier.getMotOccurence(file);
            
          //  if(!motCompte.isEmpty())
           {           
             //   traitement sur la base de données
           
            }
 /*               
        }
        
       
          
        //  lire les fichier -1
        folder = new File("F:\\memoire\\Corpus\\-1");
        
        for(File file : folder.listFiles())
       {            
            motCompte=ManipulationFichier.getMotOccurence(file);
            
            if(!motCompte.isEmpty())
            {           
                //traitement sur la base de données
           }
                
        }
                
    }
*/
          

    
}}
