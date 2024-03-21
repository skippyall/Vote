import io.github.skippyall.vote.core.config.Config;
import io.github.skippyall.vote.core.misc.EnvironmentVars;

import java.io.File;

public class ConfigText {
    public static void main(String[] args){
        EnvironmentVars.dataFolder = new File("/home/falko/IdeaProjects/VoteCore/core/src/test/resources");
        Config.load();
        System.out.println(Config.getStorageType().name());
        Config.save();
    }
}
