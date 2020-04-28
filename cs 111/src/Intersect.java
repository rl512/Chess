
public class Intersect {

	public static void main(String[] args) {
		
		System.out.println("Enter the constant d");  
		int d=IO.readInt(); 
		System.out.println("Enter the constant f");   
		int f=IO.readInt(); 
		System.out.println("Enter the constant g");  
		int g=IO.readInt(); 
		System.out.println("Enter the constant m"); 
		int m=IO.readInt(); 
		System.out.println("Enter the constant b");   
		int b=IO.readInt(); 
		
		int B=f-m; 
		int C=g-b; 
		int Bsquared=B*B; 
		int fourac=4*d*C; 
		int sqrtunder=Bsquared-fourac;  
		
		if (sqrtunder<0) { 
			
			System.out.println("the intersection does not exist"); 
			return; 
		}  
		
		if (d==0) { 
			
			System.out.println("the intersection does not exist");  
			return; 
		}
		
		int negateB=B*-1; 
		double sqrt=Math.sqrt(sqrtunder); 
		int a=2*d; 
		double numeratorpositive=negateB+sqrt; 
		double numeratornegative=negateB-sqrt; 
		double x1=numeratorpositive/a; 
		double x2=numeratornegative/a; 
		double y1=(m*x1)+b;  
		double x2squared=x2*x2; 
		double y2first=x2squared*d; 
		double y2second=f*x2;  
		double y2=y2first+y2second+g;  
		
		if (x1==-0.0 || x2==-0.0 || y1==-0.0 || y2==-0.0) {  
			
			if (x1==-0.0) { 
				
				x1=x1*-1; 
			} 
			
			else if (x2==-0.0) { 
				
				x2=x2*-1; 
			}  
			
			else if (y1==-0.0) { 
				
				y1=y1*-1; 
			}  
			
			else { 
				
				y2=y2*-1; 
			}
	
		}
		
		if (x1==x2) { 
			
			System.out.println("The intersections is:"); 
			System.out.println("("+x1+","+y1+")"); 
		} 
		
		else {
		
			System.out.println("The intersections are:"); 
			System.out.println("("+x1+","+y1+")"); 
			System.out.println("("+x2+","+y2+")"); 
		
		}

	}

}
