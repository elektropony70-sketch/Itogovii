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
    private Ingredient mockIngredient1;

    @Mock
    private Ingredient mockIngredient2;

    @Test
    public void testSetBuns() {
        burger.setBuns(mockBun);
        Assert.assertEquals("Булка не установилась в бургер", mockBun, burger.bun);
    }

    @Test
    public void testAddIngredient() {
        burger.addIngredient(mockIngredient1);
        Assert.assertEquals("Ингредиент не добавился в список", 1, burger.ingredients.size());
        Assert.assertEquals("В списке сохранен неверный ингредиент", mockIngredient1, burger.ingredients.get(0));
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(mockIngredient1);
        burger.removeIngredient(0);
        Assert.assertTrue("Список ингредиентов должен быть пустым после удаления", burger.ingredients.isEmpty());
    }

    @Test
    public void testMoveIngredient() {
        burger.addIngredient(mockIngredient1); // индекс 0
        burger.addIngredient(mockIngredient2); // индекс 1

        // Метод во внутреннем коде: ingredients.add(newIndex, ingredients.remove(index));
        burger.moveIngredient(0, 1);

        Assert.assertEquals("Ингредиенты не поменялись местами (на индексе 0 должен быть второй элемент)",
                mockIngredient2, burger.ingredients.get(0));
        Assert.assertEquals("Ингредиенты не поменялись местами (на индексе 1 должен быть первый элемент)",
                mockIngredient1, burger.ingredients.get(1));
    }

    @Test
    public void testGetPrice() {
        Mockito.when(mockBun.getPrice()).thenReturn(100.0f);
        Mockito.when(mockIngredient1.getPrice()).thenReturn(50.0f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);

        // Формула из вашего кода: price = bun.getPrice() * 2 + ингредиенты
        // (100 * 2) + 50 = 250
        float expectedPrice = 250.0f;

        Assert.assertEquals("Итоговая стоимость бургера рассчитана некорректно", expectedPrice, burger.getPrice(), 0.0f);
    }

    @Test
    public void testGetReceipt() {
        Mockito.when(mockBun.getName()).thenReturn("Краторная булка");
        Mockito.when(mockBun.getPrice()).thenReturn(100.0f);

        Mockito.when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(mockIngredient1.getName()).thenReturn("Соус чили");
        Mockito.when(mockIngredient1.getPrice()).thenReturn(50.0f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);

        String receipt = burger.getReceipt();

        // Проверяем, что чек строится по правильному шаблону и содержит ключевые строки
        Assert.assertTrue("Чек должен содержать название булки в шапке", receipt.contains("(==== Краторная булка ====)"));
        Assert.assertTrue("Чек должен содержать тип ингредиента в нижнем регистре", receipt.contains("= sauce "));
        Assert.assertTrue("Чек должен содержать название ингредиента", receipt.contains("Соус чили ="));
        Assert.assertTrue("Чек должен содержать итоговую стоимость бургера", receipt.contains("Price: 250"));
    }
}