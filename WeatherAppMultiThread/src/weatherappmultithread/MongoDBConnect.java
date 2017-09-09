/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherappmultithread;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

/**
 *
 * @author Vijjini
 */
public class MongoDBConnect {
    public static void main(String[] args) throws UnknownHostException, IOException
    {
        DB db = (new MongoClient("localhost",27017)).getDB("weatherappdb");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        boolean flag = false;
        while(!flag)
        {
            flag = authenticate(db, buffer);
        }
    }
    private static boolean authenticate(DB db, BufferedReader buffer) throws IOException
    {
        boolean flag = true;
        System.out.println("User : ");
        String user = buffer.readLine();
        System.out.println("Password : ");
        String password = buffer.readLine();
        
        DBCollection col = db.getCollection("channel");
        String cmd = null;
        while(true)
        {
            System.out.println("What do you want to do?");
            cmd = buffer.readLine();
            if(cmd.equals("exit")) break;
            else if (cmd.equals("findAll")) findAll(col);
            else if (cmd.equals("insertJSON"))
                insertJSON(buffer,col);
        }
        return flag;
    }
    
    private static void findAll(DBCollection col)
    {
        DBCursor cursor = col.find();
        while(cursor.hasNext())
        {
            System.out.println(cursor.next());
        }
    }
    private static void insertJSON(BufferedReader buffer, DBCollection col) throws IOException
    {
        System.out.println("JSON");
        col.insert((DBObject) JSON.parse(buffer.readLine()));
    }
}
