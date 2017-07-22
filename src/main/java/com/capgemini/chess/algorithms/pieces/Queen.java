package com.capgemini.chess.algorithms.pieces;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;

/**
 * Queen class stores data about possible moves of this figure, color and type.
 */
public class Queen extends MoveValidator implements PieceInterface {

	private Color color;
	private PieceType pieceType;

	public Queen(Color color) {
		this.color = color;
		this.pieceType = PieceType.QUEEN;
	}

	public List<Move> getPossibleMoves(Coordinate from, Board board) {
		List<Move> possibleMoves = new ArrayList<Move>();

		PieceInterface rook = new Rook(this.color);
		PieceInterface bishop = new Bishop(this.color);
		List<Move> possibleMovesForRook = rook.getPossibleMoves(from, board);
		List<Move> possibleMovesForBishop = bishop.getPossibleMoves(from, board);

		possibleMoves.addAll(possibleMovesForRook);
		possibleMoves.addAll(possibleMovesForBishop);

		return possibleMoves;
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
		Queen other = (Queen) obj;
		if (color != other.color)
			return false;
		if (pieceType != other.pieceType)
			return false;
		return true;
	}
}
