
public class Prime {
	
	public static void main(String[] args) {
		
		int number=IO.readInt();  
		
		while (number<=0) { 
			
			IO.reportBadInput(); 
			System.out.println("please enter another prime number"); 
			number=IO.readInt(); 
		} 
		
		int div=0; 
		int prime=0; 
		int nthnum=0; 
		
		for (int i=2; ; i++) { 
			
			nthnum=i; 
			
			for (int j=i; j>0; j--) { 
				
				if (i%j==0) { 
					
					div++; 
				}
			} 
			
			if (div<3) { 
				
				prime++; 
			}
			
			if (prime==number) { 
				
				break; 
			} 
			
			div=0; 
			
		} 
		
		IO.outputIntAnswer(nthnum);
	
	}

}
