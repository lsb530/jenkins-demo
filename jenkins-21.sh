docker run -d -p 8088:8080 -p 9090:9090 -p 50000:50000 --name jenkins-server \
	--restart=on-failure \
  --privileged \
  -v /var/run/docker.sock:/var/run/docker.sock \
	-v jenkins_home:/var/jenkins_home \
	-u root \
	jenkins/jenkins:lts-jdk21