package praktikum;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BurgerPriceAndReceiptParameterizedTest {

    private final Burger burger = new Burger();

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredient;

    private final String bunName;
    private final float bunPrice;
    private final IngredientType ingredientType;
    private final String ingredientName;
    private final float ingredientPrice;
    private final float expectedPrice;

    public BurgerPriceAndReceiptParameterizedTest(String bunName, float bunPrice,
                                                  IngredientType ingredientType, String ingredientName,
                                                  float ingredientPrice, float expectedPrice) {
        this.bunName = bunName;
        this.bunPrice = bunPrice;
        this.ingredientType = ingredientType;
        this.ingredientName = ingredientName;
        this.ingredientPrice = ingredientPrice;
        this.expectedPrice = expectedPrice;
    }

    @Parameterized.Parameters(name = "Тест {index}: Булка={0}, Ингредиент={3}")
    public static Collection<Object[]> getData() {
        return Arrays.asList(new Object[][]{
                {"Краторная булка", 100.0f, IngredientType.SAUCE, "Соус чили", 50.0f, 250.0f},
                {"Флюоресцентная булка", 150.0f, IngredientType.FILLING, "Котлета", 120.0f, 420.0f}
        });
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Общая настройка моков для обоих методов
        Mockito.when(mockBun.getName()).thenReturn(bunName);
        Mockito.when(mockBun.getPrice()).thenReturn(bunPrice);
        Mockito.when(mockIngredient.getType()).thenReturn(ingredientType);
        Mockito.when(mockIngredient.getName()).thenReturn(ingredientName);
        Mockito.when(mockIngredient.getPrice()).thenReturn(ingredientPrice);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient);
    }

    @Test
    public void testGetPrice() {
        Assert.assertEquals("Итоговая стоимость рассчитана некорректно", expectedPrice, burger.getPrice(), 0.0f);
    }

    @Test
    public void testGetReceipt() {
        String expectedReceipt = String.format("(==== %s ====)%n", bunName) +
                String.format("= %s %s =%n", ingredientType.name().toLowerCase(), ingredientName) +
                String.format("(==== %s ====)%n", bunName) +
                String.format("%nPrice: %f%n", expectedPrice);

        Assert.assertEquals("Текст чека не совпадает с ожидаемым форматом", expectedReceipt, burger.getReceipt());
    }
}