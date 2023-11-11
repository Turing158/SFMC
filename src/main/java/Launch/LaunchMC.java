package Launch;

import org.to2mbn.jmccc.auth.OfflineAuthenticator;
import org.to2mbn.jmccc.launch.LaunchException;
import org.to2mbn.jmccc.launch.Launcher;
import org.to2mbn.jmccc.launch.LauncherBuilder;
import org.to2mbn.jmccc.option.JavaEnvironment;
import org.to2mbn.jmccc.option.LaunchOption;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.option.WindowSize;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaunchMC {

    public static ArrayList<String> versions = new ArrayList<>();
    public static Map<String, String> jreVersions = new HashMap<>();
    public static File selfJreDir;


    public static File jreDir;
    public static  int windowSizeWidth = 854;
    public static  int windowSizeHeight = 480;
    public static  String playerFunc = "";
    public static String version = "";
    public static String username = "";
    public static String directory = "";
    public static int memory = 1024;
    public void start(){
        Launcher launcher = LauncherBuilder.create().printDebugCommandline(true).nativeFastCheck(false).build();
        try {
            LaunchOption option = new LaunchOption(
                    version,
                    new OfflineAuthenticator(username),
                    new MinecraftDirectory(directory));
            option.commandlineVariables().put("version_type","StarFall 1.0");
            option.setWindowSize(WindowSize.window(windowSizeWidth,windowSizeHeight));
            if(jreDir != null){
                option.setJavaEnvironment(new JavaEnvironment(jreDir));
            }
            if(memory != 0){
                option.setMaxMemory(memory);
            }
            launcher.launch(option);
        } catch (LaunchException | IOException e) {
            e.printStackTrace();
        }
    }
}
