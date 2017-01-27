package com.ar.sgt.masterpromos.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ha")
public class WarmupController {
	
	@RequestMapping(value = "/keepalive", produces = "text/plain")
	@ResponseBody
	public String warmpup() throws Exception {
		return "OK";
	}	

}
