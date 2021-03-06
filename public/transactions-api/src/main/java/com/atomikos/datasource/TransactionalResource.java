/**
 * Copyright (C) 2000-2010 Atomikos <info@atomikos.com>
 *
 * This code ("Atomikos TransactionsEssentials"), by itself,
 * is being distributed under the
 * Apache License, Version 2.0 ("License"), a copy of which may be found at
 * http://www.atomikos.com/licenses/apache-license-2.0.txt .
 * You may not use this file except in compliance with the License.
 *
 * While the License grants certain patent license rights,
 * those patent license rights only extend to the use of
 * Atomikos TransactionsEssentials by itself.
 *
 * This code (Atomikos TransactionsEssentials) contains certain interfaces
 * in package (namespace) com.atomikos.icatch
 * (including com.atomikos.icatch.Participant) which, if implemented, may
 * infringe one or more patents held by Atomikos.
 * It should be appreciated that you may NOT implement such interfaces;
 * licensing to implement these interfaces must be obtained separately from Atomikos.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.atomikos.datasource;

import com.atomikos.icatch.CompositeTransaction;

/**
 * Represents the abstraction of a data source that
 * supports transactions and recovery.
 */

public interface TransactionalResource extends RecoverableResource
{
    /**
     * Gets or creates a ResourceTransaction. This instructs the resource
     * to internally start a context for a new transaction.
     * If the resource decides to return a new instance, it should
     * also make sure that before returning, the new resource 
     * transaction is registered as a participant for the supplied
     * composite transaction.
     *
     */

    public ResourceTransaction 
        getResourceTransaction ( CompositeTransaction compositeTransaction ) 
        throws IllegalStateException, ResourceException;



}
