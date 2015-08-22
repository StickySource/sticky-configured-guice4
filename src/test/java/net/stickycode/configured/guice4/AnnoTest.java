/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.RedEngine.co.nz. All rights reserved.
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

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;

import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.component.StickyMapper;
import net.stickycode.stereotype.component.StickyRepository;

import org.junit.Test;

@StickyRepository
public class AnnoTest {

  
  @Test
  public void howToFindMetaAnnotation() {
    assertThat(StickyMapper.class.isAnnotationPresent(StickyComponent.class)).isTrue();
    
    for (Annotation a : AnnoTest.class.getAnnotations()) {
      assertThat(a.annotationType().isAnnotationPresent(StickyComponent.class)).isTrue();
    }
  }
  
  @Test
  public void howNotToFindMetaAnnotation() {
    for (Annotation a : AnnoTest.class.getAnnotations()) {
      assertThat(a.getClass().isAnnotationPresent(StickyComponent.class)).isFalse();
    }
  }
}
