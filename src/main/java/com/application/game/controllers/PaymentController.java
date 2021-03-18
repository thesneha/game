package com.application.game.controllers;

import com.application.game.Response.EntityResponse;
import com.application.game.Response.PaymentResponseOfInitiateTransactionApi;
import com.application.game.models.TransactionRecord;
import com.application.game.repository.TransactionRecordRepository;
import com.application.game.request.PaymentRequest;
import com.application.game.request.ResponseFromPaytmRequest;
import com.application.game.service.PaymentGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gameApplication")
public class PaymentController {

    @Autowired
    PaymentGatewayService paymentGatewayService;

    @Autowired
    TransactionRecordRepository transactionRecordRepository;

    @RequestMapping(value ="/paymentInitiate", method = RequestMethod.POST)
    public ResponseEntity<EntityResponse> paymentInitiate(@RequestBody PaymentRequest paymentRequest) throws Exception {

        PaymentResponseOfInitiateTransactionApi paymentResponseOfInitiateTransactionApi =paymentGatewayService.initiateTransactionApi(paymentRequest);
        if (!(paymentResponseOfInitiateTransactionApi.getResultStatus()).equals("S")) {
            return new ResponseEntity<>(new EntityResponse(true, "txnToken is not created"), HttpStatus.OK);
        }

        return new ResponseEntity<>(new EntityResponse(true, "txnToken is created"), HttpStatus.OK);
    }

    @RequestMapping(value ="/paymentVerification", method = RequestMethod.POST)
    public ResponseEntity<EntityResponse> paymentVerification(@RequestBody ResponseFromPaytmRequest responseFromPaytmRequest) throws Exception {

        String resultStatus=  paymentGatewayService.validateTransaction(responseFromPaytmRequest);
        if (resultStatus.equals("TXN_SUCCESS"))
        {
            return new ResponseEntity<>(new EntityResponse(true,"transaction successfull"), HttpStatus.OK);
        }
        if (resultStatus.equals("PENDING"))
        {
            return new ResponseEntity<>(new EntityResponse(true,"transaction is pending"), HttpStatus.OK);
        }

        return new ResponseEntity<>(new EntityResponse(true,"transaction failure"), HttpStatus.OK);
    }



}
