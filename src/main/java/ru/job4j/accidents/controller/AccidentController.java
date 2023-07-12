package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService service;

    private final AccidentTypeService typeService;

    private final RuleService ruleService;

    @GetMapping("/addAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", typeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, @RequestParam("type.id") int typeId,
                       @RequestParam("rIds") int[] rIds, Model model) {
        var type = typeService.findById(typeId);
        if (type.isEmpty()) {
            model.addAttribute("message", "Ошибки на стороне сервера");
            return "404";
        }
        accident.setType(type.get());
        service.add(accident, rIds);
        return "redirect:/";
    }

    @GetMapping("/editAccident")
    public String viewEditAccident(@RequestParam("id") int id, Model model) {
        var optionalAccident = service.findById(id);
        if (optionalAccident.isPresent()) {
            model.addAttribute("accident", optionalAccident.get());
            model.addAttribute("types", typeService.findAll());
            model.addAttribute("rules", ruleService.findAll());
            return "editAccident";
        }
        model.addAttribute("message", "Страница не найдена");
        return "404";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, @RequestParam("type.id") int typeId,
                         @RequestParam("rIds") int[] rIds, Model model) {
        var type = typeService.findById(typeId);
        if (type.isEmpty()) {
            model.addAttribute("message", "Ошибки на стороне сервера");
            return "404";
        }
        accident.setType(type.get());
        service.update(accident, rIds);
        return "redirect:/";
    }
}
