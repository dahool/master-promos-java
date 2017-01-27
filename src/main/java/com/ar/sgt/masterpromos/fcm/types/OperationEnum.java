// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.ar.sgt.masterpromos.fcm.types;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OperationEnum {

    @JsonProperty("create")
    Create,

    @JsonProperty("add")
    Add,

    @JsonProperty("remove")
    Remove

}
