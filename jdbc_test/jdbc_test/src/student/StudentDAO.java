package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentDAO {
	final String driver = "org.mariadb.jdbc.Driver";
	final String db_ip = "localhost";
	final String db_port = "3306";
	final String db_name = "jdbc_test";
	final String db_url = 
			"jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 학생 정보 등록
	public int insertStudent(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {

			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "INSERT INTO tb_student_info(\r\n"
					+ "	student_name,\r\n"
					+ "    student_grade,\r\n"
					+ "    student_school,\r\n"
					+ "    student_addr,\r\n"
					+ "    student_phone\r\n"				
					+ ") VALUES(\r\n"
					+ "	?,?,?,?,?\r\n"
					+ ");";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paramMap.get("studentName").toString());
			pstmt.setInt(2, Integer.parseInt(paramMap.get("studentGrade").toString()));
			pstmt.setString(3, paramMap.get("studentSchool").toString());
			pstmt.setString(4, paramMap.get("studentAddr").toString());
			pstmt.setString(5, paramMap.get("studentPhone").toString());
			
			resultChk = pstmt.executeUpdate();
			
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return resultChk;
	}
	
	// 학생 성적 등록
	public int insertScore(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {

			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "INSERT INTO tb_student_score(\r\n"
					+ "	score_season,\r\n"
					+ "    score_semester,\r\n"
					+ "    score_exam_type,\r\n"
					+ "    score_subject,\r\n"
					+ "    score_point,\r\n"
					+ "    student_idx\r\n"
					+ ") VALUES(\r\n"
					+ "	?,?,?,?,?,?\r\n"
					+ ");";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paramMap.get("season").toString());
			pstmt.setInt(2, Integer.parseInt(paramMap.get("semester").toString()));
			pstmt.setString(3, paramMap.get("examType").toString());
			pstmt.setString(4, paramMap.get("subject").toString());
			pstmt.setInt(5, Integer.parseInt(paramMap.get("point").toString()));
			pstmt.setInt(6, Integer.parseInt(paramMap.get("studentIdx").toString()));
			
			resultChk = pstmt.executeUpdate();
			
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return resultChk;
	}
		
	// 전체 학생 조회
	public List<HashMap<String, Object>> printAllStudent(){
		List<HashMap<String, Object>> studentList = new ArrayList();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "SELECT  student.student_name AS studentName,\r\n"
					+ "		student.student_grade AS studentGrade,\r\n"
					+ "		student.student_school AS studentSchool,\r\n"
					+ "		student.student_addr AS studentAddr,\r\n"
					+ "		student.student_phone AS studentPhone,\r\n"
					+ "        score.score_season AS scoreSeason,\r\n"
					+ "        score.score_semester AS scoreSemester,\r\n"
					+ "        case when score.score_exam_type = 'M' then '중간고사'\r\n"
					+ "			 when score.score_exam_type = 'F' then '기말고사' END AS scoreExamType,\r\n"
					+ "        score.score_subject AS scoreSubject,\r\n"
					+ "        score.score_point AS scorePoint\r\n"
					+ "FROM tb_student_info student\r\n"
					+ "LEFT JOIN tb_student_score score\r\n"
					+ "ON student.student_idx = score.student_idx;";

			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("studentName", rs.getString("studentName"));
				rsMap.put("studentGrade", rs.getInt("studentGrade"));
				rsMap.put("studentSchool", rs.getString("studentSchool"));
				rsMap.put("studentAddr", rs.getString("studentAddr"));
				rsMap.put("studentPhone", rs.getString("studentPhone"));
				rsMap.put("scoreSeason", rs.getString("scoreSeason"));	
				rsMap.put("scoreSemester", rs.getInt("scoreSemester"));	
				rsMap.put("scoreExamType", rs.getString("scoreExamType"));	
				rsMap.put("scoreSubject", rs.getString("scoreSubject"));
				rsMap.put("scorePoint", rs.getInt("scorePoint"));	
				
				
				studentList.add(rsMap);

			}

		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return studentList;
	}
	// 학생이름으로 학생 정보 조회
	public List<HashMap<String, Object>> printSearchStudent(String studentName){
		List<HashMap<String, Object>> studentList = new ArrayList();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {

			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "SELECT  student.student_idx AS studentIdx,\r\n"
					+ "		student.student_name AS studentName,\r\n"
					+ "		student.student_grade AS studentGrade,\r\n"
					+ "		student.student_school AS studentSchool,\r\n"
					+ "		student.student_addr AS studentAddr,\r\n"
					+ "		student.student_phone AS studentPhone,\r\n"
					+ "        score.score_season AS scoreSeason,\r\n"
					+ "        score.score_semester AS scoreSemester,\r\n"
					+ "        case when score.score_exam_type = 'M' then '중간고사'\r\n"
					+ "			 when score.score_exam_type = 'F' then '기말고사' END AS scoreExamType,\r\n"
					+ "        score.score_subject AS scoreSubject,\r\n"
					+ "        score.score_point AS scorePoint\r\n"
					+ "FROM tb_student_info student\r\n"
					+ "LEFT JOIN tb_student_score score\r\n"
					+ "ON student.student_idx = score.student_idx\r\n"
					+ "WHERE student.student_name like concat('%', ?, '%');";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentName);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("studentIdx", rs.getInt("studentIdx"));
				rsMap.put("studentName", rs.getString("studentName"));
				rsMap.put("studentGrade", rs.getInt("studentGrade"));
				rsMap.put("studentSchool", rs.getString("studentSchool"));
				rsMap.put("studentAddr", rs.getString("studentAddr"));
				rsMap.put("studentPhone", rs.getString("studentPhone"));
				rsMap.put("scoreSeason", rs.getString("scoreSeason"));	
				rsMap.put("scoreSemester", rs.getInt("scoreSemester"));	
				rsMap.put("scoreExamType", rs.getString("scoreExamType"));	
				rsMap.put("scoreSubject", rs.getString("scoreSubject"));
				rsMap.put("scorePoint", rs.getInt("scorePoint"));
				
				studentList.add(rsMap);

			}

		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return studentList;
	}
	
	
	
	// 학생 정보 수정
	public int updateStudent(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {

			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "UPDATE tb_student_info \n";
			int updateChoice = Integer.parseInt(paramMap.get("updateChoice").toString());
			switch (updateChoice) {
				case 1: {
					sql += "set student_name = ?\n";
					break;
				}
				case 2: {
					sql += "set student_school = ?\n";
					break;
				}
				case 3: {
					sql += "set student_grade = ?\n";
					break;
				}
				case 4: {
					sql += "set student_phone = ?\n";
					break;
				}
				case 5: {
					sql += "set student_addr = ?\n";
					break;
				}
			}
			sql += "WHERE student_idx = ?;";			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, paramMap.get("updateContents").toString());
			pstmt.setInt(2, Integer.parseInt(paramMap.get("studentIdx").toString()));
			
			
			resultChk = pstmt.executeUpdate();
			
			
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return resultChk;
	}
	
	// 학생 성적 수정
	public int updateScore(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		
		return resultChk;
	}
	
	// 학생 정보 삭제
	public int deleteStudent(int studentIdx) {
		int resultChk = 0;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {

			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "DELETE FROM tb_student_info WHERE student_idx = ?;";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentIdx);
			
			resultChk = pstmt.executeUpdate();
			
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultChk;
	}
	
	// 학생 성적 삭제
	public int deleteScore(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		
		return resultChk;
	}
	
	// 학생이름으로 학생 정보 조회(수정, 삭제 검색용)
		public List<HashMap<String, Object>> printSearchStudentInfo(String studentName){
			List<HashMap<String, Object>> studentList = new ArrayList();
			
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(db_url, "root", "1234");
				if(conn != null) {

				}
			}catch(ClassNotFoundException e) {
				System.out.println("드라이버 로드 실패");
				e.printStackTrace();
			}catch(SQLException e) {
				System.out.println("접속 실패");
				e.printStackTrace();
			}
			
			try {
				String sql = "SELECT  student.student_idx AS studentIdx,"
						+ "		student.student_name AS studentName,\r\n"
						+ "		student.student_grade AS studentGrade,\r\n"
						+ "		student.student_school AS studentSchool,\r\n"
						+ "		student.student_addr AS studentAddr,\r\n"
						+ "		student.student_phone AS studentPhone\r\n"
						+ "FROM tb_student_info student\r\n"
						+ "WHERE student.student_name like concat('%', ?, '%');";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, studentName);
				rs = pstmt.executeQuery();
				
				
				while(rs.next()) {
					HashMap<String, Object> rsMap = new HashMap<String, Object>();
					rsMap.put("studentIdx", rs.getInt("studentIdx"));
					rsMap.put("studentName", rs.getString("studentName"));
					rsMap.put("studentGrade", rs.getInt("studentGrade"));
					rsMap.put("studentSchool", rs.getString("studentSchool"));
					rsMap.put("studentAddr", rs.getString("studentAddr"));
					rsMap.put("studentPhone", rs.getString("studentPhone"));
					
					studentList.add(rsMap);

				}

			}catch (SQLException e) {
				// TODO: handle exception
				System.out.println("error :" + e);
			}finally {
				try {
					if(rs != null) {
						rs.close();
					}
					
					if(pstmt != null) {
						pstmt.close();
					}
					if(conn != null && conn.isClosed()) {
						conn.close();
					}
				
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			return studentList;
		}
}
