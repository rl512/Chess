
public class Scores {

	public static void main(String[] args) { 
		
		System.out.println("Enter the number of judges"); 
		
		int judges=IO.readInt(); 
		
		while (judges<3) { 
			
			IO.reportBadInput();   
			System.out.println("Invalid number of judges. please enter the number of judges again"); 
			judges=IO.readInt(); 
			
		} 
		
		int count=0;  
		double score=0.0;  
		double max=0.0; 
		double min=0.0;  
		double sum=0.0; 
		double average=0.0; 
		
		while (count<judges) { 
			
			score=IO.readDouble(); 
			
			while (score<0.0 || score>10.0) { 
				
				IO.reportBadInput(); 
				System.out.println("Invalid score. Please enter a different score again");   
				score=IO.readDouble(); 
			} 
			
			if (count==0) { 
				
				min=score; 
			} 
			if (count==1) { 
				
				if (score<min) { 
					
					max=min; 
					min=score; 
				}
				
				else { 
				   	
					max=score; 
				}
			} 
			
			if (count>1) {  
				
				if (max==min) { 
					
					if (score>max) { 
						
						sum=sum+max;  
						max=score; 
					} 
					if (score<min) { 
						
						sum=sum+min; 
						min=score; 
					}
				}
				
				else if (score>max) { 
					
					sum=sum+max;  
					max=score; 
				} 
				
				else if (score<min) { 
					
					sum=sum+min; 
					min=score; 
				} 
				
				else if (score==max || score==min) {  
					
					if (score==max) { 
						
						sum=sum+max; 
						max=score; 
					} 
					if (score==min) { 
						
						sum=sum+min; 
						min=score; 
					}
					
				}  
				
				else { 
					
					sum=sum+score; 
				}	
			}

			count++; 
		} 
		
		average=sum/(judges-2); 
		IO.outputDoubleAnswer(average);
	}

}
