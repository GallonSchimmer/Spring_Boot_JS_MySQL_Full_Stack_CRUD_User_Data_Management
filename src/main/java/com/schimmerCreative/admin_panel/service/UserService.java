package com.schimmerCreative.admin_panel.service;

import com.schimmerCreative.admin_panel.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //define the methods that expose business functionalities

    //createUser method
    public User createUser(String userFirstName, String userLastName, String userEmail);

    //updateUser method
    public Optional<User> updateUser(String userFirstName, String userLastName, String userEmail, int id);

    //getUserById
    //public User getUserById(int id);

    public Optional<User> findUserById(int id);


    //List<User> getUsersByIds(List<Long> ids);

    //deleteUser
    public boolean deleteUser(int id);


     /*

   implement here the getAllUsers method with a supposedly findAll method from Repository?
     */

    /*
    Type Consistency: Ensure that the service method and the controller
    are aligned in terms of the data type used (e.g., List<User> vs. ArrayList<User>).
    It's usually best to use List<User> as it is more generic and flexible.
     */
     public List<User> getAllUsers(); //uses findAll() from repository

}



