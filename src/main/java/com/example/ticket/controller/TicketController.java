package com.example.ticket.controller;

import com.example.ticket.entity.Visitor;
import com.example.ticket.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
public class TicketController {

    @Autowired
    private VisitorRepository repo;

    @GetMapping("/")
    public String home() {
        return "redirect:/register";
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("visitor", new Visitor());
        return "register";
    }

    @PostMapping("/register")
    public String submitForm(@ModelAttribute Visitor visitor, Model model) {
        visitor.setTicketId("TP" + new Random().nextInt(99999));

        // Simple ticket price logic
        switch (visitor.getCategory().toLowerCase()) {
            case "child" -> visitor.setTicketPrice(100);
            case "student" -> visitor.setTicketPrice(150);
            default -> visitor.setTicketPrice(200);
        }

        repo.save(visitor);
        model.addAttribute("ticket", visitor);
        return "ticket";
    }

    @GetMapping("/view")
    public String viewAll(Model model) {
        model.addAttribute("visitors", repo.findAll());
        return "view";
    }
}
