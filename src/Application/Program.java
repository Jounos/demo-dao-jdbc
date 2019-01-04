package Application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import model.dao.DaoFactory;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) throws ParseException {
		Locale.setDefault(Locale.US);
		Department obj=new Department(1, "Books");
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		Seller sel=new Seller(1, "Alex Green", "alex@gmail.com", sdf.parse("16/05/1991"), 10000.00, obj);
		System.out.println(sel.toString());
		
		SellerDao sellerDAO=DaoFactory.createSellerDao();
	}
}
