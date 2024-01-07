package lk.ijse.apparelHub.dto;

import javafx.scene.control.DatePicker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalaryDto {
    private String Emp_id;
    private String Name;
    private double Amount;
    private String date;
}
