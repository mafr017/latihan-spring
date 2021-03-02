package com.example.latihanspring.repository;

import com.example.latihanspring.model.Products;
import com.example.latihanspring.model.Users;
import ognl.ObjectElementsAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class MasterData {

    @Autowired
    private Sql2o sql2oo;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Products> fetchProductsJdbc(String cari) {
        if (ObjectUtils.isEmpty(cari)) {
            cari = "";
        }
        return jdbcTemplate.query("SELECT productId, productName, supplierId, categoryId, quantityperUnit, unitPrice, unitsInStock," +
                        " unitsOnOrder, reorderLevel, discontinued FROM products WHERE productName LIKE CONCAT ('%',?,'%')",
                (resultSet, rowNumber) -> {
                    Products products = new Products();
                    products.setProductId(resultSet.getLong("productId"));
                    products.setProductName(resultSet.getString("productName"));
                    products.setSupplierId(resultSet.getInt("supplierId"));
                    products.setCategoryId(resultSet.getInt("categoryId"));
                    products.setQuantityperUnit(resultSet.getString("quantityperUnit"));
                    products.setUnitPrice(resultSet.getInt("unitPrice"));
                    products.setUnitsInStock(resultSet.getInt("unitsInStock"));
                    products.setUnitsOnOrder(resultSet.getInt("unitsOnOrder"));
                    products.setReorderLevel(resultSet.getInt("reorderLevel"));
                    products.setDiscontinued(resultSet.getInt("discontinued"));
                    return products;
                }, cari);
    }

    public Products fetchProductsJdbcByPrdId(int id) {
        try (Connection con = sql2oo.open()) {
            if (ObjectUtils.isEmpty(id)) id = 0;
            final String query =
                    "SELECT productId, productName, supplierId, categoryId, quantityperUnit, unitPrice, unitsInStock," +
                            " unitsOnOrder, reorderLevel, discontinued FROM products WHERE productId = :proId ";

            return con.createQuery(query)
                    .addParameter("proId", id)
                    .executeAndFetchFirst(Products.class);
        }
    }

    public void insertProductJdbc(Products products) {
        final String query = "INSERT INTO products" +
                " (productId, productName, supplierId, categoryId, quantityperUnit, unitPrice, unitsInStock, unitsOnOrder, reorderLevel, discontinued)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query,
                products.getProductId(),
                products.getProductName(),
                products.getSupplierId(),
                products.getCategoryId(),
                products.getQuantityperUnit(),
                products.getUnitPrice(),
                products.getUnitsInStock(),
                products.getUnitsOnOrder(),
                products.getReorderLevel(),
                products.getDiscontinued());
        System.out.println("Input berhasil!");
    }

    public void updateProductJdbc(Products products) {
        final String query = "UPDATE products SET" +
                " productName = ? , supplierId = ?, categoryId = ?, quantityperUnit = ?, unitPrice = ?," +
                " unitsInStock = ?, unitsOnOrder = ?, reorderLevel = ?, discontinued = ? " +
                " WHERE productId = ?";
        jdbcTemplate.update(query,
                products.getProductName(),
                products.getSupplierId(),
                products.getCategoryId(),
                products.getQuantityperUnit(),
                products.getUnitPrice(),
                products.getUnitsInStock(),
                products.getUnitsOnOrder(),
                products.getReorderLevel(),
                products.getDiscontinued(),
                products.getProductId());
        System.out.println("Update berhasil!");
    }

    public void deleteProductJdbc(int id) {
        final String query = "DELETE FROM products WHERE productId = ? ";
        jdbcTemplate.update(query, id);
        System.out.println("Delete berhasil!");
    }

    public Users checkUsers(Users users) {
        final String query = "SELECT id, username, password, islogin FROM users WHERE username = ? AND password = ?";
        return jdbcTemplate.queryForObject(query,
                (resultSet, rowNumber) -> {
                    Users users1 = new Users();
                    users1.setId(resultSet.getInt("id"));
                    users1.setUsername(resultSet.getString("username"));
                    users1.setPassword(resultSet.getString("password"));
                    users1.setIsLogin(resultSet.getString("islogin"));
                    return users1;
                }, users.getUsername(), users.getPassword());
    }

    public void updateIsLogin(Users users, String statusLogin) {
        final String query;
        if (statusLogin.equals("true")){
            query = "UPDATE users SET islogin = ?, jam_login = now() WHERE username = ? AND password = ?";
        } else {
            query = "UPDATE users SET islogin = ?, jam_login = null WHERE username = ? AND password = ?";
        }
        jdbcTemplate.update(query, statusLogin, users.getUsername(), users.getPassword());
        System.out.println("Update isLogin = " + statusLogin);
    }

}
