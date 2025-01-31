setup:
	./gradlew wrapper --gradle-version 8.5
	cd code/app && ./gradlew clean build
	./gradlew clean compileTest

test:
	./gradlew test

lint:
	./gradlew checkstyleTest checkCode

code-run:
	make -C app run

check-updates:
	./gradlew dependencyUpdates

report:
	make -C app report