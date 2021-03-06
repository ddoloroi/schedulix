/*
Copyright (c) 2000-2013 "independIT Integrative Technologies GmbH",
Authors: Ronald Jeninga, Dieter Stubler

schedulix Enterprise Job Scheduling System

independIT Integrative Technologies GmbH [http://www.independit.de]
mailto:contact@independit.de

This file is part of schedulix

schedulix is free software:
you can redistribute it and/or modify it under the terms of the
GNU Affero General Public License as published by the
Free Software Foundation, either version 3 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
*/


package de.independit.scheduler.server.parser;

import de.independit.scheduler.server.*;
import de.independit.scheduler.server.repository.*;
import de.independit.scheduler.server.exception.*;

public class DropEvent
	extends Node
{
	public static final String __version = "@(#) $Id: DropEvent.java,v 2.3.4.1 2013/03/14 10:24:29 ronald Exp $";

	private final ObjectURL obj;
	private final boolean noerr;

	public DropEvent (ObjectURL o, Boolean ne)
	{
		super();
		obj = o;
		noerr = ne.booleanValue();
	}

	public void go (SystemEnvironment sysEnv)
	throws SDMSException
	{
		final SDMSEvent evt = (SDMSEvent) obj.resolve(sysEnv);
		final Long evtId = obj.objId;

		if (SDMSScheduledEventTable.idx_evtId.containsKey (sysEnv, evtId))
			throw new CommonErrorException (new SDMSMessage (sysEnv, "04203162027", "Event in use by Scheduled Event(s)"));

		EventParameter.kill (sysEnv, evtId);

		evt.delete (sysEnv);

		result.setFeedback (new SDMSMessage (sysEnv, "04203161916", "Event dropped"));
	}
}
