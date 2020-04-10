
package DB;

import java.sql.*;
public class DB {
    
    private final String DB_URL="jdbc:mysql://localhost/inventory";
    private final String NAME="root";
    private final String PASS="123456";
    
    Connection conn = null ;
    PreparedStatement stmnt = null ;
    ResultSet rs = null ;
    Statement mnt = null ;
    
    
    
    public void connect () throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection(DB_URL, NAME, PASS);
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
    
      
    public ResultSet select(String sql) throws Exception {
    
        stmnt = conn.prepareStatement(sql);
        rs = stmnt.executeQuery();       
      
        return rs ;
    }
    
    public void update(String sql, String name, String address, String phone, String email, String username, String password, String type)throws Exception{
        connect();
        stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, name);
        stmnt.setString(2, address);
        stmnt.setString(3, phone);
        stmnt.setString(4, email);
        stmnt.setString(5, username);
        stmnt.setString(6, password);
        stmnt.setString(7, type);
        stmnt.executeUpdate();
        releaseResourcesNo();
        
    }
    
    public void releaseResources() throws Exception{
        if(conn != null)
            conn.close();
        if(stmnt != null)
            stmnt.close();
        if(rs != null)
            rs.close();
    }
    
    public void releaseResourcesNo() throws Exception{
        if(conn != null)
            conn.close();
        if(stmnt != null)
            stmnt.close();
        
    }
         
   
    
    
}
