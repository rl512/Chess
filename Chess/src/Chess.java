
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
		boolean pawcheck=false; //check illegal move of pawn if needed  
		boolean rookcheck=false; //check illegal move of rook if needed  
		boolean bishopcheck=false; //check illegal bishop if needed  
		boolean queencheck=false; // check illegal move of queen 
		boolean kingcheck=false; // check illegal move of king 
		boolean knightcheck=false; // check illegal move of knight 
		
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
			
			if (whiteturn) { //white's turn to check 
				pawcheck=illegalcheckwhitepawn(oldposrow, oldposindexcol, newposrow, newposindexcol, count);  
				
				if (pawcheck) {  // if a white player make an illegal pawn move 
					return true; 
				} 
				else { 
					return false; 
				}
			} 
			
			else { // black's turn to check 
				
				pawcheck=illegalcheckblackpawn(oldposrow, oldposindexcol, newposrow, newposindexcol, count);  
				
				if (pawcheck) {  // if a black player made an illegal pawn move 
					return true; 
				} 
				else { 
					return false;
				}
			}
		} 
		
		if (oldpiece.getType().equalsIgnoreCase("R")) { // check the illegal move of rook  {
			
			rookcheck=illegalrook(oldposrow, oldposindexcol, newposrow, newposindexcol, count); 
			
			if (rookcheck) { 
				return true; 
			} 
			else { 
				return false;
			}
		}  
		
		if (oldpiece.getType().equalsIgnoreCase("B")) { // check the illegal move of bishop  {
			
			bishopcheck=illegalbishop(oldposrow, oldposindexcol, newposrow, newposindexcol, count); 
			
			if (bishopcheck) { 
				return true; 
			} 
			else { 
				return false;
			}
		}  
		
		if (oldpiece.getType().equalsIgnoreCase("Q")) { // check the illegal move of Queen  {
			
			queencheck=illegalqueen(oldposrow, oldposindexcol, newposrow, newposindexcol, count); 
			
			if (queencheck) { 
				return true; 
			} 
			else { 
				return false;
			}
		}  
		
		if (oldpiece.getType().equalsIgnoreCase("K")) { // check the illegal move of King  {
			
			kingcheck=illegalbishop(oldposrow, oldposindexcol, newposrow, newposindexcol, count); 
			
			if (kingcheck) { 
				return true; 
			} 
			else { 
				return false;
			}
		} 
		
		if (oldpiece.getType().equalsIgnoreCase("k")) { // check the illegal move of bishop  {
			
			knightcheck=illegalbishop(oldposrow, oldposindexcol, newposrow, newposindexcol, count); 
			
			if (knightcheck) { 
				return true; 
			} 
			else { 
				return false; 
			}
		} 
		return false;
	} 
	
	public static boolean illegalcheckwhitepawn (int oldposrow, int oldposindexcol, int newposrow, int newposindexcol, int count) { 
		
		//pawns are allowed to move up the same file twice as long as its the first move  
		int checkpieceindex=0; //check to see if there are any chesspiece ahead of pond by one space. 
		int differencerows=0; //difference between newpiece rows and oldpiece rows for testing  
		int differencecolumns=0; // difference between newpiece columns and oldpiece 
		int checking=0; //checking to see the piece in front of pawn has a piece or not on the first try. 
		 
		
		differencerows=newposrow-oldposrow;  
		differencecolumns=newposindexcol-oldposindexcol; 
		
		if (differencerows<-2 || differencerows>-1) { // if a player attempets to move more than two spots  
			return true; 
		} 
		
		if (differencecolumns>1 || differencecolumns<-1) { // if a player attemps to move a a pawn diagonal more then two spots 

			return true; 
		}
		
		if (differencerows==-2) {  
			checking=oldposrow-1; 
			
			if (oldposrow!=6) { // player attempts to move two spaces after at least one turn after game started.  
				return true; 
			}  
			
			if (differencecolumns!=0) { //player attemps to move two spaces diagonal=illegal 
				return true; 
			}
			else {//count is 0 which means player's first attempt  
				
				if (chessboard[checking][oldposindexcol].getPiece()!=null) { // if there is a piece in one space in from of pawn 
					return true; 
				}
			}
		} 
		
		if (differencerows==-1) { // white player moves forward one space or diagonal 
			
			if (differencecolumns!=0) { // white player does not move forward by one space 
				
				if (chessboard[newposrow][newposindexcol].getPiece()!=null) { // white player moves a diagonal spot with no piece in possession=illegal 
					return true; 
				}
			}
		}
		
		return false;
	} 
	
	public static boolean illegalcheckblackpawn (int oldposrow, int oldposindexcol, int newposrow, int newposindexcol, int count) { 
		
		int columndifference=0; //difference between old column position and new column position  
		int rowsdifference=0; //difference between   
		columndifference=newposindexcol-oldposindexcol;  
		rowsdifference=oldposrow-newposrow;  
		
		if (rowsdifference<1 || rowsdifference>2) { 
			return true;  
		}  
		if (columndifference>2 || columndifference<-1) { 
			return true; 
		}  
		if (rowsdifference==2) {  
			
			if (oldposrow!=1) { 
				return true; 
			}
			
			if (oldposindexcol!=newposindexcol) { 
				return true; 
			} 
			if (chessboard[oldposrow+1][oldposindexcol].getPiece()!=null) { 
				return true; 
			}
		} 
		
		if (rowsdifference==1) { 
			
			if (columndifference<-1 ||columndifference>1) { 
				return true; 
			}  
			
			if (columndifference==-1 || columndifference==1) { 
				
				if (chessboard[newposrow][newposindexcol].getPiece()==null) { 
					return true; 
				}
			}  
		}
		return false;
	} 
	
	public static boolean illegalrook (int oldposrow, int oldposindexcol, int newposrow, int newposindexcol, int count) { 
		
		if (oldposrow!=newposrow) { 
			
			if (oldposindexcol!=newposindexcol) { 
				return true; 
			}
		} 
		
		// check the path to ensure there are no other pieces around  
		
		if (oldposrow==newposrow) { // player decides to move rook across 
			
			if (newposindexcol<oldposindexcol) { // moving to left of old position 
				
				for (int i=newposindexcol; i<oldposindexcol; i++) { // check to see if there are any pieces from new position to old position 
					
					if (chessboard[oldposrow][i].getPiece()!=null) { // if there is a piece in the path, illegal 
						
						return true; 
					}
				}
			} 
			
			else { // trying to move oldpiece to the right 
				
				for (int j=newposindexcol; j>oldposindexcol; j--) { // check to see if there are any pieces from new position to old position 
					
					if (chessboard[oldposrow][j].getPiece()!=null) { // if there is a piece in the path, illegal 
						
						return true; 
					}
				}
			}
		} 
		
		
		if (newposindexcol==oldposindexcol) { // player trying to move the rook up and down  
			
			if (newposrow<oldposrow) {  // rook  trying to move up
				
				for (int k=newposrow; k<oldposrow; k++) { // check to see if there are any pieces from new position to old position 
					
					if (chessboard[k][oldposindexcol].getPiece()!=null) { // if there is a piece in the path, illegal 
						
						return true; 
					} 
					
				}
			} 
			else { // rook trying to move down 
				
				for (int l=newposrow; l<oldposrow; l--) { // check to see if there are any pieces from new position to old position 
					
					if (chessboard[l][oldposindexcol].getPiece()!=null) { // if there is a piece in the path, illegal 
						
						return true; 
					} 
					
				}
			}
		}
		
		return false; 
	} 
	
	public static boolean illegalbishop (int oldposrow, int oldposindexcol, int newposrow, int newposindexcol, int count) { 
		
		int rowmove=0; //  difference between newpiece row and oldpiece row 
		int colmove=0;  // difference oldpiece column and newpiece column  
		int checkingrow=0; 
		int checkingcol=0; 
		
		
		rowmove=oldposrow-newposrow; // calculate the difference oldpiece row and newpiece row  
		colmove=oldposindexcol-newposindexcol; // calculate the difference between oldpiece column and newpiece column   
	
		rowmove=Math.abs(rowmove); 
		colmove=Math.abs(colmove);  
		
		if (rowmove!=colmove) { // check for diagonal 
			return true; 
		} 
		
		if (newposindexcol>oldposindexcol) { // diagonal happened on the right side of oldpiece  
			
			if (newposrow<oldposrow) {  
				checkingrow=oldposrow-1; 
				checkingcol=oldposindexcol+1;
				
				while (checkingrow!=newposrow+1 && checkingcol!=newposindexcol) { 
					
					if (chessboard[checkingrow][checkingcol].getPiece()!=null) { 
						return true; 
					} 
					
					checkingrow--; 
					checkingcol++;
				}
			} 
			
			else {  
				checkingrow=oldposrow+1; 
				checkingcol=oldposindexcol-1;
				
				while (checkingrow!=newposrow && checkingcol!=newposindexcol) { 
					
					if (chessboard[checkingrow][checkingcol].getPiece()!=null) { 
						return true; 
					} 
					
					checkingrow++; 
					checkingcol++;
				}

			}
		} 
		
		else { //diagonal happened on the left side of the oldpiece 
			
			if (newposrow<oldposrow) {  
				checkingrow=oldposrow-1; 
				checkingcol=oldposindexcol-1;
				
				while (checkingrow!=newposrow && checkingcol!=newposindexcol) { 
					
					if (chessboard[checkingrow][checkingcol].getPiece()!=null) { 
						return true; 
					} 
					
					checkingrow--; 
					checkingcol--;
				}
			} 
			
			else { 
				
				checkingrow=oldposrow+1; 
				checkingcol=oldposindexcol-1;
				while (checkingrow!=newposrow && checkingcol!=newposindexcol) { 
					
					if (chessboard[checkingrow][checkingcol].getPiece()!=null) { 
						return true; 
					} 
					
					checkingrow++; 
					checkingcol--;
				}
			}
			
		}
		
		return false; 
	} 
	
	public static boolean illegalqueen (int oldposrow, int oldposindexcol, int newposrow, int newposindexcol, int count) { 
		
		int checkcolumn=0;  
		int checkrows=0;  
		int diffrows=oldposrow-newposrow; 
		int diffcolumns=oldposindexcol-newposindexcol;  
		int checkdiagonalrows=0; 
		int checkdiagonalcols=0; 
		diffrows=Math.abs(diffrows); 
		diffcolumns=Math.abs(diffcolumns); 
		
		if (oldposrow==newposrow) { //  queen plans on moving across the board  
			
			if (newposrow<oldposrow) { //newposition is to the left of oldposition  
				checkcolumn=newposindexcol+1; 
				
				for (int m=checkcolumn; m<oldposindexcol; m++) { // check the path to see if there is another path 
					
					if (chessboard[oldposrow][checkcolumn].getPiece()!=null) { // there is a piece in the path 
						return true; 
					}  
				} 
				return false; 
			} 
			
			else {  // newpiece is to the right of oldpiece  
				checkcolumn=newposindexcol-1; 
				
				for (int n=checkcolumn; n>oldposindexcol; n--) { // check the path to see if there is another path 
					
					if (chessboard[oldposrow][n].getPiece()!=null) { // there is a piece in the path 
						return true; 
					}  
				} 
				return false; 
			}
		} 
		
		else if (oldposindexcol==newposindexcol) { //  queen moves up and down the board  
			 
				if (newposrow<oldposrow) {  
					checkrows=newposrow+1;  
					
					for (int o=checkrows; o<oldposrow; o++) { // check the path to see if there is another path 
					
						if (chessboard[o][oldposindexcol].getPiece()!=null) { // there is a piece in the path 
							return true; 
						}  
					} 
					return false; 
				} 
				else { 
					checkrows=newposrow-1; 
					
					for (int p=checkrows; p>oldposindexcol; p--) { // check the path to see if there is another path 
					
						if (chessboard[p][oldposindexcol].getPiece()!=null) { // there is a piece in the path 
							return true; 
						}  
					} 
					return false; 
				}
		
		} 
		// check for diagonals if there is one  
	
		else if (diffrows==diffcolumns) { // there is a diagonal 
			
			if (newposindexcol>oldposindexcol) { // diagonal to the right of oldpiece 
				
				if (newposrow<oldposrow) { //-+ for rows and columns. Diagonal to the right and up 
					checkdiagonalrows=oldposrow-1;  
					checkdiagonalcols=oldposindexcol+1;  
					
					while (checkdiagonalrows!=newposrow && checkdiagonalcols !=newposindexcol) { 
						
						if (chessboard[checkdiagonalrows][checkdiagonalcols].getPiece()!=null) { // there is a piece in the diagonal path 
							return true; 
						}    
						checkdiagonalrows--; 
						checkdiagonalcols++; 
					} 
					return false; 
				} 
				
				else { // ++ for rows and columns. Diagonal to the right and down 
					checkdiagonalrows=oldposrow+1;  
					checkdiagonalcols=oldposindexcol+1;  
					
					while (checkdiagonalrows!=newposrow && checkdiagonalcols !=newposindexcol) { 
						
						if (chessboard[checkdiagonalrows][checkdiagonalcols].getPiece()!=null) { // there is a piece in the diagonal path 
							return true; 
						}    
						checkdiagonalrows++; 
						checkdiagonalcols++; 
					} 
					return false; 
					
				}
			} 
			
			else { // diagonal to the left 
				checkdiagonalrows=oldposrow-1;  
				checkdiagonalcols=oldposindexcol-1;  
				
				if (newposindexcol<oldposindexcol) {// diagonal to the left and up 
				
					while (checkdiagonalrows!=newposrow && checkdiagonalcols !=newposindexcol) { 
					
						if (chessboard[checkdiagonalrows][checkdiagonalcols].getPiece()!=null) { // there is a piece in the diagonal path 
							return true; 
					}    
					checkdiagonalrows--; 
					checkdiagonalcols--; 
					} 
					return false; 
				} 
				
				else { // diagonal to the left and down , +- 
					checkdiagonalrows=oldposrow+1;  
					checkdiagonalcols=oldposindexcol-1;
					
					while (checkdiagonalrows!=newposrow && checkdiagonalcols !=newposindexcol) { 
						
						if (chessboard[checkdiagonalrows][checkdiagonalcols].getPiece()!=null) { // there is a piece in the diagonal path 
							return true; 
					}    
					checkdiagonalrows++; 
					checkdiagonalcols--; 
					} 
					return false; 
				}
			}
		} 
		
		else { // all other moves illegal 
			
			return true; 
		}
	} 
	
	
	public static boolean illegalking (int oldposrow, int oldposindexcol, int newposrow, int newposindexcol, int count) { 
		
		int differencerows=newposrow-oldposrow; // calculate the difference in rows  
		int differencecols=newposindexcol-oldposindexcol; // calculate the difference in columns  
		/* 
		 * calculate the difference in rows without negative 
		 */
		differencerows=Math.abs(differencerows); 
		differencecols=Math.abs(differencecols); 
		
		if (oldposrow==newposrow) { // king is moving horizontally 
			
			if (differencecols==1) { // king is moving one space 
				return false; 
				
			}  
			else { // king moving more than one space-illegal 
				return true; 
			}
		} 
		
		else if (oldposindexcol==newposindexcol) { // king is moving up and down 
			
			if (differencerows==1) { // king is moving one space 
				return false; 
			} 
			else { // king moving more than one space-illegal 
				return true; 
			}
		} 
		
		else if (differencerows==differencecols) {  // check if king moves diagonal 
			
			if (differencerows==1) { // king moves diagonally one space 
				return false; 
			} 
			else { 
				return true; 
			}
		} 
		else { // all other moves illegal 
			return true; 
		}
	} 
	
	public static boolean illegalknight (int oldposrow, int oldposindexcol, int newposrow, int newposindexcol, int count) { 
		
		int differencerows=newposrow-oldposrow; // calculate the difference in rows  
		int differencecols=newposindexcol-oldposindexcol; // calculate the difference in columns  
		/* 
		 * calculate the difference in rows without negative 
		 */
		differencerows=Math.abs(differencerows); 
		differencecols=Math.abs(differencecols); 
		
		if (oldposrow==newposrow) { // knight moves along a row -illegal 
			return true; 
		} 
		else if (oldposindexcol==newposindexcol) { // knight moves up and down-illegal 
			return true; 
		}  
		else if (differencerows==differencecols) { // knight moves diagonally-illegal  
			return true; 
		} 
		
		else { 
			
			if (differencerows>2) { // knight made an illegal move 
				return true; 
			} 
			else if (differencecols>2) { 
				return true; 
			}  
			else { 
				return false;
			}
		}
	} 
	
	/* 
	 * method used to check if a particular King is in check 
	 * After white turn-check black's king to ensure its not in check 
	 * After black turn-check white's king to ensure its not in check 
	 */
	public static boolean check (boolean white, boolean black, boolean checkcheckmate, int kingrow, int kingcol) { 
		
		boolean checkrookorqueen=false;  
		boolean checkpawn=false;  
		boolean checkbishop=false;    
		
		//check horizontal to see if there is a rook or queen   
		checkrookorqueen=horizontalverticalcheck(white,black,kingrow,kingcol);  
		//check pawn to see if it causes its opponent king to be in check  
		checkpawn=pawncheck(white,black,kingrow,kingcol); 
		//check bishop to see if it causes its opponent king to be in check 
		checkbishop=bishopcheck(white,black,kingrow,kingcol);  
		//check
		
		if (checkrookorqueen) { // if the rook  
			return true; 
		}  
		else if (checkpawn) { // if a king is in check by pawn 
			return true; 
		} 
		else if (checkbishop) { // if a king is in check by bishop 
			return true; 
		} 
		else if (checkrookorqueen) { //
			
		}
		
		
	}  
	
	/* 
	 * check for horizontal rows and vertical columns of king to see if there is a 
	 */
	
	public static boolean horizontalverticalcheck(boolean white, boolean black, int kingrow, int kingcol) { 
		
		if (white) { // white just made a move, got info on black king, need to see if there are white pieces   
			
			for (int e=kingcol+1; e<8; e++) { // check vertically to the right of king 
				
				if (chessboard[kingrow][e].getPiece()!=null) { // there is a chesspiece 
					
					if (chessboard[kingrow][e].getPiece().getColor().equalsIgnoreCase("W")) { // the piece is white 
						
						if (chessboard[kingrow][e].getPiece().getType().equalsIgnoreCase("Q")) {  // the piece is queen 
							return true; 
						} 
						
						else if (chessboard[kingrow][e].getPiece().getType().equalsIgnoreCase("R")) { // the piece is rook 
							return true; 
						}  
						else { 
							break;
						}
					
					}
				}
			} 
			
			for (int f=kingcol-1; f>=0; f--) { // check vertically to the left of king 
				
				if (chessboard[kingrow][f].getPiece()!=null) { // there is a chesspiece 
					
					if (chessboard[kingrow][f].getPiece().getColor().equalsIgnoreCase("W")) { // the piece is white 
						
						if (chessboard[kingrow][f].getPiece().getType().equalsIgnoreCase("Q")) {  // the piece is queen 
							return true; 
						} 
						
						else if (chessboard[kingrow][f].getPiece().getType().equalsIgnoreCase("R")) { // the piece is rook 
							return true; 
						}  
						else { 
							break; 
						}
					
					}
				}
			} 
			
			//check horizontally 
			
			for (int g=kingrow+1; g<8; g++) { // check for pond or queen below the king 
				
				if (chessboard[g][kingcol].getPiece()!=null) { // there is a chesspiece 
					
					if (chessboard[g][kingcol].getPiece().getColor().equalsIgnoreCase("W")) { // the piece is white
						
						if (chessboard[g][kingcol].getPiece().getType().equalsIgnoreCase("Q")) {  // the piece is queen 
							return true; 
						} 
						
						else if (chessboard[g][kingcol].getPiece().getType().equalsIgnoreCase("R")) { // the piece is rook 
							return true; 
						}  
						else { 
							break; 
						}
					
					}
				}
			} 
			
			for (int k=kingrow-1; k>=0; k--) { // check pond or queen above the king 
				
				if (chessboard[k][kingcol].getPiece()!=null) { // there is a chesspiece 
					
					if (chessboard[k][kingcol].getPiece().getColor().equalsIgnoreCase("W")) { // the piece is white 
						
						if (chessboard[k][kingcol].getPiece().getType().equalsIgnoreCase("Q")) {  // the piece is queen 
							return true; 
						} 
						
						else if (chessboard[k][kingcol].getPiece().getType().equalsIgnoreCase("R")) { // the piece is rook 
							return true; 
						}  
						else { 
							break; 
						}
					
					}
				}
			} 
			return false; 
		} 
		
		else { // black just moved, got white king's information, and now need to check for black ponds and queens 
			
			for (int l=kingcol+1; l<8; l++) { // check vertically to the right of king 
				
				if (chessboard[kingrow][l].getPiece()!=null) { // there is a chesspiece 
					
					if (chessboard[kingrow][l].getPiece().getColor().equalsIgnoreCase("B")) { // the piece is black
						
						if (chessboard[kingrow][l].getPiece().getType().equalsIgnoreCase("Q")) {  // the piece is queen 
							return true; 
						} 
						
						else if (chessboard[kingrow][l].getPiece().getType().equalsIgnoreCase("R")) { // the piece is rook 
							return true; 
						} 
						else { 
							break;
						}
					
					}
				}
			} 
			
			for (int m=kingcol-1; m>=0; m--) { // check vertically to the left of king 
				
				if (chessboard[kingrow][m].getPiece()!=null) { // there is a chesspiece 
					
					if (chessboard[kingrow][m].getPiece().getColor().equalsIgnoreCase("B")) { // the piece is black 
						
						if (chessboard[kingrow][m].getPiece().getType().equalsIgnoreCase("Q")) {  // the piece is queen 
							return true; 
						} 
						
						else if (chessboard[kingrow][m].getPiece().getType().equalsIgnoreCase("R")) { // the piece is rook 
							return true; 
						}  
						else { 
							break; 
						}
					
					}
				}
			} 
			
			//check horizontally 
			
			for (int n=kingrow+1; n<8; n++) { // check for pond or queen below the king 
				
				if (chessboard[n][kingcol].getPiece()!=null) { // there is a chesspiece 
					
					if (chessboard[n][kingcol].getPiece().getColor().equalsIgnoreCase("B")) { // the piece is black 
						
						if (chessboard[n][kingcol].getPiece().getType().equalsIgnoreCase("Q")) {  // the piece is queen 
							return true; 
						} 
						
						else if (chessboard[n][kingcol].getPiece().getType().equalsIgnoreCase("R")) { // the piece is rook 
							return true; 
						}  
						else { 
							break; 
						}
					
					}
				}
			} 
			
			for (int o=kingrow-1; o>=0; o--) { // check pond or queen above the king 
				
				if (chessboard[o][kingcol].getPiece()!=null) { // there is a chesspiece 
					
					if (chessboard[o][kingcol].getPiece().getColor().equalsIgnoreCase("B")) { // the piece is black 
						
						if (chessboard[o][kingcol].getPiece().getType().equalsIgnoreCase("Q")) {  // the piece is queen 
							return true; 
						} 
						
						else if (chessboard[o][kingcol].getPiece().getType().equalsIgnoreCase("R")) { // the piece is rook 
							return true; 
						}  
						else { 
							break; 
						}
					
					}
				}
			} 
			return false;
		} 
	} 
	/* 
	 * check for pawn-to see if a king is in check by pawn  
	 */
	public static boolean pawncheck (boolean white, boolean black, int kingrow, int kingcol) { 
		
		int checkrows=0; 
		int checkcols=0; // check cols for left diagonal
		int checkcols2=0; // check cols for right diagonal
		
		if (white) { // white player moved, got king info, need to check to if check will caused by white pawns 
			
			if (kingrow<7) {
			
				if (kingcol>0 && kingcol<7) {  
					checkrows=kingrow+1;  
					checkcols=kingcol-1;
				
					if (chessboard[checkrows][checkcols].getPiece()!=null) { // there is a piece 
						
						if (chessboard[checkrows][checkcols].getPiece().getColor().equalsIgnoreCase("W")) { // chesspiece is white
							
							if (chessboard[checkrows][checkcols].getPiece().getType().equalsIgnoreCase("P")) { // piece is pawn
								return true; 
							} 
							else { // piece is not a pawn. Check other diagonal   
								checkcols2=kingcol+1;  
								
								if (chessboard[checkrows][checkcols2].getPiece()!=null) { // there is a piece 
									
									if (chessboard[checkrows][checkcols2].getPiece().getColor().equalsIgnoreCase("W")) { //chesspiece is white 
										
										if (chessboard[checkrows][checkcols2].getPiece().getType().equalsIgnoreCase("P")) { // piece is a pawn 
											return true; 
										} 
										else { // some other white chesspiece, return false  
											return false; 
										}
									} 
									else { // color is black 
										return false; 
									}
								} 
								else { // there is no piece 
									return false; 
								}
							}
						} 
						
						else { // there is a black piece, check other diagonal  
							checkcols2=kingcol+1; 
							
							if (chessboard[checkrows][checkcols2].getPiece()!=null) { // there is a piece 
								
								if (chessboard[checkrows][checkcols2].getPiece().getColor().equalsIgnoreCase("W")) { // chesspiece is white 
									
									if(chessboard[checkrows][checkcols2].getPiece().getType().equalsIgnoreCase("P")) { // piece is a pawn 
										return true; 
									} 
									else { // other white chesspiece 
										return false; 
									}
								} 
								else { // black piece  
									return false; 
								}
							} 
							else { // other diagonal has no piece 
								return false; 
							}
						}
				
					} 
					else { // no piece in spot, check other diagonal  
						checkcols2=kingcol+1; 
						
						if (chessboard[checkrows][checkcols2].getPiece()!=null) { // there is a piece 
							
							if (chessboard[checkrows][checkcols2].getPiece().getColor().equalsIgnoreCase("W")) {// piece is white  
								
								if (chessboard[checkrows][checkcols2].getPiece().getType().equalsIgnoreCase("P")) { // piece is pawn 
									return true;
								} 
								else { // there are some other white pieces 
									return false; 
								}
							} 
							else {// chesspiece is black  
								return false; 
							}
						} 
						else { // there is no piece on other diagonal 
							return false; 
						}
					} 
				} 
				else if (kingcol==0) { // king is located on the leftmost column  
					checkrows=kingrow+1; 
					checkcols=kingcol+1;  
					
					if (chessboard[checkrows][checkcols].getPiece()!=null) { // there is a piece 
						
						if (chessboard[checkrows][checkcols].getPiece().getColor().contentEquals("W")) { // there is a white piece 
							
							if (chessboard[checkrows][checkcols].getPiece().getType().equalsIgnoreCase("P")) { // the piece is a pawn 
								return true; 
							} 
							else { // there is some other white piece
								return false; 
							}
						} 
						else { // the piece is black
							return false; 
						}
					} 
					else { // there is not piece present 
						return false; 
					}
				} 
				else { // king is located on the rightmost column  
					checkrows=kingrow+1; 
					checkcols=kingcol-1; 
					
					if (chessboard[checkrows][checkcols].getPiece()!=null) { 
						
						if (chessboard[checkrows][checkcols].getPiece().getColor().equalsIgnoreCase("W")) { 
							
							if (chessboard[checkrows][checkcols].getPiece().getType().equalsIgnoreCase("P")) { 
								return true; 
							} 
							else { // some other white piece-return false 
								return false; 
							}
						} 
						else { // piece is black  
							return false; 
						}
					} 
					else { // there is no piece 
						return false; 
					}
				}
				
			} 
			else { // black king is located at the last row-impossible for it to be check 
				return false; 
			} 
		} 
		
		else { // black player finish turn, got white king information, check black pawns to see if white king is in check  
			
			if (kingrow>1) { // 
				
				if (kingcol>0 && kingcol<7) { 
					checkrows=kingrow-1; 
					checkcols=kingcol-1;  
					
					if (chessboard[checkrows][checkcols].getPiece()!=null) { // there is a piece 
						
						if (chessboard[checkrows][checkcols].getPiece().getColor().equalsIgnoreCase("B")) { // the piece is black 
							
							if (chessboard[checkrows][checkcols].getPiece().getType().equalsIgnoreCase("P")) { // the piece is a pawn 
								return true; 
							} 
							else {  // there is some other black piece, check other diagonal 
								checkcols2=kingcol+1;  
								
								if (chessboard[checkrows][checkcols2].getPiece()!=null) { 
									
									if (chessboard[checkrows][checkcols2].getPiece().getColor().equalsIgnoreCase("B")) { // the piece is black 
										
										if (chessboard[checkrows][checkcols2].getPiece().getType().equalsIgnoreCase("P")) { // the piece is pawn 
											return true; 
										} 
										else { // some other black piece 
											return false; 
										}
									} 
									else { // its a white piece  
										return false; 
									}
								} 
								else { // there is no piece existing 
									return false; 
								}
								
							}
						} 
						else {// there is a white piece, check other diagonal  
							checkcols2=kingcol+1;  
							
							if (chessboard[checkrows][checkcols2].getPiece()!=null) { // there is a piece 
								
								if (chessboard[checkrows][checkcols2].getPiece().getColor().equalsIgnoreCase("B")) { // it is a black piece 
									
									if (chessboard[checkrows][checkcols2].getPiece().getType().equalsIgnoreCase("P")) { // it is a pawn 
										return true;
									} 
									else {  // some other black piece 
										return false; 
									}
								} 
								else { // it is a white piece, return false  
									return false; 
								}
								
							} 
							else { // there is no piece existing 
								return false; 
							}
							
						}
					} 
					else {  // there is no piece, check other diagonal
						checkcols2=kingcol+1;  
						
						if (chessboard[checkrows][checkcols2].getPiece()!=null) { 
							
							if (chessboard[checkrows][checkcols2].getPiece().getColor().equalsIgnoreCase("B")) { 
								
								if (chessboard[checkrows][checkcols2].getPiece().getType().equalsIgnoreCase("P")) { 
									return true;
								} 
								else { // it is another type of black piece 
									return false; 
								}
							}
						} 
						else { // it is an empty space, return false 
							return false; 
						}
						
					}
					
				} 
				
				else if (kingcol==0) {  // king is located on the leftmost col
					checkrows=kingrow-1; 
					checkcols=1;  
					
					if (chessboard[checkrows][checkcols2].getPiece()!=null) { 
						
						if (chessboard[checkrows][checkcols2].getPiece().getColor().equalsIgnoreCase("B")) { 
							
							if (chessboard[checkrows][checkcols2].getPiece().getType().equalsIgnoreCase("P")) { 
								return true;
							} 
							else { // it is another type of black piece 
								return false; 
							}
						} 
						else {// the piece is a white piece  
							return false; 
						}
					} 
					else { // there is no piece 
						return false; 
					}
					
				} 
				
				else {  // king is located on the rightmost cols 
					checkrows=kingrow-1; 
					checkcols=6;  
					
					if (chessboard[checkrows][checkcols].getPiece()!=null) { 
						
						if (chessboard[checkrows][checkcols].getPiece().getColor().equalsIgnoreCase("B")) { 
							
							if (chessboard[checkrows][checkcols].getPiece().getType().equalsIgnoreCase("P")) { 
								return true; 
							} 
							else { 
								return false; 
							}
						} 
						else { 
							return false; 
						}
					} 
					else { // there is no piece 
						return false; 
					}
					
				}
				
			} 
		
			else { // white king is located at the top 2 rows of grid, impossible to have black ponds behind  
				return false; 
			}
		} 
		return false;
	} 
	/* 
	 * bishop and queen check 
	 * 
	 */
	public static boolean bishopcheck (boolean white, boolean black, int kingrow, int kingcol) { 
		  
		int checkrow=kingrow-1; 
		int checkcol=kingcol-1; 
		
		if (white) {  // white player made a move, got black king's location 
			//check diagonal going left and up  
			
			while (checkcol>=0 && checkrow>=0) { 
				
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						
						if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("B")) { // there is a white bishop 
							return true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("Q")) { // there is a white queen 
							return true; 
						} 
						else { // some other white piece  
							break; 
						}
					} 
					else {  // same color piece as king 
						break; 
					}
				} 
				checkrow--; 
				checkcol--; 
			} 
			// check diagonal going right and up 
			checkrow=kingrow-1; 
			checkcol=kingcol+1;  
			
			while (checkrow>=0 && checkcol<=7) { 
				
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						
						if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("B")) { // there is a white bishop 
							return true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("Q")) { // there is a white queen 
							return true; 
						} 
						else { // some other white piece  
							break; 
						}
					} 
					else {  // same color piece as king 
						break; 
					}
				} 
				checkrow--; 
				checkcol++; 
			} 
			
			// check diagonal going left and down 
			checkrow=kingrow+1; 
			checkcol=kingcol-1;  
			
			while (checkrow<=7 && checkcol>=0) { 
				
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						
						if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("B")) { // there is a white bishop 
							return true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("Q")) { // there is a white queen 
							return true; 
						} 
						else { // some other white piece  
							break; 
						}
					} 
					else {  // same color piece as king 
						break; 
					}
				} 
				checkrow++; 
				checkcol--; 
			} 
			
			// check diagonal going right and down 
			checkrow=kingrow+1; 
			checkcol=kingcol+1; 
			
			while (checkrow<=7 && checkcol<=7) { 
				
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						
						if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("B")) { // there is a white bishop 
							return true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("Q")) { // there is a white queen 
							return true; 
						} 
						else { // some other white piece  
							break; 
						}
					} 
					else {  // same color piece as king 
						break; 
					}
				} 
				checkrow++; 
				checkcol++; 
			}
		} 
		
		else { // black player moved black piece, got white queen's information, 
			
			//check diagonal going left and up  
			checkrow=kingrow-1; 
			checkcol=kingcol-1; 
			
			while (checkcol>=0 && checkrow>=0) { 
				
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("B")) { 
						
						if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("B")) { // there is a black bishop 
							return true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("Q")) { // there is a black queen 
							return true; 
						} 
						else { // some other white piece  
							break; 
						}
					} 
					else {  // same color piece as king 
						break; 
					}
				} 
				checkrow--; 
				checkcol--; 
			} 
			// check diagonal going right and up 
			checkrow=kingrow-1; 
			checkcol=kingcol+1;  
			
			while (checkrow>=0 && checkcol<=7) { 
				
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("B")) { 
						
						if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("B")) { // there is a black bishop 
							return true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("Q")) { // there is a black queen 
							return true; 
						} 
						else { // some other white piece  
							break; 
						}
					} 
					else {  // same color piece as king 
						break; 
					}
				} 
				checkrow--; 
				checkcol++; 
			} 
			
			// check diagonal going left and down 
			checkrow=kingrow+1; 
			checkcol=kingcol-1;  
			
			while (checkrow<=7 && checkcol>=0) { 
				
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("B")) { 
						
						if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("B")) { // there is a black bishop 
							return true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("Q")) { // there is a black queen 
							return true; 
						} 
						else { // some other black piece  
							break; 
						}
					} 
					else {  // same color piece as king 
						break; 
					}
				} 
				checkrow++; 
				checkcol--; 
			} 
			
			// check diagonal going right and down 
			checkrow=kingrow+1; 
			checkcol=kingcol+1; 
			
			while (checkrow<=7 && checkcol<=7) { 
				
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("B")) { 
						
						if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("B")) { // there is a black bishop 
							return true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getType().equalsIgnoreCase("Q")) { // there is a black queen 
							return true; 
						} 
						else { // some other black piece  
							break; 
						}
					} 
					else {  // same color piece as king 
						break; 
					}
				} 
				checkrow++; 
				checkcol++; 
			}
		} 
		
		return false; 
	} 
	/* 
	 * check the knight after each move 
	 * 
	 */
	public static boolean knightcheck (boolean white, boolean black, int kingrow, int kingcol) { 
		int knightrow=0; // row for the potential knight  
		int knightcol=0; // column for the potential knight    
		int diffrow=0; // difference between found row of knight  
		int diffcol=0; //difference between found col of knight  
		
		if (white) { // white player has moved, got black kings information, check to see if that king is in check  
			
			for (int a=0; a<7; a++) { 
				
				for (int b=0; b<7; b++) { 
					
					if (chessboard[a][b].getPiece()!=null) { 
						
						if (chessboard[a][b].getPiece().getType().equalsIgnoreCase("W")) { 
							
							if (chessboard[a][b].getPiece().getType().equalsIgnoreCase("k")) { 
								knightrow=a; 
								knightcol=b;  
								diffrow=kingrow-knightrow; 
								diffcol=kingcol-knightcol;  
								diffrow=Math.abs(diffrow); 
								diffcol=Math.abs(diffcol);  
								
								if (diffcol<=2) { 
									
									if (diffcol!=0) { // knight is not on the same column as king 
										
										if (diffcol==1 && diffrow==2) { 
											return true; 
										} 
										if (diffcol==2 && diffrow==1) { 
											return true; 
										}
									}
								}
							}
						} 
					}
				}
			} 
			return false; 
		} 
		
		else { // if black player has moved 
			
			for (int a=0; a<7; a++) { 
				
				for (int b=0; b<7; b++) { 
					
					if (chessboard[a][b].getPiece()!=null) { 
						
						if (chessboard[a][b].getPiece().getType().equalsIgnoreCase("B")) { 
							
							if (chessboard[a][b].getPiece().getType().equalsIgnoreCase("k")) { 
								knightrow=a; 
								knightcol=b;  
								diffrow=kingrow-knightrow; 
								diffcol=kingcol-knightcol;  
								diffrow=Math.abs(diffrow); 
								diffcol=Math.abs(diffcol);  
								
								if (diffcol<=2) { 
									
									if (diffcol!=0) { // knight is not on the same column as king 
										
										if (diffcol==1 && diffrow==2) { 
											return true; 
										} 
										if (diffcol==2 && diffrow==1) { 
											return true; 
										}
									}
								}
							}
						} 
					}
				}
			} 
			return false; 
		}
		
	} 
	
	public static boolean checkmate(boolean white, boolean black) {  
		int kingrow=0; 
		int kingcol=0;  
		int checkrow=0; 
		int checkcol=0; 
		boolean checkcheckmate=false;  
		boolean thereisapiece1=false;  
		boolean thereisapiece2=false; 
		boolean thereisapiece3=false; 
		boolean thereisapiece4=false; 
		boolean thereisapiece5=false;  
		boolean thereisapiece6=false; 
		boolean thereisapiece7=false; 
		boolean thereisapiece8=false; 
		boolean checkpiece1=false; 
		boolean checkpiece2=false; 
		boolean checkpiece3=false;  
		boolean checkpiece4=false; 
		boolean checkpiece5=false;  
		boolean checkpiece6=false; 
		boolean checkpiece7=false; 
		boolean checkpiece8=false;  
		boolean notchecknopiece=false; 
		
		if (white) { // white player's turn, get black king's locator 
			for (int a=0; a<7; a++) { 
				for (int b=0; b<7; b++) { 
					if (chessboard[a][b].getPiece()!=null) { 
						if (chessboard[a][b].getPiece().getColor().equalsIgnoreCase("B")) { 
							if (chessboard[a][b].getPiece().getType().equalsIgnoreCase("K")) { 
								kingrow=a; 
								kingcol=b;  
								break; 
							}
						}
					}
				}
			}
		} 
		else { // black player turn, get white king locator 
			for (int a=0; a<7; a++) { 
				for (int b=0; b<7; b++) { 
					if (chessboard[a][b].getPiece()!=null) { 
						if (chessboard[a][b].getPiece().getColor().equalsIgnoreCase("W")) { 
							if (chessboard[a][b].getPiece().getType().equalsIgnoreCase("K")) { 
								kingrow=a; 
								kingcol=b;  
								break; 
							}
						}
					}
				}
			}
		}
		
	if (kingrow==7) { // king is located on the last row 
			//check left adjust for check:  
			
		if (kingcol>0 && kingcol<7) { // king is located on the last row, but not on the edges 
				checkrow=kingrow; 
				checkcol=kingcol-1; // king is located to the left adjacent to the original king  
					if (chessboard[checkrow][checkcol].getPiece()!=null) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
							if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
								thereisapiece1=true;  
								checkpiece1=true; 
							} 
							else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
								thereisapiece1=true;  
								checkpiece1=true; 
							} 
							else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
								thereisapiece1=true; 
							}
						}
					 else { // same color piece as king. 
						thereisapiece1=true; 
					}
				} 
				if (thereisapiece1==false) { // if that space does not have a piece, move the king there to see if its in check. 
					if (check(white, black,checkcheckmate,checkrow,checkcol)) { 
						checkpiece1=true; 
					} 
					else { // empty space that is not in check 
						notchecknopiece=true; 
					}
				}  
				checkrow=kingrow; 
				checkcol=kingcol+1;  // king is located on the right adjacent to the original king   
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
							if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
								thereisapiece2=true;  
								checkpiece2=true; 
							} 
							else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
								thereisapiece2=true;  
								checkpiece2=true; 
							} 
							else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
								thereisapiece2=true; 
							}
						}
					 else { // same color piece as king. 
						thereisapiece2=true; 
					}
				} 
				if (thereisapiece2==false) { 
					if (check(white, black,checkcheckmate,checkrow,checkcol)) { 
						checkpiece2=true; 
					} 
					else { // empty space that is not in check 
						notchecknopiece=true; 
					}
				} 
			checkrow=kingrow-1; 
			checkcol=kingcol; // king is located right in front of original king  
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
							thereisapiece3=true;  
							checkpiece3=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece3=true;  
							checkpiece3=true; 
						} 
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece3=true; 
						}
					}
					else { // same color piece as king. 
						thereisapiece3=true; 
					}
				} 
				if (thereisapiece3==false) { 
					if (check(white, black,checkcheckmate,checkrow,checkcol)) { 
						checkpiece3=true; 
					} 
					else { // empty space not in check 
						notchecknopiece=true; 
					}
				}  
			checkrow=kingrow-1; // king is located to the left diagonal of original king  
			checkcol=kingcol-1;  
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("k")) { 
							thereisapiece4=true;  
							checkpiece4=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece4=true;  
							checkpiece4=true; 
						} 
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece4=true; 
						}
					}
				else { // same color piece as king. 
					thereisapiece4=true; 
				}
			}   
			if (thereisapiece4==false) { 
				if (check(white, black,checkcheckmate,checkrow,checkcol)) { 
					checkpiece4=true; 
				}  
				else { // empty space not in check 
					notchecknopiece=true; 
				}
			} // here 
			checkrow=kingrow-1; 
			checkcol=kingcol+1; // king is located to the right diagonal of original king  
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("k")) { 
							thereisapiece5=true;  
							checkpiece5=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece5=true;  
							checkpiece5=true; 
						} 
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece5=true; 
						}
					}
				 
				else { // same color piece as king. 
					thereisapiece5=true; 
				}
			} 
				if (thereisapiece5==false) {
					if (check(white, black,checkcheckmate,checkrow,checkcol)) { 
						checkpiece5=true; 
					} 
					else { // empty space but not in check 
						notchecknopiece=true; 
					} 
				}
				
			if (checkpiece1 && checkpiece2 && checkpiece3 && checkpiece4 && checkpiece5) { // all other sports for king to escape check are indeed check. 
				return true; 
			} 
			else if (notchecknopiece) {  // there is a at least a space open and that space is in check 
				return false; 
			} 
			else { // there is a combination of pieces, pieces that are a threat to check, and empty space in check 
				return true;  
			} 
		} 
		
		else if (kingcol==0) { // king is located on the leftmost side
			
			checkrow=kingrow; // king is located right next to original king 
			checkcol=kingcol+1;   
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						} 
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece1=true; 
						}
					}
				 else { // same color piece as king. 
					thereisapiece1=true; 
				}
			} 
			if (thereisapiece1==false) { 
				if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the adjacent side of king 
					checkpiece1=true; 
				} 
				else { 
					notchecknopiece=true; 
				}
			}
			checkrow=kingrow-1; // king is right in front of original king  
			checkcol=kingcol;  
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
							thereisapiece2=true;  
							checkpiece2=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece2=true;  
							checkpiece2=true; 
						} 
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece2=true; 
						}
					}
				else { // same color piece as king. 
					thereisapiece2=true; 
				}
			} 
			if (thereisapiece2==false) {
				if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located right in front of original king 
					thereisapiece2=true; 
				} 
				else { 
					notchecknopiece=true; 
				}
			} 
		checkrow=kingrow-1; // king is located diagonally to original king 
		checkcol=kingcol+1;  
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("k")) { 
							thereisapiece3=true;  
							checkpiece3=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece3=true;  
							checkpiece3=true; 
						} 
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece3=true; 
						}
					}
				 
				else { // same color piece as king. 
					thereisapiece3=true; 
				}
			} 
				if (thereisapiece3==false)  {
					if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is to the right diagonal of original king  
						thereisapiece3=true; 
					} 
					else { 
						notchecknopiece=true; 
					}
				}
			}
			
		else { // king is located on the rightmost last row 
				
			checkrow=kingrow;  // king is to the left of original king 
			checkcol=kingcol-1;  
			if (chessboard[checkrow][checkcol].getPiece()!=null) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						} 
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece1=true; 
						}
					}
				 else { // same color piece as king. 
					thereisapiece1=true; 
				 }
			} 
			if (thereisapiece1==false) { 
				if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is to the left adjacent to the original king  
					thereisapiece1=true; 
				}  
				else { 
					notchecknopiece=true;  
				}
			}
			checkrow=kingrow-1; 
			checkcol=kingcol-1;  // king is located to the diagonal of original king  
			if (chessboard[checkrow][checkcol].getPiece()!=null) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
						thereisapiece2=true;  
						checkpiece2=true; 
					} 
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
						thereisapiece2=true;  
						checkpiece2=true; 
					} 
					else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
						thereisapiece2=true; 
					}
				}
		 
				else { // same color piece as king. 
					thereisapiece2=true; 
				}
			}  
			
			if (thereisapiece2==false) { 
				if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is to the left adjacent to the original king  
					thereisapiece2=true; 
				} 
				else { 
					notchecknopiece=true;  
				}
			}
		
		checkrow=kingrow-1; 
		checkcol=kingcol;  // king is located in front of original king  
		if (chessboard[checkrow][checkcol].getPiece()!=null) { 
			if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
					thereisapiece3=true;  
					checkpiece3=true; 
				} 
				else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
					thereisapiece3=true;  
					checkpiece3=true; 
				} 
				else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
					thereisapiece3=true; 
				}
			}
			else { // same color piece as king. 
				thereisapiece3=true; 
			}
		} 
			if (thereisapiece3==false) { 
		
				if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is to the left adjacent to the original king  
					thereisapiece3=true; 
				} 
				else { 
					notchecknopiece=true;  
				}
			}
		}  
			
	} 
		
	else if (kingrow>0 && kingrow<7) { // king is located from left and right edges 
		
		if (kingcol>0 && kingcol<7) { 
				checkrow=kingrow; 
				checkcol=kingcol-1;  // // king is located to the left adjacent of original king 
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						} 
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece1=true; 
						}
					}
					else { // same color piece as king. 
						thereisapiece1=true; 
					}
				} 
				
			if (thereisapiece1==false) { 
				
				if (!check(white,black,checkcheckmate,checkrow,checkcol)) {  
					checkpiece1=true; 
				} 
				else { 
					notchecknopiece=true; 
				}
			}	
			checkcol=kingcol+1; // king is located to the right of original king 
			if (chessboard[checkrow][checkcol].getPiece()!=null) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
						thereisapiece2=true;  
						checkpiece2=true; 
					} 
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
						thereisapiece2=true;  
						checkpiece2=true; 
					} 
					else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
						thereisapiece2=true; 
					}
				}
				else { // same color piece as king. 
					thereisapiece2=true; 
				}
			} 
			if (thereisapiece2==false) { 
				if (check(white,black,checkcheckmate,checkrow,checkcol)) { 
					checkpiece2=true; 
				}  
				else  { 
					notchecknopiece=true;
				}
			}	
			checkrow=kingrow-1; // king is located right in front of original king 
			checkcol=kingcol;  
			if (chessboard[checkrow][checkcol].getPiece()!=null) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
						thereisapiece3=true;  
						checkpiece3=true; 
					} 
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
						thereisapiece3=true;  
						checkpiece3=true; 
					} 
					else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
						thereisapiece3=true; 
					}
				}
				else { // same color piece as king. 
					thereisapiece3=true; 
				}
			} 	
			if (thereisapiece3==false) { 
				if (check(white,black,checkcheckmate,checkrow,checkcol)) {  
					checkpiece3=true; 
				} 
				else { 
					notchecknopiece=true;
				}
			}
			checkrow=kingrow+1; // king is right below the original king 
			if (chessboard[checkrow][checkcol].getPiece()!=null) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
						thereisapiece4=true;  
						checkpiece4=true; 
					} 
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
						thereisapiece4=true;  
						checkpiece4=true; 
					} 
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
						thereisapiece4=true;  
						checkpiece4=true;
					}
					else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
						thereisapiece4=true; 
					}
				}
			else { // same color piece as king. 
				thereisapiece4=true; 
			}
		} 	
			if (thereisapiece4==false) { 
				if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the front of original king  
					checkpiece4=true; 
				} 
				else { 
					notchecknopiece=true; 
				}
			}
			checkrow=kingrow-1; // king is located to the left diagonal of original king 
			checkcol=kingcol-1;  
			if (chessboard[checkrow][checkcol].getPiece()!=null) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("k")) { 
						thereisapiece5=true;  
						checkpiece5=true; 
					} 
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
						thereisapiece5=true;  
						checkpiece5=true; 
					}  
					else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
						thereisapiece5=true; 
					}
				}
				else { // same color piece as king. 
					thereisapiece5=true; 
				}
			} 	
			if (thereisapiece5==false) { 
				if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the top left diagonal of original king  
				checkpiece5=true; 
				} 
				else { 
					notchecknopiece=true; 
				}
			}
			checkrow=kingrow+1; //king is located to the bottom left diagonal of original king  
			checkcol=kingcol-1; 		
			if (chessboard[checkrow][checkcol].getPiece()!=null) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("B")) { 
						thereisapiece6=true;  
						checkpiece6=true; 
					} 
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
						thereisapiece6=true;  
						checkpiece6=true; 
					} 
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
						thereisapiece6=true;  
						checkpiece6=true;
					}
					else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
						thereisapiece6=true; 
					}
				}
				else { // same color piece as king. 
					thereisapiece6=true; 
				}
			} 	
			if (thereisapiece6==false) { 
				if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom left diagonal of original king  
					thereisapiece6=true; 
				}  
				else { 
					notchecknopiece=true; 
				}
			}
			checkrow=kingrow-1; 
			checkcol=kingcol+1;  // king is located to the top right diagonal of original king 
			if (chessboard[checkrow][checkcol].getPiece()!=null) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("B")) { 
						thereisapiece7=true;  
						checkpiece7=true; 
					} 
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
						thereisapiece7=true;  
						checkpiece7=true; 
					} 
					else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
						thereisapiece7=true; 
					}
				}
				else { // same color piece as king. 
					thereisapiece7=true; 
				}
			} 		
			if (thereisapiece7==true) {
				if (check(white,black,checkcheckmate,checkrow,checkcol)) {   
					checkpiece7=true; 
				}  
				else { 
					notchecknopiece=true; 
				}
			}
			checkrow=kingrow+1; 
			checkcol=kingcol+1;   
			if (chessboard[checkrow][checkcol].getPiece()!=null) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("B")) { 
						thereisapiece8=true;  
						checkpiece8=true; 
					} 
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
						thereisapiece8=true;  
						checkpiece8=true; 
					}  
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
						thereisapiece8=true;  
						checkpiece8=true; 
					}  
					else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
						thereisapiece8=true; 
					}
				}
				else { // same color piece as king. 
					thereisapiece8=true; 
				}
			} 		
			if (thereisapiece8==false) { 	
				if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
					checkpiece8=true; 
				}   
				else { 
					notchecknopiece=true; 
				}
			}
		} 
		else if (kingcol==0) { // king is located at the leftmost column 
			checkrow=kingrow-1; // king is located one above original king 
			checkcol=kingcol;  
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						}  
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece1=true; 
						}
					} 
				}
				if (thereisapiece1==false) { 
					if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
						checkpiece1=true; 
					} 
					else { 
						notchecknopiece=true;
					}
				}
				checkrow=kingrow+1;  // king is located one below original king  
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece2=true;  
							checkpiece2=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
							thereisapiece2=true;  
							checkpiece2=true; 
						}  
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece2=true; 
						}
					} 
				} 
				if (thereisapiece2==false) { 
					if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
						checkpiece2=true; 
					} 
					else { 
						notchecknopiece=true;
					}
				}
				checkrow=kingrow; // king is located right adjacent to the original king 
				checkcol=kingcol+1; 
			if (chessboard[checkrow][checkcol].getPiece()!=null) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
						thereisapiece3=true;  
						checkpiece3=true; 
					} 
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
						thereisapiece3=true;  
						checkpiece3=true; 
					}  
					else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
						thereisapiece3=true; 
					}
				} 
			} 
			if (thereisapiece3==false) { 
				if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
					checkpiece3=true; 
				} 
				else { 
					notchecknopiece=true;
				}
			}
		
			checkrow=kingrow-1; // king is located to the top right diagonal from original king 
			checkcol=kingcol+1; 
			if (chessboard[checkrow][checkcol].getPiece()!=null) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
						thereisapiece4=true;  
						checkpiece4=true; 
					} 
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
						thereisapiece4=true;  
						checkpiece4=true; 
					}  
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
						thereisapiece4=true;  
						checkpiece4=true; 
					}  
					else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
						thereisapiece4=true; 
					}
				} 
			}
				if (thereisapiece4==false) { 
					if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
						checkpiece4=true; 
					} 
					else { 
						notchecknopiece=true;
					}
				} 
				
			checkrow=kingrow+1; // king is located to the right bottom diagonal from original king 
			checkcol=kingcol+1;  
			if (chessboard[checkrow][checkcol].getPiece()!=null) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
						thereisapiece5=true;  
						checkpiece5=true; 
					} 
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
						thereisapiece5=true;  
						checkpiece5=true; 
					} 
					else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
								thereisapiece5=true; 
					}
				} 
			}		
				if (thereisapiece5==false) { 
					if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
						checkpiece5=true; 
					} 
					else { 
						notchecknopiece=true;
					}
				}
			}   
	
		else { // king is located at the rightmost column 
			checkrow=kingrow-1; // king is located right above original king 
			checkcol=kingcol;  
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						}  
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece1=true; 
						}
					} 
				}
				if (thereisapiece1==false) { 
					if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
						checkpiece1=true; 
					} 
					else { 
						notchecknopiece=true;
					}
				}
			checkrow=kingrow+1;  // king is located one below original king 
			if (chessboard[checkrow][checkcol].getPiece()!=null) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
						thereisapiece2=true;  
						checkpiece2=true; 
					} 
					else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
						thereisapiece2=true;  
						checkpiece2=true; 
					}  
					else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
						thereisapiece2=true; 
					}
				} 
			}
		
			if (thereisapiece2==false) { 
		
				if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
					checkpiece2=true; 
				} 
				else { 
					notchecknopiece=true; 
				}
			} 
			checkrow=kingrow; 
			checkcol=kingcol-1; 
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece3=true;  
							checkpiece3=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
							thereisapiece3=true;  
							checkpiece3=true; 
						}  
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece3=true; 
						}
					} 
				}
				if (thereisapiece3==false) { 
					if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
						checkpiece3=true; 
					} 
					else { 
						notchecknopiece=true; 
					}
				} 
				checkrow=kingrow-1; // king is located to the top left diagonal of original king  
				checkcol=kingcol-1;  
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
			
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
	
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece4=true;  
							checkpiece4=true; 
						} 
			
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
							thereisapiece4=true;  
							checkpiece4=true; 
						}  
			
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece4=true; 
						}
					} 
				}
	
				if (thereisapiece4==false) { 
	
					if (check(white,black,checkcheckmate,checkrow,checkcol)) { 
						checkpiece4=true; 
					} 
					else { 
						notchecknopiece=true; 
					}
				} 
				checkrow=kingrow+1; 
				checkcol=kingcol-1; // king is located to the bottom left diagonal of original king 
				
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
			
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 

						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece5=true;  
							checkpiece5=true; 
						} 	
		
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
							thereisapiece5=true;  
							checkpiece5=true; 
						}  
				
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("B")) { 
							thereisapiece5=true;  
							checkpiece5=true; 
						}  
				
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece5=true; 
						}
					} 
				}

				if (thereisapiece5==false) { 

					if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
						checkpiece5=true; 
					} 
					else { 
						notchecknopiece=true; 
					}
				} 
			}
		} 
	else { // king is located on the top row of the board  
		if (kingcol>0 && kingcol<7) { 
			checkrow=kingrow;  // king is located adjacent to the left of 
			checkcol=kingcol-1; 
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 		
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						}  
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece1=true; 
						}
					} 
				}
					if (thereisapiece1==false) { 
						if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
							checkpiece1=true; 
						} 
						else { 
							notchecknopiece=true; 
						}
					} 
				checkcol=kingcol+1;   // king is located right next to original king 
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece2=true;  
							checkpiece2=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
							thereisapiece2=true;  
							checkpiece2=true; 
						}  
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece2=true; 
						}
					} 
				}
				if (thereisapiece2==false) { 
					if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
						checkpiece2=true; 
					} 
					else { 
						notchecknopiece=true; 
					}
				} 	
				checkrow=kingrow+1; // king is located to the left diagonal of original king  
				checkcol=kingcol-1;  
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece3=true;  
							checkpiece3=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
							thereisapiece3=true;  
							checkpiece3=true; 
						}  
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("B")) { 
							thereisapiece3=true;  
							checkpiece3=true; 
						}  
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece3=true; 
						}
					} 
				}
				if (thereisapiece3==false) { 
					if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
						checkpiece3=true; 
					} 
					else { 
						notchecknopiece=true; 
					}
				} 
				checkcol=kingcol+1;  // king is located to the right diagonal of original king 
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece4=true;  
							checkpiece4=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
							thereisapiece4=true;  
							checkpiece4=true; 
						}  
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("B")) { 
							thereisapiece4=true;  
							checkpiece4=true; 
						}  
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece4=true; 
						}
					} 
				}
				if (thereisapiece4==false) { 
					if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
						checkpiece4=true; 
					} 
					else { 
						notchecknopiece=true; 
					}
				} 
				checkcol=kingcol; 
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece5=true;  
							checkpiece5=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
							thereisapiece5=true;  
							checkpiece5=true; 
						}  
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece5=true; 
						}
					} 
				}
				if (thereisapiece5==false) { 
					if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
					checkpiece5=true; 
					} 
					else { 
						notchecknopiece=true; 
					}
				} 
			} 
			else if (kingcol==0) {  // king is located on the leftmost corner in the first row  
				checkrow=kingrow; // king is located right next to original king  
				checkcol=kingcol+1;  
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						}  
						
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece1=true; 
						}
					} 
				}	
					if (thereisapiece1==false) { 
						if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
							checkpiece1=true; 
						} 
						else { 
							notchecknopiece=true; 
						}
					} 
				checkrow=kingrow+1; // king is located right below original king 
				checkcol=kingcol;  
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece2=true;  
							checkpiece2=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("P")) { 
							thereisapiece2=true;  
							checkpiece2=true; 
						}  
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece2=true; 
						}
					} 
				}

				if (thereisapiece2==false) { 
					if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
						checkpiece2=true; 
					} 
					else { 
						notchecknopiece=true; 
					}
				} 
				checkrow=kingrow+1;// king is located diagonally from the original king  
				checkcol=kingcol+1; 
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece3=true;  
							checkpiece3=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("B")) { 
							thereisapiece3=true;  
							checkpiece3=true; 
						}  
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece3=true; 
						}
					} 
				}
				if (thereisapiece3==false) { 
					if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
						checkpiece3=true; 
					} 
					else { 
						notchecknopiece=true; 
					}
				} 
			} 
			else { // king is located at the rightmost on the top row
				checkrow=kingrow; // king is located to the left of original king 
				checkcol=kingcol-1;  
				if (chessboard[checkrow][checkcol].getPiece()!=null) { 
					if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
						if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						} 
						else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
							thereisapiece1=true;  
							checkpiece1=true; 
						}  
						
						else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
							thereisapiece1=true; 
						}
					} 
				}
					if (thereisapiece1==false) { 
						if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
							checkpiece1=true; 
						} 
						else { 
							notchecknopiece=true; 
						}
					} 
			checkrow=kingrow+1; // king is located one below of original king 
			checkcol=kingcol;   
			if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
					thereisapiece2=true;  
					checkpiece2=true; 
				} 
				else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
					thereisapiece2=true;  
					checkpiece2=true; 
				}  
				else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
					thereisapiece2=true; 
				}
			} 
		}
			if (thereisapiece2==false) { 
				if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
					checkpiece2=true; 
				} 
				else { 
					notchecknopiece=true; 
				}
			} 
			checkrow=kingrow+1; // king is located diagonally from original king 
			checkcol=kingcol-1;  
			if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("W")) { 
				if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("Q")) { 
					thereisapiece3=true;  
					checkpiece3=true; 
				} 
				else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("R")) { 
					thereisapiece3=true;  
					checkpiece3=true; 
				} 
				else if (chessboard[checkrow][checkcol].getPiece().getColor().equalsIgnoreCase("B")) { 
					thereisapiece3=true;  
					checkpiece3=true; 
				}  
				else { // all other white pieces-cant check, just move original king to the other spots to see if there are other spots 
					thereisapiece3=true; 
				}
			} 
		}
			if (thereisapiece3==false) { 
				if (check(white,black,checkcheckmate,checkrow,checkcol)) { // king is located to the bottom right diagonal of original king  
					checkpiece3=true; 
				} 
				else { 
					notchecknopiece=true; 
				}
			} 			
		}  
	
	public static void enpassant (boolean whitepawn, boolean blackpawn, int oldposrow, int oldposcol, int newposrow, int newposcol) { 
		
		if (whitepawn) { // white move 
			
			if (oldposrow==6) { 
				if (newposrow==oldposrow-2) { // white pawn did move two spaces to start the game, check the adjacent sides to see if they have the opposing pawns  
					
					
				}
			}
		}
	}
}
	






