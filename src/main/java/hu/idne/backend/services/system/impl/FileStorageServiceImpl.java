package hu.idne.backend.services.system.impl;

import hu.idne.backend.configurations.properties.FileStorageProperties;
import hu.idne.backend.exceptions.DocumentOperationException;
import hu.idne.backend.models.system.StoreLocation;
import hu.idne.backend.models.system.enums.DocType;
import hu.idne.backend.services.system.FileStorageService;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.extern.java.Log;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Log
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final MinioClient client;

    public FileStorageServiceImpl(FileStorageProperties props) {
        try {
            this.client = new MinioClient(props.getProtocol() + props.getHost() + ":" + props.getPort(), props.getAccess(), props.getSecret());
        } catch (InvalidEndpointException | InvalidPortException e) {
            throw new BeanCreationException("Unable to create FileStorageService bean, caused by: ", e);
        }
        this.client.setAppInfo("FCR", "0.1-dev");
    }

    /**
     * Put a file in the storage
     *
     * @param docType    DocType Document Type of given file, bucket name is calculated by this
     * @param fileStream InputStream to put in the storage
     * @return String file access information
     */
    @Override
    public StoreLocation storeFile(DocType docType, String name, InputStream fileStream) {
        switch (docType) {
            case POST_PHOTOS:
                return fileUpload(docType, getPictureName(name), fileStream);
            default:
                throw new DocumentOperationException("Invalid document type");
        }
    }

    @Override
    public void removeFile(String folder, String name) {
        try {
            client.removeObject(folder, name);
        } catch (Exception e) {
            throw new DocumentOperationException("unable to remove file caused by:", e);
        }
    }

    @Override
    public byte[] getFile(String folder, String fileName) {
        try {
            return IOUtils.toByteArray(client.getObject(folder, fileName));
        } catch (Exception e) {
            throw new DocumentOperationException("unable to read get the requested file caused by:", e);
        }
    }

    private String getDocumentName(String name) {
        return String.format("%s_%s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), name);
    }

    private String getPictureName(String name) {
        return UUID.randomUUID() + "." + FilenameUtils.getExtension(name);
    }

    private StoreLocation fileUpload(DocType type, String name, InputStream input) {
        createFolderIfNotExist(type);
        putObject(type.getValue(), name, input);
        return new StoreLocation(type, name);
    }

    private void createFolderIfNotExist(DocType type) {
        boolean exists;
        try {
            exists = client.bucketExists(type.getValue());
        } catch (Exception e) {
            throw new DocumentOperationException("unable to check if folder is exist caused by:", e);
        }

        if (!exists) {
            try {
                client.makeBucket(type.getValue());
            } catch (Exception e) {
                throw new DocumentOperationException("unable to create folder caused by:", e);
            }
        }
    }

    private void putObject(String folder, String name, InputStream input) {
        try {
            client.putObject(folder, name, input, null, null, null, "application/octet-stream");
        } catch (Exception e) {
            throw new DocumentOperationException("unable to save file caused by:", e);
        }
    }
}
