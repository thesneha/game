package com.application.game.Response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class EntityResponse {

	private Boolean status;
	private String message;
	private Object data;


	public EntityResponse() {
	}

//	public EntityResponse(String statusCode, String message) {
//		this.statusCode = statusCode;
//		this.message = message;
//	}
//
//	public EntityResponse(String statusCode, Object content, String message) {
//		this.statusCode = statusCode;
//		this.content = content;
//		this.message = message;
//	}

	public EntityResponse( Boolean status,String message) {
		this.status=status;
		this.message = message;

	}

	public EntityResponse( Boolean status,String message,Object content) {
		//this.statusCode = statusCode;
		this.status=status;
		this.message = message;
		this.data = content;
	}

}
