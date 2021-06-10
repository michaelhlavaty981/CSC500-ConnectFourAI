import java.util.Scanner;

/**
 * A connect four player that uses keyboard input
 * to allow a human to play the game
 */

public class HumanPlayer extends ConnectFourPlayer
{
    private Scanner s;
    
    public HumanPlayer(){
        s = new Scanner(System.in);
    }
    
    //returns a value between 0 and board.length
    //which corresponds to the column you want to put your piece in
    public int getMove(int [][] board, int player){
        int [] moves = ConnectFourBoard.getMoves(board);
        System.out.print("Enter the column you wish to move to: ");
        int move = s.nextInt();
        while (!contains(moves, move)){
            System.out.print("That move is invalid.  Try again: ");
            move = s.nextInt();
        }
        return move;
    }
    
    private boolean contains(int [] moves, int m){
        for (int i = 0; i < moves.length; i++){
            if (m == moves[i]){
                return true;
            }
        }
        return false;
    }
}
