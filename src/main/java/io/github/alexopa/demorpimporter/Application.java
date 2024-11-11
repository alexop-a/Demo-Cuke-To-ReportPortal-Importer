package io.github.alexopa.demorpimporter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import io.github.alexopa.cukereportportal.config.RPImporterProperties;
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

		File[] directories = ResourceUtils.getFile("classpath:cucumber-reports/").listFiles(File::isDirectory);

		for (File dir : directories) {
			log.info("Importing demo from folder: {}", dir.getName());

			String launchName = "Demo Cuke To RP - " + dir.getName();
			log.info("Import cucumber reports to ReportPortal with launch name: {}", launchName);

			Properties demoProperties = new Properties();
			demoProperties.put(RPImporterProperties.RP_IMPORTER_CUCUMBER_JSON_FILES.getPropertyName(),
					Stream.of(dir.listFiles(new FilenameFilter() {

						@Override
						public boolean accept(File dir, String name) {
							return name.endsWith(".json");
						}
					})).map(File::getAbsolutePath).collect(Collectors.joining(";")));
			demoProperties.put(RPImporterProperties.RP_IMPORTER_LAUNCH_NAME.getPropertyName(),
					launchName);

			RPImporterPropertyHandler propertyHandler = new RPImporterPropertyHandler(demoProperties);

			ReportPortalImporter reportPortalImporter = new ReportPortalImporter(propertyHandler);
			reportPortalImporter.importCucumberReports();

		}

	}

}
