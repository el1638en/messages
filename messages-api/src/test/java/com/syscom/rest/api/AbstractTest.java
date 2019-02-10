package com.syscom.rest.api;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.syscom.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AbstractTest {

	protected static final String R_CODE = "CODE";
	protected static final String R_LIBELLE = "LIBELLE";
	protected static final String LOGIN = "LOGIN";
	protected static final String PASSWORD = "PASSWORD";
	protected static final String NAME = "NAME";
	protected static final String FIRST_NAME = "FIRST_NAME";
}
