package com.application.game.service;

import com.application.game.Enums.TransactionStatus;
import com.application.game.Pojo.Body;
import com.application.game.Pojo.ResultInfo;
import com.application.game.Response.PaymentResponseOfInitiateTransactionApi;
import com.application.game.models.PaymentGateway;
import com.application.game.models.TransactionRecord;
import com.application.game.repository.TransactionRecordRepository;
import com.application.game.request.PaymentRequest;
import com.application.game.request.ResponseFromPaytmRequest;
import com.application.game.utils.JsonUtil;
import com.paytm.pg.merchant.PaytmChecksum;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class PaymentGatewayService {

    @Autowired
    PaymentGateway paymentGateway;

    @Autowired
    TransactionRecordRepository transactionRecordRepository;


    public PaymentResponseOfInitiateTransactionApi initiateTransactionApi(PaymentRequest paymentRequest) throws Exception {

        TransactionRecord transactionRecord=new TransactionRecord();
        transactionRecord.setOrderId(paymentRequest.getOrderId());
        transactionRecord.setUserId(paymentRequest.getCustId());
        transactionRecord.setTxnAmount(paymentRequest.getTxnValue());
        transactionRecord.setCreatedat(System.currentTimeMillis()/1000);
       transactionRecord.setTransactionStatus(TransactionStatus.INITIATED);
        transactionRecord =transactionRecordRepository.save(transactionRecord);

        JSONObject paytmParams = new JSONObject();

        JSONObject body = new JSONObject();
        body.put("requestType", "Payment");
        body.put("mid", paymentGateway.getMerchantId());
        body.put("websiteName", paymentGateway.getWebsite());
        body.put("orderId", paymentRequest.getOrderId());
        body.put("callbackUrl", paymentGateway.getCallbackUrl());

        JSONObject txnAmount = new JSONObject();
        txnAmount.put("value", paymentRequest.getTxnValue());
        txnAmount.put("currency", "INR");

        JSONObject userInfo = new JSONObject();
        userInfo.put("custId", paymentRequest.getCustId());
        body.put("txnAmount", txnAmount);
        body.put("userInfo", userInfo);

        /*
         * Generate checksum by parameters we have in body
         * You can get Checksum JAR from https://developer.paytm.com/docs/checksum/
         * Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys
         */

       // String checksum = PaytmChecksum.generateSignature(body.toString(), "YOUR_MERCHANT_KEY");

        String checksum = null;
        try {
            checksum = PaytmChecksum.generateSignature(body.toString(), "" + paymentGateway.getMerchantKey());
        } catch (Exception e) {
            throw  new Exception("Error generating checkSum!");
        }

        JSONObject head = new JSONObject();
        head.put("signature", checksum);

        paytmParams.put("body", body);
        paytmParams.put("head", head);

        String post_data = paytmParams.toString();

        /* for Staging */
        URL url = new URL("https://securegw-stage.paytm.in/theia/api/v1/initiateTransaction?mid="+paymentGateway.getMerchantId()+"&orderId="+paymentRequest.getOrderId());

        /* for Production */
       // URL url = new URL("https://securegw.paytm.in/theia/api/v1/initiateTransaction?mid=YOUR_MID_HERE&orderId=ORDERID_98765");

        String responseData = "";
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
            requestWriter.writeBytes(post_data);
            requestWriter.close();
           // String responseData = "";
            InputStream is = connection.getInputStream();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
            if ((responseData = responseReader.readLine()) != null) {
               System.out.println(responseData);
            }
            responseReader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        Body b= JsonUtil.getObjectFromJsonString(responseData, Body.class);
        System.out.println(b.getTxnToken());
//        System.out.println(resultInfo.getResultStatus());
//        PymentResponse pymentResponse=JsonUtil.getObjectFromJsonString(responseData,PymentResponse.class);
//        Body b=JsonUtil.getObjectFromJsonString(responseData,Body.class);
//        System.out.println(pymentResponse.getBody().getResultInfo().getResultMsg());
//        System.out.println(b.getResultInfo().getResultStatus());
        JSONObject obj = new JSONObject(responseData);
        PaymentResponseOfInitiateTransactionApi paymentResponseOfInitiateTransactionApi= new PaymentResponseOfInitiateTransactionApi();
        String resultStatus=obj.getJSONObject("body").getJSONObject("resultInfo").getString("resultStatus");
        if (resultStatus.equals("F"))
        {
            paymentResponseOfInitiateTransactionApi.setResultStatus(resultStatus);
            transactionRecord.setFailedAt(System.currentTimeMillis()/1000);
            transactionRecord.setTransactionStatus(TransactionStatus.FAIL);
            return paymentResponseOfInitiateTransactionApi;
        }
        paymentResponseOfInitiateTransactionApi.setResultStatus(resultStatus);
        paymentResponseOfInitiateTransactionApi.setTxnToken(obj.getJSONObject("body").getString("txnToken"));
        transactionRecord.setTxnToken(obj.getJSONObject("body").getString("txnToken"));
        transactionRecord.setTransactionStatus(TransactionStatus.PROCESSING);

        return paymentResponseOfInitiateTransactionApi;

    }

    public String validateTransaction(ResponseFromPaytmRequest responseFromPaytmRequest) throws Exception {

        JSONObject body = new JSONObject();
        body.put("mid", responseFromPaytmRequest.getMId());
        body.put("orderId",responseFromPaytmRequest.getOrderId());//order id from request is valid or not
        String checksum = null;
        try {
            checksum = PaytmChecksum.generateSignature(body.toString(), "" + paymentGateway.getMerchantKey());
        } catch (Exception e) {
            throw  new Exception("Error generating checkSum!");
        }
         String paytmCheckSum =responseFromPaytmRequest.getCheckSumHash();
        boolean isVerifySignature = PaytmChecksum.verifySignature(checksum, paymentGateway.getMerchantKey(), paytmCheckSum);
        if (!isVerifySignature) {
           throw new Exception( "Checksum is not Matched");
        }
        String res= transactionStatus(responseFromPaytmRequest);
        return res;
    }



    public String transactionStatus(ResponseFromPaytmRequest responseFromPaytmRequest) throws Exception {
        JSONObject paytmParams = new JSONObject();
        JSONObject body = new JSONObject();
        body.put("mid", responseFromPaytmRequest.getMId());

        body.put("orderId", responseFromPaytmRequest.getOrderId());
        String checksum=null;
        try {
             checksum = PaytmChecksum.generateSignature(body.toString(), paymentGateway.getMerchantKey());
        }
        catch (Exception e)
        {
            throw new Exception("checksum is not created");
        }
        JSONObject head = new JSONObject();
        head.put("signature", checksum);
        paytmParams.put("body", body);
        paytmParams.put("head", head);
        String post_data = paytmParams.toString();

        /* for Staging */
        URL url = new URL("https://securegw-stage.paytm.in/v3/order/status");
        String responseData = "";

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
            requestWriter.writeBytes(post_data);
            requestWriter.close();

            InputStream is = connection.getInputStream();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
            if ((responseData = responseReader.readLine()) != null) {
                System.out.println(responseData);
            }
            responseReader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        JSONObject obj = new JSONObject(responseData);
        String resultStatus=obj.getJSONObject("body").getJSONObject("resultInfo").getString("resultStatus");
        return resultStatus;
    }

}


