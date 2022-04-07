package com.example.service;

import com.example.dto.Intern;
import com.example.mapper.InternMapper;
import com.example.model.InternRedis;
import com.example.repository.InternsManagementServiceRepository;
import com.example.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;


@Service
public class InternsManagementService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private InternsManagementServiceRepository internsManagementServiceRepository;

    final static Logger logger = Logger.getLogger(String.valueOf(InternsManagementService.class));

    @JmsListener(destination = "PostRequestProducer", containerFactory = "PostRequestConsumer")
    public void receivePost(@Payload Intern internXml) {
        logger.info("[CONSUMING : POST] {" + "First_Name : " + internXml.getFirstName() + "Last_Name : " + internXml.getLastName()+ "}");
        com.example.model.Intern internEntity = InternMapper.MAPPER.fromXmlToEntityIntern(internXml);
        this.addAnIntern(internEntity);
        logger.info("SAVE IN DB " + internEntity.toString());

    }
    @JmsListener(destination = "GetRequestProducer", containerFactory = "GetRequestConsumer")
    public void receiveGet(@Payload Intern internXml) {
        logger.info("[CONSUMING : GET] {" + "Intern_ID : " + internXml.getIdIntern() + "}");
        com.example.model.Intern internEntity = InternMapper.MAPPER.fromXmlToEntityIntern(internXml);

        Optional<com.example.model.Intern> internResultGetById = this.getAnInternById(internEntity.getIdIntern());
        if (internResultGetById.isPresent()) {
            logger.info("[SEARCHING IN DB] : FOUND" +
                    ", First_Name : " + internResultGetById.get().getFirstName() +
                    ", Last_Name : " + internResultGetById.get().getLastName() + "}");
            //com.example.model.Intern internInCache = InternMapper.MAPPER.fromXmlToEntityIntern(internResultGetById.get());
            //this.redisRepository.deleteAll();
            InternRedis internRedis = new InternRedis(internResultGetById.get().getIdIntern(),internResultGetById.get().getFirstName(),internResultGetById.get().getLastName());
            this.redisService.save(internRedis);
/*
            Iterable<com.example.model.InternRedis> internInCache =this.redisService.getAllIntern();
            for (com.example.model.InternRedis i: internInCache) {
                System.out.print("id = " + i.getIdIntern());
                System.out.print(", firstname = " + i.getFirstName());
                System.out.println(", lastname = " + i.getLastName());
            }*/
            logger.info("[CACHE] ADDED {" +
                    "Intern_ID : " + internResultGetById.get().getIdIntern() +
                    ", First_Name : " + internResultGetById.get().getFirstName() +
                    ", Last_Name : " + internResultGetById.get().getLastName() +"}");
        } else {
            logger.warning("ID DOES NOT EXIST");
        }
    }

    public Optional<com.example.model.Intern> getAnInternById(Long id){
        return this.internsManagementServiceRepository.findById(id);
    }


    public void addAnIntern(com.example.model.Intern internEntity){
        this.internsManagementServiceRepository.save(internEntity);
    }


}
