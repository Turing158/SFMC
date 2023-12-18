package entity;

import java.util.ArrayList;
import java.util.List;

public class OptiFine {
    List<String> versions;
    List<OptiFineFiles> files;

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }

    public List<OptiFineFiles> getFiles() {
        return files;
    }

    public void setFiles(List<OptiFineFiles> files) {
        this.files = files;
    }

    public OptiFine() {
    }

    public OptiFine(List<String> versions, List<OptiFineFiles> files) {
        this.versions = versions;
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
