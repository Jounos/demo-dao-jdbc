package application;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) throws ParseException {
		Scanner sc=new Scanner(System.in);
		SellerDao sellerDAO=DaoFactory.createSellerDao();
		System.out.println("**************************Teste 1: Seller findByID**************************");
		Seller seller=sellerDAO.findById(3);
		System.out.println(seller);
		System.out.println();
		System.out.println("**************************Teste 2: Seller findByDepartment**************************");
		Department department = new Department(2, null);
		List<Seller> sellers = sellerDAO.findByIdDepartment(department);
		sellers.forEach(System.out::println);
		System.out.println();
		System.out.println("**************************Teste 3: Seller findAll**************************");
		sellers=sellerDAO.findAll();
		sellers.forEach(System.out::println);
		System.out.println();
		System.out.println("**************************Teste 4: Seller insert**************************");
		Seller newSeller=new Seller(null, "Greg white", "greg@gmail.com", new Date(), 4000.0, department);
		sellerDAO.inserir(newSeller);
		System.out.println("Inserted! New id= "+ newSeller.getId());
		System.out.println();
		System.out.println("**************************Teste 5: Seller update**************************");
		seller=sellerDAO.findById(1);
		seller.setName("Martha Waine");
		sellerDAO.update(seller);
		System.out.println("Update completed!");
		System.out.println();
		System.out.println("**************************Teste 6: Seller delete**************************");
		System.out.print("Enter id for delete test: ");
		int id=sc.nextInt();
		sc.nextLine();
		sellerDAO.deleteById(id);
		System.out.println("Delete completed!");
		sc.close();

		DB.commitTransaction();
		//DB.rollbackTransaction();
	}
}
