package com.atomikos.icatch.jta.hibernate3;

import junit.framework.TestCase;

import org.hibernate.HibernateException;

public class TransactionManagerLookupTestJUnit extends TestCase {

	private TransactionManagerLookup lookup;
	
	protected void setUp() throws Exception {
		super.setUp();
		lookup = new TransactionManagerLookup();
	}
	
	public void testTransactionManager() throws HibernateException {
		assertNotNull ( lookup.getTransactionManager( null) );
	}
	
	public void testName() throws Exception
	{
		assertNull ( lookup.getUserTransactionName() );
	}

}
