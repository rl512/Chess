
public class WordCount {

	public static void main(String[] args) {
		
		System.out.println("enter a sentence"); 
		String sentence=IO.readString();   
		System.out.println("enter a min number"); 
		int number=IO.readInt(); 
		int countwords=countWords(sentence,number);  
		System.out.println(countwords); 

	} 
	
	public static int countWords (String original, int minLength) {  
		
		int isletter=0;  
		int countword=0; 
		
		for (int i=0; i<original.length(); i++) {  
			
			//System.out.println("c");
			
			if (original.substring(i, i+1).equals(" ")) {  
				
				System.out.println("a"); 
			
				if (isletter>=minLength) {  
					
				System.out.println("b"); 
					
					countword++; 
				} 
				isletter=0; 
				//i8990., ,./;;'; 678ilobe .//.,crazy69
				
			}
			
			if (Character.isLetter(original.charAt(i))) {  
				
				//System.out.println(original.charAt(i));
				
				isletter++;  
				
				if (i==original.length()-1) { 
					
					if (isletter>=minLength) { 
						
						countword++; 
					}
				}
				
				System.out.println(isletter); 
			}
		} 
		
		return countword; 
	}

}
