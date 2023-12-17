package entity;

import java.util.ArrayList;
import java.util.List;

public class OptiFine {
    List<OptiFineVersion> versions;
    List<OptiFineFiles> files;

    public List<OptiFineVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<OptiFineVersion> versions) {
        this.versions = versions;
    }

    public List<OptiFineFiles> getFiles() {
        return files;
    }

    public void setFiles(List<OptiFineFiles> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "OptiFine{" +
                "versions=" + versions +
                ", files=" + files +
                '}';
    }
}
