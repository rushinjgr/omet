//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis
package ometcommon;

import ometcommon.OMETConnection;

//DAO Object for Student tuples in the OMET Database
//Extends Pitt objects - Students and Instructors, both with names etc

public class Student extends Pitt{
    
    private final String table = "Students";
    
    private int sid;
    private Subject majorSubject;
    private String major;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Subject getMajorSubject() {
        return majorSubject;
    }

    public void setMajorSubject(Subject majorSubject) {
        this.majorSubject = majorSubject;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        if(majorSubject != null){
            this.major = majorSubject.getSubject();
        }
    }
    
    public Student(String first_name, String last_name, String pitt_account,int sid, Subject major) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.pitt_account = pitt_account;
        this.sid = sid;
        this.majorSubject = major;
        this.major = this.majorSubject.getSubject();
    }
    
    public Student(String first_name, String last_name, String pitt_account,int sid, String major) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.pitt_account = pitt_account;
        this.sid = sid;
        this.major = major;
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
        String result = "(" + Integer.toString(this.sid) + ",'" + this.getFirst_name() + "','" + this.getLast_name() + "','" + this.getPitt_account() + "','" + this.major + "')";
        return result;
    }
    
}
