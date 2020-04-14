
package Beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import DB.*;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class UserSelect {
    
    private String name;
    private String address ;
    private String phone ;
    private String email;
    private String username;
    private String type;   
   
    public UserSelect() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    // -------------------------------- functions ----------------------------------------//
    /**
     * select function selects all the fields of the employee based on the UserName.
     * and they should be displayed on the text fields.
     */
    public void select() {
        DB db = new DB();
        ResultSet rs = null ;
        try {
            
            String sql = "select * from users where username=?";
            /**
             * this function establishes connection 
             * and do the select based on the sql statement operation
             * and return a result set;
             *          |
             *          |
             *         \ /
             *          +
             */
            
            rs = db.select(sql, getUsername()); 
            
            if (rs.next()) {
                name = rs.getString("fullname");
                address = rs.getString("address");
                phone = rs.getString("phone");
                email = rs.getString("email");
                username = rs.getString("username");
                type = rs.getString("type");
            }
            else {
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,"There is no sush employee", null);
                FacesContext.getCurrentInstance().addMessage(null,  facesMessage);
            }
    
        }
        catch (Exception ex){
            String message = ex.getMessage(); 
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null,  facesMessage);
        }
        finally{
            try{
                
            }catch(Exception ex){
                
            }
        }
    }
    /**
     * update function takes all the info of the user and inserts it in the DB
     */
    public void update() {
        DB db = new DB();
        try {
            
            String sql = "update users set fullname=?,address=?, email=?, phone=? where username=? ";
            db.update(sql, name, address, email, phone, username);
            
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Record is Updated", null);
            FacesContext.getCurrentInstance().addMessage(null,  facesMessage);
        }
        catch (Exception ex){
            String message = ex.getMessage(); 
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null,  facesMessage);
        }
    }
    
    /**
     * delete function deletes the user based on his UserName
     */
    public void delete(){
        
        DB db = new DB();
        try {
            
            String sql = "delete from users where username=? ";
            db.update(sql,username);
            
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Record is deleted", null);
            FacesContext.getCurrentInstance().addMessage(null,  facesMessage);
        }
        catch (Exception ex){
            String message = ex.getMessage(); 
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null,  facesMessage);
        }
    }
    
    
    public void reset() {
        name = "";
        address = "";
        phone = "";
        email = "";
        username = "";
        type = "";
    }
}
