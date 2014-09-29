//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis
//Task # 3 - Term Project
//omet_list

package ometlist;

import java.text.DecimalFormat;
import ometcommon.OMETConnection;

public class omet_list {

    private static final String username = "jgr10";
    private static final String password = "6083";

    //program logic is as follows:
    //verify that there are minimum amount of arguments supplied
    //verify that a valid search criteria keyword is supplied
    //verify that valid arguments are used per criteria
    //use the survey gather object to verify that the criteria are present in the database
    //use the survey gather object to collect the data on the criteria
    //if at any point invalid input is supplied or the arguments are not found, the badInput flag is set to true
    //at the end of the main method, if the flag is false we process and print the data we gathered
    //if the flag is true, we do nothing.
    public static void main(String[] args) {
        boolean badInput = false;
        survey_gather gather = new survey_gather();
        OMETConnection db = new OMETConnection(username, password);
        if (args.length > 1) {
            //regex pattern to test for numbers in the string.
            //reused. some input should have numbers, some should not.
            //if the string matches there are numbers present
            if (args[0].matches(".*\\d.*")) {
                badInput = true;
                System.err.println("Invalid data specification -- use term, class, subject, or prof");
            } else {
                //search by term
                if (args[0].equalsIgnoreCase("term")) {
                    if (args[1].matches(".*\\d.*")) {
                        int term = Integer.parseInt(args[1]);
                        if (gather.checkTerm(term, db)) {
                            gather.gatherbyTerm(term, db);
                        } else {
                            badInput = true;
                            System.err.println("Invalid Term -- " + Integer.toString(term) + " could not be found in the database.");
                        }
                    } else {
                        badInput = true;
                        System.err.println("Invalid Term -- Term should be a number.");
                    }
                    //Search by class (subject and class number)
                } else if (args[0].equalsIgnoreCase("class")) {
                    if (args.length > 2) {
                        if (args[1].matches(".*\\d.*")) {
                            badInput = true;
                            System.err.println("Invalid Subject -- Subject should not contain numbers.");
                        } else {
                            String subject = args[1];
                            if (args[2].matches(".*\\d.*")) {
                                int course_number = Integer.parseInt(args[2]);
                                if (gather.checkClass(subject, course_number, db)) {
                                    gather.gatherbyClass(subject, course_number, db);
                                } else {
                                    badInput = true;
                                    System.err.println("Invalid Subject or Course Number -- " + subject + " " + Integer.toString(course_number) + " could not be found in the database.");
                                }
                            } else {
                                badInput = true;
                                System.err.println("Invalid Course number -- Course number should be a number.");
                            }
                        }
                    } else {
                        badInput = true;
                        System.err.println("Invalid input -- Not enough args for class.");
                    }
                    //Search by subject   
                } else if (args[0].equalsIgnoreCase("subject")) {
                    if (args[1].matches(".*\\d.*")) {
                        badInput = true;
                        System.err.println("Invalid Subject -- Subject should not contain numbers.");
                    } else {
                        String subject = args[1];
                        if (gather.checkSubject(subject, db)) {
                            gather.gatherbySubject(subject, db);
                        } else {
                            badInput = true;
                            System.err.println("Invalid Subject -- " + subject + " could not be found in the database.");
                        }
                    }
                    //Search by professor last name
                } else if (args[0].equalsIgnoreCase("prof")) {
                    if (args[1].matches(".*\\d.*")) {
                        badInput = true;
                        System.err.println("Invalid Professor last name -- Professor last name should not contain numbers.");
                    } else {
                        String prof = args[1];
                        if (gather.checkProf(prof, db)) {
                            gather.gatherbyProf(prof, db);
                        } else {
                            badInput = true;
                            System.err.println("Invalid Professor last name -- " + prof + " could not be found in the database.");
                        }
                    }
                } else {
                    badInput = true;
                    //user supplied some invalid criteria specifiation
                    System.err.println("Invalid data specification -- use term, class, subject, or prof");
                }
            }
        } else {
            //no input or not enough args
            badInput = true;
        }
        if (!badInput) {
            //process the results if no bad data was encountered.
            successPrint(gather);
        }
        //close the database connection.
        db.close();
    }

    //successPrint processes the surveys in the gather object and prints the information in the required format
    //if the object is empty, we assume no survey from the criteria were found
    //this is a safe assumption because we already checked the database to see that the criteria were valid
    //just no surveys have been generated for the class
    private static void successPrint(survey_gather successful) {
        try {
            if (successful.surveys.size() > 0) {
                DecimalFormat twoDForm = new DecimalFormat("#.##");
                for (surveyinfo info : successful.surveys) {
                    System.out.print(info.survey_id);
                    System.out.print("\t");
                    System.out.print(info.term);
                    System.out.print("\t");
                    System.out.print(info.subject);
                    System.out.print("\t");
                    System.out.print(info.course_number);
                    System.out.print("\t");
                    System.out.print(info.last_name);
                    System.out.print("\t");
                    System.out.print(Double.toString(Double.valueOf(twoDForm.format(info.avg_q1))));
                    System.out.print("\n");
                }
            } else {
                System.out.println("No surveys found for specified criteria.");
            }
        } catch (Exception ex) {
            System.err.println("Error - bad data");
        }
    }

}
