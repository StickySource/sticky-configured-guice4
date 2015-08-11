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

import net.stickycode.configured.ConfiguredBeanProcessor;
import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyFramework;

import com.google.inject.Inject;
import com.google.inject.MembersInjector;

@StickyComponent
@StickyFramework
public class ConfiguredInjector
    implements MembersInjector<Object> {

  @Inject
  private ConfiguredBeanProcessor processor;

  @Override
  public void injectMembers(Object instance) {
    processor.process(instance);
  }

}
