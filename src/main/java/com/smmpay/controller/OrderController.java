package com.smmpay.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.smmpay.common.author.Authory;
import com.smmpay.common.encrypt.MD5;
import com.smmpay.common.request.RequestDataProxy;
import com.smmpay.inter.AuthorService;
import com.smmpay.inter.dto.res.ReturnDTO;
import com.smmpay.inter.dto.res.TrConfirmAuditDTO;
import com.smmpay.inter.dto.res.TrPaymentRecordDTO;
import com.smmpay.inter.smmpay.MallService;
import com.smmpay.inter.smmpay.OrderService;

/**
 * dubbo服务 订单类
 * @author wanghao
 *
 */
@Controller
@RequestMapping("order")
public class OrderController extends BaseController{
	
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
    protected AuthorService authorService;
	@Autowired
	OrderService orderService;
	@Autowired
	MallService mallService;
	
	@RequestMapping("/pay")
	@ResponseBody
	public ReturnDTO pay(TrPaymentRecordDTO record,HttpServletRequest request,
			HttpServletResponse response) {
		ReturnDTO dto = new ReturnDTO();
		if (validIsEmpty(record) == null){
			// Map<String, String> testMap = new LinkedHashMap<String, String>();
			// 判断服务端加密字串和请求的md5字串是否相同
			String md5Str=record.getDealMoney()+record.getDealType()+record.getPayType()+record.getNotifyUrl()+record.getMallOrderId()+record.getOrderCreateTime()
					+record.getBuyerMallUserName()+record.getSellerMallUserName()+record.getProductName()+record.getProductNum()
					+record.getProductNumUnit()+record.getProductDetail()+record.getInvoice()+record.getPaymentType()
					+record.getSettlementType()+record.getDate();
			if(record.getInvoice() == null || "".equals(record.getInvoice())){
				md5Str=record.getDealMoney()+record.getDealType()+record.getPayType()+record.getNotifyUrl()+record.getMallOrderId()+record.getOrderCreateTime()
						+record.getBuyerMallUserName()+record.getSellerMallUserName()+record.getProductName()+record.getProductNum()
						+record.getProductNumUnit()+record.getProductDetail()+record.getPaymentType()
						+record.getSettlementType()+record.getDate();
			}
			String md5 = MD5.md5(md5Str);
			log.info(md5Str);
			log.info(md5);
			log.info("md5:"+record.getMd5());
			if (record.getMd5().equals(md5)) {
				//0.01直接购买D2377007100012952015-11-13test002test013#银锭11180.0012548康元货票同行安全支付买方自提1447404867
				//String str ="dealMoney=10003&dealType=直接购买&mallOrderId=D205300910000844&orderCreateTime=2015-11-13&buyerMallUserName=test002&sellerMallUserName=test011&productName=1#锌锭" +
						//"&productNum=1&productNumUnit=10003.00&productDetail=12548&invoice=货票同行&paymentType=安全支付&settlementType=买方自提&date=1447405430" +
						//"&md5=dd89421e6638f32986949a43ccc51c30";
				StringBuffer sign = new StringBuffer();
				//ObjectMapper mapper = new ObjectMapper();
				Map<String,Object> mapParamIn = null;
				Map<String,String> mapParamOut = new LinkedHashMap<String,String>();
				Map<String,String> map = new LinkedHashMap<String,String>();
				map.put("dealMoney", String.valueOf(record.getDealMoney()));
				map.put("dealType", String.valueOf(record.getDealType()));
				map.put("payType", String.valueOf(record.getPayType()));
				map.put("mallOrderId", String.valueOf(record.getMallOrderId()));
				map.put("orderCreateTime", String.valueOf(record.getOrderCreateTime()));
				map.put("buyerMallUserName", String.valueOf(record.getBuyerMallUserName()));
				map.put("sellerMallUserName", String.valueOf(record.getSellerMallUserName()));
				map.put("productName", String.valueOf(record.getProductName()));
				map.put("productNum", String.valueOf(record.getProductNum()));
				map.put("productNumUnit", String.valueOf(record.getProductNumUnit()));
				map.put("productDetail", String.valueOf(record.getProductDetail()));
				map.put("invoice", String.valueOf(record.getInvoice()));
				map.put("notifyUrl", String.valueOf(record.getNotifyUrl()));
				map.put("paymentType", String.valueOf(record.getPaymentType()));
				map.put("settlementType", String.valueOf(record.getSettlementType()));
				map.put("date", String.valueOf(new Date().getTime()));
				// 调用服务
				if (Authory.token == null)
					    RequestDataProxy.getAccessToken(authorService);
				// 签名验证并返回map
				try{
					//log.info("mapper:"+mapper.readValue(JSONObject.toJSONString(record),LinkedHashMap.class));
					mapParamIn = getParamMap(map,sign);
					mapParamOut = RequestDataProxy.getRequestParam(JSONObject.toJSONString(mapParamIn), sign.toString());
					log.info("json:"+JSONObject.toJSONString(mapParamIn));
					log.info("sign:"+sign.toString());
				}catch(Exception e){
					e.printStackTrace();
				}
			    
				if (mapParamOut != null) {
					ReturnDTO returnDTO = orderService.insertPayOrder(mapParamOut);
					//if (!returnDTO.getStatus().equals("000000")) {
						//dto =  new ReturnDTO("000002",false,"调用失败");
					    log.info(JSONObject.toJSONString(returnDTO));
						return returnDTO;
					//} 
				} else {
					dto =  new ReturnDTO("000003",false,"解密失败");
				}
			} else {
				dto =  new ReturnDTO("000001",false,"MD5验证不通过");
			}
		} else {
			dto =  new ReturnDTO("000004",false,"参数"+validIsEmpty(record));
		}
		log.info(JSONObject.toJSONString(dto));
		return dto;
	}

	/**
	 * 确认收货
	 * @param confirmDTO
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/confirm")
	@ResponseBody
	public ReturnDTO confirm(TrConfirmAuditDTO confirmDTO,HttpServletRequest request,HttpServletResponse response){
		log.info("Access invoker");
		ReturnDTO dto = new ReturnDTO();
		
		log.info("valid:"+validIsEmpty(confirmDTO));
		if(validIsEmpty(confirmDTO) == null){
			String md5 = confirmDTO.getMallUserName() + confirmDTO.getPaymentId() 
					+ confirmDTO.getPayCode() + confirmDTO.getAuditStatus() + confirmDTO.getAuditTime()+ confirmDTO.getDate();
			if(confirmDTO.getAuditReason() != null) md5 = confirmDTO.getMallUserName() + confirmDTO.getPaymentId() 
					+ confirmDTO.getPayCode() + confirmDTO.getAuditStatus() + confirmDTO.getAuditTime()+confirmDTO.getAuditReason()+confirmDTO.getDate();
			if(confirmDTO.getMd5().equals(MD5.md5(md5))){    //MD5验证无误
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Map<String,String> paramMap = new LinkedHashMap<String,String>();
					paramMap.put("mallUserName", confirmDTO.getMallUserName());
					paramMap.put("paymentId", String.valueOf(confirmDTO.getPaymentId()));
					paramMap.put("payCode", confirmDTO.getPayCode());
					paramMap.put("mallAuditStatus", String.valueOf(confirmDTO.getAuditStatus()));
					paramMap.put("mallAuditTime", confirmDTO.getAuditTime());
					if(confirmDTO.getAuditReason() != null) paramMap.put("mallAuditReason", confirmDTO.getAuditReason());
					
					paramMap.put("date", sdf.format(new Date()));
					StringBuffer sign = new StringBuffer("");
					Map<String,Object> mapParamIn = getParamMap(paramMap,sign);
					Map<String,String> mapParamOut = new LinkedHashMap<String,String>();
					try{
						log.info("json:"+JSONObject.toJSONString(mapParamIn));
						log.info("sign:"+sign.toString());
						if(Authory.token == null) RequestDataProxy.getAccessToken(authorService);
						mapParamOut = RequestDataProxy.getRequestParam(JSONObject.toJSONString(mapParamIn),sign.toString());
						
					}catch(Exception e){
						e.printStackTrace();
					}
				    dto = mallService.callTrConfirmAudit(mapParamOut);
				    log.info("result:"+dto.getStatus() +" "+dto.getMsg() + "" +dto.getData());
				}else{
					dto =  new ReturnDTO("000001",false,"MD5验证不通过");
				}
		}else{
			dto = new ReturnDTO("000004",false,validIsEmpty(confirmDTO));
		}
		log.info(JSONObject.toJSONString(dto));
		return dto;
	}
	
	/**
	 * 支付码验证
	 * @param confirmDTO
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkPayCode")
	@ResponseBody
	public ReturnDTO checkPayCode(TrConfirmAuditDTO confirmDTO,HttpServletRequest request,HttpServletResponse response){
		log.info("Access invoker");
		ReturnDTO dto = new ReturnDTO();
		
		log.info("valid:"+validIsEmptyByCode(confirmDTO));
		if(this.validIsEmptyByCode(confirmDTO) == null){
			if(confirmDTO.getMd5().equals(MD5.md5(confirmDTO.getMallUserName() + confirmDTO.getPaymentId() 
					+ confirmDTO.getPayCode() + confirmDTO.getDate()))){    //MD5验证无误
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
					Map<String,String> paramMap = new LinkedHashMap<String,String>();
					paramMap.put("buyerMallUserName", confirmDTO.getMallUserName());
					paramMap.put("paymentId", String.valueOf(confirmDTO.getPaymentId()));
					paramMap.put("paymentCode", confirmDTO.getPayCode());
					paramMap.put("date", sdf.format(new Date()));
					StringBuffer sign = new StringBuffer("");
					Map<String,Object> mapParamIn = getParamMap(paramMap,sign);
					Map<String,String> mapParamOut = new LinkedHashMap<String,String>();
					try{
						log.info("json:"+JSONObject.toJSONString(mapParamIn));
						log.info("sign:"+sign.toString());
						if(Authory.token == null) RequestDataProxy.getAccessToken(authorService);
						mapParamOut = RequestDataProxy.getRequestParam(JSONObject.toJSONString(mapParamIn),sign.toString());
						
					}catch(Exception e){
						e.printStackTrace();
					}
				    dto = mallService.checkPayCodeFromMall(mapParamOut);
				    log.info("result:"+dto.getStatus() +" "+dto.getMsg() + "" +dto.getData());
				}else{
					dto =  new ReturnDTO("000001",false,"MD5验证不通过");
				}
		}else{
			dto = new ReturnDTO("000004",false,validIsEmpty(confirmDTO));
		}
		log.info(JSONObject.toJSONString(dto));
		return dto;
	}
	public static void main(String args[]){
		System.out.println(new Date().getTime());
		//System.out.println(MD5.md5("duqiangdq19900926@163.com1447306228704"));
		
		System.out.println(MD5.md5("10003直接购买D2053009100008442015-11-13test002test011#锌锭110003.0012548货票同行安全支付买方自提1447404867"));
	}
}
