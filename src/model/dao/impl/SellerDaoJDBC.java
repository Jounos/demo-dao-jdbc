package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public void inserir(Seller s) {
		PreparedStatement stm=null;
		ResultSet rs=null;
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("INSERT INTO ");
			sql.append("seller ");
			sql.append("(desc_name, email, birthdate, basesalary, departmentid) ");
			sql.append("VALUES ");
			sql.append("(?,?,?,?,?)");
			stm=con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, s.getName());
			stm.setString(2, s.getEmail());
			stm.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
			stm.setDouble(4, s.getBaseSalary());
			stm.setInt(5, s.getDepartment().getId());
			int rowsAffected=stm.executeUpdate();
			if(rowsAffected>0) {
				rs=stm.getGeneratedKeys();
				if(rs.next()) {
					int id=rs.getInt(1);
					s.setId(id);
				}
			}else {
				DB.rollbackTransaction();
				throw new DbException("Unexpected error! No rows affected!");
			}
		}catch(SQLException e) {
			DB.rollbackTransaction();
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(stm);
			DB.closeResutSet(rs);
		}
	}

	@Override
	public void update(Seller s) {
		PreparedStatement stm=null;
		ResultSet rs=null;
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("UPDATE ");
			sql.append("seller ");
			sql.append("SET ");
			sql.append("desc_name=?, email=?, birthdate=?, basesalary=?, departmentid=? ");
			sql.append("WHERE ");
			sql.append("id=? ");
			stm=con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, s.getName());
			stm.setString(2, s.getEmail());
			stm.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
			stm.setDouble(4, s.getBaseSalary());
			stm.setInt(5, s.getDepartment().getId());
			stm.setInt(6, s.getId());
			stm.executeUpdate();
		}catch(SQLException e) {
			DB.rollbackTransaction();
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(stm);
			DB.closeResutSet(rs);
		}
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
		}
	}
	
	@Override
	public List<Seller> findAll() {
		PreparedStatement stm=null;
		ResultSet rs=null;
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT ");
			sql.append("s.*, d.desc_name as DepName ");
			sql.append("FROM ");
			sql.append("department d ");
			sql.append("INNER JOIN ");
			sql.append("seller s ");
			sql.append("ON ");
			sql.append("d.id=s.departmentid ");
			sql.append("ORDER BY ");
			sql.append("s.desc_name");
			stm=con.prepareStatement(sql.toString());
			rs=stm.executeQuery();
			List<Seller> sellers=new ArrayList<>();
			Map<Integer, Department> map=new HashMap<>();
			while(rs.next()) {
				Department dep=map.get(rs.getInt("departmentid"));
				if(dep==null) {
					dep=instantiateDepartment(rs);
				}
				sellers.add(instantiateSeller(rs, dep));
			}
			return sellers;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally{
			DB.closeStatement(stm);
			DB.closeResutSet(rs);
		}
	}
	
	public List<Seller> findByIdDepartment(Department department){
		PreparedStatement stm=null;
		ResultSet rs=null;
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT ");
			sql.append("s.*, d.desc_name as DepName ");
			sql.append("FROM ");
			sql.append("department d ");
			sql.append("INNER JOIN ");
			sql.append("seller s ");
			sql.append("ON ");
			sql.append("s.departmentid=d.id ");
			sql.append("WHERE ");
			sql.append("d.id=?");
			sql.append("ORDER BY ");
			sql.append("s.desc_name ");
			sql.append("ASC");
			stm=con.prepareStatement(sql.toString());
			stm.setInt(1, department.getId());
			rs=stm.executeQuery();
			List<Seller> sellers=new ArrayList<>();
			Map<Integer, Department> map=new HashMap();
			while(rs.next()) {
				Department dep=map.get(rs.getInt("departmentid"));
				if(dep==null) {
					dep=instantiateDepartment(rs);
				}
				sellers.add(instantiateSeller(rs, dep));
			}
			return sellers;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(stm);
			DB.closeResutSet(rs);
		}
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
