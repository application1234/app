// Java Program to illustrate reading from text file
// as string in Java
package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import PostgreSQLJDBC.*;
import java.sql.Connection;
import java.io.File;
import java.sql.Statement;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;


public class Pretraitement extends MainClass
{
    static float comp;
    
    static void proc(Object file, String lemma, String pos,float tf,int occurence) throws SQLException{
        
        if(globalCompteMot.keySet().contains(lemma))
        {
            globalCompteMot.replace(lemma,globalCompteMot.get(lemma)+1);
        }
        else
        {
            globalCompteMot.put(lemma,1);
        }
        
      //  System.out.println(param1 + "\t" + param2 + "\t" + param3);
//        String request= "INSERT INTO tab_mot (mot,pos,lemma,tf,occurence) VALUES ('"+param1+"','"+param2+"','"+param3+"',"+param4+","+param5+")" ;
//        App app = new App();
//        Connection conn = app.connect();
//        Statement st1 = conn.createStatement();
//                        st1.execute(request);
                        
    }
    
    static void getMotOccurence(File file) throws SQLException, IOException, TreeTaggerException
    {
        HashMap<String,Integer> motCompte = null;
        
        ArrayList stopwords = new ArrayList(Arrays.asList("a","is","yes","no","than","am","its","we","all","as","not","here","without","they","be","are","by","off","do","while","wasn","those","your","when","can","why","who","have","with","also","it","was","her","us","you","didn","there","their","an","will","had","has","on","and","his","this","is","where","were","which","isn","did","ve","at","in","me","the","but","how","t","my","that","he","ll","about","he","what","or","of","then","next","after","for","before","to","i" ));
       
        try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                motCompte = new HashMap<>();
               
                String line = null;

                while ((line = bufferedReader.readLine()) != null) 
                {
                    List<String> mots = Arrays.asList(line.split("\\W+"));
                    comp=0;
                    Iterator<String> it = mots.iterator();
                    while(it.hasNext())
                    {
                        String mot = it.next().toLowerCase();
                       
                      

//stopwords function
                   // String request= "SELECT*FROM stopwords" ;
                   // Statement st1 = conn.createStatement();
                   // st1.execute(request);   
               // System.out.println(mot);   
                                          
                    if(!stopwords.contains(mot))
                    {              
                            if(motCompte.keySet().contains(mot))
                            {
                                motCompte.replace(mot,motCompte.get(mot)+1);
                            }
                            else
                            {
                                motCompte.put(mot,1);
                            }
                    }
       
                    Pretraitement.comp++;
                           
                                                 
  
    }}
                
    System.out.println("nbr des mots"+comp);
                
                
//VERIFICATION                
            System.out.println("VERIFICATION : "+file.getName());
                
                for(String mot : motCompte.keySet())
               {      
                    int occurence,idd=0;
                    float tff;
                        occurence =motCompte.get(mot);
                        tff= motCompte.get(mot)/Pretraitement.comp;
                        
                        System.setProperty("treetagger.home", "C:\\Program Files\\TreeTagger");
                        TreeTaggerWrapper tt = new TreeTaggerWrapper<String>();
            try {
                    tt.setModel("C:\\Program Files\\TreeTagger\\lib\\english-utf8.par");
                    String[] argum = {"-sgml","-token","-lemma"};
                    tt.setArguments(argum);
                    
                    tt.setHandler(new TokenHandler<String>(){
                
                        @Override
                        public void token(String token, String pos, String lemma)
                        {
                            if(!lemma.equals("<unknown>"))
                            {
                               try {
                                    System.out.println(file.getName()+":"+lemma+":"+pos+":"+tff+":"+occurence+":"+file.getParentFile().getName());
                                    proc(file.getName(), pos,lemma,tff,occurence);
                                } catch (SQLException ex) {
                                    Logger.getLogger(Pretraitement.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
            
                    });

                    tt.process(asList( mot ));
                    /* String request= "INSERT INTO tab_mot (tf,id,occurence) VALUES ("+tff+","+idd+","+occurence+")" ;
                                     Statement st1 = conn.createStatement();
                                     st1.execute(request);*/
                    idd++;
    }
    finally {
    tt.destroy();
     
      // System.out.println(tokenn);
      
                    
    //inserer les fichier dans la bdd
    /*  String request= "INSERT INTO tab_mot (mot,occurence,tf) VALUES ('"+mot+"',occurence,tf)" ;
                        Statement st1 = conn.createStatement();
                        st1.execute(request);*/
    }
            
                    
                }

                bufferedReader.close();
            
        } catch (IOException e) 
        {
            System.err.println(e.toString());
        }
               
}
    
    static void calculTFIDF()
    {
        //parcourir la table dans la base de données (ligne par ligne) :
                    //-document -lemma -tf -occurence
            
        //Float tfidf = tf * Math.log(globalMotCompte.get(lemma)/occurence);
        
        //insérer le tfidf du mot par rapport au document courant
               
    }
}
