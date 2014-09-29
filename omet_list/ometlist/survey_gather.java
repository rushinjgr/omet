//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis
//Task # 3 - Term Project
//omet_list

package ometlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ometcommon.OMETConnection;

public class survey_gather {

    //an arraylist to store the survey objects we gather
    public ArrayList<surveyinfo> surveys = new ArrayList<surveyinfo>();

    //gather by methods 
    //utilize similar queries to find correct attributes across Surveys, Courses, and Instructors tables
    //pass the ResultSet on to gather
    public void gatherbyTerm(int term, OMETConnection db) {
        gather(db.query("SELECT S.survey_id,S.sum_q1,C.term,C.subject,C.course_number,I.last_name FROM Surveys S, Courses C, Instructors I WHERE (S.cid = C.cid AND C.instructor_id = I.fid AND C.term =" + Integer.toString(term) + ")"));
    }

    public void gatherbyClass(String subject, int course_number, OMETConnection db) {
        gather(db.query("SELECT S.survey_id,S.sum_q1,C.term,C.subject,C.course_number,I.last_name FROM Surveys S, Courses C, Instructors I WHERE (S.cid = C.cid AND C.instructor_id = I.fid AND UPPER(C.subject) = UPPER('" + subject + "') AND C.course_number = " + Integer.toString(course_number) + ")"));
    }

    public void gatherbySubject(String subject, OMETConnection db) {
        gather(db.query("SELECT S.survey_id,S.sum_q1,C.term,C.subject,C.course_number,I.last_name FROM Surveys S, Courses C, Instructors I WHERE (S.cid = C.cid AND C.instructor_id = I.fid AND UPPER(C.subject) = UPPER('" + subject + "'))"));
    }

    public void gatherbyProf(String prof, OMETConnection db) {
        gather(db.query("SELECT S.survey_id,S.sum_q1,C.term,C.subject,C.course_number,I.last_name FROM Surveys S, Courses C, Instructors I WHERE (S.cid = C.cid AND C.instructor_id = I.fid AND UPPER(I.last_name) = UPPER('" + prof + "'))"));
    }

    //takes the resultset from the gatherby methods and processes the resultset
    //collects the data for each survey and creates a surveyinfo container, adds to array
    private void gather(ResultSet data) {
        try {
            while (data.next()) {
                surveyinfo current = new surveyinfo();
                current.survey_id = data.getInt("survey_id");
                current.term = data.getInt("term");
                current.subject = data.getString("subject");
                current.course_number = data.getInt("course_number");
                current.last_name = data.getString("last_name");
                current.avg_q1 = data.getDouble("sum_q1");
                surveys.add(current);
            }
        } catch (SQLException ex) {
            // Logger.getLogger(survey_gather.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //check methods check for the presence of the search criteria in the database
    //return true if at least one entry qualifies
    public boolean checkTerm(int term, OMETConnection db) {
        boolean result = false;
        try {
            ResultSet data = db.query("SELECT DISTINCT COUNT(*) FROM Courses WHERE term=" + Integer.toString(term));
            if (data.next()) {
                result = data.getInt("COUNT(*)") > 0;
            }
            return result;
        } catch (SQLException ex) {
            //Logger.getLogger(survey_gather.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean checkClass(String subject, int course_number, OMETConnection db) {
        boolean result = false;
        try {
            ResultSet data = db.query("SELECT DISTINCT COUNT(*) FROM Courses WHERE course_number=" + Integer.toString(course_number) + " AND UPPER(subject) = UPPER('" + subject + "')");
            if (data.next()) {
                result = data.getInt("COUNT(*)") > 0;
            }
            return result;
        } catch (SQLException ex) {
            //Logger.getLogger(survey_gather.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean checkSubject(String subject, OMETConnection db) {
        boolean result = false;
        try {
            ResultSet data = db.query("SELECT DISTINCT COUNT(*) FROM Courses WHERE UPPER(subject) = UPPER('" + subject + "')");
            if (data.next()) {
                result = data.getInt("COUNT(*)") > 0;
            }
            return result;
        } catch (SQLException ex) {
            //Logger.getLogger(survey_gather.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean checkProf(String prof, OMETConnection db) {
        boolean result = false;
        try {
            ResultSet data = db.query("SELECT DISTINCT COUNT(*) FROM Instructors WHERE UPPER(last_name) = UPPER('" + prof + "')");
            if (data.next()) {
                result = data.getInt("COUNT(*)") > 0;
            }
            return result;
        } catch (SQLException ex) {
            //Logger.getLogger(survey_gather.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
