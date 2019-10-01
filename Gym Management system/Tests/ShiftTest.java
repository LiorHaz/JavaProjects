import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mygym.models.DBConnection;
import com.mygym.models.Shift;

class ShiftTest { 
	static Shift s;
	@BeforeAll
	static void setUp() throws Exception {
		s=Shift.getInstance();
	}
	//shifts_report worker_id
	/**
	 * Test entry shift case
	 */
	@Test
	void entryShiftTest() {
		
		//check if the worker entered his shift successfully
		assertTrue("Worker didn't enter his shift successfully",s.startShift(203585565));
		//make sure the worker can not enter the shift after he is already in his shift
		assertFalse("Worker entered the shift again although he can not do it",s.startShift(203585565));
	}
	/**
	 * Test quit shift case
	 */
	@Test
	void quitShiftTest() throws Exception {
		//check if the worker has finished his shift successfully
		assertTrue("Worker has not finished his shift successfully",s.endShift(203585565));
	}
	
	@AfterAll
	static void tearDown() throws Exception {
		//reset the details in the database for the next test
		DBConnection db=DBConnection.getInstance();
		db.openConnection();
		LocalDateTime myDateObj = LocalDateTime.now();  
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String str=myDateObj.format(myFormatObj);
		String[] s=str.split(" ");
		String date=s[0];
		db.executeUpdate("delete from shifts_report where worker_id='203585565' and date ='"+date+"'");
		db.closeConnection();
	}

}
