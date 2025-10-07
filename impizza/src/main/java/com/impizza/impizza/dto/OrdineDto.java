package com.impizza.impizza.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrdineDto {



    @NotEmpty(message = "Almeno un ingrediente è obbligatorio")
    private List<String> ingredienti;
}
