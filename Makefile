setup:
	make -C code/app setup

test:
	make -C code/app test

lint:
	make -C code/app lint

clean:
	make -C code/app clean

build:
	make -C code/app build

report:
	make -C code/app report

check-updates:
	make -C code/app check-updates

code-run:
	make -C code/app run
