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
@RequestMapping(path = "/api/contracts",produces = "application/json")
@CrossOrigin(origins = "*")

public class Contract {
    @GetMapping("/contractuser/{contractID}")
    public String getContractDetails(@PathVariable("contractID") String contractID) {
        String qexec = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "SELECT ?contractID ?type ?user ?terms ?userName\n" +
                "WHERE {\n" +
                "   ?contract rdf:type ns1:Contract ;\n" +
                "             ns1:contractID ?contractID ;\n" +
                "             ns1:type ?type ;\n" +
                "             ns1:signed ?user ;\n" +
                "             ns1:terms ?terms .\n" +
                "   ?user rdf:type ns1:User ;\n" +
                "        ns1:name ?userName ;\n" +
                "        ns1:contract ?contract .\n" +
                "   FILTER (?contractID = \"" + contractID + "\") .\n" +
                "}";

        Model model = JenaEngine.readModel("data/final.owl");
        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(outputStream, results);

        // Convert the JSON result to a string
        String json = new String(outputStream.toByteArray());

        // Parse the JSON result
        JSONObject j = new JSONObject(json);
        JSONArray res = j.getJSONObject("results").getJSONArray("bindings");

        return res.toString();

    }


    @GetMapping("/AllContracts")
    public String getContracts() {
        String qexec = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                " PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                " PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                " PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +
                "SELECT ?contractID ?type ?user ?terms \n" +
                "WHERE {\n" +
                "   ?individual rdf:type ns1:Contract ;\n" +
                "             ns1:contractID ?contractID ;\n" +
                "             ns1:type ?type ;\n" +
                "             ns1:signed ?user ;\n" +
                "             ns1:terms ?terms .\n" +
                "}\n";

        Model model = JenaEngine.readModel("data/final.owl");
        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(outputStream, results);
        String json = new String(outputStream.toByteArray());

        JSONObject j = new JSONObject(json);
        System.out.println(j.getJSONObject("results").getJSONArray("bindings"));
        JSONArray res = j.getJSONObject("results").getJSONArray("bindings");
        return j.getJSONObject("results").getJSONArray("bindings").toString();
    }



    @GetMapping("/getType")
    public String getContractsType (@RequestParam("type") String type) {
        String qexec = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
        " PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
        " PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
        " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
        " PREFIX ns1: <http://www.example.org/ontology#>\n" +
        "\n" +
        "SELECT ?contractID ?type ?user ?terms \n" +
        "WHERE {\n" +
        "   ?individual rdf:type ns1:" + type + " ;\n" +
        "             ns1:contractID ?contractID ;\n" +
        "             ns1:type ?type ;\n" +
        "             ns1:signed ?user ;\n" +
        "             ns1:terms ?terms .\n" +
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
    @GetMapping("/getByUser")
    public String getContractsByUser(@RequestParam("user") String user) {
        String qexec = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                " PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                " PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                " PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +
                "SELECT ?contractID ?type ?user ?terms \n" +
                "WHERE {\n" +
                "   ?individual rdf:type ns1:Contract ;\n" +
                "             ns1:contractID ?contractID ;\n" +
                "             ns1:type ?type ;\n" +
                "             ns1:signed ns1:" + user + " ;\n" + // Assuming "user" is the user's ID
                "             ns1:terms ?terms .\n" +
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

