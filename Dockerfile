FROM gradle:8.5.0-jdk21

ENV PORT=7070

WORKDIR /

COPY / .

RUN gradle installDist

CMD ./build/install/app/bin/app --spring.profiles.active=production