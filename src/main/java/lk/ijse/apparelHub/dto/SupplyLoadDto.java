package lk.ijse.apparelHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplyLoadDto {
    private String stockId;
    private int qty;
}
