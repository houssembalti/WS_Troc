package tn.esprit.ws_troc.controller;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.ws_troc.tools.JenaEngine;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping(path = "/api/message",produces = "application/json")
@CrossOrigin(origins = "*")
public class Message {
    @GetMapping("/messages")
    public String getMessages() {
        String qexec = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +
                "SELECT ?messageType ?messageID ?content ?sentBy\n" +
                "WHERE {\n" +
                "?message rdf:type ?messageType ;\n" +
                "         ns1:messageID ?messageID ;\n" +
                "         ns1:content ?content ;\n" +
                "         ns1:sent ?sentBy .\n" +
                "}";

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
    @GetMapping("/getMessagesRelations")
    public String getMessagesRelations() {
        String qexec = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX ns1: <http://www.example.org/ontology#>\n" +
                "\n" +
                "SELECT ?messageType ?messageID ?content ?sentBy ?user ?userName ?userEmail\n" +
                "WHERE {\n" +
                "?message rdf:type ?messageType ;\n" +
                "         ns1:messageID ?messageID ;\n" +
                "         ns1:content ?content ;\n" +
                "         ns1:sent ?sentBy .\n" +
                "?sentBy ns1:name ?userName ;\n" +
                "         ns1:email ?userEmail.\n" +
                "}";

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
