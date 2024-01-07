package lk.ijse.apparelHub.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalaryTm {
    private String Emp_id;
    private String Name;
    private double Amount;
    private String date;
}
