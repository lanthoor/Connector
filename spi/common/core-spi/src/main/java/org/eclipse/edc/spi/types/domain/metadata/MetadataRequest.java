/*
 *  Copyright (c) 2020, 2021 Fraunhofer Institute for Software and Systems Engineering
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Fraunhofer Institute for Software and Systems Engineering - initial API and implementation
 *
 */

package org.eclipse.edc.spi.types.domain.metadata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.eclipse.edc.spi.types.domain.message.RemoteMessage;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.Objects;

/**
 * A request for asset metadata sent to a remote system.
 *
 * @deprecated only used by ids multipart api/dispatcher. Can be removed with IDS modules.
 */
@Deprecated(forRemoval = true)
@JsonDeserialize(builder = MetadataRequest.Builder.class)
public class MetadataRequest implements RemoteMessage {

    private final String protocol;
    private final String connectorId;
    private final String callbackAddress;
    private final URI requestedAsset;

    private MetadataRequest(@NotNull String protocol, @NotNull String connectorId, @NotNull String callbackAddress, URI requestedAsset) {
        this.protocol = protocol;
        this.connectorId = connectorId;
        this.callbackAddress = callbackAddress;
        this.requestedAsset = requestedAsset;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    public String getConnectorId() {
        return connectorId;
    }

    @Override
    public String getCounterPartyAddress() {
        return callbackAddress;
    }

    public URI getRequestedAsset() {
        return requestedAsset;
    }

    public static class Builder {
        private String protocol;
        private String connectorId;
        private String callbackAddress;
        private URI requestedAsset;

        private Builder() {
        }

        @JsonCreator
        public static Builder newInstance() {
            return new Builder();
        }

        public Builder protocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder connectorId(String connectorId) {
            this.connectorId = connectorId;
            return this;
        }

        public Builder connectorAddress(String callbackAddress) {
            this.callbackAddress = callbackAddress;
            return this;
        }

        public Builder requestedAsset(URI elementId) {
            this.requestedAsset = elementId;
            return this;
        }

        public MetadataRequest build() {
            Objects.requireNonNull(protocol, "protocol");
            Objects.requireNonNull(connectorId, "connectorId");
            Objects.requireNonNull(callbackAddress, "callbackAddress");
            return new MetadataRequest(protocol, connectorId, callbackAddress, requestedAsset);
        }
    }
}
