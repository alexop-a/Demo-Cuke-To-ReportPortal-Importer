package io.github.alexopa.demorpimporter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.alexopa.cukereportportal.config.RPImporterPropertyHandler;
import io.github.alexopa.cukereportportal.service.ReportPortalImporter;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Import cucumber reports to ReportPortal");

		RPImporterPropertyHandler propertyHandler = new RPImporterPropertyHandler();
		ReportPortalImporter reportPortalImporter = new ReportPortalImporter(propertyHandler);
		reportPortalImporter.importCucumberReports();

	}

}
