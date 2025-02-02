### Run instructions

Below is an example of how you can run this demo.

0. Prerequisities:
    make sure you have installed git, maven, docker and java 21 on your machine
1. Navigate to the project root folder
2. Build project
   ```sh
   mvn clean install
   ```
3. Build docker image
   ```sh
   docker build -t demo .
   ```
4. Navigate to folder with docker-compose.yml
   ```sh
   cd src/main/docker
   ```
5. Run docker with docker-compose
   ```
   docker-compose up
   ```
6. Check in browser if demo application is correctly started in docker
   ```
   localhost:8081/actuator/health
   ```
7. Test application with POST requests sent to  
   ```
    localhost:8081/cyanapp/api/domain-checker
   ```
8. Test statistics by sending GET request to 
   ```
    localhost:8081/cyanapp/api/domain-checker/statistics
   ```
9. After finished testing stop dockers
   ```
   docker-compose down
   ```