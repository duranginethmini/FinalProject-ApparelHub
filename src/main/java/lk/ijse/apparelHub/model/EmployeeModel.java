package lk.ijse.apparelHub.model;

import lk.ijse.apparelHub.db.DbConnection;
import lk.ijse.apparelHub.dto.CustomerDto;
import lk.ijse.apparelHub.dto.EmployeeDto;
import lk.ijse.apparelHub.dto.SalaryDto;
import lk.ijse.apparelHub.dto.SupplierDto;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {


    public static List<EmployeeDto> loadAllEmpIds() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Employee";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<EmployeeDto> empList = new ArrayList<>();

        while (resultSet.next()) {
            empList.add(new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            ));
        }
        return empList;


    }

    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Employee VALUES(?, ?, ?, ?)";
        PreparedStatement pstm =connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmp_id());
        pstm.setString(2,dto.getName());
        pstm.setString(3,dto.getAddress());
        pstm.setString(4, String.valueOf(dto.getTele()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Employee SET name = ?, address = ?, tele = ? WHERE Emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, String.valueOf(dto.getTele()));
        pstm.setString(4, dto.getEmp_id());

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteEmployee(String Emp_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM Employee WHERE Emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, Emp_id);

        return pstm.executeUpdate() > 0;
    }

    public List<EmployeeDto> loadAllEmployeeIds() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Employee";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<EmployeeDto> employeeDtos = new ArrayList<>();

        while (resultSet.next()) {
            employeeDtos.add(new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            ));
        }
        return employeeDtos;
    }

    public EmployeeDto searchById(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Employee WHERE Emp_id=?";
        PreparedStatement pstm =connection.prepareStatement(sql);
        pstm.setString(1,id);
        ResultSet resultSet = pstm.executeQuery();

        try {
            if (resultSet.next()) {
                return new EmployeeDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4)

                );
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public EmployeeDto searchEmployee(String Emp_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Employee WHERE Emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, Emp_id);

        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto dto = null;

        if(resultSet.next()) {
            Emp_id = resultSet.getString(1);
            String Emp_name = resultSet.getString(2);
            String Emp_Address = resultSet.getString(3);
            int Emp_telNum = resultSet.getInt(4);

            dto = new EmployeeDto(Emp_id,Emp_name,Emp_Address,Emp_telNum);
        }


        return dto;
    }

    public static int  countEmployee() throws SQLException {
        int count = 0;
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select Emp_id,count(*) as count from Employee group by Emp_id";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {

            count++;

        }

        return count;
    }
}
