package sudoku;

import java.util.*;

/**
 * Objekt této třídy obsahuje rozehranou hru a umožňuje interakci s ní.
 *
 * <p>Metody této třídy umožňují zjistit stav rozehrané hry, hru hrát a
 * vykreslit ji na obrazovku.</p>
 *
 * @author Pavel Trutman
 */
public class Sudoku {
  /**
   * pole s hodnotami v sudoku
   */
  private Value[][] sudoku;

  /**
   * Konstuktor, který vytvoří hru se všemi polemi prázdnými.
   *
   */
  public Sudoku() {
    //inicializace pole
    this.sudoku = new Value[9][9];
    //naplňění prázdnými hodnotami
    for(int i = 0; i < 9; i++) {
      for(int j = 0; j < 9; j++) {
        this.sudoku[i][j] = new Value();
      }
    }
  }

  /**
   * Vrátí hodnotu na určených souřadnicích.
   *
   * @param crd souřadnice na kterých chceme zjistit hodnotu
   * @return hodnota na daných souřadnicích
   */
  public Value getValue(Coordinates crd) {
    return this.sudoku[crd.getX()][crd.getY()];
  }

  /**
   * Zkontroluje, zda je v zadaném sloupci hledaná hodnota.
   *
   * @param column sloupec ve kterém se hledá
   * @param value hledaná hodnota
   * @return číslo řádku s nalezenou hodnotou nebo -1 pokud hodnota nebyla
   * nalezena
   */
  private int checkColumn(int column, Value value) {
    //prohledává sloupec a porovnávají se hodnoty v něm
    for (int i = 0; i < 9; i++) {
      if (this.sudoku[i][column].compareValue(value)) {
        //pokud jsme našli, vrátíme číslo řádku
        return i;
      }
    }
    //pokud jsme nenašli vracíme -1
    return -1;
  }

  /**
   * Zkontroluje, zda se hledaná hodnota nachází ve stejném řádku, stejném
   * sloupci nebo stejném čtverci 3x3 jako jsou zadané souřadnice.
   *
   * @param crd souřadnice podle kterých se hledá
   * @param value hledaná hodnota
   * @return objekt Ret se souřadnicemi, na kterých byla nalezena hledaná hodnota nebo
   * tento objekt v chybovém stavu, pokud hodnota nebyla nenalezena
   */
  public Coordinates checkValue(Coordinates crd, Value value) {
    int row;
    int column;

    //hledáme hodnotu v řádku
    row = checkRow(crd.getX(), value);
    if (row != -1) {
      //byla-li nalezena vrátím souřadnice
      return new Coordinates(crd.getX(), row);
    }

    //hledáme hodnotu ve sloupci
    column = checkColumn(crd.getY(), value);
    if (column != -1) {
      //byla-li nalezena vrátím souřadnice
      return new Coordinates(column, crd.getY());
    }

    //hledáme hodnotu ve čverci 3x3 a výsledek vrátíme
    return checkSquare(crd, value);
  }

  /**
   * Zkontroluje, zda je v hledaném řádku hledaná hodnota.
   *
   * @param row řádek ve kterém hledáme
   * @param value hledaná hodnota
   * @return číslo sloupce s nalezenou hodnotou nebo -1 pokud hodnota nebyla
   * nalezena
   */
  private int checkRow(int row, Value value) {
    //prohledává se řádek s porovnávají se hodnoty v něm
    for (int i = 0; i < 9; i++) {
      if (this.sudoku[row][i].compareValue(value)) {
        //pokud jsme našli, vrátíme číslo sloupce
        return i;
      }
    }
    //pokud jsme nenašli vracíme -1
    return -1;
  }

  /**
   * Prohledá čtverec 3x3 podle zadaných souřadnic.
   *
   * @param crd souřadnice podle kterých se hledá
   * @param value hledaná hodnota
   * @return objekt Ret se souřadnicemi, na kterých se nachází hledaná hodnota
   * nebo objekt v chybovém stavu, pokud hledaná hodnota nebyla nelezena
   */
  private Coordinates checkSquare(Coordinates crd, Value value) {
    int i, j;
    int x, y;
    Coordinates ret;
    ret = null;

    //najdeme počáteční souřadnice čtverce 3x3
    x = crd.getX() / 3;
    y = crd.getY() / 3;

    //prohledáme čtverec a poravnáme hodnoty
    for(i = 3*x; i < 3*(x + 1); i++) {
      for(j = 3*y; j < 3*(y + 1); j++) {
        if(this.sudoku[i][j].compareValue(value)) {
          //pokud jsme našli, souřadnice uložíme
          ret = new Coordinates(i, j);
          break;
        }
      }
    }
    //vrátíme výsledek
    return ret;
  }

  /**
   * Zkusí najít další možný tah v sudoku.
   *
   * <p>Tato metoda projde všechny volné pole v sudoku a zjistí jaké možné
   * hodnoty je tam možné umístit, tak aby neodporovaly pravidlům. Pokud je
   * možná pouze jedna hodnota pro vložení, je to další jistý tah, který může
   * být proveden a je nabídnut hráči.</p>
   *
   * @return objekt Ret se souřadnicemi a hodnotou možného dalšího tahu, pokud
   * algoritmus nenašel další možný tah, tak vrátí tento objekt v chybovém stavu
   *//*
  public Ret hint() {
    int x;
    int y;
    int value;
    int count;
    Ret result;

    //pole boolean odpovídající hotnotám 1-9, je-li TRUE lze tam hodnota vložit,
    //je-li FALSE nelze ji tam vložit
    boolean[] posibilities;
    posibilities = new boolean[9];

    //procházíme celé sudoku
    for(x = 0; x < 9; x++) {
      for(y = 0; y < 9; y++) {
        //pouze políčka, která nejsou obsazená
        if(this.sudoku[x][y].getIsEmpty()) {
          count = 0;      //počítadlo, kolik možných hodnot je možné vložit na toto políčko
          //zkusíme vložit každou hodnotu
          for(value = 0; value < 9; value++) {
            result = checkValue(new Coordinates(x, y), new Value(value + 1, false));
            //lze-li ji tam vložit
            if(!result.getIsOK()) {
              //uložíme si, že právě tato hodnota tam lze vložit
              posibilities[value] = true;
              //zvýšíme počítadlo hodnot
              count++;
            }
            //nelze hodnotu vložit
            else {
              //uložíme, že ji vložit nelze
              posibilities[value] = false;
            }
          }
          //pokud lze na toto políčko vložit právě jedna hodnota
          if (count == 1) {
            //projdeme pole hodnot a zjistíme, která to byla
            for(value = 0; value < 9; value++) {
              if(posibilities[value]) {
                //vrátíme ji
                return new Ret(x, y, (value + 1));
              }
            }
          }
        }
      }
    }
    //pokud jsme nic našli, vrátíme Ret v chybovém stavu
    return new Ret(false);
  }*/

  /**
   * Na konkrétní souřadnice v sudoku vloží danou hodnotu.
   *
   * <p>Ověří, zda je možné na políčko s danými souřadnicemi hodnotu vložit, a
   * neodporuje-li pravidlům sudoku, vloží ji tam.</p>
   *
   * @param crd souřadnice, na které se má hodnota vložit
   * @param value vkládaná hodnota
   * @return TRUE pokud se hodnota podařila vložit, FALSE pokud by vložená
   * hodnota odporovala pravidlům
   */
  public boolean insert(Coordinates crd, Value value) {
    Coordinates ret;

    //vložíme, pouze pokud je políčko na oněch souřadnicích volné
    if(this.sudoku[crd.getX()][crd.getY()].getIsEmpty()) {
      //ověříme, že tam nebude odporovat pravidlům
      ret = checkValue(crd, value);
      //pokud neodporuje
      if(ret == null) {
        //vložíme ji tam
        this.sudoku[crd.getX()][crd.getY()].setValue(value);
        //vrátíme TRUE
        return true;
      }
    }
    //pokud se nepovedlo vložit, vrátíme FALSE
    return false;
  }

  public boolean solve(boolean clc) {
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

        //try to insert all values
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
   * Zkontroluje, zda jsou již všechna políčka v sudoku vyplněná.
   *
   * @return TRUE pokud je již sudoku vyřešené, jinak FALSE
   */
  public boolean checkIsSolved() {
    //projdeme celé sudoku
    for(int i = 0; i < 9; i++) {
      for(int j = 0; j < 9; j++) {
        //pokud najdeme nějaké nevyplněné políčko
        if(this.sudoku[i][j].getIsEmpty()) {
          //vrátíme FALSE
          return false;
        }
      }
    }
    //pokud jsme žádné prázdné políčko nenašli, vracíme TRUE
    return true;
  }


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
        //System.out.println(square + " " + i);
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

    return sudoku;
  }

  public static Sudoku generatePuzzle() {
    Sudoku sudoku;
    sudoku = Sudoku.generateFullGrid();

    Coordinates crd;
    int x, y;
    int i = 1;
    int v;
    boolean moreSolutions;

    //loop until wanted numbers removed
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
            //chech if exists more solutions
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
    return sudoku;
  }

}
