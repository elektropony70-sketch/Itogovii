package praktikum;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class IngredientTest {

    private final IngredientType type;
    private final String name;
    private final float price;


    public IngredientTest(IngredientType type, String name, float price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    @Parameterized.Parameters(name = "Тест {index}: Тип={0}, Название={1}, Цена={2}")
    public static Collection<Object[]> getData() {
        return Arrays.asList(new Object[][]{
                {IngredientType.SAUCE, "hot sauce", 100.0f},
                {IngredientType.FILLING, "cutlet", 200.50f},
                {IngredientType.SAUCE, "", 0.0f},
                {IngredientType.FILLING, "Dinosaur-123", -10.0f},
                {null, "ghost ingredient", 50.0f}
        });
    }

    @Test
    public void testGetTypeReturnsCorrectValue() {
        Ingredient ingredient = new Ingredient(type, name, price);
        Assert.assertEquals("Метод getType() вернул неверный тип ингредиента", type, ingredient.getType());
    }

    @Test
    public void testGetNameReturnsCorrectValue() {
        Ingredient ingredient = new Ingredient(type, name, price);
        Assert.assertEquals("Метод getName() вернул неверное название ингредиента", name, ingredient.getName());
    }

    @Test
    public void testGetPriceReturnsCorrectValue() {
        Ingredient ingredient = new Ingredient(type, name, price);
        // Используем дельту 0.0f для точного сравнения типа float
        Assert.assertEquals("Метод getPrice() вернул неверную стоимость ингредиента", price, ingredient.getPrice(), 0.0f);
    }
}