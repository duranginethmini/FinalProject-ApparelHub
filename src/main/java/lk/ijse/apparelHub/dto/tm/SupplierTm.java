package lk.ijse.apparelHub.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierTm {
    private String id;
    private String name;
    private String address;
    private String telNum;
}
