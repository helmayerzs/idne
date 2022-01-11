package hu.idne.backend.services.business.impl;

import hu.idne.backend.services.business.InitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class InitServiceImpl implements InitService {

    @Override
    public void init() {
        log.info("RUN INITIALIZATION....");
    }
}
