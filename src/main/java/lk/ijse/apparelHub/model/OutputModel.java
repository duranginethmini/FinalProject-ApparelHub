package lk.ijse.apparelHub.model;

import lk.ijse.apparelHub.db.DbConnection;
import lk.ijse.apparelHub.dto.CustomerDto;
import lk.ijse.apparelHub.dto.OutPutLoadDto;
import lk.ijse.apparelHub.dto.OutputDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OutputModel {


    private static boolean saveOutput(String outputId, String employeeId, String productId, int qty, LocalDate now, LocalTime now1) throws SQLException {
        System.out.println(outputId);
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO Output VALUES(?,?,?,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, outputId);
        pstm.setString(2, employeeId);
        pstm.setString(3, productId);
        pstm.setString(4, String.valueOf(now));
        pstm.setString(5, String.valueOf(now1));
        pstm.setString(6, String.valueOf(qty));


        boolean isSaved = pstm.executeUpdate() > 0;
        return isSaved;
    }

    public List<OutputDto> getAllOutPuts() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Output";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<OutputDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String outputId = resultSet.getString(1);
            String empId = resultSet.getString(2);
            String prId = resultSet.getString(3);
            LocalDate date = LocalDate.parse(resultSet.getString(4));
            LocalTime time = LocalTime.parse(resultSet.getString(5));
            int qty = Integer.parseInt(resultSet.getString(6));

            var dto = new OutputDto(outputId, empId, prId,date,time,qty);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public boolean addOutput(String outputId, String employeeId, String productId, int qty, List<OutPutLoadDto> data) throws SQLException {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isSaved = OutputModel.saveOutput(outputId,employeeId,productId,qty, LocalDate.now(), LocalTime.now());
            if (isSaved) {
                boolean isUpdated = ProductModel.updateQty(productId,qty);

                if (isUpdated) {
                    boolean isSavedStock=StockModel.saveOutput(data);

                    if(isSavedStock){

                        boolean isSavedDetails=OutputProductDetalModel.saveDetails(outputId,data);

                        if(isSavedDetails){
                            con.commit();
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            con.rollback();
            System.out.println(e);

        }
        return false;
    }
}
