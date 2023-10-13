package ra.business;
import ra.entity.Bill;
import ra.notify.Notify;
import ra.util.ConnectionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserBusiness {
    public static List<Bill> getUserReceiptStatus(int offSet, int pageNumber,short number){
        return getUserStatus(offSet, pageNumber,number,"{call get_user_receipt_status(?,?,?)}");
    }
    public static List<Bill> getUserBillStatus(int offSet, int pageNumber,short number){
        return getUserStatus(offSet, pageNumber,number,"{call get_user_bill_status(?,?,?)}");
    }
    public static List<Bill> getUserStatus(int offSet, int pageNumber,short number,String sql) {
        List<Bill> billList = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setInt(1, offSet);
            callSt.setInt(2, pageNumber);
            callSt.setShort(3, number);
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
            Notify.notifyError(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return billList;
    }
    public static int getTotalRecordReceipt(short number){
        return getTotalRecords(number,"{call get_total_user_receipt_status(?,?)}");
    }
    public static int getTotalRecordBill(short number){
        return getTotalRecords(number,"{call get_total_user_bill_status(?,?)}");
    }
    public static int getTotalRecords(short number,String sql) {
        int total = 0;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.registerOutParameter(1, Types.INTEGER);
            callSt.setShort(2,number);
            callSt.execute();
            total = callSt.getInt(1);
        } catch (SQLException e) {
            Notify.notifyError(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return total;
    }
    /*
    *
    * */
}
