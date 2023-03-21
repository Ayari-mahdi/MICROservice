package com.example.microservice1.Service;

import com.example.microservice1.Entity.Candidat;

import java.util.List;

public interface ICandidatService {
    Candidat addCandidat(Candidat c);
    List<Candidat> getCandidats();
    Candidat getById(Long Id);

}
