package edu.escuelaing.arep;


import com.google.gson.Gson;
import spark.staticfiles.StaticFilesConfiguration;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * Web Spark app class
 * @author Juan Camilo Angel Hernandez
 */
public class SparkWebApp
{
    public static void main(String[] args) {
        port(getPort());
        Map<String,String> users=new HashMap<>();
        users.put("correo@mail.com",Services.hashPass("contraseña"));
        secure("keystores/ecikeystore.p12", "123456", null, null);
        Gson gson = new Gson();
        HttpClient.init();




        before("auth/*", (req, response) ->{
            req.session(true);
            if(req.session().isNew()){
                req.session().attribute("Logged",false);
            }
            boolean auth=req.session().attribute("Logged");
            if(!auth){
                halt(401, "<h1>401 Unauthorized</h1>");
            }});

        before("/login.html",((req, response) ->{
            req.session(true);
            if(req.session().isNew()){
                req.session().attribute("Logged",false);
            }
            boolean auth=req.session().attribute("Logged");
            if(auth){
                response.redirect("auth/index.html");
            }}));

        StaticFilesConfiguration staticHandler = new StaticFilesConfiguration();
        staticHandler.configure("/views");
        before((request, response) ->
                staticHandler.consume(request.raw(), response.raw()));

        get("/",((request, response) -> {
            response.redirect("/login.html");
            return "";
        }));

        get("auth/user",((req, res) -> {
            return req.session().attribute("User");
        }));

        post("/login",((request, response) -> {
            request.body();
            request.session(true);
            User user= gson.fromJson(request.body(),User.class);
            if(Services.hashPass(user.getPassword()).equals(users.get(user.getEmail()))){
                request.session().attribute("User",user.getEmail());
                request.session().attribute("Logged",true);
            }
            else{
                return "Usuario o contraseña incorrectos";
            }
            return "";

        }));


    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
