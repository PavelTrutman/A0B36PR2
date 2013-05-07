Sudoku
======

Zadání
------
Naprogramujte aplikaci sloužící jako grafické uživatelské prostředí při řešení hlavolamu sudoku. Je vyžadován vlastní generátor nových sudoku, ověřování tahů zadaných uživatelem vzhledem k pravidlům sudoku, nápověda v podobě napovězení dalšího kroku případně zobrazení celého řešení, možnost hru ukládat a načítat ze souboru.

Implementace
------------
Kód programu je rozdělen do několika logických celků, podle zásad objektového programování  a logického rozdělení. Jednotlivé logické celky jsou reprezentovány jednotlivými třídami a jejich metodami. Dále tedy uvádím jejich výčet a podrobnější popis.

__Coordinates__

Umožňuje ukládání souřadnic, tj. dvou hodnot v intervalu 0–8.

__GUI__

Obstarává veškerou komunikaci mezi uživatelem a třídou Sudoku, která obstarává všechny výpočetní operace. Při spuštění inicializuje okno s jednotlivými políčky sudoku a roletovými nabídkami. Jednotlivá políčka jsou objekty vlastní třídy TextField a do okna jsou umísťovány pomocí GridBagLayout manažeru. Akci od uživatele vždy předá dál objeku Sudoku a podle navráceného výsledku reaguje příslušnou změnou v rozhraní. Nejdůležitější je metoda setSudoku, která zajišťuje provázání s objektem třídy Sudoku.

__Main__

Hlavní třída aplikace. Pouze vytvoří objekt GUI s nově vygenerovaným hlavolamem a předá řízení objektu GUI.

__MenuItem__

Objekt zajišťující interakci s uživatelem přes jednotlivé prvky menu. V závislosti na vyvolané nabídce zavolá přes GUI potřebnou metodu.

__Sudoku__

Tato třída obstarává veškerou výpočetní logiku aplikace. Generuje nové zadání, ověřuje tahy zadané uživatelem a umí rozehranou hru restartovat, vyřešit nebo jen napovědět další tah dopředu. Umožňuje taky hru uložit do souboru, případně ji z něj zpětně nahrát.

__TextField__

Jednotlivá políčka pro vkládání hodnot obstarává tato třída. Každé má vždy uložené souřadnice na kterých se nalézá, které slouží k jednoznačné indentifikaci políčka. Políčko může být buď editovatelné nebo trvalé (hodnota nelze změnit), to se vyznačuje tím, že má zašedlé pozadí. Čísla do políček nejsou vkládány přímo, ale stisky kláves jsou přímo odchytávány a v závislosti na stisknuté klávese a vybraném políčku jsou buď vloženy nebo ne. Po vložení hodnoty je zkontrolována v objektu Sudoku její přípustnost vzhledem k pravidlům. Pokud by odporovala pravidlům, je barva textu změněná na červenou a hodnota není do Sudoku vložena. Při každém vložení další hodnoty jsou všechny tyto políčka překontrolována, zda stále odporují pravidlům. Pokud ne, je příslušná hodnota vložena do Sudoku.

__Value__

Uchovává hodnotu na jednotlivých políček v objektu Sudoku. Kontroluje vkládaný rozsah hodnot (1–9), případně může být nastavena jako prázdná. Rozlišuje také mezi stavy editovatelná a trvalá, to záleží na tom, zda je tato hodnota součástí zadání nebo ji může uživatel změnit.

Ovládání programu
-----------------
Ovládání celé aplikace je si myslím velice intuitivní. Při spuštění programu se uživateli zobrazí nově vygenerované sudoku připravené k řešení. Hodnoty do políček je možné zadávat přímo z klávesnice, s tím že neplatné znaky jsou odfiltrovány. Každá nově vložená hodnota je zkontrolována na přípustnost vzhledem k pravidlům. Pokud hodnota neodpovídá pravidlům, je zobrazována červeně. Při vyřešení celého sudoku je uživateli zobrazena hláška s gratulací.

V horní části okna je standardní menu s roletovými nabídkami. Uživatel může hrát novou hru nebo hru restartovat do původního zadání. Může si taky nechat poradit jeden další tah, což se projeví vyplněním nějakého prázdného políčka novou hodnotou se zelenou barvou. Uživatel si také může nechat vyřešit celé sudoku. Může si také hru jednoduše uložit do souboru, případně ji z něj nahrát zpátky do aplikace. V menu jsou také další dvě nabídky zobrazující informace o aplikaci a autorovi.
