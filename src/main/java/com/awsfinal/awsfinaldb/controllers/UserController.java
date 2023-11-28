package com.awsfinal.awsfinaldb.controllers;

import com.awsfinal.awsfinaldb.domain.User;
import com.awsfinal.awsfinaldb.exceptions.ParamException;
import com.awsfinal.awsfinaldb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody User user){
        User newUser = this.userService.addUser(user);
        return ResponseEntity.created(URI.create("http://localhost:8080/users/" + newUser.getDni())).body(newUser);
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity logicDeleteClient(@PathVariable String dni) throws ParamException {
        if (dni == null) throw new ParamException("You must specify an dni");
        return ResponseEntity.ok().body(this.userService.changeStatus(dni, false));
    }

    @PatchMapping("/{userDni}")
    public ResponseEntity modifyUser(@PathVariable String userDni, @RequestBody Map<String,Object> changes) throws ParamException{
        if (userDni == null) throw new ParamException("You must specify an dni");
        return ResponseEntity.ok().body(this.userService.update(userDni,changes));
    }

    @GetMapping(value = {"/"})
    public ResponseEntity getUsers(){
        List users = this.userService.getUsers();
        if(users.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value={"/{userDni}"})
    public ResponseEntity getUserByDni(@PathVariable String userDni){
        Optional<User> user = this.userService.getUser(userDni);
        if(user.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok().body(user);
    }




}
