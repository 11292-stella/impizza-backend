package com.impizza.impizza.service;

import com.impizza.impizza.dto.OrdineDto;
import com.impizza.impizza.enumeration.StatoOrdine;
import com.impizza.impizza.exception.ValidationException;
import com.impizza.impizza.model.Ordine;
import com.impizza.impizza.model.User;
import com.impizza.impizza.repository.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;

    public Ordine creaOrdine(OrdineDto dto, User clienteAutenticato){
        Ordine ordine = Ordine.builder()
                .cliente(clienteAutenticato)
                .ingredienti(dto.getIngredienti())
                .stato(StatoOrdine.IN_ATTESA).build();
        return ordineRepository.save(ordine);
    }

    public List<Ordine> getAll() {
        return ordineRepository.findAll();
    }

    public Ordine aggiornaStato(int id, StatoOrdine nuovoStato) throws ValidationException {
        Ordine ordine = ordineRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Ordine non trovato"));
        ordine.setStato(nuovoStato);
        return ordineRepository.save(ordine);
    }

    public void eliminaOrdine(int id) {
        ordineRepository.deleteById(id);
    }



}
