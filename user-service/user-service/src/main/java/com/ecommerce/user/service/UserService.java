package com.ecommerce.user.service;

import com.ecommerce.user.exception.ResourceNotFoundException;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
//import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.user.exception.BadRequestException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public User registerUser(User user){
//        if(userRepository.findByEmail(user.getEmail()).isPresent()){
//            throw new RuntimeException("Email already exists!");
//        }
        if(user.getEmail() == null || user.getEmail().trim().isEmpty()){
            throw new BadRequestException("Email cannot be empty!");
        }
        if(userRepository.findByEmail(user.getEmail()) != null){
            throw new BadRequestException("Email already exists!");
        }
        return userRepository.save(user);
    }

    public User getUserById(Long id){
//        return userRepository.findById(id)
//                .orElseThrow(()-> new RuntimeException("User not found with id: "+ id));
        return userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+ id));
    }

    public User getUserByEmail(String email){
//        return userRepository.findByEmail(email)
//                .orElseThrow(()-> new RuntimeException("User not found with email: "+email));
//        Optional user=userRepository.findByEmail(email);
//        if(user==null){
//            throw new ResourceNotFoundException("User not found with email: "+email);
//        }
//        return user;

        return userRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with email: "+email));
    }

    public User updateUser(Long id, User userDetails){
        User user=getUserById(id);

        if(userDetails.getName()!=null)
           user.setName(userDetails.getName());
        if(userDetails.getPhoneNumber()!=null)
            user.setPhoneNumber(userDetails.getPhoneNumber());
        if (userDetails.getAddress() != null)
            user.setAddress(userDetails.getAddress());
        if (userDetails.getCity() != null)
            user.setCity(userDetails.getCity());
        if (userDetails.getState() != null)
            user.setState(userDetails.getState());
        if (userDetails.getZipCode() != null)
            user.setZipCode(userDetails.getZipCode());
        if (userDetails.getCountry() != null)
            user.setCountry(userDetails.getCountry());

        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
