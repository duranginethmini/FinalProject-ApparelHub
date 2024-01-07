package lk.ijse.apparelHub.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import lk.ijse.apparelHub.dto.*;
import lk.ijse.apparelHub.dto.tm.SupplyLoadTM;
import lk.ijse.apparelHub.dto.tm.UsedStockTm;
import lk.ijse.apparelHub.model.EmployeeModel;
import lk.ijse.apparelHub.model.OutputModel;
import lk.ijse.apparelHub.model.ProductModel;
import lk.ijse.apparelHub.model.StockModel;

public class OutputManageController {

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private Label dateLbl;

    @FXML
    private ComboBox<String> empIdCmb;

    @FXML
    private Label employeeNameLbl;

    @FXML
    private TextField outputIsTxt;

    @FXML
    private ComboBox<String> productIdcmb;

    @FXML
    private Label productNameLbl;

    @FXML
    private Label qtyOnHandLbl;

    @FXML
    private TextField qtyTxt;

    @FXML
    private AnchorPane root;

    @FXML
    private Label stcokNameLbl;

    @FXML
    private ComboBox<String > stockIdCmb;

    @FXML
    private TableView<UsedStockTm> tblOutput;

    @FXML
    private Label timeLbl;

    @FXML
    private TextField usedStockQty;

    @FXML
    private TableColumn<?, ?> colAction;

    ObservableList<UsedStockTm> obList = FXCollections.observableArrayList();
    Button remove;



    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Product_Form_Controller.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Product Manage");
        stage.centerOnScreen();

    }

    @FXML
    void empIdCmbOnAction(ActionEvent event) {
        var model = new EmployeeModel();
        String id = empIdCmb.getValue();
        try {
            EmployeeDto dto = model.searchById(id);
            employeeNameLbl.setText(dto.getName());

        } catch (Exception throwables) {
            System.out.println(throwables);
        }

    }

    @FXML
    void productIdcmbOnAction(ActionEvent event) {
        var model = new ProductModel();
        String id = productIdcmb.getValue();
        try {
            ProductDto dto = model.searchById(id);
            productNameLbl.setText(dto.getDesc());
            qtyOnHandLbl.setText(String.valueOf(dto.getAmount()));

        } catch (Exception throwables) {
            System.out.println(throwables);
        }

    }


    void loadEmployeeIds(){

        var model = new EmployeeModel();
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> employeeDtos = model.loadAllEmployeeIds();
            for (EmployeeDto dto : employeeDtos) {
                obList.add(dto.getEmp_id());
            }
            empIdCmb.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    void loadProductIds(){

        var model = new ProductModel();
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ProductDto> productDtos = model.loadAllProductIds();
            for (ProductDto dto : productDtos) {
                obList.add(dto.getId());
            }
            productIdcmb.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

   void loadStockIds(){


       var model = new StockModel();
       ObservableList<String> obList = FXCollections.observableArrayList();
       try {
           List<StockDto> stockDtos = model.loadAllStocks();
           for (StockDto dto : stockDtos) {
               obList.add(dto.getStockId());
           }
           stockIdCmb.setItems(obList);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }


    }

    public void stockIdCmbOnAction(ActionEvent actionEvent) {
        var model = new StockModel();
        String id = stockIdCmb.getValue();
        try {
            StockDto dto = model.searchById(id);
            stcokNameLbl.setText(dto.getType());
        } catch (Exception throwables) {
            System.out.println(throwables);
        }

    }

//   void loadAllOutputs(){
//       var model = new OutputModel();
//
//       ObservableList<OutputTm> obList = FXCollections.observableArrayList();
//
//       try {
//           List<OutputDto> dtoList = model.getAllOutPuts();
//
//           for(OutputDto dto : dtoList) {
//               obList.add(
//                       new OutputTm(
//                               dto.getOutputId(),
//                               dto.getEmployeeId(),
//                               dto.getProductId(),
//                               dto.getDate(),
//                               dto.getTime(),
//                               dto.getQty()
//                       )
//               );
//           }
//
//           tblOutput.setItems(obList);
//       } catch (SQLException e) {
//           throw new RuntimeException(e);
//       }
//
//    }

//    void setcellValueFactory(){
//        coloutPut.setCellValueFactory(new PropertyValueFactory<>("outputId"));
//        colEmployee.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
//        colProduct.setCellValueFactory(new PropertyValueFactory<>("productId"));
//        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
//        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
//        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
//    }

    @FXML
    void initialize() {
        try{
            TimeController.timeNow(timeLbl,dateLbl);
            loadEmployeeIds();
            loadProductIds();
            loadStockIds();
//            loadAllOutputs();
            setCellValueFactory();
        }catch(Exception e){

        }
        assert dateLbl != null : "fx:id=\"dateLbl\" was not injected: check your FXML file 'Output_Manage_Controller.fxml'.";
        assert empIdCmb != null : "fx:id=\"empIdCmb\" was not injected: check your FXML file 'Output_Manage_Controller.fxml'.";
        assert employeeNameLbl != null : "fx:id=\"employeeNameLbl\" was not injected: check your FXML file 'Output_Manage_Controller.fxml'.";
        assert productIdcmb != null : "fx:id=\"productIdcmb\" was not injected: check your FXML file 'Output_Manage_Controller.fxml'.";
        assert productNameLbl != null : "fx:id=\"productNameLbl\" was not injected: check your FXML file 'Output_Manage_Controller.fxml'.";
        assert qtyOnHandLbl != null : "fx:id=\"qtyOnHandLbl\" was not injected: check your FXML file 'Output_Manage_Controller.fxml'.";
        assert qtyTxt != null : "fx:id=\"qtyTxt\" was not injected: check your FXML file 'Output_Manage_Controller.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'Output_Manage_Controller.fxml'.";
        assert timeLbl != null : "fx:id=\"timeLbl\" was not injected: check your FXML file 'Output_Manage_Controller.fxml'.";

    }

    public void addOutputDetailBtnClick(ActionEvent actionEvent) {
        String outputId=outputIsTxt.getText();
        String employeeId=empIdCmb.getValue();
        String productId=productIdcmb.getValue();
        int qty= Integer.parseInt(qtyTxt.getText());


        List<OutPutLoadDto> data = new ArrayList<>();
        for (int i = 0; i < tblOutput.getItems().size(); i++) {
            UsedStockTm usedStockTm = obList.get(i);
            OutPutLoadDto outPutLoadDto = new OutPutLoadDto(
                    usedStockTm.getStockId(), usedStockTm.getQuantity()
            );
            data.add(outPutLoadDto);
        }

        OutputModel outputModel=new OutputModel();

//        OutputDto outputDto=new OutputDto(outputId,employeeId,productId,qty,data);

        try {
            boolean isPlaced = outputModel.addOutput(outputId,employeeId,productId,qty,data);
            if (isPlaced) {
                System.out.println("pk");

            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    void addUsedStocksBtnOnCLick(ActionEvent event) {
        String id = stockIdCmb.getValue();
        System.out.println(id);
        String name = stcokNameLbl.getText();
        int qty = Integer.parseInt(usedStockQty.getText());
        remove = new Button("Remove");
        setRemoveAction(remove);

        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOutput.getItems().size(); i++) {
                if (colId.getCellData(i).equals(id)) {
                    qty += (int) colId.getCellData(i);
                    obList.get(i).setQuantity((qty));
                    tblOutput.refresh();
                    return;
                }
            }
        }

        UsedStockTm usedStockTm = new UsedStockTm(id, name, qty, remove);
        obList.add(usedStockTm);
        tblOutput.setItems(obList);

    }

    private void setRemoveAction(Button remove) {
        remove.setOnAction((e) -> {
            Boolean result = NotificationController.confirmationMasseage("Are you sure want to remove ?");
            if (result) {
                int index = tblOutput.getSelectionModel().getSelectedIndex();
                obList.remove(index);
                tblOutput.refresh();
            }

        }
        );
    }

    void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("stockName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));

    }


}
