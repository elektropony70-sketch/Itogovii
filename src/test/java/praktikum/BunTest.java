package praktikum;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BunTest {

    private final String name;
    private final float price;

    public BunTest(String name, float price) {
        this.name = name;
        this.price = price;
    }

    @Parameterized.Parameters(name = "Тест {index}: Булка={0}, Цена={1}")
    public static Collection<Object[]> getData() {
        return Arrays.asList(new Object[][]{
                {"Краторная булка", 100.0f},
                {"Флюоресцентная булка", 999.99f},
                {"", 0.0f},
                {"Bun-123!@#", -50.5f},
                {null, 150.0f}
        });
    }

    @Test
    public void testGetNameReturnsCorrectValue() {
        Bun bun = new Bun(name, price);
        Assert.assertEquals("Метод getName() вернул неверное название булочки", name, bun.getName());
    }

    @Test
    public void testGetPriceReturnsCorrectValue() {
        Bun bun = new Bun(name, price);
        Assert.assertEquals("Метод getPrice() вернул неверную стоимость булочки", price, bun.getPrice(), 0.0f);
    }
}