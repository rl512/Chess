
public class TwoSmallest {

	public static void main(String[] args) { 
	 
		System.out.println("Enter a terminating number"); 
		double terminating=IO.readDouble(); 
		
		System.out.println("Enter a number"); 
		double num=IO.readDouble(); 
	
		while (num==terminating) { 
			
			IO.reportBadInput(); 
			System.out.println("enter a term again. Bad input(5)"); 
			terminating=IO.readDouble();  
			System.out.println("Enter a number again (10"); 
			num=IO.readDouble();  
		}    
		
		int count=0;  
		double smallest=num; 
		double smaller=0.0;  
		double holder=0.0; 
		
		while (num!=terminating) {    
			
			if (count==0) { 
			
				System.out.println("Enter a num again(1)"); 
			
				num=IO.readDouble(); 
			}
			
			if (num==terminating && count==0) {  
				
				while (num==terminating) { 
					IO.reportBadInput(); 
					System.out.println("Invalid term(1). Enter a term again. Bad input"); 
					terminating=IO.readDouble(); 
				} 
			
				System.out.println("Enter a num again(3)"); 
				num=IO.readDouble();  
				
				while (num==terminating) { 
					IO.reportBadInput(); 
					System.out.println("Invalid num(1). Enter a num again. Bad input"); 
					num=IO.readDouble(); 
				} 
				
				smallest=num;  
				
				System.out.println("Enter a num again(4)"); 
				num=IO.readDouble();  
				
				while (num==terminating) { 
					IO.reportBadInput(); 
					System.out.println("Invalid num(1). Enter a num again. Bad input"); 
					num=IO.readDouble(); 
				} 
			}  
			
			if (count==0) { 
				
				smaller=num;  
				
				if (smallest>smaller) { 
					
					holder=smaller; 
					smaller=smallest; 
					smallest=holder; 
				}
			} 
			
			if (count>0) { 
				
				if (smaller==smallest) { 
					
					if (num<smaller) { 
						
						smallest=num;  
					}   
				} 
				
				else if (num<smallest) {  
					
					smaller=smallest; 
					smallest=num;   
				} 
				
				else if (num<smaller) { 
					
					smaller=num;  
				} 
				
				else if (num==smallest) { 
					
					smaller=smallest; 
					smallest=num; 
				}	
			}
			
		System.out.println("Enter a num again(2)"); 
		num=IO.readDouble();  
		count++; 
		} 
		
		IO.outputDoubleAnswer(smallest); 
		IO.outputDoubleAnswer(smaller);

	}

}
