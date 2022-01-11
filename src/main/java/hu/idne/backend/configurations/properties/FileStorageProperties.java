package hu.idne.backend.configurations.properties;

import lombok.Data;

@Data
public class FileStorageProperties {
    private String protocol = "http://";
    private String host = "localhost";
    private String port = "9000";
    private String access = "minio";
    private String secret = "minio123";
}
