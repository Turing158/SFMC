package Launch;

import jmccc.microsoft.MicrosoftAuthenticator;
import org.to2mbn.jmccc.auth.AuthInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
//用于json储存和写入
public class LaunchData {
    public Map<String, String> jreVersions;
    public AuthInfo authInfo;
    public MicrosoftAuthenticator microsoftAuthenticator;
    public File selfDir;
    public File jreDir;
    public Boolean autoMemory;
    public Boolean versionIsolate;
    public int windowSizeWidth;
    public int windowSizeHeight;
    public String playerFunc;
    public String version;
    public String username;
    public String directory;
    public int memory;

    public LaunchData() {
    }

    public LaunchData(Map<String, String> jreVersions, AuthInfo authInfo, MicrosoftAuthenticator microsoftAuthenticator, File selfDir, File jreDir, Boolean autoMemory, Boolean versionIsolate, int windowSizeWidth, int windowSizeHeight, String playerFunc, String version, String username, String directory, int memory) {
        this.jreVersions = jreVersions;
        this.authInfo = authInfo;
        this.microsoftAuthenticator = microsoftAuthenticator;
        this.selfDir = selfDir;
        this.jreDir = jreDir;
        this.autoMemory = autoMemory;
        this.versionIsolate = versionIsolate;
        this.windowSizeWidth = windowSizeWidth;
        this.windowSizeHeight = windowSizeHeight;
        this.playerFunc = playerFunc;
        this.version = version;
        this.username = username;
        this.directory = directory;
        this.memory = memory;
    }

    public Boolean getAutoMemory() {
        return autoMemory;
    }

    public void setAutoMemory(Boolean autoMemory) {
        this.autoMemory = autoMemory;
    }

    public Boolean getVersionIsolate() {
        return versionIsolate;
    }

    public void setVersionIsolate(Boolean versionIsolate) {
        this.versionIsolate = versionIsolate;
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

    public File getSelfDir() {
        return selfDir;
    }

    public void setSelfDir(File selfDir) {
        this.selfDir = selfDir;
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