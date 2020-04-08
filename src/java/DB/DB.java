
package DB;

import java.sql.*;
public class DB {
    
    private final String DB_URL="jdbc:mysql://localhost/db3";
    private final String NAME="root";
    private final String PASS="123456";
    
    Connection conn = null ;
    PreparedStatement stmnt = null ;
    ResultSet rs = null ;
    Statement mnt = null ;
    
    
    
    public void connect () throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL, NAME, PASS);
    }
    
    public ResultSet select(String sql, String p1, String p2) throws Exception {
    
        stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, p1);
        stmnt.setString(2, p2);
        rs = stmnt.executeQuery();       
      
        return rs ;
    }
    //here I will use this method to get all info by id, so Should i change this type to ( ....., int p1) ??
    public ResultSet select(String sql, int p1) throws Exception {
    
        stmnt = conn.prepareStatement(sql);
        stmnt.setInt(1, p1);
        rs = stmnt.executeQuery();       
      
        return rs ;
    }
    
    public void insert(String sql, int id, String name, double price,int quantity) throws Exception {
    
        stmnt = conn.prepareStatement(sql);
        stmnt.setInt(1, id);
        stmnt.setString(2, name);
        stmnt.setDouble(3, price);
        stmnt.setInt(4, quantity);
        stmnt.executeUpdate(); 
     
     }
    
    public void releaseResourcesNo() throws Exception{
        if(conn != null)
            conn.close();
        if(stmnt != null)
            stmnt.close();
        
    }
    public ResultSet select(String sql) throws Exception {
    
        stmnt = conn.prepareStatement(sql);
        rs = stmnt.executeQuery();       
      
        return rs ;
    }
    
    public void releaseResources() throws Exception{
        if(conn != null)
            conn.close();
        if(stmnt != null)
            stmnt.close();
        if(rs != null)
            rs.close();
    }
     public void releaseResources2() throws Exception{
        if(conn != null)
            conn.close();
        if(mnt != null)
            mnt.close();
    }
    public void updateInsertDelete(String sql) throws Exception {
        connect();
        
        mnt = conn.createStatement();
        mnt.execute(sql);
        
        releaseResources2();
    }
    
}
