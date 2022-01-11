package hu.idne.backend.services.system;

public interface FileService {
    Boolean addFile(String idd, String folder, String fileName);
    void delFile(String id);
}

