package com.application.game.repository;

import com.application.game.models.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord,Integer> {

}
