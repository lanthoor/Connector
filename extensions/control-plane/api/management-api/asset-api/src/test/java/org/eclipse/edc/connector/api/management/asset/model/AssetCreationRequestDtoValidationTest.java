/*
 *  Copyright (c) 2020 - 2022 Microsoft Corporation
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

package org.eclipse.edc.connector.api.management.asset.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.eclipse.edc.api.model.DataAddressDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AssetCreationRequestDtoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void verifyValidation_assetEntryDto_missingAsset() {
        var entry = AssetEntryDto.Builder.newInstance()
                .asset(null) //should break validation
                .dataAddress(DataAddressDto.Builder.newInstance().build())
                .build();

        var result = validator.validate(entry);

        assertThat(result).hasSize(3).anySatisfy(cv -> assertThat(cv.getMessage()).isEqualTo("asset cannot be null"));
    }

    @Test
    void verifyValidation_assetEntryDto_missingDataAddress() {
        var entry = AssetEntryDto.Builder.newInstance()
                .asset(AssetCreationRequestDto.Builder.newInstance().id("test-asset").build())
                .dataAddress(null) // should break validation
                .build();

        var result = validator.validate(entry);

        assertThat(result).anySatisfy(cv -> assertThat(cv.getMessage()).isEqualTo("dataAddress cannot be null"));
    }

    @Test
    void verifyValidation_assetDto_missingProperties() {
        var asset = AssetCreationRequestDto.Builder.newInstance()
                .properties(null)
                .build();

        var result = validator.validate(asset);

        assertThat(result).anySatisfy(cv -> assertThat(cv.getMessage()).isEqualTo("properties cannot be null"));
    }

    @Test
    void verifyValidation_assetDto_blankId() {
        var asset = AssetCreationRequestDto.Builder.newInstance()
                .id(" ")
                .build();

        var result = validator.validate(asset);

        assertThat(result).anySatisfy(cv -> assertThat(cv.getMessage()).isEqualTo("id must be either null or not blank"));
    }

    @Test
    void verifyValidation_assetDto_duplicateProperties() {
        var asset = AssetCreationRequestDto.Builder.newInstance()
                .id("test-asset")
                .properties(Map.of("key", "value"))
                .privateProperties(Map.of("key", "value"))
                .build();

        var result = validator.validate(asset);

        assertThat(result).anySatisfy(cv -> assertThat(cv.getMessage()).isEqualTo("no duplicate keys in properties and private properties"));
    }
}
