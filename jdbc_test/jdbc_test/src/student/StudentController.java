package student;

public class StudentController {

	
		public static void main(String[] args) {
			
			StudentService studentService = new StudentServiceImpl();
			studentService.startProgram();
			
		}
	
}
