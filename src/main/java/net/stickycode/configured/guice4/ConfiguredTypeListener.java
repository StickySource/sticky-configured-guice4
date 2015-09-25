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
package net.stickycode.configured.guice4;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import net.stickycode.configured.ConfiguredMetadata;
import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyFramework;

@StickyComponent
@StickyFramework
public class ConfiguredTypeListener
    implements TypeListener {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  private ConfiguredInjector membersInjector;

  @Inject
  ConfiguredMetadata annotations;

  @Override
  public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
    if (annotations != null) // happens when guice is starting up
      if (annotations.typeIsConfigured(type.getRawType())) {
        if (membersInjector == null)
          throw new AssertionError("On hearing " + type + " found that " + getClass().getSimpleName() + " was not injected with a "
              + ConfiguredInjector.class.getSimpleName());

        encounter.register(membersInjector);
        log.debug("encountering {} registering injector {}", type, membersInjector);
      }
  }

}
