package it.quattrocchi;

import java.util.ArrayList;

public class Cart {
	public Cart() {
		products = new ArrayList<ArticleBean>();
	}
	
	public void addProduct(ArticleBean product) {
		if(product.getNumeroPezziDisponibili() > numeroProdottiStessoTipo(product.getNome(), product.getMarca()))
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
	
	public ArrayList<ArticleBean> getProductsDifferenti() {
		ArrayList<ArticleBean> prod = new ArrayList<ArticleBean>();
		int i, j;
		for(i=0; i<products.size(); i++) {
			boolean find = false;
			for(j=i-1; j>=0; j--) {
				if(products.get(i).getNome().equals(products.get(j).getNome()) && products.get(i).getMarca().equals(products.get(j).getMarca()))
						find=true;
			}
			if(find == false)
				prod.add(products.get(i));
		}
		return prod;
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
