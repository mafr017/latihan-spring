package com.example.latihanspring.ui;

import com.example.latihanspring.model.Products;
import com.example.latihanspring.repository.MasterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        model.addAttribute("products", new Products());
        idPrd = 0L;
        return "addEditprd";
    }

    @GetMapping("/updateprd/{id}")
    public String updateProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("products", masterData.fetchProductsJdbcByPrdId(id));
        idPrd = Long.valueOf(id);
        return "addEditprd";
    }

    @GetMapping("/deleteprd/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        System.out.println(id);
        masterData.deleteProductJdbc(id);
        return "redirect:../listprd";
    }

    @PostMapping("/saveproduct")
    public String saveProduct(@Valid Products products, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
//            System.out.println(bindingResult.toString());
            return "addEditprd";
        } else {
            if (idPrd > 0L) {
                masterData.updateProductJdbc(products);
                System.out.println("Update " + products.getProductName());
            } else {
                System.out.println("Input " + products.getProductName());
                masterData.insertProductJdbc(products);
            }
            return "redirect:listprd";
        }
    }

    @PostMapping("/api/saveproductjson")
    public ResponseEntity<Products> saveproductjson(@RequestBody Products products) {
        System.out.println(products.getProductId() + " : " + products.getProductName());
        masterData.insertProductJdbc(products);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/api/updateproductjson")
    public ResponseEntity<Products> updateproductjson(@RequestBody Products products) {
        System.out.println(products.getProductId() + " : " + products.getProductName());
        masterData.updateProductJdbc(products);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/api/dataproductjson/{id}")
    public ResponseEntity<Products> dataproductjson(@PathVariable(required = false, name = "id") int id) {
        return ResponseEntity.ok(masterData.fetchProductsJdbcByPrdId(id));
    }

    @GetMapping("/api/listproductjson")
    public ResponseEntity<List<Products>> daftarmahasiswajson(@RequestParam(required = false, name = "cari") String cari) {
        return ResponseEntity.ok(masterData.fetchProductsJdbc(cari));
    }

    @DeleteMapping("/api/deleteproductjson/{id}")
    public ResponseEntity<List<Products>> deleteproductjson(@PathVariable("id") int id) {
        System.out.println("Proses delete " + id);
        masterData.deleteProductJdbc(id);
        String cari = "";
        return ResponseEntity.ok(masterData.fetchProductsJdbc(cari));
    }

}
