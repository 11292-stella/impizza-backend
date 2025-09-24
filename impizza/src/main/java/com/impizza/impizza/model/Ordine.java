package com.impizza.impizza.model;

import com.impizza.impizza.enumeration.StatoOrdine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "ordini")
@AllArgsConstructor
@NoArgsConstructor
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User cliente;

    private String ingredienti;

    private LocalDateTime dataOrdine;

    @Enumerated(EnumType.STRING)
    private StatoOrdine stato;


}
