/* 
 * Copyright (C) 2010-2013 by PhonyTive LLC (http://phonytive.com)
 * http://astivetoolkit.org
 *
 * This file is part of Astive Toolkit(ATK)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.astivetoolkit.agi.command.test;

import junit.framework.TestCase;
import org.astivetoolkit.agi.AgiException;
import org.astivetoolkit.agi.CommandProcessor;
import org.astivetoolkit.agi.command.Verbose;

/**
 * Test case for command {@link org.astivetoolkit.agi.command.Verbose}.
 * 
 * @since 1.0.0
 */
public class VerboseTest extends TestCase {
  /**
   * Creates a new VerboseTest object.
   *
   * @param testName {@inheritDoc}.
   */
  public VerboseTest(String testName) {
    super(testName);
  }

  /**
   * Test method.
   *
   * @throws AgiException if command is malformed.
   */
  public void testCommand() throws AgiException {
    String message = "This is a message";
    int level = 2;

    StringBuilder b = new StringBuilder("VERBOSE");
    b.append(" ");
    b.append("\"");
    b.append(message);
    b.append("\"");
    b.append(" ");
    b.append(level);

    Verbose command = new Verbose(message, level);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));
  }
}
