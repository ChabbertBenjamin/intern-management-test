package com.example.service;



import com.example.dto.Intern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class InternsManagementHttpApiService {
    @Autowired
    private JmsProducer jmsProducer;

    public List<Intern> getAllInterns() {
        return null;
    }

    public void addAnIntern(Intern internJson) {
        this.jmsProducer.sendMessage(internJson);
    }


    public Intern getAnInternByFirstName(String firstName) {
        return null;
    }

    public void deleteAnIntern(Long idIntern) {
    }

    public void updateAnIntern(Intern intern) {
    }
}
