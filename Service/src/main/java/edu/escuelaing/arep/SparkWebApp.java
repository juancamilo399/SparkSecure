package edu.escuelaing.arep;


import static spark.Spark.*;

/**
 * Spark Web App que ofrece el endpoint del servicio
 */
public class SparkWebApp {

    public static void main(String[] args) {
        port(getPort());
        secure("keystores/ecikeystore.p12","123456","keystores/myTrustStore","123456");
        get("/service",(request, response) -> "Service Response");

    }

    /**
     * retorna el puerto del ambiente, sino se encuentra retorna el puerto 5002
     * @return un entero especificando el puerto
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5002;
    }
}
