package application;

import java.util.List;
import java.util.Scanner;

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		DepartmentDao dDAO=DaoFactory.createDepartmentDao();
		System.out.println("**************************Teste 1: Department findByID**************************");
		Department department=dDAO.findById(4);
		System.out.println(department);
		System.out.println();
		System.out.println("**************************Teste 2: Department findAll**************************");
		List<Department> departments=dDAO.findAll();
		departments.forEach(System.out::println);
		System.out.println();
		System.out.println("**************************Teste 3: Department insert**************************");
		Department newDepartment=new Department(null, "Music");
		dDAO.insert(newDepartment);
		System.out.println("Inserted! New id= "+ newDepartment.getId());
		System.out.println();
		System.out.println("**************************Teste 4: Department update**************************");
		department=dDAO.findById(6);
		department.setName("Food");
		dDAO.update(department);
		System.out.println("Update completed!");
		System.out.println();
		System.out.println("**************************Teste 5: Department delete**************************");
		System.out.print("Enter id for delete test: ");
		int id=sc.nextInt();
		dDAO.deleteSellerByDepartment(id);
		System.out.println("Delete completed!");
		sc.close();
		
		DB.commitTransaction();
		//DB.rollbackTransaction();
	}
}
