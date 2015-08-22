package net.stickycode.configured.guice3.finder;

import javax.inject.Inject;

import net.stickycode.bootstrap.ComponentContainer;
import net.stickycode.configured.finder.BeanFinder;
import net.stickycode.configured.finder.BeanNotFoundException;
import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class GuiceBeanFinder
    implements BeanFinder {

  @Inject
  ComponentContainer injector;

  @Override
  public <T> T find(Class<T> type) throws BeanNotFoundException {
    return injector.find(type);
  }
}
