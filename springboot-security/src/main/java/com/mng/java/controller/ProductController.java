package com.mng.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mng.java.model.Product;
import com.mng.java.service.ProductService;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	//@RequestMapping(value = "/list", method= RequestMethod.GET)
	@GetMapping(value = "/user/list")
	public ResponseEntity<List<Product>> list() {
		List<Product> productList = productService.listAllProducts();
		
		if (productList.isEmpty()){
			System.out.println("no product found");
            return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
        }
		
		System.out.println("productService.listAllProducts() result: " + productList);
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}


	//@RequestMapping(value = "/show/{id}", method= RequestMethod.GET)
	@GetMapping(value = "/admin/show/{id}")
	public ResponseEntity<Product> showProduct(@PathVariable Integer id) {
		Product product = productService.getProductById(id);
		
		if (product == null){
			System.out.println("no product found");
            return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
        }
		
		System.out.println("productService.getProductById() result: " + product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	//@RequestMapping(value = "/add", method = RequestMethod.POST)
	@PostMapping(value = "/admin/add")
	public ResponseEntity<String> saveProduct(@RequestBody Product product) {
		if(productService.exists(product.getId())) {
			System.out.println("a product with name " + product.getId() + " already exists");
            return new ResponseEntity<String>("alreday exixts", HttpStatus.CONFLICT);
		}
		productService.saveProduct(product.getId(),product);
		System.out.println("productService.saveProduct() result: " + productService.listAllProducts());
		return new ResponseEntity<String>("Product saved successfully", HttpStatus.CREATED);
	}

	//@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	@PutMapping(value = "/admin/update/{id}")
	public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        Product productObj= productService.getProductById(id);
		
		if (productObj == null){
			System.out.println("product with id {} not found");
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
		productObj.setProductId(product.getProductId());
		productObj.setDescription(product.getDescription());
		productObj.setImageUrl(product.getImageUrl());
		productObj.setPrice(product.getPrice());
		productService.saveProduct(product.getId(),productObj);
		
		System.out.println("productService.updateProduct() result: " + productService.listAllProducts());
		return new ResponseEntity("Product updated successfully", HttpStatus.OK);
	}

	//@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
	@DeleteMapping(value = "/admin/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
		System.out.println("Before productService.delete() result: " + productService.listAllProducts());
		System.out.println("Products list size: " + productService.listAllProducts().size());
		
		Product productObj= productService.getProductById(id);
		if(productObj == null) {
			System.out.println("Unable to delete with Id " + id + " not found");
            return new ResponseEntity<String>("Unable to delete with Id",HttpStatus.NOT_FOUND);
		}
		productService.deleteProduct(id);
		System.out.println("after productService.delete() result: " + productService.listAllProducts());
		System.out.println("Products list size: " + productService.listAllProducts().size());
		return new ResponseEntity<String>("Product deleted successfully", HttpStatus.OK);

	}
	
	@GetMapping(value="/getAll",produces= {"application/json"} )
	public List<Product> getProductList(){
		return productService.getProductList();
	}
}