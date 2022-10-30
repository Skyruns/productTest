package com.example.producttest.servlet;

import com.example.producttest.db.DBManager;
import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.ResultSet;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setHeader("Access-Control-Allow-Headers", "*");
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader(  "Access-Control-Allow-Origin","*");//允许所有来源访同
        response.addHeader(  "Access-Control-Allow-Method","POST,GET");//允许访问的方式
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        System.out.println(username);
        String password = request.getParameter("password");
        String dbusername = null;
        String dbpassword = null;
        if (username!=null)
        try {
            ResultSet rs =  DBManager.getInstance().getResultSet("select * from user where username='"+username+"'");
            if (rs.next()){
                dbusername = rs.getString("username");
                dbpassword = rs.getString("password");
                System.out.println(dbusername);
                System.out.println(dbpassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObject json = new JsonObject();
        JsonObject data = new JsonObject();
        if (username.equals(dbusername) && password.equals(dbpassword)){
            HttpSession session = request.getSession();
            session.setAttribute("logined","1");
            data.addProperty("status",200);
            data.addProperty("message","登录成功");
            json.add("data",data);

        }else{
            data.addProperty("status",400);
            data.addProperty("message","登录失败");
            json.add("data",data);

        }
        response.getWriter().append(json.toString());
    }
}
