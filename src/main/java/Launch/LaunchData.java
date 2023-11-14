package Launch;

import org.to2mbn.jmccc.auth.AuthInfo;
import org.to2mbn.jmccc.auth.Authenticator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaunchData {
    public ArrayList<String> versions;
    public Map<String, String> jreVersions;
    public String Model;
    public AuthInfo authInfo;


    public File jreDir;
    public int windowSizeWidth;
    public int windowSizeHeight;
    public String playerFunc;
    public String version;
    public String username;
    public String directory;
    public int memory;
    public Authenticator authenticator;


    public ArrayList<String> getVersions() {
        return versions;
    }

    public void setVersions(ArrayList<String> versions) {
        this.versions = versions;
    }

    public Map<String, String> getJreVersions() {
        return jreVersions;
    }

    public void setJreVersions(Map<String, String> jreVersions) {
        this.jreVersions = jreVersions;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    public File getJreDir() {
        return jreDir;
    }

    public void setJreDir(File jreDir) {
        this.jreDir = jreDir;
    }

    public int getWindowSizeWidth() {
        return windowSizeWidth;
    }

    public void setWindowSizeWidth(int windowSizeWidth) {
        this.windowSizeWidth = windowSizeWidth;
    }

    public int getWindowSizeHeight() {
        return windowSizeHeight;
    }

    public void setWindowSizeHeight(int windowSizeHeight) {
        this.windowSizeHeight = windowSizeHeight;
    }

    public String getPlayerFunc() {
        return playerFunc;
    }

    public void setPlayerFunc(String playerFunc) {
        this.playerFunc = playerFunc;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }
    public LaunchData() {
    }

    public LaunchData(ArrayList<String> versions, Map<String, String> jreVersions, String model, AuthInfo authInfo, File jreDir, int windowSizeWidth, int windowSizeHeight, String playerFunc, String version, String username, String directory, int memory, Authenticator authenticator) {
        this.versions = versions;
        this.jreVersions = jreVersions;
        Model = model;
        this.authInfo = authInfo;
        this.jreDir = jreDir;
        this.windowSizeWidth = windowSizeWidth;
        this.windowSizeHeight = windowSizeHeight;
        this.playerFunc = playerFunc;
        this.version = version;
        this.username = username;
        this.directory = directory;
        this.memory = memory;
        this.authenticator = authenticator;
    }
}
