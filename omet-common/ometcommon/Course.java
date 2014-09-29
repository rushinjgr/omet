//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis

package ometcommon;

import ometcommon.OMETConnection;

//DAO Object for Course tuples in the OMET Database

public class Course {
    
    private final String table = "Courses";
    
    private int cid;
    private String subject; //10
    private int course_number;
    private String name; //30
    private int enrollment;
    private int term;
    private int instructor_id;
    
    private Instructor instructor;
    
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCourse_number() {
        return course_number;
    }

    public void setCourse_number(int course_number) {
        this.course_number = course_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(int enrollment) {
        this.enrollment = enrollment;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id() {
        if(this.instructor != null){
            this.instructor_id = instructor.getFid();
        }
    }

    public void setInstructor_id(int instructor_id) {
        this.instructor_id = instructor_id;
    }
    
    

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
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
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(Integer.toString(this.cid));
        result.append(",'");
        result.append(this.subject);
        result.append("',");
        result.append(Integer.toString(this.course_number));
        result.append(",'");
        result.append(name);
        result.append("',");
        result.append(Integer.toString(this.enrollment));
        result.append(",");
        result.append(Integer.toString(this.term));
        result.append(",");
        result.append(Integer.toString(this.instructor_id));
        result.append(")");
        return result.toString();
    }

    public Course(int cid, String subject, int course_number, String name, int enrollment, int term, Instructor instructor) {
        this.cid = cid;
        this.subject = subject;
        this.course_number = course_number;
        this.name = name;
        this.enrollment = enrollment;
        this.term = term;
        this.instructor = instructor;
        this.instructor_id = this.instructor.getFid();
    }

    public Course(int cid, String subject, int course_number, String name, int enrollment, int term, int instructor_id) {
        this.cid = cid;
        this.subject = subject;
        this.course_number = course_number;
        this.name = name;
        this.enrollment = enrollment;
        this.term = term;
        this.instructor_id = instructor_id;
        this.instructor = instructor;
    }
    
    
}
