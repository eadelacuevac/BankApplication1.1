package com.devsu.hackerearth.backend.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    // Método para obtener la última transacción de una cuenta y ver su saldo
    Transaction findTopByAccountIdOrderByDateDesc(Long accountId);

    // Consulta personalizada para el reporte (Requerimiento F4)
    @Query("SELECT t FROM Transaction t INNER JOIN Account a ON t.accountId = a.id WHERE a.clientId = :clientId AND t.date BETWEEN :startDate AND :endDate")
    List<Transaction> findTransactionsByClientIdAndDateBetween(@Param("clientId") Long clientId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}