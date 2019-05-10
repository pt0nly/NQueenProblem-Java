import static org.junit.Assert.*;
import org.junit.Test;

public class TestQueenSolver {

	@Test
	public void testEmptyStack() {
		Stack<Queen> st = new Stack<Queen>();
		long testRow = 1;
		
		assertEquals( true, QueenSolver.isSafe(st, testRow) );
	}

	@Test
	public void testUnsafeQueen() {
		long row = 1;
		long rowMask = 1 << (row - 1),
			diagUpMask = rowMask << 1,
			diagDownMask = rowMask >>> 1;
		Stack<Queen> st = new Stack<Queen>();
		long testRow = 1;
		
		st.push(new Queen(row, rowMask, diagUpMask, diagDownMask));
		
		assertEquals( false, QueenSolver.isSafe(st, testRow) );
	}

	@Test
	public void testSafeQueen() {
		long row = 1;
		long rowMask = 1 << (row - 1),
			diagUpMask = rowMask << 1,
			diagDownMask = rowMask >>> 1;
		Stack<Queen> st = new Stack<Queen>();
		long testRow = 3;
		
		st.push(new Queen(row, rowMask, diagUpMask, diagDownMask));
		
		assertEquals( true, QueenSolver.isSafe(st, testRow) );
	}


	@Test
	public void testQueen4x4() {
		int n = 4;
		Bag<String> solutions = new Bag<String>();
		
		solutions = QueenSolver.solve(n);
		
		assertEquals(2, solutions.size());
		
		n = 0;
		for (String nb: solutions) {
			if (++n == 1) {
				assertEquals("[2, 4, 1, 3]", nb);
			} else if (n == 2) {
				assertEquals("[3, 1, 4, 2]", nb);
			}
		}
	}

}
