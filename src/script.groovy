def game = new GameOfLife(40, false)

while (true) {
    println("--------------       SETUP        -----------------")
    Thread.sleep(2000);
    game.printArray()
    game.rebuildArray()
    println("--------------        END        -----------------")
}

class GameOfLife {
    int size;
    def deadSign = " "
    def aliveSign = "*"
    String[][] array;

    public GameOfLife(int size, boolean isRandom) {
        this.size = size
        initArray(isRandom)
    }

    private initArray(boolean isRandom) {
        if (isRandom || this.size < 30) {
            this.array = fillRandomValue()
        } else {
            this.array = nullifyArray()
            initSimpleArray()
        }
    }

    private String[][] initSimpleArray() {
        array[26][24] = "*"
        array[25][25] = "*"
        array[25][27] = "*"
        array[26][25] = "*"
        array[26][26] = "*"
        array[26][27] = "*"
        array[27][28] = "*"
    }

    private void fillRandomValue() {
        def tempArray = new String[this.size][this.size]
        for (int x = 0; x < tempArray.length; x++) {
            for (int y = 0; y < tempArray.length; y++) {
                tempArray[x][y] = getRandomNumber(0, 10)
            }
        }
        this.array = tempArray
    }

    private String getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min) < 5 ? aliveSign : deadSign;
    }

    private String[][] nullifyArray() {
        def emptyArray = new String[this.size][this.size]
        for (int x = 0; x < emptyArray.length; x++) {
            for (int y = 0; y < emptyArray.length; y++) {
                emptyArray[x][y] = deadSign
            }
        }

        return emptyArray
    }

    void printArray() {
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array.length; y++) {
                printf(" " + array[x][y] + " ")
            }
            println()
        }
    }

    void rebuildArray() {
        String[][] tempArray = new String[array.length][array.length]
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array.length; y++) {
                tempArray[x][y] = String.valueOf(prepareNewValue(array, x, y))
            }
        }
        this.array = tempArray
    }

    private String prepareNewValue(String[][] array, int x, int y) {
        def currentValue = array[x][y]
        int neighbours = 0
        neighbours = getValue(x - 1, y - 1) + neighbours
        neighbours = getValue(x + 0, y - 1) + neighbours
        neighbours = getValue(x + 1, y - 1) + neighbours

        neighbours = getValue(x - 1, y + 0) + neighbours
        neighbours = getValue(x + 1, y + 0) + neighbours

        neighbours = getValue(x - 1, y + 1) + neighbours
        neighbours = getValue(x + 0, y + 1) + neighbours
        neighbours = getValue(x + 1, y + 1) + neighbours

        return conwayMapper(currentValue, neighbours);
    }

    private int getValue(int x, int y) {
        if (x <= 0) {
            return 0
        }
        if (y <= 0) {
            return 0
        }
        if (x >= array.length) {
            return 0
        }
        if (y >= array.length) {
            return 0
        }
        if (array[x][y] == aliveSign) {
            return 1
        }
        return 0
    }

    private String conwayMapper(String currentValue, int neighboursValue) {
        if ((currentValue == aliveSign) && (neighboursValue == 2 || (neighboursValue == 3))) {
            return aliveSign
        } else if ((currentValue == deadSign) && (neighboursValue == 3)) {
            return aliveSign
        }
        return deadSign
    }
}
