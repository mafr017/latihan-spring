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

    @GetMapping("/listprd")
    public String listProduct(ModelMap modelMapParam) {
        modelMapParam.put("cekPrd", masterData.fetchProductsJdbc());
        return "listprd";
    }

    @GetMapping("/addprd")
    public String addProduct(Model model) {
        model.addAttribute("Product", new Products());
        return "addEditprd";
    }

    @GetMapping("/updateprd/{id}")
    public String updateProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("Product", masterData.fetchProductsJdbcByPrdId(id));
        return "addEditprd";
    }

    @GetMapping("/deleteprd/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        masterData.deleteProductJdbc(id);
        return "listprd";
    }

    @PostMapping("/saveproduct")
    public String saveProduct(Products products, @RequestParam(name = "cari") String cari) {
        System.out.println(products.getProductId());
        if (products.getProductId() > 0) {
            masterData.insertProductJdbc(products);
        } else {
            masterData.updateProductJdbc(products);
        }
        return "redirect:listprd";
    }

}
