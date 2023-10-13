package ra.business;

import ra.entity.Bill;
import ra.notify.Notify;
import ra.entity.BillDetail;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillBusiness {
    public static List<Bill> getAllDetail(int offSet, int pageNumber) {
        List<Bill> billList = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_all_bill(?,?)}");
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
        return getTotalRecordAll("{call get_total_bill(?)}");
    }
    public static int getTotalRecordDetail(){
        return getTotalRecordAll("{call get_total_bill_detail(?)}");
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
    public static boolean addNewBillDetail(Bill bill) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call insert_bill_detail(?,?)}");
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
    public static List<BillDetail> getAllBillDetail(int offSet, int pageNumber) {
        List<BillDetail> billDetailList = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_all_bill_detail(?,?)}");
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
    public static boolean updateBill(Bill bill) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call update_bill_detail(?,?,?)}");
            //set dữ liệu cho các tham số vào của procedure
            callSt.setLong(1, bill.getBillId());
            callSt.setString(2, bill.getBillCode());
            callSt.setString(3,bill.getEmployeeIdCreated());
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
    public static Bill getBillById(int id) {

        Bill bill = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call check_exists_id_detail(?)}");
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
    public static Bill getBillByIdStatus(String id) {

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
    public static boolean approveBill(Bill bill) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call approve_bill(?,?)}");
            //set dữ liệu cho các tham số vào của procedure
            callSt.setLong(1, bill.getBillId());
            callSt.setString(2,bill.getEmployeeIdAuth());
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
    public static List<Bill> searchIdBill(int id) {
        List<Bill> billList = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call search_bill_id(?)}");
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
    public static boolean addNewDetail(BillDetail billDetail) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call insert_detail(?,?,?,?)}");
            callSt.setLong(1, billDetail.getBillDetailId());
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
}
