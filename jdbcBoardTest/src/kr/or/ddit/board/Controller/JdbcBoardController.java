package kr.or.ddit.board.Controller;

import java.util.List;
import java.util.Scanner;

import kr.or.ddit.board.Service.BoardServiceImpl;
import kr.or.ddit.board.Service.IBoardService;
import kr.or.ddit.board.VO.BoardVO;

public class JdbcBoardController {

	
	private Scanner scan;
	private IBoardService service;
	
	public JdbcBoardController() {
		scan = new Scanner(System.in);
		service = BoardServiceImpl.getInstance();
	}
	
	
	public static void main(String[] args) {
		new JdbcBoardController().startBoard();
	}
	
	//시작 메서드
	public void startBoard() {
		while(true) {
			int choice = displayMenu();
			switch(choice) {
				case 1 :
					InsertBoard();
					break;
				case 2 :
					ViewBoard();
					break;
				case 3 :
					UpdateBoard();
					break;
				case 4 :
					DeleteBoard();
					break;
				case 5 :
					SearchBoard();
					break;
				case 6 :
					DisplayAllBoard();
					break;
				case 0 :
					System.out.println();
					System.out.println("게시판 프로그램을 종료합니다.");
					return;
				default : 
					System.out.println("메뉴 번호를 잘못 입력했습니다. \n 다시 입력해주세요.");
			}
		}
	}
	
	// 게시글 조회하기
	private void SearchBoard() {
		scan.nextLine(); // 버퍼 비우기
		System.out.println("검색 작업");
		System.out.println("-------------------------------");
		System.out.print("검색할 제목 입력 >> ");
		String title = scan.nextLine();
		
		List<BoardVO> boardList = service.getSearchBoard(title);
		
		displayBoardList(boardList);
	}
	
	// 게시글 삭제하기
	private void DeleteBoard() {
		System.out.println();
		System.out.println("삭제 작업 하기");
		System.out.println("-------------------------------");
		System.out.print("삭제할 게시글 번호 >> ");
		int no = scan.nextInt();
		
		int cnt = service.deleteBoard(no);
		
		if(cnt>0) {
			System.out.println(no + "번 게시글이 삭제되었습니다.");
		} else {
			System.out.println(no + "번 게시글이 없거나 삭제에 실패하였습니다.");
		}
		
		
	}
	
	// 게시글 수정하기
	private void UpdateBoard() {
		System.out.println();
		System.out.println("수정 작업 하기");
		System.out.println("-------------------------------");
		System.out.print("수정할 게시글 번호 >> ");
		int no = scan.nextInt();
		
		scan.nextLine();
		System.out.print("새로운 제목 : ");
		String title = scan.nextLine();
		
		System.out.print("새로운 내용 : ");
		String content = scan.nextLine();
		
		// 입력한 값 VO에 저장
		BoardVO boardVo = new BoardVO();
		boardVo.setBoard_no(no);
		boardVo.setBoard_title(title);
		boardVo.setBoard_content(content);
		
		int cnt = service.updateBoard(boardVo);
		if(cnt>0) {
			System.out.println(no + "번 게시글이 수정되었습니다.");
		} else {
			System.out.println(no + "번 게시글이 없거나 수정에 실패하였습니다.");
		}
		
		
	}
	
	// 게시글 보기
	private void ViewBoard() {
		System.out.println();
		System.out.print("보기를 원하는 게시글 번호를 입력하세요 >> ");
		int no = scan.nextInt();
		
		BoardVO boardVo = service.getBoard(no);
		if(boardVo == null) {
			System.out.println(no + "번 게시글은 조재하지 않습니다.");
			return;
		}
		
		System.out.println();
		System.out.println(no + "번 게시글의 내용");
		System.out.println("-------------------------------");
		System.out.println("제 목 : " + boardVo.getBoard_title());
		System.out.println("작성자 : " + boardVo.getBoard_writer());
		System.out.println("내 용 : " + boardVo.getBoard_content());
		System.out.println("조회수 : " + boardVo.getBoard_cnt());
		System.out.println("작성일 : " + boardVo.getBoard_date());
		System.out.println("-------------------------------");
		System.out.println();
	}
	// 전체 게시글 목록 출력하기
	private void DisplayAllBoard() {
		List<BoardVO> boardList = service.getAllBoard();
		displayBoardList(boardList);
	}
	
	// 게시글 목록을 매개변수로 받아서 출력하는 메서드
	private void displayBoardList(List<BoardVO> boardList) {
		
		System.out.println();
		System.out.println("-------------------------------");
		System.out.println(" 번호	제목	작성자	조회수");
		System.out.println("-------------------------------");
		
		if(boardList == null || boardList.size()==0) {
			System.out.println("게시글이 하나도 없습니다.");
		} else {
			for(BoardVO bVo : boardList) {
				System.out.println(bVo.getBoard_no()+"\t"
						+ bVo.getBoard_title()+"\t"
						+ bVo.getBoard_writer()+"\t"
						+ bVo.getBoard_cnt());
				
			}
		}
		System.out.println("-------------------------------");
		System.out.println("조회된 게시글의 총 개수는 : " + boardList.size() + "개 입니다.");
	}
	
	// 새 게시글 작성하는 메서드
	private void InsertBoard() {
		
		scan.nextLine(); // 버퍼 비우기
		
		System.out.println();
		System.out.println("	새로운 게시글 작성하기");
		System.out.println("-------------------------------");
		System.out.print("제 목 : ");
		String title = scan.nextLine();
		
		System.out.print("작성자 : ");
		String writer = scan.nextLine();
		
		System.out.print("내 용 : ");
		String content = scan.nextLine();
		
		// 입력 받은 자료를 VO객체에 저장한다.
		BoardVO boardVo = new BoardVO();
		boardVo.setBoard_title(title);
		boardVo.setBoard_writer(writer);
		boardVo.setBoard_content(content);
		
		int cnt = service.InsertBoard(boardVo);
		
		System.out.println();
		if(cnt>0) {
			System.out.println("새로운 게시글이 추가되었습니다.");
		} else {
			System.out.println("게시글 추가에 실패하였습니다.");
		}
		
		
	}
	
	// 메뉴를 출력하고 작업 번호를 반환하는 메서드
	public int displayMenu() {
		System.out.println();
		System.out.println("1. 게시글 작성");
		System.out.println("2. 게시글 보기");
		System.out.println("3. 게시글 수정");
		System.out.println("4. 게시글 삭제");
		System.out.println("5. 게시글 검색");
		System.out.println("6. 전체 게시글 조회");
		System.out.println("0. 프로그램 종료");
		System.out.println("----------------");
		System.out.print("메뉴 선택 >> ");
		
		return scan.nextInt();
		
	}

}
