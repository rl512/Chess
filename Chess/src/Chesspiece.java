
public class Chesspiece { 
	
	String type; 
	String color;  
	
	public Chesspiece (String color, String type) { 
		
		this.color=color;
		this.type=type;
	}

	public String getType() { 
		
		return type;
	}

	public void setType(String type) { 
		
		this.type = type;
	}

	public String getColor() { 
		
		return color;
	}

	public void setColor(String color) { 
		
		this.color = color;
	} 
	
	

}
