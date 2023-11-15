package util;

import Launch.LaunchMC;
import jmccc.microsoft.MicrosoftAuthenticator;
import org.to2mbn.jmccc.auth.AuthInfo;
import org.to2mbn.jmccc.auth.Authenticator;
//这个类为了能让启动类接收json储存的MicrosoftAuthenticator类
public class initAuthenticator implements Authenticator{
        private MicrosoftAuthenticator microsoftAuthenticator;


        public initAuthenticator(MicrosoftAuthenticator microsoftAuthenticator) {
            this.microsoftAuthenticator = microsoftAuthenticator;
        }

        public AuthInfo customAuth() {
            // 返回 LaunchMC.authInfo
            return LaunchMC.authInfo;
        }

        @Override
        public AuthInfo auth() {
            // 调用 customAuth 方法返回 LaunchMC.authInfo
            return customAuth();
        }
}
