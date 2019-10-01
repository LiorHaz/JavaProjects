package com.hit.games;

public class CatchTheBunnySmart extends CatchTheBunny {

	public CatchTheBunnySmart(int rowLength,int colLength) {
		super(rowLength,colLength);
	}

	@Override
	public void calcComputerMove() {
		System.out.println("Calculating computer move....");
		//Edge cases:
		if(bunny.getRow()==0) 
			checkEdgeCaseRow0();
		else if(bunny.getRow()==8)
			checkEdgeCaseRow8();
		else if(bunny.getColumn()==0)
			checkEdgeCaseColumn0();
		else if(bunny.getColumn()==8)
			checkEdgeCaseColumn8();
		else //Check rest of the cases
			checkOtherCases();
		}
				
	//Moves the bunny up
	private void moveBunnyUp() {
		board[bunny.getRow()][bunny.getColumn()]=BoardSigns.BLANK.getSign();
		bunny.setRow(bunny.getRow()-1);
		bunny.setColumn(bunny.getColumn());
		board[bunny.getRow()][bunny.getColumn()]=BoardSigns.COMPUTER.getSign();
	}
	//Moves the bunny down
	private void moveBunnyDown() {
		board[bunny.getRow()][bunny.getColumn()]=BoardSigns.BLANK.getSign();
		bunny.setRow(bunny.getRow()+1);
		bunny.setColumn(bunny.getColumn());
		board[bunny.getRow()][bunny.getColumn()]=BoardSigns.COMPUTER.getSign();
	}
	//Moves the bunny left
	private void moveBunnyLeft() {
		board[bunny.getRow()][bunny.getColumn()]=BoardSigns.BLANK.getSign();
		bunny.setRow(bunny.getRow());
		bunny.setColumn(bunny.getColumn()-1);
		board[bunny.getRow()][bunny.getColumn()]=BoardSigns.COMPUTER.getSign();
	}
	//Moves the bunny right
	private void moveBunnyRight() {
		board[bunny.getRow()][bunny.getColumn()]=BoardSigns.BLANK.getSign();
		bunny.setRow(bunny.getRow());
		bunny.setColumn(bunny.getColumn()+1);
		board[bunny.getRow()][bunny.getColumn()]=BoardSigns.COMPUTER.getSign();
	}
	//Check if the player is above the bunny
	private boolean checkUp() {
		if(bunny.getColumn()!=player.getColumn())
			return false;
		for(int i=bunny.getRow();i>0;i--)
		if(i==player.getRow())
			return true;
		return false;
	}
	//Check if the player is under the bunny
	private boolean checkDown() {
		if(bunny.getColumn()!=player.getColumn())
			return false;
		for(int i=bunny.getRow();i<row;i++)
			if(i==player.getRow())
				return true;
			return false;
	}
	//Check if the player is to the left of the bunny
	private boolean checkLeft() {
		if(bunny.getRow()!=player.getRow())
			return false;
		for(int i=bunny.getColumn();i>0;i--)
			if(i==player.getColumn())
				return true;
			return false;
	}
	//Check if the player is to the right of the bunny
	private boolean checkRight() {	
		if(bunny.getRow()!=player.getRow())
			return false;
		for(int i=bunny.getColumn();i<col;i++)
			if(i==player.getColumn())
				return true;
			return false;
	}
	//Check if the player is on the up left slant
	private boolean checkUpLeftSlant() {
		return (bunny.getRow()-1==player.getRow())&&(bunny.getColumn()-1==player.getColumn());
	}
	//Check if the player is on the down left slant
	private boolean checkDownLeftSlant() {
		return (bunny.getRow()+1==player.getRow())&&(bunny.getColumn()-1==player.getColumn());	
	}
	//Check if the player is on the up right slant
	private boolean checkUpRightSlant() {
		return (bunny.getRow()-1==player.getRow())&&(bunny.getColumn()+1==player.getColumn());
	}
	//Check if the player is on the down right slant
	private boolean checkDownRightSlant() {
		return (bunny.getRow()+1==player.getRow())&&(bunny.getColumn()+1==player.getColumn());
	}
	
	
	//Check edge case when row=0
	private void checkEdgeCaseRow0() {	
		if(bunny.getColumn()==0)
			checkEdgeCaseRow0Col0();
		else if(bunny.getColumn()==8)
			checkEdgeCaseRow0Col8();
		else
			checkEdgeCaseRow0OtherColumns();
	}
	//Check edge case when row=0 and column=0
	private void checkEdgeCaseRow0Col0() {
		if(checkRight())
			moveBunnyDown();
		else if(checkDown())
			moveBunnyRight();
		else
			moveBunnyDown();
	}
	//Check edge case when row=0 and column=8
	private void checkEdgeCaseRow0Col8() {
			if(checkLeft())
				moveBunnyDown();
			else if(checkDown())
				moveBunnyLeft();
			else
				moveBunnyLeft();
	}
	//Check edge case when row=0 and rest of the columns
	private void checkEdgeCaseRow0OtherColumns() {
		if(checkRight())
			moveBunnyLeft();
		else if(checkLeft())
			moveBunnyRight();
		else if(checkDown())
			moveBunnyRight();
		else if(checkDownRightSlant())
			moveBunnyLeft();
		else if(checkDownLeftSlant())
			moveBunnyRight();
		else
			moveBunnyLeft();
	}

		
	//Check edge case when row=8
	private void checkEdgeCaseRow8() {
		if(bunny.getColumn()==0)
			checkEdgeCaseRow8Col0();
		else if(bunny.getColumn()==8)
			checkEdgeCaseRow8Col8();
		else
			checkEdgeCaseRow8OtherColumns();
	}
	//Check edge case when row=8 and column=0
	private void checkEdgeCaseRow8Col0() {
		if(checkRight())
			moveBunnyUp();
		else if(checkUp())
			moveBunnyRight();
		else
			moveBunnyUp();
	}
	//Check edge case when row=8 and column=8
	private void checkEdgeCaseRow8Col8() {
			if(checkLeft())
				moveBunnyUp();
			else if(checkUp())
				moveBunnyLeft();
			else
				moveBunnyUp();
	}
	//Check edge case when row=8 and rest of the columns
	private void checkEdgeCaseRow8OtherColumns() {
			if(checkRight())
				moveBunnyLeft();
			else if(checkLeft())
				moveBunnyRight();
			else if(checkUp())
				moveBunnyRight();
			else if(checkUpRightSlant())
				moveBunnyLeft();
			else if(checkUpLeftSlant())
				moveBunnyRight();
			else
				moveBunnyLeft();
	}
	
	
	//Check edge case when column=0
	private void checkEdgeCaseColumn0() {
		if(bunny.getRow()==0)
			checkEdgeCaseColumn0Row0();
		else if(bunny.getRow()==8)
			checkEdgeCaseColumn0Row8();
		else
		checkEdgeCaseColumn0OtherRows();
	}
	//Check edge case when column=0 and row=0
	private void checkEdgeCaseColumn0Row0() {
		checkEdgeCaseRow0Col0();
	}
	//Check edge case when column=0 and row=8
	private void checkEdgeCaseColumn0Row8() {
		checkEdgeCaseRow8Col0();
	}
	//Check edge case when column=0 and rest of the rows
	private void checkEdgeCaseColumn0OtherRows() {
		if(checkRight()||checkDown()||checkDownRightSlant())
			moveBunnyUp();
		else if(checkUp()||checkUpRightSlant())
			moveBunnyDown();
		else
			moveBunnyUp();	
	}
	
	
	//Check edge case when column=8
	private void checkEdgeCaseColumn8() {
		if(bunny.getRow()==0)
			checkEdgeCaseColumn8Row0();
		else if(bunny.getRow()==8)
			checkEdgeCaseColumn8Row8();
		else
			checkEdgeCaseColumn8OtherRows();
	}
	//Check edge case when column=8 and row=0
	private void checkEdgeCaseColumn8Row0() {
		checkEdgeCaseRow0Col8();
	}
	//Check edge case when column=8 and row=8
	private void checkEdgeCaseColumn8Row8() {
		checkEdgeCaseRow8Col8();
	}
	//Check edge case when column=8 and rest of the rows
	private void checkEdgeCaseColumn8OtherRows() {
		if(checkLeft()||checkUp()||checkUpLeftSlant())
			moveBunnyDown();
		else if(checkDown()||checkDownLeftSlant())
			moveBunnyUp();
		else
			moveBunnyDown();	
	}

	//Check all other cases
	private void checkOtherCases() {
		if(checkRight())
			moveBunnyDown();
		else if(checkLeft()||checkDownLeftSlant()||checkDownRightSlant())
			moveBunnyUp();
		else if(checkUp()||checkUpRightSlant())
			moveBunnyLeft();
		else if(checkDown()||checkUpLeftSlant())
			moveBunnyRight();
		else
			moveBunnyUp();
			
	}
}
