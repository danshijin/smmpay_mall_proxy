package com.smmpay.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smmpay.common.author.Authory;
import com.smmpay.common.request.RequestDataProxy;
import com.smmpay.inter.AuthorService;
import com.smmpay.inter.dto.res.ReturnDTO;
import com.smmpay.inter.smmpay.OrderService;
import com.smmpay.util.ResponseMessage;

@Controller
@RequestMapping("/order/")
public class PayController extends BaseController {
	@Autowired
	private OrderService orderService;
	@Autowired
	AuthorService authorService;

	
}
