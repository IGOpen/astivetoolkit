/*
 * Copyright (C) 2010-2012 PhonyTive LLC
 * http://astive.phonytive.com
 *
 * This file is part of Astive Toolkit
 *
 * Astive is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Astive is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Astive.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.astivetoolkit.agi.command.test;

import org.astivetoolkit.agi.command.SpeechLoadGrammar;
import org.astivetoolkit.agi.AgiException;
import org.astivetoolkit.agi.CommandProcessor;
import junit.framework.TestCase;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class SpeechLoadGrammarTest extends TestCase {
  /**
   * Creates a new SpeechLoadGrammarTest object.
   *
   * @param testName DOCUMENT ME!
   */
  public SpeechLoadGrammarTest(String testName) {
    super(testName);
  }

  /**
   * DOCUMENT ME!
   *
   * @throws AgiException DOCUMENT ME!
   */
  public void testCommand() throws AgiException {
    String name = "myGrammar";
    String path = "path-to-grammar";
    StringBuilder b = new StringBuilder("SPEECH LOAD GRAMMAR");
    b.append(" ");
    b.append("\"");
    b.append(name);
    b.append("\"");
    b.append(" ");
    b.append("\"");
    b.append(path);
    b.append("\"");

    SpeechLoadGrammar command = new SpeechLoadGrammar(name, path);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));
  }
}