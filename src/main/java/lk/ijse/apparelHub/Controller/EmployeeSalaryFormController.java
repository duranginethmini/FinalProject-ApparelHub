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
import lk.ijse.apparelHub.dto.EmployeeDto;
import lk.ijse.apparelHub.dto.SalaryDto;
import lk.ijse.apparelHub.dto.tm.SalaryTm;
import lk.ijse.apparelHub.model.EmployeeModel;
import lk.ijse.apparelHub.model.SalaryModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmployeeSalaryFormController  {
    @FXML
    private AnchorPane root;

    @FXML
    private TableColumn<?, ?> colEmpAmount;

    @FXML
    private TableColumn<?, ?> colEmpDate;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEmpName;

    @FXML
    private Label empDateLbl;

    @FXML
    private TextField txtEmpDate;

    @FXML
    private TextField txtSalaryAmount;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TableView<SalaryTm> tblEmpSalary;

    @FXML
    private ChoiceBox<Double> amountBox;

    private double[] Salary = {(25000.00),(35000.00)};
    @FXML
    private Label empNameLbl;
    @FXML
    private DatePicker txtDate;
    @FXML
    private ComboBox<String> cmbEmpSalary;

    public void initialize(){
        loadAllEmpSalaryDetail();
        setCellValueFactory();
        loadEmpId();
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("Emp_id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colEmpAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colEmpDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
    }

    private void loadAllEmpSalaryDetail() {
        var model = new SalaryModel();

        ObservableList<SalaryTm> obList = FXCollections.observableArrayList();

        try {
            List<SalaryDto> dtoList = model.getAllSalaryDetail();

            for(SalaryDto dto : dtoList) {
                obList.add(
                        new SalaryTm(
                                dto.getEmp_id(),
                                dto.getName(),
                                dto.getAmount(),
                                dto.getDate()
                        )
                );
            }

            tblEmpSalary.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadEmpId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> EmpList = EmployeeModel.loadAllEmpIds();

            for (EmployeeDto dto : EmpList) {
                obList.add(dto.getEmp_id());
            }
            cmbEmpSalary.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
     clearFields();
    }

    private void clearFields() {
           cmbEmpSalary.setValue("");
           txtEmpName.setText("");
           txtSalaryAmount.setText("");
           txtDate.setValue(LocalDate.parse(""));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Employee_Form_Controller.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Employee Manage");
        stage.centerOnScreen();
    }

    public void btnEmpSalarySaveOnAction(ActionEvent actionEvent) {

            String Emp_id = cmbEmpSalary.getValue();
            String name = empNameLbl.getText();
            double amount = Double.parseDouble(txtSalaryAmount.getText());
            String date = String.valueOf(txtDate.getValue());

            var dto = new SalaryDto(Emp_id,name,amount,date);

            var model = new SalaryModel();
            try {
                boolean isSaved = model.saveSalary(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Detail Saved").show();
                    loadAllEmpSalaryDetail();
                    setCellValueFactory();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }


    public void btnEmpSalaryUpdateOnAction(ActionEvent actionEvent) {
        String Emp_id = cmbEmpSalary.getValue();
        String name = empNameLbl.getText();
        double amount = Double.parseDouble(txtSalaryAmount.getText());
        String date = String.valueOf(txtDate.getValue());


        var dto = new SalaryDto(Emp_id,name,amount,date);

        var model = new SalaryModel();
        try {
            boolean isUpdated = model.updateSalary(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Detail updated!").show();
                clearFields();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    public void btnEmpSalaryDeleteOnAction(ActionEvent actionEvent) {
        String Emp_id = cmbEmpSalary.getValue();

        var SalaryModel = new SalaryModel();
        try {
            boolean isDeleted = SalaryModel.deleteDetail(Emp_id);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Detail deleted!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void empSalaryCmbOnAction(ActionEvent actionEvent) {
        var model = new EmployeeModel();
        String Emp_id = String.valueOf(cmbEmpSalary.getValue());
        try {
            EmployeeDto dto = model.searchById(Emp_id);
            empNameLbl.setText(dto.getName());

        } catch (Exception throwables) {
            System.out.println(throwables);
        }


    }
}
