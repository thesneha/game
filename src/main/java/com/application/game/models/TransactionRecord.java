package com.application.game.models;


import com.application.game.Enums.TransactionStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TransactionRecord {

     @Id
     @GeneratedValue
     private Integer id;

     private String orderId;

     private String userId;

     private String txnToken;

     private String txnAmount;

     private long createdat;

     private long successAt;

     private long failedAt;

     @Enumerated(EnumType.STRING)
     private TransactionStatus transactionStatus;



}

