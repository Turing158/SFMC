package Launch;

import jmccc.microsoft.MicrosoftAuthenticator;
import org.to2mbn.jmccc.auth.AuthInfo;
import org.to2mbn.jmccc.auth.Authenticator;
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
//启动类
public class LaunchMC {

    public static Map<String, String> jreVersions = new HashMap<>();
    public static AuthInfo authInfo;
    public static MicrosoftAuthenticator microsoftAuthenticator;
    public static File selfDir;
    public static File jreDir;
    public static Boolean versionIsolate = false;
    public static  int windowSizeWidth = 854;
    public static  int windowSizeHeight = 480;
    public static  String playerFunc = "offline";
    public static String version = "";
    public static String username = "";
    public static String directory = "";
    public static int memory = 1024;



    public static File runtimeDir;
    public static Authenticator authenticator;
    public void start(){
        if (playerFunc.equals("offline")){
            authenticator = new OfflineAuthenticator(username);
        }
        Launcher launcher = LauncherBuilder.create().printDebugCommandline(true).nativeFastCheck(false).build();
        try {
            LaunchOption option = new LaunchOption(
                    version,
                    authenticator,
                    new MinecraftDirectory(directory));
            option.commandlineVariables().put("version_type","StarFall 1.0");
            option.setWindowSize(WindowSize.window(windowSizeWidth,windowSizeHeight));
            if(jreDir != null){
                option.setJavaEnvironment(new JavaEnvironment(jreDir));
            }
            if(memory != 0){
                option.setMaxMemory(memory);
            }
            if(versionIsolate){
                option.setRuntimeDirectory(new MinecraftDirectory(runtimeDir));
            }
            option.setServerInfo(null);
            option.setMinMemory(512);
            launcher.launch(option);
        } catch (LaunchException | IOException e) {
            e.printStackTrace();
        }
    }
}
