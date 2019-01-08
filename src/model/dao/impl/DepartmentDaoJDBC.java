package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
	public Connection con;
	
	public DepartmentDaoJDBC(Connection con) {
		this.con=con;
	}
	
	
	
	public void insert(Department d) {
		PreparedStatement stm=null;
		ResultSet rs=null;
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("INSERT INTO ");
			sql.append("department ");
			sql.append("(desc_name)");
			sql.append("VALUES");
			sql.append("(?)");
			stm=con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, d.getName());
			int row=stm.executeUpdate();
			if(row>0) {
				rs=stm.getGeneratedKeys();
				if(rs.next()) {
					int id=rs.getInt(1);
					d.setId(id);
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
	
	public void update(Department d) {
		PreparedStatement stm;
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("UPDATE ");
			sql.append("department ");
			sql.append("SET ");
			sql.append("desc_name=? ");
			sql.append("WHERE ");
			sql.append("id=?");
			stm=con.prepareStatement(sql.toString());
			stm.setString(1, d.getName());
			stm.setInt(2, d.getId());
			stm.executeUpdate();
		}catch(SQLException e) {
			DB.rollbackTransaction();
			throw new DbException(e.getMessage());
		}
	}
	
	public void deleteById(Integer id) {
		PreparedStatement stm=null;
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("DELETE FROM department WHERE id=?");
			stm=con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stm.setInt(1, id);
			int rows=stm.executeUpdate();
			if(rows==0) {
				DB.rollbackTransaction();
				throw new DbException("None row Affected!");
			}
		}catch(SQLException e) {
			DB.rollbackTransaction();
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(stm);
		}
	}
	
	public void deleteSellerByDepartment(Integer id) {
		PreparedStatement stm=null;
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("DELETE FROM ");
			sql.append("seller s ");
			sql.append("USING ");
			sql.append("department d ");
			sql.append("WHERE ");
			sql.append("s.departmentid=d.id ");
			sql.append("AND ");
			sql.append("d.id=?");
			stm=con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stm.setInt(1, id);
			int rows=stm.executeUpdate();
			if(rows==0) {
				DB.rollbackTransaction();
				throw new DbException("None row Affected!");
			}
		}catch(SQLException e) {
			DB.rollbackTransaction();
			throw new DbException(e.getMessage());
		}
	}
	
	public Department findById(Integer id) {
		PreparedStatement stm=null;
		ResultSet rs=null;
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT ");
			sql.append("d.id, d.desc_name ");
			sql.append("FROM ");
			sql.append("department d ");
			sql.append("WHERE ");
			sql.append("d.id=?");
			stm=con.prepareStatement(sql.toString());
			stm.setInt(1, id);
			rs=stm.executeQuery();
			Department d=null;
			if(rs.next()) {
				d=instantiateDepartment(rs);
			}
			return d;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(stm);
			DB.closeResutSet(rs);
		}
	}
	
	public List<Department> findAll(){
		PreparedStatement stm=null;
		ResultSet rs=null;
		try{
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT ");
			sql.append("d.id, d.desc_name ");
			sql.append("FROM ");
			sql.append("department d ");
			sql.append("ORDER BY d.id");
			stm=con.prepareStatement(sql.toString());
			rs=stm.executeQuery();
			List<Department> departments=new ArrayList<>();
			while(rs.next()) {
				departments.add(instantiateDepartment(rs));
			}
			return departments;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException{
		Department d=new Department();
		d.setId(rs.getInt("id"));
		d.setName(rs.getString("desc_name"));
		return d;
	}
}
