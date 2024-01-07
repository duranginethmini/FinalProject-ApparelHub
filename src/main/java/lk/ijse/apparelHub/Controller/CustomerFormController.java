
package lk.ijse.apparelHub.Controller;

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
import lk.ijse.apparelHub.util.DataValidateController;
import lk.ijse.apparelHub.dto.CustomerDto;
import lk.ijse.apparelHub.dto.tm.CustomerTm;
import lk.ijse.apparelHub.model.CustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class CustomerFormController {
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<CustomerTm> tblCustomer;
    @FXML
    private TableColumn<?, ?> colCusName;

    @FXML
    private TableColumn<?, ?> colCusAddress;

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TableColumn<?, ?> colCusTel;

    @FXML
    private TextField txtCusAddress;

    @FXML
    private TextField txtCusId;

    @FXML
    private TextField txtCusName;

    @FXML
    private TextField txtCusTelNum;

    @FXML
    private Label custAddressValidateLbl;

    @FXML
    private Label custIdValidateLbl;

    @FXML
    private Label custNameValidateLbl;

    @FXML
    private Label custTelValidateLbl;


    public void initialize(){
        loadAllCustomers();
        setCellValueFactory();
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtCusId.setText("");
        txtCusName.setText("");
        txtCusAddress.setText("");
        txtCusTelNum.setText("");
    }

    private void setCellValueFactory() {
        colCusId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCusTel.setCellValueFactory(new PropertyValueFactory<>("telNum"));
    }
    private void loadAllCustomers() {
        var model = new CustomerModel();

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList = model.getAllCustomers();

            for(CustomerDto dto : dtoList) {
                obList.add(
                        new CustomerTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getTelNum()
                        )
                );
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnCustomerDeleteOnAction(ActionEvent event) {
        String id = txtCusId.getText();

        var customerModel = new CustomerModel();
        try {
            boolean isDeleted = customerModel.deleteCustomer(id);

            if(isDeleted) {
                tblCustomer.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                clearFields();
                loadAllCustomers();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnCustomerSaveOnAction(ActionEvent event) {
        if(txtCusId.getText().isEmpty() || txtCusAddress.getText().isEmpty()|| txtCusName.getText().isEmpty()||txtCusTelNum.getText().isEmpty()){
            NotificationController.ErrorMasseage("please fill all empty fields before add new customer !");
        }else{

            String id = txtCusId.getText();
            String name = txtCusName.getText();
            String address = txtCusAddress.getText();
            int tel = Integer.parseInt(txtCusTelNum.getText());

            var dto = new CustomerDto(id,name,address,tel);
            var model = new CustomerModel();

            if(DataValidateController.customerIdValidate(txtCusId.getText())){
                custIdValidateLbl.setText("");

                if (DataValidateController.customerNameValidate(txtCusName.getText())) {
                    custNameValidateLbl.setText("");

                    if (DataValidateController.addressValidate(txtCusAddress.getText())){
                        custAddressValidateLbl.setText("");

                        if(DataValidateController.contactCheck(txtCusTelNum.getText())){
                            custTelValidateLbl.setText("");

                            try {
                                boolean isSaved = model.saveCustomer(dto);
                                if (isSaved) {
                                    new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved").show();
                                    clearFields();
                                    loadAllCustomers();
                                    setCellValueFactory();
                                }
                            } catch (SQLIntegrityConstraintViolationException e){
                                NotificationController.ErrorMasseage("This Id is already exits !");

                            }
                            catch (SQLException e) {
                                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                            }

                        }else {
                            custTelValidateLbl.setText("Invalid tel.Include 10 characters !");
                        }

                    }else {
                        custAddressValidateLbl.setText("Include atleast 4 characters !");
                    }

                }else{
                    custNameValidateLbl.setText("Include atleast 4 characters !");
                }

            }else {
                custIdValidateLbl.setText("Invalid customer Id !");
            }
        }

    }

    @FXML
    void btnCustomerUpdateOnAction(ActionEvent event) {
        String id = txtCusId.getText();
        String name = txtCusName.getText();
        String address = txtCusAddress.getText();
        int tel = Integer.parseInt(txtCusTelNum.getText());

        var dto = new CustomerDto(id, name, address, tel);

        var model = new CustomerModel();
        try {
            boolean isUpdated = model.updateCustomer(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                clearFields();
                loadAllCustomers();
                setCellValueFactory();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

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

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtCusId.getText();

        var model = new CustomerModel();
        try {
            CustomerDto dto = model.searchCustomer(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void fillFields(CustomerDto dto) {
        txtCusId.setText(dto.getId());
        txtCusName.setText(dto.getName());
        txtCusAddress.setText(dto.getAddress());
        txtCusTelNum.setText(String.valueOf(dto.getTelNum()));
    }
}