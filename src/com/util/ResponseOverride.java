package com.util;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ResponseOverride extends HttpServletResponseWrapper{
	public ResponseOverride(HttpServletResponse response) {
		super(response);
		// TODO Auto-generated constructor stub
	}

	private CharArrayWriter charArrayWriter = new CharArrayWriter();
	
	@Override
	public PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
		return new PrintWriter(charArrayWriter);
	}

	public CharArrayWriter getCharArrayWriter(){
		return charArrayWriter;
	}
}
