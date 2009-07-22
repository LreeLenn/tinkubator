package org.linkedprocess.security;

import org.linkedprocess.LinkedProcess;
import junit.framework.TestCase;
import org.jdom.output.XMLOutputter;

import java.util.Properties;

/**
 * Author: josh
 * Date: Jul 16, 2009
 * Time: 4:55:42 PM
 */
public class ServiceDiscoveryConfigurationTest extends TestCase {
    
    public void testAll() throws Exception {
        Properties p = new Properties();
        p.load(VMSecurityManager.class.getResourceAsStream(LinkedProcess.SECURITYDEFAULT_PROPERTIES));
        p.setProperty("org.linkedprocess.security.read", "true");

        VMSecurityManager m = new VMSecurityManager(p);
        PathPermissions pp = new PathPermissions();
        pp.addPermitRule("/tmp/foo/bar");
        pp.addPermitRule("/opt/stuff");
        pp.addPermitRule("/opt/");
        m.setReadPermissions(pp);

        ServiceDiscoveryConfiguration c = new ServiceDiscoveryConfiguration(m);

        XMLOutputter op = new XMLOutputter();
        op.output(c.toElement(), System.out);
    }
}
