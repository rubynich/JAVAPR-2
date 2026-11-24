package ru.edu.pr06;

/** Демонстрационный запуск на данных sample.csv. */
public class WarehouseDemo {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        warehouse.addItem(new StockItem("NB-01", "Notebook", Category.COMPUTER, 12));
        warehouse.addItem(new StockItem("MN-01", "Monitor", Category.DISPLAY, 7));
        warehouse.addItem(new StockItem("KB-01", "Keyboard", Category.PERIPHERAL, 20));
        warehouse.receive("NB-01", 5);
        warehouse.issue("KB-01", 3);
        System.out.println("СКЛАДСКИЕ ПОЗИЦИИ");
        warehouse.getAllItems().forEach(System.out::println);
        System.out.println("\nИТОГИ ПО КАТЕГОРИЯМ");
        warehouse.totalsByCategory().forEach((category, total) -> System.out.println(category + ": " + total));
        System.out.println("\nОпераций в журнале: " + warehouse.getMovements().size());
    }
}
