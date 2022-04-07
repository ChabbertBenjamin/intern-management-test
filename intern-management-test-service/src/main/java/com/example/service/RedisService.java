package com.example.service;

import com.example.model.Intern;
import com.example.model.InternRedis;
import com.example.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedisService {

    @Autowired
    RedisRepository redisRepository;


    public void save(InternRedis intern){
        this.redisRepository.save(intern);
    }

    public Iterable<InternRedis> getAllIntern(){
        return this.redisRepository.findAll();
    }

}
