package util;

import jmccc.microsoft.MicrosoftAuthenticator;
import jmccc.microsoft.core.MicrosoftAuthenticationController;
import jmccc.microsoft.core.MicrosoftAuthenticationService;
import jmccc.microsoft.entity.MicrosoftVerification;
import org.to2mbn.jmccc.auth.AuthInfo;
import org.to2mbn.jmccc.auth.AuthenticationException;

import java.util.function.Consumer;

public class MicrosoftLogin {
    public void login() throws AuthenticationException {
        MicrosoftAuthenticator a = MicrosoftAuthenticator.login(new Consumer<MicrosoftVerification>() {
            @Override
            public void accept(MicrosoftVerification microsoftVerification) {
                System.out.println("url:"+microsoftVerification.verificationUri);
                System.out.println("code:"+microsoftVerification.userCode);
            }
        });
        AuthInfo info = a.auth();
        System.out.println("info:"+info.toString());
        System.out.println("session:"+a.getSession());
    }

}
