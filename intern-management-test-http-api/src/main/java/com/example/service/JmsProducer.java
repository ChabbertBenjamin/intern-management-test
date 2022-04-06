package com.example.service;

import com.example.dto.Intern;
import com.example.dto.mapper.InternMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class JmsProducer {
    @Autowired
    @Qualifier("MyQueueProducer")
    private JmsTemplate jmsTemplate;

    final Logger logger = Logger.getLogger(String.valueOf(JmsProducer.class));

    public void sendMessage(Intern internJson) {
        com.example.dto.Intern internXml = InternMapper.MAPPER.fromJsonToXmlIntern(internJson);
        jmsTemplate.convertAndSend(internXml);
        logger.info("New intern send " +"firstname : " + internXml.getFirstName() + " | lastname : " + internXml.getLastName() +" to the BROKER]");
    }
}
