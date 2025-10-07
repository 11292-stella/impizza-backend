package com.impizza.impizza.model;

import com.impizza.impizza.enumeration.StatoOrdine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @ElementCollection
    private List<String> ingredienti;

    private LocalDateTime dataOrdine;

    @Enumerated(EnumType.STRING)
    private StatoOrdine stato;


}
