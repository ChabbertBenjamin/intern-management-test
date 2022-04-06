package com.example.service;

import com.example.dto.Intern;
import com.example.mapper.InternMapper;
import com.example.repository.InternsManagementServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
public class InternsManagementService {
    @Autowired
    private InternsManagementServiceRepository internsManagementServiceRepository;

    final static Logger logger = Logger.getLogger(String.valueOf(InternsManagementService.class));

    @JmsListener(destination = "TestQueue", containerFactory = "MyQueueConsumer")
    public void receive(@Payload Intern message) {
        System.out.println("on rentre ici");
        logger.info("I just Received  " + "First_Name : " + message.getFirstName() + " | Last_Name : " + message.getLastName());
        com.example.model.Intern internEntity = InternMapper.MAPPER.fromXmlToEntityIntern(message);
        this.addAnIntern2(internEntity);
        logger.info("SAVE IN DB  " + internEntity.toString());

    }


    public void addAnIntern(Intern internXml){
        com.example.model.Intern internEntity = InternMapper.MAPPER.fromXmlToEntityIntern(internXml);
        this.internsManagementServiceRepository.save(internEntity);
    }

    public void addAnIntern2(com.example.model.Intern internEntity){
        this.internsManagementServiceRepository.save(internEntity);
    }


}
