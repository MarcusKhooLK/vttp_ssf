package sg.edu.nus.iss.workshop13;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.iss.workshop13.util.IOUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class Workshop13Application {

    private static final Logger logger = Logger.getLogger(Workshop13Application.class.getName());

    private static final String DATA_DIR = "dataDir";

    public static void main(String[] args) {
        
        logger.log(Level.INFO, "Workshop 13");
        SpringApplication app = new SpringApplication(Workshop13Application.class);
        DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);

        List<String> optsVal = appArgs.getOptionValues(DATA_DIR);
        logger.log(Level.INFO, "optsVal > " + optsVal);

        if(optsVal != null){
            IOUtil.createDirectory((String)optsVal.get(0));
        }else{
            logger.log(Level.WARNING, "No directory path specified in command arguments");
        }

        app.run(args);
    }

}
