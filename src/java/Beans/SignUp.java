
package Beans;
import DB.DB;
import BCrypt.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@RequestScoped

public class SignUp {
    
    private String name ;
    private String address ;
    private String phone ;
    private String email;
    private String username ;
    private String type ;
    private String password ;
    private String rePassword;
    
    public SignUp() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
    
    public String signUp(){
        FacesMessage facesMessage;
        String display = getPassword()+ "==" + getName() + "==" + getType();
        facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, display, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        String theReturn = "";
        try {
            if (getPassword().equals(getRePassword())) {
                
                theReturn = signUpHelper();
                if(theReturn.equals("success")){
                    facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Record been inserted", null);
                    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                }
                else{
                    facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nothing has been updated", null);
                    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                }
                
            } else {
                throw new Exception("Entered password do not match");
            }
            
            
        }
        catch(Exception ex){
            String  message = ex.getMessage(); 
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null,  facesMessage);
            
        }
        
        return theReturn ;
    }
    
    private String signUpHelper() throws Exception {
        FacesMessage facesMessage;
        DB db = new DB();
        try {
            
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entered the signUpHelper", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            
            String sql = "insert into users (fullname, address, phone, email, username, password, type) values (?, ?, ?, ?, ?, ?, ?)";
            db.update(sql, getName(), getAddress(), getPhone(), getEmail(), getUsername(), getPassword(), getType());
            return "success";
            
        } catch (Exception ex) {
            String message = ex.getMessage();
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return "fail";
        }
    }

}
