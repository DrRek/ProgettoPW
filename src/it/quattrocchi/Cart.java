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
	
	public int numeroProdottiStessoTipo(String nome, String marca) {
		int i=0, j=0;
		while(i<products.size()) {
			if(nome.equalsIgnoreCase(products.get(i).getNome()) && marca.equalsIgnoreCase(products.get(i).getMarca())) {
				j++;
			}
			i++;
		}
		
		return j;
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
