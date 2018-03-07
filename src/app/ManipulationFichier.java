// Java Program to illustrate reading from text file
// as string in Java
package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;
import PostgreSQLJDBC.*;
import java.sql.Connection;


class ManipulationFichier 
{
    static HashMap<String,Integer> getMotOccurence(File file) throws SQLException
    {
        HashMap<String,Integer> motCompte = null;
        String stopwords[]  = {"yes","no","than","am","its","we","all","as","not","here","without","they","be","are","by","off","do","while","wasn","those","your","when","can","why","who","have","with","also","it","was","her","us","you","didn","there","their","an","will","had","has","on","and","his","this","is","where","were","which","isn","did","ve","at","in","me","the","but","how","t","my","that","he","ll","about","he","what","or","of","then","next","after","for","before","to","i" };
//conexion to database
        App app = new App();
        Connection conn = app.connect();
        
        try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                motCompte = new HashMap<>();
               
                String line = null;

                while ((line = bufferedReader.readLine()) != null) 
                {
                    List<String> mots = Arrays.asList(line.split("\\W+"));
                                        
                    Iterator<String> it = mots.iterator();
                    while(it.hasNext())
                    {
                        String mot = it.next().toLowerCase();
                        
                        

//stopwords function
                   // String request= "SELECT*FROM stopwords" ;
                   // Statement st1 = conn.createStatement();
                   // st1.execute(request);   
               // System.out.println(mot);   
              
             
                       
                   // System.out.println(mot+": "/*+motCompte.get(mot)*/);
                
                        String request= "INSERT INTO tab_mot (mot) VALUES ('"+mot+"')" ;
                        Statement st1 = conn.createStatement();
                        st1.execute(request);
                            //Traitements sur le mot
                            //
                            //
                            //
                            //
                            if(motCompte.keySet().contains(mot))
                            {
                                motCompte.replace(mot,motCompte.get(mot)+1);
                            }
                            else
                            {
                                motCompte.put(mot,1);
                            }
                              for(int j=0;j<stopwords.length;j++){
               //System.out.println(stopwords[j]);
                if(mot.equalsIgnoreCase(stopwords[j]))
                            {
                               motCompte.remove(stopwords[j]);
                                   
                             
                               // motCompte.remove(motCompte.get(mot));
                               
                            }
                            
                            
                            
                          
               }//System.out.println(motCompte);

                        }

                }
                
                //VERIFICATION                
                System.out.println("VERIFICATION : "+file.getName());
                
                for(String mot : motCompte.keySet())
               {
               
                       
                    System.out.println(mot+": "+motCompte.get(mot));
                }

                bufferedReader.close();
            
        } catch (IOException e) 
        {
            System.err.println(e.toString());
        }
        
        return motCompte;
        
}
}