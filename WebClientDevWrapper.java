
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLSocket;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import java.io.IOException;
import javax.net.ssl.*;



public class WebClientDevWrapper {
    private static final String TLS_INSTANCE = "TLS";
    private static final String HTTPS_PROTOCOL = "https";
    private static final int HTTPS_PORT = 443;
    public static HttpClient wrapClient(HttpClient base) {

        try {
            SSLContext ctx = SSLContext.getInstance(TLS_INSTANCE);
            X509TrustManager tm = initialiseTrustManager();
            X509HostnameVerifier verifier = initialiseHostNameVerifier();
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(verifier);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme(HTTPS_PROTOCOL, ssf, HTTPS_PORT));
            return new DefaultHttpClient(ccm, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static X509TrustManager initialiseTrustManager(){
        return new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] xcs, String string)
                    throws CertificateException {
            }
            public void checkServerTrusted(X509Certificate[] xcs, String string)
                    throws CertificateException {
            }
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
    }
    private static X509HostnameVerifier initialiseHostNameVerifier(){
        return new X509HostnameVerifier() {
            @Override
            public void verify(String string, SSLSocket ssls)
                    throws IOException {
            }
            @Override
            public void verify(String string, X509Certificate xc)
                    throws SSLException {
            }
            @Override
            public void verify(String string, String[] strings, String[] strings1)
                    throws SSLException {
            }
            @Override
            public boolean verify(String string, SSLSession ssls) {
                return true;
            }
        };
    }
}