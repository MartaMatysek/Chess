package com.capgemini.chess.algorithms.pieces;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;

public class Bishop extends MoveValidator implements PieceInterface {

	private Color color;
	private PieceType pieceType;

	public Bishop(Color color) {
		this.color = color;
		this.pieceType = PieceType.BISHOP;
	}

	public List<Move> getPossibleMoves(Coordinate from, Board board) {
		List<Move> possibleMoves = new ArrayList<Move>();
		PieceInterface pieceFrom = board.getPieceAt(from);

		for (int numberOfDirection = 0; numberOfDirection < 4; numberOfDirection++) {
			for (int step = 1; step < Board.SIZE; step++) {
				int[][] direction = movesDirection(from.getX(), from.getY(), step);
				Coordinate to = new Coordinate(direction[numberOfDirection][0], direction[numberOfDirection][1]);
				if (isInBoard(to)) {
					if (isFinishPositionFree(to, board)) {
						possibleMoves.add(new Move(from, to, MoveType.ATTACK, pieceFrom));
					} 
					else {
						PieceInterface pieceTo = board.getPieceAt(to);
						if (isFinishPositionCanCapture(pieceFrom, pieceTo)) {
							possibleMoves.add(new Move(from, to, MoveType.CAPTURE, pieceFrom));
						}
						break;
					}
				} 
				else {
					break;
				}
			}
		}

		return possibleMoves;
	}
	
	private int[][] movesDirection(int startRow, int startColumn, int step){
		int[][] direction = { {startRow + step, startColumn + step}, {startRow + step, startColumn - step}, 
							  {startRow - step, startColumn + step}, {startRow - step, startColumn - step} };
		return direction;
	}

	public Color getColor() {
		return color;
	}

	public PieceType getPieceType() {
		return pieceType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((pieceType == null) ? 0 : pieceType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bishop other = (Bishop) obj;
		if (color != other.color)
			return false;
		if (pieceType != other.pieceType)
			return false;
		return true;
	}

}
