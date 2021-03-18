package com.application.game.Pojo;

import com.application.game.Response.UserToken;
import com.application.game.models.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Head implements Serializable {
    private static final long serialVersionUID = 1L;

    private String responseTimestamp;

    private String version;

    private String clientId;

    private String signature;



    public  static Head from(JSONObject jsonObject)
    {
        return Head.builder()
                .responseTimestamp(jsonObject.getString("responseTimestamp"))
                .version(jsonObject.getString("version"))
                .clientId(jsonObject.getString("clientId"))
                .signature(jsonObject.getString("signature"))
                .build();
    }

}
