package lk.ijse.apparelHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OutputDto {
    private String outputId;
    private String employeeId;
    private String productId;
    private LocalDate date;
    private LocalTime time;
    private int qty;

    public OutputDto(String outputId, String employeeId, String productId, int qty) {
        this.outputId = outputId;
        this.employeeId = employeeId;
        this.productId = productId;
        this.qty = qty;
    }
}
