package net.stickycode.configured.guice3.strategy;

import java.beans.Introspector;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import net.stickycode.configured.strategy.StrategyFinder;
import net.stickycode.configured.strategy.StrategyNotFoundException;
import net.stickycode.stereotype.StickyComponent;

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;

@StickyComponent
public class GuiceStrategyFinder
    implements StrategyFinder {

  @Inject
  Injector injector;
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Object findWithName(Class contract, String name) throws StrategyNotFoundException {
    Binding<Set> b = injector.getBinding(Key.get(setOf(TypeLiteral.get(contract))));
    for (Object o : b.getProvider().get()) {
      if (name.equals(Introspector.decapitalize(o.getClass().getSimpleName())))
        return o;
    }
    
    throw new StrategyNotFoundException(contract, name, Collections.<String>emptySet());
  }

  @SuppressWarnings("unchecked")
  // wrapping a T in a Set safely returns a Set<T>
  static <T> TypeLiteral<Set<T>> setOf(TypeLiteral<T> elementType) {
    Type type = Types.setOf(elementType.getType());
    return (TypeLiteral<Set<T>>) TypeLiteral.get(type);
  }

}
