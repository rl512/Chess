package chess;

import java.io.*;

/**
 * Chess - CS213 Assignment
 * Group 12
 * @author Kristen Okun
 * @author Raymond Lin
 *
 */

public class Chess {

	/**
	 * global variable for the chess board
	 */
	static BoardSpace[][] board; // chess board

	/**
	 * global variables for turns and ending the game
	 */
	static boolean blackTurn; // indicates whether it is black's turn
	static boolean whiteTurn; // indicates whether it is white's turn
	static boolean blackCheck; // indicates whether black's king is in check
	static boolean whiteCheck; // indicates whether white's king is in check
	static boolean checkmate; // indicates whether there is a checkmate
	static boolean draw; // proposed draw

	/**
	 * global variables associated with special moves
	 */
	static EnPassant enpassant; // en passant object
	static char promotion; // promoted piece entered in input
	static boolean whiteCastling; // indicates whether white has used up their once-per-game castling move
	static boolean blackCastling; // indicates whether black has used up their once-per-game castling move
	static boolean whiteKingMove; // indicates whether white king has moved yet
	static boolean lwR; // indicates whether left black rook has moved
	static boolean rwR; // indicates whether right black rook has moved
	static boolean blackKingMove; // indicates whether black king has moved yet
	static boolean lbR; // indicates whether left black rook has moved
	static boolean rbR; // indicates whether right black rook has moved

	/**
	 * main method
	 * goes through the general turn-based game
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		boolean gameEnd = false; // tracks whether game as ended
		BufferedReader reader; // to read in players' moves
		String currentMove; // current move entered in by player
		String[] twoSpaces; // separate old & new space
		String oldSpace; // old space of piece to move
		String newSpace; // new space to move piece to
		boolean updated; // whether board successfully updated

		// call method to initialize everything at start of game
		startGame();

		// loop turns between two players
		do {

			// WHITE TURN
			updated = false;
			whiteTurn = true;
			blackTurn = false;
			do {

				// print prompt and take input
				System.out.print("White's move: ");
				reader = new BufferedReader(new InputStreamReader(System.in));
				currentMove = reader.readLine();
				System.out.println("");

				// check for resign
				if(currentMove.equals("resign")) {
					updated = true;
					gameEnd = true;
					System.out.println("Black wins");
					break;
				}

				// check for draw
				if(currentMove.equals("draw") && draw == true) {
					updated = true;
					gameEnd = true;
					System.out.println("draw");
					break;
				}

				// parse string + assign to oldSpace & newSpace
				twoSpaces = currentMove.split(" ", 2);
				oldSpace = twoSpaces[0];
				newSpace = twoSpaces[1];
				System.out.println("Oldspace:"+oldSpace);
				System.out.println("newSpace:"+newSpace);


				String str;
				if(newSpace.length() > 2) {
					twoSpaces = newSpace.split(" ", 2);
					newSpace = twoSpaces[0];
					str = twoSpaces[1];

					// proposed draw
					if(str.equals("draw?")) {
						draw = true;
					}

					// promotion
					if(str.length() == 1) {
						promotion = str.charAt(0);
					}

				}

				// update the board
				updated = updateBoard(oldSpace, newSpace);

			} while(!updated);

			if(gameEnd == true) {
				break;
			}

			// print new board
			printBoard();

			// check
			if(blackCheck == true) {
				System.out.println("Check");
				System.out.println("");
				checkmate('b');
			}

			// checkmate
			if(checkmate == true) {
				break;
			}

			// BLACK TURN
			updated = false;
			blackTurn = true;
			whiteTurn = false;
			do {

				// print prompt and take input
				System.out.print("Black's move: ");
				reader = new BufferedReader(new InputStreamReader(System.in));
				currentMove = reader.readLine();
				System.out.println("");

				// check for resign
				if(currentMove.equals("resign")) {
					updated = true;
					gameEnd = true;
					System.out.println("White wins");
					break;
				}

				// check for draw
				if(currentMove.equals("draw") && draw == true) {
					updated = true;
					gameEnd = true;
					System.out.println("draw");
					break;
				}

				// parse string + assign to oldSpace & newSpace
				twoSpaces = currentMove.split(" ", 2);
				oldSpace = twoSpaces[0];
				newSpace = twoSpaces[1];

				String str;
				if(newSpace.length() > 2) {
					twoSpaces = newSpace.split(" ", 2);
					newSpace = twoSpaces[0];
					str = twoSpaces[1];

					// proposed draw
					if(str.equals("draw?")) {
						draw = true;
					}

					// promotion
					if(str.length() == 1) {
						promotion = str.charAt(0);
					}

				}

				// update the board
				updated = updateBoard(oldSpace, newSpace);

			} while(!updated);

			if(gameEnd == true) {
				break;
			}

			// print new board
			printBoard();

			// check
			if(whiteCheck == true) {
				System.out.println("Check");
				System.out.println("");
				checkmate('w');
			}

			// checkmate
			if(checkmate == true) {
				break;
			}

		}while(!gameEnd);

	}

	/**
	 * startGame method
	 * initializes the board and global variables at the beginning of the game
	 * @param void
	 * @return void
	 */
	static void startGame() {

		/* rank of board-used to determine where the filerank is located on the board. For example,
		 * move the pond e2 e4. The rank from 2 to 4. Numbers could be located on the right side of the board.
		 */
		int rank=8;
		// Types of chess pieces. Intialize as char.
		char [] types= {'R','N','B','Q','K','B','N','R'};
		// spacenum-intialize it as a String array.
		String [] alpha= {"a"+rank,"b"+rank,"c"+rank,"d"+rank,"e"+rank,"f"+rank
				,"g"+rank,"h"+rank};
		//color of chess piece and tilecolor, intialized as char.
		char blacktype='b';
		char whitetype='w';
		// create the BoardSpace objects as 2d array.
		board=new BoardSpace[8][8];
		// loop columns
		for (int a=0; a<8; a++) {
			// loop rows
			for (int b=0; b<8; b++) {
				// if statement if row is between 3rd and 6th row. a>=2 and a<6
				if (a>=2 || a<6) {
					//if any rows are even array index, ex=2,4 in this case.
					if (a%2==0) {
						//all the odd row index, will have a blackspace.
						if (b%2==1) {
							board[a][b]=new BoardSpace(blacktype,null,alpha[b]);
						}
						else {
							// all the even row index, will have a white space
							board[a][b]=new BoardSpace(whitetype,null,alpha[b]);
						}
					}
					else { //a is odd, meaning tilecolor is black when row index are even
						if (b%2==0) {
							board[a][b]=new BoardSpace(blacktype,null,alpha[b]);
						}
						else { // a is odd, meaning tilecolor is white when row index is odd
							board[a][b]=new BoardSpace(whitetype,null,alpha[b]);
						}
					}
				}
				if (a==0) { // first column
						// creates a new ChessPiece object
						ChessPiece piece=new ChessPiece(types[b],blacktype);
						//if row index is odd, then tilecolor is black
						if (b%2==1) {
							board[a][b]=new BoardSpace(blacktype,piece,alpha[b]);
						}
						else { // if row index is even, then tilecolor is white
							board[a][b]=new BoardSpace(whitetype,piece,alpha[b]);
						}
				}
				if (a==1) {  // second column
					if (b%2==0) {
						board[a][b]=new BoardSpace(blacktype,new ChessPiece('p',blacktype),alpha[b]);
					}
					else {
						board[a][b]=new BoardSpace(whitetype,new ChessPiece('p',blacktype),alpha[b]);
					}
				}

				if (a==6) { // second to last column
					if (b%2==1) {
						board[a][b]=new BoardSpace(blacktype,new ChessPiece('p',whitetype),alpha[b]);
					}
					else {
						board[a][b]=new BoardSpace(whitetype,new ChessPiece('p',whitetype),alpha[b]);
					}

				}
				if (a==7) { // last column
					if (b%2==0) {
						board[a][b]=new BoardSpace(blacktype,new ChessPiece(types[b],whitetype),alpha[b]);
					}
					else {
						board[a][b]=new BoardSpace(whitetype,new ChessPiece(types[b],whitetype),alpha[b]);
					}
				}

				}
			/* decrement the rank. As the each row is created with the spacenum and other objects.
			 * The spacenum will be concatned with the alpha string array to store the spacenum.
			 */
			rank--;
		}
		// call the printboard method to print the intial chessboard before play begins.
		printBoard();

		// set variables
		enpassant = null;
		promotion = 'X';
		draw = false;
		blackCheck = false;
		whiteCheck = false;
		checkmate = false;
		whiteCastling = false;
		blackCastling = false;

	}

	/**
	 * printBoard method
	 * prints the current board
	 * @parm void
	 * @return void
	 */
	static void printBoard() {

		// loop rows
		for(int i = 0; i < 8; i++) {

			// loop columns
			for(int j = 0; j < 8; j++) {

				// black space
				if(board[i][j].getTileColor() == 'b') {
					// no piece on space
					if(board[i][j].getCurrentPiece() == null) {
						System.out.print(" ## ");
					}
					// piece on space
					else {
						System.out.print(" "+board[i][j].getCurrentPiece().getColor());
						System.out.print(board[i][j].getCurrentPiece().getName()+" ");
					}
				}

				// white space
				if(board[i][j].getTileColor() == 'w') {
					// no piece on space
					if(board[i][j].getCurrentPiece() == null) {
						System.out.print("    ");
					}
					// piece on space
					else {
						System.out.print(" "+board[i][j].getCurrentPiece().getColor());
						System.out.print(board[i][j].getCurrentPiece().getName()+" ");
					}
				}

				// print numbers at end of row
				if(j == 7) {
					int x = 8 - i;
					if(i == 1 || i == 3 || i == 5 || i == 7) {
						System.out.println(" "+x);
					} else if(i == 0 || i == 2 || i == 4 || i == 6) {
						System.out.println(" " + x);

					}
				}

			}

		}

		// print letters along bottom of board
		System.out.println("  a   b   c   d   e   f   g   h");
		System.out.println("");

	}

	/**
	 * updateBoard method
	 * move the piece and update the board
	 * @param oldSpace
	 * @param newSpace
	 * @return boolean
	 */
	static boolean updateBoard(String oldSpace, String newSpace) {

		ChessPiece movePiece; // piece being moved

		// old space
		char oldCol = oldSpace.charAt(0);
		char oldRow = oldSpace.charAt(1);

		// old space's column index
		int oldColIndex = -1;
		if(oldCol == 'A' || oldCol == 'a') {
			oldColIndex = 0;
		} else if(oldCol == 'B' || oldCol == 'b') {
			oldColIndex = 1;
		} else if(oldCol == 'C' || oldCol == 'c') {
			oldColIndex = 2;
		} else if(oldCol == 'D' || oldCol == 'd') {
			oldColIndex = 3;
		} else if(oldCol == 'E' || oldCol == 'e') {
			oldColIndex = 4;
		} else if(oldCol == 'F' || oldCol == 'f') {
			oldColIndex = 5;
		} else if(oldCol == 'G' || oldCol == 'g') {
			oldColIndex = 6;
		} else if(oldCol == 'H' || oldCol == 'h') {
			oldColIndex = 7;
		}

		// old space's row index
		int oldRowIndex = 8 - (oldRow - '0');
		System.out.println("oldRowIndex"+oldRowIndex);


		// new space
		char newCol = newSpace.charAt(0);
		char newRow = newSpace.charAt(1);

		// old space's column index
		int newColIndex = -1;
		if(newCol == 'A' || newCol == 'a') {
			newColIndex = 0;
		} else if(newCol == 'B' || newCol == 'b') {
			newColIndex = 1;
		} else if(newCol == 'C' || newCol == 'c') {
			newColIndex = 2;
		} else if(newCol == 'D' || newCol == 'd') {
			newColIndex = 3;
		} else if(newCol == 'E' || newCol == 'e') {
			newColIndex = 4;
		} else if(newCol == 'F' || newCol == 'f') {
			newColIndex = 5;
		} else if(newCol == 'G' || newCol == 'g') {
			newColIndex = 6;
		} else if(newCol == 'H' || newCol == 'h') {
			newColIndex = 7;
		}

		// old space's row index
		int newRowIndex = 8 - (newRow - '0');
		System.out.println("newRowIndex"+newRowIndex);

		// find piece to move
		movePiece = board[oldRowIndex][oldColIndex].getCurrentPiece();

		// check for illegal move
		boolean illegalMove = illegalMoveCheck(movePiece.getName(), movePiece.getColor(), oldColIndex, oldRowIndex, newColIndex, newRowIndex);

		if(illegalMove == true) {
			System.out.println("Illegal move, try again");
			System.out.println("");
			return false;
		}

		// check if either king is in check
		// resets check var to false if player moves their own king out of check
		kingLocator('w');
		System.out.println("kinglocator ran W");
		kingLocator('b');
		System.out.println("kinglocator ran K");

		// remove piece from old space and place in new space
		board[oldRowIndex][oldColIndex].setCurrentPiece(null);
		board[newRowIndex][newColIndex].setCurrentPiece(movePiece);

		return true;

	}

	/**
	 * illegalMoveCheck method
	 * check the piece to be moved to make sure the move is legal
	 * @param pieceName
	 * @param pieceColor
	 * @param oldCol
	 * @param oldRow
	 * @param newCol
	 * @param newRow
	 * @return boolean
	 */
	static boolean illegalMoveCheck(char pieceName, char pieceColor, int oldCol, int oldRow, int newCol, int newRow) {

		// illegal move: new space has piece of same color
		if(board[newRow][newCol].getCurrentPiece() != null && pieceColor == board[newRow][newCol].getCurrentPiece().getColor()) {
			return true;
		}

		// illegal move: new and old space are the same
		if(oldCol == newCol && oldRow == newRow) {
			return true;
		}

		// illegal move: trying to move piece that doesn't exist
		if(board[oldRow][oldCol].getCurrentPiece() == null) {
			return true;
		}

		// illegal move: trying to move opponent's piece
		if(whiteTurn == true && pieceColor == 'b') {
			return true;
		}
		if(blackTurn == true && pieceColor == 'w') {
			return true;
		}

		// illegal move: not moving your king when it is in check
		if(blackCheck == true && blackTurn == true && board[oldRow][oldCol].getCurrentPiece().getName() != 'K') {
			return true;
		}
		if(whiteCheck == true && whiteTurn == true && board[oldRow][oldCol].getCurrentPiece().getName() != 'K') {
			return true;
		}

		// PAWN
		// The pawn can move forward to the unoccupied square immediately in front of it on the same file
		// or on its first move it can advance two squares along the same file
		// provided both squares are unoccupied
		// if the pawn can capture an opponent's piece on a square diagonally in front of it on an adjacent file
		if(pieceName == 'p') {
			// white pawns
			if(pieceColor == 'w') {
				// legal move: en passant
				if(enpassant != null) {
					if(enpassant.getOldCol() == oldCol && enpassant.getOldRow() == oldRow && enpassant.getNewRow() == newRow && enpassant.getNewCol() == newCol) {
						board[oldRow][newCol].setCurrentPiece(null); // capture piece
						enpassant = null;
						return false;
					} else { // only valid the turn immediately after, so if it is not used, reset to null
						enpassant = null;
					}
				}
				// legal move: advance one square forward
				if(newCol == oldCol && newRow == oldRow - 1) {
					if(newRow == 0) {
						if(promotion == 'Q' || promotion == 'R' || promotion == 'B' || promotion == 'N') {
							board[oldRow][oldCol].getCurrentPiece().setName(promotion);
						} else {
							board[oldRow][oldCol].getCurrentPiece().setName('Q');
						}
						promotion = 'X';
					}
					return false;
				}
				// legal move: advance two squares forward on first move
				else if(newCol == oldCol && newRow == oldRow - 2 && oldRow == 6 && board[oldRow-1][newCol].getCurrentPiece() == null) {
					enpassantCheck(newRow, newCol, oldRow, oldCol);
					return false;
				}
				// can capture an oponent's piece on a square diagonally in front of it on an adjacent file
				else if(newRow == oldRow - 1 && (newCol == oldCol - 1 || newCol == oldCol + 1) &&  board[newRow][newCol].getCurrentPiece() != null) {
					return false;
				}
				// all other moves illegal
				else {
					return true;
				}
			}
			// black pawns
			else if(pieceColor == 'b') {
				// legal move: en passant
				if(enpassant != null) {
					if(enpassant.getOldCol() == oldCol && enpassant.getOldRow() == oldRow && enpassant.getNewRow() == newRow && enpassant.getNewCol() == newCol) {
						board[oldRow][newCol].setCurrentPiece(null); // capture piece
						enpassant = null;
						return false;
					} else { // only valid the turn immediately after, so if it is not used, reset to null
						enpassant = null;
					}
				}
				// legal move: advance one square forward
				if(newCol == oldCol && newRow == oldRow + 1) {
					if(newRow == 7) {
						if(promotion == 'Q' || promotion == 'R' || promotion == 'B' || promotion == 'N') {
							board[oldRow][oldCol].getCurrentPiece().setName(promotion);
						} else {
							board[oldRow][oldCol].getCurrentPiece().setName('Q');
						}
						promotion = 'X';
					}
					return false;
				}
				// legal move: advance two squares forward on first move
				else if(newCol == oldCol && newRow == oldRow + 2 && oldRow == 1 && board[oldRow+1][newCol].getCurrentPiece() == null) {
					enpassantCheck(newRow, newCol, oldRow, oldCol);
					return false;
				}
				// can capture an oponent's piece on a square diagonally in front of it on an adjacent file
				else if(newRow == oldRow + 1 && (newCol == oldCol - 1 || newCol == oldCol + 1) &&  board[newRow][newCol].getCurrentPiece() != null) {
					return false;
				}
				// all other moves illegal
				else {
					return true;
				}
			}
		}

		// ROOK
		// The rook can move any number of squares along a rank or file
		// cannot leap over other pieces
		if(pieceName == 'R') {
			// legal moves along the same row
			if(newRow == oldRow && newCol != oldCol) {
				// moving left - check for other pieces in path
				if(newCol < oldCol) {
					for(int i = newCol + 1; i < oldCol; i++) {
						if(board[oldRow][i].getCurrentPiece() != null) {
							return true;
						}
					}
					if(pieceColor == 'b' && oldRow == 0) {
						if(oldCol == 0) lbR = true;
						if(oldCol == 7) rbR = true;
					} else if(pieceColor == 'w' && oldRow == 7) {
						if(oldCol == 0) lwR = true;
						if(oldCol == 7) rwR = true;
					}
					return false;
				}
				// moving right - check for other pieces in path
				else if(newCol > oldCol) {
					for(int i = oldCol + 1; i < newCol; i++) {
						if(board[oldRow][i].getCurrentPiece() != null) {
							return true;
						}
					}
					if(pieceColor == 'b' && oldRow == 0) {
						if(oldCol == 0) lbR = true;
						if(oldCol == 7) rbR = true;
					} else if(pieceColor == 'w' && oldRow == 7) {
						if(oldCol == 0) lwR = true;
						if(oldCol == 7) rwR = true;
					}
					return false;
				}
			}
			// legal moves along the same column
			else if(newCol == oldCol && newRow != oldRow) {
				// moving up - check for other pieces in path
				if(newRow < oldRow) {
					for(int i = oldRow + 1; i < newRow; i++) {
						if(board[i][oldCol].getCurrentPiece() != null) {
							return true;
						}
					}
					if(pieceColor == 'b' && oldRow == 0) {
						if(oldCol == 0) lbR = true;
						if(oldCol == 7) rbR = true;
					} else if(pieceColor == 'w' && oldRow == 7) {
						if(oldCol == 0) lwR = true;
						if(oldCol == 7) rwR = true;
					}
					return false;
				}
				// moving down - check for other pieces in path
				if(newRow > oldRow) {
					for(int i = newRow + 1; i < oldRow; i++) {
						if(board[i][oldCol].getCurrentPiece() != null) {
							return true;
						}
					}
					if(pieceColor == 'b' && oldRow == 0) {
						if(oldCol == 0) lbR = true;
						if(oldCol == 7) rbR = true;
					} else if(pieceColor == 'w' && oldRow == 7) {
						if(oldCol == 0) lwR = true;
						if(oldCol == 7) rwR = true;
					}
					return false;
				}
			}
			// all other moves illegal
			else {
				return true;
			}
		}

		// KNIGHT
		// The knight moves to any of the closest squares that are not on the same rank, file, or diagonal
		// thus the move forms an "L"-shape: two squares vertically and one square horizontally,
		// or two squares horizontally and one square vertically
		if(pieceName == 'N') {
			if((newRow == oldRow + 2 && newCol == oldCol - 1) || (newRow == oldRow + 1 && newCol == oldCol - 2)) {
				return false;
			} else if((newRow == oldRow - 1 && newCol == oldCol - 2) || (newRow == oldRow - 2 && newCol == oldCol - 1)) {
				return false;
			} else if((newRow == oldRow + 2 && newCol == oldCol + 1) || (newRow == oldRow + 1 && newCol == oldCol + 2)) {
				return false;
			} else if((newRow == oldRow - 2 && newCol == oldCol + 1) || (newRow == oldRow - 1 && newCol == oldRow + 2)) {
				return false;
			} else {
				return true;
			}
		}

		// BISHOP
		// the bishop can move any number of squares diagonally
		// cannot leap over other pieces
		if(pieceName == 'B') {
			// check that move is diagonal
			int rowMove = 0;
			if(newRow > oldRow) {
				rowMove = newRow - oldRow;
			} else if(newRow < oldRow) {
				rowMove = oldRow - newRow;
			}
			int colMove = 0;
			if(newCol > oldCol) {
				colMove = newCol - oldCol;
			} else if(newCol < oldCol) {
				colMove = oldCol - newCol;
			}
			if(Math.abs(rowMove) != Math.abs(colMove)) {
				return true;
			}
			// check for hopping over pieces
			int r = oldRow;
			if(newRow > oldRow) {
				r++;
			} else {
				r--;
			}
			int c = oldCol;
			if(newCol > oldCol) {
				c++;
			} else {
				c--;
			}
			while(r != newRow && c != newCol) {
				if(r != newRow && c != newCol && board[r][c].getCurrentPiece() != null) {
					return true;
				}
				if(newRow > oldRow) {
					r++;
				} else {
					r--;
				}
				if(newCol > oldCol) {
					c++;
				} else {
					c--;
				}
			}
			return false;
		}

		// QUEEN
		// the queen can move any number of squares along a rank, file, or diagonal
		// cannot leap over other pieces
		if(pieceName == 'Q') {
			// check if move is diagonal
			int rowMove = 0;
			if(newRow > oldRow) {
				rowMove = newRow - oldRow;
			} else if(newRow < oldRow) {
				rowMove = oldRow - newRow;
			}
			int colMove = 0;
			if(newCol > oldCol) {
				colMove = newCol - oldCol;
			} else if(newCol < oldCol) {
				colMove = oldCol - newCol;
			}
			// illegal move: not diagonal, horizontal, or vertical
			if(Math.abs(rowMove) != Math.abs(colMove) && oldRow != newRow && oldCol != newCol) {
				return true;
			} else {
				// follow bishop's check
				if(Math.abs(rowMove) == Math.abs(colMove)) {
					int r = oldRow;
					if(newRow > oldRow) {
						r++;
					} else {
						r--;
					}
					int c = oldCol;
					if(newCol > oldCol) {
						c++;
					} else {
						c--;
					}
					while(r != newRow && c != newCol) {
						if(r != newRow && c != newCol && board[r][c].getCurrentPiece() != null) {
							return true;
						}
						if(newRow > oldRow) {
							r++;
						} else {
							r--;
						}
						if(newCol > oldCol) {
							c++;
						} else {
							c--;
						}
					}
					return false;
				}
				// follow rook's check
				else {
					// legal moves along the same row
					if(newRow == oldRow && newCol != oldCol) {
						// moving left - check for other pieces in path
						if(newCol < oldCol) {
							for(int i = newCol + 1; i < oldCol; i++) {
								if(board[oldRow][i].getCurrentPiece() != null) {
									return true;
								}
							}
							return false;
						}
						// moving right - check for other pieces in path
						else if(newCol > oldCol) {
							for(int i = oldCol + 1; i < newCol; i++) {
								if(board[oldRow][i].getCurrentPiece() != null) {
									return true;
								}
							}
							return false;
						}
					}
					// legal moves along the same column
					else if(newCol == oldCol && newRow != oldRow) {
						// moving up - check for other pieces in path
						if(newRow < oldRow) {
							for(int i = oldRow + 1; i < newRow; i++) {
								if(board[i][oldCol].getCurrentPiece() != null) {
									return true;
								}
							}
							return false;
						}
						// moving down - check for other pieces in path
						if(newRow > oldRow) {
							for(int i = newRow + 1; i < oldRow; i++) {
								if(board[i][oldCol].getCurrentPiece() != null) {
									return true;
								}
							}
							return false;
						}
					}
					// all other moves illegal
					else {
						return true;
					}
				}
			}
		}

		// KING
		// the king moves one square in any direction
		if(pieceName == 'K') {
			// cannot make move that will put king in check
			boolean check = check(pieceColor, newRow, newCol);
			if(check == true) {
				return true;
			}
			// castling
			if(pieceColor == 'w' && whiteKingMove == false && whiteCastling == false && newRow == 7) {
				// castling moving right
				if(newCol == oldCol + 2 && rwR == false && whiteCheck == false) {
					boolean x = check('w', newRow, oldCol+1);
					if(x == false && board[7][5].getCurrentPiece() == null && board[7][6].getCurrentPiece() == null) {
						board[newRow][oldCol+1].setCurrentPiece(board[7][7].getCurrentPiece());
						board[7][7].setCurrentPiece(null);
						whiteCastling = true;
						whiteKingMove = true;
						rwR = true;
						return false;
					}
				}
				// castling moving left
				else if(newCol == oldCol - 2 && lwR == false && whiteCheck == false) {
					boolean x = check('w', newRow, oldCol-1);
					if(x == false && board[7][3].getCurrentPiece() == null && board[7][2].getCurrentPiece() == null && board[7][1].getCurrentPiece() == null) {
						board[newRow][oldCol-1].setCurrentPiece(board[7][0].getCurrentPiece());
						board[7][0].setCurrentPiece(null);
						whiteCastling = true;
						whiteKingMove = true;
						lwR = true;
						return false;
					}
				}
			} else if(pieceColor == 'b' && blackKingMove == false && blackCastling == false && newRow == 0) {
				// castling moving right
				if(newCol == oldCol + 2 && rbR == false && blackCheck == false) {
					boolean x = check('b', newRow, oldCol+1);
					if(x == false && board[0][5].getCurrentPiece() == null && board[0][6].getCurrentPiece() == null) {
						board[newRow][oldCol+1].setCurrentPiece(board[0][7].getCurrentPiece());
						board[0][7].setCurrentPiece(null);
						blackCastling = true;
						blackKingMove = true;
						rbR = true;
						return false;
					}
				}
				// castling moving left
				else if(newCol == oldCol - 2 && lbR == false && blackCheck == false) {
					boolean x = check('b', newRow, oldCol-1);
					if(x == false && board[0][3].getCurrentPiece() == null && board[0][2].getCurrentPiece() == null && board[0][1].getCurrentPiece() == null) {
						board[newRow][oldCol-1].setCurrentPiece(board[0][0].getCurrentPiece());
						board[0][0].setCurrentPiece(null);
						blackCastling = true;
						blackKingMove = true;
						lbR = true;
						return false;
					}
				}
			}
			// other moves
			if((newCol == oldCol - 1 && newRow == oldRow + 1) || (newCol == oldCol - 1 && newRow == oldRow) || (newCol == oldCol - 1 && newRow == oldRow - 1)) {
				if(pieceColor == 'b') blackKingMove = true;
				if(pieceColor == 'w') whiteKingMove = true;
				return false;
			} else if((newCol == oldCol && newRow == oldRow + 1) || (newCol == oldCol && newRow == oldRow - 1)) {
				if(pieceColor == 'b') blackKingMove = true;
				if(pieceColor == 'w') whiteKingMove = true;
				return false;
			} else if((newCol == oldCol + 1 && newRow == oldRow + 1) || (newCol == oldCol + 1 && newRow == oldRow) || (newCol == oldCol + 1 && newRow == oldRow - 1)) {
				if(pieceColor == 'b') blackKingMove = true;
				if(pieceColor == 'w') whiteKingMove = true;
				return false;
			} else {
				return true;
			}
		}

		return true;

	}

	/**
	 * enpassantCheck method
	 * checks if a pawn can perform its special move en passant
	 * @param newRow
	 * @param newCol
	 * @param oldRow
	 * @param oldCol
	 * @return void
	 */
	static void enpassantCheck(int newRow, int newCol, int oldRow, int oldCol) {
		// potential capture piece
		ChessPiece capture = board[oldRow][oldCol].getCurrentPiece();
		// opponent's piece
		ChessPiece opponent;
		// check if opponent's piece is in right position for en passant
		// if capture is white
		if(capture.getColor() == 'w') {
			// piece to the left
			if(newCol > 0 && board[newRow][newCol-1].getCurrentPiece() != null) {
				if(board[newRow][newCol-1].getCurrentPiece().getColor() == 'b' && board[newRow][newCol-1].getCurrentPiece().getName() == 'p') {
					opponent = board[newRow][newCol-1].getCurrentPiece();
					enpassant = new EnPassant(opponent, capture, newRow+1, newCol, newRow, newCol-1);
					return;
				}
			}
			// piece to the right
			if(newCol < 7 && board[newRow][newCol+1].getCurrentPiece() != null) {
				if(board[newRow][newCol+1].getCurrentPiece().getColor() == 'b' && board[newRow][newCol+1].getCurrentPiece().getName() == 'p') {
					opponent = board[newRow][newCol+1].getCurrentPiece();
					enpassant = new EnPassant(opponent, capture, newRow+1, newCol, newRow, newCol+1);
					return;
				}

			}
		}
		// if capture is black
		else if(capture.getColor() == 'b') {
			// piece to the left
			if(newCol > 0 && board[newRow][newCol-1].getCurrentPiece() != null) {
				if(board[newRow][newCol-1].getCurrentPiece().getColor() == 'w' && board[newRow][newCol-1].getCurrentPiece().getName() == 'p') {
					opponent = board[newRow][newCol-1].getCurrentPiece();
					enpassant = new EnPassant(opponent, capture, newRow-1, newCol, newRow, newCol-1);
					return;
				}
			}
			// piece to the right
			if(newCol < 7 && board[newRow][newCol+1].getCurrentPiece() != null) {
				if(board[newRow][newCol+1].getCurrentPiece().getColor() == 'w' && board[newRow][newCol+1].getCurrentPiece().getName() == 'p') {
					opponent = board[newRow][newCol+1].getCurrentPiece();
					enpassant = new EnPassant(opponent, capture, newRow-1, newCol, newRow, newCol+1);
					return;
				}

			}
		}
		// no piece eligible for en passant
		else {
			enpassant = null;
		}
	}

	/**
	 * kingLocator method
	 * locate the king of a specified color
	 * @param color
	 * @return void
	 */
	static void kingLocator(char color) {
		int row = 0;
		int col = 0;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j].getCurrentPiece() != null && board[i][j].getCurrentPiece().getColor() == color && board[i][j].getCurrentPiece().getName() == 'K') {
					row = i;
					col = j;
				}
			}
		}
		// call check with king's color and location
		System.out.println(color);
		System.out.println(row);
		System.out.println(col);
		check(color, row, col);
	}

	/**
	 * check method
	 * check if the king is or would be in check in specified space
	 * @param color
	 * @param row
	 * @param col
	 * @return boolean
	 */
	static boolean check(char color, int row, int col) {

		// TODO
		// find king of input color
		// check all opponent's pieces to see if king is in check
		// nested for loop, check entire board
		// set global check var to true if king is in check
		// set global check var to false if not in check (important for when king moved out of check)
		//if (board[row][col].getCurrentPiece().getColor()=='b') {

		boolean check=false;  // global variable
		char name='a';
		if (color=='b') { // if color of king is black
			System.out.println("FUCK MEEEEEEEE");
			for (int a=0; a<8; a++ ) {
				for (int b=0; b<8; b++) {
					System.out.println("a in black is "+a);
					System.out.println("b in black is "+b);
					if (board[a][b].currentPiece==null) {
						continue;
					}
					if (board[a][b]!=null) {
						if (board[a][b].currentPiece.getColor()=='w') {
						name=board[a][b].currentPiece.getName(); // get name of current piece
						if (name=='R') { // if type is Rook
							System.out.println("Black Rook Ran");
							if (col==b) {
								System.out.println("Fuck this project A");
								return true;
							}
							if (row==a) {
								System.out.println("Fuck this project B");
								return true;
							}
						}
						if (name=='B') { //if type is bishop
							System.out.println("Black Bishop Ran");
							if (Bishopcheck(color,row,col)) {
								System.out.println("Fuck this project C");
								return true;
							}
						}
						if (name=='Q') { // if type is Queen
							System.out.println("Black Queen Ran");
							if (Queencheck(a,b,color,row,col)) {
								System.out.println("Fuck this project D");
								return true;
							}
						}

						if (name=='N') {
							System.out.println("Black Knight Ran");
							if (Knightcheck(a,b,color)) {
								System.out.println("Fuck this project E");
								return true;
							}
						}
						if (name=='p') {
							System.out.println("Black Pond Ran");
							if (Pondcheck(a,b,color)) {
								System.out.println("Fuck this project F");
								return true;
							}
						}
					}
				}
			}
		}
			System.out.println("Fuck this project G");
			return false;
		}

		if (color=='w') { // if color of king is white
			System.out.println("NOOOOOOO");
			for (int a=0; a<8; a++ ) {
				for (int b=0; b<8; b++) {
					if (board[a][b]!=null) {
						System.out.println("a is"+a);
						System.out.println("b is"+b );
						if (board[a][b].currentPiece==null) {
							continue;
						}
						if (board[a][b].currentPiece.getColor()=='b') {
						name=board[a][b].currentPiece.getName(); // get name of current piece
						if (name=='R') { // if type is Rook
							System.out.println("black rook ran");
							if (col==b) {
								System.out.println("Fuck this project H");
								return true;
							}
							if (row==a) {
								System.out.println("black rook ran2");
								System.out.println("Fuck this project I");
								return true;
							}
						}
						if (name=='B') { //if type is bishop
							System.out.println("black bishop ran");
							if (Bishopcheck(color,row,col)) {
								System.out.println("Fuck this project J");
								return true;
							}
						}
						if (name=='Q') { // if type is Queen
							System.out.println("black Queen ran");
							if (Queencheck(a,b,color,row,col)) {
								System.out.println("Fuck this project K");
								return true;
							}
						}

						if (name=='N') {
							System.out.println("black knight ran");
							if (Knightcheck(a,b,color)) {
								System.out.println("Fuck this project L");
								return true;
							}
						}
						if (name=='p') {
							System.out.println("black pond ran");
							if (Pondcheck(a,b,color)) {
								System.out.println("Fuck this project M");
								return true;
							}
						}
					}
				}
			}
		}
	}
		System.out.println("Fuck this project N");
		return false;
	}

	static boolean Pondcheck (int pondrow, int pondcol, char color) {

		int newpondrow=pondrow;
		int newpondcol=pondcol;

		if (color=='b') {   // pawn is white and located on the  very left side of board
			if (pondcol==0 && pondrow<7) {
				newpondrow=pondrow-1;
				newpondcol=pondcol+1;
				if (board[newpondrow][newpondcol]!=null) {
					if (board[newpondrow][newpondcol].currentPiece!=null) {
						if (board[newpondrow][newpondcol].currentPiece.getColor()=='b') {
							if (board[newpondrow][newpondcol].currentPiece.getName()=='K') {
								System.out.println("Fuck this project O");
								return true;
							}
						}
					}
				}
				System.out.println("Fuck this project P");
				return false;
			}

			if (pondcol>0 && pondcol<7) {
				if (pondrow<7) {  // checking the moves diagonally to the left
					newpondrow=pondrow-1;
					newpondcol=pondcol-1;
					if (board[newpondrow][newpondcol]!=null) {
						if (board[newpondrow][newpondcol].currentPiece!=null) {
							if (board[newpondrow][newpondcol].currentPiece.getColor()=='b') {
								if (board[newpondrow][newpondcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project Q");
									return true;
								}
							}
						}
					} // checking the movies diagonally to the right
					newpondrow=pondrow-1;
					newpondcol=pondcol+1;
					if (board[newpondrow][newpondcol]!=null) {
						if (board[newpondrow][newpondcol].currentPiece!=null) {
							if (board[newpondrow][newpondcol].currentPiece.getColor()=='b') {
								if (board[newpondrow][newpondcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project R");
									return true;
								}
							}
						}
					}
				}
				return false;
			}

			if (pondcol==7) { // if the knight is located on the right most side of the board
				newpondrow=pondrow-1;
				newpondcol=pondcol-1;
				if (board[newpondrow][newpondcol]!=null) {
					if (board[newpondrow][newpondcol].currentPiece!=null) {
						if (board[newpondrow][newpondcol].currentPiece.getColor()=='b') {
							if (board[newpondrow][newpondcol].currentPiece.getName()=='K') {
								System.out.println("Fuck this project S");
								return true;
							}
						}
					}
				}
				return false;
			}
		}

		else  {   // pawn is black and located on the  very left side of board
			if (pondcol==0 && pondrow<7) {
				newpondrow=pondrow-1;
				newpondcol=pondcol+1;
					if (board[newpondrow][newpondcol]!=null) {
						if (board[newpondrow][newpondcol].currentPiece!=null) {
						if (board[newpondrow][newpondcol].currentPiece.getColor()=='w') {
							if (board[newpondrow][newpondcol].currentPiece.getName()=='K') {
								System.out.println("Fuck this project T");
								return true;
							}
						}
					}
				}
				return false;
			}

			if (pondcol>0 && pondcol<7) {
				if (pondrow<7) {  // checking the moves diagonally to the left
					newpondrow=pondrow-1;
					newpondcol=pondcol-1;
					if (board[newpondrow][newpondcol]!=null) {
						if (board[newpondrow][newpondcol].currentPiece!=null) {
							if (board[newpondrow][newpondcol].currentPiece.getColor()=='w') {
								if (board[newpondrow][newpondcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project U");
									return true;
								}
							}
						}
					} // checking the movies diagonally to the right
					newpondrow=pondrow-1;
					newpondcol=pondcol+1;
					if (board[newpondrow][newpondcol]!=null) {
						if (board[newpondrow][newpondcol].currentPiece!=null) {
							if (board[newpondrow][newpondcol].currentPiece.getColor()=='w') {
								if (board[newpondrow][newpondcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project V");
									return true;
								}
							}
						}
					}
				}
				System.out.println("Fuck this project W");
				return false;
			}

			if (pondcol==7) { // if the knight is located on the right most side of the board
				newpondrow=pondrow-1;
				newpondcol=pondcol-1;
				if (board[newpondrow][newpondcol]!=null) {
					if (board[newpondrow][newpondcol].currentPiece!=null) {
						if (board[newpondrow][newpondcol].currentPiece.getColor()=='w') {
							if (board[newpondrow][newpondcol].currentPiece.getName()=='K') {
								System.out.println("Fuck this project X");
								return true;
							}
						}
					}
				}
				System.out.println("Fuck this project Y");
				return false;
			}
		}
		System.out.println("Fuck this project Z");
		return false;
	}

	static boolean Knightcheck (int knightrow, int knightcol, char color) {

		int newknightrow=knightrow;
		int newknightcol=knightcol;

		if (color=='b') {
			if (knightcol<2) { // if knight is located on top of board
				if (knightrow==0) { // if knight is located on top of board and in the first row
					newknightrow++;
					newknightcol+=2;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project AA");
									return true;
								}
							}
						}
					}
					if (knightcol==0) {
						newknightrow=knightrow+2;
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {  // moving from bottom to right
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project AB");
										return true;
									}
								}
							}
						}
					}
					if (knightcol==1) {
						newknightrow=2;
						newknightcol=0;
						if (board[newknightrow][newknightcol]!=null) {  // moving from bottom to left
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project AC");
										return true;
									}
								}
							}
						}

						newknightcol=2;
						if (board[newknightrow][newknightcol]!=null) {  // moving from bottom to right
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project AD");
										return true;
									}
								}
							}
						}
					}
					System.out.println("Fuck this project ADD");
					return false;
				}
				if (knightrow==7) { // if knight is located at the bottom of board. Moving from right to top
					newknightrow=knightrow-1;
					newknightcol=knightcol+2;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project AE");
									return true;
								}
							}
						}
					}

					if (knightcol==0) {
						newknightrow=5;
						newknightcol=1;
						if (board[newknightrow][newknightcol]!=null) {  // moving from top to right
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project AF");
										return true;
									}
								}
							}
						}
					}
					if (knightcol==1) {
						newknightrow=5;
						newknightcol=0;
						if (board[newknightrow][newknightcol]!=null) {  // moving from top to left
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project AG");
										return true;
									}
								}
							}
						}
						newknightcol=2;
						if (board[newknightrow][newknightcol]!=null) {  // moving from top to right
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project AH");
										return true;
									}
								}
							}
						}
					}
					System.out.println("Fuck this project AI");
					return false;
				}

				if (knightrow>0 && knightrow<7) {  // if knight
					newknightrow--;
					newknightcol+=2;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project AJ");
									return true;
								}
							}
						}
					}

					newknightrow=knightrow+1;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project AK");
									return true;
								}
							}
						}
					}

					if (knightcol==0) {
						if (knightrow==1) {
							newknightrow=knightrow+2;
							newknightcol=knightcol+1;
							if (board[newknightrow][newknightcol]!=null) { // goes down and to the right
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project AL");
										return true;
									}
								}
							}
						}
						if (knightrow==6) {
							newknightrow=knightrow-2;
							newknightcol=knightcol+1;
							if (board[newknightrow][newknightcol]!=null) {  // goes up and to the right
								if (board[newknightrow][newknightcol].currentPiece!=null) {
									if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
										if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
											System.out.println("Fuck this project AM");
											return true;
										}
									}
								}
							}
						}
						if (knightrow>1 && knightrow<6) {
							newknightrow=knightrow+2;
							newknightcol=knightcol+1;
							if (board[newknightrow][newknightcol]!=null) {
								if (board[newknightrow][newknightcol].currentPiece!=null) {
									if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
										if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
											System.out.println("Fuck this project AN");
											return true;
										}
									}
								}
							}
							newknightrow=knightrow-2;
							newknightcol=knightcol+1;
							if (board[newknightrow][newknightcol]!=null) {
								if (board[newknightrow][newknightcol].currentPiece!=null) {
									if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
										if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
											System.out.println("Fuck this project AO");
											return true;
											}
										}
									}
								}
							}
						}
					if (knightcol==1) {
						if (knightrow==1) {
							newknightrow=knightrow+2;
							newknightcol=knightcol-1;
								if (board[newknightrow][newknightcol]!=null) {
									if (board[newknightrow][newknightcol].currentPiece!=null) {
										if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
											if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
												System.out.println("Fuck this project AP");
												return true;
											}
										}
									}
								}
								}
								newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project AQ");
										return true;
									}
								}
							}
						}
					}
						if (knightrow==6) {
							newknightrow=knightrow-2;
							newknightcol=knightcol-1;
							if (board[newknightrow][newknightcol]!=null) {
								if (board[newknightrow][newknightcol].currentPiece!=null) {
									if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
										if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
											System.out.println("Fuck this project AR");
											return true;
									}
								}
							}
						}
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project AS");
										return true;
									}
								}
							}
						}
					}
					if (knightrow>1 && knightrow<6) {
						newknightrow=knightrow-2;
						newknightcol=knightcol-1;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project AT");
										return true;
									}
								}
							}
						}
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project AU");
										return true;
									}
								}
							}
						}
						newknightrow=knightrow+2;
						newknightcol=knightcol-1;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project AV");
										return true;
									}
								}
							}
						}
						newknightcol=newknightcol+1;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project AW");
									return true;
									}
								}
							}
						}
					}
				}
				System.out.println("Fuck this project AX");
				return false;
			}

			if (knightcol>1 && knightcol<6) {
				if (knightrow==0) {
					newknightrow++;
					newknightcol-=2;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project AY");
									return true;
								}
							}
						}
					}
					newknightcol=knightcol+2;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project AZ");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow;
					newknightcol=knightcol;
					newknightrow=2;
					newknightcol--;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BA");
									return true;
								}
							}
						}
					}
					newknightrow=knightcol+1;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BB");
									return true;
								}
							}
						}
					System.out.println("Fuck this project ZZ");
					return false;
				}
				if (knightrow>0 && knightrow<6) {
					newknightrow--;
					newknightcol-=2;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BC");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow+1;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BD");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow;
					newknightcol=knightcol;
					newknightrow--;
					newknightcol+=2;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BE");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow+1;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BF");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow;
					newknightcol=knightcol;
					newknightrow+=2;
					newknightcol--;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BG");
									return true;
								}
							}
						}
					}
					newknightcol=knightcol+1;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BH");
									return true;
								}
							}
						}
					}

					if (knightrow>1 && knightrow<6) {
						newknightrow=knightrow;
						newknightcol=knightcol;
						newknightrow-=2;
						newknightcol--;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project BI");
										return true;
									}
								}
							}
						}
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project BJ");
										return true;
									}
								}
							}
						}
					}
					System.out.println("Fuck this project BK");
					return false;
				}

				if (knightrow==6) {
					newknightrow=knightrow;
					newknightcol=knightcol;
					newknightrow=4;
					newknightcol--;
					if (board[newknightrow][newknightcol]!=null) {  // moving from top to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BL");
									return true;
								}
							}
						}
					}
					newknightcol=knightcol+1;
					if (board[newknightrow][newknightcol]!=null) { // moving from top to right
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BM");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow;
					newknightcol=knightcol;
					newknightrow=7;
					newknightcol-=2;
					if (board[newknightrow][newknightcol]!=null) { // moving from left to bottom
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BN");
									return true;
								}
							}
						}
					}

					newknightcol=knightcol+2;
					if (board[newknightrow][newknightcol]!=null) { // moving from right to bottom
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BO");
									return true;
								}
							}
						}
					}
					newknightrow=5;
					newknightcol-=2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from left to top
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BP");
									return true;
								}
							}
						}
					}
					newknightcol=knightcol+2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from right to top
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BQ");
									return true;
								}
							}
						}
					}
					System.out.println("Fuck this project BQQ");
					return false;
				}
				if (knightrow==7) {
					newknightrow=5;
					newknightcol--;
					if (board[newknightrow][newknightcol]!=null) {  // moving from top to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BR");
									return true;
								}
							}
						}
					}

					newknightcol=knightcol+1;
					if (board[newknightrow][newknightcol]!=null) { // moving from top to right
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BS");
									return true;
								}
							}
						}
					}

					newknightrow=6;
					newknightcol=knightcol-2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from left to top
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BT");
									return true;
								}
							}
						}
					}
					newknightcol=knightcol+2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from right to top
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BU");
									return true;
								}
							}
						}
					}
					System.out.println("Fuck this project BV");
					return false;
				}
			}
			if (knightcol>5) {
				if (knightrow==0) {
					newknightrow=1;
					newknightcol=newknightcol-2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from top to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BX");
									return true;
								}
							}
						}
					}
					newknightrow=2;
					newknightcol=knightcol-1;
					if (board[newknightrow][newknightcol]!=null) {  // moving from topbottom to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project BY");
									return true;
								}
							}
						}
					}

					if (knightcol==6) {  // only if knight is located in 6th col and oth row
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {  // moving from right to bottom
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project BZ");
										return true;
									}
								}
							}
						}
					}
					System.out.println("Fuck this project CA");
					return false;
				}
				if (knightrow==1) {
					newknightrow=0;
					newknightcol=knightcol-2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from left to top
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project CB");
									return true;
								}
							}
						}
					}

					newknightrow=2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from left to bottom
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project CC");
									return true;
								}
							}
						}
					}
					newknightrow=3;
					newknightcol=knightcol-1;
					if (board[newknightrow][newknightcol]!=null) {  // moving from topbottom to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project CD");
									return true;
								}
							}
						}
					}
					if (knightcol==6) {  // only if knight is located in 6th col and oth row
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {  // moving from right to bottom
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project CE");
										return true;
									}
								}
							}
						}
					}
					System.out.println("Fuck this project CF");
					return false;
				}
				if (knightrow>1 && knightrow<6) {  // knight is between rows 2 and 5.
					newknightrow=newknightrow-2;
					newknightcol--;
					if (board[newknightrow][newknightcol]!=null) {  // moving from top to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project CG");
									return true;
								}
							}
						}
					}

					newknightrow=knightrow+2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from bottom to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project CH");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow-1;
					newknightcol=knightcol-2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from left to top
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project CI");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow+1;
					if (board[newknightrow][newknightcol]!=null) {  // moving from left to bottom
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project CJ");
									return true;
								}
							}
						}
					}

					if (knightcol==6) {
						newknightrow=knightrow+2;
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {  // moving from  bottom to right
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project CK");
										return true;
									}
								}
							}
						}
						newknightrow=knightrow-2;
						if (board[newknightrow][newknightcol]!=null) {  // moving from top to right
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project CL");
										return true;
									}
								}
							}
						}
					}
					System.out.println("Fuck this project CM");
					return false;
				}

				if (knightrow>5) {
					newknightrow=knightrow-2;
					newknightcol=knightcol-1;
					if (board[newknightrow][newknightcol]!=null) {  // moving from top to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project CN");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow-1;
					newknightcol=knightcol-2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from left totop
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project CO");
									return true;
								}
							}
						}
					}

					if (knightcol==6) {
						newknightrow=knightrow-1;
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {  // moving from right to bottom
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project CP");
										return true;
									}
								}
							}
						}
					}

					if (knightrow==6) {
						newknightrow=knightrow+1;
						newknightcol=knightcol-2;
						if (board[newknightrow][newknightcol]!=null) {  // moving from left to bottom
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='b') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project CQ");
										return true;
									}
								}
							}
						}
					}
					System.out.println("Fuck this project CR");
					return false;
				}
			}
		}
		}

		if (color=='w') {
			if (knightcol<2) { // if knight is located on top of board
				if (knightrow==0) { // if knight is located on top of board and in the first row
					System.out.println("LALALALA");
					newknightrow++;
					newknightcol+=2;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							System.out.println("not null did ran");
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project CS");
									return true;
								}
							}
						}
					}
					if (knightcol==0) {
						System.out.println("LALALALA2");
						newknightrow=knightrow+2;
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {  // moving from bottom to right
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project CT");
										return true;
									}
								}
							}
						}
					}
					if (knightcol==1) {
						System.out.println("LALALALA3");
						newknightrow=2;
						newknightcol=0;
						if (board[newknightrow][newknightcol]!=null) {  // moving from bottom to left
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project CU");
										return true;
									}
								}
							}
						}

						newknightcol=2;
						if (board[newknightrow][newknightcol]!=null) {  // moving from bottom to right
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project CV");
										return true;
									}
								}
							}
						}
					}
					System.out.println("Fuck this project CW");
					return false;
				}
				if (knightrow==7) { // if knight is located at the bottom of board. Moving from right to top
					newknightrow=knightrow-1;
					newknightcol=knightcol+2;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project CX");
									return true;
								}
							}
						}
					}

					if (knightcol==0) {
						newknightrow=5;
						newknightcol=1;
						if (board[newknightrow][newknightcol]!=null) {  // moving from top to right
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project CY");
									return true;
								}
							}
						}
					}
					if (knightcol==1) {
						newknightrow=5;
						newknightcol=0;
						if (board[newknightrow][newknightcol]!=null) {  // moving from top to left
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project CZ");
										return true;
									}
								}
							}
						}
						newknightcol=2;
						if (board[newknightrow][newknightcol]!=null) {  // moving from top to right
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project DA");
										return true;
									}
								}
							}
						}
					}
					System.out.println("Fuck this project DB");
					return false;
				}

				if (knightrow>0 && knightrow<7) {  // if knight
					newknightrow--;
					newknightcol+=2;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
							if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
								System.out.println("Fuck this project DC");
								return true;
							}
						}
					}

					newknightrow=knightrow+1;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project DD");
									return true;
								}
							}
						}
					}

					if (knightcol==0) {
						if (knightrow==1) {
							newknightrow=knightrow+2;
							newknightcol=knightcol+1;
							if (board[newknightrow][newknightcol]!=null) { // goes down and to the right
								if (board[newknightrow][newknightcol].currentPiece!=null) {
									if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
										if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
											System.out.println("Fuck this project DE");
											return true;
										}
									}
								}
							}
						}
						if (knightrow==6) {
							newknightrow=knightrow-2;
							newknightcol=knightcol+1;
							if (board[newknightrow][newknightcol]!=null) {  // goes up and to the right
								if (board[newknightrow][newknightcol].currentPiece!=null) {
									if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
										if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
											System.out.println("Fuck this project DF");
											return true;
										}
									}
								}
							}
						}
						if (knightrow>1 && knightrow<6) {
							newknightrow=knightrow+2;
							newknightcol=knightcol+1;
							if (board[newknightrow][newknightcol]!=null) {
								if (board[newknightrow][newknightcol].currentPiece!=null) {
									if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
										if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
											System.out.println("Fuck this project DG");
											return true;
										}
									}
								}
							}
							newknightrow=knightrow-2;
							newknightcol=knightcol+1;
							if (board[newknightrow][newknightcol]!=null) {
								if (board[newknightrow][newknightcol].currentPiece!=null) {
									if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
										if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
											System.out.println("Fuck this project DH");
											return true;
										}
									}
								}
							}
						}
					}
					if (knightcol==1) {
						if (knightrow==1) {
							newknightrow=knightrow+2;
							newknightcol=knightcol-1;
								if (board[newknightrow][newknightcol]!=null) {
									if (board[newknightrow][newknightcol].currentPiece!=null) {
										if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
											if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
												System.out.println("Fuck this project DI");
												return true;
											}
										}
									}
								}
								newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project DJ");
									return true;
								}
							}
						}
					}
						if (knightrow==6) {
							newknightrow=knightrow-2;
							newknightcol=knightcol-1;
							if (board[newknightrow][newknightcol]!=null) {
								if (board[newknightrow][newknightcol].currentPiece!=null) {
									if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
										if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
											System.out.println("Fuck this project DK");
											return true;
									}
								}
							}
						}
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project DL");
										return true;
									}
								}
							}
						}
					}
					if (knightrow>1 && knightrow<6) {
						newknightrow=knightrow-2;
						newknightcol=knightcol-1;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project DM");
										return true;
									}
								}
							}
						}
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project DN");
										return true;
									}
								}
							}
						}
						newknightrow=knightrow+2;
						newknightcol=knightcol-1;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project DO");
										return true;
									}
								}
							}
						}
						newknightcol=newknightcol+1;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project DP");
									return true;
									}
								}
							}
						}
					}
				}
			}
			System.out.println("Fuck this project DQ");
			return false;
		}
			if (knightcol>1 && knightcol<6) {
				if (knightrow==0) {
					newknightrow++;
					newknightcol-=2;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project DR");
									return true;
								}
							}
						}
					}
					newknightcol=knightcol+2;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project DS");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow;
					newknightcol=knightcol;
					newknightrow=2;
					newknightcol--;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project DT");
									return true;
								}
							}
						}
					}
					newknightrow=knightcol+1;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project DV");
									return true;
								}
							}
						}
					}
				}
				if (knightrow>0 && knightrow<6) {
					newknightrow--;
					newknightcol-=2;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project DW");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow+1;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project DX");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow;
					newknightcol=knightcol;
					newknightrow--;
					newknightcol+=2;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project DY");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow+1;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project DZ");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow;
					newknightcol=knightcol;
					newknightrow+=2;
					newknightcol--;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project EA");
									return true;
								}
							}
						}
					}
					newknightcol=knightcol+1;
					if (board[newknightrow][newknightcol]!=null) {
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project EB");
									return true;
								}
							}
						}
					}

					if (knightrow>1 && knightrow<6) {
						newknightrow=knightrow;
						newknightcol=knightcol;
						newknightrow-=2;
						newknightcol--;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project EC");
										return true;
									}
								}
							}
						}
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project ED");
										return true;
									}
								}
							}
						}
					}
					System.out.println("Fuck this project EE");
					return false;
				}

				if (knightrow==6) {
					newknightrow=knightrow;
					newknightcol=knightcol;
					newknightrow=4;
					newknightcol--;
					if (board[newknightrow][newknightcol]!=null) {  // moving from top to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project EF");
									return true;
								}
							}
						}
					}
					newknightcol=knightcol+1;
					if (board[newknightrow][newknightcol]!=null) { // moving from top to right
						if (board[newknightrow][newknightcol].currentPiece!=null) {
						if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
							if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
								System.out.println("Fuck this project EG");
								return true;
							}
						}
						}
					}
					newknightrow=knightrow;
					newknightcol=knightcol;
					newknightrow=7;
					newknightcol-=2;
					if (board[newknightrow][newknightcol]!=null) { // moving from left to bottom
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project EH");
									return true;
								}
							}
						}
					}

					newknightcol=knightcol+2;
					if (board[newknightrow][newknightcol]!=null) { // moving from right to bottom
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project EI");
									return true;
								}
							}
						}
					}
					newknightrow=5;
					newknightcol-=2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from left to top
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project EJ");
									return true;
								}
							}
						}
					}
					newknightcol=knightcol+2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from right to top
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project EK");
									return true;
								}
							}
						}
					}
					System.out.println("Fuck this project EL");
					return false;
				}
				if (knightrow==7) {
					newknightrow=5;
					newknightcol--;
					if (board[newknightrow][newknightcol]!=null) {  // moving from top to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project EM");
									return true;
								}
							}
						}
					}

					newknightcol=knightcol+1;
					if (board[newknightrow][newknightcol]!=null) { // moving from top to right
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project EN");
									return true;
								}
							}
						}
					}

					newknightrow=6;
					newknightcol=knightcol-2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from left to top
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project EO");
										return true;
								}
							}
						}
					}
					newknightcol=knightcol+2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from right to top
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project EP");
									return true;
								}
							}
						}
					}
					System.out.println("Fuck this project EQ");
					return false;
				}
			}
			if (knightcol>5) {
				if (knightrow==0) {
					newknightrow=1;
					newknightcol=newknightcol-2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from top to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project ER");
										return true;
									}
								}
							}
						}
					newknightrow=2;
					newknightcol=knightcol-1;
					if (board[newknightrow][newknightcol]!=null) {  // moving from topbottom to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project ES");
									return true;
								}
							}
						}
					}

					if (knightcol==6) {  // only if knight is located in 6th col and oth row
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {  // moving from right to bottom
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project ET");
										return true;
									}
								}
							}
						}
					}
					System.out.println("Fuck this project EU");
					return false;
				}
				if (knightrow==1) {
					newknightrow=0;
					newknightcol=knightcol-2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from left to top
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project EV");
									return true;
								}
							}
						}
					}

					newknightrow=2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from left to bottom
						if (board[newknightrow][newknightcol].currentPiece!=null) {
						if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
							if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
								System.out.println("Fuck this project EW");
								return true;
								}
							}
						}
					}
					newknightrow=3;
					newknightcol=knightcol-1;
					if (board[newknightrow][newknightcol]!=null) {  // moving from topbottom to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project EX");
									return true;
								}
							}
						}
					}
					if (knightcol==6) {  // only if knight is located in 6th col and oth row
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {  // moving from right to bottom
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project EY");
										return true;
									}
								}
							}
						}
					}
					System.out.println("Fuck this project EZ");
					return false;
				}
				if (knightrow>1 && knightrow<6) {  // knight is between rows 2 and 5.
					newknightrow=newknightrow-2;
					newknightcol--;
					if (board[newknightrow][newknightcol]!=null) {  // moving from top to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project FA");
									return true;
								}
							}
						}
					}

					newknightrow=knightrow+2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from bottom to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project FB");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow-1;
					newknightcol=knightcol-2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from left to top
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project FC");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow+1;
					if (board[newknightrow][newknightcol]!=null) {  // moving from left to bottom
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project FD");
									return true;
								}
							}
						}
					}

					if (knightcol==6) {
						newknightrow=knightrow+2;
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {  // moving from  bottom to right
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project FE");
										return true;
									}
								}
							}
						}
						newknightrow=knightrow-2;
						if (board[newknightrow][newknightcol]!=null) {  // moving from top to right
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project FF");
										return true;
									}
								}
							}
						}
					}
					System.out.println("Fuck this project FG");
					return false;
				}

				if (knightrow>5) {
					newknightrow=knightrow-2;
					newknightcol=knightcol-1;
					if (board[newknightrow][newknightcol]!=null) {  // moving knightrom top to left
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project FH");
									return true;
								}
							}
						}
					}
					newknightrow=knightrow-1;
					newknightcol=knightcol-2;
					if (board[newknightrow][newknightcol]!=null) {  // moving from left totop
						if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project FI");
									return true;
								}
							}
						}
					}

					if (knightcol==6) {
						newknightrow=knightrow-1;
						newknightcol=knightcol+1;
						if (board[newknightrow][newknightcol]!=null) {  // moving from right to bottom
							if (board[newknightrow][newknightcol].currentPiece!=null) {
								if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
									if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
										System.out.println("Fuck this project FJ");
										return true;
									}
								}
							}
						}
					}

					if (knightrow==6) {
						newknightrow=knightrow+1;
						newknightcol=knightcol-2;
						if (board[newknightrow][newknightcol]!=null) {  // moving from left to bottom
							if (board[newknightrow][newknightcol].currentPiece!=null) {
							if (board[newknightrow][newknightcol].currentPiece.getColor()=='w') {
								if (board[newknightrow][newknightcol].currentPiece.getName()=='K') {
									System.out.println("Fuck this project FK");
									return true;
								}
							}
							}
						}
					}
					System.out.println("Fuck this project FL");
					return false;
				}
			}
		}
			System.out.println("Fuck this project FM");
			return false;

	}

	static boolean Queencheck (int queenrow, int queencol, char color, int row, int col) {

		int counterrows=row;
		int countercolumns=col;
		boolean isqueencheck=false;
		boolean runthrough=false;

		if (queenrow==row) {  // if queen piece is the same row as King
			return true;
		}
		if (queencol==col) { // if queen piece is the same row as King
			return true;
		}

		if (color=='b') {
			if (col==7) {
				while (runthrough==false || isqueencheck==false) {
					if (row==0 && col==7) {
						System.out.println("Fuck this project FN");
						break;
					}
					counterrows--;
					countercolumns--;
					System.out.println("Fuck this project WERT");
					if (board[counterrows][countercolumns]!=null) {
						if (board[counterrows][countercolumns].currentPiece!=null) {
							if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
								if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
									System.out.println("Fuck this project FO");
									isqueencheck=true;
								}
							}
						}
					}
					if (counterrows==0) {
						runthrough=true;
						counterrows=row;
						countercolumns=col;
						System.out.println("Fuck this project FFFFFFF");
					}
				}
				if (isqueencheck) {
					System.out.println("Fuck this project BYEEEEEE");
					return true;
				}
				else {
					runthrough=false;
					while (runthrough==false || isqueencheck==false) {
						if (row==7 && col==7) {
							System.out.println("Fuck this project YYYYYYY");
							break;
						}
						counterrows++;
						countercolumns--;
						System.out.println("Fuck this project DDDDDD");
						if (board[counterrows][countercolumns]!=null) {
							if (board[counterrows][countercolumns].currentPiece!=null) {
								if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
									if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
										System.out.println("Fuck this project FP");
										isqueencheck=true;
									}
								}
							}
						}
						if (counterrows==7) {
							runthrough=true;
							counterrows=row;
							countercolumns=col;
							System.out.println("Fuck this project HHHHH");
						}
					}
				}
			}
			if (isqueencheck==true) {
				System.out.println("Fuck this project FQ");
				return true;
			}
			else {
				System.out.println("Fuck this project FR");
				return false;
			}


		}

		if (color=='b') {
			if (row==0) {  // if king is top of board
				while (runthrough==false || isqueencheck==false) { // checking diagonal from top first row to all the way left
				if (row==0 && col==0) { // if king is in top left corner
					System.out.println("Fuck this project GGGGGGG");
					break;
				}
				counterrows--;
				countercolumns++;
				System.out.println("Fuck this project PPPPPP");
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
								isqueencheck=true;
								System.out.println("Fuck this project FS");
							}
						}
					}
				}
				if (countercolumns==0) {
					runthrough=true;
					counterrows=row;
					countercolumns=col;
					System.out.println("Fuck this project QQQQ");
				}
			}
				if (isqueencheck) {
					System.out.println("Fuck this project FT");
					return true;
				}
				else {
					runthrough=false;
					while (runthrough==false || isqueencheck==false) {
						counterrows++;
						countercolumns++;
						System.out.println("Fuck this project XXXXXX");
						if (board[counterrows][countercolumns]!=null) {
							if (board[counterrows][countercolumns].currentPiece!=null) {
								if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
									if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
										isqueencheck=true;
										System.out.println("Fuck this project FU");
									}
								}
							}
						}
						if (countercolumns==7) {
							runthrough=true;
							counterrows=row;
							countercolumns=col;
							System.out.println("Fuck this project XXXXXX");
						}
					}
				}
		}
			if (isqueencheck==true) {
				System.out.println("Fuck this project FV");
				return true;
			}
			else {
				System.out.println("Fuck this project FW");
				return false;
			}
	}

		if (color=='b') { // if type is black
			if (col==0) { // king is located on left side of board
				while (runthrough==false || isqueencheck==false) {  // checking diagonals from bottom left to top right
					counterrows--;
					countercolumns++;
					System.out.println("Fuck this project BBBBBBB");
					if (board[counterrows][countercolumns]!=null) {
						if (board[counterrows][countercolumns].currentPiece!=null) {
							if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
								if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
									isqueencheck=true;
									System.out.println("Fuck this project FX");
								}
							}
						}
					}
					if (counterrows==0) {
						runthrough=true;
						counterrows=row;
						countercolumns=col;
						System.out.println("Fuck this project AAAAAAA");
					}
				}
				if (isqueencheck) {
					System.out.println("Fuck this project FY");
					return true;
				}
				else {
					runthrough=false;

					while (runthrough==false || isqueencheck==false) { // check diagonals from top left to bottom right
						if (row==7 && col==0) {
							System.out.println("Fuck this project AQWWESSA");
							break;
						}
						counterrows++;
						countercolumns++;
						System.out.println("Fuck this project WXYZ");
						if (board[counterrows][countercolumns]!=null) {
							if (board[counterrows][countercolumns].currentPiece!=null) {
								if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
									if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
										isqueencheck=true;
										System.out.println("Fuck this project FZ");
									}
								}
							}
						}
						if (counterrows==7) {
							System.out.println("Fuck this project GA");
							runthrough=true;
							counterrows=row;
							countercolumns=col;
						}
					}
				}

			}
			if (isqueencheck==true) {
				System.out.println("Fuck this project GB");
				return true;
			}
			else {
				System.out.println("Fuck this project GC");
				return false;
			}

		}

		if (color=='b') { // if type of king is black
			if (row==7) { //king is located at the bottom of board . Scenario 1
				while (runthrough==false || isqueencheck==false) { // checking diagonals bottom right to top right.
				counterrows--;
				countercolumns--;
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
								isqueencheck=true;
								System.out.println("Fuck this project GD");
							}
						}
					}
				}
				if (countercolumns==0) { // possible bishop cannot be further than column 0 or row 0. set them to original king position.
					counterrows=row;
					countercolumns=col;
					runthrough=true;
					System.out.println("Fuck this project GE");
				}
			}

			if (isqueencheck==true) {
				System.out.println("Fuck this project GF");
				return true;
			}
			else {
				runthrough=false;
				while (runthrough==false || isqueencheck==false) {
				counterrows--;
				countercolumns++;
				System.out.println("Fuck this project QL");
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
								isqueencheck=true;
								System.out.println("Fuck this project GG");
							}
						}
					}
				}
				if (countercolumns==7) {
					System.out.println("Fuck this project GH");
					runthrough=true;
				}
			}
			}

		}
		if (isqueencheck==true) {
			System.out.println("Fuck this project GI");
			return true;
		}
		else {
			System.out.println("Fuck this project GJ");
			return false;
		}
	}

		if (color=='b') { // if the king is not located on all sides
			while (isqueencheck==false || runthrough==false) {  // checking diagonals to left of king
				counterrows--;
				countercolumns--;
				System.out.println("Fuck this project QK");
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
								System.out.println("Fuck this project GK");
								isqueencheck=true;
							}
						}
					}
				}
				if (countercolumns==0 || counterrows==0) { // checking ends because of NPE exception
					counterrows=row;
					countercolumns=col;
					runthrough=true;
					System.out.println("Fuck this project GL");
				}

			}
			if (isqueencheck) {
				System.out.println("Fuck this project GM");
				return true;
			}
			else {
				runthrough=true;
				while (isqueencheck==false || runthrough==false) { // checking diagonals to the right of king
					counterrows++;
					countercolumns++;
					System.out.println("Fuck this project GQ");
				}
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
								isqueencheck=true;
								System.out.println("Fuck this project GQ");
							}
						}
					}
				}
				if (counterrows==7) {
					runthrough=true;
					System.out.println("Fuck this project GN");
				}
			}

			if (isqueencheck) {
				System.out.println("Fuck this project GO");
				return true;
			}
			else {
				System.out.println("Fuck this project GP");
				return false;
			}
		}

		if (color=='w') { // if type king is white. Type 1
			if (col==7) {
				while (runthrough==false || isqueencheck==false) {  // type 1.
					if (row==0 && col==7) {
						System.out.println("Fuck this project GR");
						break;
					}
					counterrows--;
					countercolumns--;
					if (board[counterrows][countercolumns]!=null) {
						if (board[counterrows][countercolumns].currentPiece!=null) {
							if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
								if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
									isqueencheck=true;
									System.out.println("Fuck this project GS");
								}
							}
						}
					}
					if (counterrows==0) {
						runthrough=true;
						counterrows=row;
						countercolumns=col;
						System.out.println("Fuck this project GT");
					}
				}
				if (isqueencheck) {
					System.out.println("Fuck this project GU");
					return true;
				}
				else {
					runthrough=false;
					while (runthrough==false || isqueencheck==false) {
						if (row==7 && col==7) {
							System.out.println("Fuck this project GV");
							break;
						}
						counterrows++;
						countercolumns--;
						if (board[counterrows][countercolumns]!=null) {
							if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
								if (board[counterrows][countercolumns].currentPiece!=null) {
								if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
									isqueencheck=true;
									System.out.println("Fuck this project GW");
								}
							}
							}
						}
						if (counterrows==7) {
							runthrough=true;
							counterrows=row;
							countercolumns=col;
							System.out.println("Fuck this project GX");
						}
					}
				}
			}
			if (isqueencheck==true) {
				System.out.println("Fuck this project GY");
				return true;
			}
			else {
				System.out.println("Fuck this project GZ");
				return false;
			}
		}

		if (color=='w') {  // type 2
			if (row==0) {  // if king is top of board
				while (runthrough==false || isqueencheck==false) { // checking diagonal from top first row to all the way left
				if (row==0 && col==0) { // if king is in top left corner
					System.out.println("Fuck this project HA");
					break;
				}
				counterrows--;
				countercolumns++;
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
								isqueencheck=true;
								System.out.println("Fuck this project HB");
							}
						}
					}
				}
				if (countercolumns==0) {
					runthrough=true;
					counterrows=row;
					countercolumns=col;
					System.out.println("Fuck this project HC");
				}
			}
				if (isqueencheck) {
					System.out.println("Fuck this project HD");
					return true;
				}
				else {
					runthrough=false;
					while (runthrough==false || isqueencheck==false) {
						counterrows++;
						countercolumns++;
						System.out.println("Fuck this project WU");
						if (board[counterrows][countercolumns]!=null) {
							if (board[counterrows][countercolumns].currentPiece!=null) {
								if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
									if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
										isqueencheck=true;
										System.out.println("Fuck this project HE");
									}
								}
							}
						}
						if (countercolumns==7) {
							runthrough=true;
							counterrows=row;
							countercolumns=col;
							System.out.println("Fuck this project HF");
						}
					}
				}
		}
			if (isqueencheck==true) {
				System.out.println("Fuck this project HG");
				return true;
			}
			else {
				System.out.println("Fuck this project HH");
				return false;
			}
	}

		if (color=='w') { // if type is black. Type 3
			if (col==0) { // king is located on left side of board
				while (runthrough==false || isqueencheck==false) {  // checking diagonals from bottom left to top right
					counterrows--;
					countercolumns++;
					System.out.println("Fuck this project QD");
					if (board[counterrows][countercolumns]!=null) {
						if (board[counterrows][countercolumns].currentPiece!=null) {
							if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
								if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
									isqueencheck=true;
									System.out.println("Fuck this project HI");
								}
							}
						}
					}
					if (counterrows==0) {
						runthrough=true;
						counterrows=row;
						countercolumns=col;
						System.out.println("Fuck this project HJ");
					}
				}
				if (isqueencheck) {
					System.out.println("Fuck this project HK");
					return true;
				}
				else {
					runthrough=false;

					while (runthrough==false || isqueencheck==false) { // check diagonals from top left to bottom right
						if (row==7 && col==0) {
							System.out.println("Fuck this project HL");
							break;
						}
						counterrows++;
						countercolumns++;
						if (board[counterrows][countercolumns]!=null) {
							if (board[counterrows][countercolumns].currentPiece!=null) {
								if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
									if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
										isqueencheck=true;
										System.out.println("Fuck this project HM");
									}
								}
							}
						}
						if (counterrows==7) {
							runthrough=true;
							counterrows=row;
							countercolumns=col;
							System.out.println("Fuck this project HN");
						}
					}
				}

			}
			if (isqueencheck==true) {
				System.out.println("Fuck this project HO");
				return true;
			}
			else {
				System.out.println("Fuck this project HP");
				return false;
			}

		}

		if (color=='w') { // if type of king is black
			if (row==7) { //king is located at the bottom of board . Scenario 1
				while (runthrough==false || isqueencheck==false) { // checking diagonals bottom right to top right.
				counterrows--;
				countercolumns--;
				System.out.println("Fuck this project Qw");
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
								isqueencheck=true;
								System.out.println("Fuck this project HQ");
							}
						}
					}
				}
				if (countercolumns==0) { // possible bishop cannot be further than column 0 or row 0. set them to original king position.
					counterrows=row;
					countercolumns=col;
					runthrough=true;
					System.out.println("Fuck this project HR");
				}
			}

			if (isqueencheck==true) {
				System.out.println("Fuck this project HS");
				return true;
			}
			else {
				runthrough=false;
				while (runthrough==false || isqueencheck==false) {
				counterrows--;
				countercolumns++;
				System.out.println("Fuck this project WW");
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
								isqueencheck=true;
								System.out.println("Fuck this project HT");
							}
						}
					}
				}
				if (countercolumns==7) {
					System.out.println("Fuck this project HU");
					runthrough=true;
				}
			}
			}

		}
		if (isqueencheck==true) {
			System.out.println("Fuck this project HV");
			return true;
		}
		else {
			System.out.println("Fuck this project HW");
			return false;
		}
	}
		if (color=='w') { // if the king is not located on all sides. type 5
			while (isqueencheck==false || runthrough==false) {  // checking diagonals to left of king
				counterrows--;
				countercolumns--;
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
								isqueencheck=true;
								System.out.println("Fuck this project HX");
							}
						}
					}
				}
				if (countercolumns==0 || counterrows==0) { // checking ends because of NPE exception
					counterrows=row;
					countercolumns=col;
					runthrough=true;
					System.out.println("Fuck this project HY");
				}

			}
			if (isqueencheck) {
				System.out.println("Fuck this project HZ");
				return true;
			}
			else {
				runthrough=true;
				while (isqueencheck==false || runthrough==false) { // checking diagonals to the right of king
					counterrows++;
					countercolumns++;
					System.out.println("Fuck this project IA");
				}
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='Q') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
								isqueencheck=true;
								System.out.println("Fuck this project IB");
							}
						}
					}
				}
				if (counterrows==7) {
					runthrough=true;
					System.out.println("Fuck this project IC");
				}
			}

			if (isqueencheck) {
				System.out.println("Fuck this project ID");
				return true;
			}
		}
		System.out.println("Fuck this project IE");
		return false;
	}

	static boolean Bishopcheck (char type, int row, int col) {   // parameter pass position of current king

		int counterrows=row;
		int countercolumns=col;
		boolean ischeck=false;
		boolean runthrough=false;

		if (type=='b') {
			if (col==7) {
				while (runthrough==false || ischeck==false) {
					if (row==0 && col==7) {
						System.out.println("Fuck this project IF");
						break;
					}
					counterrows--;
					countercolumns--;
					System.out.println("Fuck this project IH");
					if (board[counterrows][countercolumns]!=null) {
						if (board[counterrows][countercolumns].currentPiece!=null) {
							if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
								if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
									System.out.println("Fuck this project IG");
									ischeck=true;
								}
							}
						}
					}
					if (counterrows==0) {
						runthrough=true;
						counterrows=row;
						countercolumns=col;
						System.out.println("Fuck this project II");
					}
				}
				if (ischeck) {
					System.out.println("Fuck this project IJ");
					return true;
				}
				else {
					runthrough=false;
					while (runthrough==false || ischeck==false) {
						if (row==7 && col==7) {
							System.out.println("Fuck this project IK");
							break;
						}
						counterrows++;
						countercolumns--;
						System.out.println("Fuck this project IL");
						if (board[counterrows][countercolumns]!=null) {
							if (board[counterrows][countercolumns].currentPiece!=null) {
								if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
									if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
										ischeck=true;
										System.out.println("Fuck this project IM");
									}
								}
							}
						}
						if (counterrows==7) {
							runthrough=true;
							counterrows=row;
							countercolumns=col;
							System.out.println("Fuck this project IN");
						}
					}
				}
			}
			if (ischeck==true) {
				System.out.println("Fuck this project IO");
				return true;
			}
			else {
				System.out.println("Fuck this project IP");
				return false;
			}


		}

		if (type=='b') {
			if (row==0) {  // if king is top of board
				while (runthrough==false || ischeck==false) { // checking diagonal from top first row to all the way left
				if (row==0 && col==0) { // if king is in top left corner
					System.out.println("Fuck this project IQ");
					break;
				}
				counterrows--;
				countercolumns++;
				System.out.println("Fuck this project IR");
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
								ischeck=true;
								System.out.println("Fuck this project IS");
							}
						}
					}
				}
				if (countercolumns==0) {
					runthrough=true;
					counterrows=row;
					countercolumns=col;
					System.out.println("Fuck this project IT");
				}
			}
				if (ischeck) {
					System.out.println("Fuck this project IU");
					return true;
				}
				else {
					runthrough=false;
					while (runthrough==false || ischeck==false) {
						counterrows++;
						countercolumns++;
						System.out.println("Fuck this Project XB");
						if (board[counterrows][countercolumns]!=null) {
							if (board[counterrows][countercolumns].currentPiece!=null) {
								if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
									if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
										ischeck=true;
										System.out.println("Fuck this project IV");
									}
								}
							}
						}
						if (countercolumns==7) {
							runthrough=true;
							counterrows=row;
							countercolumns=col;
							System.out.println("Fuck this project IW");
						}
					}
				}
		}
			if (ischeck==true) {
				System.out.println("Fuck this project IX");
				return true;
			}
			else {
				System.out.println("Fuck this project IY");
				return false;
			}
	}

		if (type=='b') { // if type is black
			if (col==0) { // king is located on left side of board
				while (runthrough==false || ischeck==false) {  // checking diagonals from bottom left to top right
					counterrows--;
					countercolumns++;
					System.out.println("Fuck this project IZ");
					if (board[counterrows][countercolumns]!=null) {
						if (board[counterrows][countercolumns].currentPiece!=null) {
							if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
								if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
									ischeck=true;
									System.out.println("Fuck this project JA");
								}
							}
						}
					}
					if (counterrows==0) {
						runthrough=true;
						counterrows=row;
						countercolumns=col;
						System.out.println("Fuck this project JB");
					}
				}
				if (ischeck) {
					System.out.println("Fuck this project JC");
					return true;
				}
				else {
					runthrough=false;

					while (runthrough==false || ischeck==false) { // check diagonals from top left to bottom right
						if (row==7 && col==0) {
							System.out.println("Fuck this project JD");
							break;
						}
						counterrows++;
						countercolumns++;
						System.out.println("Fuck this project JE");
						if (board[counterrows][countercolumns]!=null) {
							if (board[counterrows][countercolumns].currentPiece!=null) {
								if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
									if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
										ischeck=true;
										System.out.println("Fuck this project JF");
									}
								}
							}
						}
						if (counterrows==7) {
							runthrough=true;
							counterrows=row;
							countercolumns=col;
							System.out.println("Fuck this project JG");
						}
					}
				}

			}
			if (ischeck==true) {
				System.out.println("Fuck this project JH");
				return true;
			}
			else {
				System.out.println("Fuck this project JI");
				return false;
			}

		}

		if (type=='b') { // if type of king is black
			if (row==7) { //king is located at the bottom of board . Scenario 1
				while (runthrough==false || ischeck==false) { // checking diagonals bottom right to top right.
				counterrows--;
				countercolumns--;
				System.out.println("Fuck this project JJ");
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
								ischeck=true;
								System.out.println("Fuck this project JK");
							}
						}
					}
				}
				if (countercolumns==0) { // possible bishop cannot be further than column 0 or row 0. set them to original king position.
					counterrows=row;
					countercolumns=col;
					runthrough=true;
					System.out.println("Fuck this project JL");
				}
			}

			if (ischeck==true) {
				System.out.println("Fuck this project JM");
				return true;
			}
			else {
				runthrough=false;
				while (runthrough==false || ischeck==false) {
				counterrows--;
				countercolumns++;
				System.out.println("Fuck this Project XA");
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
								ischeck=true;
								System.out.println("Fuck this project JN");
							}
						}
					}
				}
				if (countercolumns==7) {
					System.out.println("Fuck this project JO");
					runthrough=true;
				}
			}
			}

		}
		if (ischeck==true) {
			System.out.println("Fuck this project JP");
			return true;
		}
		else {
			System.out.println("Fuck this project JQ");
			return false;
		}
	}

		if (type=='b') { // if the king is not located on all sides
			while (ischeck==false || runthrough==false) {  // checking diagonals to left of king
				counterrows--;
				countercolumns--;
				System.out.println("Fuck this projec JR");
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
								ischeck=true;
								System.out.println("Fuck this project XX");
							}
						}

					}
				}
				if (countercolumns==0 || counterrows==0) { // checking ends because of NPE exception
					counterrows=row;
					countercolumns=col;
					runthrough=true;
					System.out.println("Fuck this project JS");
				}

			}
			if (ischeck) {
				System.out.println("Fuck this project JT");
				return true;
			}
			else {
				runthrough=true;
				while (ischeck==false || runthrough==false) { // checking diagonals to the right of king
					counterrows++;
					countercolumns++;
					System.out.println("Fuck this project JU");
				}
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='w') {
								ischeck=true;
								System.out.println("Fuck this project JW");
							}
						}
					}
				}
				if (counterrows==7) {
					System.out.println("Fuck this project JX");
					runthrough=true;
				}
			}

			if (ischeck) {
				System.out.println("Fuck this project JY");
				return true;
			}
			else {
				System.out.println("Fuck this project JZ");
				return false;
			}
		}

		if (type=='w') { // if type king is white. Type 1
			if (col==7) {
				while (runthrough==false || ischeck==false) {  // type 1.
					if (row==0 && col==7) {
						System.out.println("Fuck this project KA");
						break;
					}
					counterrows--;
					countercolumns--;
					System.out.println("Fuck this project KB");
					if (board[counterrows][countercolumns]!=null) {
						if (board[counterrows][countercolumns].currentPiece!=null) {
							if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
								if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
									ischeck=true;
									System.out.println("Fuck this project KC");
								}
							}
						}
					}
					if (counterrows==0) {
						runthrough=true;
						counterrows=row;
						countercolumns=col;
						System.out.println("Fuck this project KD");
					}
				}
				if (ischeck) {
					System.out.println("Fuck this project KE");
					return true;
				}
				else {
					runthrough=false;
					while (runthrough==false || ischeck==false) {
						if (row==7 && col==7) {
							System.out.println("Fuck this project KF");
							break;
						}
						counterrows++;
						countercolumns--;
						System.out.println("Fuck this project KG");
						if (board[counterrows][countercolumns]!=null) {
							if (board[counterrows][countercolumns].currentPiece!=null) {
								if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
									if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
										ischeck=true;
										System.out.println("Fuck this project KH");
									}
								}
							}
						}
						if (counterrows==7) {
							runthrough=true;
							counterrows=row;
							countercolumns=col;
							System.out.println("Fuck this project KI");
						}
					}
				}
			}
			if (ischeck==true) {
				System.out.println("Fuck this project KJ");
				return true;
			}
			else {
				System.out.println("Fuck this project KK");
				return false;
			}
		}

		if (type=='w') {  // type 2
			if (row==0) {  // if king is top of board
				while (runthrough==false || ischeck==false) { // checking diagonal from top first row to all the way left
				if (row==0 && col==0) { // if king is in top left corner
					System.out.println("Fuck this project KL");
					break;
				}
				counterrows--;
				countercolumns++;
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
								ischeck=true;
								System.out.println("Fuck this project KM");
							}
						}
					}
				}
				if (countercolumns==0) {
					runthrough=true;
					counterrows=row;
					countercolumns=col;
					System.out.println("Fuck this project KN");
				}
			}
				if (ischeck) {
					System.out.println("Fuck this project KO");
					return true;
				}
				else {
					runthrough=false;
					while (runthrough==false || ischeck==false) {
						counterrows++;
						countercolumns++;
						if (board[counterrows][countercolumns]!=null) {
							if (board[counterrows][countercolumns].currentPiece!=null) {
								if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
									if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
										ischeck=true;
										System.out.println("Fuck this project KP");
									}
								}
							}
						}
						if (countercolumns==7) {
							runthrough=true;
							counterrows=row;
							countercolumns=col;
							System.out.println("Fuck this project KQ");
						}
					}
				}
		}
			if (ischeck==true) {
				System.out.println("Fuck this project KR");
				return true;
			}
			else {
				System.out.println("Fuck this project KS");
				return false;
			}
	}

		if (type=='w') { // if type is black. Type 3
			if (col==0) { // king is located on left side of board
				while (runthrough==false || ischeck==false) {  // checking diagonals from bottom left to top right
					counterrows--;
					countercolumns++;
					System.out.println("Fuck this project KT");
					if (board[counterrows][countercolumns]!=null) {
						if (board[counterrows][countercolumns].currentPiece!=null) {
							if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
								if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
									ischeck=true;
									System.out.println("Fuck this project KU");
								}
							}
						}
					}
					if (counterrows==0) {
						runthrough=true;
						counterrows=row;
						countercolumns=col;
						System.out.println("Fuck this project KV");
					}
				}
				if (ischeck) {
					System.out.println("Fuck this project KW");
					return true;
				}
				else {
					runthrough=false;

					while (runthrough==false || ischeck==false) { // check diagonals from top left to bottom right
						if (row==7 && col==0) {
							System.out.println("Fuck this project KX");
							break;
						}
						counterrows++;
						countercolumns++;
						System.out.println("Fuck this project KY");
						if (board[counterrows][countercolumns]!=null) {
							if (board[counterrows][countercolumns].currentPiece!=null) {
								if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
									if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
										ischeck=true;
										System.out.println("Fuck this project KZ");
									}
								}
							}
						}
						if (counterrows==7) {
							runthrough=true;
							counterrows=row;
							countercolumns=col;
							System.out.println("Fuck this project LA");
						}
					}
				}

			}
			if (ischeck==true) {
				System.out.println("Fuck this project LB");
				return true;
			}
			else {
				System.out.println("Fuck this project LC");
				return false;
			}

		}

		if (type=='w') { // if type of king is black
			if (row==7) { //king is located at the bottom of board . Scenario 1
				while (runthrough==false || ischeck==false) { // checking diagonals bottom right to top right.
				counterrows--;
				countercolumns--;
				System.out.println("Fuck this project LD");
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
								ischeck=true;
								System.out.println("Fuck this project LE");
							}
						}
					}
				}
				if (countercolumns==0) { // possible bishop cannot be further than column 0 or row 0. set them to original king position.
					counterrows=row;
					countercolumns=col;
					runthrough=true;
					System.out.println("Fuck this project LF");
				}
			}

			if (ischeck==true) {
				System.out.println("Fuck this project LG");
				return true;
			}
			else {
				runthrough=false;
				while (runthrough==false || ischeck==false) {
				counterrows--;
				countercolumns++;
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
								ischeck=true;
								System.out.println("Fuck this project LH");
							}
						}
					}
				}
				if (countercolumns==7) {
					System.out.println("Fuck this project LI");
					runthrough=true;
				}
			}
			}

		}
		if (ischeck==true) {
			System.out.println("Fuck this project LJ");
			return true;
		}
		else {
			System.out.println("Fuck this project LK");
			return false;
		}
	}
		if (type=='w') { // if the king is not located on all sides. type 5
			while (ischeck==false || runthrough==false) {  // checking diagonals to left of king
				counterrows--;
				countercolumns--;
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
								ischeck=true;
								System.out.println("Fuck this project LL");
							}
						}
					}
				}
				if (countercolumns==0 || counterrows==0) { // checking ends because of NPE exception
					counterrows=row;
					countercolumns=col;
					runthrough=true;
					System.out.println("Fuck this project LM");
				}

			}
			if (ischeck) {
				System.out.println("Fuck this project LN");
				return true;
			}
			else {
				runthrough=true;
				while (ischeck==false || runthrough==false) { // checking diagonals to the right of king
					counterrows++;
					countercolumns++;
					System.out.println("Fuck this project LO");
				}
				if (board[counterrows][countercolumns]!=null) {
					if (board[counterrows][countercolumns].currentPiece!=null) {
						if (board[counterrows][countercolumns].getCurrentPiece().getName()=='B') {
							if (board[counterrows][countercolumns].getCurrentPiece().getColor()=='b') {
								ischeck=true;
								System.out.println("Fuck this project LP");
							}
						}
					}
				}
				if (counterrows==7) {
					System.out.println("Fuck this project LQ");
					runthrough=true;
				}
			}

			if (ischeck) {
				System.out.println("Fuck this project LR");
				return true;
			}
			else {
				System.out.println("Fuck this project LS");
				return false;
			}
		}

		System.out.println("Fuck this project LT");
		return false;
	}

	/**
	 * checkmate method
	 * check if the king of a specified color is in checkmate
	 * @param color
	 * @return void
	 */
	static void checkmate(char color) {
		// find king with input color
		int row = 0;
		int col = 0;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j].getCurrentPiece().getColor() == color && board[i][j].getCurrentPiece().getName() == 'K') {
					row = i;
					col = j;
				}
			}
		}
		// check if this king is in checkmate
		boolean gameover = true;
		// check all possible moves for king
		if(gameover != false && row < 7) {
			gameover = check(color, row+1, col);
		}
		if(gameover != false && row < 7 && col > 0) {
			gameover = check(color, row+1, col-1);
		}
		if(gameover != false && row < 7 && col < 7) {
			gameover = check(color, row+1, col+1);
		}
		if(gameover != false && col > 0) {
			gameover = check(color, row, col-1);
		}
		if(gameover != false && col > 0 && row > 0) {
			gameover = check(color, row-1, col-1);
		}
		if(gameover != false && row > 0) {
			gameover = check(color, row-1, col);
		}
		if(gameover != false && row >0 && col < 7) {
			gameover = check(color, row-1, col+1);
		}
		if(gameover != false && col < 7) {
			gameover = check(color, row, col+1);
		}
		// if king in checkmate, print Checkmate and declare winner
		if(gameover == true) {
			checkmate = true;
			System.out.println("Checkmate");
			System.out.println("");
			if(color == 'b') {
				System.out.println("White wins");
			} else if(color == 'w') {
				System.out.println("Black wins");
			}
			checkmate = true;
		}
	}
