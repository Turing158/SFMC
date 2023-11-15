package Launch;

import jmccc.microsoft.MicrosoftAuthenticator;
import jmccc.microsoft.entity.MicrosoftSession;
import org.to2mbn.jmccc.auth.AuthInfo;
import org.to2mbn.jmccc.auth.Authenticator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaunchData {
    public ArrayList<String> versions;
    public Map<String, String> jreVersions;
    public AuthInfo authInfo;
    public MicrosoftAuthenticator microsoftAuthenticator;

    public File jreDir;
    public int windowSizeWidth;
    public int windowSizeHeight;
    public String playerFunc;
    public String version;
    public String username;
    public String directory;
    public int memory;

    public LaunchData() {
    }
    public LaunchData(ArrayList<String> versions, Map<String, String> jreVersions, AuthInfo authInfo,MicrosoftAuthenticator microsoftAuthenticator, File jreDir, int windowSizeWidth, int windowSizeHeight, String playerFunc, String version, String username, String directory, int memory) {
        this.versions = versions;
        this.jreVersions = jreVersions;
        this.authInfo = authInfo;
        this.microsoftAuthenticator = microsoftAuthenticator;
        this.jreDir = jreDir;
        this.windowSizeWidth = windowSizeWidth;
        this.windowSizeHeight = windowSizeHeight;
        this.playerFunc = playerFunc;
        this.version = version;
        this.username = username;
        this.directory = directory;
        this.memory = memory;
    }

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

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }
    public MicrosoftAuthenticator getMicrosoftAuthenticator() {
        return microsoftAuthenticator;
    }

    public void setMicrosoftAuthenticator(MicrosoftAuthenticator microsoftAuthenticator) {
        this.microsoftAuthenticator = microsoftAuthenticator;
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


}