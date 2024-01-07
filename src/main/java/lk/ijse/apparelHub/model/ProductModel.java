package lk.ijse.apparelHub.model;

import lk.ijse.apparelHub.db.DbConnection;
import lk.ijse.apparelHub.dto.EmployeeDto;
import lk.ijse.apparelHub.dto.ProductDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductModel {
    public static boolean updateQty(String productId ,int Qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE Product SET TotalAmount=(TotalAmount+?) WHERE Pr_id=?";
        System.out.println("akak");
        PreparedStatement pstm =connection.prepareStatement(sql);
        try {
            pstm.setInt(1,Qty);
            pstm.setString(2,productId);
            return pstm.executeUpdate()>0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean saveSupplier(ProductDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Product VALUES(?, ?, ?)";
        PreparedStatement pstm =connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, String.valueOf(dto.getAmount()));
        pstm.setString(3,dto.getDesc());


        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public List<ProductDto> loadAllProductIds() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Product";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<ProductDto> productDtos = new ArrayList<>();

        while (resultSet.next()) {
            productDtos.add(new ProductDto(
                    resultSet.getString(1),
                    resultSet.getInt(2),
                    resultSet.getString(3)

            ));
        }
        return productDtos;
    }

    public ProductDto searchById(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Product WHERE Pr_id=?";
        PreparedStatement pstm =connection.prepareStatement(sql);
        pstm.setString(1,id);
        ResultSet resultSet = pstm.executeQuery();

        try {
            if (resultSet.next()) {
                return new ProductDto(
                        resultSet.getString(1),
                        resultSet.getInt(2),
                        resultSet.getString(3)


                );
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
    public static int  countProduct() throws SQLException {
        int count = 0;
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select TotalAmount,count(*) as count from Product group by TotalAmount";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {

            count++;

        }

        return count;
    }
}

