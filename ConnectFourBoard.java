import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;

/**
 * Plays a game of connect four using two player objects
 * Displays output either graphically or via text
 * 
 * version 1.2
 */
public class ConnectFourBoard
{
    private int width;
    private int height;
    private int [][] board;  //0 = open, 1 = player 1, 2 == player 2
    private int currentPlayer;
    
    public static final int WIN_LENGTH = 4;
    
    //For graphics
    private BufferedImage drawer = null;
    private JFrame frame;
    private final int window_width = 512;  //window size
    private static final int window_height = 512;
    private static final int radius = 22;  //radius of the point
    
    private int xOffset;
    private int yOffset;
    
    
    public ConnectFourBoard(int width, int height){
        this.width = width;
        this.height = height;
        
        //setup graphics
        drawer = new BufferedImage(window_width, window_height, BufferedImage.TYPE_INT_RGB);
        frame = new JFrame("Connect Four: " + width + " X " + height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel labelImage = new JLabel(new ImageIcon(drawer));
        frame.getContentPane().add(labelImage);
        frame.pack();
        frame.setVisible(false);
        
        xOffset = window_width / 2 - this.width * radius;
        yOffset = window_height /  2 - this.height * radius;
        
    }
    
    public int play(ConnectFourPlayer one, ConnectFourPlayer two, boolean showGraphics, boolean showText){
        //reset the board
        board = new int[width][height];
        currentPlayer = 1;
        //loop until game ends
        while (hasWinner(this.board) == 0){
            //current player gets asked for a move
            int move;
            if (currentPlayer == 1){
                move = one.getMove(this.board, 1);
            }
            else {
                move = two.getMove(this.board, 2);
            }
            //update the board
            board = forecastMove(board, move, currentPlayer);
            if (board == null){
                System.out.println("Player " + currentPlayer + " has made an invalid move");
                return currentPlayer = ((currentPlayer % 2) + 1);
            }
            currentPlayer = ((currentPlayer % 2) + 1); 
            //draw the board
            if (showGraphics){
                graphicDraw();
                try
                {
                    Thread.sleep(400);
                }
                catch(InterruptedException ex)
                {
                    System.out.println(ex);
                }
            }
            if (showText){
                plainDraw();
            }
        }
        //there is a winner
        plainDraw();
        if (hasWinner(this.board) != -1){
            System.out.println("Player " + hasWinner(this.board) + " has won!");
        }
        else {
            System.out.println("The game resulted in a tie.");
        }
        if (showGraphics){
            Graphics2D g = drawer.createGraphics();
            int winner = hasWinner(this.board);
            if (winner == 1){
                g.setColor (Color.red);
            }
            else {
                g.setColor (Color.blue);
            } 
            g.drawString("Player " + winner + " has won!", xOffset, yOffset / 2);
            frame.repaint();
        }
        return hasWinner(this.board);
    }
    
    //returns an array containing all of the valid moves for a player
    public static int [] getMoves(int [][] board){
        int [] temp = new int[board.length];
        int count = 0;
        for (int i = 0; i < board.length; i++){
            if (board[i][0] == 0){
                temp[count] = i;
                count++;
            }
        }
        int [] ret = new int[count];
        for (int i = 0; i < ret.length; i++){
            ret[i] = temp[i];
        }
        return ret;
    }
    
    //returns a new board that has been updated with the given move
    //or null if the move is invalid
    public static int [][] forecastMove(int [][] board, int move, int currentPlayer){
        int [][] next = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++){
                next[i][j] = board[i][j];
            }
        }
        
        if (move < 0 || move >= board.length){
            return null;
        }
        
        for (int i = next[0].length - 1; i >= 0; i--){
            if (next[move][i] == 0){
                next[move][i] = currentPlayer;
                return next;
            }
        }
        
        return null;
    }
    
    //returns -1 if there is a tie (board is full)
    //returns 0 if there is no current winner 
    //1 if player 1 wins or 2 if player two wins
    public static int hasWinner(int [][] board){
        //check for a full board
        if (getMoves(board).length == 0){
            return -1;
        }
        
        //check if player 1 wins
        int player = 1;
        //look for vert
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length - WIN_LENGTH + 1; j++){
                boolean won = true;
                for (int k = 0; k < WIN_LENGTH; k++){
                    won = won && board[i][j + k] == player;
                }
                if (won) {
                    return player;
                }
            }
        }
        //look for hori
        for (int j = 0; j < board[0].length; j++){
            for (int i = 0; i < board.length - WIN_LENGTH + 1; i++){
                boolean won = true;
                for (int k = 0; k < WIN_LENGTH; k++){
                    won = won && board[i + k][j] == player;
                }
                if (won) {
                    return player;
                }
            }
        }
        //look for pos diag
        for (int i = 0; i < board.length - WIN_LENGTH + 1; i++){
            for (int j = 0; j < board[0].length - WIN_LENGTH + 1; j++){
                boolean won = true;
                for (int k = 0; k < WIN_LENGTH; k++){
                    won = won && board[i + k][j + k] == player;
                }
                if (won) {
                    return player;
                }
            }
        }
        //look for neg diag
        for (int i = 0; i < board.length - WIN_LENGTH + 1; i++){
            for (int j = WIN_LENGTH - 1; j < board[0].length; j++){
                boolean won = true;
                for (int k = 0; k < WIN_LENGTH; k++){
                    won = won && board[i + k][j - k] == player;
                }
                if (won) {
                    return player;
                }
            }
        }
        //check if player 2 wins
        player = 2;
        //look for vert
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length - WIN_LENGTH + 1; j++){
                boolean won = true;
                for (int k = 0; k < WIN_LENGTH; k++){
                    won = won && board[i][j + k] == player;
                }
                if (won) {
                    return player;
                }
            }
        }
        //look for hori
        for (int j = 0; j < board[0].length; j++){
            for (int i = 0; i < board.length - WIN_LENGTH + 1; i++){
                boolean won = true;
                for (int k = 0; k < WIN_LENGTH; k++){
                    won = won && board[i + k][j] == player;
                }
                if (won) {
                    return player;
                }
            }
        }
        //look for pos diag
        for (int i = 0; i < board.length - WIN_LENGTH + 1; i++){
            for (int j = 0; j < board[0].length - WIN_LENGTH + 1; j++){
                boolean won = true;
                for (int k = 0; k < WIN_LENGTH; k++){
                    won = won && board[i + k][j + k] == player;
                }
                if (won) {
                    return player;
                }
            }
        }
        //look for neg diag
        for (int i = 0; i < board.length - WIN_LENGTH + 1; i++){
            for (int j = WIN_LENGTH - 1; j < board[0].length; j++){
                boolean won = true;
                for (int k = 0; k < WIN_LENGTH; k++){
                    won = won && board[i + k][j - k] == player;
                }
                if (won) {
                    return player;
                }
            }
        }
        
        return 0;
    }
    
    //draw the board
    private void graphicDraw(){
        frame.setVisible(true);
        Graphics2D g = drawer.createGraphics();
        //int xOffset = window_width / 2 - this.width * radius;
        //int yOffset = window_height /  2 - this.height * radius;
        for (int j = 0; j < board[0].length; j++){
            for (int i = 0; i < board.length; i++){
                if (board[i][j] == 0){
                    g.setColor (Color.white);
                }
                else if (board[i][j] == 1){
                    g.setColor (Color.red);
                }
                else {
                    g.setColor (Color.blue);
                }
                g.fillOval(2 * i * radius + xOffset, 2 * j * radius + yOffset, 2*radius, 2*radius);
            }
        } 
        frame.repaint();
    }
    
    private void plainDraw(){
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
    
}
