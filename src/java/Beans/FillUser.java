
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

    List <User> ls ;

    public List<User> getLs() {
        return ls;
    }

    public void setLs(List<User> ls) {
        this.ls = ls;
    }
    
    public FillUser() {
        ResultSet rs = null;
        ls = new LinkedList<> ();
        DB db = new DB();
        String sql = "select * from users";
        try{
            
            rs = db.select(sql);
            
            while(rs.next()){
                User us = new User();
                us.setName(rs.getString("fullname"));
                us.setAddress(rs.getString("address"));
                us.setPhone(rs.getString("phone"));
                us.setEmail(rs.getString("email"));
                us.setUsername(rs.getString("username"));
                us.setType(rs.getString("type"));
                ls.add(us);
            }
            
            
        }catch(Exception ex){
            String  message = ex.getMessage(); 
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null,  facesMessage);
        }
        
        finally {
            try{
                db.releaseResources();
            }
                catch(Exception ex){
                
            }
        }
        
    }
    
}
