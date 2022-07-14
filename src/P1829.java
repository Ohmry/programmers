import java.util.HashMap;
import java.util.Map;

public class P1829 {
    public String execute() {
        int[][] picture = new int[][] {
                {1, 1, 1, 0},
                {1, 2, 2, 0},
                {1, 0, 0, 1},
                {1, 0, 3, 1},
                {1, 2, 0, 3},
                {1, 0, 0, 3}
        };

        int[] answer = solution(6, 4, picture);
        return "[" + answer[0] +", " + answer[1] + "]";
    }

    PictureMap pictureMap;
    public int[] solution(int m, int n, int[][] picture) {
        pictureMap = new PictureMap(m, n);

        for (int rowIndex = 0; rowIndex < picture.length; rowIndex++) {
            int[] row = picture[rowIndex];
            for (int colIndex = 0; colIndex < row.length; colIndex++) {
                int cellValue = row[colIndex];

                if (cellValue < 1) {
                    // 색칠하지 않은 셀인 경우
                    pictureMap.addNotPaintedCell(rowIndex, colIndex);
                } else if (rowIndex == 0 && colIndex == 0) {
                    // 첫번째 칸인 경우
                    pictureMap.addNewPaintedCell(rowIndex, colIndex, cellValue);
                } else if (rowIndex == 0) {
                    // 첫번째 행인 경우
                    Cell leftCell = pictureMap.getCell(rowIndex, colIndex - 1);
                    if (leftCell.value == cellValue) {
                        pictureMap.addPaintedCell(rowIndex, colIndex, leftCell.area, cellValue);
                    } else {
                        pictureMap.addNewPaintedCell(rowIndex, colIndex, cellValue);
                    }
                } else if (colIndex == 0) {
                    // 첫번째 열인 경우
                    Cell topCell = pictureMap.getCell(rowIndex - 1, colIndex);
                    if (topCell.value == cellValue) {
                        pictureMap.addPaintedCell(rowIndex, colIndex, topCell.area, cellValue);
                    } else {
                        pictureMap.addNewPaintedCell(rowIndex, colIndex, cellValue);
                    }
                } else {
                    // 그 외 칸
                    Cell topCell = pictureMap.getCell(rowIndex - 1, colIndex);
                    Cell leftCell = pictureMap.getCell(rowIndex, colIndex - 1);
                    if (topCell.value == cellValue) {
                        pictureMap.addPaintedCell(rowIndex, colIndex, topCell.area, cellValue);
                    } else if (leftCell.value == cellValue) {
                        pictureMap.addPaintedCell(rowIndex, colIndex, leftCell.area, cellValue);
                    } else {
                        pictureMap.addNewPaintedCell(rowIndex, colIndex, cellValue);
                    }
                }
            }
        }

        return new int[] { pictureMap.numberOfArea, pictureMap.maxSizeOfArea };
    }

    class PictureMap {
        int numberOfArea;
        int maxSizeOfArea;
        Cell[][] cells;
        Map<Integer, Integer> areaInfo;

        public PictureMap(int row, int col) {
            this.numberOfArea = 0;
            this.maxSizeOfArea = 0;
            this.cells = new Cell[row][col];
            this.areaInfo = new HashMap<>();
        }

        public void addPaintedCell(int row, int col, int area, int value) {
            int sizeOfArea = this.areaInfo.get(area);
            sizeOfArea++;
            this.areaInfo.put(area, sizeOfArea);
            if (this.maxSizeOfArea < sizeOfArea) {
                this.maxSizeOfArea = sizeOfArea;
            }
            this.cells[row][col] = new Cell(area, value);
        }

        public void addNewPaintedCell(int row, int col, int value) {
            this.numberOfArea++;
            this.cells[row][col] = new Cell(this.numberOfArea, value);
            this.areaInfo.put(this.numberOfArea, 1);
            if (this.maxSizeOfArea == 0) this.maxSizeOfArea = 1;
        }

        public void addNotPaintedCell(int row, int col) {
            this.cells[row][col] = new Cell(0, 0);
        }

        public Cell getCell(int row, int col) {
            return this.cells[row][col];
        }
    }

    class Cell {
        int area;
        int value;

        public Cell(int area, int value) {
            this.area = area;
            this.value = value;
        }
    }
}
