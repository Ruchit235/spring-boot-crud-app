package com.tickets;

import java.util.*;
import java.util.stream.IntStream;
import java.util.logging.Level;
import java.util.logging.Logger;

class HousieTicketException extends Exception {
    public HousieTicketException(String message) {
        super(message);
    }
}

class HousieTicketGenerator {
    private static final Logger logger = Logger.getLogger(HousieTicketGenerator.class.getName());
    private int rows;
    private int columns;
    private int[][] ticket;
    private static final Random random = new Random();

    public HousieTicketGenerator(int rows, int columns) throws HousieTicketException {
        if (rows <= 0 || columns <= 0) {
            throw new HousieTicketException("Rows and columns must be greater than zero.");
        }
        this.rows = rows;
        this.columns = columns;
        this.ticket = new int[rows][columns];
        logger.log(Level.INFO, "Initialized HousieTicketGenerator with rows: {0} and columns: {1}", new Object[]{rows, columns});
    }

    public void generateTicket() {
        for (int col = 0; col < columns; col++) {
            List<Integer> numbers = new ArrayList<>(generateColumnNumbers(col));
            Collections.shuffle(numbers);
            for (int row = 0; row < rows; row++) {
                ticket[row][col] = row < 2 ? numbers.get(row) : 0; // Fill 2 cells in each column
            }
        }
        shuffleRows();
    }

    private List<Integer> generateColumnNumbers(int col) {
        int start = col * 10 + 1;
        int end = (col == 8) ? 90 : start + 9;
        return IntStream.rangeClosed(start, end).boxed().toList();
    }

    private void shuffleRows() {
        for (int row = 0; row < rows; row++) {
            List<Integer> rowList = new ArrayList<>();
            for (int col = 0; col < columns; col++) {
                if (ticket[row][col] != 0) {
                    rowList.add(ticket[row][col]);
                }
            }
            Collections.shuffle(rowList);
            fillRow(row, rowList);
        }
    }

    private void fillRow(int row, List<Integer> numbers) {
        Set<Integer> positions = new HashSet<>();
        while (positions.size() < 5) {
            positions.add(random.nextInt(columns));
        }
        Iterator<Integer> numberIterator = numbers.iterator();
        for (int col = 0; col < columns; col++) {
            if (positions.contains(col) && numberIterator.hasNext()) {
                ticket[row][col] = numberIterator.next();
            } else {
                ticket[row][col] = 0;
            }
        }
    }

    public void printTicket() {
        System.out.println("Housie Ticket:");
        for (int[] row : ticket) {
            for (int number : row) {
                if (number == 0) {
                    System.out.print("  \t");
                } else {
                    System.out.print(number + "\t");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try {
            HousieTicketGenerator generator = new HousieTicketGenerator(3, 9);
            generator.generateTicket();
            generator.printTicket();
        } catch (HousieTicketException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}

