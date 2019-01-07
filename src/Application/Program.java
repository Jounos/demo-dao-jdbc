package Application;

import java.text.ParseException;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) throws ParseException {
		SellerDao sellerDAO=DaoFactory.createSellerDao();
		System.out.println("**************************Teste 1: Seller findByID**************************");
		System.out.println(sellerDAO.findById(3));
		System.out.println();
		System.out.println("**************************Teste 1: Seller findByDepartment**************************");
		List<Seller> departments=sellerDAO.findByIdDepartment(new Department(2, null));
		departments.forEach(System.out::println);
	}
}
