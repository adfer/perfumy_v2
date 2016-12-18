package com.adfer.controller;

import com.adfer.entity.Perfume;
import com.adfer.enums.PerfumeCategory;
import com.adfer.exception.InvalidRequestParameterException;
import com.adfer.service.PerfumeService;
import com.google.common.base.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by adrianferenc on 18.12.2016.
 */
@Controller
@RequestMapping("perfume")
public class PerfumeController {

    private PerfumeService perfumeService;

    @Autowired
    public PerfumeController(PerfumeService perfumeService) {
        this.perfumeService = perfumeService;
    }

    @RequestMapping({"{perfumeCategory}"})
    public String getPerfumesByCategory(@PathVariable String perfumeCategory, Model model) {
        PerfumeCategory perfumeCategoryEnum = Enums.getIfPresent(PerfumeCategory.class, perfumeCategory.toUpperCase()).or(() -> {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Category ");
            errorMessage.append(perfumeCategory);
            errorMessage.append(" is invalid");
            throw new InvalidRequestParameterException(errorMessage.toString());
        });
        List<Perfume> perfumes = perfumeService.getPerfumesByCategory(perfumeCategoryEnum);
        model.addAttribute("perfumes", perfumes);
        return "perfume/list";
    }
}
