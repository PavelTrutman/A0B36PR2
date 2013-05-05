package sudoku;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * It enables to create an text field with properties we want.
 *
 * @author Pavel Trutman
 */
public class TextField extends JTextField implements KeyListener {

  /*
   * Coordinates
   */
  private Coordinates crd;

  /*
   * Valid
   */
  private boolean isValid;

  /*
   * Graphical user interface, where input was made
   */
  private GUI gui;

  /*
   * Default colors
   */
  private Color BGEditable= new Color(250, 250, 250);
  private Color BGNonEditable = new Color(238, 238, 238);
  private Color FGInvalid = new Color(210, 30, 30);
  private Color FGValid = new Color(50, 50, 50);
  private Color FGGreen = new Color(30, 130, 30);

  /**
   * Constructor of this text field.
   *
   * @param crd coordinates on which was made
   * @param gui GUI the field was made
   */
  public TextField(Coordinates crd, GUI gui) {
    //set some properties
    this.crd = crd;
    this.gui = gui;
    this.setColumns(1);
    this.setFont(new java.awt.Font("Verdana", 1, 20));
    this.setBorder(null);
    this.setHorizontalAlignment(JTextField.CENTER);
    this.setBackground(BGEditable);
    this.setForeground(FGValid);
    //add key listener
    this.addKeyListener(this);
  }

  /**
   * Clears the text field.
   *
   */
  public void clear() {
    //set it clear
    this.setEditable(true);
    this.setBackground(BGEditable);
    this.setText("");
    this.setForeground(FGValid);
  }

  /**
   * Sets the text field by given value.
   *
   * @param val value to set the text field
   */
  public void setFromValue(Value val) {
    //set editable as value is not persistent
    this.setEditable(!val.getIsPersistent());
    //set as value
    if(val.getIsEmpty()) {
      //if empty set as empty too
      this.setText("");
    }
    else {
      //pasre value to string
      this.setText(Integer.toString(val.getValue()));
    }
  }

  /**
   * Set the text field editable or non-editable.
   *
   * @param bln TRUE to set editable, FALSE to set non-editable
   */
  @Override
  public void setEditable(boolean bln) {
    super.setEditable(bln);
    //change background
    if(bln) {
      this.setBackground(BGEditable);
    }
    else {
      this.setBackground(BGNonEditable);
    }
  }

  /**
   * Returns coordinates of the text field.
   *
   * @return coordinates of the text field.
   */
  public Coordinates getCoordinates() {
    return this.crd;
  }

  /**
   * Sets the text filed valid or invalid.
   *
   * @param bln TRUE to set valid, FALSE to set invalid
   */
  public void setValid(boolean bln) {
    this.isValid = bln;
    //change text color
    if(bln) {
      this.setForeground(FGValid);
    }
    else {
      this.setForeground(FGInvalid);
    }
  }

  /**
   * Returns if the text filed is valid or not.
   *
   * @return TRUE if it is valid, FALSE if not
   */
  public boolean getIsValid() {
    return this.isValid;
  }

  /**
   * Sets the text color green.
   */
  public void setGreen() {
    this.setForeground(FGGreen);
  }

  /**
   * Change the value of text field on key typed.
   *
   * @param key key event
   */
  @Override
  public void keyTyped(KeyEvent key) {
    //get the focused component
    TextField In = (TextField) gui.getMostRecentFocusOwner();

    //get char from pressed key
    char c = key.getKeyChar();

    //clear input if Ctrl+v pressed
    if(key.isControlDown()) {
      c = '0';
    }

    //only if is editable
    if(In.isEditable()) {
      //only numbers 1-9
      if(Integer.valueOf(c) >= 49 && Integer.valueOf(c) <= 57) {
        //set input text
        In.setText(String.valueOf(c));
        //insert into object
        gui.getSudoku().getValue(In.getCoordinates()).clear();
        In.setValid(gui.getSudoku().insert(In.getCoordinates(), new Value(Integer.parseInt(String.valueOf(c)), false)));
        //if sudoku solved, show message
        if(gui.getSudoku().checkIsSolved()) {
          JOptionPane.showMessageDialog(null, "Congratulations!\nYou have successfully solved the sudoku.", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
        }
      }
      //if pressed number 0
      else if(Integer.valueOf(c) == 48) {
        //clear input text
        In.setText("");
        //clear from object
        gui.getSudoku().getValue(In.getCoordinates()).clear();
        In.setValid(true);
      }
    }

    //revalidate all invalid values
    gui.revalidateInputs();

    //consume the key
    key.consume();
  }

  /**
   * On key pressed action.
   *
   * @param ke key event
   */
  @Override
  public void keyPressed(KeyEvent ke) {
    //do nothing
  }

  /**
   * On key released action.
   *
   * @param ke key event
   */
  @Override
  public void keyReleased(KeyEvent ke) {
    //do nothing
  }

}
