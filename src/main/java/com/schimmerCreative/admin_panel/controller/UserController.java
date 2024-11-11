package com.schimmerCreative.admin_panel.controller;

import com.schimmerCreative.admin_panel.model.User;
import com.schimmerCreative.admin_panel.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
indicate that the return value of the
 methods should be bound to the web response body
*/

/*
Global Exception Handling:
Use @ControllerAdvice or @RestControllerAdvice to handle exceptions globally.
This centralizes error handling logic and keeps controllers clean.

HTTP Status Codes: Map exceptions to HTTP status codes properly.
For instance, EntityNotFoundException could map to 404 Not Found.
 */
@ControllerAdvice
@RestController
public class UserController {

    /*
    Directly returning a User from a controller method automatically implies
     an HTTP 200 (OK) status if no issues arise. Using ResponseEntity,
     however, allows you to explicitly set the HTTP status
     (e.g., 201 Created, 404 Not Found, 400 Bad Request).
    The choice of HTTP status code when updating a resource can reflect different outcomes of the operation:

        200 OK: Successfully updated the user and the response body contains the updated user.
        204 No Content: Successfully updated the user but there's no body in the response.
        400 Bad Request: The request was malformed or invalid. Typically used if validation fails.
        404 Not Found: The specified user ID does not exist.
        409 Conflict: Typically used if updating the user would lead to a conflict (e.g., duplicate email if emails must be unique).
    Returns need the User Object or status variables or ResponseEntity<User>
    Consider using ResponseEntity<User> or ResponseEntity<?> for
     more control over the HTTP response, including setting specific status codes.
    */

    //usable injected UserService interface for calling methods instantiated in a private class member variable
    UserService userServiceInject;

    /*
     For each CRUD operation, define a method in the
     controller that will execute the corresponding service method.
    POST and PUT Methods**: Require `@RequestBody User user`
     to capture user details from the incoming request.
    GET and DELETE Methods**: Need `@PathVariable int id`
     to identify which user to retrieve or delete.
    */

    /*
    addUser method will call the createUser method from the UserService interface
    This endpoint should accept user details from the request body
    */
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User newUser){
        /*
        Model User Object as parameter
        @RequestBody binds the JSON and XML payload in  the request body to the User object parameter
        CRUD: create, adding a new user
        call the createUser from the UserService interface
        It’s practical to store this result in a variable,
         typically of type User, to handle the response appropriately.
        extract the parameters from the newUser object to use them with the interface method
        create a variable, and call the methods get...() from the newUser Request Body
        */

        try{
            String userFirstName = newUser.getUserFirstName();
            String userLastName = newUser.getUserLastName();
            String userEmail = newUser.getUserEmail();

            //instead of creating the variable class level, instantiate it here directly-> createdUser
            User createdUser = userServiceInject.createUser(userFirstName, userLastName, userEmail);

        /*
        return an HTTP status code 201 Created
        remember that the return of the method is of type ResponseEntity<User>
        Stateless Behavior: HTTP is a stateless protocol, meaning each request is independent of others
        */
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

        }catch (Exception e) {
            /*
            typically HttpStatus.INTERNAL_SERVER_ERROR or HttpStatus.BAD_REQUEST
            might be more appropriate depending on the context).
             */

            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }


    }





    /*
    correspondant to updateUser in UserService interface
    add the path variable int id for no inconsistencies
    */
    @PutMapping("/users/{id}")
    public ResponseEntity<Optional<User>> modifyUser(@RequestBody User userToUpdate, @PathVariable int id){
        //CRUD: update, modifying an existing user´s details

        try{
            String userFirstName = userToUpdate.getUserFirstName();
            String userLastName = userToUpdate.getUserLastName();
            String userEmail = userToUpdate.getUserEmail();

            //the id can be directed from the main method parameter to the interface parameter call
            Optional<User> updatedUser = userServiceInject.updateUser(userFirstName, userLastName, userEmail, id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);

        }catch (Exception e) {
            System.out.println("Could not complete modifyUser method in UserController " + e);
            throw new EntityNotFoundException(String.valueOf(HttpStatus.NOT_MODIFIED));

        }



    }

    //Correspondant to deleteUser in UserService interface
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Boolean> removeUser(@PathVariable int id){
        //CRUD: delete, removing a user from the database

        try{

            Boolean deletionStatus = userServiceInject.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(deletionStatus);

        }catch (Exception e) {
            System.out.println("Could not complete removeUser method in UserController " + e);
            /*
             A better status might be HttpStatus.NOT_FOUND or HttpStatus.INTERNAL_SERVER_ERROR.
             */
            throw new EntityNotFoundException(String.valueOf(HttpStatus.NOT_FOUND));
        }



    }


    /*
    correspondant to getUserbyId in UserService interface
    the path variable in the annotations is named {id}.
     These should match to correctly bind the path variable to the method parameter.
    */
    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<User>> retrieveUser(@PathVariable int id){
        //CRUD: read, retrieving one or more users


        try{
            Optional<User> requestedUser = userServiceInject.findUserById(id);

            System.out.println("controller, user : " + requestedUser);
            return ResponseEntity.status(HttpStatus.OK).body(requestedUser);

        }catch (Exception e) {
            System.out.println("Could not complete retrieveUser method in UserController " + e);
            throw new EntityNotFoundException(String.valueOf(HttpStatus.NOT_FOUND));
        }



    }

    /*

   implement here the retrieveAllUsers method with maybe findAll from Repository, getAllUsers from UserService?
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> retrieveAllUsers(){
        //CRUD: read, retrieving one or more users

        try{
            //uses findAll() from repository
            List<User> allUsers =  userServiceInject.getAllUsers();


            System.out.println("allUsers : " + allUsers);
            return ResponseEntity.status(HttpStatus.OK).body(allUsers);

        }catch (Exception e) {
            System.out.println("Could not complete retrieveAllUsers method in UserController " + e);
            throw new EntityNotFoundException(String.valueOf(HttpStatus.NOT_FOUND));
        }


    }


    @Autowired
    public UserController(UserService userServiceInject) {

        this.userServiceInject = userServiceInject;
        /*
        using it here as parameter helps that controller cannot be used without it
        declaring it in the class as composition and then declaring is needed also
         for calling the methods
        */

    }
}
