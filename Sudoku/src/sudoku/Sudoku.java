package sudoku;

import java.io.*;
import java.util.*;

/**
 * An object of this class contains the whole game a enables to interact with it.
 *
 * <p>Methods of this class enables to get the states of the game and change
 * them.</p>
 *
 * @author Pavel Trutman
 */
public class Sudoku implements Serializable {
  /**
   * field with values of sudoku
   */
  private Value[][] sudoku;

  /**
   * Constructor which creates an empty game.
   *
   */
  public Sudoku() {
    //initialize the field
    this.sudoku = new Value[9][9];
    //set them empty
    for(int i = 0; i < 9; i++) {
      for(int j = 0; j < 9; j++) {
        this.sudoku[i][j] = new Value();
      }
    }
  }

  /**
   * Returns value on given coordinates.
   *
   * @param crd position of value, we want to know
   * @return value on the given coordinates
   */
  public Value getValue(Coordinates crd) {
    return this.sudoku[crd.getX()][crd.getY()];
  }

  /**
   * Checks, if in the given column is the target value.
   *
   * @param column column we want search in
   * @param value target value
   * @return number of row, where the value is or -1 if not found
   */
  private int checkColumn(int column, Value value) {
    //search column a compare all values in it
    for (int i = 0; i < 9; i++) {
      if (this.sudoku[i][column].compareValue(value)) {
        //if we have found it, return the row
        return i;
      }
    }
    //if not found, return -1
    return -1;
  }

  /**
   * Checks if the target value is in same row, column and square 3x3 as given
   * coordinates
   *
   * @param crd coordinates we want to search by
   * @param value target value
   * @return position of the same value or NULL if not found
   */
  public Coordinates checkValue(Coordinates crd, Value value) {
    int row;
    int column;

    //search in the row
    row = checkRow(crd.getX(), value);
    if (row != -1) {
      //if found return coordinates
      return new Coordinates(crd.getX(), row);
    }

    //search in the column
    column = checkColumn(crd.getY(), value);
    if (column != -1) {
      //if found return coordiantes
      return new Coordinates(column, crd.getY());
    }

    //search in square 3x3 and return found coordinates
    return checkSquare(crd, value);
  }

  /**
   * Checks, if the target value is in the same row.
   *
   * @param row row, we want to search in
   * @param value target value
   * @return number of row with the same value or -1 if not found
   */
  private int checkRow(int row, Value value) {
    //search the whole row and compare each value
    for (int i = 0; i < 9; i++) {
      if (this.sudoku[row][i].compareValue(value)) {
        //if found, return the row
        return i;
      }
    }
    //if not found, return -1
    return -1;
  }

  /**
   * Search target value in square 3x3 by given coordinates
   *
   * @param crd coordinations we searching by
   * @param value target value
   * @return coordinates with found value or NULL if nothing found
   */
  private Coordinates checkSquare(Coordinates crd, Value value) {
    int i, j;
    int x, y;
    Coordinates ret;
    ret = null;

    //get the begining of the quare 3x3
    x = crd.getX() / 3;
    y = crd.getY() / 3;

    //search in the square and compare values
    for(i = 3*x; i < 3*(x + 1); i++) {
      for(j = 3*y; j < 3*(y + 1); j++) {
        if(this.sudoku[i][j].compareValue(value)) {
          //if found, save coordinates
          ret = new Coordinates(i, j);
          break;
        }
      }
    }
    //return coordinates
    return ret;
  }

  /**
   * Set the value in sudoku at given coordinates.
   *

   * <p>Checks, if it is possible to input the value into field on given
   * coordinates and if it correct it will insert it here.</p>
   *
   * @param crd coordinates, where to input the value
   * @param value input value
   * @return TRUE if the value was entered, FALSE if it was against the rules
   */
  public boolean insert(Coordinates crd, Value value) {
    Coordinates ret;

    //insert only if the field is empty
    if(this.sudoku[crd.getX()][crd.getY()].getIsEmpty()) {
      //check if it is not against the rules
      ret = checkValue(crd, value);
      //if it is correct
      if(ret == null) {
        //insert it
        this.sudoku[crd.getX()][crd.getY()].setValue(value);
        //return TRUE
        return true;
      }
    }
    //if not inserted return FALSE
    return false;
  }

  /**
   * Tries to solve the sudoku.
   *
   * @param clc at the end removes all inserted values if set TRUE
   * @return TRUE if solved, FALSE if failed to solve
   */
  public boolean solve(boolean clc) {
    //linked list with empty fileds
    LinkedList<Coordinates> list;
    list = new LinkedList();

    Coordinates crd;
    int n;
    boolean ret = false;
    boolean inserted;

    //find all empty values
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        crd = new Coordinates(i, j);
        if(this.getValue(crd).getIsEmpty()) {
          //add them to the linked list
          list.add(crd);
        }
      }
    }

    //iterate over empty values
    int s = list.size();
    int is = -1;
    if((is + 1) < s) {
      //take the first one
      is++;
      crd = list.get(is);
      while(true) {
        n = 0;
        //get value on the position
        if(!this.getValue(crd).getIsEmpty()) {
          n = this.getValue(crd).getValue();
        }

        //try to insert all values, that was no inserted yet
        inserted = false;
        for(int i = n + 1; i <= 9; i++) {
          this.getValue(crd).clear();
          if(this.insert(crd, new Value(i, false))) {
            inserted = true;
            break;
          }
        }

        //backtrack if all values tried
        if(!inserted) {
          this.getValue(crd).clear();
          //take previous empty square
          if((is - 1) >= 0) {
            is--;
            crd = list.get(is);
            continue;
          }
          else {
            //solution not found
            ret = false;
            break;
          }
        }
        //take next empty position if exists
        else {
          if((is + 1) < s) {
            is++;
            crd = list.get(is);
            continue;
          }
          else {
            //we filled all positions
            ret = true;
            break;
          }
        }
      }
    }

    //clear filled values if needed
    if(clc) {
      for(is = 0; is < s; is++) {
        crd = list.get(is);
        this.getValue(crd).clear();
      }
    }

    return ret;
  }

  /**
   * Adds only one value into sudoku to help player.
   *
   * @return coordinates with inserted value, NULL if can not be solved
   */
  public Coordinates hint() {
    Sudoku solved;
    Coordinates crd;
    //make copy
    solved = this.copy();
    //try to solve it
    if(solved.solve(false)) {
      //get first empty value
      for(int i = 0; i < 9; i++) {
        for(int j = 0; j < 9; j++) {
          if(this.getValue(new Coordinates(i, j)).getIsEmpty()) {
            //if found, insert it into sudoku and return coordinates
            crd = new Coordinates(i, j);
            this.insert(crd, solved.getValue(crd));
            return crd;
          }
        }
      }
    }
    //if can not be solved, return NULL
    return null;
  }


  /**
   * Checks if all fileds are filled
   *
   * @return TRUE if it is all filled, else FALSE
   */
  public boolean checkIsSolved() {
    //go through whole sudoku
    for(int i = 0; i < 9; i++) {
      for(int j = 0; j < 9; j++) {
        //if we find any empy filed
        if(this.sudoku[i][j].getIsEmpty()) {
          //return FALSE
          return false;
        }
      }
    }
    //if no empty field found, return TRUE
    return true;
  }


  /**
   * It generates the sudoku with all fields filled by rules.
   *
   * @return generated sudoku
   */
  public static Sudoku generateFullGrid() {
    Sudoku sudoku;
    sudoku = new Sudoku();
    boolean numbers[][];
    numbers = new boolean[81][9];
    int square = 0;
    boolean outOfNumbers;
    int i;

    while(square < 81 && square >= 0) {
      sudoku.getValue(new Coordinates(square%9, square/9)).clear();

      //are we out of numbers?
      outOfNumbers = true;
      for(i = 0; i < 9; i++) {
        outOfNumbers &= numbers[square][i];
      }
      if(outOfNumbers) {
        //no numbers left
        for(i = 0; i < 9; i++) {
          numbers[square][i] = false;
        }
        //back one square
        sudoku.getValue(new Coordinates(square%9, square/9)).clear();
        square--;
        continue;
      }
      else {
        //get random unused number
        int num = (int) Math.round(Math.random()*8);
        while(numbers[square][num]) {
          num = (num + 1) % 9;
        }
        numbers[square][num] = true;
        //check if conflict
        if(sudoku.insert(new Coordinates(square%9, square/9), new Value(num + 1, false))) {
          square++;
        }
      }
    }

    //return generated sudoku
    return sudoku;
  }

  /**
   * From the full filled sudoku removes some values to generate puzzle.
   *
   * @return generated puzzle
   */
  public static Sudoku generatePuzzle() {
    Sudoku sudoku;
    sudoku = Sudoku.generateFullGrid();

    Coordinates crd;
    int x, y;
    int i = 1;
    int v;
    boolean moreSolutions;

    //loop until wanted numbers are not removed
    while(i <= 46) {
      //get random coordinates
      x = (int) Math.round(Math.random()*8);
      y = (int) Math.round(Math.random()*8);
      crd = new Coordinates(x, y);

      //take another one if empty
      if(sudoku.getValue(crd).getIsEmpty()) {
        continue;
      }

      //remove value and try remaining values
      v = sudoku.getValue(crd).getValue();
      sudoku.getValue(crd).clear();
      moreSolutions = false;
      for(int j = 1; j <= 9; j++) {
        if(j != v) {
          if(sudoku.insert(crd, new Value(j, false))) {
            //check if exists more solutions
            if(sudoku.solve(true)) {
              moreSolutions = true;
              sudoku.getValue(crd).clear();
              break;
            }
            sudoku.getValue(crd).clear();
          }
        }
      }
      if(moreSolutions) {
        //insert value back and try another one
        sudoku.insert(crd, new Value(v, false));
      }
      else {
        i++;
      }
    }

    //set all used squares as persistent
    for(i = 0; i < 9; i++) {
      for(int j = 0; j < 9; j++) {
        if(!sudoku.getValue(new Coordinates(i, j)).getIsEmpty()) {
          sudoku.getValue(new Coordinates(i, j)).setPersistent();
        }
      }
    }

    //return generated puzzle
    return sudoku;
  }

  /**
   * Makes a copy of the sudoku.
   *
   * @return copy of sudoku
   */
  public Sudoku copy() {
    //new sudoku
    Sudoku cp;
    cp = new Sudoku();
    Coordinates crd;

    //go through and copy all values
    for(int i = 0; i < 9; i++) {
      for(int j = 0; j < 9; j++) {
        crd = new Coordinates(i, j);
        cp.getValue(crd).setValue(this.getValue(crd));
      }
    }

    //return copied sudoku
    return cp;
  }

  /**
   * Saves sudoku to the file.
   *
   * @param path file to save
   * @return TRUE if saving was successful, else FALSE
   */
  public boolean writeToFile(String path) {
    //make output stream
    ObjectOutputStream oos = null;
    try {
      //open file
      oos = new ObjectOutputStream(new FileOutputStream(path));
      //wtite to file
      oos.writeObject(this);
    }
    catch (IOException e) {
      //file can not be open
      return false;
    }
    finally {
      try {
        //close if something still open
        if(oos != null) {
          oos.close();
        }
      }
      catch(IOException ex) {
        //do nothing, all sources are closed
      }
    }

    //saving was successful, return TRUE
    return true;
  }

  /**
   * Loads sudoku from file.
   *
   * @param path file to load from
   * @return loaded sudoku on success, NULL if failed
   */
  public static Sudoku readFromFile(String path) {
    //create input stream
    ObjectInputStream ois = null;
    Sudoku s;
    try {
      //open file
      ois = new ObjectInputStream(new FileInputStream(path));
      //read from file
      s = (Sudoku) ois.readObject();
    }
    catch (IOException | ClassNotFoundException e) {
      //if can not open file or contains something else
      return null;
    }
    finally {
      try {
        //close file, if not closed yet
        if(ois != null) {
          ois.close();
        }
      }
      catch(IOException ex) {
        //do nothing, all sources are closed
      }
    }

    //return sudoku
    return s;
  }

}
