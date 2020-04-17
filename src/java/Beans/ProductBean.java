package Beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import DB.DB;
import Model.Category;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import Model.Product;

@ManagedBean
@RequestScoped
public class ProductBean {

    private List<Integer> options = new ArrayList<Integer>();
    private String selectedItem = "0";
    List<Product> productData;

    private int pid;
    private String name;
    private double price;
    private int quantity;
    private String description;
    private String date;
    private int sid;
    private int catid;
    private String nameSearch;
    
      private int catid2;
     private String catName;
     List<Category> catData;

    private List<Integer> SupplierID = new ArrayList<Integer>();
    private String selectedItemSupplier = "0";
    private List<Integer> CategoryID = new ArrayList<Integer>();
    private List<String> CategoryName = new ArrayList<String>();
    private String selectedItemcategory = "0";

    public ProductBean() {
        productData = new LinkedList<>();
        catData = new LinkedList<>();
        try {
            DB dbm = new DB();
            ResultSet rs = dbm.select1("SELECT * from products;");
            while (rs.next()) {

                Product c = new Product();
                c.setPid(rs.getInt("pid"));
                c.setName(rs.getString("name"));
                c.setDescription(rs.getString("description"));
                c.setPrice(rs.getDouble("price"));
                c.setQuantity(rs.getInt("quantity"));
                c.setCatid(rs.getInt("catid"));
                c.setSid(rs.getInt("sid"));
                c.setDate(rs.getString("date"));
                options.add(rs.getInt("pid"));
                productData.add(c);

            }
            dbm.releaseResources1();          
     
            
            DB dbm1 = new DB();
            ResultSet rs1 = dbm1.select1("SELECT sid from suppliers;");
            while (rs1.next()) {
                SupplierID.add(rs1.getInt("sid"));
            }

            dbm.releaseResources1();
            DB dbm2 = new DB();
            ResultSet rs2 = dbm2.select1("SELECT * from categories;");
            while (rs2.next()) {
                Category cat = new Category();
                cat.setCatid(rs2.getInt("catid"));
                 cat.setName(rs2.getString("name")); 
                 CategoryID.add(rs2.getInt("catid"));
                 catData.add(cat);
            }

            dbm.releaseResources1();
        } catch (Exception ex) {
            String message = ex.getMessage();
            FacesMessage facesMessage = new FacesMessage(message);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);

        }

    }

    public void productBeanSelectBtn() {
        try {
            DB dbm = new DB();
            ResultSet rs = dbm.select1("select * from products where pid=" + getSelectedItem());
            rs.next();
            name = rs.getString("name");
            description = rs.getString("description");
            price = rs.getDouble("price");
            quantity = rs.getInt("quantity");
            catid = rs.getInt("catid");
            sid = rs.getInt("sid");
            dbm.releaseResources1();
        } catch (Exception ex) {
            String message = ex.getMessage();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);

        }
    }

    public void productBeanSearchByName() {

        productData = new LinkedList<>();

        try {
            if (Found()) {
                DB dbm = new DB();
                ResultSet rs = dbm.select1("SELECT * from products where name like '%" + nameSearch + "%' ;");
                while (rs.next()) {
                    Product c = new Product();
                    c.setPid(rs.getInt("pid"));
                    c.setName(rs.getString("name"));
                    c.setDescription(rs.getString("description"));
                    c.setPrice(rs.getDouble("price"));
                    c.setQuantity(rs.getInt("quantity"));
                    c.setCatid(rs.getInt("catid"));
                    c.setSid(rs.getInt("sid"));
                    c.setDate(rs.getString("date"));
                    options.add(rs.getInt("pid"));
                    productData.add(c);
                }

                dbm.releaseResources1();
            } else {
                throw new Exception("product Name Not Found");
            }

        } catch (Exception ex) {
            String message = ex.getMessage();
            FacesMessage facesMessage = new FacesMessage(message);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);

        }
    }

    public void productBeanInsert() {
        try {
            if (check()) {
                DB dbm = new DB();

                String Q = "insert into products (name,price,quantity,description,sid,catid) values"
                        + "('" + name + "'," + price + ", " + quantity + ",'" + description + "'," + getSelectedItemSupplier() + " ," + getSelectedItemcategory() + ");";
                dbm.InsertUpdateDelete(Q);
                dbm.releaseResources1();
                FacesMessage facesMessage1 = new FacesMessage(FacesMessage.SEVERITY_INFO, "Your record has inserted", null);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage1);
                reload();
            }

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

    private boolean Found() throws Exception {
        String sql = "SELECT * from products where name like '%" + nameSearch + "%' ;";
        DB dbm = new DB();
        ResultSet rs = dbm.select1(sql);
        boolean found = rs.next(); //if there is a row will return true
        dbm.releaseResources1();

        if (found) {
            return true;
        }
        return false;

    }

    public void productBeanUpdate() {
        try {
            DB dbm = new DB();

            String Q = "update products set "
                    + " name = '" + name + "',"
                    + " price = " + price + ","
                    + " quantity=  " + quantity + ","
                    + " description = '" + description + "',"
                    + " sid = " + getSelectedItemSupplier() + " , "
                    + " catid = " + getSelectedItemcategory() + "  "
                    + " where pid =" + selectedItem + ";";
            dbm.InsertUpdateDelete(Q);
            dbm.releaseResources1();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Your record has been updated", null);
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
    public void productBeanDelete() {

        DB db = new DB();
        try {

            String sql = "delete from products where pid=" + selectedItem;
            db.InsertUpdateDelete(sql);
            db.releaseResources1();
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
        name = "";
        price = 0;
        description = "";
        quantity = 0;
        sid = 0;
        catid = 0;

    }

    public boolean check() throws Exception {
        if (name.equals("")) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fill The Name Field!", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return false;
        }

        if (price == 0) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "price cannot be zero", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return false;
        }
        if (quantity == 0) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "quantity cannot be zero", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return false;
        }

        return true;
    }

    public int getCatid2() {
        return catid2;
    }

    public void setCatid2(int catid2) {
        this.catid2 = catid2;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public List<Category> getCatData() {
        return catData;
    }

    public void setCatData(List<Category> catData) {
        this.catData = catData;
    }

    public List<String> getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(List<String> CategoryName) {
        this.CategoryName = CategoryName;
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public String getSelectedItemcategory() {
        return selectedItemcategory;
    }

    public void setSelectedItemcategory(String selectedItemcategory) {
        this.selectedItemcategory = selectedItemcategory;
    }

    public String getSelectedItemSupplier() {
        return selectedItemSupplier;
    }

    public void setSelectedItemSupplier(String selectedItemSupplier) {
        this.selectedItemSupplier = selectedItemSupplier;
    }

    public List<Integer> getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(List<Integer> SupplierID) {
        this.SupplierID = SupplierID;
    }

    public List<Integer> getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(List<Integer> CategoryID) {
        this.CategoryID = CategoryID;
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

    public List<Product> getProductData() {
        return productData;
    }

    public void setProductData(List<Product> productData) {
        this.productData = productData;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

}
