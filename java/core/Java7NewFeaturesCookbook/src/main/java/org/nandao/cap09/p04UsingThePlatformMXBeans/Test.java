package org.nandao.cap09.p04UsingThePlatformMXBeans;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.List;

// Java Management Extensions (JMX) is a standard way of adding a management interface
// to an application. A managed bean (MBean) provides the management services for the
// application and is registered with a javax.management.MBeanServer, which holds and
// administers the MBean. A javax.management.MXBean is a type of MBean, which permits
// clients to access the bean without the need to access specific classes.
//
// The java.lang.management package's ManagementFactory class has added several
// new methods to gain access to an MBean. These can then be used to access process and
// load monitoring.

public class Test {

    public static void main(String[] args) {
        final RuntimeMXBean mxBean = ManagementFactory.getPlatformMXBean(RuntimeMXBean.class);

        System.out.println("JVM Name: " + mxBean.getName());
        System.out.println("JVM Specification Name: " + mxBean.getSpecName());
        System.out.println("JVM Specification Version: " + mxBean.getSpecVersion());
        System.out.println("JVM Implementation Name: " + mxBean.getVmName());
        System.out.println("JVM Implementation Vendor: " + mxBean.getVmVendor());
        System.out.println("JVM Implementation Version: " + mxBean.getVmVersion());

        System.out.println("");

        final List<OperatingSystemMXBean> list = ManagementFactory.getPlatformMXBeans(OperatingSystemMXBean.class);

        for (final OperatingSystemMXBean bean : list) {
            System.out.println("Operating System Name: " + bean.getName());
            System.out.println("Operating System Architecture: " + bean.getArch());
            System.out.println("Operating System Version: " + bean.getVersion());
        }
    }

}
