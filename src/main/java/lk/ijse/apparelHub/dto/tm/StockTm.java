package lk.ijse.apparelHub.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StockTm {
    private String stockId;
    private String type;
    private  int amount;
    private String description;

}
