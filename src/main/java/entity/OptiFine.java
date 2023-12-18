package entity;

import java.util.ArrayList;
import java.util.List;

public class OptiFine {
    String _id;
    String mcversion;
    String patch;
    String type;
    String __v;
    String filename;
    String forge;


    public OptiFine() {
    }

    public OptiFine(String _id, String mcversion, String patch, String type, String __v, String filename, String forge) {
        this._id = _id;
        this.mcversion = mcversion;
        this.patch = patch;
        this.type = type;
        this.__v = __v;
        this.filename = filename;
        this.forge = forge;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMcversion() {
        return mcversion;
    }

    public void setMcversion(String mcversion) {
        this.mcversion = mcversion;
    }

    public String getPatch() {
        return patch;
    }

    public void setPatch(String patch) {
        this.patch = patch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getForge() {
        return forge;
    }

    public void setForge(String forge) {
        this.forge = forge;
    }

    @Override
    public String toString() {
        return "OptiFine{" +
                "_id='" + _id + '\'' +
                ", mcversion='" + mcversion + '\'' +
                ", patch='" + patch + '\'' +
                ", type='" + type + '\'' +
                ", __v='" + __v + '\'' +
                ", filename='" + filename + '\'' +
                ", forge='" + forge + '\'' +
                '}';
    }
    //    List<String> versions;
//    List<OptiFineFiles> files;
//
//    public List<String> getVersions() {
//        return versions;
//    }
//
//    public void setVersions(List<String> versions) {
//        this.versions = versions;
//    }
//
//    public List<OptiFineFiles> getFiles() {
//        return files;
//    }
//
//    public void setFiles(List<OptiFineFiles> files) {
//        this.files = files;
//    }
//
//    public OptiFine() {
//    }
//
//    public OptiFine(List<String> versions, List<OptiFineFiles> files) {
//        this.versions = versions;
//        this.files = files;
//    }
//
//    @Override
//    public String toString() {
//        return "OptiFine{" +
//                "versions=" + versions +
//                ", files=" + files +
//                '}';
//    }
}
