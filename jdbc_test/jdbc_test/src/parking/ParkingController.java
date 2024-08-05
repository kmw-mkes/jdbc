package parking;

public class ParkingController {

	
	public static void main(String[] args) {
		ParkingService parkingService = new ParkingServiceImpl();
		
		parkingService.startProgram();
	}
	
}
