package com.example.producttest.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager {
    public static DBManager dbManager;

    private static String url = "jdbc:mysql://127.0.0.1:3306/productdb?useUnicode=true&characterEncoding=utf8";
    private static String userName = "root";
    private static String password = "";
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection con = null;
        Class.forName("com.mysql.jdbc.Driver");
//		Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, userName, password);
        System.out.println("连接数据库成功");
        return con;
    }
    //构造方法私有化
    private DBManager() throws ClassNotFoundException, SQLException {}

    //获取ChatDB实例
    public synchronized static DBManager getInstance() throws ClassNotFoundException, SQLException {
        if (dbManager ==null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    //查询返回方法
    public ResultSet getResultSet(String sql) throws Exception{
        ResultSet rs = null;
        Statement stmt = null;
        Connection con = null;
        System.out.println(sql);
        try {
            con = getConnection();
            stmt = (Statement) con.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            throw e;
        }

        return rs;
    }

    public int insertDB(String sql) throws ClassNotFoundException, SQLException{
        Statement stmt = null;
        Connection con;
        try {
            con = getConnection();
            stmt = (Statement)con.createStatement();
        }catch(Exception e) {
            System.out.println("连接数据库失败");

            return 0;
//			throw e;
        }
        int flag = 0;
        try {
            flag = stmt.executeUpdate(sql);
            //System.out.println(sql+"语句执行成功");
        }catch(SQLException e) {
            System.out.println("语句执行失败");
            e.printStackTrace();
            return 0;
//        	throw e;
        }finally{
            con.close();
        }
        return flag;

    }
}
