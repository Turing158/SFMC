package Launch;

import org.to2mbn.jmccc.auth.OfflineAuthenticator;
import org.to2mbn.jmccc.launch.LaunchException;
import org.to2mbn.jmccc.launch.Launcher;
import org.to2mbn.jmccc.launch.LauncherBuilder;
import org.to2mbn.jmccc.option.JavaEnvironment;
import org.to2mbn.jmccc.option.LaunchOption;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LaunchMC {
    public static String version = "1.9";
    public static String username = "";
    public static String directory = "";
    public static int memory = 0;
    public void start(){
        Launcher launcher = LauncherBuilder.create().printDebugCommandline(true).nativeFastCheck(true).build();
        try {
            LaunchOption option = new LaunchOption(
                    version,
                    new OfflineAuthenticator(username),
                    new MinecraftDirectory(directory));
            option.commandlineVariables().put("version_type","StarFall Starter");
            if(memory != 0){
                option.setMinMemory(memory);
            }
            launcher.launch(option);
        } catch (LaunchException | IOException e) {
            e.printStackTrace();
        }
    }
}
