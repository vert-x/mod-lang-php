/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the MIT License (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertx.lang.php.eventbus;

/**
 * An event bus reply failure.
 *
 * @author Jordan Halterman
 */
public class ReplyFailure {

  /**
   * Indicates a reply timeout.
   */
  public static final int TIMEOUT = 0;

  /**
   * Indicates no handlers.
   */
  public static final int NO_HANDLERS = 1;

  /**
   * Indicates a recipient side failure.
   */
  public static final int RECIPIENT_FAILURE = 2;

}
