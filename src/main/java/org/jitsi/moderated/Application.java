package org.jitsi.moderated;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Maps the JS router paths to index.html.
	 *
	 * @return
	 */
	@RequestMapping("/{id:[a-zA-Z0-9]+}")
	public String index() {
		return "index.html";
	}

}
