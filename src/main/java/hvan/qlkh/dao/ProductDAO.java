/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.dao;

import hvan.qlkh.models.Product;
import hvan.qlkh.models.ProductList;
import hvan.qlkh.utils.FileUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author PC
 */
public final class ProductDAO {

    private static final String XMLFILE_PATH = "product.xml";
    private List<Product> products;
    private static ProductDAO instance;

    private ProductDAO() {
        this.readProducts();
    }

    public static synchronized ProductDAO getInstance(){
        if(instance == null){
            instance = new ProductDAO();
        }
        return instance;
    }
    /**
     * Lưu các đối tượng user vào file product.xml
     *
     */
    public void writeProducts() {
        ProductList pl = ProductList.getInstance();
        pl.setProducts(products);
        FileUtils.writeXMLtoFile(XMLFILE_PATH, pl);
    }

    /**
     * Đọc các đối tượng user từ file product.xml
     *
     * @return list student
     */
    public List<Product> readProducts() {
        if (products == null){
            products = new ArrayList<>();
            ProductList pl = (ProductList) FileUtils.readXMLFile(XMLFILE_PATH, ProductList.class);
            if (pl != null){
                if (pl.getProducts() != null) {
                    products = pl.getProducts();
                }
            }
        }
        return products;
    }

    public Product findById(String id){
        return readProducts().stream()
            .filter(temp -> id.equals(temp.getId()))
            .findAny()
            .orElse(null);
    }

    public Product findByName(String name){
        return readProducts().stream()
            .filter(temp -> name.equals(temp.getName()))
            .findAny()
            .orElse(null);
    }
    
    public void create(Product product) {
        products.add(product);
        writeProducts();
    }
    
    public Product update(String id, Product product){
        Product temp = null;
        for (Product p: products){
            if (p.getId().equals(id)){
                p.setId(product.getId());
                p.setName(product.getName());
                p.setCategory(product.getCategory());
                p.setQuantity(product.getQuantity());
                p.setPrice(product.getPrice());
                p.setExpDate(product.getExpDate());
                p.setManafacturer(product.getManafacturer());
                p.setThumbnail(product.getThumbnail());
                p.setDescription(product.getDescription());
                temp = p;
                writeProducts();
                break;
            }
        }
        return temp;
    }
    
    public boolean delete(String id) {
        Product product = findById(id);
        if (product != null) {
            products.remove(product);
            writeProducts();
            return true;
        }
        return false;
    }
}
