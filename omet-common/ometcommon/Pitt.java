//Justin Rushin III
//jgr10@pitt.edu
//4/21/14
//CS 1555 - Prof Labrinidis

package ometcommon;

//DAO Object for Pitt tuples in the OMET Database
//Is extended by students and instructors

public class Pitt {
    
    String first_name;
    String last_name;
    String pitt_account; //10

    public String getFirst_name() {
        return first_name;
                
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPitt_account() {
        return pitt_account;
    }

    public void setPitt_account(String pitt_account) {
        this.pitt_account = pitt_account;
    }
     
}
