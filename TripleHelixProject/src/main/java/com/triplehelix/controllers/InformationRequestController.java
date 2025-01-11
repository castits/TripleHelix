package com.triplehelix.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.triplehelix.entities.InformationRequest;
import com.triplehelix.services.InformationRequestService;

@RestController
@RequestMapping("/api/information_requests")
public class InformationRequestController {

    @Autowired
    private InformationRequestService informationRequestService;

    /**
     * Restituisce tutte le richieste di informazioni.
     *
     * @return Lista di tutte le richieste di informazioni.
     */
    @GetMapping
    public ResponseEntity<List<InformationRequest>> getAllRequests() {
        List<InformationRequest> requests = informationRequestService.getAllInformationRequests();
        return ResponseEntity.ok(requests);
    }

    /**
     * Restituisce una specifica richiesta di informazioni tramite ID.
     *
     * @param id ID della richiesta.
     * @return Richiesta di informazioni con l'ID specificato.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InformationRequest> getRequestById(@PathVariable int id) {
        Optional<InformationRequest> request = informationRequestService.getInformationRequestById(id);
        if (request.isPresent()) {
            return ResponseEntity.ok(request.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea una nuova richiesta di informazioni.
     *
     * @param request Oggetto InformationRequest inviato nel body della richiesta.
     * @return Richiesta di informazioni appena creata.
     */
    @PostMapping
    public ResponseEntity<InformationRequest> createRequest(@RequestBody InformationRequest request) {
        InformationRequest createdRequest = informationRequestService.createInformationRequest(request);
        return ResponseEntity.ok(createdRequest);
    }

    /**
     * Aggiorna una richiesta di informazioni esistente.
     *
     * @param id ID della richiesta da aggiornare.
     * @param updatedRequest Oggetto InformationRequest con i dati aggiornati.
     * @return Richiesta di informazioni aggiornata.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InformationRequest> updateRequest(@PathVariable int id, @RequestBody InformationRequest updatedRequest) {
        try {
            InformationRequest updated = informationRequestService.updateInformationRequest(id, updatedRequest);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina una richiesta di informazioni esistente.
     *
     * @param id ID della richiesta da eliminare.
     * @return Risposta vuota con lo stato HTTP appropriato.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable int id) {
        try {
            informationRequestService.deleteInformationRequest(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
