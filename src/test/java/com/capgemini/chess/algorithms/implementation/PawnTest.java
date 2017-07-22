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

public class PawnTest {

	
	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidPawnWhiteAttack() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(4, 6));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boardManager.performMove(new Coordinate(4, 1), new Coordinate(4, 3));
		
		// then
		fail("This method should throw InvalidMoveException");
	}
	
	@Test
	public void testPerformMoveEnPassantForBlackPawn() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(new King(Color.WHITE), new Coordinate(7, 0));
		board.setPieceAt(new King(Color.BLACK), new Coordinate(7, 7));
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(1, 1));
		board.setPieceAt(new Pawn(Color.BLACK), new Coordinate(0, 3));
		BoardManager boardManager = new BoardManager(board);
		
		// when
		boardManager.performMove(new Coordinate(1, 1), new Coordinate(1, 3));
		Move move = boardManager.performMove(new Coordinate(0, 3), new Coordinate(1, 2));
		
		// then
		assertEquals(MoveType.EN_PASSANT, move.getType());
		assertEquals(new Pawn(Color.BLACK), move.getMovedPiece());
	}
	
	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidEnPassantForBlackPawn() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(new King(Color.WHITE), new Coordinate(2, 1));
		board.setPieceAt(new King(Color.BLACK), new Coordinate(7, 7));
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(1, 1));
		board.setPieceAt(new Pawn(Color.BLACK), new Coordinate(2, 3));
		BoardManager boardManager = new BoardManager(board);
		
		// when
		boardManager.performMove(new Coordinate(1, 1), new Coordinate(1, 3));
		boardManager.performMove(new Coordinate(0, 3), new Coordinate(1, 2));
		
		// then
		fail("This method should InvalidMoveException");
	}
	
	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidPawnBackwardBlackMove() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(4, 2));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boardManager.performMove(new Coordinate(4, 2), new Coordinate(4, 1));
		
		// then 
		fail("This method should throw InvalidMoveException");
	}
	
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
	
	private Move createDummyMove(Board board) {
		
		Move move = new Move();
		
		if (board.getMoveHistory().size() % 2 == 0) {
			board.setPieceAt(new Rook(Color.WHITE), new Coordinate(0, 0));
			move.setMovedPiece(new Rook(Color.WHITE));
		}
		else {
			board.setPieceAt(new Rook(Color.BLACK), new Coordinate(0, 0));
			move.setMovedPiece(new Rook(Color.BLACK));
		}
		move.setFrom(new Coordinate(0, 0));
		move.setTo(new Coordinate(0, 0));
		move.setType(MoveType.ATTACK);
		board.setPieceAt(null, new Coordinate(0, 0));
		return move;
	}

}
