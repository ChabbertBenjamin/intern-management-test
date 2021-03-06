package com.example.controller;

import com.example.dto.json.Intern;
import com.example.service.InternsManagementHttpApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping()
@RestController
public class InternsManagementHttpApiController {

    @Autowired
    private InternsManagementHttpApiService internsManagementHttpApiService;

    @GetMapping("interns")
    public List<Intern> getAllInterns() {
        return this.internsManagementHttpApiService.getAllInterns();
    }

    @GetMapping("intern/{internId}")
    public Intern getAnInternById(@PathVariable String internId){
        return this.internsManagementHttpApiService.getAnInternByIdInCacheOrDb(internId);
    }

    @GetMapping("intern")
    public Intern getAnInternByFirstNameUsingRequestParam(@RequestParam String firstName){
        return this.internsManagementHttpApiService.getAnInternByFirstName(firstName);
    }

    @PostMapping("intern")
    public String addAnIntern(@RequestBody com.example.dto.Intern intern) {
        this.internsManagementHttpApiService.addAnIntern(intern);
        return "Your request has been taken in account";
    }

    @DeleteMapping("intern/{idIntern}")
    public void deleteAnIntern(@PathVariable Long idIntern) {
        this.internsManagementHttpApiService.deleteAnIntern(idIntern);
    }

    @PutMapping("intern")
    public void updateAnIntern(@RequestBody Intern intern){
        this.internsManagementHttpApiService.updateAnIntern(intern);
    }
}
