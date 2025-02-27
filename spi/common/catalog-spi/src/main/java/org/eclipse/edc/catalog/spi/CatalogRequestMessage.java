/*
 *  Copyright (c) 2022 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - Initial implementation
 *
 */

package org.eclipse.edc.catalog.spi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.eclipse.edc.spi.query.QuerySpec;
import org.eclipse.edc.spi.types.domain.message.RemoteMessage;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A request for a participant's {@link Catalog}.
 */
@JsonDeserialize(builder = CatalogRequestMessage.Builder.class)
public class CatalogRequestMessage implements RemoteMessage {

    private String protocol = "unknown";
    @Deprecated(forRemoval = true)
    private String connectorId;
    private String counterPartyAddress;
    private QuerySpec querySpec;

    @NotNull
    @Override
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @NotNull
    @Override
    public String getCounterPartyAddress() {
        return counterPartyAddress;
    }

    @Deprecated
    @NotNull
    public String getConnectorId() {
        return connectorId;
    }

    public QuerySpec getQuerySpec() {
        return querySpec;
    }

    private CatalogRequestMessage() {
    }

    public static class Builder {
        private CatalogRequestMessage message;

        @JsonCreator
        public static CatalogRequestMessage.Builder newInstance() {
            return new CatalogRequestMessage.Builder();
        }

        public CatalogRequestMessage.Builder protocol(String protocol) {
            this.message.protocol = protocol;
            return this;
        }

        @Deprecated
        public CatalogRequestMessage.Builder connectorId(String connectorId) {
            this.message.connectorId = connectorId;
            return this;
        }

        public CatalogRequestMessage.Builder counterPartyAddress(String callbackAddress) {
            this.message.counterPartyAddress = callbackAddress;
            return this;
        }

        public CatalogRequestMessage.Builder querySpec(QuerySpec querySpec) {
            this.message.querySpec = querySpec;
            return this;
        }

        public CatalogRequestMessage build() {
            Objects.requireNonNull(message.protocol, "protocol");

            if (message.querySpec == null) {
                message.querySpec = QuerySpec.none();
            }

            return message;
        }

        private Builder() {
            message = new CatalogRequestMessage();
        }

    }
}
