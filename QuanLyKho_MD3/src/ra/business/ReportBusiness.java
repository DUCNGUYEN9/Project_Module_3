package ra.business;

import jdk.internal.org.objectweb.asm.Type;
import ra.notify.Notify;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.Date;

import static ra.color.Color.*;
import static ra.color.Color.RESET;
import static ra.table.Table.borderTable;
import static ra.table.Table.starBorder;

public class ReportBusiness {
    public static float getStatisticsExpense(Date date) {
        return statistics(date, "{call statistics_expense(?,?)}");
    }

    public static float getStatisticsExpenseTime(Date date1, Date date2) {
        return statisticsTime(date1, date2, "{call statistics_expense_time(?,?,?)}");
    }

    public static float getStatisticsProfit(Date date) {
        return statistics(date, "{call statistics_profit(?,?)}");
    }

    public static float getStatisticsProfitTime(Date date1, Date date2) {
        return statisticsTime(date1, date2, "{call statistics_profit_time(?,?,?)}");
    }

    public static float statistics(Date date, String sql) {
        float total = 0;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setDate(1, new java.sql.Date(date.getTime()));
            callSt.registerOutParameter(2, Type.FLOAT);
            callSt.execute();
            total = callSt.getFloat(2);
        } catch (SQLException e) {
            Notify.notifyError(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return total;
    }

    public static float statisticsTime(Date dateStart, Date dateEnd, String sql) {
        float total = 0;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setDate(1, new java.sql.Date(dateStart.getTime()));
            callSt.setDate(2, new java.sql.Date(dateEnd.getTime()));
            callSt.registerOutParameter(3, Types.FLOAT);
            callSt.execute();
            total = callSt.getFloat(3);
        } catch (SQLException e) {
            Notify.notifyError(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return total;
    }

    public static void statisticsEmployee() {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            ResultSet rs = null;
            try {
                callSt = conn.prepareCall("{call statistics_employee()}");
                rs = callSt.executeQuery();
            } catch (SQLException e) {
                Notify.notifyError(e.getMessage());
            }
            System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG THỐNG KÊ NHÂN VIÊN  ◯◯◯" + RESET);
            borderTable(150);
            System.out.printf("%-50s %-50s %-53s %s\n", starBorder(), BLUE_BB + "Trạng thái", "Số nhân viên" + RESET, starBorder());
            while (rs.next()) {
                short status = rs.getShort("emp_status");
                String empStatus = null;
                if (status == 0) {
                    empStatus = "Hoạt động";
                } else if (status == 1) {
                    empStatus = "Nghỉ chế độ";
                } else {
                    empStatus = "Nghỉ việc";
                }
                int count = rs.getInt("emp_number");
                System.out.printf("%-50s %-45s %-47d %s\n", starBorder(), empStatus, count, starBorder());
            }
            borderTable(150);
        } catch (Exception ex) {
            Notify.notifyError(ex.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }

    public static String getStatisticsImportMany(Date date1, Date date2) {
        return statisticsProduct(date1, date2, "{call statistics_import_many(?,?,?)}");
    }

    public static String getStatisticsImportLeast(Date date1, Date date2) {
        return statisticsProduct(date1, date2, "{call statistics_import_least(?,?,?)}");
    }
    public static String getStatisticsExportMany(Date date1, Date date2) {
        return statisticsProduct(date1, date2, "{call statistics_export_many(?,?,?)}");
    }


    public static String getStatisticsExportLeast(Date date1, Date date2) {
        return statisticsProduct(date1, date2, "{call statistics_export_least(?,?,?)}");
    }

    public static String statisticsProduct(Date dateStart, Date dateEnd, String sql) {
        String name = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setDate(1, new java.sql.Date(dateStart.getTime()));
            callSt.setDate(2, new java.sql.Date(dateEnd.getTime()));
            callSt.registerOutParameter(3, Types.VARCHAR);
            callSt.execute();
            name = callSt.getString(3);
        } catch (SQLException e) {
            Notify.notifyError(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return name;
    }


}
