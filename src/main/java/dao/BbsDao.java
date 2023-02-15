package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;
import dto.BbsDto;
import dto.MemberDto;

public class BbsDao {
	// singleton	: 하나만 만드는거 생성자를 private로 만드는게 중요
	
	private static BbsDao dao = null;
	
	private BbsDao() {
		DBConnection.initConnection();
	}
	
	public static BbsDao getInstance() {
		if(dao == null) {
			dao = new BbsDao();
		}
		return dao;
	}
	
	public List<BbsDto> getBbsList() {
		
		String sql = " select seq, id, ref, step, depth,"
				+ "			  title, content, wdate, del, readcount "
				+ "    from bbs "
				+ "    order by ref desc, step asc ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				BbsDto dto = new BbsDto(rs.getInt(1),
										rs.getString(2),
										rs.getInt(3),
										rs.getInt(4),
										rs.getInt(5),
										rs.getString(6),
										rs.getString(7),
										rs.getString(8),
										rs.getInt(9),
										rs.getInt(10));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}
	
	public int getAllBbs(String choice, String search) {
		
		String sql = " select count(*) from bbs ";
		
		String searchSql = "";
		
		if(choice.equals("title")) {
			searchSql = " where title like '%" + search + "%'";
		} else if (choice.equals("content")) {
			searchSql = " where content like '%" + search + "%'";
		} else if (choice.equals("writer")) {
			searchSql = " where id='" + search +"'"; 
		}
		
		sql += searchSql;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return count;
	}
	
	public boolean addBbs(BbsDto dto) {
		
		String sql = " insert into bbs(id, ref, step, depth, title, content, wdate, del, readcount) "
				+ "		values(?, "
				+ "		(select ifnull(max(ref), 0)+1 from bbs b), 0, 0, "
				+ "		?, ?, now(), 0, 0) ";
				
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			
			count = psmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("addBbs fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return count > 0 ? true : false;
	}

	
	public List<BbsDto> getBbsSearchList(String choice, String search) {
		
		String sql = " select seq, id, ref, step, depth,"
				+ "			  title, content, wdate, del, readcount "
				+ "    from bbs ";
		
		String searchSql = "";
		if(choice.equals("title")) {
			searchSql = " where title like '%" + search + "%'";
		} else if (choice.equals("content")) {
			searchSql = " where content like '%" + search + "%'";
		} else if (choice.equals("writer")) {
			searchSql = " where id='" + search +"'"; 
		}
		
		sql += searchSql;
		
		sql +=	"    order by ref desc, step asc ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				BbsDto dto = new BbsDto(rs.getInt(1),
										rs.getString(2),
										rs.getInt(3),
										rs.getInt(4),
										rs.getInt(5),
										rs.getString(6),
										rs.getString(7),
										rs.getString(8),
										rs.getInt(9),
										rs.getInt(10));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}
	
	public List<BbsDto> getBbsPageList(String choice, String search, int pageNumber) {
		
		String sql = " select seq, id, ref, step, depth, title, content, wdate, del, readcount " 
				+ " from (select row_number()over(order by ref desc, step asc) as rnum, "
				+ "		seq, id, ref, step, depth, title, content, wdate, del, readcount "
				+ " 	from bbs ";
		
		String searchSql = "";
		if(choice.equals("title")) {
			searchSql = " where title like '%" + search + "%'";
		} else if (choice.equals("content")) {
			searchSql = " where content like '%" + search + "%'";
		} else if (choice.equals("writer")) {
			searchSql = " where id='" + search +"'"; 
		}
		
		sql += searchSql;
		
		sql += 		" 	order by ref desc, step asc) b " 
				+ 	"	where rnum between ? and ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int start, end;
		start = 1 + 10 * pageNumber;
		end = start + 9;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, start);
			psmt.setInt(2, end);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				BbsDto dto = new BbsDto(rs.getInt(1),
										rs.getString(2),
										rs.getInt(3),
										rs.getInt(4),
										rs.getInt(5),
										rs.getString(6),
										rs.getString(7),
										rs.getString(8),
										rs.getInt(9),
										rs.getInt(10));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}
	
	public BbsDto getBbs(int seq) {
		
		String sql = " select seq, id, ref, step, depth, title, content, wdate, del, readcount "
				+ " 	from bbs "
				+ "	 	where seq=? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		BbsDto dto = null;
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto = new BbsDto(rs.getInt(1),
								rs.getString(2),
								rs.getInt(3),
								rs.getInt(4),
								rs.getInt(5),
								rs.getString(6),
								rs.getString(7),
								rs.getString(8),
								rs.getInt(9),
								rs.getInt(10));
			}
		} catch (SQLException e) {
			System.out.println("getBbs fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return dto;
	}
	
	public boolean answer(int seq, BbsDto dto) {
		
		// update
		String sql1 = " update bbs "
				+ " set step=step+1 "
				+ " where ref=(select ref from (select ref from bbs a where seq=?) A) "
				+ "	  and step>(select step from (select step from bbs b where seq=?) B) ";
				
		// insert
		String sql2 = " insert into bbs(id, ref, step, depth, title, content, wdate, del, readcount) "
				+ " values(?, "
				+ "		(select ref from bbs a where seq=?), "
				+ "		(select step from bbs b where seq=?)+1, "
				+ "		(select depth from bbs c where seq=?)+1, "
				+ "		?, ?, now(), 0, 0) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count1 = 0;
		int count2 = 0;
		
		try {
			conn = DBConnection.getConnection();
			// commit을 비활성화
			conn.setAutoCommit(false);
			
			// update
			psmt = conn.prepareStatement(sql1);
			psmt.setInt(1, seq);
			psmt.setInt(2, seq);
			
			count1 = psmt.executeUpdate();
			
			//psmt 초기화
			psmt.clearParameters();
			
			// insert
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, dto.getId());
			psmt.setInt(2, seq);
			psmt.setInt(3, seq);
			psmt.setInt(4, seq);
			psmt.setString(5, dto.getTitle());
			psmt.setString(6, dto.getContent());
			
			count2 = psmt.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {e1.printStackTrace();}
			System.out.println("answer() fail");
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {e.printStackTrace();}
			DBClose.close(conn, psmt, null);
		}
		
		return count2>0?true:false;
	}
	
	public boolean updateBbs(int seq, String title, String content) {
		
		String sql = " update bbs "
				+ " 	set title = ?, content = ? "
				+ " 	where seq = ? " ;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			
			// update
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			psmt.setString(2, content);
			psmt.setInt(3, seq);
			
			count = psmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("updateBbs() fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return count>0?true:false;
	}
	
	public boolean deleteBbs(int seq) {
		
		String sql = " update bbs "
				+ " 	set del = 1 "
				+ " 	where seq = ? " ;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			
			// update
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			
			count = psmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("deleteBbs() fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return count>0?true:false;
	}
	
	public void readcountBbs(int seq, String id) {
		String sql1 = " update bbs "
				+ "		set readcount = readcount+1 "
				+ "		where seq = ? ";
		
		String sql2 = " insert into readC(seq, id, readcount) "
				+ "		values (?, ?, 1) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		try {
			conn = DBConnection.getConnection();
			// commit을 비활성화
			conn.setAutoCommit(false);
			
			// update
			psmt = conn.prepareStatement(sql1);
			psmt.setInt(1, seq);
			
			psmt.executeUpdate();
			
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql2);
			psmt.setInt(1, seq);
			psmt.setString(2, id);
			
			psmt.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {e1.printStackTrace();}
			System.out.println("answer() fail");
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {e.printStackTrace();}
			DBClose.close(conn, psmt, null);
		}
	}
	
	public boolean readcheck(int seq, String id) {
		String sql = " select readcount "
				+ " 	from readC "
				+ "		where seq = ? and id = ? ";	 
		
		int count = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			
			psmt.setInt(1, seq);
			psmt.setString(2, id);
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return count==0?true:false;
	}
}
