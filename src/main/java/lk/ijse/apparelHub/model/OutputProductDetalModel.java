package lk.ijse.apparelHub.model;

import lk.ijse.apparelHub.db.DbConnection;
import lk.ijse.apparelHub.dto.OutPutLoadDto;
import lk.ijse.apparelHub.dto.SupplyLoadDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OutputProductDetalModel {

    public static boolean saveDetails(String outputId, List<OutPutLoadDto> data) throws SQLException {

        for (OutPutLoadDto outPutLoadDto : data) {
            if (!saveOutputLoad(outputId,outPutLoadDto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean saveOutputLoad(String outputId, OutPutLoadDto outPutLoadDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO OutputStockDetail VALUES(?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, outputId);
        pstm.setString(2, outPutLoadDto.getStockId());
        pstm.setInt(3, outPutLoadDto.getQty());


        boolean isSaved = pstm.executeUpdate() > 0;
        return isSaved;
    }
}
