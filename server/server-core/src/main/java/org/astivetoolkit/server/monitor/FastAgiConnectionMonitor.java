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
package org.astivetoolkit.server.monitor;

import java.io.IOException;
import java.net.SocketPermission;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.astivetoolkit.AstiveException;
import org.astivetoolkit.agi.AgiCommandHandler;
import org.astivetoolkit.agi.AgiException;
import org.astivetoolkit.agi.AgiResponse;
import org.astivetoolkit.agi.Connection;
import org.astivetoolkit.agi.fastagi.FastAgiConnection;
import org.astivetoolkit.agi.fastagi.FastAgiResponse;
import org.astivetoolkit.astivlet.AstivletRequest;
import org.astivetoolkit.astivlet.AstivletResponse;
import org.astivetoolkit.server.AstivletProcessor;
import org.astivetoolkit.server.ConnectionManager;
import org.astivetoolkit.server.FastAgiConnectionManager;
import org.astivetoolkit.server.FastAgiServerSocket;
import org.astivetoolkit.server.security.AstPolicy;
import org.astivetoolkit.server.security.AstPolicyUtil;
import org.astivetoolkit.util.AppLocale;

/**
 *
 * @since 1.0.0
 * @see ConnectionMonitor
 */
public class FastAgiConnectionMonitor implements ConnectionMonitor {
  // A usual logging class
  private static final Logger LOG = Logger.getLogger(FastAgiConnectionMonitor.class);
  private ConnectionManager manager;
  private FastAgiServerSocket server;
  private ThreadPoolExecutor threadPoolExecutor;

  /**
   * Creates a new FastAgiConnectionMonitor object.
   *
   * @param server DOCUMENT ME!
   * @param threads DOCUMENT ME!
   */
  public FastAgiConnectionMonitor(FastAgiServerSocket server, int threads) {
    if (LOG.isDebugEnabled()) {
      LOG.debug(AppLocale.getI18n("startingConnectionMonitor"));
    }

    this.server = server;
    manager = new FastAgiConnectionManager();

    // TODO: This should be a parameter
    int corePoolSize = threads;
    int maxPoolSize = threads;
    long keepAliveTime = 0x1388;

    threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime,
                                                TimeUnit.MILLISECONDS,
                                                new LinkedBlockingQueue<Runnable>());
  }

  /**
   * DOCUMENT ME!
   *
   * @param conn DOCUMENT ME!
   *
   * @throws AstiveException DOCUMENT ME!
   */
  @Override
  public void processConnection(final Connection conn)
                         throws AstiveException {
    try {
      if (LOG.isDebugEnabled()) {
        LOG.debug(AppLocale.getI18n("processingCall"));
      }

      FastAgiConnection fastConn = (FastAgiConnection) conn;

      StringBuilder sbr = new StringBuilder();
      sbr.append(fastConn.getSocket().getInetAddress().getHostAddress());
      sbr.append(":");
      sbr.append(fastConn.getSocket().getPort());

      SocketPermission sp = new SocketPermission(sbr.toString(), AstPolicy.DEFAULT_ACTION);

      if (AstPolicyUtil.hasPermission(sp)) {
        AgiCommandHandler cHandler = new AgiCommandHandler(conn);
        FastAgiResponse response = new FastAgiResponse(cHandler);
        AstivletRequest aRequest =
          new AstivletRequest(cHandler.getAgiRequest().getLines(), fastConn);
        AstivletResponse aResponse = new AstivletResponse((AgiResponse) response);

        AstivletProcessor.invokeAstivlet(aRequest, aResponse);

        if (LOG.isDebugEnabled()) {
          LOG.debug("done.");
        }
      } else {
        LOG.error(AppLocale.getI18n("unableToPlaceCallCheckNetPermissions"));

        try {
          fastConn.getSocket().close();
        } catch (IOException ex) {
          // Drop connection
        }
      }
    } catch (AgiException ex) {
      LOG.error(AppLocale.getI18n("unexpectedError", new Object[] { ex.getMessage() }));
    }
  }

  /**
   * DOCUMENT ME!
   */
  @Override
  public void run() {
    while (true) {
      final FastAgiConnection conn;

      try {
        conn = server.acceptConnection();

        // TODO: This should be configurable.
        if (threadPoolExecutor.getMaximumPoolSize() == threadPoolExecutor.getTaskCount()) {
          conn.close();

          continue;
        }

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
              manager.add(conn);

              try {
                processConnection(conn);
              } catch (AstiveException ex) {
                LOG.warn(ex.getMessage());
              }

              try {
                manager.remove(conn);
              } catch (IOException ex) {
                LOG.error(AppLocale.getI18n("unableToPerformIOOperations",
                                            new Object[] { ex.getMessage() }));
              }
            }
          });
      } catch (IOException ex) {
        LOG.error(AppLocale.getI18n("unableToPerformIOOperations", new Object[] { ex.getMessage() }));
      }
    }
  }
}