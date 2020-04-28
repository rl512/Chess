
public class Poly {

	public static void main(String[] args) {
		
		System.out.println("Enter the first root"); 
		int first=IO.readInt(); 
		
		System.out.println("Enter the second root"); 
		int second=IO.readInt(); 
		
		System.out.println("Enter the third root"); 
		int third=IO.readInt(); 
		
		first=first*-1; 
		second=second*-1; 
		third=third*-1; 
		
		int sum1=second+first; 
		int multiply1=first*second; 
		int multiply2=sum1*third; 
		int constant=multiply1*third; //constant   
		int X=multiply2+multiply1; //x  
		int xsquared=third+sum1;  //x^2 
		
		
		System.out.println("The polynomial is:");  
		
		if (xsquared>0 && X>0 && constant>0) { 
			
			System.out.println("X^3+"+xsquared+"X^2+"+X+"X+"+constant);  
			System.out.println("a"); 
		} 
		
		else if (xsquared<0 && X>0 && constant>0) { 
			
			System.out.println("X^3"+xsquared+"X^2+"+X+"X+"+constant); 
			System.out.println("b"); 
		} 
		
		else if (xsquared<0 && X<0 && constant>0) { 
			
			System.out.println("X^3"+xsquared+"X^2"+X+"X+"+constant);  
			System.out.println("c"); 
		}   
		
		else if (xsquared<0 && X>0 && constant<0) { 
			
			System.out.println("X^3"+xsquared+"X^2+"+X+"X"+constant); 
			System.out.println("c"); 
		}   
		
		else if (xsquared>0 && X<0 && constant<0) { 
			
			System.out.println("X^3+"+xsquared+"X^2"+X+"X"+constant);  
			System.out.println("d"); 
		}   
		
		else if (xsquared>0 && X>0 && constant<0) { 
			
			System.out.println("X^3+"+xsquared+"X^2+"+X+"X"+constant);  
			System.out.println("e"); 
		}   
		
		else if (xsquared<0 && X<0 && constant>0) { 
			
			System.out.println("X^3"+xsquared+"X^2"+X+"X+"+constant); 
			System.out.println("f"); 
		}   
		
		else if (xsquared==0 && X>0 && constant>0) { 
			
			System.out.println("X^3+"+X+"X+"+constant);  
			System.out.println("g"); 
		} 
		
		else if (xsquared>0 && X==0 && constant>0) { 
			
			System.out.println("X^3+"+xsquared+"X^2+"+constant);  
			System.out.println("h"); 
		} 
		
		else if (xsquared>0 && X>0 && constant==0) { 
			
			System.out.println("X^3+"+xsquared+"X^2+"+X+"X"); 
			System.out.println("i"); 
		} 
		
		else if (xsquared==0 && X<0 && constant>0) { // editing 
			
			System.out.println("X^3"+X+"X+"+constant); 
			System.out.println("j"); 
		}   
		
		else if (xsquared==0 && X>0 && constant==0) { 
			
			System.out.println("X^3+"+X+"X^2"); 
			System.out.println("l"); 
		}  
		
		else if (xsquared==0 && X==0 && constant>0) { 
			
			System.out.println(constant); 
			System.out.println("m"); 
		}  
		
		else if (xsquared>0 && X==0 && constant==0) { 
			
			System.out.println("X^3+"+xsquared+"X^2"); 
			System.out.println("n"); 
		}  
		
		else if (xsquared==0 && X>0 && constant==0) { 
			
			System.out.println("X^3+"+X+"X"); 
			System.out.println("o"); 
		}  
		
		else if (xsquared>0 && X==0 && constant>0) { 
			
			System.out.println("X^3"+xsquared+"X^2"+constant); 
			System.out.println("p"); 
		}   
		
		else if (xsquared>0 && X==0 && constant>0) { 
			
			System.out.println("X^3+"+xsquared+"X^2+"+constant); 
			System.out.println("q"); 
		}   
		
		
		else if (xsquared<0 && X==0 && constant==0) { 
			
			System.out.println("X^3"+xsquared+"X^2"); 
			System.out.println("r"); 
		}   
		
		else if (xsquared==0 && X<0 && constant==0) { 
			
			System.out.println("X^3"+X+"X"); 
			System.out.println("s"); 
		}   
		
		
		else if (xsquared==0 && X==0 && constant<0) { 
			
			System.out.println("X^3"+constant); 
			System.out.println("t"); 
		}   
		
		else if (xsquared==0 && X<0 && constant<0) { 
			
			System.out.println("X^3"+X+"X"+constant); 
			System.out.println("u"); 
		}   
		
		else if (xsquared==0 && X<0 && constant<0) { 
			
			System.out.println("X^3"+X+"X"+constant); 
			System.out.println("v"); 
		}   
		
		else if (xsquared<0 && X<0 && constant==0) { 
			
			System.out.println("X^3"+xsquared+"X^2"+X+"X"); 
			System.out.println("w"); 
		}   
		
		else if (xsquared<0 && X==0 && constant<0) { 
			
			System.out.println("X^3"+xsquared+"X^2"+constant); 
			System.out.println("x"); 
		}  
		
		else if (xsquared==0 && X==0 && constant==0) { 
			
			System.out.println("X^3"); 
			System.out.println("y"); 
		}   
		
		else if (xsquared<0 && X>0 && constant==0) { 
			
			System.out.println("X^3"+xsquared+"X^2+"+X+"X"); 
			System.out.println("Z"); 
		} 
		
		else if (xsquared>0 && X<0 && constant==0) { 
			
			System.out.println("X^3+"+xsquared+"X^2"+X+"X"); 
			System.out.println("y"); 
		}  
		
		else if (xsquared>0 && X==0 && constant<0) { 
			
			System.out.println("X^3+"+xsquared+"X^2"+constant); 
			System.out.println("aa"); 
		}  
		
		else if (xsquared==0 && X>0 && constant<0) { 
			
			System.out.println("X^3+"+X+"X"+constant); 
			System.out.println("ab"); 
		}   
		
		else if (xsquared<0 && X==0 && constant>0) { 
			
			System.out.println("X^3"+xsquared+"X^2+"+constant); 
			System.out.println("ac"); 
		}  
		  
		else {  // all negative 
			
			System.out.println("X^3"+xsquared+"X^2"+X+"X"+constant);  
			System.out.println("z"); 
		} 
		
	}

}
