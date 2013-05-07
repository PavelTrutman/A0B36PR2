package sudoku;

import java.awt.event.*;
import javax.swing.*;

/**
 * This class enables to make a menu item with special properties.
 *
 * @author Pavel Trutman
 */
public class MenuItem extends JMenuItem implements ActionListener {

  /*
   * Graphical user interface, where the MenuItem was made
   */
  private GUI gui;

  /**
   * Constructor of the menu item.
   *
   * @param gui GUI where the item was made
   * @param string string to be diplayed as name
   */
  public MenuItem(GUI gui, String string) {
    //call constructor of parent
    super(string);
    //set GUI
    this.gui = gui;
    //add action listener
    this.addActionListener(this);
  }

  /**
   * Does the action of the item.
   *
   * @param ae action event
   */
  @Override
  public void actionPerformed(ActionEvent ae) {
    //get the source of the event
    MenuItem menu = (MenuItem) ae.getSource();
    //do the action of each owns item
    if(menu == gui.newGame) {
      //generate new game
      gui.setSudoku(Sudoku.generatePuzzle());
    }
    else if(menu == gui.restartGame) {
      //restart the game
      gui.restart();
    }
    else if(menu == gui.nextMoveGame) {
      //make hint
      gui.hint();
    }
    else if(menu == gui.solveGame) {
      //solve it all
      gui.solve();
    }
    else if(menu == gui.saveGame) {
      //save into file
      gui.save();
    }
    else if(menu == gui.loadGame) {
      //load from file
      gui.load();
    }
    else if(menu == gui.exitGame) {
      //exit the game
      System.exit(0);
    }
    else if(menu == gui.gameAbout) {
      //show info about game
      JOptionPane.showMessageDialog(null, "Sudoku, version 1.0\nBuilt 2013/05/07.\n\nThis game was made as semestral work at CTU.\n\nAll sources are avaible at site: https://github.com/trutmpav/A0B36PR2.\nAll contributors are welcome.", "About game", JOptionPane.PLAIN_MESSAGE);
    }
    else if(menu == gui.authorAbout) {
      //show info about author
      JOptionPane.showMessageDialog(null, "Made by Pavel Trutman.\nStudent at CTU, FEE.\nE-mail: trutmpav@fel.cvut.cz.", "About author", JOptionPane.PLAIN_MESSAGE);
    }
  }


}
