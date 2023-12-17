package entity;

public class OptiFineFiles {
    String name;
    String md5;
    String time;
    String version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "OptiFineFiles{" +
                "name='" + name + '\'' +
                ", md5='" + md5 + '\'' +
                ", time='" + time + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
