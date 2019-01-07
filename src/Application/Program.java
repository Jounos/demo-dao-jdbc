package Application;

import java.text.ParseException;
import java.util.Date;
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
		Department department=new Department(2, null);
		List<Seller> sellers=sellerDAO.findByIdDepartment(department);
		sellers.forEach(System.out::println);
		System.out.println();
		System.out.println("**************************Teste 1: Seller findAll**************************");
		sellers=sellerDAO.findAll();
		sellers.forEach(System.out::println);
		System.out.println();
		System.out.println("**************************Teste 1: Seller insert**************************");
		Seller newSeller=new Seller(null, "Greg white", "greg@gmail.com", new Date(), 4000.0, department);
		sellerDAO.inserir(newSeller);
		System.out.println("Inserted! New id= "+ newSeller.getId());
		
		
	}
}
