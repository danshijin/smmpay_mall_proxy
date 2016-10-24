package com.smmpay.util;

public class Msg {

	    //返回状态
		private String status;
		//返回信息
		private String success;
		//返回对象
		private Object msg;
		
		public Msg(){}

		public Msg(String status, String success, Object msg) {
			super();
			this.status = status;
			this.success = success;
			this.msg = msg;
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

		public Object getMsg() {
			return msg;
		}

		public void setMsg(Object msg) {
			this.msg = msg;
		}
		
}
