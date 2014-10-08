//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis
//Task # 2 - Term Project
//omet_show

package ometshow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import ometcommon.OMETConnection;

public class omet_show {

    private static final String username = "user";
    private static final String password = "password";

    //parse, sanitize, and check if user input is valid
    //attempt to find the survey data in the database
    //if valid survey id is entered, find the relevant survey data
    public static void main(String[] args) {
        if (args.length != 0) {
            try {
                int input = Integer.parseInt(args[0]);
                int status = -1;
                OMETConnection db = new OMETConnection(username, password);
                ResultSet data = db.query("SELECT UNIQUE COUNT(*) FROM Surveys WHERE survey_id=" + Integer.toString(input));
                if (data.next()) {
                    status = data.getInt("COUNT(*)");
                }
                if (status == 1) {
                    //valid surveyid
                    showdata(input);
                } else {
                    System.err.println("Invalid survey -- no such survey id:\t" + args[0]);
                }
                db.close();
            } catch (NumberFormatException exnum) {
                System.err.println("An error occurred. Ensure input is one number, the survey_id of the survey.");
            } catch (Exception ex) {
                //gger.getLogger(omet_show.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("An error occurred.");
            }
        } else {
            System.out.println("No arguments supplied.");
        }
    }

    //find the necessary data with a series of queries
    public static void showdata(int surveyid) {
        OMETConnection db = new OMETConnection(username, password);
        //query the table to get the correct course number and subject
        ResultSet data = db.query("SELECT UNIQUE subject,course_number,last_name FROM Courses,Surveys,Instructors where (Surveys.survey_id=" + Integer.toString(surveyid) + " AND Surveys.cid = Courses.cid AND Courses.instructor_id = Instructors.fid)");
        String subject = null;
        String course_number = null;
        String prof = null;
        try {
            while (data.next()) {
                subject = (data.getString("subject"));
                course_number = (data.getString("course_number"));
                prof = (data.getString("last_name"));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(omet_show.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Print the success message
        System.out.println("Survey " + Integer.toString(surveyid) + " for class " + subject + " " + course_number + " (Prof. " + prof + ")");
        data = db.query("SELECT q1,q2,q3,q4,q5_str from Surveydata WHERE survey_id=" + surveyid);
        int count = 1;
        try {
            while (data.next()) {
                System.out.println(Integer.toString(count) + ".\t" + Integer.toString(data.getInt("q1")) + "\t" + Integer.toString(data.getInt("q2")) + "\t" + Integer.toString(data.getInt("q3")) + "\t" + Integer.toString(data.getInt("q4")) + "\t" + data.getString("q5_str"));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(omet_show.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        for (int p = 0; p < 50; p++) {
            System.out.print("-");
        }
        System.out.print("\n");
        System.out.println("SURVEY SUMMARY");
        double numsubmitted;
        double enrollment;
        double average;
        double participation;
        data = db.query("SELECT UNIQUE S.num_submitted,S.sum_q1,S.sum_q2,S.sum_q3,S.sum_q4,C.enrollment from Surveys S,Courses C WHERE S.survey_id=" + surveyid + " AND C.cid=S.cid");
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        DecimalFormat intForm = new DecimalFormat("#");
        try {
            if (data.next()) {
                numsubmitted = data.getInt("num_submitted");
                average = data.getInt("sum_q1") / numsubmitted;
                System.out.println("Average Q1:\t" + Double.toString(Double.valueOf(twoDForm.format(average))));
                average = data.getInt("sum_q2") / numsubmitted;
                System.out.println("Average Q2:\t" + Double.toString(Double.valueOf(twoDForm.format(average))));
                average = data.getInt("sum_q3") / numsubmitted;
                System.out.println("Average Q3:\t" + Double.toString(Double.valueOf(twoDForm.format(average))));
                average = data.getInt("sum_q4") / numsubmitted;
                System.out.println("Average Q4:\t" + Double.toString(Double.valueOf(twoDForm.format(average))));
                System.out.println("Submitted:\t" + Double.toString(Double.valueOf(intForm.format(average))));
                enrollment = data.getInt("enrollment");
                System.out.println("Enrollment:\t" + Double.toString(Double.valueOf(intForm.format(average))));
                participation = (numsubmitted / enrollment) * 100;
                System.out.println("Participation:\t" + Double.toString(Double.valueOf(twoDForm.format(participation))) + "%");
            }
            db.close();
        } catch (SQLException ex) {
            //Logger.getLogger(omet_show.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Database error");
        }
    }

}
