//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis

package ometcommon;

import ometcommon.OMETConnection;

//DAO Object for Instructor tuples in the OMET Database
//Extends Pitt objects - Students and Instructors, both with names etc

public class Instructor extends Pitt{

    private final String table = "Instructors";
    
    private int fid;
    private Dept department;
    private int dept_id;

    public int getDept_id() {
        return dept_id;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public Dept getDepartment() {
        return department;
    }

    public void setDepartment(Dept department) {
        this.department = department;
    }
    
    public void setDept_id() {
        if(!(null == this.department)){
            this.dept_id = this.department.getDept_id();
        }
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }    

    public Instructor(String first_name, String last_name, String pitt_account,int fid, Dept department) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.pitt_account = pitt_account;
        this.fid = fid;
        this.department = department;
        this.dept_id = this.department.getDept_id();
    }
    
    public Instructor(String first_name, String last_name, String pitt_account,int fid, int dept_id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.pitt_account = pitt_account;
        this.fid = fid;
        this.department = department;
        this.dept_id = dept_id;
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
        String result = "(" + Integer.toString(this.fid) + ",'" + this.getFirst_name() + "','" + this.getLast_name() + "','" + this.getPitt_account() + "'," + Integer.toString(this.dept_id) + ")";
        return result;
    }
}
