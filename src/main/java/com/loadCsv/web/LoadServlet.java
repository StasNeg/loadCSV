package com.loadCsv.web;
import com.loadCsv.service.LoadCSV;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


public class LoadServlet extends HttpServlet {

    private LoadCSV service;

    @Override
    public void destroy() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        service = new LoadCSV(config.getInitParameter("folder"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> statistic = service.getStatistic();
        request.setAttribute("csvStatistics", statistic);
        request.getRequestDispatcher("/listFiles.jsp").forward(request, response);
    }
}