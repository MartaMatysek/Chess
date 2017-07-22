package com.capgemini.chess.algorithms.pieces;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.data.Move;

public class King extends MoveValidator implements PieceInterface {

	private Color color;
	private PieceType pieceType;

	public King(Color color) {
		this.color = color;
		this.pieceType = PieceType.KING;
	}

	public List<Move> getPossibleMoves(Coordinate from, Board board) {
		List<Move> possibleMoves = new ArrayList<Move>();
		PieceInterface pieceFrom = board.getPieceAt(from);

		int startRow = from.getX();
		int startColumn = from.getY();

		for (int currentRow = -1; currentRow < 2; currentRow++) {
			for (int currentColumn = -1; currentColumn < 2; currentColumn++) {
				Coordinate to = new Coordinate(startRow + currentRow, startColumn + currentColumn);
				if (isInBoard(to) && !from.equals(to)) {
					if (isFinishPositionFree(to, board)) {
						Move move = new Move(from, to, MoveType.ATTACK, pieceFrom);
						possibleMoves.add(move);
					} else {
						PieceInterface pieceTo = board.getPieceAt(to);
						if (isFinishPositionCanCapture(pieceFrom, pieceTo)) {
							Move move = new Move(from, to, MoveType.CAPTURE, pieceFrom);
							possibleMoves.add(move);
						}
					}
				}
			}
		}
		
		if (from.getX() == 4 && from.getY() == 0 && this.color == Color.WHITE) {
			possibleMoves.addAll(getCastlingMovesIfIsPossibleForWhite(from, board, possibleMoves));
		}

		
		if (from.getX() == 4 && from.getY() == 7 && this.color == Color.BLACK) {
			possibleMoves.addAll(getCastlingMovesIfIsPossibleForBlack(from, board, possibleMoves));
		}

		return possibleMoves;
	}
	
	private List<Move> getCastlingMovesIfIsPossibleForWhite(Coordinate from, Board board, List<Move> possibleMoves) {
		List<Move> movesList = board.getMoveHistory();
		boolean isValid = true;

		PieceInterface RookWhiteLeft = board.getPieceAt(new Coordinate(0, 0));
		PieceInterface RookWhiteRight = board.getPieceAt(new Coordinate(7, 0));

		if (RookWhiteLeft != null && RookWhiteLeft.equals(new Rook(Color.WHITE))) {
			for (Move tempMove : movesList) {
				if (tempMove.getMovedPiece().equals(new King(this.color)) || tempMove.getMovedPiece().equals(RookWhiteLeft)) {
					isValid = false;
				}
			}

			if (isValid == true) {
				for (int row = 1; row < from.getX(); row++) {
					if (board.getPieceAt(new Coordinate(row, 0)) != null) {
						isValid = false;
					}
				}
				if (isValid == true) {
					Move move = new Move(from, new Coordinate(2, 0), MoveType.CASTLING, new King(Color.WHITE));
					possibleMoves.add(move);
				}
			}
		}

		if (RookWhiteRight != null && RookWhiteRight.equals(new Rook(Color.WHITE))) {
			isValid = true;

			for (Move tempmove : movesList) {
				if (tempmove.getMovedPiece().equals(new King(this.color)) || tempmove.getMovedPiece().equals(RookWhiteRight)) {
					isValid = false;
				}
			}

			if (isValid == true) {
				for (int row = from.getX() + 1; row < 7; row++) {
					if (board.getPieceAt(new Coordinate(row, 0)) != null) {
						isValid = false;
					}
				}
				if (isValid == true) {
					Move move = new Move(from, new Coordinate(6, 0), MoveType.CASTLING, new King(Color.WHITE));
					possibleMoves.add(move);
				}
			}
		}
		return possibleMoves;
	}

	private List<Move> getCastlingMovesIfIsPossibleForBlack(Coordinate from, Board board, List<Move> possibleMoves) {
		List<Move> movesList = board.getMoveHistory();
		boolean isValid = true;

		PieceInterface RookBlackLeft = board.getPieceAt(new Coordinate(0, 7));
		PieceInterface RookBlackRight = board.getPieceAt(new Coordinate(7, 7));

		if (RookBlackLeft != null && RookBlackLeft.equals(new Rook(Color.BLACK))) {
			isValid = true;

			for (Move tempmove : movesList) {
				if (tempmove.getMovedPiece().equals(new King(this.color)) || tempmove.getMovedPiece().equals(RookBlackLeft)) {
					isValid = false;
				}
			}

			if (isValid == true) {
				for (int row = 1; row < from.getX(); row++) {
					if (board.getPieceAt(new Coordinate(row, 7)) != null) {
						isValid = false;
					}
				}
				if (isValid == true) {
					Move move = new Move(from, new Coordinate(2, 7), MoveType.CASTLING, new King(Color.BLACK));
					possibleMoves.add(move);
				}
			}
		}

		if (RookBlackRight != null && RookBlackRight.equals(new Rook(Color.BLACK))) {
			isValid = true;

			for (Move tempmove : movesList) {
				if (tempmove.getMovedPiece().equals(new King(this.color)) || tempmove.getMovedPiece().equals(RookBlackRight)) {
					isValid = false;
				}
			}

			if (isValid == true) {
				for (int row = from.getX() + 1; row < 7; row++) {
					if (board.getPieceAt(new Coordinate(row, 7)) != null) {
						isValid = false;
					}
				}
				if (isValid == true) {
					Move move = new Move(from, new Coordinate(6, 7), MoveType.CASTLING, new King(Color.BLACK));
					possibleMoves.add(move);
				}
			}
		}
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
		King other = (King) obj;
		if (color != other.color)
			return false;
		if (pieceType != other.pieceType)
			return false;
		return true;
	}
}
