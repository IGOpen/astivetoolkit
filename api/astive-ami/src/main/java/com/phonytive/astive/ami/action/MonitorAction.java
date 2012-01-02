// Astive, is the core library of Astive Toolkit, the framework for
// developers wishing to create concise and easy to maintain applications
// for Asterisk® PBX, even for complex navigation.
//
// Copyright (C) 2010-2011 PhonyTive, S.L.
// http://www.phonytive.com/astive
//
// This file is part of Astive
//
// Astive is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Astive is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Astive.  If not, see <http://www.gnu.org/licenses/>.
package com.phonytive.astive.ami.action;


/**
 *
 * @author Pedro Sanders <psanders@kaffeineminds.com>
 * @since 0.1
 * @version $Id$
 */
public class MonitorAction extends ActionMessage {
    private String channel;
    private String file;
    private String format;
    private boolean mix;

    public MonitorAction(String channel) {
        super(ActionType.MONITOR);
        this.channel = channel;
    }

    public MonitorAction(String channel, String file) {
        super(ActionType.MONITOR);
        this.channel = channel;
        this.file = file;
    }

    public MonitorAction(String channel, String file, String format) {
        super(ActionType.MONITOR);
        this.channel = channel;
        this.file = file;
        this.format = format;
    }

    public MonitorAction(String channel, String file, String format, boolean mix) {
        super(ActionType.MONITOR);
        this.channel = channel;
        this.file = file;
        this.format = format;
        this.mix = mix;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isMix() {
        return mix;
    }

    public void setMix(boolean mix) {
        this.mix = mix;
    }
}
