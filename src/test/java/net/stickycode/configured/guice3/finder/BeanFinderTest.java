package net.stickycode.configured.guice3.finder;

import javax.inject.Singleton;

import net.stickycode.configured.finder.AbstractBeanFinderTest;
import net.stickycode.configured.finder.Bean;
import net.stickycode.configured.finder.BeanFinder;
import net.stickycode.configured.finder.SingletonBean;
import net.stickycode.configured.finder.TooMany;
import net.stickycode.configured.finder.TooManyOne;
import net.stickycode.configured.finder.TooManyTwo;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.multibindings.Multibinder;

public class BeanFinderTest
    extends AbstractBeanFinderTest {

  @Override
  protected BeanFinder getFinder() {
    Injector injector = Guice.createInjector(new AbstractModule() {

      @Override
      protected void configure() {
        bind(BeanFinder.class).to(GuiceBeanFinder.class);
        bind(Bean.class);
        bind(SingletonBean.class).in(Singleton.class);

        Multibinder<TooMany> binder = Multibinder.newSetBinder(binder(), TooMany.class);
        binder.addBinding().to(TooManyOne.class);
        binder.addBinding().to(TooManyTwo.class);

        binder().requireExplicitBindings();
      }

    });
    return injector.getInstance(BeanFinder.class);
  }
}
