package com.capgemini.chess.algorithms.implementation;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
import com.capgemini.chess.algorithms.pieces.Bishop;
import com.capgemini.chess.algorithms.pieces.King;
import com.capgemini.chess.algorithms.pieces.Pawn;
import com.capgemini.chess.algorithms.pieces.PieceInterface;
import com.capgemini.chess.algorithms.pieces.Rook;

public class KingTest {
	

	@Test
	public void testBlackKingCastlingWithRightRook() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 7));
		board.setPieceAt(new Rook(Color.BLACK), new Coordinate(7, 7));
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(1, 1));
		board.setPieceAt(new King(Color.WHITE), new Coordinate(0, 0));

		
		// when
		BoardManager boardManager = new BoardManager(board);
		boardManager.performMove(new Coordinate(1, 1), new Coordinate(1, 2));
		Move castlingMove = boardManager.performMove(new Coordinate(4, 7), new Coordinate(6, 7));
		
		// then
		assertEquals(MoveType.CASTLING, castlingMove.getType());
		assertEquals(new King(Color.BLACK), castlingMove.getMovedPiece());
	}
	
	
	@Test
	public void testBlackKingCastlingWithLeftRook() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 7));
		board.setPieceAt(new Rook(Color.BLACK), new Coordinate(0, 7));
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(7, 1));
		board.setPieceAt(new King(Color.WHITE), new Coordinate(7, 0));

		
		// when
		BoardManager boardManager = new BoardManager(board);
		boardManager.performMove(new Coordinate(7, 1), new Coordinate(7, 2));
		Move castlingMove = boardManager.performMove(new Coordinate(4, 7), new Coordinate(2, 7));
		
		// then
		assertEquals(MoveType.CASTLING, castlingMove.getType());
		assertEquals(new King(Color.BLACK), castlingMove.getMovedPiece());
	}
	
	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidCastlingWhiteKingMoved() throws InvalidMoveException {
		// given
		Board board = new Board();
		
		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		board.setPieceAt(new Rook(Color.WHITE), new Coordinate(0, 0));

		BoardManager boardManager = new BoardManager(board);
		boardManager.performMove(new Coordinate(4, 0), new Coordinate(3, 0));
		boardManager.performMove(new Coordinate(3, 0), new Coordinate(4, 0));
		
		// when
		boardManager.performMove(new Coordinate(4, 0), new Coordinate(6, 0));
		
		// then 
		fail("This method should throw InvalidMoveException");
	}
	
	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidCastlingWithPiecesBetweenForWhiteKing() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		board.setPieceAt(new Rook(Color.WHITE), new Coordinate(0, 0));
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(3, 0));
		BoardManager boardManager = new BoardManager(board);
		
		// when
		boardManager.performMove(new Coordinate(4, 0), new Coordinate(2, 0));
		
		// then 
		fail("This method should throw InvalidMoveException");
	}
	
	@Test
	public void testCheckNumberOfKingPossibleMoves() {
		// given
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface king = new King(Color.WHITE);
		board.setPieceAt(king, new Coordinate(0, 4));

		// when
		List<Move> listOfMoves = king.getPossibleMoves(new Coordinate(0, 4), board);
		int numberOfPossibleMoves = listOfMoves.size();

		// then
		assertTrue(numberOfPossibleMoves == 5);
	}
	
	@Test
	public void testCheckKingPossibleMovesContainsMove() {
		// given
		Coordinate from = new Coordinate(4, 4);
		Coordinate to = new Coordinate(4, 5);
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface king = new King(Color.BLACK);
		board.setPieceAt(king, from);
		Move move = new Move(from, to, MoveType.ATTACK, king);
		
		//when
		List<Move> listOfMoves = king.getPossibleMoves(from, board);

		//then
		assertTrue(listOfMoves.contains(move));
	}
	
	@Test
	public void testCheckKingPossibleMovesNotContainsMove() {
		// given
		Coordinate from = new Coordinate(4, 4);
		Coordinate to = new Coordinate(6, 5);
		BoardManager boardManager = new BoardManager();
		Board board = boardManager.getBoard();
		PieceInterface king = new King(Color.WHITE);
		board.setPieceAt(king, from);
		Move move = new Move(from, to, MoveType.ATTACK, king);
		
		//when
		List<Move> listOfMoves = king.getPossibleMoves(from, board);

		//then
		assertFalse(listOfMoves.contains(move));
	}

}
