package com.schimmerCreative.admin_panel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_data_table")
public class User {
    /*
    Java expects this name, while i would rather use UserModel, but stick to this one
    For java to understand better what table we are referring to, use the annotation @table
    */

    /*
    GenerationType.IDENTITY: Relies on an auto-incremented database column
    and is typically used in databases like MySQL and SQL Server.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    //Specify the corresponding field of the Table in MySQL through (name = "")
    @Column(name = "user_first_name")
    String userFirstName;

    @Column(name = "user_last_name")
    String userLastName;

    @Column(name = "user_email")
    String userEmail;


    //no argument Constructor for JPA (Java Persistence API)
    public User(){
        //noArgs

    }

    //Constructor with Arguments for instantiating Objects
    public User(int id, String userFirstName, String userLastName, String userEmail) {
        this.id = id;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
    }

    @Override
    public String toString(){
        return "USER [ id: "  + id + " userFirstName: " + userFirstName + " userLastName: " + userLastName + " userEmail: " + userEmail + "]";
    }

    //Setter and getters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
