//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis
//Task # 4 - Term Project
//omet_stats

package ometstats;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import ometcommon.OMETConnection;
import ometlist.survey_gather;
import ometlist.surveyinfo;

public class omet_stats {

    private static final String username = "jgr10";
    private static final String password = "6083";
    
    //application logic is virtually identical to that of omet_list
    //data processing at the end is different, however
    //due to these facts, objects survey_gather and surveyinfo are reused
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
    
    //get the q1 data from the relevant surveys
    //sort the q1 results, find the min, max, and median
    //split the results into bottom and top halves
    //perform median procedure on halves to get Top25% and Bottom25%
    //as in omet_list, when passed an empty set of survey info, we assume no results were found
    private static void successPrint(survey_gather successful) {
        ArrayList<Double> averages = new ArrayList<Double>();
        DecimalFormat dform = new DecimalFormat("#");
        try {
            if (successful.surveys.size() > 0) {
                for (surveyinfo info : successful.surveys) {
                    averages.add(info.avg_q1);
                }
                //use the Collections utils to sort the arraylist
                Collections.sort(averages);
                //the min and max will be the first and last elements respectively
                Double min = averages.get(0);
                Double max = averages.get(averages.size() - 1);
                //the median will be the middle element of the sorted arraylist
                double median = getMedian(averages);
                //split the list around the median
                ArrayList<Double> bottom50 = new ArrayList<Double>();
                ArrayList<Double> top50 = new ArrayList<Double>();
                for (double num : averages) {
                    if (num <= median) {
                        bottom50.add(num);
                    } else {
                        top50.add(num);
                    }
                }
                //get the medians of the split lists, these are the bottom and top 25% numbers
                double bottom = getMedian(bottom50);
                double top = getMedian(top50);
                System.out.print(Double.toString(Double.valueOf(dform.format(max))));
                System.out.print("\t Max\n");
                System.out.print(Double.toString(Double.valueOf(dform.format(top))));
                System.out.print("\t Top 25%\n");
                System.out.print(Double.toString(Double.valueOf(dform.format(median))));
                System.out.print("\t Median\n");
                System.out.print(Double.toString(Double.valueOf(dform.format(bottom))));
                System.out.print("\t Bottom 25%\n");
                System.out.print(Double.toString(Double.valueOf(dform.format(min))));
                System.out.print("\t Min\n");
            } else {
                System.out.println("No surveys found for specified criteria.");
            }
        } catch (Exception ex) {
            System.err.println("Error - bad data");
            ex.printStackTrace();
        }
    }
    
    //util method gets median from an arraylist of doubles
    private static double getMedian(ArrayList<Double> list) {
        Double result;
        int middle = list.size() / 2;
        if (list.size() % 2 == 1) {
            //if arraylist is odd in length, median will just be the middle number
            result = list.get(middle);
        } else {
            //if the arraylist is even in length the two middle numbers must be averaged
            result = (list.get(middle - 1) + list.get(middle)) / 2.0;
        }
        return result;
    }
}
