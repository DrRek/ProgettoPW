package it.quattrocchi;

import java.util.ArrayList;

public class Cart {
	public Cart() {
		products = new ArrayList<CartArticle>();	}

	public void addProduct(ArticleBean product) {
		boolean found = false;
		for (CartArticle p: products){
			if(product.equals(p.getArticle())){
				p.aggiungi(); found = true;
			}
		}
		if(!found)
			products.add(new CartArticle(product,1));
	}	

	public void deleteProduct(ArticleBean product) {
		for(int i = 0; i < products.size(); i++) {
			CartArticle p = products.get(i);
			if(product.equals(p.getArticle())){
				p.rimuovi();
				if(p.getQuantity() == 0)
					products.remove(i);
			}		
		}
	}


	public ArrayList<CartArticle> getProducts() {
		return  products;
	}

	public boolean isEmpty(){
		if(products.isEmpty()) return true;
		return false;
	}

	private ArrayList<CartArticle> products;
}
