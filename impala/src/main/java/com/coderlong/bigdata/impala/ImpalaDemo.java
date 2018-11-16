package com.coderlong.bigdata.impala;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author Long Qiong
 * @create 2018/10/26
 */
public class ImpalaDemo {
    static String JDBC_DRIVER = "com.cloudera.impala.jdbc41.Driver";
    static String CONNECTION_URL = "jdbc:impala://cdh02-03:21050/jmhx";

    public static void main(String[] args)
    {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try
        {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(CONNECTION_URL);
            ps = con.prepareStatement("select max(c_id),count(staffs_num) from hbase_company_label_flat");
            rs = ps.executeQuery();
            while (rs.next())
            {
                System.out.println(rs.getString(1) + '\t' + rs.getLong(2));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            //关闭rs、ps和con
        }
    }
}
