package sudoku;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Pavel Trutman
 */
public class TextField extends JTextField {

  /*
   * Coordinates
   */
  private Coordinates crd;

  /*
   * Disabled
   */
  private boolean isDisabled;

  /*
   * Background colors
   */
  private Color BGEditable= new Color(250, 250, 250);
  private Color BGNonEditable = new Color(238, 238, 238);

  public TextField(Coordinates crd) {
    this.crd = crd;
    this.isDisabled = false;
    this.setColumns(1);
    this.setFont(new java.awt.Font("Verdana", 1, 20));
    this.setBorder(null);
    this.setHorizontalAlignment(JTextField.CENTER);
    this.setBackground(BGEditable);
  }

  public void clear() {
    this.setEditable(true);
    this.setBackground(BGEditable);
    this.setText("");
  }

  @Override
  public void setEditable(boolean bln) {
    super.setEditable(bln);
    if(bln) {
      this.setBackground(BGEditable);
    }
    else {
      this.setBackground(BGNonEditable);
    }
  }



}
