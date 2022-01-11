package hu.idne.backend.models.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchValue implements Serializable {
    private String value;
    private Boolean strict = false;
    private Boolean regex = false;
}
