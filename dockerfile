From openjdk:8-alpine
copy .target/amsrestapplication.jar amsrestapplication.jar
CMD ["java","-jar","amsrestapplication.jar"]