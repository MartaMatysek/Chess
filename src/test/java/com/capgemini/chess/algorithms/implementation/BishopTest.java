package com.capgemini.chess.algorithms.implementation;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.pieces.Bishop;
import com.capgemini.chess.algorithms.pieces.PieceInterface;

public class BishopTest {

	@Test
	public void testCheckNumberOfBishopPossibleMoves() {
		// given
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface bishop = new Bishop(Color.BLACK);
		board.setPieceAt(bishop, new Coordinate(3, 4));

		// when
		List<Move> listOfMoves = bishop.getPossibleMoves(new Coordinate(3, 4), board);
		int numberOfPossibleMoves = listOfMoves.size();

		// then
		assertTrue(numberOfPossibleMoves == 8);
	}
	
	@Test
	public void testCheckBishopPossibleMovesContainsMove() {
		// given
		Coordinate from = new Coordinate(3, 4);
		Coordinate to = new Coordinate(4, 5);
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface bishop = new Bishop(Color.BLACK);
		board.setPieceAt(bishop, from);
		Move move = new Move(from, to, MoveType.ATTACK, bishop);
		
		//when
		List<Move> listOfMoves = bishop.getPossibleMoves(from, board);

		//then
		assertTrue(listOfMoves.contains(move));
	}
	
	@Test
	public void testCheckBishopPossibleMovesNotContainsMove() {
		// given
		Coordinate from = new Coordinate(4, 4);
		Coordinate to = new Coordinate(4, 5);
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface bishop = new Bishop(Color.WHITE);
		board.setPieceAt(bishop, from);
		Move move = new Move(from, to, MoveType.ATTACK, bishop);
		
		//when
		List<Move> listOfMoves = bishop.getPossibleMoves(from, board);

		//then
		assertFalse(listOfMoves.contains(move));
	}
}
