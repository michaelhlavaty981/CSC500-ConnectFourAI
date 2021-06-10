/**
 * A simple AI that makes a random move each turn
 * Also includes an example of how to create a game and play ot
 */

public class RandomPlayer extends ConnectFourPlayer 
{
    
    public static void main(String [] args)
    {
        ConnectFourPlayer cpu = new HumanPlayer();
        ConnectFourPlayer ourAI = new ConnectFourAI();
        ConnectFourBoard board = new ConnectFourBoard(9, 7);
/*
        int numWins = 0;
        for (int i = 0; i < 50; i++)
        {
            if (board.play(cpu, ourAI, false, false) == 2)
            {
                numWins++;
            }
        }
        */
        board.play(cpu, ourAI, true, false);
        //System.out.println("Player 2 won: " + numWins +  " out of 50 games");
    }
    
    //returns a value between 0 and board.length
    //which corresponds to the column you want to put your piece in
    public int getMove(int [][] board, int player){
        int [] moves = ConnectFourBoard.getMoves(board);
        int m = (int) (moves.length * Math.random());
        return moves[m];
    }
}
