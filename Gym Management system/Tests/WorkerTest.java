import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.mygym.models.DBConnection;
import com.mygym.models.Trainer;
import com.mygym.models.Worker;
import com.mygym.models.WorkersModel;

class WorkerTest {
	static Worker worker;
	static WorkersModel wm;
	
	@BeforeAll
	static void setUp() throws Exception {
		worker=new Trainer("121","miko","edri","17/7/1991");
		wm=new WorkersModel();
	}

	/**
	 * Adding worker test
	 * @throws Exception
	 */
	@Test
	void addWorkerTest() {
		//check that a new worker can be added successfully
		assertTrue("Worker was not added successfully",wm.addWorker(worker));
		//Check that an existing worker can not be added
		assertFalse("Worker was added although he is already exists",wm.addWorker(worker));
	}
	
	@AfterAll
	static void tearDown() throws Exception {
		//reset the details in the database for the next test
		DBConnection db=DBConnection.getInstance();
		db.openConnection();
		db.executeUpdate("delete from workers where id=121");
		db.closeConnection();
	}

}
