package com.pluralsight;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

public class NorthwindDataSource {
    public static DataSource getDataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://localhost:3306/northwind");
        ds.setUser("root");
        ds.setPassword("Yearup");  // Replace with your DB password
        return ds;
    }
}