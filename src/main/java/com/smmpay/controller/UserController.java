package com.smmpay.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.smmpay.common.author.Authory;
import com.smmpay.common.encrypt.MD5;
import com.smmpay.common.request.RequestDataProxy;
import com.smmpay.inter.AuthorService;
import com.smmpay.inter.dto.res.MallUserRecordDTO;
import com.smmpay.inter.dto.res.ReturnDTO;
import com.smmpay.inter.smmpay.UserAccountService;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	AuthorService authorService;
	@Autowired
	UserAccountService userAccountService;

	@RequestMapping("/register")
	@ResponseBody
	public  ReturnDTO register(@ModelAttribute MallUserRecordDTO record,HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		String mallUserName = request.getParameter("mallUserName");
//		String realName = request.getParameter("realName");
//		String email = request.getParameter("email");
//		String date = request.getParameter("date");
		String md5 = request.getParameter("md5");// cee47d08b9002e6813395a7ca3074128
		ReturnDTO dto = new ReturnDTO();
		log.info("realName"+record.getRealName()) ;
		log.info("realName1"+request.getParameter("realName")) ; 
		if (record.getMallUserName() != null && record.getEmail() != null && record.getDate() != null
				&& record.getRealName() != null && md5 != null) {
			
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("mallUserName", record.getMallUserName());
			map.put("realName", record.getRealName());
			map.put("email", record.getEmail());
			map.put("date", record.getDate());

		    list.add(map);
			log.info("mallUserName"+record.getMallUserName());
			log.info("realName"+record.getRealName());
			log.info("email"+record.getEmail());
			log.info("date"+record.getDate());
			log.info("md5"+md5);
			
            log.info("MD5_new"+MD5.md5(record.getMallUserName() + record.getRealName() + record.getEmail() + record.getDate()));
			// 判断服务端加密字串和请求的md5字串是否相同
			if (MD5.md5(record.getMallUserName() + record.getRealName() + record.getEmail() + record.getDate()).equals(md5)) {
				// 调用服务
				if (Authory.token == null)
					RequestDataProxy.getAccessToken(authorService);
					// 签名验证并返回map
					Map<String, String> setMap = requestDataProxy(list);
				
					if (setMap != null) {
						ReturnDTO returnDTO = userAccountService
								.registerUserFromMall(setMap);
					   if (returnDTO.getStatus().equals("000001")) {
						    dto =new ReturnDTO("000002",false,"邮箱已存在");
						} 
					} else {
						dto =new ReturnDTO("000001",false,"失败");
				}
			 } else {
				 dto =  new ReturnDTO("000001",false,"MD5验证不通过");
			 }
		} else {
			dto =new ReturnDTO("000003",false,"参数不能为空");
		}
		log.info(JSONObject.toJSONString(dto));
		return dto;
	}
	
	@RequestMapping("/checkUserExists")
	@ResponseBody
	public ReturnDTO checkUserExists(HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String mallUserName = request.getParameter("mallUserName");
		String date = request.getParameter("date");
		String md5 = request.getParameter("md5");// cee47d08b9002e6813395a7ca3074128
		ReturnDTO dto = new ReturnDTO();
		
		if (mallUserName != null &&date != null
				&& md5 != null) {
			
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("mallUserName", mallUserName);
			//map.put("email", email);
			map.put("date", date);

			list.add(map);

			// 判断服务端加密字串和请求的md5字串是否相同
			if (MD5.md5(mallUserName + date).equals(md5)) {
				// 调用服务
				if (Authory.token == null)
					RequestDataProxy.getAccessToken(authorService);
					// 签名验证并返回map
					Map<String, String> setMap = requestDataProxy(list);
					if (setMap != null) {
						dto = userAccountService
								.checkUserPayChannel(setMap);
					} else {
						dto =new ReturnDTO("000001",false,"解密错误");
				    }
			 } else {
				 dto =  new ReturnDTO("000001",false,"MD5验证不通过");
			 }
		} else {
			dto =new ReturnDTO("000003",false,"参数不能为空");
		}
		log.info(JSONObject.toJSONString(dto));
		return dto;
	}

	@RequestMapping("/chekUserPay")
	@ResponseBody
	public ReturnDTO checkUserPay(HttpServletRequest request,
			HttpServletResponse response) {
		ReturnDTO dto = new ReturnDTO();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String mallUserName = request.getParameter("mallUserName");
		String email = request.getParameter("email");
		String date = request.getParameter("date");
		String md5 = request.getParameter("md5");// cee47d08b9002e6813395a7ca3074128   dq19900926@163.com  duqiang a1ee90e312e0e51a9b48b51e14e1fe80

		if (mallUserName != null && email != null && date != null
				&& md5 != null) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("mallUserName", mallUserName);
				map.put("email", email);
				map.put("date", date);
	
				list.add(map);
	
				// 判断服务端加密字串和请求的md5字串是否相同
				if (MD5.md5(mallUserName + email + date).equals(md5)) {
					// 调用服务
					if (Authory.token == null)
						RequestDataProxy.getAccessToken(authorService);
					// 签名验证并返回map
					Map<String, String> setMap = requestDataProxy(list);
					if (setMap == null) dto = new ReturnDTO("000004",false,"解密错误");
					else{
						
					}
				}else {
					dto =  new ReturnDTO("000001",false,"MD5验证不通过");
				}
		}else {
			dto =  new ReturnDTO("000003",false,"参数不能为空");
		}
		log.info(JSONObject.toJSONString(dto));
		return dto;
	}

}
