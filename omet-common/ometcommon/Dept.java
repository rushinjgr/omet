//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis

package ometcommon;

import ometcommon.OMETConnection;

//DAO Object for Department tuples in the OMET Database

public class Dept {
    
    private final String table = ("Dept");
    
    private int dept_id;
    private String dept_name; //30
    private School school;
    private int school_id;

    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public int getSchool_id() {
        return school_id;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
    
    public void setSchool_id(){
        if(!(null == this.school)){
            this.school_id = this.school.getSchool_id();
        }
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }
    
    public Dept(int dept_id, String dept_name, int school_id) {
        this.dept_id = dept_id;
        this.dept_name = dept_name;
        this.school_id = school_id;
    }

    public Dept(int dept_id, String dept_name, School school) {
        this.dept_id = dept_id;
        this.dept_name = dept_name;
        this.school = school;
        this.school_id = this.school.getSchool_id();
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
        String result = "(" + Integer.toString(this.dept_id) + ",'" + this.dept_name + "'," + this.school_id + ")";
        return result;
    }
}
