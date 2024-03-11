import io.github.skippyall.vote.core.config.Config;
import io.github.skippyall.vote.core.misc.EnvironmentVars;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

import java.io.File;

public class ConfigText {
    public static void main(String[] args){
        Configurator.initialize(new DefaultConfiguration()).start();
        EnvironmentVars.dataFolder = new File("/home/falko/IdeaProjects/Vote/core/src/test/resources");
        Config.load();
        System.out.println(Config.getStorageType().name());
        Config.save();
    }
}
