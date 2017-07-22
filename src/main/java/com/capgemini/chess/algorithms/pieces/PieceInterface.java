package com.capgemini.chess.algorithms.pieces;

import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;

/**
 * PieceInterface is interface who contains methods for all figures
 */
public interface PieceInterface {

	/**
	 * getPossibleMoves returns a list of possible moves for piece for given coordinate and state of board
	 * @param from coordinate of piece
	 * @param board actual board
	 * @return List of possible moves
	 */
	public List<Move> getPossibleMoves(Coordinate from, Board board);
	
	public Color getColor();
	
	public PieceType getPieceType();
}
