package ru.edu.pr06;

import java.util.*;

/** Управляет карточками оборудования, остатками и журналом движений. */
public class Warehouse {
    /** Правило варианта 6: максимум единиц в одной операции списания. */
    public static final int MAX_ISSUE_PER_OPERATION = 35;

    private final Map<String, StockItem> items = new HashMap<>();
    private final List<StockMovement> movements = new ArrayList<>();

    public void addItem(StockItem item) {
        Objects.requireNonNull(item, "Карточка оборудования не задана");
        if (items.containsKey(item.getCode()))
            throw new IllegalArgumentException("Позиция с кодом " + item.getCode() + " уже существует");
        items.put(item.getCode(), item);
    }

    public void receive(String code, int q) {
        StockItem item = getRequiredItem(code);
        if (q <= 0) throw new IllegalArgumentException("Количество прихода должно быть положительным");
        item.increase(q);
        movements.add(new StockMovement(item.getCode(), MovementType.RECEIVE, q));
    }

    public void issue(String code, int q) {
        StockItem item = getRequiredItem(code);
        if (q <= 0) throw new IllegalArgumentException("Количество списания должно быть положительным");
        // Индивидуальное правило варианта 6 сосредоточено в одном месте.
        if (q > MAX_ISSUE_PER_OPERATION)
            throw new IllegalArgumentException("За одну операцию можно списать не более " + MAX_ISSUE_PER_OPERATION + " единиц");
        item.decrease(q);
        movements.add(new StockMovement(item.getCode(), MovementType.ISSUE, q));
    }

    private StockItem getRequiredItem(String code) {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("Инвентарный код не задан");
        StockItem item = items.get(code.trim());
        if (item == null) throw new IllegalArgumentException("Позиция с кодом " + code + " не найдена");
        return item;
    }

    /** Возвращает новый список; внутренний список склада не раскрывается. */
    public List<StockItem> findByCategory(Category category) {
        Objects.requireNonNull(category, "Категория не задана");
        List<StockItem> result = new ArrayList<>();
        for (StockItem item : items.values()) if (item.getCategory() == category) result.add(item);
        result.sort(Comparator.comparing(StockItem::getCode));
        return result;
    }

    /** Суммарный остаток по каждой категории. */
    public Map<Category, Integer> totalsByCategory() {
        Map<Category, Integer> totals = new EnumMap<>(Category.class);
        for (Category category : Category.values()) totals.put(category, 0);
        for (StockItem item : items.values())
            totals.merge(item.getCategory(), item.getQuantity(), Math::addExact);
        return Collections.unmodifiableMap(totals);
    }

    /** Уникальный набор категорий, представленных на складе. */
    public Set<Category> getCategories() {
        Set<Category> categories = new HashSet<>();
        for (StockItem item : items.values()) categories.add(item.getCategory());
        return Collections.unmodifiableSet(categories);
    }

    /** Копия журнала операций. */
    public List<StockMovement> getMovements() { return new ArrayList<>(movements); }

    /** Копия набора карточек для просмотра. */
    public List<StockItem> getAllItems() {
        List<StockItem> result = new ArrayList<>(items.values());
        result.sort(Comparator.comparing(StockItem::getCode));
        return result;
    }
}
