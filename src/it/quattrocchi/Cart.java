package it.quattrocchi;

import java.util.ArrayList;

public class Cart {

private ArrayList<ArticleBean> products;
	
	public Cart() {
		products = new ArrayList<ArticleBean>();
	}
	
	public void addProduct(ArticleBean product) {
		products.add(product);
	}
	
	public void deleteProduct(ArticleBean product) {
		for(ArticleBean prod : products) {
			if(prod.getNome() == product.getNome()) {
				products.remove(prod);
				break;
			}
		}
 	}
	
	public ArrayList<ArticleBean> getProducts() {
		return  products;
	}
}
