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
    @Qualifier("PostRequestProducer")
    private JmsTemplate jmsTemplateForPostProducer;

    @Autowired
    @Qualifier("GetRequestProducer")
    private JmsTemplate jmsTemplateForGetProducer;

    final Logger logger = Logger.getLogger(String.valueOf(JmsProducer.class));

    public void sendMessage(Intern internJson) {
        com.example.dto.Intern internXml = InternMapper.MAPPER.fromJsonToXmlIntern(internJson);
        jmsTemplateForPostProducer.convertAndSend(internXml);
        logger.info("[PRODUCING : POST] {" +"firstname : " + internXml.getFirstName() + " | lastname :" + internXml.getLastName() +"}");
    }

    public void sendMessageForGetById(Intern internXml) {
        logger.info("[PRODUCING : GET] {" +"Intern_ID : " + internXml.getIdIntern() + "}");
        jmsTemplateForGetProducer.convertAndSend(internXml);

    }
}
