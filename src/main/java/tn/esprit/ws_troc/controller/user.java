package tn.esprit.ws_troc.controller;


import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ws_troc.tools.JenaEngine;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping(path = "/api/user",produces = "application/json")
@CrossOrigin(origins = "*")
public class user {
    @GetMapping("/getallusers")
    public String getusers() {
        String qexec =  "  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                " PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +
                "SELECT ?name ?email ?username ?location ?userID\n" +
                "WHERE {\n" +
                "  ?individual rdf:type ns1:User ;\n" +
                " ns1:name ?name ;\n" +
                " ns1:email ?email ;\n" +
                " ns1:username ?username ;\n" +
                " ns1:location ?location ;\n" +
                " ns1:userID ?userID .\n" +


                "}\n";

        Model model = JenaEngine.readModel("data/final.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        JSONObject j = new JSONObject(json);
        System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

        JSONArray res = j.getJSONObject("results").getJSONArray("bindings");


        return j.getJSONObject("results").getJSONArray("bindings").toString();
    }
    @GetMapping("/guests")
    public String getguest() {
        String qexec =  "  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                " PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +
                "SELECT ?name ?email ?username ?location\n" +
                "WHERE {\n" +
                "  ?individual rdf:type ns1:guest ;\n" +
                " ns1:name ?name ;\n" +
                " ns1:email ?email ;\n" +
                " ns1:username ?username ;\n" +
                " ns1:location ?location .\n" +
                "}\n";

        Model model = JenaEngine.readModel("data/final.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        JSONObject j = new JSONObject(json);
        System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

        JSONArray res = j.getJSONObject("results").getJSONArray("bindings");


        return j.getJSONObject("results").getJSONArray("bindings").toString();
    }
    @GetMapping("/filteruser/{location}")
    public String getuserbylocation(@PathVariable("location") String location) {
        System.out.println(location);
        String qexec =  "  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                " PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +
                "SELECT ?name ?email ?username ?location\n" +
                "WHERE {\n" +
                "  ?individual rdf:type ns1:User;\n" +
                " ns1:name ?name ;\n" +
                " ns1:email ?email ;\n" +
                " ns1:username ?username ;\n" +
                " ns1:location ?location .\n" +
                "  FILTER (?location = \""+location+ "\") \n"+

                "}\n";

        Model model = JenaEngine.readModel("data/final.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());
        System.out.println(json);
        JSONObject j = new JSONObject(json);
        System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

        JSONArray res = j.getJSONObject("results").getJSONArray("bindings");


        return j.getJSONObject("results").getJSONArray("bindings").toString();
    }

    @GetMapping("/user/{type}")
    public String getusersbytype(@PathVariable("type") String type) {
        System.out.println(type);
        String qexec =  "  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                " PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +
                "SELECT ?name ?email ?username ?location\n" +
                "WHERE {\n" +
                "   ?individual rdf:type ns1:" + type + " ;\n" +
                " ns1:name ?name ;\n" +
                " ns1:email ?email ;\n" +
                " ns1:username ?username ;\n" +
                " ns1:location ?location .\n" +

                "}\n";

        Model model = JenaEngine.readModel("data/final.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String

        String json = new String(outputStream.toByteArray());
        System.out.println(json);
        JSONObject j = new JSONObject(json);
        System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

        JSONArray res = j.getJSONObject("results").getJSONArray("bindings");


        return j.getJSONObject("results").getJSONArray("bindings").toString();
    }
    @GetMapping("/req")
    public String getuser_request() {
        String qexec =  "  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                " PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +
                "SELECT ?name ?email ?username ?location ?status ?userID \n" +
                "WHERE {\n" +
                "   ?individual rdf:type ns1:User ;\n" +
                " ns1:name ?name ;\n" +
                " ns1:email ?email ;\n" +
                " ns1:username ?username ;\n" +
                " ns1:location ?location ;\n" +
                " ns1:userID ?userID ;\n" +
                "   ns1:initiates ?request .\n" +
                "   ?request ns1:status ?status .\n" +
                "}\n";

        Model model = JenaEngine.readModel("data/final.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String

        String json = new String(outputStream.toByteArray());
        System.out.println(json);
        JSONObject j = new JSONObject(json);
        System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

        JSONArray res = j.getJSONObject("results").getJSONArray("bindings");


        return j.getJSONObject("results").getJSONArray("bindings").toString();
    }


}
