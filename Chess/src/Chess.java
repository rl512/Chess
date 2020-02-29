
import java.util.Scanner;

public class Chess {    
	
	
	static Board chessboard [][]=new Board[8][8];  
	static String alphabetrank[]= {"a","b","c","d","e","f","g","h"};  
	static int numberrank[]={8,7,6,5,4,3,2,1}; 
	static Chesspiece rookblk=new Chesspiece("B","R"); 
	static Chesspiece knightblk=new Chesspiece("B","k");  
	static Chesspiece bishopblk=new Chesspiece ("B","B");  
	static Chesspiece kingblk=new Chesspiece ("B","K");  
	static Chesspiece queenblk=new Chesspiece ("B","Q"); 
	static Chesspiece pawnblk=new Chesspiece ("B","P");  
	static Chesspiece pawnwht=new Chesspiece ("W","P");  
	static Chesspiece rookwht=new Chesspiece ("W","R"); 
	static Chesspiece knightwht=new Chesspiece ("W","k");  
	static Chesspiece bishopwht=new Chesspiece ("W","B");  
	static Chesspiece queenwht=new Chesspiece ("W","Q");  
	static Chesspiece kingwht=new Chesspiece ("W","K");  
	
	public static void main(String[] args) {
		
		int count=0; 
		boolean illegal;  
		boolean quit=true;  
		boolean draw=false;   
		boolean white=true; // signals white turn, especially when white turn is done   
		boolean gameover=false; 
		boolean black=true; // signals black turn, when white is done    
		boolean whtmove=false; // signals white player has entered a move  
		boolean whtdraw=false;  // signals weather player white wants to draw 
		Scanner input; 
		Scanner whitedraw; 
		
		while (quit) { // 
			
			//white turn  
			while (white) {
				
				System.out.println("Player for white, please enter a move or resign");  
				input=new Scanner (System.in);  
				String whtinput=input.nextLine();   
				
				if (whtinput.equalsIgnoreCase("resign")) { 
					
					System.out.println("Black wins!");  
					gameover=true; 
					black=false;  
					break; 
					
				} 
				
				if (whtinput.length()==5) { // white player did indeed input a move
					
					whtmove=true;  
					//check for illegal move here 
				} 
				
				if (whtmove) { 
					
					System.out.println("Do you want to draw?. Enter true for yes and false for no"); 
					whitedraw=new Scanner (System.in);   
					String whtdrawresult=whitedraw.nextLine();  
					
					while (!whtdrawresult.equalsIgnoreCase("yes"))  { // if user does not enter a yes 
						
						System.out.println("Error. Invalid response, please enter again"); 
						whitedraw=new Scanner (System.in); 
						whtdrawresult=whitedraw.nextLine(); 
					}
					
					while (!whtdrawresult.equalsIgnoreCase("no"))  { // if user does not enter a no 
						
						System.out.println("Error. Invalid response, please enter again"); 
						whitedraw=new Scanner (System.in); 
						whtdrawresult=whitedraw.nextLine();  
					} 
					
					if (whtdrawresult.equalsIgnoreCase("yes")) { 
						
						whtdraw=true; 
					} 
					
				}
			
			} 
			// black turn 
			while (black)  { 
				
				System.out.println("Player for black, please enter a move or resign");  
				input=new Scanner (System.in);  
				String blkinput=input.nextLine();  
				
				if (blkinput.equalsIgnoreCase("resign")) { 
					
					System.out.println("White wins!");  
					gameover=true; 
					
				}
				
			} 
			
			if (gameover) {  // if game ends because we have declared a winner 
				
				quit=false;
			}
			 
	}
	printboard();
	}  
	
	// prints the original starting board 
	public static void printboard () {    
		
	int numbers=8; 
	
	for (int a=0; a<8; a++) { 
		for (int b=0; b<8; b++) {    
			if (a==0) { // creates the first row of the board 
				if (b==0 || b==7) {   
								
					if (b%2==0) { 
						chessboard[a][b]=new Board(rookblk,'W',alphabetrank[b]+numberrank[a]); 
					} 
									
					else { 
						chessboard[a][b]=new Board(rookblk,'B',alphabetrank[b]+numberrank[a]);  
					}
				} 
								
				else if (b==1 || b==6) {  
					if (b%2!=0) { 
						chessboard[a][b]=new Board(knightblk,'B',alphabetrank[b]+numberrank[a]); 
					} 
									
					else { 
						chessboard[a][b]=new Board(knightblk,'W',alphabetrank[b]+numberrank[a]);
					}
				} 
				else if (b==2 || b==5) {  
				   if (b%2==0) {
					   chessboard[a][b]=new Board(bishopblk,'W',alphabetrank[b]+numberrank[a]); 
				   } 
									
				   else if (b%2!=0) { 
					   chessboard[a][b]=new Board(bishopblk,'B',alphabetrank[b]+numberrank[a]);
				   }
				} 
				else if (b==3) { 
					chessboard[a][b]=new Board(queenblk,'B',alphabetrank[b]+numberrank[a]);
				} 
								
				else if (b==4) { 
					chessboard[a][b]=new Board(kingblk,'B',alphabetrank[b]+numberrank[a]);
				}
			}
						 
			if (a==1 || a==6) {  // sets up the black and white ponds 
				if (a==1) { 
					
					if (b%2==0) { 
						chessboard[a][b]=new Board(pawnblk,'B',alphabetrank[b]+numberrank[a]);
					} 
							
					else { 
						chessboard[a][b]=new Board(pawnblk,'W',alphabetrank[b]+numberrank[a]);
					} 
				} 
				else { 
								
					if (b%2==0) { 
						chessboard[a][b]=new Board(pawnwht,'W',alphabetrank[b]+numberrank[a]);
					} 
					else { 
						chessboard[a][b]=new Board(pawnwht,'B',alphabetrank[b]+numberrank[a]);
					} 
				}
			} 
						
			if (a==7) { // complete the last row of the board 
				if (b==0|| b==7) { // complete the white rook
								
					if (b==0) { 
						chessboard[a][b]=new Board(rookwht,'B',alphabetrank[b]+numberrank[a]);
					} 
								
					else { 
						chessboard[a][b]=new Board(rookwht,'W',alphabetrank[b]+numberrank[a]);
					}
				} 
							
				if (b==1 || b==6) { 
								
					if (b==1) {  
						chessboard[a][b]=new Board(knightwht,'W',alphabetrank[b]+numberrank[a]);
					} 
								
					else { 
						chessboard[a][b]=new Board(knightwht,'B',alphabetrank[b]+numberrank[a]);
					}
				} 
							
				if (b==2 || b==5) {  
								
					if (b==2) {
						chessboard[a][b]=new Board(bishopwht,'B',alphabetrank[b]+numberrank[a]); 
					} 
								
					else { 
						chessboard[a][b]=new Board(bishopwht,'W',alphabetrank[b]+numberrank[a]); 
					}
				} 
							
				if (b==3) { 
					chessboard[a][b]=new Board(queenwht,'W',alphabetrank[b]+numberrank[a]); 
				} 
				if (b==4)  { 
					chessboard[a][b]=new Board(kingwht,'B',alphabetrank[b]+numberrank[a]); 
				}
			} 
						
			if (a>1 && a<6) { 
				if (a%2==0) {  // even a=odd b is black  
					if (b%2!=0) {  // odd need to be black! 
						chessboard[a][b]=new Board(null,'B',alphabetrank[b]+numberrank[a]); 
					} 
								
					else { 
						chessboard[a][b]=new Board(null,'W',alphabetrank[b]+numberrank[a]); 
					}
				} 
							
				else {  // odd a= even b is black 
								
					if (b%2==0) { 
						chessboard[a][b]=new Board(null,'B',alphabetrank[b]+numberrank[a]); 
					} 
								
					else { 
						chessboard[a][b]=new Board(null,'W',alphabetrank[b]+numberrank[a]); 
					}
				}
			}
		} 
	} 
	
	// print the board 
				
	for (int c=0; c<8; c++) { 
		for (int d=0; d<8; d++) { 
			if (c==0 || c==1 || c==6 || c==7) {  
				if (d==0) { // print the numbers on left side of board 
					System.out.print(numbers+" ");
				}
				System.out.print(chessboard[c][d].getPiece().getColor()+chessboard[c][d].getPiece().getType()+" ");   
				
				if (d==7) { // print the numbers on the right side of board 
					System.out.print(" "+numbers);
				}
			} 
			else {  
				
				if (d==0) { // print the numbers on the left side of this board 
					System.out.print(numbers+" ");
				}
				if (chessboard[c][d].getColor()=='B') { 
					System.out.print ("## ");  
				} 
				if (chessboard[c][d].getColor()!='B') { 
					System.out.print("   ");
				} 
				if (d==7) { // print the numbers on the right side of board 
					System.out.print(" "+numbers);
				}
			}
		} 
		numbers--; 
		System.out.println(); 
	} 
	
	// print the alphabet on the bottom of this board  
	
	System.out.println("  a  b  c  d  e  f  g  h");
} 

}





