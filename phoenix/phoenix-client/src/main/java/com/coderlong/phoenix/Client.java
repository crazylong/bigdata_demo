package com.coderlong.phoenix;

import org.junit.Test;

import java.sql.*;

public class Client {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        ResultSet rset = null;

        //String driver="org.apache.phoenix.jdbc.PhoenixDriver";
        //Class.forName(driver);
        Connection con = DriverManager.getConnection("jdbc:phoenix:hadoop01,hadoop02,hadoop03");
        stmt = con.createStatement();

        //stmt.executeUpdate("create table test (mykey integer not null primary key, mycolumn varchar)");
        stmt.executeUpdate("upsert into test values (1,'Hello')");
        stmt.executeUpdate("upsert into test values (2,'World!')");
        con.commit();

        PreparedStatement statement = con.prepareStatement("select * from test");
        rset = statement.executeQuery();
        while (rset.next()) {
            System.out.println(rset.getString("mycolumn"));
        }
        statement.close();
        con.close();
    }

    @Test
    public void testSelect() throws SQLException {
        Statement stmt = null;
        ResultSet rset = null;


        Connection con = DriverManager.getConnection("jdbc:phoenix:hadoop01,hadoop02,hadoop03");


        PreparedStatement statement = con.prepareStatement("select * from test");
        rset = statement.executeQuery();
        while (rset.next()) {
            System.out.println(rset.getString("mycolumn"));
        }
        statement.close();
        con.close();
    }
}
