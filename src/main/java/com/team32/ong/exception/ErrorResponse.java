package com.team32.ong.exception;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ErrorResponse {

	@ApiModelProperty(notes="Error Code", name="code", value="200")
    private int status;
	@ApiModelProperty(notes="Error Date", name="date", value="10/10/1010 - 10:10:10")
    private Date timestamp;
	@ApiModelProperty(notes="Message", name="message", value="200")
    private String message; 
	@ApiModelProperty(notes="Path", name="path", value="/api/v1/categories/")
    private String path;
    
	public ErrorResponse(int status, Date timestamp, String message, String path) {
		this.status = status;
		this.timestamp = timestamp;
		this.message = message;
		this.path = path;
	};
}