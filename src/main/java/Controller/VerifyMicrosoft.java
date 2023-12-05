package Controller;

import Launch.LaunchMC;
import entity.Player;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import jmccc.microsoft.MicrosoftAuthenticator;
import util.EffectAnimation;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class VerifyMicrosoft {
    @FXML
    public Label message1;
    @FXML
    public Label message2;
    @FXML
    public Button cancel;
    String verificationUri;
    String userCode;
    @FXML
    public Label url;
    @FXML
    public Label code;
    @FXML
    public Label waitTips;

    @FXML
    public void initialize(){
        EffectAnimation effect = new EffectAnimation();
//        防止I/O阻塞
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
//                调用微软验证登录函数
                MicrosoftAuthenticator microsoftAuthenticator = MicrosoftAuthenticator.login(microsoftVerification -> {
//                    获取到的url和code展示
                    verificationUri = microsoftVerification.verificationUri;
                    userCode = microsoftVerification.userCode;
//                    打开浏览器
                    openBrowser(microsoftVerification.verificationUri);
//                    复制代码
                    copyCode();
//                    在多线程里得用这个方法来调整界面
                    Platform.runLater(() -> {
                        url.setText(verificationUri);
                        code.setText(userCode);
                        waitTips.setVisible(false);
                        effect.fadeEmergeVanish(0.5,true,message1,message2,url,code);
                    });
                });
                if (microsoftAuthenticator.auth() != null){
                    System.out.println(microsoftAuthenticator.auth().getUsername());
                    ArrayList<Player> players = LaunchMC.players;
                    System.out.println("xun");
                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getAuthInfo() != null && players.get(i).getAuthInfo().getUsername().equals(microsoftAuthenticator.auth().getUsername())) {
                            players.remove(players.get(i));
                            break;
                        }
                    }
                    //                获取到的信息存入启动类里，方便用于json储存
                    Player player = new Player();
                    player.setMicrosoftAuthenticator(microsoftAuthenticator);
                    player.setAuthInfo(microsoftAuthenticator.auth());
                    LaunchMC.selectPlayer = players.size();
                    LaunchMC.players.add(player);
//                在多线程里得用这个方法来调整界面
                    Platform.runLater(() -> {
                        closePage();
                    });

                }
                else {
                    Platform.runLater(() -> {
                        message2.setText("登录失败，请重试\t1.需要刷新微软Minecraft的profile\n2.你没有购买Minecraft或者Minecraft绑定微软账户");
                    });
                }
                return null;
            }
        };
        new Thread(task).start();
    }
//    打开浏览器方法
    public void openBrowser(String url){
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                URI uri = new URI(url);
                desktop.browse(uri);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("不支持打开浏览器");
        }
    }
    public void closePage(){
        EffectAnimation effect = new EffectAnimation();
        AnchorPane parent = (AnchorPane) waitTips.getParent().getParent();
        AnchorPane main = (AnchorPane) parent.getParent();
        effect.fadeEmergeVanish(0.2,false,parent);
        AnchorPane playerPane = (AnchorPane) main.getChildren().get(2);
        playerPane.getChildren().setAll(new Frame().player());
        effect.fadeEmergeVanish(0.2,true,playerPane);
    }
//  复制方法
    public void copyUrl(){
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        cb.setContents(new StringSelection(verificationUri), null);
    }
//  复制方法
    public void copyCode(){
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        cb.setContents(new StringSelection(userCode), null);
    }
}
