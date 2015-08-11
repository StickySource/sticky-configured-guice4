package net.stickycode.configured.guice3.finder;

import java.lang.reflect.Type;
import java.util.Set;

import javax.inject.Inject;

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.ProvisionException;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;

import net.stickycode.configured.finder.BeanFinder;
import net.stickycode.configured.finder.BeanNotFoundException;
import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class GuiceBeanFinder
    implements BeanFinder {

  @Inject
  Injector injector;

  @Override
  public <T> T find(Class<T> type) throws BeanNotFoundException {
    try {
      return injector.getInstance(type);
    }
    catch (ProvisionException e) {
      throw new BeanNotFoundException(e, type);
    }
    catch (ConfigurationException e) {
      try {
        Set<T> beans = injector.getInstance(Key.get(setOf(TypeLiteral.get(type))));
        throw new BeanNotFoundException(type, beans);
      }
      catch (ConfigurationException e2) {
        throw new BeanNotFoundException(e, type);
      }
    }
  }

  @SuppressWarnings("unchecked")
  // wrapping a T in a Set safely returns a Set<T>
  static <T> TypeLiteral<Set<T>> setOf(TypeLiteral<T> elementType) {
    Type type = Types.setOf(elementType.getType());
    return (TypeLiteral<Set<T>>) TypeLiteral.get(type);
  }
}
