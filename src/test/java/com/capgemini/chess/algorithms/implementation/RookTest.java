package com.capgemini.chess.algorithms.implementation;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.pieces.PieceInterface;
import com.capgemini.chess.algorithms.pieces.Rook;

public class RookTest {
	
	@Test
	public void testCheckNumberOfRookPossibleMoves() {
		// given
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface rook = new Rook(Color.WHITE);

		// when
		List<Move> listOfMoves = rook.getPossibleMoves(new Coordinate(0, 0), board);
		int numberOfPossibleMoves = listOfMoves.size();

		// then
		assertTrue(numberOfPossibleMoves == 0);

	}
	
	@Test
	public void testCheckRookPossibleMovesContainsMove() {
		// given
		Coordinate from = new Coordinate(3, 4);
		Coordinate to = new Coordinate(4, 4);
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface rook = new Rook(Color.BLACK);
		board.setPieceAt(rook, from);
		Move move = new Move(from, to, MoveType.ATTACK, rook);
		
		//when
		List<Move> listOfMoves = rook.getPossibleMoves(from, board);

		//then
		assertTrue(listOfMoves.contains(move));

	}
	
	@Test
	public void testCheckRookPossibleMovesNotContainsMove() {
		// given
		Coordinate from = new Coordinate(4, 4);
		Coordinate to = new Coordinate(5, 5);
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface rook = new Rook(Color.WHITE);
		board.setPieceAt(rook, from);
		Move move = new Move(from, to, MoveType.ATTACK, rook);
		
		//when
		List<Move> listOfMoves = rook.getPossibleMoves(from, board);

		//then
		assertFalse(listOfMoves.contains(move));
	}
}
