// Abdul Ali
// alixx656

import java.util.*;
public class BattleshipBoard {
    private boolean DEBUG = false;
    String[][] board;
    int height, width, guesses, numberofShips;


    public static void main(String[] args) {
        BattleshipBoard b;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the size of the game board, i.e., a x b, where both 2 <= a,b <= 10 and a and b are separated by a space: "); // Prompt user for board dimensions
        if (s.hasNextInt()) {
            int h, w;
            h = s.nextInt();
            w = s.nextInt();
            while (h > 10 || h < 2 || w > 10 || w < 2) { // Error checking
                System.out.println("Re-enter board size, recall that the smallest board is 2x2 and the largest is 10x10: ");
                h = s.nextInt();
                w = s.nextInt();
            }
            b = new BattleshipBoard(h, w); // Create BattleshipBoard object
            System.out.println("Do you want to play in debug mode, enter 'y' for yes or 'n' for no"); // Ask user if they wish to play in debug mode and re-define 'DEBUG' accordingly
            if (s.next().equals("y")) {
                b.DEBUG = true;
            }
            // Start Game
            b.initBoard();
            b.setShips();
            b.showBoard();
            System.out.println("Number of ship(s): " + b.getNumberofShips());
            String[][] game = b.getBoard();
            for (int i = 0; i < b.getHeight(); i++) {
                for (int j = 0; j < b.getWidth(); j++) {

                    // Run the game while there is an un-sunk ship(s) on the board

                    while(game[i][j].equals("S")) {
                        b.shoot();
                        b.showBoard();
                    }
                }
            } System.out.println("You have beat the game in " + b.getGuesses() + " guesses.");
        }
    }

    public BattleshipBoard(int h, int w) { // Constructor
        this.height = h;
        this.width = w;
        this.board = new String[height][width];
        }

        // Simple Getter Methods

    public int getHeight() { return this.height; }
    public int getWidth() { return this.width; }
    public String[][] getBoard(){ return this.board;}
    public int getGuesses(){ return this.guesses; }
    public int getNumberofShips(){ return this.numberofShips; }

    public void initBoard() { //Initialize each position on the board to contain the string '-'
            for(int i = 0; i<height; i++) {
                for(int j = 0; j<width; j++) {
                    board[i][j] = "-";
                }
            }
    }

    public void showBoard() { // Displays board only when game is being played in DEBUG mode.
        if(DEBUG) {
            System.out.println("___________________________________________");
            System.out.println(" ");
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    System.out.print(" " + board[i][j]);
                }
                System.out.println(" ");
            }
        }
    }

    public void createShip(int shipSize){
        int decr = shipSize-1;
        if (Math.random() > 0.5) { // Creates Horizontal Ship

            // Generates random position on board to begin placing ship

            int col = (int)(Math.random() * (width - (shipSize - 1)));
            int row = (int)(Math.random() * (height - 1));

            for(int i = 0; i < shipSize; i++) {
                if (board[row][col+i].equals("S")){ // Makes sure none of the ships overlap
                    row = (int)(Math.random() * (height - 1));
                }
                board[row][col + i] = "S";
            }
        } else { // Creates Vertical Ship

            // Generates random position on board to begin placing ship

            int col = (int)(Math.random() * (width - 1));
            int row = (int)(Math.random() * (height - (shipSize - 1)));

            for(int i = 0; i < shipSize; i++) {
                if (board[row+i][col].equals("S")){ // Makes sure none of the ships overlap
                    col = (int)(Math.random() * (width-1));
                }
                board[row + i][col] = "S";
            }
        }
    }
    // Prompts user for coordinates to fire at and checks if location was a hit or miss

    public void shoot() {
        Scanner s = new Scanner(System.in);
        int x, y;
        System.out.println("Enter a row to fire in: ");
        x = s.nextInt();
        while (x > height || x < 1) { // Error checking, entering invalid firing locations doesn't increment number of guesses.
            System.out.println("Enter a valid row; 1 - " + height + ": ");
            x = s.nextInt();
        }
        System.out.println("Enter a column to fire in: ");
        y = s.nextInt();
        while (y > width || y < 1) { // Error checking, entering invalid firing locations doesn't increment number of guesses.
            System.out.println("Enter a valid column; 1 - " + width + ": ");
            y = s.nextInt();
        }
        x = x - 1;
        y = y - 1;
        if(board[x][y].equals("S")){ // Checks to see if coordinates contain part of a ship
            System.out.println("hit");
            board[x][y] = "H";
            guesses++;
        } else if(board[x][y].equals("-")){ // Checks to see if coordinates contain a '-' aka a miss, which is also the default value of all positions on the board until ships are created and placed.
            System.out.println("miss");
            board[x][y] = "M";
            guesses++;
        } else if(board[x][y].equals("H") || board[x][y].equals("M")) { // Checks for repeat user input, repeat input counts as two guesses
            System.out.println("Penalty. You have already attacked this location.");
            guesses+=2;
        }
    }

    // Calls CreateShip method certain number of times and of varying ship lengths depending on size of board

    public void setShips() {
        if (width == 2 || height == 2 ) { // 1 ship of length 2
            createShip(2);
            numberofShips = 1;
        } else if(2 < width && width <= 4 || 2 < height && height <= 4 ) { // 1 ship of length 2 and 1 ship of length 3
            createShip(2);
            createShip(3);
            numberofShips = 2;
        } else if(4 < width && width <= 6 || 4 < height && height <= 6 ) { // 1 ship of length 2 and 2 ships of length 3
            createShip(2);
            createShip(3);
            createShip(3);
            numberofShips = 3;
        } else if(6 < width && width <= 8 || 6 < height && height <= 8 ) { // 1 ship of length 2, 2 ships of length 3 and 1 ship of length 4
            createShip(2);
            createShip(3);
            createShip(3);
            createShip(4);
            numberofShips = 4;
        } else if(8 < width && width <= 10 || 8 < height && height <= 10 ) { // 1 ship of length 2, 2 ships of length 3, 1 ship of length 4 and 1 ship of length 5
            createShip(2);
            createShip(3);
            createShip(3);
            createShip(4);
            createShip(5);
            numberofShips = 5;
        }
    }
}
