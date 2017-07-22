package com.capgemini.chess.algorithms.pieces;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;

public class Pawn extends MoveValidator implements PieceInterface {

	private Color color;
	private PieceType pieceType;

	public Pawn(Color color) {
		this.color = color;
		this.pieceType = PieceType.PAWN;
	}

	public List<Move> getPossibleMoves(Coordinate from, Board board) {
		List<Move> possibleMoves = new ArrayList<Move>();
		PieceInterface pieceFrom = board.getPieceAt(from);

		int[][] direction = new int[4][2];
		if (pieceFrom.getColor() == Color.WHITE) {
			direction = movesDirectionForWhite(from.getX(), from.getY());
		}
		if (pieceFrom.getColor() == Color.BLACK) {
			direction = movesDirectionForBlack(from.getX(), from.getY());
		}
		
		for (int numberOfDirection = 0; numberOfDirection < 4; numberOfDirection++) {
			Coordinate to = new Coordinate(direction[numberOfDirection][0], direction[numberOfDirection][1]);
			if (numberOfDirection == 0 && isInBoard(to) && isFinishPositionFree(to, board)) {
				Move move = new Move(from, to, MoveType.ATTACK, board.getPieceAt(from));
				possibleMoves.add(move);
			}
			if (numberOfDirection == 1 && isFinishPositionFree(to, board) && checkPositionForAttackIsCorrect(this.color, from.getY())){
				Move move = new Move(from, to, MoveType.ATTACK, board.getPieceAt(from));
				possibleMoves.add(move);
			}
			if ((numberOfDirection == 2 || numberOfDirection == 3) && isInBoard(to) && isFinishPositionFree(to, board)) {
				PieceInterface pieceTo = board.getPieceAt(to);
				if (isFinishPositionCanCapture(pieceFrom, pieceTo)) {
					Move move = new Move(from, to, MoveType.CAPTURE, board.getPieceAt(from));
					possibleMoves.add(move);
				}
			}
		}
		
		if((this.color == Color.WHITE && from.getY() == 4) || (this.color == Color.BLACK && from.getY() == 3)){
				possibleMoves.addAll(makeEnPassantIfIsPossible(from, board));
		}
		

		return possibleMoves;
	}

	private int[][] movesDirectionForWhite(int startRow, int startColumn){
		int[][] direction = { {startRow, startColumn + 1}, {startRow, startColumn + 2},
							  {startRow + 1, startColumn + 1}, {startRow - 1, startColumn + 1 } };
		return direction;
	}
	
	private int[][] movesDirectionForBlack(int startRow, int startColumn){
		int[][] direction = { { startRow, startColumn - 1 }, { startRow, startColumn - 2 },
							  { startRow + 1, startColumn - 1 }, { startRow - 1, startColumn - 1 } };
		return direction;
	}
	
	private boolean checkPositionForAttackIsCorrect(Color color, int fromY){
		return ((color == Color.WHITE && fromY == 1) || (color == Color.BLACK && fromY == 6));
	}

	private List<Move> makeEnPassantIfIsPossible(Coordinate from, Board board) {
		List<Move> possibleMoves = new ArrayList<Move>();
		List<Move> historyMove = board.getMoveHistory();
		Move lastMove = historyMove.get(historyMove.size() - 1);
		
		int fromX = from.getX();
		int fromY = from.getY();

		Coordinate lastMoveFrom = lastMove.getFrom();
		Coordinate lastMoveTo = lastMove.getTo();
		PieceInterface typeLastPiece = lastMove.getMovedPiece();
		Color colorLastPiece = lastMove.getMovedPiece().getColor();

		if (colorLastPiece != this.color && typeLastPiece.getPieceType() == PieceType.PAWN && fromY == lastMoveTo.getY()){
			
			if (this.color == Color.WHITE && fromY + 2 == lastMoveFrom.getY()) {
				if (fromX + 1 == lastMoveFrom.getX() && fromX + 1 == lastMoveTo.getX()
					&& board.getPieceAt(new Coordinate(fromX + 1, fromY + 1)) == null){
					Move newMove = new Move(from, new Coordinate(fromX + 1, fromY + 1), MoveType.EN_PASSANT, new Pawn(Color.WHITE));
					possibleMoves.add(newMove);
				} 
				if (fromX - 1 == lastMoveFrom.getX() && fromX - 1 == lastMoveTo.getX()
					&& board.getPieceAt(new Coordinate(fromX - 1, fromY + 1)) == null) {
						Move newMove = new Move(from, new Coordinate(fromX - 1, fromY + 1), MoveType.EN_PASSANT,new Pawn(Color.WHITE));
						possibleMoves.add(newMove);
				}

			}
			else if (this.color == Color.BLACK && fromY - 2 == lastMoveFrom.getY()) {
				if (fromX + 1 == lastMoveFrom.getX() && fromX + 1 == lastMoveTo.getX()
					&& board.getPieceAt(new Coordinate(fromX + 1, fromY - 1)) == null){
					Move newMove = new Move(from, new Coordinate(fromX + 1, fromY - 1), MoveType.EN_PASSANT, new Pawn(Color.BLACK));
					possibleMoves.add(newMove);
				}
				if (fromX - 1 == lastMoveFrom.getX() && fromX - 1 == lastMoveTo.getX()
					&& board.getPieceAt(new Coordinate(fromX - 1, fromY - 1)) == null){
						Move newMove = new Move(from, new Coordinate(fromX - 1, fromY - 1), MoveType.EN_PASSANT, new Pawn(Color.BLACK));
						possibleMoves.add(newMove);
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
		Pawn other = (Pawn) obj;
		if (color != other.color)
			return false;
		if (pieceType != other.pieceType)
			return false;
		return true;
	}

}
