package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;

import java.util.List;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService service;

    private final List<AccidentType> types = List.of(new AccidentType(0, "Две машины"),
            new AccidentType(1, "Машина и человек"), new AccidentType(2, "Машина и велосипед"));

    @GetMapping("/addAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", types);
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, @RequestParam("type.id") int typeId) {
        accident.setType(types.get(typeId));
        service.add(accident);
        return "redirect:/";
    }

    @GetMapping("/editAccident")
    public String viewEditAccident(@RequestParam("id") int id, Model model) {
        var optionalAccident = service.findById(id);
        if (optionalAccident.isPresent()) {
            model.addAttribute("accident", optionalAccident.get());
            model.addAttribute("types", types);
            return "editAccident";
        }
        model.addAttribute("message", "Страница не найдена");
        return "404";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, @RequestParam("type.id") int typeId) {
        accident.setType(types.get(typeId));
        service.update(accident);
        return "redirect:/";
    }
}
