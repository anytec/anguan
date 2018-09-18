package cn.anytec.anguan.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {
        assertApplicationContext();
        return applicationContext.getBean(clazz);
    }

    /**
     * 健壮性判断
     */
    private static void assertApplicationContext() {
        if (null == ApplicationContextHolder.applicationContext) {
            throw new RuntimeException("ApplicationContextHolder ERROR");
        }
    }
}
