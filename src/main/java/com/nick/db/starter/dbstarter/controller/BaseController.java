package com.nick.db.starter.dbstarter.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
//import com.nick.db.starter.dbstarter.model.APIJSON;
//import com.nick.db.starter.dbstarter.model.Query;

import com.nick.db.starter.dbstarter.model.Users;
import com.nick.db.starter.dbstarter.repository.UsersRepository;
import com.nick.db.starter.dbstarter.service.DataLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * MainController class that provides the routing of requests for the web application Annotated with Controller,
 * RequestMapping '/', and JasonIgnoreProperties
 */
@JsonIgnoreProperties
@RequestMapping("/")
@Controller
public class BaseController {

    private static final Logger weblog = LoggerFactory.getLogger(BaseController.class);

    private String page;

    private final UsersRepository usersRepository;
    private final DataLoaderService dataLoaderService;


    /**
     * Constructor for BaseController
     *
     * @throws JsonProcessingException
     *         JSON encoding exception
     */
    private BaseController(UsersRepository usersRepository, DataLoaderService dataLoaderService) throws JsonProcessingException {

        this.usersRepository = usersRepository;
        this.dataLoaderService = dataLoaderService;
    }


    /**
     * Mapping of GET '/default' request to default page
     *
     * @param model
     *         The model that is passed in to the web document
     *
     * @return The page to serve
     */
    @GetMapping("/main")
    public String main(Model model, HttpServletResponse response, HttpServletRequest httpServletRequest) {

        System.out.println(model.toString());
        model.addAttribute("SpringBoot", true);
        page = "main";
        return page;
    }


    /**
     * Mapping of GET '/users' request to users page
     *
     * @param model
     *         The model that is passed in to the web document
     *
     * @return The page to serve
     */
    @GetMapping("/users")
    public String users(Model model, HttpServletResponse response, HttpServletRequest httpServletRequest) {

        System.out.println(model.toString());

        List<Users> allUsers = usersRepository.findAll();
        System.out.println(allUsers);

        model.addAttribute("SpringBoot", true);
        model.addAttribute("Users", allUsers);
        page = "users";
        return page;
    }

    @GetMapping("/load-users")
    public String loadUsers(Model model, HttpServletResponse response, HttpServletRequest httpServletRequest) {

        System.out.println(model.toString());

        ArrayList<Users> usersList = dataLoaderService.loadUsersFromCSV("users.csv");
        HashMap<String, ArrayList<Users>> savedUsersToDB = dataLoaderService.saveUsersToDB(usersList);

        System.out.println("New users added: " + savedUsersToDB.get("usersToAdd"));
        System.out.println("Users already exist in database: " + savedUsersToDB.get("usersAlreadyExist"));

        model.addAttribute("SpringBoot", true);
        model.addAttribute("UsersAdded", savedUsersToDB.get("usersToAdd"));
        model.addAttribute("UsersExist", savedUsersToDB.get("usersAlreadyExist"));
        page = "load-users";
        return page;
    }


    /**
     * Mapping of POST '/query' request to query route, that submits form data
     *
     * @param query
     *         The query (question) form attribute
     * @param model
     *         The model of attribute pages for the web document
     *
     * @return The name attribute of the page returned from the request
     * @throws URISyntaxException
     *         Encoding exception
     */
//    @PostMapping("/query")
//    public String querySubmit(@ModelAttribute Query query, Model model) throws URISyntaxException {
//
//        System.out.println("Query: " + query.toString());
//
//
//        RestTemplate restTemplate = new RestTemplate();
//        APIJSON result = restTemplate.getForObject(APIEndpoint, APIJSON.class);
//
//        model.addAttribute("query", query);
//        page = "query";
//        return page;
//    }

}