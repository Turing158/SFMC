package entity;

public class Launcher_profiles {
    Profiles profiles;
    Settings settings;
    int version;
}
class Profiles{
    Serialize serialize;
    class Serialize{
        String created;
        String gameDir;
        String icon;
        String javaArgs;
        String javaDir;
        String lastUsed;
        String lastVersionId;
        String name;
        Resolution resolution;
        String type;
        class Resolution{
            int height;
            int width;
        }
    }
}
class Settings{
    boolean crashAssistance;
    boolean enableAdvanced;
    boolean enableAnalytics;
    boolean enableHistorical;
    boolean enableReleases;
    boolean enableSnapshots;
    boolean keepLauncherOpen;
    String profileSorting;
    boolean showGameLog;
    boolean showMenu;
    boolean soundOn;
}