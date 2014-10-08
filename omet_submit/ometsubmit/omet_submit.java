//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis
//Task # 1 - Term Project
//omet_submit
package ometsubmit;

import ometcommon.Surveydata;
import ometcommon.OMETConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class omet_submit {

    private static final String username = "user";
    private static final String password = "password";

    //application logic is as follows:
    //sanitize user input or screen for bad input
    //attempt to submit the data
    //return the results
    public static void main(String[] args) {

        String input;
        boolean badinput = false;
        //not enough args
        if (args.length < 6) {
            System.err.println("Not enough arguments");
            return;
        }
        input = args[0];
        int surveyid = -1;
        int sid = -1;
        int[] q = new int[4];
        String q5 = null;

        if (input != null) {
            try {
                surveyid = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.err.println("Invalid survey id - not an integer - " + input);
                badinput = true;
            }
        } else {
            badinput = true;
        }
        //now parse sid
        input = args[1];
        if (input != null) {
            try {
                sid = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.err.println("Invalid student id - not an integer - " + input);
                badinput = true;
            }
        } else {
            badinput = true;
        }
        //parse q1 through q4
        for (int i = 2; i < 6; i++) {
            if (args[i] != null) {
                try {
                    q[i - 2] = Integer.parseInt(args[i]);
                    if (q[i - 2] > 5 || q[i - 2] < 1) {
                        System.err.println("Invalid input for q" + Integer.toString(i - 1) + " - must be # from 1 to 5");
                        badinput = true;
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid input for q" + Integer.toString(i - 1) + " not an integer");
                    badinput = true;
                }
            } else {
                badinput = true;
            }
        }
        //parse q5
        input = args[6];
        if (input != null) {
            //check string length for schema compliance
            if (input.length() > 250) {
                System.err.println("Q5 must be less than 250 charaters in length.");
            }
            q5 = input;
        } else {
            badinput = true;
        }
        //abort if bad input
        if (badinput || surveyid < 0 || sid < 0) {
            System.err.println("Bad input - aborting");
            return;
        } else {

            //attempt to submit the survey and return feedback: success or error handling
            switch (surveySubmit(surveyid, sid, q[0], q[1], q[2], q[3], q5)) {
                case 1:
                    //print the success message, requires additional query
                    successPrint(surveyid);
                    break;
                case -1:
                    System.err.println("Invalid survey -- no such survey id: " + Integer.toString(surveyid));
                    break;
                case -2:
                    System.err.println("Invalid survey -- no such student id: " + Integer.toString(sid));
                    break;
                case -3:
                    System.err.println("Insert error");
                    break;
                case -4:
                    System.err.println("Commit error");
                    break;
                case -5:
                    System.err.println("SQL Error");
                    break;
                case 0:
                    System.err.println("Connection error");
                    break;
            }
        }
    }

    // returns  -1 if survey id is invalid
    //          -2 if student id is invalid 
    //          -3 if insert error
    //          -4 if commit error
    //          -5 if SQLException
    //           0 if unknown error/exception
    //           1 if success
    private static int surveySubmit(int surveyid, int studentid, int question1, int question2, int question3, int question4, String question5) {
        int result = 0;
        int status = 0;
        String qString;
        ResultSet data;
        try {
            //get the current date
            Date now = new Date();
            //create surveydata object
            Surveydata survey = new Surveydata(surveyid, studentid, now, question1, question2, question3, question4, question5);
            //create an omet connection
            OMETConnection db = new OMETConnection(username, password);
            //check to see if the survey id is valid
            qString = "SELECT DISTINCT count(*) FROM surveys WHERE survey_id=" + Integer.toString(surveyid) + "";
            data = db.query(qString);
            if (data.next()) {
                status = data.getInt("COUNT(*)");
            }
            if (status != 1) {
                result = -1;
                return result;
            }
            //check to see if the student id is valid
            qString = "SELECT DISTINCT count(*) FROM Students WHERE sid=" + Integer.toString(studentid) + "";
            data = db.query(qString);
            if (data.next()) {
                status = data.getInt("COUNT(*)");
            }
            if (status != 1) {
                result = -2;
                return result;
            }
            //submit the surveydata to the table
            if (survey.insert(db) <= 0) {
                //insert error
                result = -3;
            } else {
                result = 1;
            }
            if (db.commit() < 0) {
                //commit error
                result = -4;
            } else {
                //commit worked   
            }
            //close the connection and return the result
            db.close();
            return result;
        } catch (Exception ex) {
            //Logger.getLogger(omet_submit.class.getName()).log(Level.SEVERE, null, ex);
            SQLException test = new SQLException();
            if (ex.getCause().equals(test.getCause())) {
                result = -5;
            }
            return result;
        }
    }

    //if we succeeded, print the desired information in the success message
    private static void successPrint(int surveyid) {
        OMETConnection db = new OMETConnection(username, password);
        //query the table to get the correct course number and subject
        ResultSet data = db.query("SELECT UNIQUE subject,course_number FROM Courses,Surveys where (Surveys.survey_id=" + Integer.toString(surveyid) + " AND Surveys.cid = Courses.cid)");
        String subject = null;
        String course_number = null;
        try {
            while (data.next()) {
                subject = (data.getString("subject"));
                course_number = (data.getString("course_number"));
            }
            // Print the success message
            System.out.println("Survey " + Integer.toString(surveyid) + " for class " + subject + " " + course_number + " recorded.");
            db.close();
        } catch (SQLException ex) {
            //Logger.getLogger(omet_submit.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Data submitted with errors");
        }
    }
}
