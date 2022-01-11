package hu.idne.backend.utils.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@SuppressWarnings("squid:S2696")
public class AutowireUtil implements ApplicationContextAware, Ordered {
    private static ApplicationContext context;

    public AutowireUtil() {
        log.debug("autowireUtil is created");
    }

    public static void staticAutowire(@NonNull Object objectToAutowire) {
        context.getAutowireCapableBeanFactory().autowireBean(objectToAutowire);
    }

    public static ApplicationContext getContext() {
        return context;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) {
        log.debug("ApplicationContext is set");
        AutowireUtil.context = context;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
