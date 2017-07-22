package com.capgemini.chess.algorithms.pieces;

import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;

public interface PieceInterface {

	public List<Move> getPossibleMoves(Coordinate from, Board board);
	
	public Color getColor();
	
	public PieceType getPieceType();
}
