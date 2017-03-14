package com.renlink.push.msg;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.renlink.push.msg.lang.PlatformType;
import com.renlink.push.msg.scheme.MessageScheme;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath*:config/applicationContext-client.xml")
public class PushControllerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws Exception {


//		RmiProxyFactoryBean bean = new RmiProxyFactoryBean();
//		bean.setServiceUrl("rmi://localhost:10098/PushControllerRmi");
//		bean.setServiceInterface(IController.class);
//		bean.setRefreshStubOnConnectFailure(true);
//		bean.setLookupStubOnStartup(false);
		
		StringBuffer sb = new StringBuffer(System.getProperty("user.dir"));
		sb.append("/src/test/java/config/applicationContext-client.xml");

		ApplicationContext context = new FileSystemXmlApplicationContext(
				sb.toString());

		IController controller = (IController)context.getBean("pushController",
				IController.class);
		MessageScheme scheme = new MessageScheme();
		Set<String> alias = new HashSet<String>();
		alias.add("JL");
		scheme.setPlatform(PlatformType.ALL);
		scheme.setAlias(alias);
		scheme.setTitle("");
		scheme.setMsgContent("21212");
		
		controller.pushMsg(scheme);
		
		
	}

}
