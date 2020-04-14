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

        this.password = password;
    }

    public String checkLogin() {
        FacesMessage facesMessage;
        DB db = new DB();
        ResultSet rs = null;
        String pageTransactionMessage = "";
        try {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hello from checkLogin", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            db.connect();
            String sql = "SELECT * FROM users WHERE username=? AND password = md5(?)";

            rs = db.select(sql, getName(), getPassword());

            if (rs.next()) {

                pageTransactionMessage = "success";
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "you entered the thing!!!! ", null);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                
//                    facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome to the application SIR...", null);
//                    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                
            } else {
                pageTransactionMessage = "fail";

                facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "invalid username, password", null);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            }

        } catch (Exception ex) {
            String message = ex.getMessage();
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            pageTransactionMessage = "fail";
        } 
        finally {
            try {
                db.releaseResources();
            } catch (Exception ex) {
                String message = ex.getMessage();
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            }
        }
        return pageTransactionMessage ;
    }
}
