package com.atomikos.icatch.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ConfigPropertiesTestJUnit {
	
	private static final String CUSTOM_PROPERTY_NAME = "bla";
	private ConfigProperties props;

	@Before
	public void setUp() throws Exception {
		props = new ConfigProperties(new Properties());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetPropertyThrowsIfNotSet() {
		props.getProperty("bla");
	}
	
	@Test
	public void testGetAsBoolean() {
		props.setProperty(CUSTOM_PROPERTY_NAME,"true");
		Assert.assertTrue(props.getAsBoolean(CUSTOM_PROPERTY_NAME));
	}

	@Test
	public void testGetAsInt() {
		props.setProperty(CUSTOM_PROPERTY_NAME, "10");
		Assert.assertEquals(10, props.getAsInt(CUSTOM_PROPERTY_NAME));
	}

	@Test
	public void testGetAsLong() {
		props.setProperty(CUSTOM_PROPERTY_NAME, "10");
		Assert.assertEquals(10, props.getAsLong(CUSTOM_PROPERTY_NAME));
	}
	
	@Test
	public void testGetPropertyTrimsSpaces() {
		props.setProperty(CUSTOM_PROPERTY_NAME, " bla ");
		assertEquals("bla", props.getProperty(CUSTOM_PROPERTY_NAME));
	}
	
	@Test
	public void testGetLogBaseDir() {
		props.setProperty("com.atomikos.icatch.log_base_dir", "bla");
		assertEquals("bla", props.getLogBaseDir());
	}
	
	@Test
	public void testGetLogBaseName() {
		props.setProperty("com.atomikos.icatch.log_base_name", "bla");
		assertEquals("bla", props.getLogBaseName());
	}
	
	@Test
	public void testGetEnableLogging() {
		props.setProperty("com.atomikos.icatch.enable_logging", "true");
		assertTrue(props.getEnableLogging());
	}
	
	@Test
	public void testMaxTimeout() {
		props.setProperty("com.atomikos.icatch.max_timeout", "30000");
		assertEquals(30000, props.getMaxTimeout());
	}
	
	@Test 
	public void testMaxActives() {
		props.setProperty("com.atomikos.icatch.max_actives", "100");
		assertEquals(100, props.getMaxActives());
	}
	
	@Test
	public void testThreaded2pc() {
		props.setProperty("com.atomikos.icatch.threaded_2pc", "true");
		assertTrue(props.getThreaded2pc());
	}
	
	@Test
	public void testMergeProperties() {
		props.setProperty(CUSTOM_PROPERTY_NAME, "bla");
		Properties userSpecificProperties = new Properties();
		userSpecificProperties.setProperty("userSpecific", "userBla");
		props.applyUserSpecificProperties(userSpecificProperties);
		assertEquals("bla" , props.getProperty(CUSTOM_PROPERTY_NAME));
		assertEquals("userBla", props.getProperty("userSpecific"));
	}
	
	@Test
	public void testDefaultTmUniqueName() {
		assertNotNull(props.getTmUniqueName());
	}
	
	@Test
	public void testGetPropertiesIncludesSystemProperties() {
		final String name = "com.atomikos.icatch.testGetPropertiesIncludesSystemProperties";
		System.setProperty(name, "bla");
		assertNotNull(props.getCompletedProperties().getProperty(name));
	}
	
	@Test
	public void testForceShutdownOnVmExit() {
		props.setProperty("com.atomikos.icatch.force_shutdown_on_vm_exit", "true");
		Assert.assertTrue(props.getForceShutdownOnVmExit());
		props.setProperty("com.atomikos.icatch.force_shutdown_on_vm_exit", "false");
		Assert.assertFalse(props.getForceShutdownOnVmExit());
	}


	private Properties createDefaultProperties(String name, String value) {
		Properties ret = new Properties();
		ret.setProperty(name, value);
		return ret;
	}
	
	@Test
	public void testDefaultTmUniqueNameDoesNotOverrideAnyCustomSetting() {
		final String NAME = "bla";
		Properties p = createDefaultProperties(ConfigProperties.TM_UNIQUE_NAME_PROPERTY_NAME, NAME);
		props = new ConfigProperties(p);
		assertEquals(NAME, props.getTmUniqueName());
	}
}

