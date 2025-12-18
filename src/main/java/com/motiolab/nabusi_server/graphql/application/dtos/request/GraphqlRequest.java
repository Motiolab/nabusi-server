package com.motiolab.nabusi_server.graphql.application.dtos.request;

import java.util.Map;

public record GraphqlRequest(String query, Map<String, Object> variables) {
}
