package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBClose;
import db.DBConnection;
import dto.MemberDto;

public class MemberDao {
	// singleton
	
	private static MemberDao dao = null;
	
	private MemberDao() {
		DBConnection.initConnection();
	}

	public static MemberDao getInstance() {
		if(dao == null) {
			dao = new MemberDao();
		}
		return dao;
	}
	
	public boolean getId(String id) {
		String sql = " select id "
				+ " 	from member "
				+ " 	where id = ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		boolean findid = false;		// return 값
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {		// 있으면
				findid = true;
				System.out.println("getId success");
			}
			
		} catch (SQLException e) {
			System.out.println("getId fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return findid;
	}
	
	public boolean addMember(MemberDto dto) {
		
		String sql = " insert into member(id, pwd, name, email, auth) "
				+ " 	values(?, ?, ?, ?, 3) ";
				
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPwd());
			psmt.setString(3, dto.getName());
			psmt.setString(4, dto.getEmail());
			
			count = psmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("addMember fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return count > 0 ? true : false;
	}
	
	public MemberDto login(String id, String pwd) {
		
		String sql = " select id, name, email, auth "
				+ "    from member "
				+ "    where id=? and pwd=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		MemberDto mem = null;
				
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pwd);
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				String _id = rs.getString(1);
				String _name = rs.getString(2);
				String _email = rs.getString(3);
				int _auth = rs.getInt(4);
				
				mem = new MemberDto(_id, null, _name, _email, _auth);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return mem;
	}
	
}
