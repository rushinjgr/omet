//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis

package ometcommon;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//this object represents a connection to the OMET oracle database
//the reason i made this a class was to eliminate some of the JDBC code from the body of my programs

public class OMETConnection {
    
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private String query;
    private String username, password;
    
    //performs a read only query on the database and returns the resultset
    //readonly: whether or not the concurrency is set to read only or updatable
    //holdcursors: whehther to hold the cursors after commit (true) or to close them (false)
    public ResultSet query(String query){
        int concurrency = ResultSet.CONCUR_READ_ONLY;
        int type = ResultSet.TYPE_FORWARD_ONLY; //default
        int holdability = ResultSet.CLOSE_CURSORS_AT_COMMIT;
        try {
            statement = connection.createStatement(type, concurrency, holdability);
            resultSet = statement.executeQuery(query);
            return resultSet;        
        } catch (SQLException ex) {
            //Logger.getLogger(OMETConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    //performs a writable update or query on the database and returns the int result code
    public int update(String update){
        int result;
        int concurrency = ResultSet.CONCUR_UPDATABLE;
        int type = ResultSet.TYPE_FORWARD_ONLY; //default
        int holdability = ResultSet.CLOSE_CURSORS_AT_COMMIT;
        try {
            statement = connection.createStatement(type, concurrency, holdability);
            //System.out.println("update: "+update);
            result = statement.executeUpdate(update);
            return result;
        } catch (SQLException ex) {
            //Logger.getLogger(OMETConnection.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    //closes the connection to the database
    public void close(){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OMETConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //constructor that opens connection to the database
    public OMETConnection(String nusername, String npasswd){
        this.username = nusername;
        this.password = npasswd;
        try{
            DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
            String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass"; 
            connection = DriverManager.getConnection(url, username, password); 
        }
        catch(Exception Ex){
            //System.err.println(Ex.getStackTrace());
        }
    }
    
    //performs a commit on the database
    public int commit(){
        int result;
        int concurrency = ResultSet.CONCUR_UPDATABLE;
        int type = ResultSet.TYPE_FORWARD_ONLY; //default
        int holdability = ResultSet.CLOSE_CURSORS_AT_COMMIT;
        try {
            statement = connection.createStatement(type, concurrency, holdability);
            result = statement.executeUpdate("COMMIT");
            return result;
        } catch (SQLException ex) {
            //Logger.getLogger(OMETConnection.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
}
    