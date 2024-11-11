package com.schimmerCreative.admin_panel.repository;

import com.schimmerCreative.admin_panel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


/*
JpaRepository is designed to use method signatures to derive SQL queries automatically.
 The method names themselves tell Spring Data JPA what queries to construct and execute.
*/
public interface UserRepository extends JpaRepository<User, Integer> {
    /*
    the JpaRepository requires two parameters:
    Entity type it manages
    data type of the entity´s primary key
    Java generics work with objects, not primitive datatype, then Integer
    also int is not nullable, whether Integer wrapper supports null values
    Entities in JPA need to represent the absence of a value with null
    */

    /*
    Repository Layer

    Spring Data JPA Methods: These methods inherently handle many common exceptions
    (like DataAccessException). Custom methods should handle specific cases (like not finding an entity)
    by returning Optional which can then be used to throw custom exceptions if necessary.
     */

    //public void findByLastName(String userLastName);
    //Spring Data JPA will implement dynamically in runtime

    public void findUserById(int id);

    /*
    The method signature should be void delete(User userToDelete);
    as the repository layer typically handles entities, not optionals.
     */



    //List<User> findAllById(List<Long> ids);
    //takes less memory by only doing one query for finding various objects from one id

    //findAll() will be used to fill up the table through getAllUsers() and retrieveAllUsers()

}
