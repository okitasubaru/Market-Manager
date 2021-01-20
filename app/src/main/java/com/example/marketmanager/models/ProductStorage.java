package com.example.marketmanager.models;

public class ProductStorage extends Product{
    private Product product;
    private int StorageQuantity;

    public ProductStorage(Product product, int storageQuantity) {
        this.product = product;
        StorageQuantity = storageQuantity;
    }

    public ProductStorage() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getStorageQuantity() {
        return StorageQuantity;
    }

    public void setStorageQuantity(int storageQuantity) {
        StorageQuantity = storageQuantity;
    }
}
