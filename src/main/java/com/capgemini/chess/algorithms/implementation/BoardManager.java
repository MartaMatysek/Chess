package com.capgemini.chess.algorithms.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.BoardState;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
import com.capgemini.chess.algorithms.implementation.exceptions.KingInCheckException;
import com.capgemini.chess.algorithms.pieces.Bishop;
import com.capgemini.chess.algorithms.pieces.King;
import com.capgemini.chess.algorithms.pieces.Knight;
import com.capgemini.chess.algorithms.pieces.MoveValidator;
import com.capgemini.chess.algorithms.pieces.Pawn;
import com.capgemini.chess.algorithms.pieces.PieceInterface;
import com.capgemini.chess.algorithms.pieces.Queen;
import com.capgemini.chess.algorithms.pieces.Rook;



/**
 * Class for managing of basic operations on the Chess Board.
 *
 * @author Michal Bejm
 *
 */
public class BoardManager {

	private Board board = new Board();

	public BoardManager() {
		initBoard();
	}

	public BoardManager(List<Move> moves) {
		initBoard();
		for (Move move : moves) {
			addMove(move);
		}
	}

	public BoardManager(Board board) {
		this.board = board;
	}

	/**
	 * Getter for generated board
	 *
	 * @return board
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Performs move of the chess piece on the chess board from one field to
	 * another.
	 *
	 * @param from
	 *            coordinates of 'from' field
	 * @param to
	 *            coordinates of 'to' field
	 * @return move object which includes moved piece and move type
	 * @throws InvalidMoveException
	 *             in case move is not valid
	 */
	public Move performMove(Coordinate from, Coordinate to) throws InvalidMoveException {
		getExceptionForInvalidateMove(from, to);

		Move move = validateMove(from, to);
		addMove(move);
		return move;
	}

	/**
	 * Calculates state of the chess board.
	 *
	 * @return state of the chess board
	 * @throws InvalidMoveException 
	 */
	public BoardState updateBoardState() throws InvalidMoveException {

		Color nextMoveColor = calculateNextMoveColor();

		boolean isKingInCheck = isKingInCheck(nextMoveColor);
		boolean isAnyMoveValid = isAnyMoveValid(nextMoveColor);

		BoardState boardState;
		if (isKingInCheck) {
			if (isAnyMoveValid) {
				boardState = BoardState.CHECK;
			} else {
				boardState = BoardState.CHECK_MATE;
			}
		}
		else {
			if (isAnyMoveValid) {
				boardState = BoardState.REGULAR;
			} else {
				boardState = BoardState.STALE_MATE;
			}
		}
		this.board.setState(boardState);
		return boardState;
	}

	/**
	 * Checks threefold repetition rule (one of the conditions to end the chess
	 * game with a draw).
	 *
	 * @return true if current state repeated at list two times, false otherwise
	 */
	public boolean checkThreefoldRepetitionRule() {

		// there is no need to check moves that where before last capture/en
		// passant/castling
		int lastNonAttackMoveIndex = findLastNonAttackMoveIndex();
		List<Move> omittedMoves = this.board.getMoveHistory().subList(0, lastNonAttackMoveIndex);
		BoardManager simulatedBoardManager = new BoardManager(omittedMoves);

		int counter = 0;
		for (int i = lastNonAttackMoveIndex; i < this.board.getMoveHistory().size(); i++) {
			Move moveToAdd = this.board.getMoveHistory().get(i);
			simulatedBoardManager.addMove(moveToAdd);
			boolean areBoardsEqual = Arrays.deepEquals(this.board.getPieces(),
					simulatedBoardManager.getBoard().getPieces());
			if (areBoardsEqual) {
				counter++;
			}
		}

		return counter >= 2;
	}

	/**
	 * Checks 50-move rule (one of the conditions to end the chess game with a
	 * draw).
	 *
	 * @return true if no pawn was moved or not capture was performed during
	 *         last 50 moves, false otherwise
	 */
	public boolean checkFiftyMoveRule() {

		// for this purpose a "move" consists of a player completing his turn
		// followed by his opponent completing his turn
		if (this.board.getMoveHistory().size() < 100) {
			return false;
		}

		for (int i = this.board.getMoveHistory().size() - 1; i >= this.board.getMoveHistory().size() - 100; i--) {
			Move currentMove = this.board.getMoveHistory().get(i);
			PieceInterface currentPieceType = currentMove.getMovedPiece();
			if (currentMove.getType() != MoveType.ATTACK || currentPieceType.getPieceType() == PieceType.PAWN) {
				return false;
			}
		}

		return true;
	}

	// PRIVATE
	
	private void getExceptionForInvalidateMove(Coordinate from, Coordinate to) throws InvalidMoveException {
		MoveValidator moveValidator = new MoveValidator();

		if(!moveValidator.isInBoard(from)){
			throw new InvalidMoveException("Start coordinate is out of bound.");
		}
		
		if(!moveValidator.isInBoard(to)){
			throw new InvalidMoveException("End coordinate is out of bound.");
		}
		
		if(board.getPieceAt(from)== null){
			throw new InvalidMoveException("You have to choose start coordinate.");
		}

		if(board.getMoveHistory().size() == 0 && board.getPieceAt(from).getColor() == Color.BLACK){
			throw new InvalidMoveException("You can't start playing with black figure");
		}
	}

	private void initBoard() {
		
		this.board.setPieceAt(new Rook(Color.BLACK), new Coordinate(0, 7));
		this.board.setPieceAt(new Knight(Color.BLACK), new Coordinate(1, 7));
		this.board.setPieceAt(new Bishop(Color.BLACK), new Coordinate(2, 7));
		this.board.setPieceAt(new Queen(Color.BLACK), new Coordinate(3, 7));
		this.board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 7));
		this.board.setPieceAt(new Bishop(Color.BLACK), new Coordinate(5, 7));
		this.board.setPieceAt(new Knight(Color.BLACK), new Coordinate(6, 7));
		this.board.setPieceAt(new Rook(Color.BLACK) , new Coordinate(7, 7));

		for (int x = 0; x < Board.SIZE; x++) {
			this.board.setPieceAt(new Pawn(Color.BLACK), new Coordinate(x, 6));
		}
		
		this.board.setPieceAt(new Rook(Color.WHITE), new Coordinate(0, 0));
		this.board.setPieceAt(new Knight(Color.WHITE), new Coordinate(1, 0));
		this.board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(2, 0));
		this.board.setPieceAt(new Queen(Color.WHITE), new Coordinate(3, 0));
		this.board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		this.board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(5, 0));
		this.board.setPieceAt(new Knight(Color.WHITE), new Coordinate(6, 0));
		this.board.setPieceAt(new Rook(Color.WHITE), new Coordinate(7, 0));

		for (int x = 0; x < Board.SIZE; x++) {
			this.board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(x, 1));
		}
	}

	private void addMove(Move move) {

		addRegularMove(move);

		if (move.getType() == MoveType.CASTLING) {
			addCastling(move);
		} else if (move.getType() == MoveType.EN_PASSANT) {
			addEnPassant(move);
		}

		this.board.getMoveHistory().add(move);
	}

	private void addRegularMove(Move move) {
		PieceInterface movedPiece = this.board.getPieceAt(move.getFrom());
		this.board.setPieceAt(null, move.getFrom());
		this.board.setPieceAt(movedPiece, move.getTo());

		performPromotion(move, movedPiece);
	}

	private void performPromotion(Move move, PieceInterface movedPiece) {
		if (movedPiece instanceof Pawn && ((Pawn) movedPiece).getColor() == Color.WHITE 
			&& move.getTo().getY() == (Board.SIZE - 1)) {
			this.board.setPieceAt(new Queen(Color.WHITE), move.getTo());
		}
		if (movedPiece instanceof Pawn && ((Pawn) movedPiece).getColor() == Color.BLACK
			&& move.getTo().getY() == 0) {
			this.board.setPieceAt(new Queen(Color.BLACK), move.getTo());
		}
	}

	private void addCastling(Move move){
		if (move.getFrom().getX() > move.getTo().getX()) {
			PieceInterface rook = this.board.getPieceAt(new Coordinate(0, move.getFrom().getY()));
			this.board.setPieceAt(null, new Coordinate(0, move.getFrom().getY()));
			this.board.setPieceAt(rook, new Coordinate(move.getTo().getX() + 1, move.getTo().getY()));
		} else {
			PieceInterface rook = this.board.getPieceAt(new Coordinate(Board.SIZE - 1, move.getFrom().getY()));
			this.board.setPieceAt(null, new Coordinate(Board.SIZE - 1, move.getFrom().getY()));
			this.board.setPieceAt(rook, new Coordinate(move.getTo().getX() - 1, move.getTo().getY()));
		}
	}

	private void addEnPassant(Move move) {
		Move lastMove = this.board.getMoveHistory().get(this.board.getMoveHistory().size() - 1);
		this.board.setPieceAt(null, lastMove.getTo());
	}
	
	private Move validateMove(Coordinate from, Coordinate to) throws InvalidMoveException, KingInCheckException {	
		PieceInterface pieceFrom = board.getPieceAt(from);
		PieceInterface pieceTo = board.getPieceAt(to);
		List<Move> possibleMoves = pieceFrom.getPossibleMoves(from, board);
		Move move = getMoveIfIsInTheList(from, to, possibleMoves);

		if(checkPlanningNextMoveIsValidate(from, to)){
			board.setPieceAt(pieceFrom, from);
			board.setPieceAt(pieceTo, to);
			return move;			
		}
		else{
			return null;
		}
	}
	
	private Move getMoveIfIsInTheList(Coordinate from, Coordinate to, List<Move> moves) throws InvalidMoveException{
		boolean isValid = false;
		for(Move move: moves){
			if( from.equals(move.getFrom()) && to.equals(move.getTo())){
				isValid = true;
				return move;
			}
		}
		
		if(isValid == false ){
			throw new InvalidMoveException("This coordinate is not on the list !");
		}
		
		return null;
	}
	
	private boolean checkPlanningNextMoveIsValidate(Coordinate from, Coordinate to) throws KingInCheckException{
		PieceInterface pieceFrom = board.getPieceAt(from);
		PieceInterface pieceTo = board.getPieceAt(to);
		
		board.setPieceAt(null, from);
		board.setPieceAt(pieceFrom, to);

		if (isKingInCheck(pieceFrom.getColor())) {
			board.setPieceAt(pieceFrom, from);
			board.setPieceAt(pieceTo, to);
			throw new KingInCheckException("Inpossible move! King will be in check!");
		}
		
		return true;
	}
	
	private  boolean isKingInCheck(Color kingColor) {
		List<Move> moves = new ArrayList<Move>();
		Coordinate kingPosition = null;
		MoveValidator moveValid = new MoveValidator();
		
		for(int row = 0; row < Board.SIZE ; row++){
			for(int columns = 0; columns < Board.SIZE ; columns++){
				Coordinate coordinate = new Coordinate(row, columns);
				if(!moveValid.isFinishPositionFree(coordinate, board)){
					PieceInterface piece = board.getPieceAt(coordinate);
					if(piece.getColor() != kingColor){
						moves.addAll(piece.getPossibleMoves(coordinate, board));
					}
					else if(piece.getColor() == kingColor && piece.getPieceType() == PieceType.KING){
						 kingPosition = new Coordinate(coordinate.getX(),coordinate.getY());
					}
				}
			}
		}
		
		for (Move move : moves) {
			if (move.getTo().getX() == kingPosition.getX() && move.getTo().getY() == kingPosition.getY()){	
				return true;
			}
		}
		
		return false;
	}

	private boolean isAnyMoveValid(Color nextMoveColor) throws InvalidMoveException{
		List<Move> validateMoves =  findValidateMovesForColorOnTheChessboard(nextMoveColor);
		List<Move> finalList = new ArrayList<>();
		
		for(Move move: validateMoves){
			try{
				validateMove(move.getFrom(), move.getTo());	
				finalList.add(move);
			}
			catch(InvalidMoveException e) {
				e.getMessage();
			}
		}
		
		if(finalList.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
	
	private List<Move> findValidateMovesForColorOnTheChessboard(Color nextMoveColor){
		List<Move> validateMoves =  new ArrayList<Move>();
		
		for(int row = 0; row < Board.SIZE; row++){
			for(int column = 0; column < Board.SIZE; column++){
				PieceInterface piece = board.getPieceAt(new Coordinate(row, column));
				if(piece != null && piece.getColor().equals(nextMoveColor) ){
					validateMoves.addAll(piece.getPossibleMoves(new Coordinate(row, column), board));
				}
			}
		}
		return validateMoves;
	}
	
	private Color calculateNextMoveColor() {
		if (this.board.getMoveHistory().size() % 2 == 0) {
			return Color.WHITE;
		} else {
			return Color.BLACK;
		}
	}

	private int findLastNonAttackMoveIndex() {
		int counter = 0;
		int lastNonAttackMoveIndex = 0;

		for (Move move : this.board.getMoveHistory()) {
			if (move.getType() != MoveType.ATTACK) {
				lastNonAttackMoveIndex = counter;
			}
			counter++;
		}

		return lastNonAttackMoveIndex;
	}

}
