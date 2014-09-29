//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis

package ometcommon;

import ometcommon.OMETConnection;

//DAO Object for Course tuples in the OMET Database

public class Subject {
    
    private final String table = ("Subjects");

    private String Subject;
    private int dept_id;
    private Dept department;
    
    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id() {
        if(this.department!=null){
            this.dept_id = department.getDept_id();
        }
    }

    public Dept getDepartment() {
        return department;
    }

    public void setDepartment(Dept department) {
        this.department = department;
    }    

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public Subject(String Subject, int dept_id) {
        this.Subject = Subject;
        this.dept_id = dept_id;
    }

    public Subject(String Subject, Dept department) {
        this.Subject = Subject;
        this.department = department;
        this.dept_id = this.department.getDept_id();
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
        String result = "('" + this.Subject + "'," + this.dept_id + ")";
        return result;
    }
}
