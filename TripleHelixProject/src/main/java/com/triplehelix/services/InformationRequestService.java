package com.triplehelix.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.triplehelix.entities.InformationRequest;
import com.triplehelix.repos.InformationRequestDAO;

@Service
public class InformationRequestService {

    @Autowired
    private InformationRequestDAO informationRequestDAO;

   
    public List<InformationRequest> getAllInformationRequests() {
        return informationRequestDAO.findAll();
    }

    public Optional<InformationRequest> getInformationRequestById(int id) {
        return informationRequestDAO.findById(id);
    }

    public InformationRequest createInformationRequest(InformationRequest informationRequest) {
        return informationRequestDAO.save(informationRequest);
    }

    public InformationRequest updateInformationRequest(int id, InformationRequest updatedRequest) {
        Optional<InformationRequest> existingRequestOpt = informationRequestDAO.findById(id);

        if (existingRequestOpt.isPresent()) {
            InformationRequest existingRequest = existingRequestOpt.get();

            existingRequest.setRequestId(updatedRequest.getRequestId());
            existingRequest.setInformationRequestText(updatedRequest.getInformationRequestText());
            existingRequest.setUserName(updatedRequest.getUserName());
            existingRequest.setUserSurname(updatedRequest.getUserSurname());
            existingRequest.setUserEmail(updatedRequest.getUserEmail());
            existingRequest.setUserPhone(updatedRequest.getUserPhone());

            return informationRequestDAO.save(existingRequest);
        } else {
            throw new IllegalArgumentException("Richiesta di informazioni con ID " + id + " non trovata.");
        }
    }


    public void deleteInformationRequest(int id) {
        if (informationRequestDAO.existsById(id)) {
            informationRequestDAO.deleteById(id);
        } else {
            throw new IllegalArgumentException("Richiesta di informazioni con ID " + id + " non trovata.");
        }
    }
}
