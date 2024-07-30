package managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManagersTest {

    @Test
    public void getDefault() {
        Assertions.assertNotNull(Managers.getDefault());
    }

    @Test
    public void getDefaultHistory() {
        Assertions.assertNotNull(Managers.getDefaultHistory());
    }

}
