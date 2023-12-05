package entity;

import jmccc.microsoft.MicrosoftAuthenticator;
import org.to2mbn.jmccc.auth.AuthInfo;
import org.to2mbn.jmccc.auth.Authenticator;
//这个类为了能让启动类接收json储存的MicrosoftAuthenticator类
public class InitAuthenticator implements Authenticator{
        private MicrosoftAuthenticator microsoftAuthenticator;
        private AuthInfo authInfo;


        public InitAuthenticator(MicrosoftAuthenticator microsoftAuthenticator) {
            this.microsoftAuthenticator = microsoftAuthenticator;
        }

        public AuthInfo customAuth(AuthInfo authInfo) {
            this.authInfo = authInfo;
            // 返回 LaunchMC.authInfo
            return authInfo;
        }
        public MicrosoftAuthenticator getMicrosoftAuthenticator() {
            return microsoftAuthenticator;
        }
        @Override
        public AuthInfo auth() {
            // 调用 customAuth 方法返回 LaunchMC.authInfo
            return authInfo;
        }
}
