/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherappmultithread;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Vijjini
 */
public class TestFile {
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        //Creating instance to access dataCode class
        dataCode d = new dataCode();
            
        
            //Thread1 to download data from 5 days/3 hour forecast API (Houston)
            Thread Thread1 = new Thread( new Runnable() {
            @Override
            public void run() {
                    System.out.println("Thread 1 Running");
                    d.getData("http://api.openweathermap.org/data/2.5/forecast?q=Houston&appid=dfe38fb2e819afb357d57356978aa055");
                }
            });
            //Thread2 to download data from 16 days/daily forecast API (Houston)
            Thread Thread2 = new Thread( new Runnable() {
            @Override
            public void run() {
                    System.out.println("Thread 2 Running");
                    d.getData("http://samples.openweathermap.org/data/2.5/forecast/daily?id=524901&appid=dfe38fb2e819afb357d57356978aa055");
                }
            });
            //Thread3 to download weather maps
            Thread Thread3 = new Thread( new Runnable() {
            @Override
            public void run() {
                    System.out.println("Thread 3 Running");
                    d.getData("http://openweathermap.org/weathermap?basemap=map&cities=false&layer=windspeed&lat=30&lon=-20&zoom=3&appid=dfe38fb2e819afb357d57356978aa055");
                }
            });
            //Thread4 to open the latest weather map
            Thread Thread4 = new Thread( new Runnable() {
            @Override
            public void run() {
                    System.out.println("Thread 4 Running");
                    d.getData("http://openweathermap.org/weathermap?basemap=map&cities=true&layer=temperature&lat=29.7633&lon=-95.3633&zoom=12&appid=dfe38fb2e819afb357d57356978aa055");
                }
            });
           
            
           Thread1.start();
           try {Thread.sleep(10);} catch(Exception e){}
           Thread2.start();
           try {Thread.sleep(10);} catch(Exception e){}
           Thread3.start();
           try {Thread.sleep(10);} catch(Exception e){}
           Thread4.start();
           try {Thread.sleep(10);} catch(Exception e){}
          
           
           Thread1.join();
           Thread2.join();
           Thread3.join();
           Thread4.join();
           
          // System.out.println("No of threads = " + Thread.activeCount());
    }
    
    public static class dataCode
    {
        public synchronized void getData(String str)
        {
            try
            {
                DB db = (new MongoClient("localhost",27017)).getDB("weatherappdb");
                DBCollection col = db.getCollection("channel");
                URL url = new URL(str);

                URLConnection con = url.openConnection();
                InputStream stream = con.getInputStream();
                
                col.insert((DBObject) JSON.parse(stream.toString()));
                int i;
                while((i = stream.read()) != -1)
                {
                    System.out.print((char) i);
                }
                System.out.println();
                System.out.println("Thread Executed");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
