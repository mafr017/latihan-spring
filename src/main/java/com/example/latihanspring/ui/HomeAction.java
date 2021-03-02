package com.example.latihanspring.ui;

import com.example.latihanspring.model.Products;
import com.example.latihanspring.model.Users;
import com.example.latihanspring.repository.MasterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @PostMapping("api/cekloginjson1")
    public ResponseEntity<Map> loginjson1(@Valid @RequestBody Users users, BindingResult bindingResult) {
        //Buat Tablenya id, username, password, isLogin(true|false), jam_login(isi1jam|kosong)
        Map<String, Object> status = new HashMap<>();
        if (bindingResult.hasErrors()) {
            status.put("status", false);
            return ResponseEntity.ok(status);
        } else {
            masterData.updateIsLogin(users, "true");
            Users newUser = masterData.checkUsers(users);
            System.out.println("id " + newUser.getId() + " username " + newUser.getUsername() + " password " + newUser.getPassword() + " islogin " + newUser.getIsLogin());
            if (newUser.getIsLogin().equals("true")) {
                status.put("status", true);
                status.put("user", newUser);
                System.out.println("BERHASIL");
            } else {
                status.put("status", false);
                status.put("user", "fail");
                System.out.println("GAGAL");
            }
            return ResponseEntity.ok(status);
        }
    }

//    @GetMapping("api/cekisloginjson")
//    public ResponseEntity<Map> cekIsLogin(@Valid @RequestBody Users users) {
//        Map<String, Object> status = new HashMap<>();
//        status.put("islogin", masterData.checkUsers(users).getIsLogin());
//        return ResponseEntity.ok(status);
//    }

    @PostMapping("api/logoutjson")
    public ResponseEntity<String> logoutjson(@Valid @RequestBody Users users) {
        masterData.updateIsLogin(users, "false");
        return ResponseEntity.ok("Logout");
    }

//    @PostMapping("api/cekloginjson1")
//    public ResponseEntity<Map> cekLoginjson1(@RequestBody Users users, ModelMap modelMap) {
//        //Buat Tablenya id, username, password, isLogin(true|false), jam_login(isi1jam|kosong)
//        Map<String, Object> status = new HashMap<>();
//        if (users != null
//                && Objects.equals(users.getUsername(), "ABC")
//                && Objects.equals(users.getPassword(), "123")) {
//            status.put("status", true);
//        } else {
//            status.put("status", false);
//        }
//        return ResponseEntity.ok(status);
//    }

//    @PostMapping("api/cekloginjson")
//    public ResponseEntity<Users> cekLoginjson(@RequestBody Users users, ModelMap modelMap) {
//        modelMap.addAttribute("ABC", "123");
//        modelMap.addAttribute("CBA", "321");
//        if (modelMap.containsAttribute(users.getUsername()) && modelMap.containsValue(users.getPassword())) {
//            users.setHasil(1);
//        } else {
//            users.setHasil(0);
//        }
//        System.out.println(users.getUsername() + " : " + users.getPassword() + " : " + users.getHasil());
//        return ResponseEntity.ok(users);
//    }

}
