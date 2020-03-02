
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
		boolean whiteillegal;  // check to see if white made an illegal move 
		boolean blackillegal; // check to see if black made an illegal move 
		boolean quit=true;  
		boolean draw=false;   
		boolean white=false; // signals white turn, especially when white turn is done   
		boolean gameover=false; 
		boolean black=false; // signals black turn, when white is done    
		boolean whtmove=false; // signals white player has entered a move  
		boolean whtdraw=false;  // signals weather player white wants to draw 
		Scanner input; 
		Scanner whitedraw;  
	
		while (quit) { //  
			
			white=true; 
			
			//white turn  
			while (white) {
				
				System.out.println("Player for white, please enter a move or resign");  
				input=new Scanner (System.in);  
				String whtinput=input.nextLine();   
				
				if (whtinput.equalsIgnoreCase("resign")) { 
					
					System.out.println("Black wins!");  
					gameover=true; 
					black=false;  
					white=false; 
					break; 
					
				} 
				
				if (whtinput.length()==5) { // white player did indeed input a move 
					
					whiteillegal=illegalmove(black,white,count,whtinput); 
					
					if (whiteillegal) { 
						whtmove=true;
					}   
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
			
			black=true;  
			white=false; 
			
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
			white=true; 
			
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
	
	public static boolean illegalmove (boolean blackturn, boolean whiteturn, int count, String input) { 
		
		char oldposnum=input.charAt(1); // extract the old position row from String input 
		char newposnum=input.charAt(4); // extract the new position row from String input 
		String oldpos=input.substring(0, 2); // Extract the old position overall String 
		String newpos=input.substring(3,4)+input.charAt(4); // Extract the 
		int oldposrow=Integer.parseInt(String.valueOf(oldposnum)); // translate the Char old position number into an integer  
		int newposrow=Integer.parseInt(String.valueOf(newposnum));  // translate the Char new position numbr into an integer 
		char oldposcol=input.charAt(0); // extract the Char character column from the String  
		char newposcol=input.charAt(3); // extract char character column from String input   
		int oldposindexcol = 0; // translate old position column character 
		int newposindexcol = 0;  // translate new position column character  
		Chesspiece oldpiece; // position on chessboard of oldpiece  
		Chesspiece newpiece; // new position where the player wants to move their piece  
		String oldcolor;  // color of old chesspiece  
		String newcolor; // color of new chesspiece  
		boolean pawcheck; //check illegal move of pawn if needed 
		
		if (Character.isUpperCase(oldposcol)) { //Checks to see if the oldpositioncolumn is uppercase 
			
			if (oldposcol>'H') {  // player has selected an column out of range  
				return true; 
			} 
			
			else { // translate each old character column uppercase into its respective oldindexcolumn 
				
				if (oldposcol=='A') { 
					oldposindexcol=0; 
				} 
				else if (oldposcol=='B') { 
					oldposindexcol=1; 
				}  
				else if (oldposcol=='C') { 
					oldposindexcol=2; 
				}  
				else if (oldposcol=='D') { 
					oldposindexcol=3; 
				}  
				else if (oldposcol=='E') { 
					oldposindexcol=4; 
				}  
				else if (oldposcol=='F') { 
					oldposindexcol=5; 
				}  
				else if (oldposcol=='G') { 
					oldposindexcol=6; 
				}  
				else { 
					oldposindexcol=7; 
				} 
			}
		} 
		

		if (!Character.isUpperCase(oldposcol)) { // checks to see if the oldpositionrow is lowercase 
			
			if (oldposcol>'h') {  // player has selected an column out of range  
				return true; 
			} 
			
			else { // translate each old character column lowercase into its respective oldndexcolumn 
				
				if (oldposcol=='a') { 
					oldposindexcol=0; 
				} 
				else if (oldposcol=='b') { 
					oldposindexcol=1; 
				}  
				else if (oldposcol=='c') { 
					oldposindexcol=2; 
				}  
				else if (oldposcol=='d') { 
					oldposindexcol=3; 
				}  
				else if (oldposcol=='e') { 
					oldposindexcol=4; 
				}  
				else if (oldposcol=='f') { 
					oldposindexcol=5; 
				}  
				else if (oldposcol=='g') { 
					oldposindexcol=6; 
				}  
				else { 
					oldposindexcol=7; 
				} 
			}
		}  
		
		if (Character.isUpperCase(newposcol)) { //Checks to see if the newpositioncolumn is uppercase 
			
			if (newposcol>'H') {  // player has selected an column out of range  
				return true; 
			} 
			
			else { // translate each old character column uppercase into its respective oldindexcolumn 
				
				if (newposcol=='A') { 
					newposindexcol=0; 
				} 
				else if (newposcol=='B') { 
					newposindexcol=1; 
				}  
				else if (newposcol=='C') { 
					newposindexcol=2; 
				}  
				else if (newposcol=='D') { 
					newposindexcol=3; 
				}  
				else if (newposcol=='E') { 
					newposindexcol=4; 
				}  
				else if (newposcol=='F') { 
					newposindexcol=5; 
				}  
				else if (newposcol=='G') { 
					newposindexcol=6; 
				}  
				else { 
					newposindexcol=7; 
				} 
			}
		}  
		
		if (!Character.isUpperCase(newposcol)) { // checks to see if the newpositionrow is lowercase 
			
			if (newposcol>'h') {  // player has selected an column out of range  
				return true; 
			} 
			
			else { // translate each old character column lowercase into its respective oldndexcolumn 
				
				if (newposcol=='a') { 
					newposindexcol=0; 
				} 
				else if (newposcol=='b') { 
					newposindexcol=1; 
				}  
				else if (newposcol=='c') { 
					newposindexcol=2; 
				}  
				else if (newposcol=='d') { 
					newposindexcol=3; 
				}  
				else if (newposcol=='e') { 
					newposindexcol=4; 
				}  
				else if (newposcol=='f') { 
					newposindexcol=5; 
				}  
				else if (newposcol=='g') { 
					newposindexcol=6; 
				}  
				else { 
					newposindexcol=7; 
				} 
			}
		}  
		
		if (oldpos.equalsIgnoreCase(newpos)) { // if the player has input the same 
			return true; 
		}   
		
		if (oldposrow<1 || oldposrow>8) { // player has selected an old position out of range 
			System.out.println("White player has selected an old position out of the board"); 
			return true; 
		} 
		
		if (newposnum<1 || newposnum>8) {  // player has selected a new position out of range 
			System.out.println("White player has selected an new position out of the board"); 
			return true; 	
		}   
		
		if (oldposrow>=1 && oldposrow<=8 ) { // transform the oldposrow from the input into its corresponding array row index  
			oldposrow=8-oldposrow; 
		} 
		
		if (newposrow >=1 && newposrow<=8) { // tranform the newposrow from the input into its corresponding arrao row index 
			newposrow=8-newposrow; 
		} 
		
		oldpiece=chessboard[oldposrow][oldposindexcol].getPiece();    
		newpiece=chessboard[newposrow][newposindexcol].getPiece();  
		
		
		if (oldpiece==null) { // player has selected a piece that does not exist  
			return true; 
		} 
		oldcolor=oldpiece.getColor(); // get the old position Chesspiece color 
		
		if (whiteturn) { 
			
			if (oldcolor.equalsIgnoreCase("B")) {  // white player attempting to move their opponent player 
				return true; 
			}
		}  
		
		if (blackturn) { 
			
			if (oldcolor.equalsIgnoreCase("W")) { // black player attempting to move their opponent player 
				return true; 
			}
		}
		
		
		if (newpiece!=null) { //there is a chesspiece in the new position . 
			
			newcolor=newpiece.getColor(); // get newchesspiece color 
			
			if (oldcolor.equalsIgnoreCase(newcolor)) { // player is attempting to to take their own opponent. 
				
				return true; 
			}
		} 
		
		if (oldpiece.getType().equalsIgnoreCase("P")) { // check for illegal move of pawn 
			
			pawcheck=illegalcheckpawn(oldposrow, oldposindexcol, newposrow, newposindexcol, count);  
			
			if (pawcheck) { 
				return true; 
			}
		}
		
		return false;
	} 
	
	public static boolean illegalcheckpawn (int oldposrow, int oldposindexcol, int newposrow, int newposindexcol, int count) { 
		
		//pawns are allowed to move up the same file twice as long as its the first move  
		int checkpieceindex=0; //check to see if there are any chesspiece ahead of pond by one space. 
		int difference=0; //difference between newpiece and oldpiece 
		
		difference=newposrow-oldposrow;  
		
		if (difference>2) { // if a player attempets to move more than two spots  
			return true; 
		}
		
		else if (difference==2) { 
			
			if (count!=0) { // player attempts to move two spaces after at least one turn after game started.  
				return true; 
			} 
			else {//count is 0 
				
				if (oldposindexcol!=newposindexcol) { //player attempts to move two spaces to another file which is not allowed 
					return true; 
				}
			}
		}
		
		return false;
	}
}





