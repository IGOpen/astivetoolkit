/*
 * Copyright (C) 2010-2014 by PhonyTive LLC (http://phonytive.com)
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
package org.astivetoolkit;

import org.apache.log4j.Logger;
import org.astivetoolkit.event.EventType;
import org.astivetoolkit.event.ManagerEvent;
import org.astivetoolkit.util.AppLocale;

/**
 *
 * @since 1.1
 */
public class ManagerEventFactory {
  private static final Logger logger = Logger.getLogger(MessageHandler.class);
  private static ManagerEventFactory INSTANCE = new ManagerEventFactory();

  private ManagerEventFactory() {
  }

  public ManagerEvent getEvent(Object source, Message msg) {
    logger.warn(AppLocale.getI18n("unknownEvent",
                                  new Object[] { msg.getMessageLines().get(0), msg.toString() }));

    return new ManagerEvent(source, EventType.UNKNOWN, msg.getParams());
  }

  public static ManagerEventFactory getInstance() {
    return INSTANCE;
  }

  // TODO: Implement a "create" method for each event type
}
