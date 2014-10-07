package pl.coursera.mszarlinski.symptoms;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import pl.coursera.mszarlinski.symptoms.configuration.Application;

/**
 * 
 * @author Maciej
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = Application.class, loader = AnnotationConfigContextLoader.class)
@Ignore //ignore empty test set
public class SymptomsContextTest {
	

}
