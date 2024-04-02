package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.OptiFine;
import entity.OptiFineFiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class OptiFineDownload {

    private final String api = "https://bmclapi2.bangbang93.com/optifine";
    public OptiFineDownload() {

    }

    public List<OptiFine> getOptiFineVersions(String MinecraftVersion) {
        String link = api+"/"+MinecraftVersion;
        String jsonStr = HttpRequest.GET(link);
        try{
            ObjectMapper mapper = new ObjectMapper();
            List<OptiFine> optiFines = mapper.readValue(jsonStr, List.class);
            Collections.reverse(optiFines);
            return optiFines;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public void download(OptiFine optiFine){
        String link = api+"/"+optiFine.getMcversion()+"/"+optiFine.getType()+"/"+optiFine.getPatch();
        try{
            HttpRequest.download(link);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        OptiFineDownload optiFineDownload = new OptiFineDownload();
        System.out.println(optiFineDownload.getOptiFineVersions("1.12").toString());
        HttpRequest.download("https://bmclapi2.bangbang93.com/optifine/1.12/HD_U/G5");
//        optiFineDownload.download();
    }


}
