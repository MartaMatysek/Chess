package com.capgemini.chess.algorithms.pieces;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.generated.Board;


public class MoveValidator{
	
	/**
	 * isInBoard check is coordinate is in board
	 * @param coordinate check coordinate
	 * @return true if the coordinate is in board or false if not
	 */
	public boolean isInBoard(Coordinate coordinate){
		return (coordinate.getX()> -1 && coordinate.getX() < Board.SIZE 
				&& coordinate.getY()> -1 && coordinate.getY() < Board.SIZE);
	}
	
	/**
	 * isFinishPositionFree check is final position is free
	 * @param to check coordinate
	 * @param board  actual board
	 * @return true if position is free, false if not
	 */
	public boolean isFinishPositionFree(Coordinate to, Board board){
		return (board.getPieceAt(to) == null);
	}
	
	/**
	 * isFinishPositionCanCapture check is in final position stay piece in other color than source piece 
	 * @param pieceFrom source piece 
	 * @param pieceTo target piece
	 * @return true if color of pieces is different, false if not
	 */
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
