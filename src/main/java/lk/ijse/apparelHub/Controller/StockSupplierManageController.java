package lk.ijse.apparelHub.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.apparelHub.dto.CustomerDto;
import lk.ijse.apparelHub.dto.StockDto;
import lk.ijse.apparelHub.dto.SupplierDto;
import lk.ijse.apparelHub.dto.SupplyLoadDto;
import lk.ijse.apparelHub.dto.tm.SupplyLoadTM;
import lk.ijse.apparelHub.model.CustomerModel;
import lk.ijse.apparelHub.model.StockModel;
import lk.ijse.apparelHub.model.SupplierModel;
import lk.ijse.apparelHub.model.SupplyLoadModel;

import static lk.ijse.apparelHub.Controller.TimeController.timeNow;
import static lk.ijse.apparelHub.model.SupplyLoadModel.getNextOrderId;

public class StockSupplierManageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label dateLbl;

    @FXML
    private TextField loadIdTxt;

    @FXML
    private Label qtyOnHandLbl;

    @FXML
    private TextField qtytxt;

    @FXML
    private AnchorPane root;

    @FXML
    private TableColumn<?, ?> stockIdCol;

    @FXML
    private TextField netTotalTxt;

    @FXML
    private ComboBox<String> supplierIdCmb;

    @FXML
    private TableColumn<?, ?> stockNameCol;

    @FXML
    private TableColumn<?, ?> actionCol;

    @FXML
    private Label stockNameLbl;

    @FXML
    private TableColumn<?, ?> stockQtyCol;

    @FXML
    private ComboBox<String> stcokIdCmb;

    @FXML
    private Label supplierNameLbl;

    @FXML
    private Label timeLbl;

    @FXML
    private TableView<SupplyLoadTM> tblSupplyLoad;

    ObservableList<SupplyLoadTM> obList = FXCollections.observableArrayList();
    Button remove;

    void loadSupllierId(){
        var model = new SupplierModel();

        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<SupplierDto> supList = model.loadAllSuppliers();

            for (SupplierDto dto : supList) {
                obList.add(dto.getId());
            }
            supplierIdCmb.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    void loadstockId() throws SQLException {
        var model = new StockModel();
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<StockDto> supList = model.loadAllStocks();

        for (StockDto dto : supList) {
            obList.add(dto.getStockId());
        }
        stcokIdCmb.setItems(obList);

    }

    @FXML
    void addtoCartOnAction(ActionEvent event) {
        String id = stcokIdCmb.getValue();
        System.out.println(id);
        String name = stockNameLbl.getText();
        int qty = Integer.parseInt(qtytxt.getText());
        remove = new Button("Remove");
        setRemoveAction(remove);

        if (!obList.isEmpty()) {
            for (int i = 0; i < tblSupplyLoad.getItems().size(); i++) {
                if (stockIdCol.getCellData(i).equals(id)) {
                    qty += (int) stockQtyCol.getCellData(i);
                    obList.get(i).setQuantity((qty));
                    tblSupplyLoad.refresh();
                    return;
                }
            }
        }

        SupplyLoadTM supplyLoadTM = new SupplyLoadTM(id, name, qty, remove);
        obList.add(supplyLoadTM);
        tblSupplyLoad.setItems(obList);
//        quantity.setText("");

    }

    void setCellValueFactory() {
        stockIdCol.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        stockNameCol.setCellValueFactory(new PropertyValueFactory<>("stockName"));
        stockQtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        actionCol.setCellValueFactory(new PropertyValueFactory<>("remove"));

    }

    private void setRemoveAction(Button remove) {
        remove.setOnAction((e) -> {
            Boolean result = NotificationController.confirmationMasseage("Are you sure want to remove ?");
            if (result) {
                int index = tblSupplyLoad.getSelectionModel().getSelectedIndex();
                obList.remove(index);
                tblSupplyLoad.refresh();
            }

        });
    }

    @FXML
    void placeOrdreBtnOnAction(ActionEvent event) {

        SupplyLoadModel supplyLoadModel=new SupplyLoadModel();

        String loadId = loadIdTxt.getText();
        String supId = supplierIdCmb.getValue();
        String payment = netTotalTxt.getText();


        List<SupplyLoadDto> data = new ArrayList<>();
        for (int i = 0; i < tblSupplyLoad.getItems().size(); i++) {
            SupplyLoadTM supplyLoadTM = obList.get(i);
            SupplyLoadDto supplyLoad = new SupplyLoadDto(
                    supplyLoadTM.getStockId(), supplyLoadTM.getQuantity()
            );
            data.add(supplyLoad);
        }

        try {
            boolean isPlaced = supplyLoadModel.PlaceSupplyLoad(supId, loadId, payment, data);
            if (isPlaced) {
                loadIdTxt.setText(getNextOrderId());
                netTotalTxt.setText("");
                stockNameLbl.setText("");
                supplierIdCmb.setValue(null);
                stcokIdCmb.setValue(null);
                qtytxt.setText("");
                stockNameLbl.setText("");
                qtyOnHandLbl.setText("");

            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Customer_form_Controller.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Customer Manage");
        stage.centerOnScreen();
    }

    @FXML
    void btnDashBoardOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/DashBoard_Form_Controller.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dash Board");
        stage.centerOnScreen();
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Employee_Form_Controller.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Employee Manage");
        stage.centerOnScreen();
    }

    @FXML
    void btnMachineOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Machine_Manage_Controller.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Machine Manage");
        stage.centerOnScreen();
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Order_Form_Controller.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Order Manage");
        stage.centerOnScreen();
    }

    @FXML
    void btnProductOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Product_Form_Controller.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Product Manage");
        stage.centerOnScreen();
    }

    @FXML
    void btnReportOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Report_Form_Controller.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Reports");
        stage.centerOnScreen();
    }

    @FXML
    void btnStockOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Stock_Manage_Controller.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Stock Manage");
        stage.centerOnScreen();
    }

    @FXML
    void stcokIdCmbOnAction(ActionEvent event) {
        var model = new StockModel();
        String id = stcokIdCmb.getValue();
        try {
            StockDto stock = model.searchById(id);
            stockNameLbl.setText(stock.getType());
            qtyOnHandLbl.setText(String.valueOf(stock.getAmount()));
        } catch (Exception throwables) {
            System.out.println(throwables);
        }
    }

    @FXML
    void supplierIdCmbOnAction(ActionEvent event) {
        var model = new SupplierModel();
        String id = supplierIdCmb.getValue();
        try {
            SupplierDto dto = model.searchById(id);
            supplierNameLbl.setText(dto.getName());

        } catch (Exception throwables) {
            System.out.println(throwables);
        }

    }

    @FXML
    void initialize() {

        try {
            loadSupllierId();
            loadstockId();
            TimeController.timeNow(timeLbl,dateLbl);
            setCellValueFactory();
            loadIdTxt.setText(getNextOrderId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assert dateLbl != null : "fx:id=\"dateLbl\" was not injected: check your FXML file 'StockSupplier_Manage_Controller.fxml'.";
        assert loadIdTxt != null : "fx:id=\"loadIdTxt\" was not injected: check your FXML file 'StockSupplier_Manage_Controller.fxml'.";
        assert qtyOnHandLbl != null : "fx:id=\"qtyOnHandLbl\" was not injected: check your FXML file 'StockSupplier_Manage_Controller.fxml'.";
        assert qtytxt != null : "fx:id=\"qtytxt\" was not injected: check your FXML file 'StockSupplier_Manage_Controller.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'StockSupplier_Manage_Controller.fxml'.";
        assert stockIdCol != null : "fx:id=\"stockIdCol\" was not injected: check your FXML file 'StockSupplier_Manage_Controller.fxml'.";
        assert stockNameCol != null : "fx:id=\"stockNameCol\" was not injected: check your FXML file 'StockSupplier_Manage_Controller.fxml'.";
        assert stockNameLbl != null : "fx:id=\"stockNameLbl\" was not injected: check your FXML file 'StockSupplier_Manage_Controller.fxml'.";
        assert stockQtyCol != null : "fx:id=\"stockQtyCol\" was not injected: check your FXML file 'StockSupplier_Manage_Controller.fxml'.";
        assert supplierNameLbl != null : "fx:id=\"supplierNameLbl\" was not injected: check your FXML file 'StockSupplier_Manage_Controller.fxml'.";
        assert timeLbl != null : "fx:id=\"timeLbl\" was not injected: check your FXML file 'StockSupplier_Manage_Controller.fxml'.";

    }


}