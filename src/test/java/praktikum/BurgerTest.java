package praktikum;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private final Burger burger = new Burger();

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient sauce;

    @Mock
    private Ingredient filling;

    @Test
    public void testSetBuns() {
        burger.setBuns(mockBun);
        Assert.assertEquals("Булка не установилась в бургер", mockBun, burger.bun);
    }

    @Test
    public void testAddIngredientSize() {
        burger.addIngredient(sauce);
        Assert.assertEquals("Ингредиент не добавился в список (неверный размер)", 1, burger.ingredients.size());
    }

    @Test
    public void testAddIngredientCorrectObject() {
        burger.addIngredient(sauce);
        Assert.assertEquals("В списке сохранен неверный ингредиент", sauce, burger.ingredients.get(0));
    }

    @Test
    public void testRemoveIngredientIsEmpty() {
        burger.addIngredient(sauce);
        burger.removeIngredient(0);
        Assert.assertTrue("Список ингредиентов должен быть пустым после удаления", burger.ingredients.isEmpty());
    }

    @Test
    public void testMoveIngredientFirstPosition() {
        burger.addIngredient(sauce);   // индекс 0
        burger.addIngredient(filling); // индекс 1

        burger.moveIngredient(0, 1);

        Assert.assertEquals("На индексе 0 должен быть второй элемент (filling)", filling, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredientSecondPosition() {
        burger.addIngredient(sauce);   // индекс 0
        burger.addIngredient(filling); // индекс 1

        burger.moveIngredient(0, 1);

        Assert.assertEquals("На индексе 1 должен быть первый элемент (sauce)", sauce, burger.ingredients.get(1));
    }
}