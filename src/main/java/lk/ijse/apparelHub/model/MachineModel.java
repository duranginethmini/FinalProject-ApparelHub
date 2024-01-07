package lk.ijse.apparelHub.model;

import lk.ijse.apparelHub.db.DbConnection;
import lk.ijse.apparelHub.dto.CustomerDto;
import lk.ijse.apparelHub.dto.MachineDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineModel {

    public boolean saveMachine(MachineDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Machine VALUES(?, ?, ?)";
        PreparedStatement pstm =connection.prepareStatement(sql);

        pstm.setString(1, dto.getM_id());
        pstm.setString(2,dto.getType());
        pstm.setString(3, String.valueOf(dto.getCost()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean updateMachineDetail(MachineDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Machine SET type = ?, cost = ? WHERE M_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getM_id());
        pstm.setString(2, dto.getType());
        pstm.setString(3, String.valueOf(dto.getCost()));

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteMachine(String M_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM Machine WHERE M_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, M_id);

        return pstm.executeUpdate() > 0;

    }

    public List<MachineDto> getAllMachine() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Machine";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<MachineDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String M_id = resultSet.getString(1);
            String type = resultSet.getString(2);
            double cost = Double.parseDouble(resultSet.getString(3));


            var dto = new MachineDto(M_id,type,cost);
            dtoList.add(dto);
        }

        return dtoList;
    }
}
