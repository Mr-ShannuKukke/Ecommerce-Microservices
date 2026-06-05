package com.ecommerce.user.controller;

import com.ecommerce.user.model.User;
import com.ecommerce.user.security.JwtTokenProvider;
import com.ecommerce.user.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        User user=userService.getUserByEmail(request.getEmail());

        if(user ==null || !user.getPassword().equals(request.getPassword())){
            return ResponseEntity.status(401).body(new LoginResponse("Invalid credentials 🤨", null, null));
        }

        String token=jwtTokenProvider.generateToken(user.getId(), user.getEmail());
        return ResponseEntity.ok(new LoginResponse("Login successful 😍", user.getId(), token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails){
        return ResponseEntity.ok(userService.updateUser(id, userDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User with id: "+id+" has been deleted successfully");
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
//        User user=userService.getUserByEmail(request.getEmail());
//        if(!user.getPassword().equals(request.getPassword())){
//            return ResponseEntity.status(401).body(new LoginResponse("Invalid credentials 🤨",null));
//        }
//        return ResponseEntity.ok(new LoginResponse("Login successful 😍", user.getId()));
//    }

}

class LoginRequest {
    private String email;
    private String password;

    public void setEmail(String email){
        this.email=email;
    }

    public String getEmail(){
        return email;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getPassword(){
        return password;
    }

}

class LoginResponse{
    private String message;
    private Long userId;
    private String token;

    public LoginResponse(String message, Long userId, String token){
        this.message=message;
        this.userId=userId;
        this.token=token;
    }

    public void setMessage(String message){
        this.message=message;
    }

    public String getMessage(){
        return message;
    }

    public void setUserId(Long userId){
        this.userId=userId;
    }

    public Long getUserId(){
        return userId;
    }

    public void setToken(String token){
        this.token=token;
    }

    public String getToken(){
        return token;
    }
}