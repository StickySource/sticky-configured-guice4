/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package net.stickycode.configured.guice3;

import net.stickycode.coercion.Coercion;
import net.stickycode.coercion.CoercionFinder;
import net.stickycode.coercion.Coercions;
import net.stickycode.coercion.PatternCoercion;
import net.stickycode.configured.ConfigurationListener;
import net.stickycode.configured.ConfigurationRepository;
import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.configured.ConfiguredConfigurationListener;
import net.stickycode.configured.InlineConfigurationRepository;
import net.stickycode.configured.finder.BeanFinder;
import net.stickycode.configured.guice3.finder.GuiceBeanFinder;
import net.stickycode.metadata.MetadataResolverRegistry;
import net.stickycode.metadata.ReflectiveMetadataResolverRegistry;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.multibindings.Multibinder;

public class ConfiguredModule
    extends AbstractModule {

  @Override
  protected void configure() {
    bind(ConfigurationSystem.class).in(Singleton.class);

    Multibinder<ConfigurationListener> configurationListener = Multibinder.newSetBinder(binder(), ConfigurationListener.class);
    configurationListener.addBinding().to(ConfiguredConfigurationListener.class);

    bind(ConfiguredInjector.class).in(Singleton.class);
    bind(ConfigurationRepository.class).to(InlineConfigurationRepository.class).in(Singleton.class);
    bind(MetadataResolverRegistry.class).to(ReflectiveMetadataResolverRegistry.class);
    bind(BeanFinder.class).to(GuiceBeanFinder.class);
    bindCoercions();
    ConfiguredTypeListener listener = new ConfiguredTypeListener();
    requestInjection(listener);
    bindListener(Matchers.any(), listener);
  }

  // XXX So coercion needs to be '' not '<?>' as there is no way that I could figure
  // out how to actually get guice to multibind to a set of Coercion<?>
  @SuppressWarnings("rawtypes")
  private void bindCoercions() {
    TypeLiteral<Coercion> literal = TypeLiteral.get(Coercion.class);
    Multibinder<Coercion> extensions = Multibinder.newSetBinder(binder(), literal);
    extensions.addBinding().to(PatternCoercion.class);
    bind(CoercionFinder.class).to(Coercions.class);
  }

}
