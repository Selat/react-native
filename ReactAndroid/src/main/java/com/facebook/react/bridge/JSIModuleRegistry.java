/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.facebook.react.bridge;

import com.facebook.infer.annotation.Assertions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSIModuleRegistry {

  private final Map<JSIModuleType, JSIModuleHolder> mModules = new HashMap<>();

  public JSIModuleRegistry() { }

  public JSIModule getModule(JSIModuleType moduleType) {
    JSIModuleHolder jsiModuleHolder = mModules.get(moduleType);
    if (jsiModuleHolder == null) {
      throw new IllegalArgumentException("Unable to find JSIModule for class " + moduleType);
    }
    return Assertions.assertNotNull(jsiModuleHolder.getJSIModule());
  }

  public void registerModules(List<JSIModuleSpec> jsiModules) {
    for (JSIModuleSpec spec : jsiModules) {
      mModules.put(spec.getJSIModuleType(), new JSIModuleHolder(spec));
    }
  }

  public void notifyJSInstanceDestroy() {
    for (JSIModuleHolder moduleHolder : mModules.values()) {
      moduleHolder.notifyJSInstanceDestroy();
    }
  }
}
