package lk.ijse.apparelHub.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsedStockTm {
    private String stockId;
    private String stockName;
    private int quantity;
    private Button remove;
}

