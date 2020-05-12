package com.fengjunlin.accident.prediction.model.web.config;
import com.fengjunlin.accident.prediction.model.web.tools.ServerAck;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Description 返回数据的bean
 * @Author fengjl
 * @Date 2019/6/15 18:21
 * @Version 1.0
 **/
@Configuration
@Component("com.fengjunlin")
public class ApplicationConfig {
    @Bean(name="serverAck")
    @Scope("prototype")
    public ServerAck getServerAck() {
        return new ServerAck();
    }
}
