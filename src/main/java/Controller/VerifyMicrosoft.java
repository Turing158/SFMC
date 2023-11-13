package Controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import jmccc.microsoft.MicrosoftAuthenticator;

public class VerifyMicrosoft {
    String verificationUri;
    String userCode;
    @FXML
    public Label tips;
    @FXML
    public Label url;
    @FXML
    public Label code;
    @FXML
    public Label waitTips;

    @FXML
    public void initialize(){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                MicrosoftAuthenticator.login(microsoftVerification -> {
                    verificationUri = microsoftVerification.verificationUri;
                    userCode = microsoftVerification.userCode;
                    Platform.runLater(() -> {
                        url.setText(verificationUri);
                        code.setText(userCode);
                        waitTips.setVisible(false);
                        url.setVisible(true);
                        code.setVisible(true);
                    });
                });

                return null;
            }
        };
        new Thread(task).start();
    }

}
