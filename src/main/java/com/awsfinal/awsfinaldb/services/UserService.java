package com.awsfinal.awsfinaldb.services;

import com.awsfinal.awsfinaldb.domain.Profession;
import com.awsfinal.awsfinaldb.domain.User;
import com.awsfinal.awsfinaldb.repositories.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final ProfessionService professionService;

    @Autowired
    public UserService(IUserRepository userRepository, ProfessionService professionService){
        this.userRepository = userRepository;
        this.professionService = professionService;
    }
    public List getUsers(){
        return this.userRepository.findAll();
    }

    public Optional<User> getUser(String userDni){
        return this.userRepository.findByDni(userDni);
    }
    @Transactional
    public User addUser(User user) throws DataAccessException {
        user.setUserStatus(true);
        Profession profession = this.professionService.addProfession(user.getProfession());
        user.setProfession(profession);
        return userRepository.save(user);
    }

    @Transactional
    public User update(String dni, Map<String, Object> changes) throws DataAccessException{
        User user = userRepository.findByDni(dni).orElseThrow();
        changes.forEach(
                (change,value) ->  {
                    switch (change){
                        case "dni": user.setDni((String) value); break;
                        case "firstName": user.setFirstName((String) value); break;
                        case "lastName": user.setLastName((String) value); break;
                        case "email": user.setEmail((String) value); break;
                        case "birthdate":
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                            try {
                                user.setBirthdate(sdf.parse((String)value));
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "phoneNumber": user.setPhoneNumber((String) value); break;
                        case "profession":
                            if (value instanceof Map<?, ?> professionInfo) {
                                Object professionName = professionInfo.get("professionName");
                                if (professionName instanceof String) {
                                    Profession profession = this.professionService.addProfession(new Profession((String) professionName));
                                    user.setProfession(profession);
                                }
                            }
                            break;
                    }
                }
        );
        return userRepository.save(user);
    }
    public User changeStatus(String dni, boolean status) throws DataAccessException {
        User user = userRepository.findByDni(dni).orElseThrow();
        user.setUserStatus(status);
        return userRepository.save(user);
    }
}
