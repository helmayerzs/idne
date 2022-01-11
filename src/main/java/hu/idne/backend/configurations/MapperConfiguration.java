package hu.idne.backend.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.idne.backend.services.system.impl.ModelMapperService;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ModelMapperService.class)
public class MapperConfiguration {

    @Autowired
    public MapperConfiguration(ObjectProvider<ObjectMapper> objectMapperProvider, ModelMapperService moduleFactory) {
        ObjectMapper objectMapper = objectMapperProvider.getIfAvailable();
        if (objectMapper == null) {
            throw new BeanCreationException("ObjectMapper bean is required!");
        }

        objectMapper.registerModule(moduleFactory.factoryToDtoSerializerModule());
        objectMapper.registerModule(moduleFactory.factoryToDtoDeserializerModule());
    }
}
