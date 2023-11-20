package Controller;

import Launch.LaunchMC;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
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

public class VerifyMicrosoft {
    @FXML
    public Label message1;
    @FXML
    public Label message2;
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
                    System.out.println("start verify");
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
//                获取到的信息存入启动类里，方便用于json储存
                LaunchMC.authInfo = microsoftAuthenticator.auth();
                LaunchMC.microsoftAuthenticator = microsoftAuthenticator;
                LaunchMC.authenticator = microsoftAuthenticator;
//                在多线程里得用这个方法来调整界面
                Platform.runLater(() -> {
                    AnchorPane parent = (AnchorPane) waitTips.getParent().getParent();
                    parent.setVisible(false);
                    effect.fadeEmergeVanish(0.5,true,parent);
                    parent.getChildren().setAll(new Frame().player());
                });
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
