package com.capgemini.chess.algorithms.pieces;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;

/**
 * Knight class stores data about possible moves of this figure, color and type.
 */
public class Knight extends MoveValidator implements PieceInterface {

	private Color color;
	private PieceType pieceType;
	
	public Knight(Color color){
		this.color = color;
		this.pieceType = PieceType.KNIGHT;
	}
	
	public List<Move> getPossibleMoves(Coordinate from, Board board) {
		List<Move> possibleMoves = new ArrayList<Move>();
		PieceInterface pieceFrom = board.getPieceAt(from);

		for (int numberOfDirection = 0; numberOfDirection < 8; numberOfDirection++) {
			int[][] direction = movesDirection(from.getX(), from.getY());
			Coordinate to = new Coordinate(direction[numberOfDirection][0], direction[numberOfDirection][1]);
			if (isInBoard(to)) {
				if (isFinishPositionFree(to, board)) {
					Move move = new Move(from, to, MoveType.ATTACK, board.getPieceAt(from));
					possibleMoves.add(move);
				} else {
					PieceInterface pieceTo = board.getPieceAt(to);
					if (isFinishPositionCanCapture(pieceFrom, pieceTo)) {
						Move move = new Move(from, to, MoveType.CAPTURE, board.getPieceAt(from));
						possibleMoves.add(move);
					}
				}
			}
		}
		return possibleMoves;
	}
	
	/**
	 * movesDirection contains all of possible direction for knight
	 * @param startRow source row piece position
	 * @param startColumn source column piece position 
	 * @param step step by which the figure moves across the board
	 * @return table of all moves direction
	 */
	private int[][] movesDirection(int startRow, int startColumn) {
		int[][] direction = { { startRow - 2, startColumn + 1 }, { startRow - 2, startColumn - 1 },
				{ startRow - 1, startColumn - 2 }, { startRow + 1, startColumn - 2 }, { startRow + 2, startColumn - 1 },
				{ startRow + 2, startColumn + 1 }, { startRow - 1, startColumn + 2 },
				{ startRow + 1, startColumn + 2 } };
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
		Knight other = (Knight) obj;
		if (color != other.color)
			return false;
		if (pieceType != other.pieceType)
			return false;
		return true;
	}
	
}
