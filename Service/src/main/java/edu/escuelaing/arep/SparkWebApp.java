package edu.escuelaing.arep;


import static spark.Spark.*;

public class SparkWebApp {

    public static void main(String[] args) {
        port(getPort());
        secure("keystores/ecikeystore.p12","123456","keystores/myTrustStore","123456");
        get("/service",(request, response) -> "Service Response");

    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5002;
    }
}
