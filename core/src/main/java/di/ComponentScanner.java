package di;

import di.annotation.AutoConfiguration;
import di.annotation.Component;

import org.burningwave.core.assembler.ComponentContainer;
import org.burningwave.core.classes.ClassCriteria;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.SearchConfig;
import org.burningwave.core.io.PathHelper;

import java.util.Collection;

public class ComponentScanner {
    public static Collection<Class<?>> getAllComponentsClasses(String rootPath) {
        ComponentContainer container = ComponentContainer.getInstance();
        PathHelper pathHelper = container.getPathHelper();
        ClassHunter classHunter = container.getClassHunter();

        SearchConfig searchConfig = SearchConfig.forPaths(pathHelper.getMainClassPaths())
                .by(ClassCriteria.create().allThoseThatMatch(cls ->
                        cls.isAnnotationPresent(Component.class) || cls.isAnnotationPresent(AutoConfiguration.class)
                ));

        return classHunter.findBy(searchConfig).getClasses();
    }
}
