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
@RequestMapping(path = "/api/products",produces = "application/json")
@CrossOrigin(origins = "*")
public class Product {
    @GetMapping("/list")
    public String getProducts() {
        String qexec = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                " PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                " PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                " PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +

                "SELECT ?name ?productID ?category ?user ?description \n" +
                "WHERE {\n" +
                "   ?individual rdf:type ns1:Product ;\n" +
                "             ns1:name ?name ;\n" +
                "             ns1:productID ?productID ;\n" +
                "             ns1:category ?category ;\n" +
                "             ns1:belongs ?user ;\n" +
                "             ns1:description ?description .\n" +
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

    @GetMapping("/getByType")
    public String getTypeProducts(@RequestParam("type") String type) {
        String qexec = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                " PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                " PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                " PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +

                "SELECT ?name ?productID ?category ?user ?description \n" +
                "WHERE {\n" +
                "   ?individual rdf:type ns1:" + type + " ;\n" +
                "             ns1:name ?name ;\n" +
                "             ns1:productID ?productID ;\n" +
                "             ns1:category ?category ;\n" +
                "             ns1:belongs ?user ;\n" +
                "             ns1:description ?description .\n" +
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

    @GetMapping("/getByCategory")
    public String getCategoryProducts(@RequestParam("category") String category) {
        String qexec = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                " PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                " PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                " PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +

                "SELECT ?name ?productID ?category ?user ?description \n" +
                "WHERE {\n" +
                "   ?individual rdf:type ns1:Product ;\n" +
                "             ns1:name ?name ;\n" +
                "             ns1:productID ?productID ;\n" +
                "             ns1:category ?category ;\n" +
                "             ns1:belongs ?user ;\n" +
                "             ns1:description ?description .\n" +
                "   FILTER ( ?category = \"" + category + "\")\n" +
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
    @GetMapping("/getProductsRelations")
    public String getProductsRelations() {
        String qexec = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +
                "SELECT ?name ?productID ?category ?description ?userName ?userEmail\n" +
                "WHERE {\n" +
                "   ?individual rdf:type ns1:Product ;\n" +
                "             ns1:name ?name ;\n" +
                "             ns1:productID ?productID ;\n" +
                "             ns1:category ?category ;\n" +
                "             ns1:description ?description ;\n" +
                "             ns1:belongs ?user.\n" +
                "   ?user ns1:name ?userName ;\n" +
                "         ns1:email ?userEmail.\n" +
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
}
