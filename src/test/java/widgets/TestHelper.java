package widgets;

import java.util.Random;

public class TestHelper {

    private static Random random = new Random();

    public static int randomInt() {
        return random.nextInt();
    }

    public static int randomPositiveInt() {
        return random.nextInt(50) + 1;
    }

}
