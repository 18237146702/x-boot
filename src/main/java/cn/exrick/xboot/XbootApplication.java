package cn.exrick.xboot;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Exrickx
 */
// Activiti5.22需要排除Security
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
//启用JPA审计
@EnableJpaAuditing
//启用缓存
@EnableCaching
//启用异步
@EnableAsync
//启用自带定时任务
@EnableScheduling
public class XbootApplication {

    public static void main(String[] args) {
    	System.out.println("git 与家里测试-----");
        SpringApplication.run(XbootApplication.class, args);
    }
}
