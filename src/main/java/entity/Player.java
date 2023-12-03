package entity;

import org.to2mbn.jmccc.auth.Authenticator;

public class Player {
    String state;
    Authenticator authenticator;
    public Player(){}
    public Player(String state, Authenticator authenticator) {
        this.state = state;
        this.authenticator = authenticator;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }
}
