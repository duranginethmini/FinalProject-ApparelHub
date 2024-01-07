package lk.ijse.apparelHub.model;

import lk.ijse.apparelHub.db.DbConnection;
import lk.ijse.apparelHub.dto.SupplyLoadDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SupplyLoadModel {
    public boolean PlaceSupplyLoad(String supId, String loadId, String payment, List<SupplyLoadDto> data) throws SQLException {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isSaved = SupplyLoadModel.saveSupplyLoad(loadId, supId, payment, LocalDate.now(), LocalTime.now(), data);

            if (isSaved) {
                boolean isUpdated = StockModel.updateSupplyQty(data);
                if (isUpdated) {
                    con.commit();
                    return true;
                }
            }
        } catch (Exception e) {
            con.rollback();
            System.out.println(e);

        }
        return false;
    }

    private static boolean saveSupplyLoad(String loadId, String supId, String payment, LocalDate now, LocalTime now1, List<SupplyLoadDto> data) throws SQLException {
        for (SupplyLoadDto supplyLoad : data) {
            if (!saveSupplyLoad(loadId, supId, payment, now, now1, supplyLoad)) {
                return false;
            }
        }
        return true;
    }

    private static boolean saveSupplyLoad(String supLoadId, String supId, String payment, LocalDate now, LocalTime now1, SupplyLoadDto supplyLoad) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO StockSupplierDetail(supplierLoadId,st_id,Sup_id," +
                "supplierQuantity,supplierLoadTime,supplierLoadDate,supplierLoadPrice)VALUES(" +
                "?,?,?,?,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, supLoadId);
        pstm.setString(2, supplyLoad.getStockId());
        pstm.setString(3, supId);
        pstm.setInt(4, supplyLoad.getQty());
        pstm.setString(5, String.valueOf(now1));
        pstm.setString(6, String.valueOf(now));
        pstm.setString(7, payment);


        boolean isSaved = pstm.executeUpdate() > 0;
        return isSaved;

    }


    public static String getNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT supplierLoadId FROM StockSupplierDetail ORDER BY supplierLoadId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery(sql);

        if (resultSet.next()) {
            return SplitSupplierLoadId(resultSet.getString(1));
        }
        return SplitSupplierLoadId(null);
    }

    private static String SplitSupplierLoadId(String string) {
        if (string != null) {
            String[] strings = string.split("load-");
            int id = Integer.parseInt(strings[1]);
            ++id;
            String digit = String.format("%03d", id);
            return "load-" + digit;

        }
        return "load-001";
    }
}
