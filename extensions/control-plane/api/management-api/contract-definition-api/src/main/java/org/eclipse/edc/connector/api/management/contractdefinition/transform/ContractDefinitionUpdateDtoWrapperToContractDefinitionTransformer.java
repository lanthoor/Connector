/*
 *  Copyright (c) 2023 Bayerische Motoren Werke Aktiengesellschaft (BMW AG)
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Bayerische Motoren Werke Aktiengesellschaft (BMW AG) - initial API and implementation
 *
 */

package org.eclipse.edc.connector.api.management.contractdefinition.transform;

import org.eclipse.edc.api.transformer.DtoTransformer;
import org.eclipse.edc.connector.api.management.contractdefinition.model.ContractDefinitionUpdateDtoWrapper;
import org.eclipse.edc.connector.contract.spi.types.offer.ContractDefinition;
import org.eclipse.edc.spi.query.Criterion;
import org.eclipse.edc.transform.spi.TransformerContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ContractDefinitionUpdateDtoWrapperToContractDefinitionTransformer implements DtoTransformer<ContractDefinitionUpdateDtoWrapper, ContractDefinition> {

    @Override
    public Class<ContractDefinitionUpdateDtoWrapper> getInputType() {
        return ContractDefinitionUpdateDtoWrapper.class;
    }

    @Override
    public Class<ContractDefinition> getOutputType() {
        return ContractDefinition.class;
    }

    @Override
    public @Nullable ContractDefinition transform(@NotNull ContractDefinitionUpdateDtoWrapper object, @NotNull TransformerContext context) {
        var assetsSelector = object.getContractDefinition().getAssetsSelector().stream().map(it -> context.transform(it, Criterion.class)).toList();
        return ContractDefinition.Builder.newInstance()
                .id(object.getId())
                .accessPolicyId(object.getContractDefinition().getAccessPolicyId())
                .contractPolicyId(object.getContractDefinition().getContractPolicyId())
                .assetsSelector(assetsSelector)
                .build();
    }
}
