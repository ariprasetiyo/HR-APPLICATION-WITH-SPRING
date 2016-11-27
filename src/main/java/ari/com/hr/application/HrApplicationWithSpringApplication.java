package ari.com.hr.application;

import java.util.Collection;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class HrApplicationWithSpringApplication {

    static Logger log = Logger.getLogger(HrApplicationWithSpringApplication.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(HrApplicationWithSpringApplication.class, args);
        Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
        if (auth2 != null) {

            Collection<?> auths = auth2.getAuthorities();

            for (Object da : auths) {
                log.debug(da);
            }

            log.debug("name : " + auth2.getName());
            log.debug("principle : " + auth2.getPrincipal());
            log.debug("principle : " + auth2.getDetails());
            log.debug("principle : " + auth2.getCredentials());
        }
    }
}
