package tn.esprit.ws_troc.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user",produces = "application/json")
@CrossOrigin(origins = "*")
public class user {
}
