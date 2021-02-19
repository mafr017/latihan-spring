package com.example.latihanspring.ui;

import com.example.latihanspring.model.Products;
import com.example.latihanspring.repository.MasterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeAction {

    @Autowired
    private MasterData masterData;

    private Long idPrd;

    @GetMapping("/listprd")
    public String listProduct(@RequestParam(required = false, name = "cari") String cari, ModelMap modelMapParam) {
        modelMapParam.put("cekPrd", masterData.fetchProductsJdbc(cari));
        return "listprd";
    }

    @GetMapping("/addprd")
    public String addProduct(Model model) {
        model.addAttribute("Product", new Products());
        idPrd = 0L;
        return "addEditprd";
    }

    @GetMapping("/updateprd/{id}")
    public String updateProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("Product", masterData.fetchProductsJdbcByPrdId(id));
        idPrd = Long.valueOf(id);
        return "addEditprd";
    }

    @GetMapping("/deleteprd/{id}")
    public String deleteProduct(@PathVariable("id") int id, ModelMap modelMapParam) {
        System.out.println(id);
        masterData.deleteProductJdbc(id);
        return "redirect:../listprd";
    }

    @PostMapping("/saveproduct")
    public String saveProduct(Products products) {
        if (idPrd > 0L) {
            masterData.updateProductJdbc(products);
        } else {
            masterData.insertProductJdbc(products);
        }
        return "redirect:listprd";
    }

}
