package entity;

public class OptiFineVersion {
    String versions;

    public OptiFineVersion(String versions) {
        this.versions = versions;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    @Override
    public String toString() {
        return "OptiFineVersion{" +
                "versions='" + versions + '\'' +
                '}';
    }
}
