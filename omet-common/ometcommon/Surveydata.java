//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis

package ometcommon;
import ometcommon.OMETConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

//DAO Object for Surveydata tuples in the OMET Database

public class Surveydata {
    private final String table = "Surveydata";
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    
    private int survey_id;
    private int sid;
    private Date submit_time; //yy/mm/dd
    private int q1;
    private int q2;
    private int q3;
    private int q4;
    private String q5; //250
    
    private Survey survey;
    private Student student;
    
    public int getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id() {
        if(this.survey != null){
            this.survey_id = this.survey.getSurvey_id();
        }
    }

    public int getSid() {
        return sid;
    }

    public void setSid() {
        if(this.student != null){
            this.sid = this.student.getSid();
        }
    }

    public Date getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(Date submit_time) {
        this.submit_time = submit_time;
    }

    public int getQ1() {
        return q1;
    }

    public void setQ1(int q1) {
        this.q1 = q1;
    }

    public int getQ2() {
        return q2;
    }

    public void setQ2(int q2) {
        this.q2 = q2;
    }

    public int getQ3() {
        return q3;
    }

    public void setQ3(int q3) {
        this.q3 = q3;
    }

    public int getQ4() {
        return q4;
    }

    public void setQ4(int q4) {
        this.q4 = q4;
    }

    public String getQ5() {
        return q5;
    }

    public void setQ5(String q5) {
        this.q5 = q5;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setSurvey_id(int survey_id) {
        this.survey_id = survey_id;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }
    
    public int insert(OMETConnection connection) {
        String update = "INSERT INTO " + table + " VALUES" + this.values();
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
        result.append(Integer.toString(this.sid));
        result.append(",to_date('");
        result.append(format.format(submit_time));
        result.append("','");
        result.append("yyyy/mm/dd'),");
        result.append(Integer.toString(this.q1));
        result.append(",");
        result.append(Integer.toString(this.q2));
        result.append(",");
        result.append(Integer.toString(this.q3));
        result.append(",");
        result.append(Integer.toString(this.q4));
        result.append(",'");
        result.append(q5);
        result.append("')");
        return result.toString();
    }

    public Surveydata(Date submit_time, int q1, int q2, int q3, int q4, String q5, Survey survey, Student student) {
        this.submit_time = submit_time;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.survey = survey;
        this.student = student;
        this.survey_id = survey.getSurvey_id();
        this.sid = student.getSid();
    }
    
    public Surveydata(int survey_id, int sid, Date submit_time, int q1, int q2, int q3, int q4, String q5) {
        this.survey_id = survey_id;
        this.sid = sid;
        this.submit_time = submit_time;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
    }
}
