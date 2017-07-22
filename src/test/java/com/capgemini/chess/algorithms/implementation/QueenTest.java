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
import com.capgemini.chess.algorithms.pieces.Queen;
import com.capgemini.chess.algorithms.pieces.Rook;

public class QueenTest {

	@Test
	public void testCheckNumberOfQueenPossibleMoves() {
		// given
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface queen = new Queen(Color.BLACK);
		board.setPieceAt(queen, new Coordinate(3, 4));

		// when
		List<Move> listOfMoves = queen.getPossibleMoves(new Coordinate(3, 4), board);
		int numberOfPossibleMoves = listOfMoves.size();

		// then
		assertTrue(numberOfPossibleMoves == 19);
	}
	
	@Test
	public void testCheckQueenPossibleMovesContainsMove() {
		// given
		Coordinate from = new Coordinate(3, 3);
		Coordinate to = new Coordinate(3, 5);
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface queen = new Queen(Color.BLACK);
		PieceInterface rook = new Rook(Color.WHITE);
		board.setPieceAt(queen, from);
		board.setPieceAt(rook, to);
		Move move = new Move(from, to, MoveType.CAPTURE, queen);
		
		//when
		List<Move> listOfMoves = queen.getPossibleMoves(from, board);

		//then
		assertTrue(listOfMoves.contains(move));

	}
	
	@Test
	public void testCheckQueenPossibleMovesNotContainsMove(){
		// given
		Coordinate from = new Coordinate(4, 4);
		Coordinate to = new Coordinate(4, 0);
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface queen = new Queen(Color.WHITE);
		board.setPieceAt(queen, from);
		Move move = new Move(from, to, MoveType.ATTACK, queen);
		
		//when
		List<Move> listOfMoves = queen.getPossibleMoves(from, board);

		//then
		assertFalse(listOfMoves.contains(move));
	}

}
