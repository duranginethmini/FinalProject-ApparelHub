package lk.ijse.apparelHub.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OutputTm {
    private String outputId;
    private String employeeId;
    private String productId;
    private LocalDate date;
    private LocalTime time;
    private int qty;

}

