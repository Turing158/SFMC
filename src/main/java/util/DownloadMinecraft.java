package util;

import org.to2mbn.jmccc.mcdownloader.MinecraftDownloader;
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloaderBuilder;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter;
import org.to2mbn.jmccc.mcdownloader.provider.DownloadProviderChain;
import org.to2mbn.jmccc.mcdownloader.provider.fabric.FabricDownloadProvider;
import org.to2mbn.jmccc.mcdownloader.provider.fabric.FabricVersionList;
import org.to2mbn.jmccc.mcdownloader.provider.forge.ForgeDownloadProvider;
import org.to2mbn.jmccc.mcdownloader.provider.forge.ForgeVersionList;
import org.to2mbn.jmccc.mcdownloader.provider.liteloader.LiteloaderDownloadProvider;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.version.Version;

import java.util.concurrent.Future;
//下载类
public class DownloadMinecraft {
    ForgeDownloadProvider forgeDownloadProvider = new ForgeDownloadProvider();
    FabricDownloadProvider fabricDownloadProvider = new FabricDownloadProvider();
    LiteloaderDownloadProvider liteloaderDownloadProvider = new LiteloaderDownloadProvider();
    MinecraftDownloader downloader = MinecraftDownloaderBuilder.create()
            .providerChain(DownloadProviderChain.create()
                    .addProvider(forgeDownloadProvider)
                    .addProvider(fabricDownloadProvider))
            .build();
    Future<Version> downloading;
    public void download(String filePath,String version,CallbackAdapter<Version> callbackAdapter){
        MinecraftDirectory dir = new MinecraftDirectory(filePath+"/.minecraft");
        downloading = downloader.downloadIncrementally(dir, version,callbackAdapter);

    }
    public void shutdown(){
        downloader.shutdown();
    }
    public void cancel(){
        downloading.cancel(true);
    }

    public MinecraftDownloader getDownloader(){
        return downloader;
    }
    public void downloadForgeVersion(CallbackAdapter<ForgeVersionList> callback){
        downloader.download(forgeDownloadProvider.forgeVersionList(),callback);
    }
    public void downloadFabricVersion(CallbackAdapter<FabricVersionList> callback){
        downloader.download(fabricDownloadProvider.fabricVersionList(),callback);
    }
}
