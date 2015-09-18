package com.hictech.test.wildfly10;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test")
@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {

    @Resource(lookup="java:global/hcloud/TestService")
    private TestService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        
        try( PrintWriter writer = resp.getWriter() ) {
        	writer.println(service.test());
        	writer.close();
        }
    }
    
}
