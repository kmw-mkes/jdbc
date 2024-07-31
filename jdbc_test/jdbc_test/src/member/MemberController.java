package member;

public class MemberController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MemberService memberService = new MemberServiceImpl();
		
		memberService.startProgram();
	}

}
