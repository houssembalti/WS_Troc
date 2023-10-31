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
@RequestMapping(path = "/api/reviews",produces = "application/json")
@CrossOrigin(origins = "*")
public class Reviews {
    @GetMapping("/list")
    public String getReviews() {
        String qexec = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                " PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                " PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                " PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +

                "SELECT ?reviewID ?rating ?feedback ?user  \n" +
                "WHERE {\n" +
                "   ?individual rdf:type ns1:Review ;\n" +
                "             ns1:reviewID ?reviewID ;\n" +
                "             ns1:rating ?rating ;\n" +
                "             ns1:feedback ?feedback ;\n" +
                "             ns1:belongs ?user .\n" +
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
    public String getTypeReviews(@RequestParam("type") String type) {
        String qexec = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                " PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                " PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                " PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +

                "SELECT ?reviewID ?rating ?feedback ?user  \n" +
                "WHERE {\n" +
                "   ?individual rdf:type ns1:" + type + " ;\n" +
                "             ns1:reviewID ?reviewID ;\n" +
                "             ns1:rating ?rating ;\n" +
                "             ns1:feedback ?feedback ;\n" +
                "             ns1:belongs ?user .\n" +
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

    @GetMapping("/getByRating")
    public String getRatingsReviews(@RequestParam("rating") String rating) {
        String qexec = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                " PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                " PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                " PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +

                "SELECT ?reviewID ?rating ?feedback ?user  \n" +
                "WHERE {\n" +
                "   ?individual rdf:type ns1:Review ;\n" +
                "             ns1:reviewID ?reviewID ;\n" +
                "             ns1:rating ?rating ;\n" +
                "             ns1:feedback ?feedback ;\n" +
                "             ns1:belongs ?user .\n" +
                "   FILTER ( ?rating = \"" + rating + "\")\n" +
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
    @GetMapping("/getReviewsRelations")
    public String getReviewsRelations() {
        String qexec = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +
                "SELECT ?reviewID ?rating ?feedback ?userName ?userEmail\n" +
                "WHERE {\n" +
                "   ?individual rdf:type ns1:Review ;\n" +
                "             ns1:reviewID ?reviewID ;\n" +
                "             ns1:rating ?rating ;\n" +
                "             ns1:feedback ?feedback ;\n" +
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
