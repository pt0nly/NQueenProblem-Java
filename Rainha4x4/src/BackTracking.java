
public class BackTracking {
	private long colDone;
	private long col;
	private long row;
	private boolean done;
	private Stack<Queen> st;
	
	public BackTracking(long _colDone, long _col, long _row, boolean _done, Stack<Queen> _stack) {
		this.setColDone(_colDone);
		this.setCol(_col);
		this.setRow(_row);
		this.setDone(_done);
		this.setStack(_stack);
	}

	public long getColDone() {
		return this.colDone;
	}
	
	public void setColDone(long _colDone) {
		this.colDone = _colDone;
	}

	public long getCol() {
		return this.col;
	}
	
	public void setCol(long _col) {
		this.col = _col;
	}

	public long getRow() {
		return row;
	}

	public void setRow(long _row) {
		this.row = _row;
	}

	public boolean getDone() {
		return this.done;
	}

	public void setDone(boolean _done) {
		this.done = _done;
	}

	public Stack<Queen> getStack() {
		return this.st;
	}
	
	public void setStack(Stack<Queen> _stack) {
		this.st = _stack;
	}

}
