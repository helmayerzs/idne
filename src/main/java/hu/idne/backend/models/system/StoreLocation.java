package hu.idne.backend.models.system;

import hu.idne.backend.models.system.enums.DocType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreLocation {
    private String folderName;
    private String objectName;

    public StoreLocation(DocType type, String objectName) {
        this.folderName = type.getValue();
        this.objectName = objectName;
    }
}

