package hu.idne.backend.services.system;

import hu.idne.backend.models.system.StoreLocation;
import hu.idne.backend.models.system.enums.DocType;

import java.io.InputStream;

public interface FileStorageService {

    StoreLocation storeFile(DocType docType, String name, InputStream fileStream);

    byte[] getFile(String folder, String fileName);

    void removeFile(String folder, String fileName);
}

