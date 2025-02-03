setup:
	make -C app setup

test:
	make -C app test

lint:
	make -C app lint

clean:
	make -C app clean

build:
	make -C app build

report:
	make -C app report

check-updates:
	make -C app check-updates

# Если у вас есть отдельная цель "run" в целевом Makefile
code-run:
	make -C app run
