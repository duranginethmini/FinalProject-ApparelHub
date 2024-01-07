package lk.ijse.apparelHub.model;

import lk.ijse.apparelHub.db.DbConnection;
import lk.ijse.apparelHub.dto.ScheduleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScheduleModel {

    public boolean saveSchedule(ScheduleDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Schedule VALUES(?, ?, ?, ?)";
        PreparedStatement pstm =connection.prepareStatement(sql);

        pstm.setString(1, dto.getSch_id());
        pstm.setString(2,dto.getEmp_id());
        pstm.setString(3,dto.getName());
        pstm.setString(4, dto.getDesc());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean updateSchedule(ScheduleDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Schedule SET Emp_id = ?, Name = ?, desc = ? WHERE Sch_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmp_id());
        pstm.setString(2, dto.getName());
        pstm.setString(3,dto.getDesc());
        pstm.setString(4, dto.getSch_id());

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteSchedule(String schId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM Schedule WHERE Sch_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, schId);

        return pstm.executeUpdate() > 0;
    }
}
