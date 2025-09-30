
import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

final public class TicTacToeGame{

    private static char[][] board = {{'0','1','2'},{'3','4','5'},{'6','7','8'}};
    final private static int[][] winCases = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    private static ArrayList<ArrayList<Integer>> availableCases = new ArrayList<>()
    {{
    add(new ArrayList<>(Arrays.asList(0, 1, 2)));
    add(new ArrayList<>(Arrays.asList(3, 4, 5)));
    add(new ArrayList<>(Arrays.asList(6, 7, 8)));
    add(new ArrayList<>(Arrays.asList(0, 3, 6)));
    add(new ArrayList<>(Arrays.asList(1, 4, 7)));
    add(new ArrayList<>(Arrays.asList(2, 5, 8)));
    add(new ArrayList<>(Arrays.asList(0, 4, 8)));
    add(new ArrayList<>(Arrays.asList(2, 4, 6)));
    }};
    private static ArrayList<Integer> allPositions = new ArrayList<>(){{add(0);add(1);add(2);add(3);add(4);add(5);add(6);add(7);add(8);}};
    private static ArrayList<Integer> computeMoves = new ArrayList<>();
    private static ArrayList<Integer> playerMoves = new ArrayList<>();
    private static ArrayList<Integer> totalMoves = new ArrayList<>();
    private static boolean gameOver = false;
    private static Scanner player =  new Scanner(System.in);


    private static boolean in(ArrayList<Integer> arr, int item){
        for (int i : arr) {
            if (i == item){
                return true;
            }
        }
        return false;
    }  

    private static boolean in(ArrayList<Integer> arrList1, ArrayList<Integer> arrList2){
        int counter = 0;
        for (int i : arrList2) {
            if (in(arrList1,i)){
                counter++;
            }
        }
        if(counter == arrList2.size()){
            return true;
        }
        else{
            return false;
        }
    }   

    private static  void showBoard(){
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    private static void updateBoard(int position,char symbol){
        int row = position/3;
        int col = position%3;
        board[row][col] = symbol;
    }

    private static  boolean checkWiner(char symbol){
        int counter = 0;
        if (in(totalMoves,allPositions)){
            System.out.println("it's draw!");
            player.close();
            return true;
        }
        if (symbol=='X'){
            for (int[] cs : winCases) {
                counter = 0;
                for (int pos : cs) {
                    if (in(playerMoves, pos)){
                        counter++;
                    }
                }
                if(counter==3){
                    System.out.println("you win!");
                    player.close();
                    return true;
                }
            }
        }
        else{
            for (int[] cs : winCases) {
                counter = 0;
                for (int pos : cs) {
                    if (in(computeMoves, pos)){
                        counter++;
                    }
                }
                if(counter==3){
                    System.out.println("you lose!");
                    player.close();
                    return true;
                }
            }
        }
        return false;
    }

    private static  void playerMove(){
        System.out.print("your choice: ");
        int choice = player.nextInt();
        playerMoves.add(choice);
        totalMoves.add(choice);
        if(in(computeMoves,choice)){player.close();return;}
        for (ArrayList<Integer> cs : availableCases) {
            if(in(cs,choice)){
                cs.remove(Integer.valueOf(choice));
            }            
        }
        updateBoard(choice, 'X');
    }

    private static  void computerMove(){
        int maxLigth = 3;
        Integer computeChoice = null;
        for (ArrayList<Integer> cs : availableCases) {
            if (in(cs,4) && !in(computeMoves,4)){ computeChoice = 4; break;}
            else if(0 < cs.size() && cs.size() <= maxLigth){
                for (int i : cs){
                    if ( !in(computeMoves, i) ){
                        maxLigth=cs.size();
                        computeChoice=i;
                    }
                }
            }
        }
        for (int[] cs : winCases) {
            int counter=0;
            for (int i : cs) {
                if (in(computeMoves,i)){
                    counter++;
                }
            }
            if (counter==2){
                for (int it : cs) {
                    if(!in(computeMoves,it) && !in(playerMoves,it)){
                        computeChoice=it;
                    }
                }
            }
        }
        if(computeChoice == null){
            return;
        }
        computeMoves.add(computeChoice);
        totalMoves.add(computeChoice);
        updateBoard(computeChoice, 'O');
        System.out.println("computer choice: "+computeChoice);
    }
    
    public static void run(){
        while (!gameOver) {
        showBoard();
        playerMove();
        gameOver = checkWiner('X');
        if(gameOver){
            break;
        }
        computerMove();
        gameOver = checkWiner('O');
        }
    }

    public static void main(String[] args){
        TicTacToeGame.run();
    }
    
}
