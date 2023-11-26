package util;

import Launch.LaunchData;
import Launch.LaunchMC;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.to2mbn.jmccc.auth.AuthInfo;

import java.io.File;
import java.io.FileWriter;

//json保存写入类
public class JsonOperate {
    public void save(){
//        创建启动类entity类
        LaunchData launchData = new LaunchData(LaunchMC.jreVersions,LaunchMC.authInfo,LaunchMC.microsoftAuthenticator,LaunchMC.selfDir,LaunchMC.jreDir,LaunchMC.autoMemory,LaunchMC.versionIsolate,LaunchMC.windowSizeWidth,LaunchMC.windowSizeHeight,LaunchMC.playerFunc,LaunchMC.version,LaunchMC.username,LaunchMC.directory,LaunchMC.memory);
//        导入Json库
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        ObjectWriter objectWriter= mapper.writerWithDefaultPrettyPrinter();
        try {
//            写入的json字符串[将LaunchData转换成json]
            String json = objectWriter.writeValueAsString(launchData);
            File file = new File(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent()+"/sfmc.json");
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void load(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixInAnnotations(AuthInfo.class, AuthInfoMixin.class);
        try {
//            获取json文件
            File file = new File(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent()+"/sfmc.json");
//            写入json到entity
            LaunchData launchData = mapper.readValue(file,LaunchData.class);
//            将数据传到LaunchMC
            LaunchMC.jreVersions = launchData.getJreVersions();
            LaunchMC.authInfo = launchData.getAuthInfo();
            LaunchMC.microsoftAuthenticator = launchData.getMicrosoftAuthenticator();
            LaunchMC.selfDir = launchData.getSelfDir();
            LaunchMC.jreDir = launchData.getJreDir();
            LaunchMC.autoMemory = launchData.getAutoMemory();
            LaunchMC.versionIsolate = launchData.getVersionIsolate();
            LaunchMC.windowSizeWidth = launchData.getWindowSizeWidth();
            LaunchMC.windowSizeHeight = launchData.getWindowSizeHeight();
            LaunchMC.playerFunc = launchData.getPlayerFunc();
            LaunchMC.version = launchData.getVersion();
            LaunchMC.username = launchData.getUsername();
            LaunchMC.directory = launchData.getDirectory();
            LaunchMC.memory = launchData.getMemory();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean exist(){
        File file = new File(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent()+"/sfmc.json");
        return file.exists();
    }
}
