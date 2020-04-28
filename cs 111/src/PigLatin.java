
public class PigLatin {

	public static void main(String[] args) {
		
		System.out.println("enter a word"); 
		String word=IO.readString();  
		System.out.println(translate(word)); 

	} 
	
	public static String translate (String original) { 
		
		String first=original.substring(0, 1);  
		
		if (first.equalsIgnoreCase("a") || first.equalsIgnoreCase("e")|| first.equalsIgnoreCase("i") || first.equalsIgnoreCase("o") || first.equalsIgnoreCase("u") ) { 
			
			original=original+"way";  
			return original; 
		} 
		
		else { 
			
			original=original.substring(1)+first+"ay"; 
			return original; 
		}
	}

}
