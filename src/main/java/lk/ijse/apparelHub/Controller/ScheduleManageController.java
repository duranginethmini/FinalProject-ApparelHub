package lk.ijse.apparelHub.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.apparelHub.dto.EmployeeDto;
import lk.ijse.apparelHub.dto.ScheduleDto;
import lk.ijse.apparelHub.model.EmployeeModel;
import lk.ijse.apparelHub.model.SalaryModel;
import lk.ijse.apparelHub.model.ScheduleModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ScheduleManageController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXComboBox<String> txtCmbEmpId;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtSchId;

    @FXML
    private Label empNameLbl;

   public void initialize(){
    loadEmpIds();
}

    private void loadEmpIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> EmpList = EmployeeModel.loadAllEmpIds();

            for (EmployeeDto dto : EmpList) {
                obList.add(dto.getEmp_id());
            }
            txtCmbEmpId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Employee_Form_Controller.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Employee Manage");
        stage.centerOnScreen();
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
             clearFields();
    }

    private void clearFields() {
       txtSchId.setText("");
       txtCmbEmpId.setValue("");
       empNameLbl.setText("");
       txtDesc.setText("");
    }

    @FXML
    void btnSchDeleteOnAction(ActionEvent event) {
        String Sch_id = txtSchId.getText();

        var ScheduleModel = new ScheduleModel();
        try {
            boolean isDeleted = ScheduleModel.deleteSchedule(Sch_id);

            if(isDeleted) {
                //tblCustomer.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, " Schedule detail deleted!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSchSaveOnAction(ActionEvent event) {
        String Sch_id = txtSchId.getText();
        String Emp_id = String.valueOf(txtCmbEmpId.getValue());
        String Name = empNameLbl.getText();
        String desc = txtDesc.getText();

        var dto = new ScheduleDto(Sch_id,Emp_id,Name,desc);

        var model = new ScheduleModel();
        try {
            boolean isSaved = model.saveSchedule(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION,"Schedule detail Saved").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSchUpdateOnAction(ActionEvent event) {
        String Sch_id =txtSchId.getText();
        String Emp_id = txtCmbEmpId.getValue();
        String Name = empNameLbl.getText();
        String desc = txtDesc.getText();

        var dto = new ScheduleDto(Sch_id,Emp_id,Name,desc);

        var model = new ScheduleModel();
        try {
            boolean isUpdated = model.updateSchedule(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Schedule detail updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void empIdCmbOnAction(ActionEvent actionEvent) {
        var model = new EmployeeModel();
        String Emp_id = String.valueOf(txtCmbEmpId.getValue());
        try {
            EmployeeDto dto = model.searchById(Emp_id);
            empNameLbl.setText(dto.getName());

        } catch (Exception throwables) {
            System.out.println(throwables);
        }

    }
}
