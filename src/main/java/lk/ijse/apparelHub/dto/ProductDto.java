package lk.ijse.apparelHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Struct;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {

    private String id;
    private int amount;
    private String desc;
}
