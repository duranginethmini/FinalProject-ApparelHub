package lk.ijse.apparelHub.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.apparelHub.dto.CustomerDto;
import lk.ijse.apparelHub.dto.EmployeeDto;
import lk.ijse.apparelHub.model.CustomerModel;
import lk.ijse.apparelHub.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;

public class EmployeeDetail_Form_Controller {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtEmpAddress;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtEmpTelNum;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtEmpId.setText("");
        txtEmpName.setText("");
        txtEmpAddress.setText("");
        txtEmpTelNum.setText("");

    }

    @FXML
    void btnEmpDeleteOnAction(ActionEvent event) {
        String Emp_id = txtEmpId.getText();

        var EmployeeModel = new EmployeeModel();
        try {
            boolean isDeleted = EmployeeModel.deleteEmployee(Emp_id);

            if(isDeleted) {

                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnEmpSaveOnAction(ActionEvent event) {
        String Emp_id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String address = txtEmpAddress.getText();
        int tel = Integer.parseInt(txtEmpTelNum.getText());

        var dto = new EmployeeDto(Emp_id,name,address,tel);

        var model = new EmployeeModel();
        try {
            boolean isSaved = model.saveEmployee(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Saved").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnEmpUpdateOnAction(ActionEvent event) {
        String Emp_id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String address = txtEmpAddress.getText();
        int tel = Integer.parseInt(txtEmpTelNum.getText());

        var dto = new EmployeeDto(Emp_id, name, address, tel);

        var model = new EmployeeModel();
        try {
            boolean isUpdated = model.updateEmployee(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Employee_Form_Controller.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Stock Manage");
        stage.centerOnScreen();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String Emp_id = txtEmpId.getText();

        var model = new EmployeeModel();
        try {
            EmployeeDto dto = model.searchEmployee(Emp_id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(EmployeeDto dto) {
        txtEmpId.setText(dto.getEmp_id());
        txtEmpName.setText(dto.getName());
        txtEmpAddress.setText(dto.getAddress());
        txtEmpTelNum.setText(String.valueOf(dto.getTele()));
    }
}