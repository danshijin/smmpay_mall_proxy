package com.smmpay.util;

/**
 * 返回响应基类
 * @author wanghao
 */
public class ResponseMessage {
	//返回状态
	private String responseCode;
	//返回信息
	private String responseMsg;
	//返回对象
	private Object data = null;
	
	private String status;
	
	private String success;
	
	private String msg;
	
	public ResponseMessage(){}
	public ResponseMessage(String responseCode,String responseMsg,Object data){
		this.responseCode = responseCode;
		this.responseMsg = responseMsg;
		this.data = data;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
