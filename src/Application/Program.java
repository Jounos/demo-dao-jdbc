package Application;

import java.text.ParseException;

import model.dao.DaoFactory;
import model.dao.SellerDao;

public class Program {
	public static void main(String[] args) throws ParseException {
		SellerDao sellerDAO=DaoFactory.createSellerDao();
		System.out.println("**************************Teste 1: Seller findByID**************************");
		System.out.println(sellerDAO.findById(3));
	}
}
