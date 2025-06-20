FROM fedora:41

# RUN yum -y install openssh-server
RUN ssh-keygen -A

RUN useradd remote_user && \
    echo "1234" | passwd remote_user  --stdin && \
    mkdir /home/remote_user/.ssh && \
    chmod 700 /home/remote_user/.ssh