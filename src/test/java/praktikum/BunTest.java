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

    // Конструктор тестового класса принимает параметры текущего набора данных
    public BunTest(String name, float price) {
        this.name = name;
        this.price = price;
    }

    // Набор тестовых данных для проверки граничных значений и разных типов строк
    @Parameterized.Parameters(name = "Тест {index}: Булка={0}, Цена={1}")
    public static Collection<Object[]> getData() {
        return Arrays.asList(new Object[][]{
                {"Краторная булка", 100.0f}, // Стандартные валидные данные
                {"Флюоресцентная булка", 999.99f}, // Большая цена
                {"", 0.0f}, // Граничное значение: пустая строка и нулевая цена
                {"Bun-123!@#", -50.5f}, // Спецсимволы в названии и отрицательная цена для проверки логики геттера
                {null, 150.0f} // Проверка обработки null значения
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
        // Для сравнения чисел с плавающей точкой (float) третьим параметром передается дельта (погрешность) 0.0f
        Assert.assertEquals("Метод getPrice() вернул неверную стоимость булочки", price, bun.getPrice(), 0.0f);
    }
}