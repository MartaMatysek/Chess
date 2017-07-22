package com.capgemini.chess.algorithms.data;

import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.pieces.PieceInterface;

/**
 * Chess move definition.
 * 
 * @author Michal Bejm
 *
 */
public class Move {

	private Coordinate from;
	private Coordinate to;
	private MoveType type;
	private PieceInterface movedPiece;
	
	public Move(){}
	
	public Move( Coordinate from,  Coordinate to, MoveType type, PieceInterface movedPiece){
		this.from = from;
		this.to = to;
		this.type = type;
		this.movedPiece = movedPiece;
	}

	public Coordinate getFrom() {
		return from;
	}

	public void setFrom(Coordinate from) {
		this.from = from;
	}

	public Coordinate getTo() {
		return to;
	}

	public void setTo(Coordinate to) {
		this.to = to;
	}

	public MoveType getType() {
		return type;
	}

	public void setType(MoveType type) {
		this.type = type;
	}

	public PieceInterface getMovedPiece() {
		return movedPiece;
	}

	public void setMovedPiece(PieceInterface movedPiece) {
		this.movedPiece = movedPiece;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((movedPiece == null) ? 0 : movedPiece.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Move other = (Move) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (movedPiece == null) {
			if (other.movedPiece != null)
				return false;
		} else if (!movedPiece.equals(other.movedPiece))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (type != other.type)
			return false;
		return true;
	}	
}
