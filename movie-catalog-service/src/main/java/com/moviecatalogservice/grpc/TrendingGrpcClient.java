package com.moviecatalogservice.grpc;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import trending.TopRequest;
import trending.TopResponse;
import trending.TrendingServiceGrpc;

import java.util.Collections;
import java.util.List;

@Service
public class TrendingGrpcClient {

    @GrpcClient("trendingService")
    private TrendingServiceGrpc.TrendingServiceBlockingStub stub;

    public List<String> getTopMovies(int limit) {
        try {
            TopRequest request = TopRequest.newBuilder()
                    .setLimit(limit)
                    .build();

            TopResponse response = stub.getTopMovies(request);

            return response.getMovieIdsList();

        } catch (StatusRuntimeException e) {
            // In case gRPC service is down or fails
            System.err.println("gRPC call failed: " + e.getStatus());
            return Collections.emptyList();
        }
    }
}