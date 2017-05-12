package it.quattrocchi;

import java.util.ArrayList;

public class Cart {
	public Cart() {
		products = new ArrayList<ArticleBean>();
	}
	
	public void addProduct(ArticleBean product) {
		products.add(product);
	}
	
	public void deleteProduct(ArticleBean product) {
		for(ArticleBean prod : products) {
			if(prod.getNome().equalsIgnoreCase(product.getNome())&&prod.getMarca().equalsIgnoreCase(product.getMarca())) {
				products.remove(prod);
				break;
			}
		}
 	}
	
	public ArrayList<ArticleBean> getProducts() {
		return  products;
	}
	
	public boolean isEmpty(){
		if(products.isEmpty()) return true;
		return false;
	}

	private ArrayList<ArticleBean> products;
}
