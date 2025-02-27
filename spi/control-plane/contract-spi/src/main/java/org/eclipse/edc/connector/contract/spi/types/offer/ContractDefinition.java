/*
 *  Copyright (c) 2021 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - initial API and implementation
 *
 */

package org.eclipse.edc.connector.contract.spi.types.offer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.eclipse.edc.spi.entity.Entity;
import org.eclipse.edc.spi.query.Criterion;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Defines the parameters of a contract.
 * <p>
 * The {@link #assetsSelector} defines which assets this contract applies to. Access policy defines the non-public requirements for accessing a set of assets
 * governed by the contract. These requirements are therefore not advertised to the agent. For example, access control policy may require an agent to be in a business partner tier.
 * Contract policy defines the requirements governing use an agent must follow when accessing the data. This policy is advertised to agents as part of a contract.
 * <p>
 * A participant agent may access a contract (and its associated assets) if the union of the access policy and contract policy is satisfied by the agent.
 * <p>
 * Note that the id must be a UUID.
 */
@JsonDeserialize(builder = ContractDefinition.Builder.class)
public class ContractDefinition extends Entity {

    private String accessPolicyId;
    private String contractPolicyId;
    private final List<Criterion> assetsSelector = new ArrayList<>();

    private ContractDefinition() {
    }

    @NotNull
    public String getAccessPolicyId() {
        return accessPolicyId;
    }

    @NotNull
    public String getContractPolicyId() {
        return contractPolicyId;
    }

    @NotNull
    public List<Criterion> getAssetsSelector() {
        return assetsSelector;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accessPolicyId, contractPolicyId, assetsSelector);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContractDefinition that = (ContractDefinition) o;
        return Objects.equals(id, that.id) && Objects.equals(accessPolicyId, that.accessPolicyId) &&
                Objects.equals(contractPolicyId, that.contractPolicyId) &&
                Objects.equals(assetsSelector, that.assetsSelector);
    }

    @Override
    public String toString() {
        return "ContractDefinition{" +
                "accessPolicyId='" + accessPolicyId + '\'' +
                ", contractPolicyId='" + contractPolicyId + '\'' +
                ", assetsSelector=" + assetsSelector +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder extends Entity.Builder<ContractDefinition, Builder> {

        private Builder() {
            super(new ContractDefinition());
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder id(String id) {
            entity.id = id;
            return this;
        }

        @Override
        public Builder self() {
            return this;
        }

        public Builder accessPolicyId(String policyId) {
            entity.accessPolicyId = policyId;
            return this;
        }

        public Builder contractPolicyId(String policyId) {
            entity.contractPolicyId = policyId;
            return this;
        }

        public Builder assetsSelectorCriterion(Criterion criterion) {
            entity.assetsSelector.add(criterion);
            return this;
        }

        public Builder assetsSelector(List<Criterion> criteria) {
            entity.assetsSelector.addAll(criteria);
            return this;
        }

        @Override
        public ContractDefinition build() {
            if (entity.getId() == null) {
                id(UUID.randomUUID().toString());
            }
            Objects.requireNonNull(entity.accessPolicyId);
            Objects.requireNonNull(entity.contractPolicyId);
            return super.build();
        }
    }
}
