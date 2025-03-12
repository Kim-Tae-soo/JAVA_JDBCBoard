package kr.or.ddit.board.Service;

import java.util.List;

import kr.or.ddit.board.DAO.BoardDAOImpl;
import kr.or.ddit.board.DAO.IBoardDAO;
import kr.or.ddit.board.VO.BoardVO;

public class BoardServiceImpl implements IBoardService {

	private IBoardDAO dao;
	
	private static BoardServiceImpl service; // 1
	
	private BoardServiceImpl() {
		dao = BoardDAOImpl.getInstance();	//2
	}
	
	public static BoardServiceImpl getInstance() {	// 3
		if(service == null)service = new BoardServiceImpl();
		return service;
	}
	
	@Override
	public int InsertBoard(BoardVO boardVo) {
		return dao.InsertBoard(boardVo);
	}

	@Override
	public int deleteBoard(int boardNo) {
		return dao.deleteBoard(boardNo);
	}

	@Override
	public int updateBoard(BoardVO boardVo) {
		return dao.updateBoard(boardVo);
	}

	@Override
	public BoardVO getBoard(int boardNo) {
		// 조회수를 증가하고 그 결과를 검사
		if(setCountIncrment(boardNo)==0) {
			// 조회수 증가 작업이 실패하면 해당 게시글이 없다는 의미이므로 null을 반환한다.
			return null;
		}
		return dao.getBoard(boardNo);
	}

	@Override
	public List<BoardVO> getAllBoard() {
		return dao.getAllBoard();
	}

	@Override
	public List<BoardVO> getSearchBoard(String title) {
		return dao.getSearchBoard(title);
	}

	@Override
	public int setCountIncrment(int boardNo) {
		return dao.setCountIncrment(boardNo);
	}

}
