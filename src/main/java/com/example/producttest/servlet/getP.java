package com.example.producttest.servlet;

import com.example.producttest.bean.Product;
import com.example.producttest.db.DBManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Locale;

@WebServlet(name = "getP", value = "/getP")
public class getP extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader(  "Access-Control-Allow-Origin","*");//允许所有来源访同
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String logined = (String)session.getAttribute("logined");
        JsonObject json = new JsonObject();
        JsonObject data = new JsonObject();
//        if (logined!=null && logined.equals("1")){
//            System.out.println("已登录，可以操作");
//        }else{
//            System.out.println("请先登录");
//
//            data.addProperty("status",401);
//            data.addProperty("message","请先登录");
//            json.add("data",data);
//            response.getWriter().append(json.toString());
//
////            response.sendRedirect(request.getContextPath()+"/index.jsp");
//            return;
//        }
        Product product;    //存储产品信息
        Gson gson = new Gson();
        JsonArray arr = new JsonArray();
        try {
            ResultSet rs =  DBManager.getInstance().getResultSet("select * from p");
            if (rs.next()){
                product = new Product();
                product.setId(rs.getLong("id"));
                product.setProductName(rs.getString("productName"));
                product.setDirid(rs.getString("dirid"));
                product.setSalePrice(rs.getDouble("salePrice"));
                product.setSupplier(rs.getString("supplier"));
                product.setBrand(rs.getString("brand"));
                product.setCutoff(rs.getDouble("cutoff"));
                product.setCostPrice(rs.getDouble("costPrice"));
                arr.add(gson.toJson(product));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.add("data",arr);
        json.add("data",data);
        response.getWriter().append(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
