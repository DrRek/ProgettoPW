package it.quattrocchi.support;

import java.util.ArrayList;

public class Cart {
	public Cart() {
		products = new ArrayList<CartArticle>();	}

	public void addProduct(ArticleBean product) {
		boolean found = false;
		for (CartArticle p: products){
			if(product.equals(p.getArticle())){
				p.aggiungi(); 
				found = true;
			}
		}
		if(!found){
			products.add(new CartArticle(product,1));
		}
	}	

	public void updateProduct(ArticleBean product, int n) {
		for (CartArticle p: products){
			if(product.equals(p.getArticle()))
				p.aggiorna(n); 
		}
	}	
	
	public void removeProduct(ArticleBean product) {
		for(int i = 0; i < products.size(); i++) {
			CartArticle p = products.get(i);
			if(product.equals(p.getArticle()))
				products.remove(i);	
		}
	}


	public ArrayList<CartArticle> getProducts() {
		return  products;
	}
	
	public double getPrezzo(){
		float tot = 0;
		for(CartArticle ca : products){
			tot+=ca.getPrezzo();
		}
		return Math.floor(tot * 100) / 100;
	}

	public boolean isEmpty(){
		if(products.isEmpty()) return true;
		return false;
	}

	public int getNumberOfProducts()
	{
		int n = 0;
		for(CartArticle c : products)
		{
			n += c.getQuantity();
		}
		return n;
	}
	
	private ArrayList<CartArticle> products;
}
