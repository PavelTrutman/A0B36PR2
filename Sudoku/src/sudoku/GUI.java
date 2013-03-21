package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Graphical user interface.
 *
 * <p>Creates a graphical user interface to interact with user.</p>
 *
 * @author Pavel Trutman
 */
public class GUI extends JFrame implements KeyListener {

  /**
   * Inits GUI.
   */
  public GUI (){
    super("Sudoku");

    /**
     * Components
     */
    Container container;
    GridBagLayout bigGrid;
    GridBagLayout smallGrid;
    GridBagConstraints bigGridCon;
    GridBagConstraints smallGridCon;
    JTextField[][] inputs;
    JPanel[][] bigSquares;
    JPanel sud;

    /*
     * Menu items
     */
    JMenuBar menuBar;
    JMenu gameMenu;
    JMenuItem exitGame;
    JMenuItem newGame;
    JMenuItem restartGame;
    JMenuItem nextMoveGame;
    JMenuItem solveGame;
    JMenuItem saveGame;
    JMenuItem loadGame;
    JMenu aboutMenu;
    JMenuItem gameAbout;
    JMenuItem authorAbout;

    Dimension Dim;
    Border bord;

    int i, j;

    //set border
    bord = new LineBorder(Color.BLACK, 1, false);

    //INIT WINDOW
    Dim = new Dimension(500, 500);
    this.setSize(Dim);
    this.setMaximumSize(Dim);
    this.setMinimumSize(Dim);
    this.setBounds(100, 100, 500, 500);
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //MENUS
    menuBar = new JMenuBar();

    //game menu
    gameMenu = new JMenu();
    gameMenu.setText("Game");
    menuBar.add(gameMenu);

    newGame = new JMenuItem();
    newGame.setText("New");
    gameMenu.add(newGame);

    restartGame = new JMenuItem();
    restartGame.setText("Restart");
    gameMenu.add(restartGame);

    nextMoveGame = new JMenuItem();
    nextMoveGame.setText("Next move");
    gameMenu.add(nextMoveGame);

    solveGame = new JMenuItem();
    solveGame.setText("Solve");
    gameMenu.add(solveGame);

    gameMenu.addSeparator();

    saveGame = new JMenuItem();
    saveGame.setText("Save");
    gameMenu.add(saveGame);

    loadGame = new JMenuItem();
    loadGame.setText("Load");
    gameMenu.add(loadGame);

    gameMenu.addSeparator();

    exitGame = new JMenuItem();
    exitGame.setText("Exit");
    gameMenu.add(exitGame);

    //about menu
    aboutMenu = new JMenu();
    aboutMenu.setText("About");
    menuBar.add(aboutMenu);

    gameAbout = new JMenuItem();
    gameAbout.setText("About game");
    aboutMenu.add(gameAbout);

    authorAbout = new JMenuItem();
    authorAbout.setText("About auhtor");
    aboutMenu.add(authorAbout);

    setJMenuBar(menuBar);


    //GRID 3x3 INIT
    //size of grids
    int[] bigGridSize = {120, 120, 120};
    bigGrid = new GridBagLayout();
    bigGrid.columnWidths = bigGridSize;
    bigGrid.rowHeights = bigGridSize;
    bigGridCon = new GridBagConstraints();
    bigGridCon.fill = GridBagConstraints.BOTH;

    //GRID 9x9 INIT
    //size of grids
    int[] smallGridSize = {37, 37, 37};
    smallGrid = new GridBagLayout();
    smallGrid.columnWidths = smallGridSize;
    smallGrid.rowHeights = smallGridSize;
    smallGridCon = new GridBagConstraints();
    smallGridCon.fill = GridBagConstraints.BOTH;
    smallGridCon.insets = new Insets(2, 2, 2, 2);

    //WRAP INIT
    sud = new JPanel();
    sud.setLayout(bigGrid);
    sud.setBorder(bord);

    //CONTAINER INIT
    container = getContentPane();
    container.setLayout(new GridBagLayout());
    container.add(sud);

    //GRID 3x3 GENERATE
    bigSquares = new JPanel[3][3];
    for(i = 0; i < 3; i++) {
      for(j = 0; j <3 ; j++) {
        bigGridCon.gridx = i;
        bigGridCon.gridy = j;
        bigSquares[i][j] = new JPanel();
        bigSquares[i][j].setBorder(bord);
        bigSquares[i][j].setLayout(smallGrid);
        sud.add(bigSquares[i][j], bigGridCon);
      }
    }

    //GRID 9x9 GENERATE
    inputs = new JTextField[9][9];
    for(i = 0; i < 9; i++) {
      for(j = 0; j < 9; j++) {
        smallGridCon.gridx = i%3;
        smallGridCon.gridy = j%3;
        inputs[i][j] = new JTextField();
        inputs[i][j].setColumns(1);
        inputs[i][j].setFont(new java.awt.Font("Verdana", 1, 20));
        inputs[i][j].setBorder(null);
        inputs[i][j].setHorizontalAlignment(JTextField.CENTER);
        inputs[i][j].setBackground(new Color(250, 250, 250));
        inputs[i][j].addKeyListener(this);
        bigSquares[i/3][j/3].add(inputs[i][j], smallGridCon);

      }
    }

    /*
    //disable
    inputs[5][6].setEditable(false);
    inputs[5][6].setText("6");
    inputs[5][6].setBackground(new Color(238, 238, 238));
    */

    //SET IT VISIBLE AND PACK IT
    this.setVisible(true);
    pack();

  }

  /**
   * Iplementing KeyListener methods.
   */

  @Override
  public void keyTyped(KeyEvent key) {
    //get the focused component
    JTextField In = (JTextField) this.getMostRecentFocusOwner();
    //clear input if Ctrl+v pressed
    if(key.isControlDown()) {
      In.setText("");
    }
    //get char from pressed key
    char c = key.getKeyChar();
    //only numbers 1-9
    if(Integer.valueOf(c) >= 49 && Integer.valueOf(c) <= 57) {
      //set input text
      In.setText(String.valueOf(c));
    }
    //if pressed number 0
    else if(Integer.valueOf(c) == 48) {
      //clear input text
      In.setText("");
    }
    //consume the key
    key.consume();
  }

  @Override
  public void keyPressed(KeyEvent key) {
    //do nothing
  }

  @Override
  public void keyReleased(KeyEvent key) {
    //do nothing
  }
}
