public class ConnectFourAI extends ConnectFourPlayer
{
	public ConnectFourAI()
	{

	}

	public int getMove(int [][] board, int player){

		int[] moves = ConnectFourBoard.getMoves(board);
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int[] bestMove = minimax(board, 5, true, player);
        return moves[bestMove[1]];
    }

    private int[] minimax(int[][] board, int depth, boolean maximizingPlayer, int player)
    {
    	int[] arr = new int[2];
    	if ((ConnectFourBoard.hasWinner(board) == (3-player)))
		{
			//plainDraw(board);
			arr[0] = Integer.MIN_VALUE;
			arr[1] = 1;
			return arr;
		}
		if ((ConnectFourBoard.hasWinner(board) == player))
		{
			//plainDraw(board);
			arr[0] = Integer.MAX_VALUE;
			arr[1] = 1;
			return arr;
		}
		if ((depth == 0))
    	{	
    		arr[0] = evaluateBoard(board, player);
    		arr[1] = 1;
    		return arr;
    	}
    	
    	if (maximizingPlayer)
    	{
    		int maxEval = Integer.MIN_VALUE;
    		int[] moves = ConnectFourBoard.getMoves(board);
    		for (int move = 0; move < moves.length; move++)
    		{
    			int[][] newBoard = ConnectFourBoard.forecastMove(board, moves[move], player);
    			int[] eval = minimax(newBoard, depth - 1, false, player);
    			if (eval[0] >= maxEval)
    			{
    				arr[0] = eval[0];
    				arr[1] = move;
    				maxEval = eval[0];
    			}
    			/*
    			if (eval[0] > alpha)
    			{
    				alpha = eval[0];
    			}
    			if (beta <= alpha)
    			{
    				break;
    			}
    			*/
    			
    		}
    		return arr;
    	} 
    	else
    	{
    		int minEval = Integer.MAX_VALUE;
    		int[] moves = ConnectFourBoard.getMoves(board);
    		for (int move = 0; move < moves.length; move++)
    		{
    			int[][] newBoard = ConnectFourBoard.forecastMove(board, moves[move], 3-player);
    			int[] eval = minimax(newBoard, depth - 1, true, player);
    			if (eval[0] <= minEval)
    			{
    				arr[0] = eval[0];
    				arr[1] = move;
    				minEval = eval[0];
    			}
    			/*
    			if (eval[0] < beta)
    			{
    				beta = eval[0];
    			}
    			if (beta <= alpha)
    			{
    				break;
    			}
    			*/
    		}
    		return arr;
    	}
    }

    			/*
    			System.out.println("________________________");
    			plainDraw(newBoard);
    			System.out.println();
    			System.out.println("player: " + player + " eval: " + eval[0]);
    			System.out.println();
    			System.out.println("________________________");
    			*/
        			/*
    			System.out.println("________________________");
    			plainDraw(newBoard);
    			System.out.println();
    			System.out.println("player: " + player + " eval: " + eval[0]);
    			System.out.println();
    			System.out.println("________________________");
    			*/

    private int evaluateBoard(int[][] board, int player)
    {
    	int[][] boardWeights = createBoardWeights(board);
		int score = countSpots(board, boardWeights, player) + checkHorizontals(board, boardWeights, player) + checkVerticals(board, boardWeights, player) + checkDiagonals(board, boardWeights, player);
    	//System.out.println("Score Horizontal: " + checkHorizontals(board, boardWeights, player));
    	//System.out.println("Score Vertical: " + checkVerticals(board, boardWeights, player));
    	return score;	
    }


    private int countSpots(int[][] board, int[][] boardWeights, int player)
    {
    	int score = 0;
    	for (int c = 0; c < board[0].length; c++)
    	{
    		for (int r = 0; r < board.length; r++)
    		{
    			if (board[r][c] == player)
    			{
    				score += boardWeights[r][c];
    			}
    		}
    	}
    	//System.out.println("Score for player " + player +  ": " + score);
    	return score;
    }

    private void plainDraw(int[][] board){
        for (int j = 0; j < board[0].length; j++){
            System.out.print("|");
            for (int i = 0; i < board.length; i++){
                if (board[i][j] == 0){
                    System.out.print(" ");
                }
                else {
                    System.out.print(board[i][j]);
                }
                System.out.print("|");
            }
            System.out.println();
        } 
        System.out.println();
    }

    private int[][] createBoardWeights(int[][] board)
    {
    	int[][] boardWeights = new int[board.length][board[0].length];
    	
    	boardWeights[0][0] = 100;
    	boardWeights[0][1] = 100;
    	boardWeights[0][2] = 100;
    	boardWeights[0][3] = 100;
    	boardWeights[0][4] = 100;
    	boardWeights[0][5] = 100;
    	boardWeights[0][6] = 100;

    	boardWeights[1][0] = 200;
    	boardWeights[1][1] = 200;
    	boardWeights[1][2] = 200;
    	boardWeights[1][3] = 200;
    	boardWeights[1][4] = 200;
    	boardWeights[1][5] = 200;
    	boardWeights[1][6] = 200;

    	boardWeights[2][0] = 300;
    	boardWeights[2][1] = 300;
    	boardWeights[2][2] = 300;
    	boardWeights[2][3] = 300;
    	boardWeights[2][4] = 300;
    	boardWeights[2][5] = 300;
    	boardWeights[2][6] = 300;

    	boardWeights[3][0] = 400;
    	boardWeights[3][1] = 400;
    	boardWeights[3][2] = 400;
    	boardWeights[3][3] = 400;
    	boardWeights[3][4] = 400;
    	boardWeights[3][5] = 400;
    	boardWeights[3][6] = 400;

    	boardWeights[4][0] = 500;
    	boardWeights[4][1] = 500;
    	boardWeights[4][2] = 500;
    	boardWeights[4][3] = 500;
    	boardWeights[4][4] = 500;
    	boardWeights[4][5] = 500;
    	boardWeights[4][6] = 500;

    	boardWeights[5][0] = 400;
    	boardWeights[5][1] = 400;
    	boardWeights[5][2] = 400;
    	boardWeights[5][3] = 400;
    	boardWeights[5][4] = 400;
    	boardWeights[5][5] = 400;
    	boardWeights[5][6] = 400;

    	boardWeights[6][0] = 300;
    	boardWeights[6][1] = 300;
    	boardWeights[6][2] = 300;
    	boardWeights[6][3] = 300;
    	boardWeights[6][4] = 300;
    	boardWeights[6][5] = 300;
    	boardWeights[6][6] = 300;

    	boardWeights[7][0] = 200;
    	boardWeights[7][1] = 200;
    	boardWeights[7][2] = 200;
    	boardWeights[7][3] = 200;
    	boardWeights[7][4] = 200;
    	boardWeights[7][5] = 200;
    	boardWeights[7][6] = 200;

    	boardWeights[8][0] = 100;
    	boardWeights[8][1] = 100;
    	boardWeights[8][2] = 100;
    	boardWeights[8][3] = 100;
    	boardWeights[8][4] = 100;
    	boardWeights[8][5] = 100;
    	boardWeights[8][6] = 100;

    	return boardWeights;
    }

    private int checkDiagonals(int[][] board, int[][] boardWeights, int player)
    {
    	int score = 0;
    	//pos diag
    	for (int c = 0; c < board.length-1; c++)
    	{
    		for (int r = 0; r < board[0].length-1; r++)
    		{
    			if ((board[c][r] == player) && (board[c+1][r+1] == player))
    			{
    				score += (int) (0.5 * boardWeights[c][r]);
    			}
    		}
    	}
    	for (int c = board.length-1; c > 1; c--)
    	{
    		for (int r = 0; r < board[0].length-1; r++)
    		{
    			if ((board[c][r] == player) && (board[c-1][r+1] == player))
    			{
    				score += (int) (0.5 * boardWeights[c][r]);
    			}
    		}
    	}

    	return score;
    }


    private int checkHorizontals(int[][] board, int[][] boardWeights, int player)
    {
    	int score = 0;
    	for (int c = 1; c < board.length; c++)
    	{
    		for (int r = 0; r < board[0].length;r++)
    		{
    			//check backwards
    			if ((board[c][r] == player) && (board[c-1][r] == player))
    			{
    				score += (int) (0.5 * boardWeights[c][r]);
    			}
    		}
    	}
    	return score;
    }

    private int checkVerticals(int[][] board, int[][] boardWeights, int player)
    {
    	int score = 0;
    	for (int c = 0; c < board.length; c++)
    	{
    		for (int r = 1; r < board[0].length; r++)
    		{
    			//check above
    			if ((board[c][r] == player) && (board[c][r-1] == player))
    			{
    				score += (int) (0.5 * boardWeights[c][r]);
    			}
    		}
    	}
    	return score;
    }

}