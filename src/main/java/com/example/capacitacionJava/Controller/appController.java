package com.example.capacitacionJava.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Controller
public class appController {

    private ArrayList<Person> listPerson = new ArrayList();
    @GetMapping("/")
    public ModelAndView root(){
        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/login")
    public ModelAndView validateLogin(Person person){
        for (Person key: listPerson) {
            if(person.getUserName().equals(key.getUserName()) && person.getUserPassword().equals(key.getUserPassword())){
                if(key.getRol().equals("user")){
                    return new ModelAndView("redirect:/home");
                }
                else{
                    return new ModelAndView("redirect:/adminHome");
                }
            }else if (person.getUserName().equals(key.getUserName()) && !person.getUserPassword().equals(key.getUserPassword())){
                return new ModelAndView("redirect:/wrongPassword");
            }
        }
        return new ModelAndView("redirect:/createAccount");
    }

    @GetMapping("/login")
    public String login(Model model){
        boolean isFind = false;
        for (Person listPerson : listPerson) {
            if(listPerson.getName().equals("admin")){
                isFind = true;
            }
        }

        if(!isFind){
            Person admin = new Person("admin@admin.com", "admin","admin", "Masculino",new Date(), "1", "admin");
            listPerson.add(admin);
        }

        model.addAttribute("person", new Person());
        return "login";
    }

    @GetMapping("/createAccount")
    public String createAccount(Model model){
        model.addAttribute("person",new Person());
        return "createAccount";
    }

    @PostMapping("/createAccount")
    public ModelAndView newAccount(Person person){
        person.setRol("user");
        Date now = new Date();
        if(person.getBirthday().after(now)){
            return new ModelAndView("redirect:/incorrectDate");
        }else{
            listPerson.add(person);
        }
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/home")
    public String home(){ return "home";}

    @GetMapping("/incorrectDate")
    public String incorrectDate(){
        return "incorrectDate";
    }

    @GetMapping("/wrongPassword")
    public String wrongPassword(){
        return "wrongPassword";
    }

    @GetMapping("/adminHome")
    public String adminHome(Model model){
        model.addAttribute("listUser", listPerson);
        return "adminHome";
    }

    @GetMapping("/updatePerson")
    public String updatePerson(){
        return "updatePerson";
    }

    @GetMapping("/eliminatePerson")
    public String eliminatePerson(){
        return "eliminatePerson";
    }
}
