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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
    
    static void proc(String fileName,String lemma,String pos,float tff,int occurence) throws SQLException{
        
        if(globalCompteMot.keySet().contains(lemma))
        {
            globalCompteMot.replace(lemma,globalCompteMot.get(lemma)+1);
        }
        else
        {
            globalCompteMot.put(lemma,1);
        }
        
        String request= "INSERT INTO tab_mot (radical,pos,occurence,tf,filename) VALUES ('"+lemma+"','"+pos+"','"+occurence+"','"+tff+"','"+fileName+"')" ;
        App app = new App();
        Connection conn = app.connect();
        Statement st1 = conn.createStatement();
                        st1.execute(request);
                        
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
                    int occurence;
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
                                  //  System.out.println(file.getName()+":"+lemma+":"+pos+":"+tff+":"+occurence+":"+file.getParentFile().getName());
                                    proc(file.getName(),lemma, pos,tff,occurence);
                                } catch (SQLException ex) {
                                    Logger.getLogger(Pretraitement.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
            
                    });

                    tt.process(asList( mot ));
                   
                    
    }
    finally {
    tt.destroy();
      
                    
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
    try {
      
        App app = new App();
        Connection conn = app.connect();        
      //Création d'un objet Statement
      Statement state = conn.createStatement();
      
      //L'objet ResultSet contient le résultat de la requête SQL
      ResultSet result = state.executeQuery("SELECT * FROM tab_mot");
      //On récupère les MetaData
      ResultSetMetaData resultMeta = result.getMetaData();
         

      while(result.next()){ int i=1;        
       
         String lemma=result.getObject(1).toString()  ;i++;
         String pos=result.getObject(2).toString()  ;i++;
         int occurence=result.getInt(3)  ;i++;
         float tff=result.getFloat(4);
        
        
      
       if(globalCompteMot.keySet().contains(lemma))
        {
            globalCompteMot.replace(lemma,globalCompteMot.get(lemma)+1);
        }
        else
        {
            globalCompteMot.put(lemma,1);
        }
        
        
        float tfidf = (float) (tff * Math.log(globalCompteMot.get(lemma)/occurence));
        System.out.println(lemma+"occurence doc:"+globalCompteMot.get(lemma)+"\ttfidf\t"+tfidf);
        String request= "INSERT INTO tab_mot (tfidf) VALUES ('"+tfidf+"')WHERE(lemma=='"+lemma+"')" ;
       //
       // Connection conn = app.connect();
        Statement st1 = conn.createStatement();
                        st1.execute(request);
        //System.out.println("\n-lemma\n"+lemma+"\n-pos\n"+pos+"\n-occurence\n"+occurence+"..."+tff);
      }

      result.close();
      state.close();
         
    } catch (Exception e) {
      e.printStackTrace();
    }      
  
            
      
        
        //insérer le tfidf du mot par rapport au document courant
               
    }
}
