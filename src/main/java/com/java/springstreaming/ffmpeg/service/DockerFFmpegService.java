package com.java.springstreaming.ffmpeg.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Ports;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DockerFFmpegService {

    private final DockerClient docker;
    private String containerId;
    private String containerName;


    public String startMongo() {
        String image = "mongo:latest";
        this.containerName = "mongo-test-" + UUID.randomUUID();

        ExposedPort mongoTcp = ExposedPort.tcp(27017);
        Ports portBindings = new Ports();
        portBindings.bind(mongoTcp, Ports.Binding.empty()); // 랜덤 포트에 실행

        List<String> env = List.of("MONGO_INITDB_ROOT_USERNAME=test", "MONGO_INITDB_ROOT_PASSWORD=test");

        HostConfig hostConfig = HostConfig.newHostConfig()
                .withAutoRemove(true)
                .withPortBindings(portBindings);

        CreateContainerResponse created = docker.createContainerCmd(image)
                .withName(containerName)
                .withEnv(env)
                .withExposedPorts(mongoTcp)
                .withHostConfig(hostConfig)
                .exec();

        this.containerId = created.getId();

        docker.startContainerCmd(containerId).exec();
        return "ok";
    }
}
