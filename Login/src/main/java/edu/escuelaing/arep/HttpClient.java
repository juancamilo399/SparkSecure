package edu.escuelaing.arep;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class HttpClient {

    public static void init() {
        File trustStoreFile = new File("keystores/myTrustStore");
        char[] trustStorePassword = "123456".toCharArray();
        KeyStore trustStore = null;
        TrustManagerFactory tmf = null;
        SSLContext sslContext = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);
            tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);
        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static String getService() throws IOException {
        System.out.println("entre");
        URL url = new URL("https://localhost:5002/service");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();

        }
    }
}
