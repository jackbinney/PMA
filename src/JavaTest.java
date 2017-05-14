import junit.framework.TestCase;

// Test function for the calculateWeeklyDosage function
public class JavaTest extends TestCase {
	protected int frequency;
	protected double dosage, expected;
	
	protected void setUp() {
		frequency = 4;
		dosage = 15;
		expected = 420;
	}
	
	public void testDosages() {
		double result = CurrentMedicationTab.calculateWeeklyDosage(frequency, dosage);
		assertTrue(result == expected);
	}
}
