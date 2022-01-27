/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.signalement.entities.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Mamitiana
 */
public class CustomURLFilter implements Filter {
      private static final Logger LOGGER = LoggerFactory.getLogger(CustomURLFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("########## Initiating CustomURLFilter filter ##########");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        LOGGER.info("This Filter is only called when request is mapped for /customer resource");
         String[] hors={"/admin/login","/admin/traitementlogin"};
        List<String> myList = new ArrayList<String>(Arrays.asList(hors));
        

         String path = request.getRequestURI();
        if (myList.contains(path)){
            filterChain.doFilter(request, response);
            return;
        }
        
        Integer admin = (Integer)session.getAttribute("admin");
        System.out.println("ID:"+admin);

//        //call next filter in the filter chain
//        filterChain.doFilter(request, response);
//        LOGGER.info("Logging Response :{}", response.getContentType());
//        return;
        

        
        if( admin!= null ){
           filterChain.doFilter(request, response);
           LOGGER.info("Logging Response :{}", response.getContentType());
           return;
           }
       else{
            RequestDispatcher rd =  request.getRequestDispatcher("/admin/login");
            rd.forward(request, response);
            return;
       }

    }

    @Override
    public void destroy() {

    }
}
