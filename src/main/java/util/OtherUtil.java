package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

//其他杂七杂八的方法
public class OtherUtil {
//    获取本机jre版本
    public Map<String,String> getJreVersions(){
        Map<String,String> jreVersions = new HashMap<>();
        String javaHome = System.getenv("JAVA_HOME");
        if (javaHome != null) {
            File jreDir = new File(javaHome + File.separator + "jre");
            if (jreDir.exists() && jreDir.isDirectory()) {
                String version = getJavaVersion(jreDir.getAbsolutePath());
                jreVersions.put(jreDir.getAbsolutePath(),version);
            }
        }
        Preferences prefs = Preferences.userRoot().node("Software\\JavaSoft\\Java Runtime Environment");
        String[] jreKeys;
        try {
            jreKeys = prefs.childrenNames();
            for (String key : jreKeys) {
                Preferences jrePref = prefs.node(key);
                String javaHomeDir = jrePref.get("JavaHome", "Unknown");
                String version = jrePref.get("MicroVersion", "Unknown");
                jreVersions.put(javaHomeDir,version);

            }
            return jreVersions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    格式化path格式
    private String getJavaVersion(String jrePath){
        try {
            Process process = Runtime.getRuntime().exec(jrePath + File.separator + "bin" + File.separator + "java -version");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("java version")) {
                    return line.split(" ")[2].replace("\"", "");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

}
