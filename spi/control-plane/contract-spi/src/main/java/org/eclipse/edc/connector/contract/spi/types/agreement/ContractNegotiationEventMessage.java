/*
 *  Copyright (c) 2021 Fraunhofer Institute for Software and Systems Engineering
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

package org.eclipse.edc.connector.contract.spi.types.agreement;

import org.eclipse.edc.connector.contract.spi.types.protocol.ContractRemoteMessage;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static java.util.UUID.randomUUID;

public class ContractNegotiationEventMessage implements ContractRemoteMessage {
    private String id;
    private String protocol = "unknown";
    private String counterPartyAddress;
    private String processId;
    private Type type;

    @Override
    @NotNull
    public String getId() {
        return id;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public void setProtocol(String protocol) {
        Objects.requireNonNull(protocol);
        this.protocol = protocol;
    }

    @Override
    public String getCounterPartyAddress() {
        return counterPartyAddress;
    }

    @Override
    @NotNull
    public String getProcessId() {
        return processId;
    }

    @NotNull
    public Type getType() {
        return type;
    }

    public static class Builder {
        private final ContractNegotiationEventMessage message;

        private Builder() {
            this.message = new ContractNegotiationEventMessage();
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder id(String id) {
            this.message.id = id;
            return this;
        }

        public Builder protocol(String protocol) {
            this.message.protocol = protocol;
            return this;
        }

        public Builder counterPartyAddress(String counterPartyAddress) {
            this.message.counterPartyAddress = counterPartyAddress;
            return this;
        }

        public Builder processId(String processId) {
            this.message.processId = processId;
            return this;
        }

        public Builder type(Type type) {
            this.message.type = type;
            return this;
        }

        public ContractNegotiationEventMessage build() {
            if (message.id == null) {
                message.id = randomUUID().toString();
            }
            Objects.requireNonNull(message.processId, "processId");
            Objects.requireNonNull(message.type, "type");
            return message;
        }
    }

    public enum Type {
        ACCEPTED, FINALIZED
    }
}
