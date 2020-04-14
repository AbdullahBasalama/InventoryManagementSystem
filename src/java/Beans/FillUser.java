
package Beans;


import Model.User;
import DB.*;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class FillUser {

    List <User> userInfo ;
    
    List <Integer> userId;

    public List<Integer> getUserId() {
        return userId;
    }

    public void setUserId(List<Integer> userId) {
        this.userId = userId;
    }
    
    public List<User> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<User> userInfo) {
        this.userInfo = userInfo;
    }
    
    public FillUser() {
        // ------------ select user info -------------------//
        ResultSet rs = null;
        userInfo = new LinkedList<> ();
        DB db = new DB();
        String sql = "select * from users";
        
        // ------------ select user id -------------------//
        ResultSet rs2 = null;
        userId = new LinkedList<> ();
        DB db2 = new DB();
        String sql2 = "select id from users";
        
        try{
            
            rs = db.select(sql);            
            while(rs.next()){
                User us = new User();
                us.setId(rs.getInt("id"));
                us.setName(rs.getString("fullname"));
                us.setAddress(rs.getString("address"));
                us.setPhone(rs.getString("phone"));
                us.setEmail(rs.getString("email"));
                us.setUsername(rs.getString("username"));
                us.setType(rs.getString("type"));
                userInfo.add(us);
            }
            
            rs2 = db2.select(sql2);
            while(rs.next()){
                User us2 = new User();
                us2.setId(rs2.getInt("id"));
                userId.add(us2.getId());
            }
            
            
        }catch(Exception ex){
            String  message = ex.getMessage(); 
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null,  facesMessage);
        }
        
        finally {
            try{
                db.releaseResources();
                db2.releaseResources();
            }
            catch (Exception ex) {
                String message = ex.getMessage();
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            }
        }
        
    }
    
}
