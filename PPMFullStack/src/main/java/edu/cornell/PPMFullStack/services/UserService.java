package edu.cornell.PPMFullStack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.cornell.PPMFullStack.Exceptions.UserNameAlreadyExistsException;
import edu.cornell.PPMFullStack.domain.User;
import edu.cornell.PPMFullStack.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser) {

        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            // make sure password and confirm password match

            newUser.setConfirmPassword("");

            // we don't persist or show the confirm password
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new UserNameAlreadyExistsException(
                "User name: " + newUser.getUsername() + " already exist!");
        }

    }

}
