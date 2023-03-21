package com.example.microservice1.Controller;

import com.example.microservice1.Entity.Candidat;
import com.example.microservice1.Service.ICandidatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "API/GestionCandidat")
public class CandidatController {
    @Autowired
    ICandidatService canService;
    @PostMapping("/AddCandidat")
    public Candidat addCandidat(@RequestBody Candidat c){
        return canService.addCandidat(c);
    }
    @GetMapping("/getOne/{id}")
    public Candidat getOne(@PathVariable("id") Long id) {
        return canService.getById(id);
    }
    @GetMapping("/getAll")
    public List<Candidat> getAll(){
        return canService.getCandidats();
    }
}
