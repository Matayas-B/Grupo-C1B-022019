package backend.architectural;

import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class PackageNameNomenclatureTest {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void allModelEntitiesShouldHaveAnEmptyConstructorTest() throws NoSuchMethodException {
        Reflections reflections = new Reflections("backend.model");
        Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(javax.persistence.Entity.class);
        for (Class myClass : allClasses) {
            myClass.getConstructor();
        }
    }
}
