package com.example.microservice1.ServiceImpl;

import com.example.microservice1.Entity.Candidat;
import com.example.microservice1.Repo.ICandidateRepository;
import com.example.microservice1.Service.ICandidatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CandidatServiceImpl implements ICandidatService {
    @Autowired
    ICandidateRepository candRepo;
    @Override
    public Candidat addCandidat(Candidat c) {
        return candRepo.save(c);
    }

    @Override
    public List<Candidat> getCandidats() {
        return (List<Candidat>)candRepo.findAll();
    }

    @Override
    public Candidat getById(Long Id) {
        Candidat c = candRepo.findById(Id).orElse(null);
        return c;
    }
}
