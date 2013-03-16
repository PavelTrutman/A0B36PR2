package sudoku;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Pavel Trutman
 */
public class GUI extends JFrame {

  public GUI (){
    super("Sudoku");

    //components
    Container container;
    GridBagLayout bigGrid;
    GridBagLayout smallGrid;
    GridBagConstraints bigGridCon;
    GridBagConstraints smallGridCon;
    JTextField[][] inputs;
    JPanel[][] bigSquares;
    JPanel sud;

    //menus
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

    bord = new LineBorder(Color.BLACK, 1, false);

    //INIT WINDOW
    this.setSize(Dim = new Dimension(500, 500));
    this.setMaximumSize(Dim);
    this.setMinimumSize(Dim);
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

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


    //grid 3x3 init
    int[] bigGridSize = {120, 120, 120};
    bigGrid = new GridBagLayout();
    bigGrid.columnWidths = bigGridSize;
    bigGrid.rowHeights = bigGridSize;
    bigGridCon = new GridBagConstraints();
    bigGridCon.fill = GridBagConstraints.BOTH;

    //grid 9x9 init
    int[] smallGridSize = {37, 37, 37};
    smallGrid = new GridBagLayout();
    smallGrid.columnWidths = smallGridSize;
    smallGrid.rowHeights = smallGridSize;
    smallGridCon = new GridBagConstraints();
    smallGridCon.fill = GridBagConstraints.BOTH;
    smallGridCon.insets = new Insets(2, 2, 2, 2);

    //wrap init
    sud = new JPanel();
    sud.setLayout(bigGrid);
    sud.setBorder(bord);

    //container init
    container = getContentPane();
    container.setLayout(new GridBagLayout());
    container.add(sud);

    //grid 3x3 generate
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

    //grid 9x9 generate
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
        bigSquares[i/3][j/3].add(inputs[i][j], smallGridCon);

      }
    }

    /*
    //disable
    inputs[5][6].setEditable(false);
    inputs[5][6].setText("6");
    inputs[5][6].setBackground(new Color(238, 238, 238));
    */

    pack();

  }
}
