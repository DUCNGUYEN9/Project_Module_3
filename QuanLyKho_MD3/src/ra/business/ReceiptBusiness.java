package ra.business;

import ra.entity.Bill;
import ra.notify.Notify;
import ra.entity.BillDetail;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ReceiptBusiness {
    public static List<Bill> getAllReceipt(int offSet, int pageNumber) {
        List<Bill> billList = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_all_receipt(?,?)}");
            callSt.setInt(1, offSet);
            callSt.setInt(2, pageNumber);
            ResultSet rs = callSt.executeQuery();
            billList = new ArrayList<>();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getLong("bill_id"));
                bill.setBillCode(rs.getString("bill_code"));
                bill.setBillType(rs.getBoolean("bill_type"));
                bill.setEmployeeIdCreated(rs.getString("emp_id_created"));
                java.sql.Date dateCreated = rs.getDate("created");
                bill.setCreated(new Date(dateCreated.getTime()));
                bill.setEmployeeIdAuth(rs.getString("emp_id_auth"));
                java.sql.Date dateAuth = rs.getDate("auth_date");
                if (dateAuth == null) {
                    bill.setAuthDateNull("Chưa duyệt");
                } else {
                    bill.setAuthDate(new Date(dateAuth.getTime()));
                }
                bill.setBillStatus(rs.getShort("bill_status"));
                billList.add(bill);
            }
        } catch (SQLException e) {
            Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");

        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return billList;
    }

    public static int getTotalRecord() {
        return getTotalRecordAll("{call get_total_receipt(?)}");
    }

    public static boolean addNewReceipt(Bill bill) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call insert_receipt(?,?)}");
            callSt.setString(1, bill.getBillCode());
            callSt.setString(2, bill.getEmployeeIdCreated());
            callSt.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");

        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    public static List<BillDetail> getAllReceiptDetail(int offSet, int pageNumber) {
        List<BillDetail> billDetailList = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_all_receipt_detail(?,?)}");
            callSt.setInt(1, offSet);
            callSt.setInt(2, pageNumber);
            ResultSet rs = callSt.executeQuery();
            billDetailList = new ArrayList<>();
            while (rs.next()) {
                BillDetail billDetail = new BillDetail();
                billDetail.setBillDetailId(rs.getLong("bill_detail_id"));
                billDetail.setBillId(rs.getLong("bill_id"));
                billDetail.setProductId(rs.getString("product_id"));
                billDetail.setQuantity(rs.getInt("quantity"));
                billDetail.setPrice(rs.getFloat("price"));
                billDetailList.add(billDetail);
            }
        } catch (SQLException e) {
            Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");

        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return billDetailList;
    }

    public static int getTotalRecordDetail() {
        return getTotalRecordAll("{call get_total_receipt_detail(?)}");
    }

    public static int getTotalRecordAll(String sql) {
        int total = 0;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.registerOutParameter(1, Types.INTEGER);
            callSt.execute();
            total = callSt.getInt(1);
        } catch (SQLException e) {
            Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");

        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return total;
    }

    public static boolean updateReceipt(Bill bill) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call update_receipt(?,?,?)}");
            //set dữ liệu cho các tham số vào của procedure
            callSt.setLong(1, bill.getBillId());
            callSt.setString(2, bill.getBillCode());
            callSt.setString(3, bill.getEmployeeIdCreated());
            //Thực hiện gọi procedure
            callSt.executeUpdate();
            result = true;
        } catch (Exception ex) {
            Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");
            System.out.println(ex.getMessage());

        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }


    public static Bill getReceiptById(int id) {

        Bill bill = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call check_exists_id_bill(?)}");
            //set giá trị tham số vào
            callSt.setLong(1, id);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            //Lấy dữ liệu rs đẩy vào đối tượng book trả về
            while (rs.next()) {
                bill = new Bill();
                bill.setBillId(rs.getLong("bill_id"));
                bill.setBillCode(rs.getString("bill_code"));
                bill.setBillType(rs.getBoolean("bill_type"));
                bill.setEmployeeIdCreated(rs.getString("emp_id_created"));
                java.sql.Date dateCreated = rs.getDate("created");
                bill.setCreated(new Date(dateCreated.getTime()));
                bill.setEmployeeIdAuth(rs.getString("emp_id_auth"));
                java.sql.Date dateAuth = rs.getDate("auth_date");
                if (dateAuth == null) {
                    bill.setAuthDateNull("Chưa duyệt");
                } else {
                    bill.setAuthDate(new Date(dateAuth.getTime()));
                }
                bill.setBillStatus(rs.getShort("bill_status"));
            }
        } catch (Exception ex) {
            Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");

        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return bill;
    }

    public static Bill getReceiptByIdStatus(String id) {

        Bill bill = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call check_exists_id_status_bill(?)}");
            //set giá trị tham số vào
            callSt.setString(1, id);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            //Lấy dữ liệu rs đẩy vào đối tượng book trả về
            while (rs.next()) {
                bill = new Bill();
                bill.setBillId(rs.getLong("bill_id"));
                bill.setBillCode(rs.getString("bill_code"));
                bill.setBillType(rs.getBoolean("bill_type"));
                bill.setEmployeeIdCreated(rs.getString("emp_id_created"));
                java.sql.Date dateCreated = rs.getDate("created");
                bill.setCreated(new Date(dateCreated.getTime()));
                bill.setEmployeeIdAuth(rs.getString("emp_id_auth"));
                java.sql.Date dateAuth = rs.getDate("auth_date");
                if (dateAuth == null) {
                    bill.setAuthDateNull("Chưa duyệt");
                } else {
                    bill.setAuthDate(new Date(dateAuth.getTime()));
                }
                bill.setBillStatus(rs.getShort("bill_status"));
            }
        } catch (Exception ex) {
            Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");

        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return bill;
    }

    public static boolean approveReceipt(Bill bill) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call approve_receipt(?,?)}");
            //set dữ liệu cho các tham số vào của procedure
            callSt.setLong(1, bill.getBillId());
            callSt.setString(2, bill.getEmployeeIdAuth());
            //Thực hiện gọi procedure
            callSt.executeUpdate();
            result = true;
        } catch (Exception ex) {
            Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");

        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    public static List<Bill> searchIdReceipt(int id) {
        List<Bill> billList = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call search_receipt_id(?)}");
            callSt.setLong(1, id);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            billList = new ArrayList<>();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getLong("bill_id"));
                bill.setBillCode(rs.getString("bill_code"));
                bill.setBillType(rs.getBoolean("bill_type"));
                bill.setEmployeeIdCreated(rs.getString("emp_id_created"));
                java.sql.Date dateCreated = rs.getDate("created");
                bill.setCreated(new Date(dateCreated.getTime()));
                bill.setEmployeeIdAuth(rs.getString("emp_id_auth"));
                java.sql.Date dateAuth = rs.getDate("auth_date");
                if (dateAuth == null) {
                    bill.setAuthDateNull("Chưa duyệt");
                } else {
                    bill.setAuthDate(new Date(dateAuth.getTime()));
                }
                bill.setBillStatus(rs.getShort("bill_status"));
                billList.add(bill);
            }
        } catch (Exception ex) {
            Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");

        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return billList;
    }

    public static boolean addNewReceiptDetail(BillDetail billDetail) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call insert_receipt_detail(?,?,?,?)}");
            callSt.setLong(1, billDetail.getBillId());
            callSt.setString(2, billDetail.getProductId());
            callSt.setInt(3, billDetail.getQuantity());
            callSt.setFloat(4, billDetail.getPrice());
            callSt.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    /*
     *
     * */
    public static String validateReceiptId(String text) {
        return ProductBusiness.validate(text, "{call validate_receipt_id(?,?)}");
    }

    public static String validateProductStatus(String text) {
        return ProductBusiness.validate(text, "{call validate_product_status(?,?)}");
    }
    public static String validateProductIdExists(String text) {
        return ProductBusiness.validate(text, "{call validate_product_id_exists(?,?)}");
    }
    public static String validateReceiptStatus(long num) {
        return ProductBusiness.validateLong(num, "{call validate_receipt_status(?,?)}");
    }
    public static String validateReceiptIdExists(long num) {
        return ProductBusiness.validateLong(num, "{call validate_receipt_id_exists(?,?)}");
    }
    public static String validateBillStatus(long num) {
        return ProductBusiness.validateLong(num, "{call validate_bill_status(?,?)}");
    }
    public static String validateBillDetailApproved(long num) {
        return ProductBusiness.validateLong(num, "{call validate_bill_detail_approved(?,?)}");
    }
    public static String validateBillIdExists(long num) {
        return ProductBusiness.validateLong(num, "{call validate_Bill_id_exists(?,?)}");
    }
    public static String validateQuantity(int num,String name) {
        return ProductBusiness.validateIntString(num,name, "{call validate_quantity(?,?,?)}");
    }

    public static String validateAccId(String text) {
        return ProductBusiness.validate(text, "{call validate_acc_id(?,?)}");
    }
    public static String validateAccIdUser(String text) {
        return ProductBusiness.validate(text, "{call validate_acc_id_User(?,?)}");
    }
    public static String validateReceiptCode(String text) {
        return ProductBusiness.validate(text, "{call validate_receipt_code(?,?)}");
    }
    /*
    *
    * */
//    public static String updateReceiptDetail(BillDetail billDetail) {
//        String result = null;
//        Connection conn = null;
//        CallableStatement callSt = null;
//        try {
//            conn = ConnectionDB.openConnection();
//            callSt = conn.prepareCall("call update_receipt_detail(?,?,?,?,?)");
//            callSt.setLong(1, billDetail.getBillId());
//            callSt.setString(2, billDetail.getProductId());
//            callSt.setInt(3, billDetail.getQuantity());
//            callSt.setFloat(4, billDetail.getPrice());
//            callSt.registerOutParameter(5, Types.VARCHAR);
//            callSt.execute();
//            result = callSt.getString(5);
//        } catch (SQLException e) {
//            Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");
//
//        } finally {
//            ConnectionDB.closeConnection(conn, callSt);
//        }
//        return result;
//    }
}
