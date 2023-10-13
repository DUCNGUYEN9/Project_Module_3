package ra.business;

import ra.entity.Employee;
import ra.entity.Product;
import ra.notify.Notify;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductBusiness {
    public static List<Product> getAllProduct(int offSet, int pageNumber) {
        List<Product> productList = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_all_product(?,?)}");
            callSt.setInt(1,offSet);
            callSt.setInt(2,pageNumber);
            ResultSet rs = callSt.executeQuery();
            productList = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setManufacture(rs.getString("manufacturer"));
                java.sql.Date date = rs.getDate("created");
                product.setCreated(new Date(date.getTime()));
                product.setBatch(rs.getShort("batch"));
                product.setQuantity(rs.getInt("quantity"));
                product.setProductStatus(rs.getBoolean("product_status"));
                productList.add(product);
            }
        } catch (SQLException e) {
            Notify.notifyError(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return productList;
    }
    public static int getTotalRecord() {
        int total = 0 ;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_total_product(?)}");
            callSt.registerOutParameter(1, Types.INTEGER);
            callSt.execute();
            total = callSt.getInt(1);
        } catch (SQLException e) {
            Notify.notifyError(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return total;
    }
    public static boolean addNewProduct(Product product) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call insert_product(?,?,?,?,?)}");
            callSt.setString(1, product.getProductId());
            callSt.setString(2, product.getProductName());
            callSt.setString(3, product.getManufacture());
//            callSt.setDate(4, new java.sql.Date(product.getCreated().getTime()));
            callSt.setShort(4, product.getBatch());
            callSt.setBoolean(5, product.isProductStatus());
            callSt.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            Notify.notifyError(ex.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }
    public static boolean updateProduct(Product product) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call update_product(?,?,?,?,?)}");
            //set dữ liệu cho các tham số vào của procedure
            callSt.setString(1, product.getProductId());
            callSt.setString(2, product.getProductName());
            callSt.setString(3, product.getManufacture());
//            callSt.setDate(4, new java.sql.Date(product.getCreated().getTime()));
            callSt.setShort(4, product.getBatch());
            callSt.setBoolean(5, product.isProductStatus());
            //Thực hiện gọi procedure
            callSt.executeUpdate();
            result = true;
        } catch (Exception ex) {
            Notify.notifyError(ex.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }
    public static Product getProductById(String productId) {

        Product product = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call check_exists_id_product(?)}");
            //set giá trị tham số vào
            callSt.setString(1, productId);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            //Lấy dữ liệu rs đẩy vào đối tượng book trả về
            while (rs.next()) {
                product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setManufacture(rs.getString("manufacturer"));
                java.sql.Date date = rs.getDate("created");
                product.setCreated(new Date(date.getTime()));
                product.setBatch(rs.getShort("batch"));
                product.setQuantity(rs.getInt("quantity"));
                product.setProductStatus(rs.getBoolean("product_status"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return product;
    }
    public static List<Product> searchNameProduct(String name) {
        List<Product> productList = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call search_product_name(?)}");
            callSt.setString(1, name);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            productList = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setManufacture(rs.getString("manufacturer"));
                java.sql.Date date = rs.getDate("created");
                product.setCreated(new Date(date.getTime()));
                product.setBatch(rs.getShort("batch"));
                product.setQuantity(rs.getInt("quantity"));
                product.setProductStatus(rs.getBoolean("product_status"));
                productList.add(product);
            }
        } catch (Exception ex) {
            Notify.notifyError(ex.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return productList;
    }
    public static Product checkProductByName(String name) {
        Product product = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call search_product_name(?)}");
            //set giá trị tham số vào
            callSt.setString(1, name);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setManufacture(rs.getString("manufacturer"));
                java.sql.Date date = rs.getDate("created");
                product.setCreated(new Date(date.getTime()));
                product.setBatch(rs.getShort("batch"));
                product.setQuantity(rs.getInt("quantity"));
                product.setProductStatus(rs.getBoolean("product_status"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return product;
    }
    /*
    *
    * */
    public static String validateProductId(String text){
        return validate(text,"{call validate_product_id(?,?)}");
    }
    public static String validateProductName(String text){
        return validate(text,"{call validate_product_name(?,?)}");
    }
    public static String validate(String text,String sql) {
        String result = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setString(1, text);
            callSt.registerOutParameter(2, Types.VARCHAR);
            callSt.execute();
            result = callSt.getString(2);
        } catch (SQLException e) {
            Notify.notifyError("Lỗi chưa rõ vui lòng liên hệ hệ thống !");
            Notify.notifyError(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }
    public static String validateLong(long num,String sql) {
        String result = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setLong(1, num);
            callSt.registerOutParameter(2, Types.VARCHAR);
            callSt.execute();
            result = callSt.getString(2);
        } catch (SQLException e) {
            Notify.notifyError("Lỗi chưa rõ vui lòng liên hệ hệ thống !");
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }
    public static String validateInt(int num,String sql) {
        String result = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setInt(1, num);
            callSt.registerOutParameter(2, Types.VARCHAR);
            callSt.execute();
            result = callSt.getString(2);
        } catch (SQLException e) {
            Notify.notifyError("Lỗi chưa rõ vui lòng liên hệ hệ thống !");
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }
    public static String validateIntString(int num,String name,String sql) {
        String result = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setInt(1, num);
            callSt.setString(2, name);
            callSt.registerOutParameter(3, Types.VARCHAR);
            callSt.execute();
            result = callSt.getString(3);
        } catch (SQLException e) {
            Notify.notifyError("Lỗi chưa rõ vui lòng liên hệ hệ thống !");
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }
}
