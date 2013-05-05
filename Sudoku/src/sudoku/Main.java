package sudoku;

/**
 * The initialization class.
 *
 * <p>Initialize the whole application.</p>
 *
 * @author Pavel Trutman
 */
public class Main {

  /**
   * Main function of this application.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    //GUI
    GUI window;

    //sudoku
    Sudoku sudoku;

    //generate new game
    sudoku = Sudoku.generatePuzzle();
    //init GUI with this game
    window = new GUI(sudoku);


  }
}
