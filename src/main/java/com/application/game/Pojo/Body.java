package com.application.game.Pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Body implements Serializable {
    private static final long serialVersionUID = 1L;

    private ResultInfo resultInfo;
    private String txnToken;
    private Boolean isPromoCodeValid;
    private Boolean authenticated;
}


