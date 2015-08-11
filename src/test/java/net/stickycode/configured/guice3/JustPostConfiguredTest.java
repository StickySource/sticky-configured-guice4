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

import net.stickycode.bootstrap.guice3.StickyModule;
import net.stickycode.configured.AbstractJustPostConfiguredTest;
import net.stickycode.exception.PermanentException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.ProvisionException;

import de.devsurf.injection.guice.scanner.PackageFilter;

public class JustPostConfiguredTest
    extends AbstractJustPostConfiguredTest {

  protected void configured(Object target) {
    PackageFilter packageFilter = PackageFilter.create("net.stickycode");
    Module startup = StickyModule.bootstrapModule(packageFilter);
    Injector injector = Guice.createInjector(startup)
        .createChildInjector(StickyModule.applicationModule(packageFilter));
    try {
      injector.injectMembers(this);
      injector.injectMembers(target);
    }
    catch (ProvisionException e) {
      if (e.getCause() instanceof PermanentException)
        throw (PermanentException) e.getCause();

      throw e;
    }
  }

}
