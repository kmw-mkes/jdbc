package member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class MemberServiceImpl implements MemberService{
	MemberDAO mDAO = new MemberDAO();
	Scanner sc = new Scanner(System.in);
	
	@Override
	public void startProgram() {
		// TODO Auto-generated method stub
		int count = 0;

		while (true) {

			int choice = printMenu();

			switch(choice) {
			case 1 :
				displayMsg("1. 회원 정보 등록");
				insertMember();
				break;
			case 2 :
				displayMsg("2. 회원 정보 수정");
				//updateMember();
				break;
			case 3 :
				displayMsg("3. 회원 정보 삭제");
				deleteMember();
				break;
			case 4 :
				displayMsg("4. 회원 정보 출력(이름)");
				printMember();
				break;
			case 5 :
				displayMsg("5. 회원 전체 정보 출력");
				printAllMember();
				break;
			case 6 :
				displayMsg("프로그램 종료 ~!!");
				count++;
				break;
			default :
				// "잘못된 숫자가 입력됨. 1~6 사이의 숫자 입력 가능");
				break;
			}

			if (count == 1) {
				break;
			}
		}
	}
	
	// 0. 프로그램 메뉴 출력 및 선택
	public int printMenu() {
		displayMsg("===== 회원 관리 프로그램 =====");
		displayMsg("1. 회원 정보 등록");
		displayMsg("2. 회원 정보 수정");
		displayMsg("3. 회원 정보 삭제");
		displayMsg("4. 회원 정보 출력(이름)");
		displayMsg("5. 회원 전체 정보 출력");
		displayMsg("6. 프로그램 종료");
		System.out.print("[선택] : ");

		int choice = sc.nextInt();

		return choice;
	}
	
	// 메시지 출력용
	public void displayMsg(String msg) {
		System.out.println(msg);
	}
	
	public void insertMember() {
		// 회원 정보를 등록할 member 객체 생성자
		Member member = new Member();

		// 회원 정보 입력받기
		System.out.print("회원 아이디를 입력해 주세요 : ");
		String memberId = sc.next();

		System.out.print("회원 비밀번호 : ");
		String memberPw = sc.next();

		System.out.print("회원 이름 : ");
		String memberName = sc.next();
		
		System.out.print("생년월일(ex) 19900101) : ");
		String memberBirth = sc.next();

		System.out.print("이메일 주소를 입력해 주세요. : ");
		String email = sc.next();

		System.out.print("연락처 정보를 입력해 주세요. : ");
		String phone = sc.next();

		// member에 회원정보 셋팅
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);
		member.setMemberBirth(memberBirth);
		member.setMemberEmail(email);
		member.setMemberPhone(phone);

		int chk = 0;
		chk = mDAO.insertMember(member);
		
		if(chk > 0) {
			System.out.println("등록되었습니다.");
		}else {
			System.out.println("등록에 실패하였습니다.");
		}
	}
	
	public void printAllMember() {
		
		List<HashMap<String, Object>> memberList = new ArrayList();
		memberList = mDAO.printAllMember();
		System.out.println("ID\t이름\t생년월일\t연락처");
		for(int i =0; i<memberList.size(); i++) {
			System.out.print(memberList.get(i).get("member_id")+"\t");
			System.out.print(memberList.get(i).get("member_name")+"\t"); 
			System.out.print(memberList.get(i).get("member_birth")+"\t");
			System.out.println(memberList.get(i).get("member_phone"));
		}
	}
	
	public void printMember() {
		System.out.print("조회할 회원 아이디를 입력해 주세요 : ");
		String findId = sc.next();
		List<HashMap<String, Object>> memberList = mDAO.printSearchMember(findId);

		boolean flag = false;

		for (int i=0; i<memberList.size(); i++) {

			System.out.print(memberList.get(i).get("member_id")+"\t");
			System.out.print(memberList.get(i).get("member_name")+"\t"); 
			System.out.print(memberList.get(i).get("member_birth")+"\t");
			System.out.println(memberList.get(i).get("member_phone"));
		}
	}
	
	public void deleteMember() {
		System.out.println("삭제할 회원 이름을 입력하세요>>>>>");
		sc.nextLine();
		String memberName = sc.nextLine();
		
		List<HashMap<String, Object>> list = mDAO.printSearchMember(memberName);
		for (int i=0; i<list.size(); i++) {
			System.out.print(list.get(i).get("member_id")+"\t");
			System.out.print(list.get(i).get("member_name")+"\t"); 
			System.out.print(list.get(i).get("member_birth")+"\t");
			System.out.println(list.get(i).get("member_phone"));
		}
		System.out.println("삭제할 회원의 순번을 입력하세요>>>>>");
		int num = sc.nextInt();
		int memberIdx = Integer.parseInt(list.get(num-1).get("member_idx").toString());
		
		// DB에서 삭제
		int chk = 0;
		chk = mDAO.deleteMember(memberIdx);
		if(chk > 0) {
			System.out.println("삭제완료");
		}else {
			System.out.println("삭제 실패");
		}
		
	}
}
