package com.gpch.login.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class AkurasiController {
	public String index() {
		return "akurasi/list";
	}
	@RequestMapping(value = "/merge")
	public String merge(int Id) {
		return "akurasi/form";
	}
}
