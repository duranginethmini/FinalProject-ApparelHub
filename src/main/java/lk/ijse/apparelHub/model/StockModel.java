package lk.ijse.apparelHub.model;

import lk.ijse.apparelHub.db.DbConnection;
import lk.ijse.apparelHub.dto.CustomerDto;
import lk.ijse.apparelHub.dto.OutPutLoadDto;
import lk.ijse.apparelHub.dto.StockDto;
import lk.ijse.apparelHub.dto.SupplyLoadDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockModel {


    public static boolean updateSupplyQty(List<SupplyLoadDto> data) throws SQLException {
        for (SupplyLoadDto supplyLoadDto : data) {
            if (!updateSupplyQty(supplyLoadDto)) {
                return false;
            }
        }
        return true;

    }

    static boolean updateSupplyQty(SupplyLoadDto supplyLoadDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE Stock SET Amount=(Amount + ?) WHERE St_id=?";
       // System.out.println("akak");
        PreparedStatement pstm =connection.prepareStatement(sql);
        try {
            pstm.setInt(1,supplyLoadDto.getQty());
            pstm.setString(2,supplyLoadDto.getStockId());
            return pstm.executeUpdate()>0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }



    public  StockDto searchById(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Stock WHERE St_id=?";
        PreparedStatement pstm =connection.prepareStatement(sql);
        pstm.setString(1,id);
        ResultSet resultSet = pstm.executeQuery();

        try {

            if (resultSet.next()) {
                return new StockDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4)

                );
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public boolean saveStock(StockDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Stock VALUES(?, ?, ?, ?)";
        PreparedStatement pstm =connection.prepareStatement(sql);

        pstm.setString(1, dto.getStockId());
        pstm.setString(2,dto.getType());
        pstm.setString(3, String.valueOf(dto.getAmount()));
        pstm.setString(4, dto.getDescription());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean updateStock(StockDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Stock SET Type = ?, Description = ?,  WHERE St_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getStockId());
        pstm.setString(2, dto.getType());
        pstm.setString(3, String.valueOf(dto.getAmount()));
        pstm.setString(4, dto.getDescription());

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteStock(String stockId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM Stock WHERE St_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,stockId);

        return pstm.executeUpdate() > 0;
    }

    public List<StockDto> loadAllStocks() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Stock";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<StockDto> stockList = new ArrayList<>();

        while (resultSet.next()) {
            stockList.add(new StockDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4)
            ));
        }
        return stockList;
    }


    public static boolean saveOutput(List<OutPutLoadDto> data) throws SQLException {
        for (OutPutLoadDto outPutLoadDto : data) {
            if (!updateOutput(outPutLoadDto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateOutput(OutPutLoadDto outPutLoadDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE Stock SET Amount=(Amount + ?) WHERE St_id=?";
      //  System.out.println("akak");
        PreparedStatement pstm =connection.prepareStatement(sql);
        try {
            pstm.setInt(1,outPutLoadDto.getQty());
            pstm.setString(2,outPutLoadDto.getStockId());
            return pstm.executeUpdate()>0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public List<StockDto> getAllStockDetail() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Stock";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<StockDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String stockId = resultSet.getString(1);
            String  type = resultSet.getString(2);
            int amount = resultSet.getInt(3);
            String desc = resultSet.getString(4);

            var dto = new StockDto(stockId,type,amount,desc);
            dtoList.add(dto);
        }

        return dtoList;

    }
}
