package com.awsfinal.awsfinaldb.services;

import com.awsfinal.awsfinaldb.domain.Profession;
import com.awsfinal.awsfinaldb.repositories.IProfessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ProfessionService {
    private IProfessionRepository professionRepository;

    @Autowired
    public ProfessionService(IProfessionRepository professionRepository){
        this.professionRepository = professionRepository;
    }

    public Profession addProfession(Profession profession) throws DataAccessException {
        return professionRepository.findByProfessionName(profession.getProfessionName())
                .orElseGet(() -> {
                    Profession newProfession = new Profession();
                    newProfession.setProfessionName(profession.getProfessionName());
                    return professionRepository.save(newProfession);
                });
    }
}
