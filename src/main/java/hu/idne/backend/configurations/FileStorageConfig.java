package hu.idne.backend.configurations;


import hu.idne.backend.configurations.properties.FileStorageProperties;
import hu.idne.backend.services.system.FileStorageService;
import hu.idne.backend.services.system.impl.FileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfig {

    @Bean
    @ConfigurationProperties("hu.idne.backend.minio")
    public FileStorageProperties fileStorageProperties() {
        return new FileStorageProperties();
    }

    @Bean
    public FileStorageService getFileStorageService(@Autowired FileStorageProperties properties) {
        return new FileStorageServiceImpl(properties);
    }
}
