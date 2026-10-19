package ru.golyakov.MyUiRestDbService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.golyakov.MyUiRestDbService.dao.StudyGroupRepository;
import ru.golyakov.MyUiRestDbService.entity.StudyGroup;

import java.util.Optional;

@RestController
public class StudyGroupController {

    private final StudyGroupRepository studyGroupRepository;

    @Autowired
    public StudyGroupController(StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    }

    @GetMapping("/groups")
    public ModelAndView getAllGroups() {
        ModelAndView mav = new ModelAndView("list-groups");
        mav.addObject("groups", studyGroupRepository.findAll());
        return mav;
    }

    @GetMapping("/addGroupForm")
    public ModelAndView addGroupForm() {
        ModelAndView mav = new ModelAndView("add-group-form");
        mav.addObject("group", new StudyGroup());
        return mav;
    }

    @PostMapping("/saveGroup")
    public RedirectView saveGroup(@ModelAttribute StudyGroup group) {
        studyGroupRepository.save(group);
        return new RedirectView("/groups");
    }

    @GetMapping("/showGroupUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Integer groupId) {
        ModelAndView mav = new ModelAndView("add-group-form");
        Optional<StudyGroup> optionalGroup = studyGroupRepository.findById(groupId);
        StudyGroup group = new StudyGroup();
        if (optionalGroup.isPresent()) {
            group = optionalGroup.get();
        }
        mav.addObject("group", group);
        return mav;
    }

    @GetMapping("/deleteGroup")
    public RedirectView deleteGroup(@RequestParam Integer groupId) {
        studyGroupRepository.deleteById(groupId);
        return new RedirectView("/groups");
    }
}