package com.example.microservice1.Repo;

import com.example.microservice1.Entity.Candidat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICandidateRepository  extends CrudRepository<Candidat,Long> {
}
