package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	private Connection con;
	
	public SellerDaoJDBC(Connection con) {
		this.con=con;
	}
	
	@Override
	public void inserir(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement stm=null;
		ResultSet rs=null;
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT ");
			sql.append("s.*, d.desc_name as depName ");
			sql.append("FROM ");
			sql.append("seller s ");
			sql.append("INNER JOIN ");
			sql.append("department d ON d.id=s.departmentid ");
			sql.append("WHERE ");
			sql.append("s.id = ?");
			stm=con.prepareStatement(sql.toString());
			stm.setInt(1, id);
			rs=stm.executeQuery();
			if(rs.next()) {
				Department dep=instantiateDepartment(rs);
				Seller sel=instantiateSeller(rs, dep);
				return sel;
			}
			return null;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(stm);
			DB.closeResutSet(rs);
			DB.closeConnection();
		}
	}
	
	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null; 	
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException{
		Department dep=new Department();
		dep.setId(rs.getInt("departmentid"));
		dep.setName(rs.getString("depName"));
		return dep;
	}
	
	private Seller instantiateSeller(ResultSet rs, Department dep)throws SQLException{
		Seller sel=new Seller();
		sel.setId(rs.getInt("id"));
		sel.setName(rs.getString("desc_name"));
		sel.setEmail(rs.getString("email"));
		sel.setBirthDate(rs.getDate("birthdate"));
		sel.setBaseSalary(rs.getDouble("basesalary"));
		sel.setDepartment(dep);
		return sel;
	}
}
