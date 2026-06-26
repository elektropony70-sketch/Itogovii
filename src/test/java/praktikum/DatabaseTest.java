package praktikum;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class DatabaseTest {

    private final Database database = new Database();

    @Test
    public void testAvailableBunsSize() {
        List<Bun> buns = database.availableBuns();
        // В конструкторе добавляется ровно 3 булки (black, white, red)
        Assert.assertEquals("Количество доступных булочек в базе данных не равно 3", 3, buns.size());
    }

    @Test
    public void testAvailableBunsContainsSpecificData() {
        List<Bun> buns = database.availableBuns();

        // Проверяем данные первой булочки в списке
        Assert.assertEquals("Неверное название первой булочки", "black bun", buns.get(0).getName());
        Assert.assertEquals("Неверная цена первой булочки", 100.0f, buns.get(0).getPrice(), 0.0f);

        // Проверяем название последней булочки в списке
        Assert.assertEquals("Неверное название третьей булочки", "red bun", buns.get(2).getName());
    }

    @Test
    public void testAvailableIngredientsSize() {
        List<Ingredient> ingredients = database.availableIngredients();
        // В конструкторе добавляется 3 соуса и 3 начинки — всего 6 элементов
        Assert.assertEquals("Количество доступных ингредиентов в базе данных не равно 6", 6, ingredients.size());
    }

    @Test
    public void testAvailableIngredientsContainsSaucesAndFillings() {
        List<Ingredient> ingredients = database.availableIngredients();

        // Проверяем первый соус
        Assert.assertEquals("Первый ингредиент должен быть соусом", IngredientType.SAUCE, ingredients.get(0).getType());
        Assert.assertEquals("Неверное название соуса", "hot sauce", ingredients.get(0).getName());
        Assert.assertEquals("Неверная цена соуса", 100.0f, ingredients.get(0).getPrice(), 0.0f);

        // Проверяем начинку (элемент с индексом 4 — это "dinosaur")
        Assert.assertEquals("Пятый ингредиент должен быть начинкой", IngredientType.FILLING, ingredients.get(4).getType());
        Assert.assertEquals("Неверное название начинки", "dinosaur", ingredients.get(4).getName());
        Assert.assertEquals("Неверная цена начинки", 200.0f, ingredients.get(4).getPrice(), 0.0f);
    }
}
