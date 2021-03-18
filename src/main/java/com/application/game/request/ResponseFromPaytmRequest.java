package com.application.game.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseFromPaytmRequest {

    private String mId;
    private String txnId;
    private String orderId;
    private String bankTxnId;
    private String txnAmount;
    private String currency;
    private String status;
    private String respCode;
    private String respMsg;
    private Date txnDate;
    private String gateWayName;
    private String bankName;
    private String paymentMode;
    private String checkSumHash;

}
