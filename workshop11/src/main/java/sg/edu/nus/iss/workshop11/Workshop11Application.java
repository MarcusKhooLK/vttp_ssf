package sg.edu.nus.iss.workshop11;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This is to get opts as list
import java.util.Collections;
import java.util.List;

// import third party library for logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class Workshop11Application {

    private static final Logger logger = LoggerFactory.getLogger(Workshop11Application.class);
    private static final String DEFAULT_PORT = "3000";

    public static void main(String[] args) {
        logger.info("Workshop 11");
        SpringApplication app = new SpringApplication(Workshop11Application.class);
        DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);

        List<String> optsVal = appArgs.getOptionValues("port");
        logger.info("optsVal > " + optsVal);

        String portNumber = null;
        if(optsVal == null  || optsVal.get(0) == null){
            portNumber = System.getenv("PORT");
            if(portNumber == null)
                portNumber = DEFAULT_PORT;
        }else{
            portNumber = (String)optsVal.get(0);
        }
        
        if(portNumber != null){
            app.setDefaultProperties(Collections.singletonMap("server.port", portNumber));
        }

        // using original args instead of appArgs because appArgs is manipulated
        app.run(args);
    }

}
