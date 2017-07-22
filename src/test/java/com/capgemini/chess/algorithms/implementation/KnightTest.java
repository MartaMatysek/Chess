package com.capgemini.chess.algorithms.implementation;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.pieces.Knight;
import com.capgemini.chess.algorithms.pieces.PieceInterface;
import com.capgemini.chess.algorithms.pieces.Rook;

public class KnightTest {

	@Test
	public void testCheckNumberOfKnightPossibleMoves() {
		// given
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface knight = new Knight(Color.BLACK);
		board.setPieceAt(knight, new Coordinate(3, 4));
		board.setPieceAt(new Rook(Color.BLACK), new Coordinate(1, 3));

		// when
		List<Move> listOfMoves = knight.getPossibleMoves(new Coordinate(3, 4), board);
		int numberOfPossibleMoves = listOfMoves.size();

		// then
		assertTrue(numberOfPossibleMoves == 5);
	}
	
	@Test
	public void testCheckKnightPossibleMovesContainsMove() {
		// given
		Coordinate from = new Coordinate(3, 4);
		Coordinate to = new Coordinate(1, 3);
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface knight = new Knight(Color.BLACK);
		board.setPieceAt(knight, from);
		Move move = new Move(from, to, MoveType.ATTACK, knight);
		
		//when
		List<Move> listOfMoves = knight.getPossibleMoves(from, board);

		//then
		assertTrue(listOfMoves.contains(move));
	}
	
	@Test
	public void testCheckKnightPossibleMovesNotContainsMove() {
		// given
		Coordinate from = new Coordinate(4, 4);
		Coordinate to = new Coordinate(2, 3);
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface knight = new Knight(Color.WHITE);
		PieceInterface rook = new Rook(Color.WHITE);
		board.setPieceAt(knight, from);
		board.setPieceAt(rook, to);
		Move move = new Move(from, to, MoveType.ATTACK, knight);
		
		//when
		List<Move> listOfMoves = knight.getPossibleMoves(from, board);

		//then
		assertFalse(listOfMoves.contains(move));
	}
}
