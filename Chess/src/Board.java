
public class Board { 
	
	Chesspiece piece; 
	char color; 
	String rank; 
	
	public Board (Chesspiece piece, char color, String rank) {  
		
		this.piece=piece; 
		this.color=color; 
		this.rank=rank; 
	}

	public Chesspiece getPiece() { 
		
		return piece;
	}

	public void setPiece(Chesspiece piece) { 
		
		this.piece = piece;
	}

	public char getColor() { 
		
		return color;
	}

	public void setColor(char color) { 
		
		this.color = color;
	}

	public String getRank() { 
		
		return rank;
	}

	public void setRank(String rank) { 
		
		this.rank = rank;
	} 
	
	
}
