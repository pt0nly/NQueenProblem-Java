
public class Queen {
	private long row;
	private long diagonalUpMask;
	private long diagonalDownMask;
	private long rowMask;
	
	public Queen(long _row, long _rowMsk, long _diagUpMsk, long _diagDownMsk) {
		this.setRow(_row);
		this.setDiagonalUpMask(_diagUpMsk);
		this.setDiagonalDownMask(_diagDownMsk);
		this.setRowMask(_rowMsk);
	}
	
	public String toString() {
		return "" + this.row;
	}

	public long getRow() {
		return row;
	}

	public void setRow(long row) {
		this.row = row;
	}

	public long getDiagonalUpMask() {
		return diagonalUpMask;
	}

	public void setDiagonalUpMask(long diagonalUpMask) {
		this.diagonalUpMask = diagonalUpMask;
	}

	public long getDiagonalDownMask() {
		return diagonalDownMask;
	}

	public void setDiagonalDownMask(long diagonalDownMask) {
		this.diagonalDownMask = diagonalDownMask;
	}

	public long getRowMask() {
		return rowMask;
	}

	public void setRowMask(long rowMask) {
		this.rowMask = rowMask;
	}

}
