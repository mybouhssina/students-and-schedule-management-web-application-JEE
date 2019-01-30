package com.gestionabs.repositories;

import com.gestionabs.beans.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndIdNot(String username,Integer id);
}