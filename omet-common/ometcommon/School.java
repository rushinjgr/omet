//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis

package ometcommon;

import ometcommon.OMETConnection;

//DAO Object for School tuples in the OMET Database

public class School{

    //the name of the table, for insert operations
    private final String table = ("SCHOOL");
    
    //attributes corresponding to the oracle table
    
    private int school_id;
    private String school_name;
    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }
    
    public int insert(OMETConnection connection) {
        String update = "INSERT INTO " + table + "VALUES" + this.values();
        int result;
        result = connection.update(update);
        return result;
    }
    
    public School parseResults(Object results) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String values(){
        String result = "(" + Integer.toString(this.school_id) + ",'" + this.school_name + "')";
        return result;
    }
}
