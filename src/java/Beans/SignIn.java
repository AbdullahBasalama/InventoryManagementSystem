package Beans;

import BCrypt.*;
import DB.*;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class SignIn {

    private String name;
    private String password;
    private char [] passwordStringToChar;

    public SignIn() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password ;
    }

    private char[] toChar() {
        String theNewPass = getPassword();
        char a [] = new char[ theNewPass.length()] ;
        for(int i = 0; i< theNewPass.length();i++){
            a[i] = theNewPass.charAt(i);
        }
        return a ;
    }
    
    public void checkLogin() {
        FacesMessage facesMessage;
        DB db = new DB();
        ResultSet rs = null;
        passwordStringToChar = toChar();
        
        try {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hello from checkLogin", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            db.connect();
            String sql = "select * from users where username=? and password=?";
            rs = db.select(sql, getName(), EncryptedPassword(passwordStringToChar));
            
            if (rs.next()) {
                
//                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Encrypted pass: " + getPassword(), null);
//                FacesContext.getCurrentInstance().addMessage(null, facesMessage);

//                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Encrypted DB pass: " + rs.getString("password"), null);
//                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "you entered the thing!!!! ", null);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
//                if (matchPassword(password, rs.getString("password"))) {
//                    facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome to the application SIR...", null);
//                    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
//                }
            } else {

                facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "invalid username, password", null);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            }

        } catch (Exception ex) {
            String message = ex.getMessage();
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        } finally {
            try {
                db.releaseResources();
            } catch (Exception ex) {
                String message = ex.getMessage();
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            } 
        }  
    }
    
    private String EncryptedPassword(char [] password) {
        String originalPassword = new String (password);
        String hashedPassword = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
        return hashedPassword;
    }
    
    private boolean matchPassword(char [] formPass, String DBPassword) {
        String loginPassword = new String(formPass);
        boolean matched = BCrypt.checkpw(loginPassword, DBPassword);
        return matched;
    }
}
