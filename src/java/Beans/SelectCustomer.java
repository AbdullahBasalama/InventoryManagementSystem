/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import DB.DB;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import Model.Customer;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ِِACER-PC
 */
@ManagedBean
@RequestScoped
public class SelectCustomer {

    private List<Integer> options = new ArrayList<Integer>();
    private String selectedItem = "0";

    List<Customer> cusData;
    private int cid;
    private String fullname;
    private String address;
    private String phone;
    private String email;
    private String company;
    private String date;
    private String phoneSearch;

    public String getPhoneSearch() {
        return phoneSearch;
    }

    public void setPhoneSearch(String phoneSearch) {
        this.phoneSearch = phoneSearch;
    }

    public SelectCustomer() {
        cusData = new LinkedList<>();

        try {
            DB dbm = new DB();
            ResultSet rs = dbm.select("SELECT * from customers;");
            while (rs.next()) {
                Customer c = new Customer();
                c.setCid(rs.getInt("cid"));
                c.setFullname(rs.getString("fullname"));
                c.setAddress(rs.getString("address"));
                c.setEmail(rs.getString("email"));
                c.setPhone(rs.getString("phone"));
                c.setCompany(rs.getString("company"));
                c.setDate(rs.getString("date"));
                options.add(rs.getInt("cid"));
                cusData.add(c);

            }

            dbm.releaseResources22();
        } catch (Exception ex) {
            String message = ex.getMessage();
            FacesMessage facesMessage = new FacesMessage(message);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);

        }

    }

    public void selectCustomerBtn() {
        try {
            DB dbm = new DB();
            ResultSet rs = dbm.select("select * from customers where cid=" + getSelectedItem());
            rs.next();
            fullname = rs.getString("fullname");
            address = rs.getString("address");
            email = rs.getString("email");
            phone = rs.getString("phone");
            company = rs.getString("company");
            dbm.releaseResources22(); //must close rs only after checking its number of rows.
        } catch (Exception ex) {
            String message = ex.getMessage();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);

        }
    }
    public void selectCustomerByPhone() {
        
          cusData = new LinkedList<>();

        try {
            if(Found()){
            DB dbm = new DB();
            ResultSet rs = dbm.select("SELECT * from customers where phone ='"+phoneSearch+"' ;");
            while (rs.next()) {
                Customer c = new Customer();
                c.setCid(rs.getInt("cid"));
                c.setFullname(rs.getString("fullname"));
                c.setAddress(rs.getString("address"));
                c.setEmail(rs.getString("email"));
                c.setPhone(rs.getString("phone"));
                c.setCompany(rs.getString("company"));
                c.setDate(rs.getString("date"));
                options.add(rs.getInt("cid"));
                cusData.add(c);
            }
            
            dbm.releaseResources22();
            }
            else
            throw new Exception("Customer phone Not Found");

            
        } catch (Exception ex) {
            String message = ex.getMessage();
            FacesMessage facesMessage = new FacesMessage(message);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);

        }
    }

    public void selectCustomerInsert() {
        try {
            DB dbm = new DB();

            String Q = "insert into customers (fullname,address,email,phone,company) values"
                    + "('" + fullname + "','" + address + "', '" + email + "','" + phone + "','" + company + "');";
            dbm.InsertUpdateDelete(Q);
            dbm.releaseResources22();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Your record has inserted", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            reload();
        } catch (Exception ex) {
            String message = ex.getMessage();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }

    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
      private boolean Found() throws Exception 
    {
        
        DB dbm = new DB();
        ResultSet rs = dbm.select("select * from customers where phone='"+phoneSearch+"' ;");
        boolean found = rs.next(); //if there is a row will return true
        dbm.releaseResources22();
        
        if(found)
            return true;
        return false;
        
    }

    public void selectCustomerUpdate() {
        try {
            DB dbm = new DB();

            String Q = "update customers set "
                    + " fullname = '" + fullname + "',"
                    + " address = '" + address + "',"
                    + " email=  '" + email + "',"
                    + " phone = '" + phone + "',"
                    + " company = '" + company + "' "
                    + " where cid =" + selectedItem + ";";
            dbm.InsertUpdateDelete(Q);
            dbm.releaseResources22();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Your record has updated", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            reload();
        } catch (Exception ex) {
            String message = ex.getMessage();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }
    }

    /**
     * delete function deletes the user based on his UserName
     */
    public void selectCustomerDelete() {

        DB db = new DB();
        try {

            String sql = "delete from customers where cid=" + selectedItem;
            db.InsertUpdateDelete(sql);
            db.releaseResources22();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Record is deleted", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
             reload();
        } catch (Exception ex) {
            String message = ex.getMessage();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }
    }

    public void reset() {
        fullname = "";
        address = "";
        phone = "";
        email = "";
        company = "";
        phoneSearch="";

    }

    public List<Customer> getCusData() {
        return cusData;
    }

    public void setCusData(List<Customer> cusData) {
        this.cusData = cusData;
    }

    public List<Integer> getOptions() {
        return options;
    }

    public void setOptions(List<Integer> options) {
        this.options = options;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
