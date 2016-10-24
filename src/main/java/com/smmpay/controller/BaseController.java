package com.smmpay.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.smmpay.common.encrypt.MD5;
import com.smmpay.common.request.RequestDataProxy;
import com.smmpay.inter.dto.res.TrConfirmAuditDTO;
import com.smmpay.inter.dto.res.TrPaymentRecordDTO;

/**
 * 
 * @author youxiaoshuang
 * 
 */
public class BaseController {
	
	
	/**
	 * 验证访问的参数MD5
	 * 
	 * @param paramList
	 * @param inMd5
	 * @return
	 */
	public boolean checkMD5(List<Map<String,String>> paramList, String inMd5) {
		Map<String,String> amap = paramList.get(0);
		StringBuffer sb = new StringBuffer();
		for (Entry<String, String> ent : amap.entrySet()) {
			sb.append(ent.getValue());
		}
		
		System.out.println("MD5值："+MD5.md5(sb.toString()));
		return MD5.md5(sb.toString()).equals(inMd5);
	}

	/**
	 * 返回参数拼接字符串
	 */
	public String reqPramToString(List<Map<String,String>> paramList) {
		StringBuffer sb = new StringBuffer();
		Map<String,String> amap = paramList.get(0);
		for (Entry<String, String> ent : amap.entrySet()) {
			sb.append(ent.getValue());
		}
		System.out.println("参数拼接："+sb.toString());
		return sb.toString();
	}

	/**
	 * 将map转换为json字符串
	 * 
	 * @param map
	 * @return
	 */
	public String toJsonString(Map<String, Object> map) {
		return JSON.toJSONString(map, true);
	}

	public Map<String, String> requestDataProxy(List<Map<String,String>> paramList) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("data", paramList);
		System.out.println("RequestDataProxy传入的JSON值"+toJsonString(paramMap));
		System.out.println("传入的Json串"+reqPramToString(paramList));
 		try {
			return RequestDataProxy.getRequestParam(toJsonString(paramMap), reqPramToString(paramList));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	protected String validIsEmpty(TrConfirmAuditDTO confirmDTO){
		
	    if(confirmDTO.getPaymentId() == null || "".equals(confirmDTO.getPaymentId())){
	    	return "paymentId is null";
	    }else if(confirmDTO.getMallUserName() == null || "".equals(confirmDTO.getMallUserName())){
	    	return "mallUserName is null";
	    }else if(confirmDTO.getPayCode() == null || "".equals(confirmDTO.getPayCode())){
	    	return "payCode is null";
	    }else if(confirmDTO.getAuditStatus() == null || "".equals(confirmDTO.getAuditStatus())){
	    	return "auditStatus is null";
	    }else if(confirmDTO.getAuditTime() == null || "".equals(confirmDTO.getAuditTime())){
	    	return "auditTime is null";
	    }else if(confirmDTO.getDate() == null || "".equals(confirmDTO.getDate())){
	    	return "date is null";
	    }else if(confirmDTO.getMd5() == null || "".equals(confirmDTO.getMd5())){
	    	return "md5 is null";
	    }
	    return null;
	}
	
     protected String validIsEmpty(TrPaymentRecordDTO record){
		
	    if(record.getDealMoney()== null || "".equals(record.getDealMoney())){
	    	return "dealMoney is null";
	    }else if(record.getDealType() == null || "".equals(record.getDealType())){
	    	return "dealType is null";
	    }else if(record.getPayType() == null || "".equals(record.getPayType())){
	    	return "payType is null";
	    }else if(record.getNotifyUrl() == null || "".equals(record.getNotifyUrl())){
	    	return "notifyUrl is null";
	    }else if(record.getMallOrderId() == null || "".equals(record.getMallOrderId())){
	    	return "mallOrderId is null";
	    }else if(record.getOrderCreateTime() == null || "".equals(record.getOrderCreateTime())){
	    	return "orderCreateTime is null";
	    }else if(record.getBuyerMallUserName() == null || "".equals(record.getBuyerMallUserName())){
	    	return "buyerMallUserName is null";
	    }else if(record.getSellerMallUserName() == null || "".equals(record.getSellerMallUserName())){
	    	return "sellerMallUserName is null";
	    }else if(record.getProductName() == null || "".equals(record.getProductName())){
	    	return "productName is null";
	    }else if(record.getProductNum() == null || "".equals(record.getProductNum())){
	    	return "productNum is null";
	    }else if(record.getProductNumUnit() == null || "".equals(record.getProductNumUnit())){
	    	return "productNumUnit is null";
	    }else if(record.getProductDetail() == null || "".equals(record.getProductDetail())){
	    	return "productDetail is null";
	    }else if(record.getPaymentType()== null || "".equals(record.getPaymentType())){
	    	return "paymentType is null";
	    }else if(record.getSettlementType() == null || "".equals(record.getSettlementType())){
	    	return "settlementType is null";
	    }else if(record.getDate() == null || "".equals(record.getDate())){
	    	return "date is null";
	    }else if(record.getMd5() == null || "".equals(record.getMd5())){
	    	return "md5 is null";
	    }
	    return null;
	}
	
    protected String validIsEmptyByCode(TrConfirmAuditDTO confirmDTO){
	    if(confirmDTO.getPaymentId() == null || "".equals(confirmDTO.getPaymentId())){
	    	return "paymentId is null";
	    }else if(confirmDTO.getMallUserName() == null || "".equals(confirmDTO.getMallUserName())){
	    	return "mallUserName is null";
	    }else if(confirmDTO.getPayCode() == null || "".equals(confirmDTO.getPayCode())){
	    	return "payCode is null";
	    }else if(confirmDTO.getDate() == null || "".equals(confirmDTO.getDate())){
	    	return "date is null";
	    }else if(confirmDTO.getMd5() == null || "".equals(confirmDTO.getMd5())){
	    	return "md5 is null";
	    }
	    return null;
	}
	
   
	
    protected String getSignStringByMap(Map<String,String> param){
		StringBuffer sb = new StringBuffer("");
	    for(Map.Entry<String, String> env:param.entrySet()){
	    	if(!env.getKey().equals("md5")){
	    		sb.append(String.valueOf(env.getValue()));
	    	}
	    }
	    return sb.toString();
	}
	
    protected Map<String,Object> getParamMap(Map<String,String> map,StringBuffer sb){
    	Map<String,Object> mapParam =new HashMap<String,Object>();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		list.add(map);
		mapParam.put("data", list);
		
		for(String key : map.keySet()){
			System.out.println("key:"+key);
			//if(!key.equals("mallAuditReason")) 
				sb.append(map.get(key));
		}
		return mapParam;
    }
	
}
