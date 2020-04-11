
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
     private char [] passwordStringToChar;
    
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
    private char[] toChar() {
        String theNewPass = getPassword();
        char a [] = new char[ theNewPass.length()] ;
        for(int i = 0; i< theNewPass.length();i++){
            a[i] = theNewPass.charAt(i);
        }
        return a ;
    }
    public String signUp(){
        FacesMessage facesMessage;
        
        
        String pageTransactionMessage = "";
        try {
            if (getPassword().equals(getRePassword())) {
                
                pageTransactionMessage = signUpHelper();
                if(pageTransactionMessage.equals("success")){
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
        
        return pageTransactionMessage ;
    }
    
    private String signUpHelper() throws Exception {
        FacesMessage facesMessage;
        DB db = new DB();
        passwordStringToChar = toChar();
        try {
            
            String sql = "insert into users (fullname, address, phone, email, username, password, type) values (?, ?, ?, ?, ?, ?, ?)";
            db.update(sql, getName(), getAddress(), getPhone(), getEmail(), getUsername(), EncryptedPassword(passwordStringToChar), getType());
            return "success";
            
        } catch (Exception ex) {
            String message = ex.getMessage();
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return "fail";
        }
    }
    
   
    
    private String EncryptedPassword(char [] password) {
        String originalPassword = new String (password);
        String hashedPassword = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
        return hashedPassword;
    }
}
