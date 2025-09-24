package com.impizza.impizza.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrdineDto {



    @NotEmpty(message = "Il tipo di ingrediente è obbligatorio")
    private String ingredienti;
}
