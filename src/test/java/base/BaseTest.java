package base;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/spring-applicationContext-test.xml" })
public abstract class BaseTest {
	
//	@Autowired
//	protected SessionFactory sessionFactory;
//	
//	@Autowired
//	protected DataSource dataSource;
//	
//	protected SimpleJdbcTemplate jdbcTemplate;
	
	
	
	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}
}
