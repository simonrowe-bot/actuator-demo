package io.pivotal.pa.actuatordemo;

import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ActuatorDemoApplication {

	private static final Logger log = LoggerFactory.getLogger(ActuatorDemoApplication.class);

	@Autowired
	private CounterDao counterDao;

	public static void main(String[] args) {
		SpringApplication.run(ActuatorDemoApplication.class, args);
	}

	@GetMapping("/")
	@Timed("sayHello")
	public String sayHello() {
		incrementPageCount();
		log.debug(String.format("Page count is: %s", getCount()));
		return String.format("Hello RBS - This page has been viewed %s times", getCount());
	}

	private void incrementPageCount() {
		Counter counter = null;
		if (counterDao.findAll().iterator().hasNext()) {
			counter = counterDao.findAll().iterator().next();
		} else {
			counter = new Counter();
			counter.setCount(0);
		}
		counter.setCount(counter.getCount() + 1);
		counterDao.save(counter);
	}

	private Integer getCount() {
		return counterDao.findAll().iterator().next().getCount();
	}



}

