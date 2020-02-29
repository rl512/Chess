
import java.util.Scanner;

public class Test {

	public static void main(String[] args) { 
		
		System.out.println("enter code"); 
		
		Scanner sc=new Scanner (System.in);  
		
		String recieve=sc.nextLine();  
		
		String first; 
		
		String second;  
		
		first=recieve.substring(0,2);  
		
		second=recieve.substring(3,4)+recieve.charAt(4);
		
		System.out.println(first);   
		
		System.out.println(second);

	}

}
