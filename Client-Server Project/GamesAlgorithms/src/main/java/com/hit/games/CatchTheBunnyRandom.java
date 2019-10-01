package com.hit.games;
import java.util.Random;

public class CatchTheBunnyRandom extends CatchTheBunny {
	public CatchTheBunnyRandom(int rowLength,int colLength) {
		super(rowLength,colLength);
	}
	@Override
	//Locating the bunny on the board randomly
	public void calcComputerMove() {
		System.out.println("Calculating computer move....");
		int r=0,c=0,randDir,movement=0;
		board[bunny.getRow()][bunny.getColumn()]=BoardSigns.BLANK.getSign();
		//Check computer's steps validation 
		//randDir=0-->Computer will move up/down.
		//randDir=1-->Computer will move left/right.
		randDir=new Random().nextInt(2);
		//Choose randomly to move up/down
		if(randDir==0) {
			do {
				movement=(new Random().nextInt(3)-1);
				r=bunny.getRow()+movement;
				c=bunny.getColumn();
			}
			while((r<0||r>row-1)||movement==0);
		}
		//Choose randomly to move left/right
		else
			do {
				movement=(new Random().nextInt(3)-1);
				r=bunny.getRow();
				c=bunny.getColumn()+movement;
			}
			while((c<0||c>col-1)||movement==0);
		bunny.setRow(r);
		bunny.setColumn(c);
		board[bunny.getRow()][bunny.getColumn()]=BoardSigns.COMPUTER.getSign();
	}
}
