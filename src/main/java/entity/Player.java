package entity;

import jmccc.microsoft.MicrosoftAuthenticator;
import org.to2mbn.jmccc.auth.AuthInfo;
import org.to2mbn.jmccc.auth.Authenticator;
import org.to2mbn.jmccc.auth.OfflineAuthenticator;

public class Player {
    String offUsername;
    MicrosoftAuthenticator microsoftAuthenticator;
    AuthInfo authInfo;

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    public Player(){}

    public String getOffUsername() {
        return offUsername;
    }

    public void setOffUsername(String offUsername) {
        this.offUsername = offUsername;
    }

    public MicrosoftAuthenticator getMicrosoftAuthenticator() {
        return microsoftAuthenticator;
    }

    public void setMicrosoftAuthenticator(MicrosoftAuthenticator microsoftAuthenticator) {
        this.microsoftAuthenticator = microsoftAuthenticator;
    }
}
