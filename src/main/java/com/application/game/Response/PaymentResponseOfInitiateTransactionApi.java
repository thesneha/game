package com.application.game.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseOfInitiateTransactionApi {

    private String txnToken;
    private String resultStatus;
  //  private String transactionRecordId;
}
