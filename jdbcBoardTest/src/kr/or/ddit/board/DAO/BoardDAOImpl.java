package kr.or.ddit.board.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.board.Util.DBUtil3;
import kr.or.ddit.board.VO.BoardVO;

public class BoardDAOImpl implements IBoardDAO {
	
	private static BoardDAOImpl dao; // 1번
	
	private BoardDAOImpl() {	} // 2번
	
	public static BoardDAOImpl getInstance() { // 3번
			if(dao==null) dao = new BoardDAOImpl();
			return dao;
	}

	@Override
	public int InsertBoard(BoardVO boardVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		
		try {
			conn = DBUtil3.getConnection();
			String sql = "INSERT INTO JDBC_BOARD(BOARD_NO, BOARD_TITLE, BOARD_WRITER,"
					+ "BOARD_CNT, BOARD_CONTENT, BOARD_DATE)"
					+ "VALUES(BOARD_SEQ.NEXTVAL, ?, ?, 0, ?, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardVo.getBoard_title());
			pstmt.setString(2, boardVo.getBoard_writer());
			pstmt.setString(3, boardVo.getBoard_content());
			
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) { }
			if(conn!=null) try {conn.close();} catch(SQLException e) { }
		}
		
		return cnt;
	}

	@Override
	public int deleteBoard(int boardNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		
		try {
			conn = DBUtil3.getConnection();
			String sql = "DELETE FROM JDBC_BOARD WHERE BOARD_NO =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) { }
			if(conn!=null) try {conn.close();} catch(SQLException e) { }
		}
		
		
		return cnt;
	}

	@Override
	public int updateBoard(BoardVO boardVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
			
		try {
			conn = DBUtil3.getConnection();
			String sql = "UPDATE JDBC_BOARD SET BOARD_TITLE = ?,"
					+ "BOARD_CONTENT = ? WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardVo.getBoard_title());
			pstmt.setString(2, boardVo.getBoard_content());
			pstmt.setInt(3, boardVo.getBoard_no());
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) { }
			if(conn!=null) try {conn.close();} catch(SQLException e) { }
		}
		
		
		return cnt;
	}

	@Override
	public BoardVO getBoard(int boardNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO bVo = null;	// 반환값 변수
		
		try {
			conn = DBUtil3.getConnection();
			String sql = "SELECT * FROM JDBC_BOARD WHERE BOARD_NO = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 검색 결과를 저장할 BoardVO객체 생성 후 검색된 자료 저장하기
				
				bVo = new BoardVO();
				
				bVo.setBoard_no(rs.getInt("board_no"));
				bVo.setBoard_title(rs.getString("board_title"));
				bVo.setBoard_writer(rs.getString("board_writer"));
				bVo.setBoard_cnt(rs.getInt("board_cnt"));
				bVo.setBoard_content(rs.getString("board_content"));
				bVo.setBoard_date(rs.getString("board_date"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try {rs.close();} catch(SQLException e) { }
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) { }
			if(conn!=null) try {conn.close();} catch(SQLException e) { }
		}
		
		return bVo;
	}

	@Override
	public List<BoardVO> getAllBoard() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> boardList = null;	// 반환값 변수
		
		try {
			conn = DBUtil3.getConnection();
			String sql = "SELECT * FROM JDBC_BOARD ORDER BY BOARD_NO ASC";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(boardList == null) boardList = new ArrayList<BoardVO>();
				// 검색 결과를 저장할 BoardVO객체 생성 후 검색된 자료 저장하기
				
				BoardVO bVo = new BoardVO();
				
				bVo.setBoard_no(rs.getInt("board_no"));
				bVo.setBoard_title(rs.getString("board_title"));
				bVo.setBoard_writer(rs.getString("board_writer"));
				bVo.setBoard_cnt(rs.getInt("board_cnt"));
				bVo.setBoard_content(rs.getString("board_content"));
				bVo.setBoard_date(rs.getString("board_date"));
				
				boardList.add(bVo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try {rs.close();} catch(SQLException e) { }
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) { }
			if(conn!=null) try {conn.close();} catch(SQLException e) { }
		}
		
		
		return boardList;
	}

	@Override
	public List<BoardVO> getSearchBoard(String title) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> boardList = null;	// 반환값 변수
		
		try {
			conn = DBUtil3.getConnection();
			String sql = "SELECT * FROM JDBC_BOARD "
					+ "WHERE BOARD_TITLE LIKE '%' || ? || '%'"
					+ "ORDER BY BOARD_NO DESC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(boardList == null) boardList = new ArrayList<BoardVO>();
				// 검색 결과를 저장할 BoardVO객체 생성 후 검색된 자료 저장하기
				
				BoardVO bVo = new BoardVO();
				
				bVo.setBoard_no(rs.getInt("board_no"));
				bVo.setBoard_title(rs.getString("board_title"));
				bVo.setBoard_writer(rs.getString("board_writer"));
				bVo.setBoard_cnt(rs.getInt("board_cnt"));
				bVo.setBoard_content(rs.getString("board_content"));
				bVo.setBoard_date(rs.getString("board_date"));
				
				boardList.add(bVo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try {rs.close();} catch(SQLException e) { }
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) { }
			if(conn!=null) try {conn.close();} catch(SQLException e) { }
		}
		
		
		return boardList;
	}

	@Override
	public int setCountIncrment(int boardNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
			
		try {
			conn = DBUtil3.getConnection();
			String sql = "UPDATE JDBC_BOARD SET BOARD_CNT = BOARD_CNT + 1"
					+ "WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) { }
			if(conn!=null) try {conn.close();} catch(SQLException e) { }
		}
		
		
		return cnt;
	}

}
