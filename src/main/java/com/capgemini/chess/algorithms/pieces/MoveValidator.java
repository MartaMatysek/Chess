package com.capgemini.chess.algorithms.pieces;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.generated.Board;

public class MoveValidator {
	
	public boolean isInBoard(Coordinate coordinate){
		return (coordinate.getX()> -1 && coordinate.getX() < Board.SIZE 
				&& coordinate.getY()> -1 && coordinate.getY() < Board.SIZE);
	}
	
	public boolean isFinishPositionFree(Coordinate to, Board board){
		return (board.getPieceAt(to) == null);
	}
	
	public boolean isFinishPositionCanCapture(PieceInterface pieceFrom, PieceInterface pieceTo){
		if(pieceTo == null){
			return false;
		}
		else if(pieceFrom.getColor() != pieceTo.getColor()){
			return true;
		}
		else{
			return false;
		}
	}
}
