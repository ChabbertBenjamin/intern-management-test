package com.example.service;

import com.example.model.Intern;
import com.example.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedisService {
    @Autowired
    private RedisRepository redisRepository;

    public Optional<Intern> getAnInternById(String id){
        return this.redisRepository.findById(Long.valueOf(id));
    }

    public Iterable<Intern> getAllIntern(){
        return this.redisRepository.findAll();
    }

    public void deleteAllIntern(){
        this.redisRepository.deleteAll();
    }


}
