package util;

import jmccc.microsoft.MicrosoftAuthenticator;
import jmccc.microsoft.core.MicrosoftAuthenticationController;
import jmccc.microsoft.core.MicrosoftAuthenticationService;
import jmccc.microsoft.core.response.MicrosoftDeviceCodeResponse;
import jmccc.microsoft.core.response.MicrosoftTokenResponse;
import jmccc.microsoft.entity.MicrosoftSession;
import jmccc.microsoft.entity.MicrosoftVerification;
import org.to2mbn.jmccc.auth.AuthInfo;
import org.to2mbn.jmccc.auth.AuthenticationException;

import java.io.IOException;
import java.util.function.Consumer;

public class MicrosoftLogin {
    public static void login() throws AuthenticationException {
        MicrosoftAuthenticator a = MicrosoftAuthenticator.login(microsoftVerification -> {
            System.out.println("url:"+microsoftVerification.verificationUri);
            System.out.println("code:"+microsoftVerification.userCode);
        });
        AuthInfo info = a.auth();
        System.out.println("info:"+info.toString());
        System.out.println("session:"+a.getSession());
    }


}
