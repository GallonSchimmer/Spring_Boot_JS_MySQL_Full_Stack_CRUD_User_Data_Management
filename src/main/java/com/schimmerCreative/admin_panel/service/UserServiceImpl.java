package com.schimmerCreative.admin_panel.service;

import com.schimmerCreative.admin_panel.model.User;
import com.schimmerCreative.admin_panel.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
Implement Business Methods:
Write methods in the service class that use the UserRepository
 to fulfill the business requirements, such as adding or retrieving users.
Add business logic such as validation (e.g., email format validation,
 non-duplication of email).

Transaction Management:
Configure transaction management to ensure data integrity
 and handle any transactions required by business processes,
 especially where multiple database operations must be completed
 successfully or not at all.

Handling Nulls and Exceptions:
Consider handling scenarios where user operations might fail
 (e.g., no user found for a given ID, or email duplication).
 Implement exception handling and possibly return more informative
 results or throw custom exceptions.
*/

@Transactional
@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService{
    /*
    This class will use the UserRepository to interact with the database
     instance of UserRepository
     allows your service to access UserRepository methods like findById, save, delete
    */

    //to return the objects and datatype, need to declare them
    User newUser;
    Optional<User> updatedUser;

    /*
    Returning Optional: Consider using Optional for methods that retrieve data.
    This can prevent null checks
    in other layers and let you elegantly handle missing values using orElseThrow().
     */
    Optional<User> byIdUser;
    boolean deletionStatus;

    List<User> allUsers = new ArrayList<>();

    UserRepository userRepository;

    //methods

/*
Throwing New Exceptions: When catching exceptions, you're throwing new RuntimeExceptions.
While this will certainly propagate the error,
consider using more specific exceptions that could provide more context or be
handled differently at higher layers.

Use of EntityNotFoundException: This is appropriate for situations where a user isn't found.
It translates well into a 404 Not Found HTTP status in a web context,
providing clear feedback to the client.
 */
    @Override
    public User createUser(String userFirstName, String userLastName, String userEmail) {

        /*
        Validation: Before attempting to save the user, implement validation checks
        (e.g., non-null, email format, uniqueness) to catch any data inconsistencies
        early in the process.
         */
        /*
        Validate Inputs: Ensure no input is null.
        Create and Set User: Instantiate a user object and set its properties.
        Save User: Persist the user to the database inside a try-catch block.
        Handle Exceptions: Appropriately catch and rethrow exceptions with informative messages.
         */


        if (userFirstName != null && userLastName != null && userEmail != null) {

            User userToCreate = new User();

        /*
        the return can be the object or the status
        It's useful for the client-side to receive the created entity for immediate use or confirmation,
         enhancing user experience and debugging ease.
        */
            try {

                userToCreate.setUserFirstName(userFirstName);
                userToCreate.setUserLastName(userLastName);
                userToCreate.setUserEmail(userEmail);

                newUser = userRepository.save(userToCreate);
            /*
            Enhanced Logging: Replace System.out.println with a robust logging framework which allows
            categorization of logs (INFO, DEBUG, ERROR) and better management of log outputs.

            should be replaced with a proper logging mechanism (like SLF4J or Log4J)
             */

                System.out.println("newUSer in createUser UserServiceImpl created: " + newUser);
                return newUser;
                //return creationStatus;
            } catch (Exception e) {
                //Consider using more specific exceptions such as DataAccessException or
                // custom exceptions that accurately describe the failure.
                throw new RuntimeException("Could not complete createUser method in UserServiceImpl " + e);
            }
        }else{
            Exception e = new Exception();
            throw new EntityNotFoundException("User to create not found on createUSer method in UserServiceImpl " + e);
        }
    }



    @Override
    public Optional<User> updateUser(String userFirstName, String userLastName, String userEmail, int id) {

        /*
        Start by checking if the Optional<User> is not empty.
        Extract the User from Optional if it's present.
        Update the User's attributes.
        Save the updated User.
        Handle cases where the User is not found by throwing appropriate exceptions.
         */
        /*
        return the updatedUser object or return the success status
        Returning the updated User provides immediate feedback on
         what changes were persisted to the database
        */
        /*
        Optional<User>, which means it could contain a User object or be empty if no user is found.
         Optional in Java is a container object used to contain not-null objects.
         It offers a way to avoid null checks and NullPointerExceptions.
         */
        /*
        Validation: Before attempting to update the user, validate the input data to ensure it
        meets your application's requirements (e.g., non-null values, valid email format).
         */
        Optional<User> userToUpdate = userRepository.findById(id);

            if(userToUpdate.isPresent() && userFirstName != null && userLastName != null && userEmail != null) {

                try {
                    User userObject = userToUpdate.get();
                    userObject.setUserFirstName(userFirstName);
                    userObject.setUserLastName(userLastName);
                    userObject.setUserEmail(userEmail);

                    //ERROR: Inferred type 'S' for type parameter 'S' is not within its bound; should extend 'com.schimmerCreative.admin_panel.model.User'
                    updatedUser = Optional.of(userRepository.save(userObject)); //save userToUpdate

                    /*
                    Returning Updated User: After successfully updating the user, the method returns the updated user object wrapped in an Optional.
                    This is useful for the client-side to confirm the changes.
                     */
                    System.out.println("updatedUSer in updateUser UserServiceImpl created: " + updatedUser);
                    return updatedUser;
                    //return updatedStatus;
                }catch (Exception e) {
                    throw new EntityNotFoundException("Could not complete updateUser method in UserServiceImpl "  + e);
                }

            }else{
                Exception e = new Exception();
                throw new EntityNotFoundException("User to update not found on updateUser method in UserServiceImpl " + e);
            }

    }
    /*
    @Override
    public User findUserById(int id) {

        /*
        int cannot be null and int does not accommodate null checks
        throw exception UserNotFoundException
        later Controller should catch the exception and send an HTTP 404 error
        */
    /*
        System.out.println("user : " + byIdUser);
        return byIdUser;

        */
    /*
    }
   */





    @Override
    public Optional<User> findUserById(int id) {

        try {
            byIdUser = Optional.ofNullable(userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Could not complete findUserById method in UserServiceImpl " + id)));

            System.out.println("userServiceImpl, found User Entity : " + byIdUser);

            return byIdUser;

        }catch (Exception e) {
            throw new EntityNotFoundException("Could not complete findUserById method in UserServiceImpl " + e);
        }




    }



    /*
    @Override
    public List<User> getUsersByIds(List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    */
    @Override
    public boolean deleteUser(int id) {


        /*
        This is good as it handles the case where the user might not exist.
         */
        Optional<User> userToDelete = userRepository.findById(id);

        if (userToDelete.isPresent()) {

            //Typically returns a success confirmation or a boolean status.
            try {

                userRepository.deleteById(id);

                System.out.println("userServiceImpl, deleted User Entity, false if delete, status: " + deletionStatus);
                return deletionStatus;

            }catch (Exception e) {
                throw new EntityNotFoundException("Could not complete deleteUSer method in UserServiceImpl" + e);
            }

        }else{
            Exception e = new Exception();
            throw new EntityNotFoundException("User to delete not found on deleteUser method in UserServiceImpl " + e);
        }


    }

    @Override
    public List<User> getAllUsers() {

        try {
            allUsers =  userRepository.findAll();
            return allUsers;
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not complete getAllUsers method in UserServiceImpl " + e);
        }

    }



    /*
    constructor
    as parameter use the UserRepository, not the raw Repository
    Use @Autowired on the constructor to wire the UserRepository dependency correctly.
    */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

}
