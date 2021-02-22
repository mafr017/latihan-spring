package com.example.latihanspring.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Products {

    @NotNull(message = "Tidak boleh Kosong!")
    @Min(1)
    private Long productId;
    @NotEmpty(message = "Tidak boleh Kosong!")
    private String productName;
    @NotNull(message = "Tidak boleh Kosong!")
    @Min(1)
    private Integer supplierId;
    @NotNull(message = "Tidak boleh Kosong!")
    @Min(1)
    private Integer categoryId;
    @NotEmpty(message = "Tidak boleh Kosong!")
    private String quantityperUnit;
    @NotNull(message = "Tidak boleh Kosong!")
    @Min(1)
    private Integer unitPrice;
    @NotNull(message = "Tidak boleh Kosong!")
    @Min(1)
    private Integer unitsInStock;
    @NotNull(message = "Tidak boleh Kosong!")
    @Min(1)
    private Integer unitsOnOrder;
    @NotNull(message = "Tidak boleh Kosong!")
    @Min(1)
    private Integer reorderLevel;
    @NotNull(message = "Tidak boleh Kosong!")
    @Min(1)
    private Integer discontinued;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuantityperUnit() {
        return quantityperUnit;
    }

    public void setQuantityperUnit(String quantityperUnit) {
        this.quantityperUnit = quantityperUnit;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Integer unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public Integer getUnitsOnOrder() {
        return unitsOnOrder;
    }

    public void setUnitsOnOrder(Integer unitsOnOrder) {
        this.unitsOnOrder = unitsOnOrder;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Integer getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Integer discontinued) {
        this.discontinued = discontinued;
    }
}
