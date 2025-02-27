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

package org.eclipse.edc.spi.types.domain.metadata;

import org.eclipse.edc.spi.types.TypeManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class QueryRequestTest {

    @Test
    void verifyDeserialization() throws IOException {
        var mapper = new TypeManager().getMapper();

        QueryRequest process = QueryRequest.Builder.newInstance().protocol("foo").connectorId("id").counterPartyAddress("https://localhost:8080").queryLanguage("bar").query("baz").build();
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, process);

        QueryRequest deserialized = mapper.readValue(writer.toString(), QueryRequest.class);

        assertNotNull(deserialized);
    }


}
