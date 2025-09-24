package com.impizza.impizza.repository;

import com.impizza.impizza.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdineRepository extends JpaRepository<Ordine,Integer> {
}
