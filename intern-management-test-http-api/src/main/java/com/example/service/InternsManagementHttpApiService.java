package com.example.service;



import com.example.dto.json.Intern;
import com.example.mapper.InternMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
@Service
public class InternsManagementHttpApiService {
    static Logger logger = Logger.getLogger(String.valueOf(InternsManagementHttpApiService.class));

    @Autowired
    private JmsProducer jmsProducer;

    @Autowired
    private RedisService redisService;

    public List<Intern> getAllInterns() {
        return null;
    }

    public void addAnIntern(com.example.dto.Intern internJson) {
        this.jmsProducer.sendMessage(internJson);
    }

    public Intern getAnInternByFirstName(String firstName) {
        return null;
    }

    public void deleteAnIntern(Long idIntern) {
    }

    public void updateAnIntern(Intern intern) {
    }

    public Intern getAnInternByIdInCacheOrDb(String internId) {
        com.example.model.Intern isInternPresentInCache = this.getCacheValueWithoutLog(internId);
        if(isInternPresentInCache != null){
            return InternMapper.MAPPER.fromEntityToJson(isInternPresentInCache);
        }else{
            return this.getAnInternById(internId);
        }
    }

    public synchronized com.example.model.Intern getCacheValueWithoutLog(String internId){
        try{
            Optional<com.example.model.Intern> intern = this.redisService.getAnInternById(internId);
            if(intern.isPresent()){
                return intern.get();
            }
            return null;
        }
        catch (Exception e){
            return null;
        }
    }
    public Intern getAnInternById(String id) {
        /*
        Prepare to send the InternXml into Get Request Queue
         */
        com.example.dto.Intern internXml = new com.example.dto.Intern();
        internXml.setIdIntern(new BigInteger(id));
        ///Send it
        this.jmsProducer.sendMessageForGetById(internXml);


        long currentTime = System.currentTimeMillis();
        long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(currentTime);

        while(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - timeSeconds < 5  &&
                this.getCacheValueWithoutLog(id) == null) {
            this.getCacheValueWithoutLog(id);
        }
        com.example.model.Intern cacheResult = this.getCacheValue(id);

        if(cacheResult == null) {
            logger.info("[TIME OUT] OCCURRED INTERN DOES NOT EXIST");
            return null;
        }
        return InternMapper.MAPPER.fromEntityToJson(cacheResult);
    }
    public synchronized com.example.model.Intern getCacheValue(String internId){
        try{
            System.out.println("[CACHE REDIS] = " + this.redisService.getAllIntern());
            Optional<com.example.model.Intern> intern = this.redisService.getAnInternById(internId);
            logger.info("[CACHE] READING " +
                    "Intern_ID : " + intern.get().getIdIntern()+
                    ", First_Name : " + intern.get().getFirstName() +
                    ", Last_Name : " + intern.get().getLastName() +"}");
            if(intern.isPresent()){
                return intern.get();
            }
            return null;
        }
        catch (Exception e){
            return null;
        }
    }
}
