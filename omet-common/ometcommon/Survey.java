//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis
package ometcommon;

import ometcommon.OMETConnection;

//DAO Object for Survey tuples in the OMET Database

public class Survey {
    
    private int survey_id;
    private int cid;
    private int num_submitted;
    private int sum_q1;
    private int sum_q2;
    private int sum_q3;
    private int sum_q4;
    
    private Course course;
    
    private final String table = "Surveys";

    public int getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(int survey_id) {
        this.survey_id = survey_id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid() {
        if (this.course != null){
            this.cid = this.course.getCid();
        }
    }

    public int getNum_submitted() {
        return num_submitted;
    }

    public void setNum_submitted(int num_submitted) {
        this.num_submitted = num_submitted;
    }

    public int getSum_q1() {
        return sum_q1;
    }

    public void setSum_q1(int sum_q1) {
        this.sum_q1 = sum_q1;
    }

    public int getSum_q2() {
        return sum_q2;
    }

    public void setSum_q2(int sum_q2) {
        this.sum_q2 = sum_q2;
    }

    public int getSum_q3() {
        return sum_q3;
    }

    public void setSum_q3(int sum_q3) {
        this.sum_q3 = sum_q3;
    }

    public int getSum_q4() {
        return sum_q4;
    }

    public void setSum_q4(int sum_q4) {
        this.sum_q4 = sum_q4;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
    
    

    public Survey(int survey_id, int num_submitted, int sum_q1, int sum_q2, int sum_q3, int sum_q4, Course course) {
        this.survey_id = survey_id;
        this.num_submitted = num_submitted;
        this.sum_q1 = sum_q1;
        this.sum_q2 = sum_q2;
        this.sum_q3 = sum_q3;
        this.sum_q4 = sum_q4;
        this.course = course;
        this.cid = this.course.getCid();
    }

    public Survey(int survey_id, int cid, int num_submitted, int sum_q1, int sum_q2, int sum_q3, int sum_q4) {
        this.survey_id = survey_id;
        this.cid = cid;
        this.num_submitted = num_submitted;
        this.sum_q1 = sum_q1;
        this.sum_q2 = sum_q2;
        this.sum_q3 = sum_q3;
        this.sum_q4 = sum_q4;
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
        result.append(Integer.toString(this.survey_id));
        result.append(",");
        result.append(Integer.toString(this.cid));
        result.append(",");
        result.append(Integer.toString(this.num_submitted));
        result.append(",");
        result.append(Integer.toString(this.sum_q1));
        result.append(",");
        result.append(Integer.toString(this.sum_q2));
        result.append(",");
        result.append(Integer.toString(this.sum_q3));
        result.append(",");
        result.append(Integer.toString(this.sum_q4));
        result.append(")");
        return result.toString();
    }  
}
