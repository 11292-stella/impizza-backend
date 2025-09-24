package com.impizza.impizza.controller;

import com.impizza.impizza.dto.OrdineDto;
import com.impizza.impizza.enumeration.StatoOrdine;
import com.impizza.impizza.exception.ValidationException;
import com.impizza.impizza.model.Ordine;
import com.impizza.impizza.model.User;
import com.impizza.impizza.service.OrdineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordini")
public class OrdineController {

    @Autowired
    private OrdineService ordineService;

    // Cliente invia ordine
    @PostMapping
    @PreAuthorize("hasAuthority('CLIENTE')")
    public Ordine creaOrdine(@RequestBody @Valid OrdineDto dto,
                             BindingResult result,
                             @AuthenticationPrincipal User clienteAutenticato) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Dati ordine non validi");
        }
        return ordineService.creaOrdine(dto, clienteAutenticato);
    }

    // Admin vede tutti gli ordini
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Ordine> getAll() {
        return ordineService.getAll();
    }

    // Admin aggiorna stato
    @PutMapping("/{id}/stato")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Ordine aggiornaStato(@PathVariable int id, @RequestParam StatoOrdine stato) throws ValidationException {
        return ordineService.aggiornaStato(id, stato);
    }

    // Admin elimina ordine
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void elimina(@PathVariable int id) {
        ordineService.eliminaOrdine(id);
    }
}
