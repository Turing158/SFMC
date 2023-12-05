package Launch;

import entity.Player;
import jmccc.microsoft.MicrosoftAuthenticator;
import org.to2mbn.jmccc.auth.AuthInfo;
import org.to2mbn.jmccc.auth.Authenticator;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
//用于json储存和写入
public class LaunchData {
    public Map<String, String> jreVersions;
    public ArrayList<Player> players;
    public int selectPlayer;
    public File selfDir;
    public File jreDir;
    public Boolean autoMemory;
    public Boolean versionIsolate;
    public int windowSizeWidth;
    public int windowSizeHeight;
    public String version;
    public String username;
    public String directory;
    public int memory;

    public LaunchData() {
    }

    public LaunchData(Map<String, String> jreVersions, ArrayList<Player> players, int selectPlayer, File selfDir, File jreDir, Boolean autoMemory, Boolean versionIsolate, int windowSizeWidth, int windowSizeHeight, String version, String username, String directory, int memory) {
        this.jreVersions = jreVersions;
        this.players = players;
        this.selectPlayer = selectPlayer;
        this.selfDir = selfDir;
        this.jreDir = jreDir;
        this.autoMemory = autoMemory;
        this.versionIsolate = versionIsolate;
        this.windowSizeWidth = windowSizeWidth;
        this.windowSizeHeight = windowSizeHeight;
        this.version = version;
        this.username = username;
        this.directory = directory;
        this.memory = memory;
    }

    public int getSelectPlayer() {
        return selectPlayer;
    }

    public void setSelectPlayer(int selectPlayer) {
        this.selectPlayer = selectPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
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