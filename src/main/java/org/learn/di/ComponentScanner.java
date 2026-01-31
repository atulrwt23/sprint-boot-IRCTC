package org.learn.di;

import org.burningwave.core.assembler.ComponentContainer;
import org.burningwave.core.classes.ClassCriteria;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.SearchConfig;
import org.burningwave.core.io.PathHelper;
import org.learn.di.annotation.Component;

import java.util.Collection;

public class ComponentScanner {
    public static Collection<Class<?>> getAllComponentsClasses() {
        ComponentContainer container = ComponentContainer.getInstance();
        PathHelper pathHelper = container.getPathHelper();
        ClassHunter classHunter = container.getClassHunter();

        SearchConfig searchConfig = SearchConfig.forPaths(pathHelper.getMainClassPaths())
                .by(ClassCriteria.create().allThoseThatMatch(cls ->
                        cls.getAnnotation(Component.class) != null
                ));

        return classHunter.findBy(searchConfig).getClasses();
    }
}
